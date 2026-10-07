package cz.vse.studentlife.logika;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * Herní svět a celý stav hry: prostory, batoh, energie, peníze, kredity a příznaky příběhu.
 */
public class HerniPlan {

    public static final int MAX_ENERGIE = 30;
    public static final int KREDITY_START = 174;
    public static final int KREDITY_CIL = 180;

    private final Map<String, Prostor> prostory = new LinkedHashMap<>();
    private Prostor aktualniProstor;
    private final Batoh batoh = new Batoh();

    private int energie = 12;
    private int penize = 120;
    private int kredity = KREDITY_START;

    private boolean noc;
    private boolean oslaveno;
    private boolean naucen;
    private boolean zkouskaProbiha;
    private boolean zkouskaSlozena;
    private boolean diplomVydan;
    private boolean naramekPredan;
    private boolean nahradniKlicVydan;
    private boolean kebabSnezen;

    public HerniPlan() {
        zalozProstory();
        batoh.vloz(Vec.vytvor("klice"));
    }

    private void zalozProstory() {
        Prostor pokoj = pridej("pokoj", "Pokoj 312, Kolej Blanice",
                "Tvůj pokoj má rozlohu průměrné šatní skříně. Na stole stojí hrnek, ve kterém se pomalu "
                        + "vyvíjí nová forma života, a na zdi visí rozvrh z prváku, který nikdo nesundal.");
        Prostor chodba = pridej("chodba", "Chodba koleje",
                "Dlouhá chodba voní instantními nudlemi a cizím pracím práškem. Na nástěnce visí vzkaz "
                        + "„KDO MI SNĚDL JOGURT?!“ – už třetí měsíc bez odpovědi.");
        Prostor vratnice = pridej("vratnice", "Vrátnice",
                "Prosklená budka u vchodu. Z rádia hraje dechovka a paní vrátná Božena sleduje každého, "
                        + "kdo projde, pohledem celní správy.");
        Prostor zastavkaBlanice = pridej("zastavka_blanice", "Zastávka Blanice",
                "Tramvajová zastávka před kolejí. Automat na jízdenky přijímá karty, mince i tiché modlitby. "
                        + "Na lavičce někdo zapomněl půl rohlíku a veškerou naději.");
        Prostor vecerka = pridej("vecerka", "Večerka",
                "Malý obchod, kde mají všechno od rohlíků po nabíječky, jen nikdy v tom pořadí, v jakém to "
                        + "hledáš. Rohlíky jsou prý čerstvé – tvrdí to cedulka.");
        Prostor zastavkaSkola = pridej("zastavka_skola", "Zastávka Škola",
                "Zastávka kousek od budovy školy. Bronzová socha státníka s doutníkem shlíží na studenty "
                        + "s výrazem „tohle má být budoucnost národa?“");
        Prostor vestibul = pridej("vestibul", "Vestibul školy",
                "Hlavní hala školy. Davy studentů v mikinách, rozpis zkoušek na nástěnce a kávovar, který "
                        + "peníze bere vždy, ale kávu vydává jen někdy.");
        Prostor menza = pridej("menza", "Menza",
                "Fronta sahá až ke dveřím. Dnes se podává „plátek po myslivecku“ – myslivce tu naposledy "
                        + "viděli v roce 1998.");
        Prostor studovna = pridej("studovna", "Studovna",
                "Ticho tak hluboké, že slyšíš, jak sousedovi praská sebevědomí. Jediné místo ve škole, kde "
                        + "se dá opravdu učit.");
        Prostor ucebna = pridej("ucebna", "Učebna",
                "Zkušební místnost. Na tabuli stojí: „MOBILY VYPNOUT. NADĚJE TAKY.“ U katedry sedí docent "
                        + "a míchá si čaj s klidem člověka, který už vyhodil tisíce studentů.");
        Prostor studijni = pridej("studijni", "Studijní oddělení",
                "Na dveřích visí úřední hodiny, které zatím nikdo nepochopil. Za přepážkou sedí paní "
                        + "referentka a vedle ní razítko mocnější než celý akademický senát.");
        Prostor ulice = pridej("ulice", "Ulice",
                "Ulice, kde se po setmění potkává půlka školy. Hospoda, klub a kebab na sto metrech – "
                        + "ekonom by řekl „efektivní alokace zdrojů“. U vchodu do klubu stojí vyhazovač "
                        + "velikosti menšího automobilu.");
        Prostor hospoda = pridej("hospoda", "Hospoda U Zlatého kreditu",
                "Lepkavé stoly, hokej v televizi a vůně, kterou nejde popsat, jen přežít. V rohu sedí tvůj "
                        + "kamarád Honza a mává na tebe.");
        Prostor klub = pridej("klub", "Klub Sklep",
                "Basy, které cítíš v zubech. Parket je lepkavý, světla blikají a nikdo tu netuší, kolik je "
                        + "hodin.");
        Prostor kebab = pridej("kebab", "Kebab Nonstop",
                "Jediné místo v ulici, které svítí ve dne i v noci. Kebabář krájí maso s klidem zenového "
                        + "mistra.");

        // východy (pořadí = pořadí ve výpisu)
        pokoj.pridejVychod(chodba, false);
        chodba.pridejVychod(pokoj, false);
        chodba.pridejVychod(vratnice, false);
        vratnice.pridejVychod(chodba, false);
        vratnice.pridejVychod(zastavkaBlanice, false);
        zastavkaBlanice.pridejVychod(vratnice, false);
        zastavkaBlanice.pridejVychod(vecerka, false);
        zastavkaBlanice.pridejVychod(zastavkaSkola, true);
        vecerka.pridejVychod(zastavkaBlanice, false);
        zastavkaSkola.pridejVychod(zastavkaBlanice, true);
        zastavkaSkola.pridejVychod(vestibul, false);
        zastavkaSkola.pridejVychod(ulice, false);
        vestibul.pridejVychod(zastavkaSkola, false);
        vestibul.pridejVychod(menza, false);
        vestibul.pridejVychod(studovna, false);
        vestibul.pridejVychod(ucebna, false);
        vestibul.pridejVychod(studijni, false);
        menza.pridejVychod(vestibul, false);
        studovna.pridejVychod(vestibul, false);
        ucebna.pridejVychod(vestibul, false);
        studijni.pridejVychod(vestibul, false);
        ulice.pridejVychod(zastavkaSkola, false);
        ulice.pridejVychod(hospoda, false);
        ulice.pridejVychod(klub, false);
        ulice.pridejVychod(kebab, false);
        hospoda.pridejVychod(ulice, false);
        klub.pridejVychod(ulice, false);
        kebab.pridejVychod(ulice, false);

        // věci
        Vec skrin = Vec.vytvor("skrin");
        skrin.vlozDoObsahu(Vec.vytvor("skripta"));
        skrin.vlozDoObsahu(Vec.vytvor("isic"));
        Vec lednice = Vec.vytvor("lednice");
        lednice.vlozDoObsahu(Vec.vytvor("horcice"));
        pokoj.pridejVec(Vec.vytvor("postel"));
        pokoj.pridejVec(skrin);
        pokoj.pridejVec(lednice);
        vloz(chodba, "krabice", "nastenka");
        vloz(zastavkaBlanice, "automat", "lavicka");
        vloz(zastavkaSkola, "socha", "automat");
        vloz(vestibul, "kavovar");
        vloz(menza, "tac");
        vloz(studovna, "propiska", "pocitac");
        vloz(ucebna, "tabule");
        vloz(studijni, "razitko");
        vloz(ulice, "lampa");
        vloz(hospoda, "tacek");
        vloz(klub, "parket", "bar");
        vloz(kebab, "ubrousek");

        // postavy
        vratnice.pridejPostavu(Postava.VRATNA);
        ucebna.pridejPostavu(Postava.DOCENT);
        studijni.pridejPostavu(Postava.REFERENTKA);
        ulice.pridejPostavu(Postava.VYHAZOVAC);
        hospoda.pridejPostavu(Postava.HONZA);
        kebab.pridejPostavu(Postava.KEBABAR);

        // obchody
        zastavkaBlanice.pridejZbozi("jizdenka", 30);
        zastavkaSkola.pridejZbozi("jizdenka", 30);
        vecerka.pridejZbozi("rohlik", 6);
        vecerka.pridejZbozi("energetak", 39);
        menza.pridejZbozi("obed", 149);
        menza.setPopisNabidky("obed (82 Kč s ISIC / 149 Kč bez ISIC)");
        kebab.pridejZbozi("kebab", 129);
        kebab.setPopisNabidky("kebab (129 Kč nebo poukaz)");

        aktualniProstor = pokoj;
    }

    private Prostor pridej(String id, String nazev, String popis) {
        Prostor p = new Prostor(id, nazev, popis);
        prostory.put(id, p);
        return p;
    }

    private static void vloz(Prostor prostor, String... idVeci) {
        for (String id : idVeci) {
            prostor.pridejVec(Vec.vytvor(id));
        }
    }

    // ---------- stav ----------

    public Prostor getAktualniProstor() {
        return aktualniProstor;
    }

    public void setAktualniProstor(Prostor prostor) {
        aktualniProstor = prostor;
    }

    public Prostor getProstor(String id) {
        return prostory.get(id);
    }

    public Collection<Prostor> getProstory() {
        return prostory.values();
    }

    public Batoh getBatoh() {
        return batoh;
    }

    public int getEnergie() {
        return energie;
    }

    /** Změní energii; nad 30 se nezvýší. */
    public void zmenEnergii(int zmena) {
        energie = Math.min(MAX_ENERGIE, energie + zmena);
    }

    public int getPenize() {
        return penize;
    }

    public void zmenPenize(int zmena) {
        penize += zmena;
    }

    public int getKredity() {
        return kredity;
    }

    public void setKredity(int kredity) {
        this.kredity = kredity;
    }

    public boolean jeNoc() {
        return noc;
    }

    public void setNoc(boolean noc) {
        this.noc = noc;
    }

    public boolean jeOslaveno() {
        return oslaveno;
    }

    public void setOslaveno(boolean oslaveno) {
        this.oslaveno = oslaveno;
    }

    public boolean jeNaucen() {
        return naucen;
    }

    public void setNaucen(boolean naucen) {
        this.naucen = naucen;
    }

    public boolean zkouskaProbiha() {
        return zkouskaProbiha;
    }

    public void setZkouskaProbiha(boolean zkouskaProbiha) {
        this.zkouskaProbiha = zkouskaProbiha;
    }

    public boolean jeZkouskaSlozena() {
        return zkouskaSlozena;
    }

    public void setZkouskaSlozena(boolean zkouskaSlozena) {
        this.zkouskaSlozena = zkouskaSlozena;
    }

    public boolean jeDiplomVydan() {
        return diplomVydan;
    }

    public void setDiplomVydan(boolean diplomVydan) {
        this.diplomVydan = diplomVydan;
    }

    public boolean jeNaramekPredan() {
        return naramekPredan;
    }

    public void setNaramekPredan(boolean naramekPredan) {
        this.naramekPredan = naramekPredan;
    }

    public boolean jeNahradniKlicVydan() {
        return nahradniKlicVydan;
    }

    public void setNahradniKlicVydan(boolean nahradniKlicVydan) {
        this.nahradniKlicVydan = nahradniKlicVydan;
    }

    public boolean jeKebabSnezen() {
        return kebabSnezen;
    }

    public void setKebabSnezen(boolean kebabSnezen) {
        this.kebabSnezen = kebabSnezen;
    }

    /** Stavový řádek podle části 1.3 zadání. */
    public String stavovyRadek() {
        return "[Energie: " + energie + " | Peníze: " + penize + " Kč | Kredity: " + kredity + "/"
                + KREDITY_CIL + " | " + batoh.popis() + "]";
    }

    // ---------- ukládání a načítání ----------

    private static final String HLAVICKA = "studentlife-save";

    /** Převede celý stav hry na dvojice klíč–hodnota. */
    public Properties ulozStav() {
        Properties p = new Properties();
        p.setProperty(HLAVICKA, "1");
        p.setProperty("prostor", aktualniProstor.getId());
        p.setProperty("energie", String.valueOf(energie));
        p.setProperty("penize", String.valueOf(penize));
        p.setProperty("kredity", String.valueOf(kredity));
        p.setProperty("noc", String.valueOf(noc));
        p.setProperty("oslaveno", String.valueOf(oslaveno));
        p.setProperty("naucen", String.valueOf(naucen));
        p.setProperty("zkouskaProbiha", String.valueOf(zkouskaProbiha));
        p.setProperty("zkouskaSlozena", String.valueOf(zkouskaSlozena));
        p.setProperty("diplomVydan", String.valueOf(diplomVydan));
        p.setProperty("naramekPredan", String.valueOf(naramekPredan));
        p.setProperty("nahradniKlicVydan", String.valueOf(nahradniKlicVydan));
        p.setProperty("kebabSnezen", String.valueOf(kebabSnezen));
        p.setProperty("batoh", idcka(batoh.getVeci()));
        for (Prostor prostor : prostory.values()) {
            p.setProperty("veci." + prostor.getId(), idcka(prostor.getVeci()));
            for (Vec v : prostor.getVeci()) {
                if (v.jeKontejner()) {
                    p.setProperty("kontejner." + v.getId(), v.jeOtevreny() + ":" + idcka(v.getObsah()));
                }
            }
        }
        return p;
    }

    private static String idcka(List<Vec> veci) {
        return veci.stream().map(Vec::getId).collect(Collectors.joining(","));
    }

    /**
     * Vytvoří herní plán z uloženého stavu.
     *
     * @throws IllegalArgumentException když je stav neúplný nebo nesmyslný (poškozený soubor)
     */
    public static HerniPlan zeStavu(Properties p) {
        if (!"1".equals(p.getProperty(HLAVICKA))) {
            throw new IllegalArgumentException("chybí hlavička");
        }
        HerniPlan plan = new HerniPlan();
        Prostor akt = plan.prostory.get(povinne(p, "prostor"));
        if (akt == null) {
            throw new IllegalArgumentException("neznámý prostor");
        }
        plan.aktualniProstor = akt;
        plan.energie = cislo(p, "energie");
        plan.penize = cislo(p, "penize");
        plan.kredity = cislo(p, "kredity");
        if (plan.energie <= 0 || plan.energie > MAX_ENERGIE || plan.penize < 0
                || (plan.kredity != KREDITY_START && plan.kredity != KREDITY_CIL)) {
            throw new IllegalArgumentException("hodnoty mimo rozsah");
        }
        plan.noc = logicka(p, "noc");
        plan.oslaveno = logicka(p, "oslaveno");
        plan.naucen = logicka(p, "naucen");
        plan.zkouskaProbiha = logicka(p, "zkouskaProbiha");
        plan.zkouskaSlozena = logicka(p, "zkouskaSlozena");
        plan.diplomVydan = logicka(p, "diplomVydan");
        plan.naramekPredan = logicka(p, "naramekPredan");
        plan.nahradniKlicVydan = logicka(p, "nahradniKlicVydan");
        plan.kebabSnezen = logicka(p, "kebabSnezen");

        plan.batoh.odeber("klice");
        List<Vec> vBatohu = veci(povinne(p, "batoh"));
        if (vBatohu.size() > Batoh.KAPACITA) {
            throw new IllegalArgumentException("přeplněný batoh");
        }
        for (Vec v : vBatohu) {
            if (!v.jePrenositelna()) {
                throw new IllegalArgumentException("nepřenositelná věc v batohu");
            }
            plan.batoh.vloz(v);
        }

        for (Prostor prostor : plan.prostory.values()) {
            List<Vec> puvodni = new ArrayList<>(prostor.getVeci());
            for (Vec v : puvodni) {
                prostor.odeberVec(v.getId());
            }
            for (Vec v : veci(povinne(p, "veci." + prostor.getId()))) {
                if (v.jeKontejner()) {
                    String k = povinne(p, "kontejner." + v.getId());
                    int dvojtecka = k.indexOf(':');
                    if (dvojtecka < 0) {
                        throw new IllegalArgumentException("poškozený kontejner");
                    }
                    if (parseLogicka(k.substring(0, dvojtecka))) {
                        v.otevri();
                    }
                    for (Vec o : veci(k.substring(dvojtecka + 1))) {
                        v.vlozDoObsahu(o);
                    }
                }
                prostor.pridejVec(v);
            }
        }
        return plan;
    }

    private static String povinne(Properties p, String klic) {
        String hodnota = p.getProperty(klic);
        if (hodnota == null) {
            throw new IllegalArgumentException("chybí " + klic);
        }
        return hodnota.trim();
    }

    private static int cislo(Properties p, String klic) {
        return Integer.parseInt(povinne(p, klic));
    }

    private static boolean logicka(Properties p, String klic) {
        return parseLogicka(povinne(p, klic));
    }

    private static boolean parseLogicka(String s) {
        if (s.equals("true")) {
            return true;
        }
        if (s.equals("false")) {
            return false;
        }
        throw new IllegalArgumentException("neplatná logická hodnota");
    }

    private static List<Vec> veci(String seznam) {
        List<Vec> vysledek = new ArrayList<>();
        if (seznam.isBlank()) {
            return vysledek;
        }
        for (String id : seznam.split(",")) {
            vysledek.add(Vec.vytvor(id.trim()));
        }
        return vysledek;
    }
}
