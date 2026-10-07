package cz.vse.studentlife.logika;

/** Příkaz „spi“ – v pokoji s oslaveným diplomem znamená výhru. */
public class PrikazSpi extends AbstraktniPrikaz {

    public PrikazSpi(Hra hra) {
        super(hra, "spi", "spi", 0, 0);
    }

    @Override
    protected String proved(String[] parametry) {
        HerniPlan plan = plan();
        if (!tady().getId().equals("pokoj")) {
            return Chyby.E25;
        }
        if (!batoh().obsahuje("diplom")) {
            return "Lehneš si, ale nemůžeš zavřít oči. Bez diplomu dnes neusneš.";
        }
        if (!plan.jeOslaveno()) {
            return "Diplom bez oslavy? Takhle to na VŠE nechodí. Nejdřív to pořádně oslav.";
        }
        hra.ukonciHru();
        return "Doplazíš se k posteli, diplom opatrně položíš na noční stolek a zhroutíš se do peřin. Je 4:12.\n"
                + "Máš 180 kreditů, " + plan.getPenize() + " Kč na účtu, žádné klíče a nohy jako z olova. "
                + "Ale hlavně máš diplom.\n"
                + "Zítra tě čeká dospělost. Ale to až zítra.\n"
                + "GRATULUJEME! VYHRÁL JSI: jsi bakalář a ležíš ve vlastní posteli.";
    }
}
