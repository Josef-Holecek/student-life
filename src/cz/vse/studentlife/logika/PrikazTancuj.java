package cz.vse.studentlife.logika;

/** Příkaz „tancuj“ – v klubu poprvé: noc, oslava, ztráta klíčů a poukaz na kebab. */
public class PrikazTancuj extends AbstraktniPrikaz {

    public PrikazTancuj(Hra hra) {
        super(hra, "tancuj", "tancuj", 0, 0);
    }

    @Override
    protected String proved(String[] parametry) {
        HerniPlan plan = plan();
        Prostor prostor = tady();
        if (!prostor.getId().equals("klub")) {
            return Chyby.E24;
        }
        if (plan.jeOslaveno()) {
            return "Nohy už neposlouchají. Na další tanec nemáš sílu ani náladu.";
        }

        plan.zmenEnergii(-3);
        plan.setNoc(true);
        plan.setOslaveno(true);

        // klíče navždy zmizí z batohu i ze světa
        boolean kliceVBatohu = batoh().odeber("klice") != null;
        for (Prostor p : plan.getProstory()) {
            while (p.odeberVec("klice") != null) {
                // odstraňujeme všechny výskyty
            }
        }

        String poukaz;
        if (batoh().vloz(Vec.vytvor("poukaz"))) {
            poukaz = "Do batohu přibylo: poukaz.";
        } else {
            prostor.pridejVec(Vec.vytvor("poukaz"));
            poukaz = "Poukaz se ti do batohu nevešel a spadl na zem.";
        }

        return "Vrhneš se na parket a tančíš jako nikdy předtím – a hlavně jako nikdo předtím. Čas letí "
                + "a najednou jsou 3:00 ráno.\n"
                + "DJ tě vyhlásí vítězem soutěže „Nejodvážnější tanečník večera“ a předá ti poukaz na kebab "
                + "zdarma.\n"
                + "Při jedné otočce ti ale z kapsy vylétly klíče od pokoje a zmizely v davu. Navždy.\n"
                + "Diplom je oslavený. Únava na tebe padá: od teď tě každý krok stojí 2 energie. Energie −3.\n"
                + poukaz + (kliceVBatohu ? " Z batohu zmizelo: klice." : "");
    }
}
