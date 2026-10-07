package cz.vse.studentlife.logika;

/** Příkaz „odpovez &lt;a|b|c&gt;“ – odpověď na otázku docenta u zkoušky. */
public class PrikazOdpovez extends AbstraktniPrikaz {

    private static final String SPRAVNA_ODPOVED = "b";

    public PrikazOdpovez(Hra hra) {
        super(hra, "odpovez", "odpovez <a|b|c>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String odpoved = parametry[0];
        HerniPlan plan = plan();
        if (!plan.zkouskaProbiha()) {
            return Chyby.E20;
        }
        if (!odpoved.equals("a") && !odpoved.equals("b") && !odpoved.equals("c")) {
            return Chyby.E21;
        }

        boolean spravne = odpoved.equals(SPRAVNA_ODPOVED);
        if (spravne && plan.jeNaucen()) {
            if (batoh().jePlny()) {
                return Chyby.E9;
            }
            plan.setZkouskaProbiha(false);
            plan.setZkouskaSlozena(true);
            plan.setKredity(HerniPlan.KREDITY_CIL);
            batoh().vloz(Vec.vytvor("potvrzeni"));
            return "Docent chvíli mlčí. „Výborně. Kupodivu.“ Podepíše ti potvrzení o vykonané zkoušce. "
                    + "Kredity: 180/180!\n"
                    + "Do batohu přibylo: potvrzeni.";
        }

        hra.ukonciHru();
        String uvod = spravne ? "„Správně. A proč?“ zeptá se docent. Ticho. Dlouhé ticho.\n" : "";
        return uvod
                + "Docent sundá brýle a pomalu je vyčistí. „Děkuji, to stačí.“ Do indexu ti zapíše F. Došly ti "
                + "kreditové poukázky. Ze školy tě vyhodí dřív, než stihneš říct „mezní užitek“.\n"
                + "KONEC HRY – PROHRA: vyletěl jsi od zkoušky.";
    }
}
