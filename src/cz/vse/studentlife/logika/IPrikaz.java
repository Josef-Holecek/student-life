package cz.vse.studentlife.logika;

/** Společné rozhraní všech příkazů hry. */
public interface IPrikaz {

    /** Název, kterým se příkaz volá (bez diakritiky). */
    String getNazev();

    /** Syntaxe pro chybová hlášení E2 a E30, např. „jdi &lt;prostor&gt;“. */
    String getSyntaxe();

    /**
     * Provede příkaz a vrátí text pro hráče (bez stavového řádku).
     *
     * @param parametry slova za názvem příkazu (už normalizovaná)
     */
    String provedPrikaz(String... parametry);
}
