package cz.vse.studentlife.logika;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Prostor (místnost) herního světa. */
public class Prostor {

    /** Přechod do sousedního prostoru; tramvajový přechod se platí jízdenkou. */
    public record Vychod(Prostor cil, boolean tramvaj) {
    }

    private final String id;
    private final String nazev;
    private final String popis;
    private final List<Vychod> vychody = new ArrayList<>();
    private final List<Vec> veci = new ArrayList<>();
    private final List<Postava> postavy = new ArrayList<>();
    private final Map<String, Integer> nabidka = new LinkedHashMap<>();
    private String popisNabidky;

    public Prostor(String id, String nazev, String popis) {
        this.id = id;
        this.nazev = nazev;
        this.popis = popis;
    }

    public String getId() {
        return id;
    }

    public String getNazev() {
        return nazev;
    }

    // ---------- východy ----------

    public void pridejVychod(Prostor cil, boolean tramvaj) {
        vychody.add(new Vychod(cil, tramvaj));
    }

    public Vychod getVychod(String idCile) {
        return vychody.stream().filter(v -> v.cil().getId().equals(idCile)).findFirst().orElse(null);
    }

    // ---------- věci ----------

    public void pridejVec(Vec vec) {
        veci.add(vec);
    }

    public Vec getVec(String idVeci) {
        return veci.stream().filter(v -> v.getId().equals(idVeci)).findFirst().orElse(null);
    }

    public Vec odeberVec(String idVeci) {
        for (int i = 0; i < veci.size(); i++) {
            if (veci.get(i).getId().equals(idVeci)) {
                return veci.remove(i);
            }
        }
        return null;
    }

    public List<Vec> getVeci() {
        return Collections.unmodifiableList(veci);
    }

    /** Najde otevřený kontejner v prostoru, který obsahuje danou věc. */
    public Vec najdiKontejnerS(String idVeci) {
        for (Vec v : veci) {
            if (v.jeKontejner() && v.jeOtevreny()
                    && v.getObsah().stream().anyMatch(o -> o.getId().equals(idVeci))) {
                return v;
            }
        }
        return null;
    }

    // ---------- postavy ----------

    public void pridejPostavu(Postava postava) {
        postavy.add(postava);
    }

    public boolean jeTuPostava(String idPostavy) {
        return postavy.stream().anyMatch(p -> p.getId().equals(idPostavy));
    }

    public Postava getPostava(String idPostavy) {
        return postavy.stream().filter(p -> p.getId().equals(idPostavy)).findFirst().orElse(null);
    }

    // ---------- obchod ----------

    public void pridejZbozi(String idZbozi, int cena) {
        nabidka.put(idZbozi, cena);
    }

    public void setPopisNabidky(String popisNabidky) {
        this.popisNabidky = popisNabidky;
    }

    public boolean jeObchod() {
        return !nabidka.isEmpty();
    }

    public boolean prodava(String idZbozi) {
        return nabidka.containsKey(idZbozi);
    }

    public int getCena(String idZbozi) {
        return nabidka.get(idZbozi);
    }

    // ---------- výpis ----------

    /** Výpis prostoru podle části 4.2 zadání. */
    public String dlouhyPopis() {
        StringBuilder sb = new StringBuilder();
        sb.append(nazev).append('\n');
        sb.append(popis).append('\n');
        sb.append("Věci: ").append(veci.isEmpty() ? "nic"
                : veci.stream().map(Vec::popisVeVypisu).collect(Collectors.joining(", "))).append('\n');
        if (!postavy.isEmpty()) {
            sb.append("Postavy: ")
                    .append(postavy.stream().map(Postava::getId).collect(Collectors.joining(", ")))
                    .append('\n');
        }
        if (jeObchod()) {
            String text = popisNabidky != null ? popisNabidky
                    : nabidka.entrySet().stream()
                    .map(e -> e.getKey() + " (" + e.getValue() + " Kč)")
                    .collect(Collectors.joining(", "));
            sb.append("Nabídka: ").append(text).append('\n');
        }
        sb.append("Východy: ").append(vychody.stream()
                .map(v -> v.cil().getId() + (v.tramvaj() ? " (tramvají)" : ""))
                .collect(Collectors.joining(", ")));
        return sb.toString();
    }
}
