@echo off
chcp 65001 >nul
echo ==========================================
echo   🌐 COMPILAR FRONTEND WEB APENAS
echo ==========================================
echo.

echo [1/2] Parando daemons do Gradle...
call gradlew.bat --stop

echo.
echo [2/2] Compilando webApp...
call gradlew.bat :webApp:clean :webApp:build --no-daemon

if errorlevel 1 (
    echo ❌ Erro ao compilar webApp!
    pause
    exit /b 1
)

echo.
echo ==========================================
echo ✅ FRONTEND WEB COMPILADO COM SUCESSO!
echo.
echo Arquivos em: webApp/build/dist/js/productionExecutable/
echo ==========================================
pause

