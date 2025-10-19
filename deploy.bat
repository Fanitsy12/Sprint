@echo off
REM ===========================================
REM   DEPLOIEMENT SIMPLIFIE DU SPRINT 1
REM ===========================================

REM === CONFIGURATION ===

set TOMCAT_PATH=C:\xampp\tomcat
set WAR_NAME=sprint1
set PROJECT_DIR=%~dp0
set TARGET_WAR=%PROJECT_DIR%test\target\%WAR_NAME%.war

echo.
echo ==============================
echo 🚀 DEPLOIEMENT DE %WAR_NAME%
echo ==============================

REM === Étape 1: Construction du Framework ===
cd /d "%PROJECT_DIR%framework"
call mvn clean install

REM === Étape 2: Construction du Projet Test ===
cd /d "%PROJECT_DIR%test"
call mvn clean package

REM === Étape 3: Copie du WAR dans Tomcat ===
echo.
echo 📦 Copie du WAR vers Tomcat...
if exist "%TOMCAT_PATH%\webapps\%WAR_NAME%.war" del "%TOMCAT_PATH%\webapps\%WAR_NAME%.war"
if exist "%TOMCAT_PATH%\webapps\%WAR_NAME%" rmdir /S /Q "%TOMCAT_PATH%\webapps\%WAR_NAME%"
copy "%TARGET_WAR%" "%TOMCAT_PATH%\webapps\" >nul

REM === Étape 4: Redémarrage de Tomcat ===
echo.
echo 🔄 Redémarrage de Tomcat...
cd /d "%TOMCAT_PATH%\bin"
call shutdown.bat >nul 2>&1
timeout /t 3 /nobreak >nul
start "" "startup.bat"



REM === Étape 5: Ouverture du navigateur ===
echo.
echo 🌐 Ouverture dans le navigateur...
timeout /t 5 /nobreak >nul
start "" "http://localhost:8080/%WAR_NAME%/"

echo.
echo ✅ DEPLOIEMENT TERMINE !
pause


