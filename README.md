# Student life

Textová adventura podle zadání „kolej lajf“ (Java 17+, bez knihoven).

## Spuštění

```
java -jar student-life.jar
```

Uložené hry se ukládají do složky `saves/` vedle jaru.

## 2D verze v prohlížeči

`web/index.html` je 2D pixelová verze stejné hry – stačí ji otevřít v prohlížeči
(dvojklik na soubor). Chodí se šipkami nebo WASD, akce klávesou E, věci v batohu
klávesami 1–5, nápověda H. Na telefonu jsou pod hrou dotyková tlačítka.

## Překlad

```
powershell -ExecutionPolicy Bypass -File build.ps1
```

Skript přeloží `src/`, spustí testy z `test/` a vytvoří `student-life.jar`.
V IntelliJ stačí označit `src` jako Sources Root, `test` jako Test Sources Root
a spustit `cz.vse.studentlife.main.Start`.

## Struktura

- `logika/` – `Hra`, `HerniPlan` (svět + stav + ukládání), `Prostor`, `Vec`, `Batoh`, `Postava`,
  `Chyby` (hlášky E1–E30) a jedna třída na každý příkaz (`PrikazJdi`, `PrikazKup`, …).
- `uitext/TextoveRozhrani` – čtení příkazů z konzole.
- `main/Start` – spouštěcí třída.
- `test/` – `ScenarePrepis` přehraje vítězný a prohrávající scénář (výstup odpovídá části 4.1
  zadání znak po znaku), `HraTest` ověřuje prohry, ukládání a zpracování vstupu.
