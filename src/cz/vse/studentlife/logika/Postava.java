package cz.vse.studentlife.logika;

/** Postava ve hře. Identifikátor se píše do příkazů, jméno se používá v hláškách. */
public enum Postava {
    VRATNA("vratna", "Vrátná"),
    DOCENT("docent", "Docent"),
    REFERENTKA("referentka", "Referentka"),
    HONZA("honza", "Honza"),
    VYHAZOVAC("vyhazovac", "Vyhazovač"),
    KEBABAR("kebabar", "Kebabář");

    private final String id;
    private final String jmeno;

    Postava(String id, String jmeno) {
        this.id = id;
        this.jmeno = jmeno;
    }

    public String getId() {
        return id;
    }

    public String getJmeno() {
        return jmeno;
    }
}
