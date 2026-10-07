package cz.vse.studentlife.logika;

/** Příkaz „seber &lt;věc&gt;“ – z prostoru nebo z otevřeného kontejneru v prostoru. */
public class PrikazSeber extends AbstraktniPrikaz {

    public PrikazSeber(Hra hra) {
        super(hra, "seber", "seber <věc>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String co = parametry[0];
        Prostor prostor = tady();
        Vec vec = prostor.getVec(co);
        if (vec != null) {
            if (!vec.jePrenositelna()) {
                return Chyby.e8(co);
            }
            if (batoh().jePlny()) {
                return Chyby.E9;
            }
            batoh().vloz(prostor.odeberVec(co));
            return "Do batohu sis dal: " + co + ".";
        }
        Vec kontejner = prostor.najdiKontejnerS(co);
        if (kontejner == null) {
            return Chyby.e7(co);
        }
        if (batoh().jePlny()) {
            return Chyby.E9;
        }
        batoh().vloz(kontejner.vyjmiZObsahu(co));
        return "Do batohu sis dal: " + co + ".";
    }
}
