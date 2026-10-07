package cz.vse.studentlife.logika;

/** Příkaz „konec“ – ukončí hru bez výhry i prohry. */
public class PrikazKonec extends AbstraktniPrikaz {

    public PrikazKonec(Hra hra) {
        super(hra, "konec", "konec", 0, 0);
    }

    @Override
    protected String proved(String[] parametry) {
        hra.ukonciHru();
        return "Hra ukončena. Diplom počká – nebo taky ne.";
    }
}
