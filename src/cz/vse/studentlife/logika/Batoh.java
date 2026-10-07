package cz.vse.studentlife.logika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Batoh hráče – nejvýše 5 věcí, vypisují se v pořadí, v jakém přibyly. */
public class Batoh {

    public static final int KAPACITA = 5;

    private final List<Vec> veci = new ArrayList<>();

    public boolean jePlny() {
        return veci.size() >= KAPACITA;
    }

    /** Vloží věc, pokud je místo. */
    public boolean vloz(Vec vec) {
        if (jePlny()) {
            return false;
        }
        veci.add(vec);
        return true;
    }

    public boolean obsahuje(String id) {
        return veci.stream().anyMatch(v -> v.getId().equals(id));
    }

    /** Odebere první věc s daným identifikátorem, nebo vrátí null. */
    public Vec odeber(String id) {
        for (int i = 0; i < veci.size(); i++) {
            if (veci.get(i).getId().equals(id)) {
                return veci.remove(i);
            }
        }
        return null;
    }

    public List<Vec> getVeci() {
        return Collections.unmodifiableList(veci);
    }

    public int pocet() {
        return veci.size();
    }

    /** Např. „Batoh (1/5): klice“ nebo „Batoh (0/5): prázdný“. */
    public String popis() {
        String obsah = veci.isEmpty() ? "prázdný"
                : veci.stream().map(Vec::getId).reduce((a, b) -> a + ", " + b).orElse("");
        return "Batoh (" + veci.size() + "/" + KAPACITA + "): " + obsah;
    }
}
