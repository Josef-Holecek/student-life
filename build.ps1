# Prelozi hru, spusti testy a vytvori spustitelny student-life.jar (staci JDK 17+).
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot

Remove-Item -Recurse -Force out -ErrorAction SilentlyContinue
$zdroje = Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName }
javac --release 17 -encoding UTF-8 -d out/classes $zdroje
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

$testy = Get-ChildItem -Recurse -Filter *.java test | ForEach-Object { $_.FullName }
javac --release 17 -encoding UTF-8 -cp out/classes -d out/test $testy
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
java -cp "out/classes;out/test" cz.vse.studentlife.HraTest
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

jar --create --file student-life.jar --main-class cz.vse.studentlife.main.Start -C out/classes .
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }
Write-Host "Hotovo: student-life.jar  (spusteni: java -jar student-life.jar)"
