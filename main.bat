
@echo off
REM ===========================================
REM   COMPILATION ET EXECUTION DE MAIN.JAVA
REM ===========================================

REM === CONFIGURATION ===
REM fichier JAR par défaut (version attendue)
set FRAMEWORK_JAR=framework\target\mvc-framework-1.0.0-SNAPSHOT.jar
set TEST_SRC=test\src\main\java
set TEST_CLASSES=test\target\classes

REM === Vérifier l'existence du JAR ; sinon rechercher une version disponible ===
if exist "%FRAMEWORK_JAR%" (
    echo Utilisation de : %FRAMEWORK_JAR%
) else (
    echo Fichier attendu introuvable : %FRAMEWORK_JAR%
    echo Recherche d'un JAR disponible dans framework\target...
    set "FOUND_JAR="
    for /f "delims=" %%F in ('dir /b /a-d "framework\target\mvc-framework-*.jar" 2^>nul') do (
        set "FOUND_JAR=framework\target\%%F"
        goto :use_found_jar
    )
    :use_found_jar
    if defined FOUND_JAR (
        echo JAR trouve : %FOUND_JAR% - utilisation de cette version.
        set "FRAMEWORK_JAR=%FOUND_JAR%"
    ) else (
        echo Aucun mvc-framework-*.jar trouve dans framework\target.
        echo Veuillez builder le module framework, par exemple :
        echo    mvn -f framework/pom.xml clean package
        pause
        exit /b 1
    )
)

REM === Étape 1 : Compilation du Main ===
echo.
echo 🔨 Compilation de Main.java...
javac -cp "%FRAMEWORK_JAR%;%TEST_CLASSES%" "%TEST_SRC%\Main.java" -d "%TEST_CLASSES%"

if ERRORLEVEL 1 (
    echo ❌ Erreur lors de la compilation !
    pause
    exit /b 1
)

REM === Étape 2 : Execution de Main ===
echo.
echo 🚀 Execution de Main...
java -cp "%FRAMEWORK_JAR%;%TEST_CLASSES%" Main

echo.
echo ✅ Execution terminee !
pause