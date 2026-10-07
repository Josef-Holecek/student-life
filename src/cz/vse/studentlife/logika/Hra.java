package cz.vse.studentlife.logika;

import java.nio.file.Path;
import java.text.Normalizer;

/**
 * Hlavní třída logiky hry. Přijímá řádek od hráče a vrací text, který se má vypsat.
 */
public class Hra {

    private HerniPlan herniPlan = new HerniPlan();
    private final SeznamPrikazu seznamPrikazu = new SeznamPrikazu();
    private final Path slozkaUlozeni;
    private boolean konecHry;

    /** @param slozkaUlozeni složka „saves“, kam se ukládají hry */
    public Hra(Path slozkaUlozeni) {
        this.slozkaUlozeni = slozkaUlozeni;
        seznamPrikazu.vlozPrikaz(new PrikazJdi(this));
        seznamPrikazu.vlozPrikaz(new PrikazSeber(this));
        seznamPrikazu.vlozPrikaz(new PrikazPoloz(this));
        seznamPrikazu.vlozPrikaz(new PrikazOtevri(this));
        seznamPrikazu.vlozPrikaz(new PrikazKup(this));
        seznamPrikazu.vlozPrikaz(new PrikazSnez(this));
        seznamPrikazu.vlozPrikaz(new PrikazStuduj(this));
        seznamPrikazu.vlozPrikaz(new PrikazMluv(this));
        seznamPrikazu.vlozPrikaz(new PrikazOdpovez(this));
        seznamPrikazu.vlozPrikaz(new PrikazDej(this));
        seznamPrikazu.vlozPrikaz(new PrikazProdej(this));
        seznamPrikazu.vlozPrikaz(new PrikazTancuj(this));
        seznamPrikazu.vlozPrikaz(new PrikazSpi(this));
        seznamPrikazu.vlozPrikaz(new PrikazPomoc(this));
        seznamPrikazu.vlozPrikaz(new PrikazNapoveda(this));
        seznamPrikazu.vlozPrikaz(new PrikazUloz(this));
        seznamPrikazu.vlozPrikaz(new PrikazNacti(this));
        seznamPrikazu.vlozPrikaz(new PrikazKonec(this));
    }

    public String vratUvitani() {
        return "=============================================\n"
                + "                Student life\n"
                + "    textová adventura ze života studenta\n"
                + "=============================================\n"
                + "Budík zvoní v 7:00. Jsi student posledního ročníku VŠE a bydlíš na koleji Blanice.\n"
                + "Na účtu máš 120 Kč a do výplaty stipendia zbývá 24 dní.\n"
                + "K diplomu ti chybí posledních 6 kreditů – dnes skládáš zkoušku z Mikroekonomie.\n"
                + "Tvůj cíl: složit zkoušku, získat diplom, pořádně ho oslavit a dostat se zpátky do vlastní "
                + "postele.\n"
                + "Napiš „pomoc“ pro seznam příkazů.\n"
                + herniPlan.getAktualniProstor().dlouhyPopis() + "\n"
                + herniPlan.stavovyRadek();
    }

    /**
     * Zpracuje jeden řádek vstupu. Pořadí: akce → změna energie → výhra → prohra (energie ≤ 0)
     * → jinak text a stavový řádek.
     */
    public String zpracujPrikaz(String radek) {
        if (konecHry) {
            return "";
        }
        String[] slova = normalizuj(radek);
        IPrikaz prikaz = slova.length == 0 ? null : seznamPrikazu.vratPrikaz(slova[0]);
        if (prikaz == null) {
            return Chyby.E1 + "\n" + herniPlan.stavovyRadek();
        }
        String[] parametry = new String[slova.length - 1];
        System.arraycopy(slova, 1, parametry, 0, parametry.length);

        String text = prikaz.provedPrikaz(parametry);
        if (konecHry) {
            return text;
        }
        if (herniPlan.getEnergie() <= 0) {
            konecHry = true;
            return text + "\n"
                    + "Energie ti klesla na nulu. Oči se ti zavřou přímo tady – "
                    + herniPlan.getAktualniProstor().getNazev() + ". Probudíš se až druhý den v poledne, "
                    + "bez telefonu, bez důstojnosti a s otiskem podlahy na tváři.\n"
                    + "KONEC HRY – PROHRA: došla ti energie.";
        }
        return text + "\n" + herniPlan.stavovyRadek();
    }

    /** Malá písmena, bez diakritiky, bez nadbytečných mezer. */
    static String[] normalizuj(String radek) {
        String bezDiakritiky = Normalizer.normalize(radek == null ? "" : radek, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        String upraveny = bezDiakritiky.toLowerCase().trim();
        return upraveny.isEmpty() ? new String[0] : upraveny.split("\\s+");
    }

    public boolean konecHry() {
        return konecHry;
    }

    /** Ukončí hru (výhra, prohra nebo příkaz konec). */
    public void ukonciHru() {
        konecHry = true;
    }

    public HerniPlan getHerniPlan() {
        return herniPlan;
    }

    void setHerniPlan(HerniPlan herniPlan) {
        this.herniPlan = herniPlan;
    }

    Path getSlozkaUlozeni() {
        return slozkaUlozeni;
    }
}
