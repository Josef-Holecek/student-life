package cz.vse.studentlife.logika;

/**
 * Předek příkazů: drží odkaz na hru a hlídá počet parametrů (E2 / E30).
 */
public abstract class AbstraktniPrikaz implements IPrikaz {

    protected final Hra hra;
    private final String nazev;
    private final String syntaxe;
    private final int minParametru;
    private final int maxParametru;

    protected AbstraktniPrikaz(Hra hra, String nazev, String syntaxe, int minParametru, int maxParametru) {
        this.hra = hra;
        this.nazev = nazev;
        this.syntaxe = syntaxe;
        this.minParametru = minParametru;
        this.maxParametru = maxParametru;
    }

    @Override
    public String getNazev() {
        return nazev;
    }

    @Override
    public String getSyntaxe() {
        return syntaxe;
    }

    @Override
    public final String provedPrikaz(String... parametry) {
        if (parametry.length < minParametru) {
            return Chyby.e2(syntaxe);
        }
        if (parametry.length > maxParametru) {
            return Chyby.e30(syntaxe);
        }
        return proved(parametry);
    }

    /** Vlastní provedení – počet parametrů už je ověřený. */
    protected abstract String proved(String[] parametry);

    protected HerniPlan plan() {
        return hra.getHerniPlan();
    }

    protected Batoh batoh() {
        return plan().getBatoh();
    }

    protected Prostor tady() {
        return plan().getAktualniProstor();
    }
}
