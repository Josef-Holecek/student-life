package cz.vse.studentlife.logika;

import java.util.LinkedHashMap;
import java.util.Map;

/** Seznam příkazů, které hra zná. */
public class SeznamPrikazu {

    private final Map<String, IPrikaz> prikazy = new LinkedHashMap<>();

    public void vlozPrikaz(IPrikaz prikaz) {
        prikazy.put(prikaz.getNazev(), prikaz);
    }

    public IPrikaz vratPrikaz(String nazev) {
        return prikazy.get(nazev);
    }
}
