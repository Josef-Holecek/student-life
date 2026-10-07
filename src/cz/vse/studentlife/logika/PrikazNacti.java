package cz.vse.studentlife.logika;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Příkaz „nacti [jmeno]“ – načte stav ze saves/&lt;jmeno&gt;.sav a vypíše prostor. */
public class PrikazNacti extends AbstraktniPrikaz {

    public PrikazNacti(Hra hra) {
        super(hra, "nacti", "nacti [jmeno]", 0, 1);
    }

    @Override
    protected String proved(String[] parametry) {
        String jmeno = parametry.length == 0 ? PrikazUloz.VYCHOZI_JMENO : parametry[0];
        if (!PrikazUloz.jePlatneJmeno(jmeno)) {
            return Chyby.E26;
        }
        Path soubor = hra.getSlozkaUlozeni().resolve(jmeno + ".sav");
        if (!Files.isRegularFile(soubor)) {
            return Chyby.e28(jmeno);
        }
        HerniPlan novy;
        try (Reader r = Files.newBufferedReader(soubor, StandardCharsets.UTF_8)) {
            Properties p = new Properties();
            p.load(r);
            novy = HerniPlan.zeStavu(p);
        } catch (IOException | RuntimeException e) {
            return Chyby.e29(jmeno);
        }
        hra.setHerniPlan(novy);
        return "Hra načtena ze souboru saves/" + jmeno + ".sav.\n" + novy.getAktualniProstor().dlouhyPopis();
    }
}
