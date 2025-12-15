@echo off
chcp 65001 >nul
echo ==========================================
echo   RECICLAI - COMPILAR E INICIAR
echo ==========================================
echo.

cd /d "%~dp0"

echo [1/5] Limpando processos Java...
taskkill /F /IM java.exe 2>nul
taskkill /F /IM javaw.exe 2>nul
timeout /t 2 /nobreak >nul
echo ✅ OK

echo.
echo [2/5] Limpando build anterior...
call gradlew.bat clean --no-daemon --quiet
echo ✅ OK

echo.
echo [3/5] Compilando SHARED...
call gradlew.bat :shared:build --no-daemon -x test --quiet
if %errorlevel% neq 0 (
    echo ❌ ERRO ao compilar SHARED
    pause
    exit /b 1
)
echo ✅ SHARED OK

echo.
echo [4/5] Compilando WEBAPP...
call gradlew.bat :webApp:build --no-daemon --quiet
if %errorlevel% neq 0 (
    echo ❌ ERRO ao compilar WEBAPP
    pause
    exit /b 1
)
echo ✅ WEBAPP OK

echo.
echo [5/5] Compilando BACKEND...
call gradlew.bat :backend:build --no-daemon -x test --quiet
if %errorlevel% neq 0 (
    echo ❌ ERRO ao compilar BACKEND
    pause
    exit /b 1
)
echo ✅ BACKEND OK

echo.
echo ==========================================
echo   ✅ COMPILACAO COMPLETA!
echo ==========================================
echo.
echo Agora execute: INICIAR_DOCKER.bat
echo.
pause

