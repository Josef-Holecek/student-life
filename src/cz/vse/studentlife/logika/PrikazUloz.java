package cz.vse.studentlife.logika;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/** Příkaz „uloz [jmeno]“ – uloží celý stav hry do saves/&lt;jmeno&gt;.sav. */
public class PrikazUloz extends AbstraktniPrikaz {

    static final String VYCHOZI_JMENO = "hra";
    private static final String PLATNE_JMENO = "[A-Za-z0-9_]+";

    public PrikazUloz(Hra hra) {
        super(hra, "uloz", "uloz [jmeno]", 0, 1);
    }

    static boolean jePlatneJmeno(String jmeno) {
        return jmeno.matches(PLATNE_JMENO);
    }

    @Override
    protected String proved(String[] parametry) {
        String jmeno = parametry.length == 0 ? VYCHOZI_JMENO : parametry[0];
        if (!jePlatneJmeno(jmeno)) {
            return Chyby.E26;
        }
        Path slozka = hra.getSlozkaUlozeni();
        try {
            Files.createDirectories(slozka);
            try (Writer w = Files.newBufferedWriter(slozka.resolve(jmeno + ".sav"), StandardCharsets.UTF_8)) {
                plan().ulozStav().store(w, "Student life - ulozena hra");
            }
        } catch (IOException e) {
            return Chyby.e27(jmeno);
        }
        return "Hra uložena do souboru saves/" + jmeno + ".sav.";
    }
}
