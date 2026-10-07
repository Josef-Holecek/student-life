package cz.vse.studentlife;

import cz.vse.studentlife.logika.Hra;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Přehraje vítězný a prohrávající scénář z části 3 zadání a vypíše přepis ve stejném tvaru
 * jako část 4.1 („--- N: příkaz ---“), aby šel porovnat se zadáním.
 */
public final class ScenarePrepis {

    static final String[] VITEZNY = {
            "otevri skrin", "seber skripta", "seber isic", "otevri lednice", "jdi chodba", "jdi vratnice",
            "mluv vratna", "jdi zastavka_blanice", "jdi vecerka", "kup rohlik", "snez rohlik",
            "jdi zastavka_blanice", "kup jizdenka", "jdi zastavka_skola", "jdi vestibul", "jdi menza",
            "kup obed", "snez obed", "jdi vestibul", "jdi studovna", "seber propiska", "studuj skripta",
            "jdi vestibul", "poloz skripta", "jdi ucebna", "mluv docent", "odpovez b", "jdi vestibul",
            "poloz propiska", "seber skripta", "jdi studijni", "dej potvrzeni referentka", "jdi vestibul",
            "jdi zastavka_skola", "jdi ulice", "jdi hospoda", "mluv honza", "jdi ulice", "jdi klub", "tancuj",
            "jdi ulice", "jdi kebab", "prodej skripta", "kup kebab", "snez kebab", "jdi ulice",
            "jdi zastavka_skola", "kup jizdenka", "jdi zastavka_blanice", "jdi vratnice", "dej isic vratna",
            "jdi chodba", "jdi pokoj", "spi"};

    static final String[] PROHRAVAJICI = {
            "jdi chodba", "jdi vratnice", "jdi zastavka_blanice", "jdi zastavka_skola"};

    private ScenarePrepis() {
    }

    public static void main(String[] args) throws Exception {
        Path saves = Files.createTempDirectory("studentlife-test");
        StringBuilder sb = new StringBuilder();

        Hra hra = new Hra(saves);
        sb.append("--- 0: spuštění hry ---\n").append(hra.vratUvitani()).append('\n');
        for (int i = 0; i < VITEZNY.length; i++) {
            sb.append("--- ").append(i + 1).append(": ").append(VITEZNY[i]).append(" ---\n");
            sb.append(hra.zpracujPrikaz(VITEZNY[i])).append('\n');
        }
        sb.append("=== PROHRÁVAJÍCÍ SCÉNÁŘ ===\n");
        hra = new Hra(saves);
        for (int i = 0; i < PROHRAVAJICI.length; i++) {
            sb.append("--- P").append(i + 1).append(": ").append(PROHRAVAJICI[i]).append(" ---\n");
            sb.append(hra.zpracujPrikaz(PROHRAVAJICI[i])).append('\n');
        }

        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        if (args.length > 0) {
            Files.writeString(Path.of(args[0]), sb.toString(), StandardCharsets.UTF_8);
        } else {
            out.print(sb);
        }
    }
}
