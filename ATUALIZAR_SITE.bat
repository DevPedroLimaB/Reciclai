@echo off
chcp 65001 >nul
echo ==========================================
echo   ATUALIZAR SITE COM BOTÃO CONVIDADO
echo ==========================================
echo.

cd /d "%~dp0"

echo [1/3] Matando processos Java...
taskkill /F /IM java.exe 2>nul
timeout /t 2 /nobreak >nul
echo ✅ OK

echo.
echo [2/3] Compilando APENAS o webApp...
call gradlew.bat :webApp:build --no-daemon -x test
if %errorlevel% neq 0 (
    echo ❌ ERRO na compilação!
    pause
    exit /b 1
)
echo ✅ OK

echo.
echo [3/3] Recriando container do frontend...
docker-compose stop frontend
docker-compose rm -f frontend
docker-compose build frontend
docker-compose up -d frontend
echo ✅ OK

echo.
echo ==========================================
echo   ✅ SITE ATUALIZADO!
echo ==========================================
echo.
echo 🌐 Acesse: http://localhost:3000
echo.
echo Agora você verá o botão:
echo    🚀 Entrar como Convidado
echo.
echo Este botão permite:
echo    ✅ Entrar sem fazer login
echo    ✅ Explorar o sistema como "Convidado"
echo    ✅ Sem precisar de email/senha
echo.
pause

