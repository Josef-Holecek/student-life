package cz.vse.studentlife.uitext;

import cz.vse.studentlife.logika.Hra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;

/** Textové rozhraní: čte příkazy z konzole a vypisuje odpovědi hry. */
public class TextoveRozhrani {

    private final Hra hra;

    public TextoveRozhrani(Hra hra) {
        this.hra = hra;
    }

    public void hraj() {
        Charset kodovani = System.console() != null ? System.console().charset() : Charset.defaultCharset();
        PrintStream out = new PrintStream(System.out, true, kodovani);
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in, kodovani));

        out.println(hra.vratUvitani());
        while (!hra.konecHry()) {
            out.println();
            out.print("> ");
            out.flush();
            String radek;
            try {
                radek = in.readLine();
            } catch (IOException e) {
                break;
            }
            if (radek == null) {
                break;
            }
            out.println(hra.zpracujPrikaz(radek));
        }
    }
}
