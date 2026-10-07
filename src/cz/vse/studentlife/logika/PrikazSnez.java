package cz.vse.studentlife.logika;

/** Příkaz „snez &lt;věc&gt;“ – sní jídlo z batohu a změní energii. */
public class PrikazSnez extends AbstraktniPrikaz {

    public PrikazSnez(Hra hra) {
        super(hra, "snez", "snez <věc>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String co = parametry[0];
        if (!batoh().obsahuje(co)) {
            return Chyby.e10(co);
        }
        int zmena;
        String text;
        switch (co) {
            case "rohlik" -> {
                zmena = 5;
                text = "Sníš rohlík. Suchý, ale tvůj. Energie +5.";
            }
            case "energetak" -> {
                zmena = 6;
                text = "Vypiješ energeťák na ex. Srdce ti buší v rytmu techna. Energie +6.";
            }
            case "obed" -> {
                zmena = 15;
                text = "Sníš oběd. Myslivec by se styděl, ale tvůj žaludek je vděčný. Energie +15.";
            }
            case "kebab" -> {
                zmena = 12;
                text = "Kebab ve tři ráno. Nejlepší jídlo tvého života. Energie +12.";
                plan().setKebabSnezen(true);
            }
            case "horcice" -> {
                zmena = -2;
                text = "Vymáčkneš si hořčici přímo do pusy. Proč? Energie −2.";
            }
            default -> {
                return Chyby.e16(co);
            }
        }
        batoh().odeber(co);
        plan().zmenEnergii(zmena);
        return text;
    }
}
