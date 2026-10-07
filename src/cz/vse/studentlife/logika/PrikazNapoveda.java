package cz.vse.studentlife.logika;

/** Příkaz „napoveda“ – vypíše první radu z tabulky 4.6, jejíž podmínka platí. */
public class PrikazNapoveda extends AbstraktniPrikaz {

    public PrikazNapoveda(Hra hra) {
        super(hra, "napoveda", "napoveda", 0, 0);
    }

    @Override
    protected String proved(String[] parametry) {
        HerniPlan plan = plan();
        Batoh batoh = batoh();
        if (plan.jeNoc() && !plan.jeKebabSnezen()) {
            return "Je noc, nemáš klíče ani peníze a každý krok bolí dvojnásob. Kebabář v noci vykupuje zvláštní "
                    + "věci – a s poukazem tě nakrmí.";
        }
        if (plan.jeNoc() && !batoh.obsahuje("nahradni_klic")) {
            return "Domů jedeš jedině s jízdenkou. A vrátná ti náhradní klíč dá jen za zástavu.";
        }
        if (plan.jeNoc()) {
            return "Už jen dojít do pokoje a zalehnout.";
        }
        if (batoh.obsahuje("diplom") && !batoh.obsahuje("naramek")) {
            return "Diplom bez oslavy neplatí. Kamarád Honza sedí v hospodě.";
        }
        if (batoh.obsahuje("naramek")) {
            return "S náramkem tě do Sklepa pustí. A v klubu se tancuje.";
        }
        if (plan.jeZkouskaSlozena()) {
            return "Potvrzení o zkoušce odnes na studijní oddělení.";
        }
        if (plan.jeNaucen()) {
            return "Jsi připravený. Do učebny si vezmi propisku – skripta ale nech venku.";
        }
        Vec skrin = plan.getProstor("pokoj").getVec("skrin");
        if (skrin != null && !skrin.jeOtevreny()) {
            return "Než vyrazíš, mrkni do skříně. Bez skript a ISICu dnes nic nedáš.";
        }
        return "Učit se dá jen v klidu studovny a se skripty v batohu. A nezapomeň jíst – hladový mozek nic "
                + "nepobere.";
    }
}
