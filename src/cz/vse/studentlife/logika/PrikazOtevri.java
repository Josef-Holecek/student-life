package cz.vse.studentlife.logika;

/** Příkaz „otevri &lt;věc&gt;“ – otevře skříň nebo lednici a vypíše obsah. */
public class PrikazOtevri extends AbstraktniPrikaz {

    public PrikazOtevri(Hra hra) {
        super(hra, "otevri", "otevri <věc>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String co = parametry[0];
        Vec vec = tady().getVec(co);
        if (vec == null) {
            return Chyby.e7(co);
        }
        if (!vec.jeKontejner()) {
            return Chyby.e11(co);
        }
        if (vec.jeOtevreny()) {
            return Chyby.e12(co);
        }
        vec.otevri();
        String obsah = vec.getObsah().isEmpty() ? "nic" : vec.vypisObsahu();
        if (co.equals("lednice")) {
            return "Otevřeš lednici. Studené světlo osvítí jedinou věc: " + obsah + ". Klasika.";
        }
        return "Otevřeš skříň. Mezi zmuchlaným oblečením a ponožkou bez páru najdeš: " + obsah + ".";
    }
}
