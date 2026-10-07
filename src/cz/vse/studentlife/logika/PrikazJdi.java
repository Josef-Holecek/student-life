package cz.vse.studentlife.logika;

/** Příkaz „jdi &lt;prostor&gt;“ – přesun, zámky, tramvaj s revizorem a spotřeba energie. */
public class PrikazJdi extends AbstraktniPrikaz {

    public PrikazJdi(Hra hra) {
        super(hra, "jdi", "jdi <prostor>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String kam = parametry[0];
        HerniPlan plan = plan();
        Prostor odkud = tady();
        Prostor.Vychod vychod = odkud.getVychod(kam);
        if (vychod == null) {
            return Chyby.e3(kam);
        }
        Prostor cil = vychod.cil();
        boolean maKlice = batoh().obsahuje("klice");
        boolean maNahradni = batoh().obsahuje("nahradni_klic");

        if (cil.getId().equals("pokoj") && !maKlice && !maNahradni) {
            return Chyby.E4;
        }
        if (cil.getId().equals("klub") && !batoh().obsahuje("naramek")) {
            return Chyby.E5;
        }
        if (odkud.getId().equals("zastavka_skola") && cil.getId().equals("vestibul") && plan.jeNoc()) {
            return Chyby.E6;
        }

        StringBuilder text = new StringBuilder();
        if (vychod.tramvaj()) {
            if (!batoh().obsahuje("jizdenka")) {
                hra.ukonciHru();
                return "Nastoupíš do tramvaje bez jízdenky. Na další zastávce se zvednou dva nenápadní pánové "
                        + "v šusťákovkách: „Přepravní kontrola, jízdenky prosím.“\n"
                        + "Pokuta 1 500 Kč. Na účtu máš " + plan.getPenize() + " Kč. Nezbývá než zavolat domů: "
                        + "„Mami… potřeboval bych…“\n"
                        + "Místo stipendia dostaneš jízdenku domů. Studium končí.\n"
                        + "KONEC HRY – PROHRA: chytil tě revizor.";
            }
            batoh().odeber("jizdenka");
            if (plan.jeNoc()) {
                text.append("Noční tramvaj. Půlka vozu spí, druhá půlka zpívá. Jízdenku označíš – a o zastávku "
                        + "dál opravdu nastoupí revizor. Zkontroluje tě, zklamaně zabručí a jde dál.\n");
            } else {
                text.append("Nastoupíš do tramvaje a poctivě označíš jízdenku. Revizor dnes kontroluje jinou "
                        + "linku. Máš štěstí.\n");
            }
        } else {
            plan.zmenEnergii(plan.jeNoc() ? -2 : -1);
        }

        if (cil.getId().equals("pokoj")) {
            text.append(maKlice ? "Odemkneš dveře klíčem.\n"
                    : "Odemkneš dveře náhradním klíčem. Napoprvé to nejde, napodruhé taky ne, napotřetí ano.\n");
        }
        if (cil.getId().equals("klub")) {
            text.append("Vyhazovač se podívá na náramek, pak na tebe, pak zase na náramek. „Dobrý. Běž.“\n");
        }

        plan.setZkouskaProbiha(false);
        plan.setAktualniProstor(cil);
        text.append(cil.dlouhyPopis());
        return text.toString();
    }
}
