package cz.vse.studentlife.logika;

/** Příkaz „studuj &lt;věc&gt;“ – učení ze skript ve studovně. */
public class PrikazStuduj extends AbstraktniPrikaz {

    public PrikazStuduj(Hra hra) {
        super(hra, "studuj", "studuj <věc>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String co = parametry[0];
        if (!batoh().obsahuje(co)) {
            return Chyby.e10(co);
        }
        if (!co.equals("skripta")) {
            return Chyby.e17(co);
        }
        if (!tady().getId().equals("studovna")) {
            return Chyby.E18;
        }
        if (plan().jeNaucen()) {
            return "Už to umíš. Víc se ti toho do hlavy nevejde.";
        }
        plan().setNaucen(true);
        plan().zmenEnergii(-2);
        return "Otevřeš skripta a ponoříš se do mikroekonomie. Nabídka, poptávka, mezní užitek… Po dvou "
                + "hodinách to kupodivu začne dávat smysl. Energie −2. Na zkoušku jsi připravený.";
    }
}
