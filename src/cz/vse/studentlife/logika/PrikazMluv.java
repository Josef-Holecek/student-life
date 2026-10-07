package cz.vse.studentlife.logika;

/** Příkaz „mluv &lt;postava&gt;“ – rozhovory s postavami (vrátná, docent, referentka, Honza, …). */
public class PrikazMluv extends AbstraktniPrikaz {

    static final String OTAZKA_DOCENTA =
            "Docent si tě změří pohledem přes brýle. „Tak, kolego. Mikroekonomie. Jedna otázka, jedna šance.\n"
                    + "Co je mezní užitek?\n"
                    + "   a) Užitek, který mám, když stojím na mezi.\n"
                    + "   b) Přírůstek užitku ze spotřeby další jednotky statku.\n"
                    + "   c) Poslední rohlík v lednici.“\n"
                    + "(Odpověz příkazem: odpovez a, odpovez b nebo odpovez c)";

    public PrikazMluv(Hra hra) {
        super(hra, "mluv", "mluv <postava>", 1, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String kdo = parametry[0];
        Postava postava = tady().getPostava(kdo);
        if (postava == null) {
            return Chyby.e19(kdo);
        }
        return switch (postava) {
            case VRATNA -> mluvVratna();
            case DOCENT -> mluvDocent();
            case REFERENTKA -> plan().jeDiplomVydan()
                    ? "Referentka: „Diplom máte, tak už jděte. A nepomačkat!“"
                    : "Referentka: „Máte potvrzení o zkoušce? Ne? Tak proč mi tu stojíte? Další!“";
            case HONZA -> mluvHonza();
            case VYHAZOVAC -> "Vyhazovač: „Náramek máš? Tak dovnitř. Nemáš? Tak domů.“";
            case KEBABAR -> plan().jeNoc()
                    ? "Kebabář: „Kebab za 129, nebo poukaz. Skripta beru za třicet, hoří krásně.“"
                    : "Kebabář: „Kebab za 129. A jestli máš nějaký starý skripta, přijď v noci – vykupuju je "
                    + "na podpal.“";
        };
    }

    private String mluvVratna() {
        if (plan().jeNahradniKlicVydan()) {
            return "Vrátná: „Klíč ráno vrátit, ISIC dostanete zpátky. A potichu, lidi spí.“";
        }
        if (plan().jeNoc() && !batoh().obsahuje("klice")) {
            return "Vrátná: „Ve tři ráno? Bez klíčů? Já to věděla. Dejte mi ISIC jako zástavu a dostanete "
                    + "náhradní.“";
        }
        return "Vrátná: „Dobré ráno. Zase na poslední chvíli, co? A kdybyste zase ztratil klíče, náhradní vám "
                + "dám jen za zástavu – nejlíp ISIC. A ne ve tři ráno, prosím.“";
    }

    private String mluvDocent() {
        HerniPlan plan = plan();
        if (plan.jeZkouskaSlozena()) {
            return "Docent: „Už jste to složil. Nepokoušejte štěstí.“";
        }
        if (plan.zkouskaProbiha()) {
            return OTAZKA_DOCENTA;
        }
        if (batoh().obsahuje("skripta")) {
            return "Docent: „Skripta nechte za dveřmi, kolego. Tohle není zkouška s taháky.“";
        }
        if (!batoh().obsahuje("propiska")) {
            return "Docent: „A čím budete psát? Prstem? Bez propisky nepíšete.“";
        }
        plan.setZkouskaProbiha(true);
        return OTAZKA_DOCENTA;
    }

    private String mluvHonza() {
        HerniPlan plan = plan();
        if (plan.jeNaramekPredan()) {
            return "Honza: „Běž do Sklepa, já jen dopiju a dorazím.“ (Nedorazí.)";
        }
        if (!batoh().obsahuje("diplom")) {
            return "Honza: „Ty ještě nemáš diplom? Tak co tu děláš? Mazej na zkoušku!“";
        }
        if (batoh().jePlny()) {
            return "Honza: „Mám pro tebe náramek do Sklepa, ale nemáš ho kam dát. Udělej si v batohu místo.“";
        }
        String uvod = "Honza: „Čau! Ty máš diplom?! To se musí oslavit! Jo a… ty mi pořád dlužíš stovku "
                + "z minulýho tejdne.“\n";
        String platba;
        if (plan.getPenize() > 0) {
            platba = "Honza si vezme všechny tvoje peníze (" + plan.getPenize() + " Kč). „No, je to začátek. "
                    + "Tady máš VIP náramek do Sklepa, vyhazovač je můj bratranec.“\n";
            plan.zmenPenize(-plan.getPenize());
        } else {
            platba = "Honza zjistí, že nemáš ani korunu, a mávne rukou. „Tady máš VIP náramek do Sklepa, "
                    + "vyhazovač je můj bratranec.“\n";
        }
        batoh().vloz(Vec.vytvor("naramek"));
        plan.setNaramekPredan(true);
        return uvod + platba + "Do batohu přibylo: naramek.";
    }
}
