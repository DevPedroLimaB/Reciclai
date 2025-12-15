@echo off
chcp 65001 >nul
echo ==========================================
echo   📱 COMPILAR ANDROID APENAS
echo ==========================================
echo.

echo [1/2] Parando daemons do Gradle...
call gradlew.bat --stop

echo.
echo [2/2] Compilando Android App...
call gradlew.bat :androidApp:clean :androidApp:assembleDebug --no-daemon

if errorlevel 1 (
    echo ❌ Erro ao compilar Android!
    pause
    exit /b 1
)

echo.
echo ==========================================
echo ✅ ANDROID COMPILADO COM SUCESSO!
echo.
echo APK gerado em:
echo   androidApp/build/outputs/apk/debug/androidApp-debug.apk
echo ==========================================
pause

