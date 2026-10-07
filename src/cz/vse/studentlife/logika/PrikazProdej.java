package cz.vse.studentlife.logika;

/** Příkaz „prodej &lt;věc&gt;“ – kebabář v noci vykupuje skripta za 30 Kč. */
public class PrikazProdej extends AbstraktniPrikaz {

    private static final int CENA_SKRIPT = 30;

    public PrikazProdej(Hra hra) {
        super(hra, "prodej", "prodej <věc>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String co = parametry[0];
        if (!tady().jeTuPostava(Postava.KEBABAR.getId())) {
            return Chyby.E23;
        }
        if (!batoh().obsahuje(co)) {
            return Chyby.e10(co);
        }
        if (!plan().jeNoc()) {
            return "Kebabář: „Ve dne ne, šéf má všude kamery. Přijď v noci.“";
        }
        if (!co.equals("skripta")) {
            return "Kebabář: „Tohle nechci. Beru jen skripta.“";
        }
        batoh().odeber(co);
        plan().zmenPenize(CENA_SKRIPT);
        return "Kebabář skripta prolistuje. „Mikroekonomie? Hoří krásně. Dám ti třicet.“\n"
                + "Prodal jsi: skripta (+" + CENA_SKRIPT + " Kč).";
    }
}
