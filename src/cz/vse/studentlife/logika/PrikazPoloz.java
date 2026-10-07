package cz.vse.studentlife.logika;

/** Příkaz „poloz &lt;věc&gt;“ – položí věc z batohu na konec seznamu věcí v prostoru. */
public class PrikazPoloz extends AbstraktniPrikaz {

    public PrikazPoloz(Hra hra) {
        super(hra, "poloz", "poloz <věc>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String co = parametry[0];
        Vec vec = batoh().odeber(co);
        if (vec == null) {
            return Chyby.e10(co);
        }
        tady().pridejVec(vec);
        return "Položil jsi: " + co + ".";
    }
}
