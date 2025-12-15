@echo off
chcp 65001 >nul
cls
echo ==========================================
echo   FORÇAR ATUALIZAÇÃO DO BOTÃO
echo ==========================================
echo.

cd /d "%~dp0"

echo [1/5] Parando tudo...
docker-compose down -v
taskkill /F /IM java.exe 2>nul
echo ✅ OK

echo.
echo [2/5] Limpando cache...
call gradlew.bat clean --no-daemon
echo ✅ OK

echo.
echo [3/5] Compilando shared + webApp...
call gradlew.bat :shared:build :webApp:build --no-daemon -x test
if %errorlevel% neq 0 (
    echo ❌ ERRO! Verifique a compilação acima.
    pause
    exit /b 1
)
echo ✅ OK

echo.
echo [4/5] Reconstruindo imagens Docker...
docker-compose build --no-cache frontend
echo ✅ OK

echo.
echo [5/5] Iniciando containers...
docker-compose up -d
echo ✅ OK

echo.
echo ==========================================
echo   ✅ CONCLUÍDO!
echo ==========================================
echo.
echo 🌐 Abra agora: http://localhost:3000
echo.
echo Na tela de login você verá:
echo    1. [Entrar] - botão verde
echo    2. [← Voltar] - botão transparente
echo    3. [🚀 Entrar como Convidado] - botão verde claro
echo.
echo Clique no botão 3 para entrar SEM LOGIN!
echo.
pause
