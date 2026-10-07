package cz.vse.studentlife;

import cz.vse.studentlife.logika.Hra;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Jednoduché testy bez knihoven: spuštění vypíše OK/CHYBA u každého případu. */
public final class HraTest {

    private static int chyb;
    private static final PrintStream OUT = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    private HraTest() {
    }

    public static void main(String[] args) throws Exception {
        Path saves = Files.createTempDirectory("studentlife-test");

        // vstup bez diakritiky a s nadbytečnými mezerami
        Hra hra = new Hra(saves);
        hra.zpracujPrikaz("Otevři   SKŘÍŇ");
        hra.zpracujPrikaz("seber skripta");
        over("diakritika a velká písmena", hra.zpracujPrikaz("  Polož   Skripta "),
                "Položil jsi: skripta.");

        // vítězný scénář končí výhrou s energií 6
        hra = new Hra(saves);
        String posledni = "";
        for (String p : ScenarePrepis.VITEZNY) {
            posledni = hra.zpracujPrikaz(p);
        }
        over("výhra", posledni, "GRATULUJEME! VYHRÁL JSI");
        over("výhra ukončí hru", String.valueOf(hra.konecHry()), "true");
        over("energie po výhře", String.valueOf(hra.getHerniPlan().getEnergie()), "6");

        // prohra – došla energie (hořčice při energii 2)
        hra = new Hra(saves);
        hra.zpracujPrikaz("otevri lednice");
        hra.zpracujPrikaz("seber horcice");
        for (int i = 0; i < 5; i++) {
            hra.zpracujPrikaz(i % 2 == 0 ? "jdi chodba" : "jdi pokoj");
        }
        over("energie 7 po 5 krocích", String.valueOf(hra.getHerniPlan().getEnergie()), "7");
        for (int i = 0; i < 5; i++) {
            hra.zpracujPrikaz(i % 2 == 0 ? "jdi pokoj" : "jdi chodba");
        }
        String vystup = hra.zpracujPrikaz("snez horcice");
        over("prohra energií", vystup, "KONEC HRY – PROHRA: došla ti energie.");
        over("prohra energií – prostor", vystup, "přímo tady – Pokoj 312, Kolej Blanice.");

        // prohra u zkoušky bez učení se správnou odpovědí
        hra = new Hra(saves);
        for (String p : new String[]{"jdi chodba", "jdi vratnice", "jdi zastavka_blanice", "kup jizdenka",
                "jdi zastavka_skola", "jdi vestibul", "jdi studovna", "seber propiska", "jdi vestibul",
                "jdi ucebna", "mluv docent"}) {
            hra.zpracujPrikaz(p);
        }
        vystup = hra.zpracujPrikaz("odpovez b");
        over("vyhazov – správně, ale bez učení", vystup, "„Správně. A proč?“ zeptá se docent.");
        over("vyhazov – konec", vystup, "KONEC HRY – PROHRA: vyletěl jsi od zkoušky.");

        // uložení, načtení a poškozený soubor
        hra = new Hra(saves);
        hra.zpracujPrikaz("otevri skrin");
        hra.zpracujPrikaz("seber isic");
        hra.zpracujPrikaz("jdi chodba");
        over("uloz", hra.zpracujPrikaz("uloz test1"), "Hra uložena do souboru saves/test1.sav.");
        Hra druha = new Hra(saves);
        vystup = druha.zpracujPrikaz("nacti test1");
        over("nacti – prostor", vystup, "Chodba koleje");
        over("nacti – stav", vystup, "[Energie: 11 | Peníze: 120 Kč | Kredity: 174/180 | Batoh (2/5): klice, isic]");
        Files.writeString(saves.resolve("rozbita.sav"), "nesmysl=1\n");
        over("poškozený soubor", druha.zpracujPrikaz("nacti rozbita"),
                "Uloženou hru „rozbita“ nelze načíst: soubor je poškozený.");

        OUT.println(chyb == 0 ? "\nVšechny testy prošly." : "\nChybných testů: " + chyb);
        System.exit(chyb == 0 ? 0 : 1);
    }

    private static void over(String nazev, String vystup, String ocekavany) {
        if (vystup.contains(ocekavany)) {
            OUT.println("OK     " + nazev);
        } else {
            chyb++;
            OUT.println("CHYBA  " + nazev + "\n  čekáno: " + ocekavany + "\n  vráceno: " + vystup);
        }
    }
}
