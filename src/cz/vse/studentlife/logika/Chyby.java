package cz.vse.studentlife.logika;

/** Chybová hlášení E1–E30 podle části 4.4 zadání. */
public final class Chyby {

    private Chyby() {
    }

    public static final String E1 = "Tomu nerozumím. Napiš „pomoc“ pro seznam příkazů.";
    public static final String E4 = "Dveře pokoje jsou zamčené. Bez klíče se dovnitř nedostaneš.";
    public static final String E5 = "Vyhazovač ti položí ruku na rameno: „Bez náramku ne, kámo.“";
    public static final String E6 = "Škola je v noci zavřená. Vrátný uvnitř spí a nepustil by ani rektora.";
    public static final String E9 = "Batoh je plný (5/5). Nejdřív něco polož.";
    public static final String E13 = "Tady se nic neprodává.";
    public static final String E18 = "Tady se učit nedá. Na to potřebuješ klid studovny.";
    public static final String E20 = "Nikdo se tě na nic neptá.";
    public static final String E21 = "Odpověz písmenem a, b nebo c.";
    public static final String E23 = "Tady nikdo nic nevykupuje.";
    public static final String E24 = "Tady? Bez hudby? Lidi by si mysleli, že máš záchvat.";
    public static final String E25 = "Tady spát nemůžeš. Teda můžeš, ale ráno by ses probudil bez peněženky. "
            + "Spí se v posteli.";
    public static final String E26 = "Jméno uložené hry smí obsahovat jen písmena bez diakritiky, číslice "
            + "a podtržítko.";

    public static String e2(String syntaxe) {
        return "Chybí parametr. Správně: " + syntaxe;
    }

    public static String e3(String x) {
        return "Odsud se do „" + x + "“ jít nedá.";
    }

    public static String e7(String x) {
        return "Věc „" + x + "“ tady není.";
    }

    public static String e8(String x) {
        return "„" + x + "“ se odnést nedá.";
    }

    public static String e10(String x) {
        return "„" + x + "“ v batohu nemáš.";
    }

    public static String e11(String x) {
        return "„" + x + "“ se otevřít nedá.";
    }

    public static String e12(String x) {
        return "„" + x + "“ už je otevřená.";
    }

    public static String e14(String x) {
        return "„" + x + "“ tady neprodávají.";
    }

    public static String e15(String x, int cena, int penize) {
        return "Na „" + x + "“ nemáš. Stojí " + cena + " Kč, máš " + penize + " Kč.";
    }

    public static String e16(String x) {
        return "„" + x + "“ se nejí. I když… ne.";
    }

    public static String e17(String x) {
        return "Z „" + x + "“ se učit nedá.";
    }

    public static String e19(String x) {
        return "Nikdo jménem „" + x + "“ tu není.";
    }

    public static String e22(String postava, String x) {
        return postava + " o „" + x + "“ nestojí.";
    }

    public static String e27(String jmeno) {
        return "Hru se nepodařilo uložit do souboru saves/" + jmeno + ".sav.";
    }

    public static String e28(String jmeno) {
        return "Uloženou hru „" + jmeno + "“ nelze načíst: soubor saves/" + jmeno + ".sav neexistuje.";
    }

    public static String e29(String jmeno) {
        return "Uloženou hru „" + jmeno + "“ nelze načíst: soubor je poškozený.";
    }

    public static String e30(String syntaxe) {
        return "Příliš mnoho parametrů. Správně: " + syntaxe;
    }
}
