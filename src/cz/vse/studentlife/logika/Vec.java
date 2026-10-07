package cz.vse.studentlife.logika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Věc ve hře. Může být přenositelná (vejde se do batohu), nebo kulisa.
 * Kontejner (skříň, lednice) má vlastní obsah, který jde sebrat až po otevření.
 */
public class Vec {

    private static final Set<String> NEPRENOSITELNE = Set.of(
            "postel", "skrin", "lednice", "nastenka", "automat", "lavicka", "socha",
            "kavovar", "pocitac", "tabule", "razitko", "lampa", "parket", "bar");

    private static final Set<String> KONTEJNERY = Set.of("skrin", "lednice");

    private static final Set<String> VSECHNY = Set.of(
            "klice", "skripta", "isic", "horcice", "postel", "skrin", "lednice", "krabice",
            "nastenka", "automat", "lavicka", "rohlik", "energetak", "jizdenka", "socha",
            "kavovar", "obed", "tac", "propiska", "pocitac", "tabule", "potvrzeni", "razitko",
            "diplom", "lampa", "naramek", "tacek", "parket", "bar", "poukaz", "kebab",
            "ubrousek", "nahradni_klic");

    private final String id;
    private final boolean prenositelna;
    private final boolean kontejner;
    private boolean otevreny;
    private final List<Vec> obsah = new ArrayList<>();

    private Vec(String id) {
        this.id = id;
        this.prenositelna = !NEPRENOSITELNE.contains(id);
        this.kontejner = KONTEJNERY.contains(id);
    }

    /** Vytvoří věc podle identifikátoru. */
    public static Vec vytvor(String id) {
        if (!existuje(id)) {
            throw new IllegalArgumentException("Neznámá věc: " + id);
        }
        return new Vec(id);
    }

    public static boolean existuje(String id) {
        return VSECHNY.contains(id);
    }

    public String getId() {
        return id;
    }

    public boolean jePrenositelna() {
        return prenositelna;
    }

    public boolean jeKontejner() {
        return kontejner;
    }

    public boolean jeOtevreny() {
        return otevreny;
    }

    public void otevri() {
        otevreny = true;
    }

    public List<Vec> getObsah() {
        return Collections.unmodifiableList(obsah);
    }

    public void vlozDoObsahu(Vec vec) {
        obsah.add(vec);
    }

    /** Vyjme věc z obsahu kontejneru, nebo vrátí null. */
    public Vec vyjmiZObsahu(String idVeci) {
        for (int i = 0; i < obsah.size(); i++) {
            if (obsah.get(i).getId().equals(idVeci)) {
                return obsah.remove(i);
            }
        }
        return null;
    }

    public String vypisObsahu() {
        return obsah.stream().map(Vec::getId).reduce((a, b) -> a + ", " + b).orElse("");
    }

    /** Jak se věc zobrazí v řádku „Věci:“ ve výpisu prostoru. */
    public String popisVeVypisu() {
        if (!kontejner) {
            return id;
        }
        if (!otevreny) {
            return id + " (zavřená)";
        }
        if (obsah.isEmpty()) {
            return id + " (otevřená, prázdná)";
        }
        return id + " (otevřená: " + vypisObsahu() + ")";
    }
}
