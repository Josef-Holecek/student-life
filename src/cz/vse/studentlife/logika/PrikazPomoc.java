package cz.vse.studentlife.logika;

/** Příkaz „pomoc“ – seznam příkazů podle části 4.5 zadání. */
public class PrikazPomoc extends AbstraktniPrikaz {

    public PrikazPomoc(Hra hra) {
        super(hra, "pomoc", "pomoc", 0, 0);
    }

    @Override
    protected String proved(String[] parametry) {
        return "Příkazy:\n"
                + radek("jdi <prostor>", "přesun do sousedního prostoru")
                + radek("seber <věc>", "vezme věc do batohu (max. 5 věcí)")
                + radek("poloz <věc>", "položí věc z batohu")
                + radek("otevri <věc>", "otevře skříň, lednici a podobně")
                + radek("kup <zboží>", "koupí zboží v obchodě nebo z automatu")
                + radek("snez <věc>", "sní jídlo z batohu")
                + radek("studuj <věc>", "učení (jen tam, kde je klid)")
                + radek("mluv <postava>", "promluví s postavou")
                + radek("odpovez <a|b|c>", "odpověď na otázku")
                + radek("dej <věc> <postava>", "dá věc postavě")
                + radek("prodej <věc>", "prodá věc tomu, kdo vykupuje")
                + radek("tancuj", "tanec (jen tam, kde hraje hudba)")
                + radek("spi", "spánek (jen ve vlastní posteli)")
                + radek("napoveda", "rada, co dělat dál")
                + radek("pomoc", "tento seznam")
                + radek("uloz [jmeno]", "uloží hru")
                + radek("nacti [jmeno]", "načte hru")
                + radek("konec", "ukončí hru")
                + "Pohyb stojí energii, jídlo ji doplňuje. Když energie klesne na nulu, usneš.";
    }

    private static String radek(String prikaz, String popis) {
        return String.format("   %-22s%s%n", prikaz, popis).replace(System.lineSeparator(), "\n");
    }
}
