package cz.vse.studentlife.main;

import cz.vse.studentlife.logika.Hra;
import cz.vse.studentlife.uitext.TextoveRozhrani;

import java.nio.file.Files;
import java.nio.file.Path;

/** Spouštěcí třída hry Student life. */
public final class Start {

    private Start() {
    }

    public static void main(String[] args) {
        new TextoveRozhrani(new Hra(slozkaUlozeni())).hraj();
    }

    /** Složka „saves“ vedle souboru hry (jaru); při spuštění z IDE v pracovním adresáři. */
    private static Path slozkaUlozeni() {
        try {
            Path umisteni = Path.of(Start.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            if (Files.isRegularFile(umisteni)) {
                return umisteni.getParent().resolve("saves");
            }
        } catch (Exception e) {
            // nepovedlo se zjistit umístění jaru – použijeme pracovní adresář
        }
        return Path.of("saves");
    }
}
