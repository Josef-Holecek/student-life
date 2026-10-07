package cz.vse.studentlife.logika;

/** Příkaz „dej &lt;věc&gt; &lt;postava&gt;“ – platné jsou jen potvrzeni → referentka a isic → vratna. */
public class PrikazDej extends AbstraktniPrikaz {

    public PrikazDej(Hra hra) {
        super(hra, "dej", "dej <věc> <postava>", 2, 2);
    }

    @Override
    protected String proved(String[] parametry) {
        String co = parametry[0];
        String komu = parametry[1];
        if (!batoh().obsahuje(co)) {
            return Chyby.e10(co);
        }
        Postava postava = tady().getPostava(komu);
        if (postava == null) {
            return Chyby.e19(komu);
        }
        if (postava == Postava.DOCENT) {
            return "Docent: „Úplatky neberu.“";
        }
        if (postava == Postava.REFERENTKA && co.equals("potvrzeni")) {
            batoh().odeber("potvrzeni");
            batoh().vloz(Vec.vytvor("diplom"));
            plan().setDiplomVydan(true);
            return "Referentka potvrzení třikrát orazítkuje, dvakrát podepíše a jednou vzdychne. „180 kreditů. "
                    + "Tady je váš diplom. Nepomačkat!“\n"
                    + "Do batohu přibylo: diplom.";
        }
        if (postava == Postava.VRATNA && co.equals("isic")) {
            if (batoh().obsahuje("klice")) {
                return "Vrátná: „A proč? Vždyť klíče máte.“";
            }
            if (batoh().jePlny()) {
                return "Vrátná: „Náhradní klíč bych vám dala, ale nemáte ho kam dát.“";
            }
            batoh().odeber("isic");
            batoh().vloz(Vec.vytvor("nahradni_klic"));
            plan().setNahradniKlicVydan(true);
            return "Vrátná si ISIC prohlédne, porovná fotku s tvým obličejem a povzdechne si. „No… dejme tomu, "
                    + "že jste to vy.“ Podá ti náhradní klíč.\n"
                    + "Do batohu přibylo: nahradni_klic.";
        }
        return Chyby.e22(postava.getJmeno(), co);
    }
}
