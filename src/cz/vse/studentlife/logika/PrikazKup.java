package cz.vse.studentlife.logika;

/** Příkaz „kup &lt;zboží&gt;“ – nákup v obchodě nebo z automatu; kebab jde zaplatit poukazem. */
public class PrikazKup extends AbstraktniPrikaz {

    private static final int OBED_S_ISIC = 82;

    public PrikazKup(Hra hra) {
        super(hra, "kup", "kup <zboží>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String co = parametry[0];
        Prostor prostor = tady();
        if (!prostor.jeObchod()) {
            return Chyby.E13;
        }
        if (!prostor.prodava(co)) {
            return Chyby.e14(co);
        }

        if (co.equals("kebab") && batoh().obsahuje("poukaz")) {
            batoh().odeber("poukaz");
            batoh().vloz(Vec.vytvor("kebab"));
            return "Platíš poukazem. Kebabář ho prohlédne proti světlu, pokrčí rameny a podá ti kebab.\n"
                    + "Koupil jsi: kebab (poukaz).";
        }

        boolean studentskaCena = co.equals("obed") && batoh().obsahuje("isic");
        int cena = studentskaCena ? OBED_S_ISIC : prostor.getCena(co);
        HerniPlan plan = plan();
        if (plan.getPenize() < cena) {
            return Chyby.e15(co, cena, plan.getPenize());
        }
        if (batoh().jePlny()) {
            return Chyby.E9;
        }
        plan.zmenPenize(-cena);
        batoh().vloz(Vec.vytvor(co));
        return "Koupil jsi: " + co + " (" + cena + " Kč" + (studentskaCena ? ", studentská cena s ISIC" : "")
                + ").";
    }
}
