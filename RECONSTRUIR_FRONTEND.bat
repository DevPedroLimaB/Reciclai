@echo off
chcp 65001 >nul
cls
echo ==========================================
echo   RECONSTRUIR FRONTEND - GOOGLE MAPS
echo ==========================================
echo.
cd /d "%~dp0"

echo [1/2] Reconstruindo imagem Docker do frontend...
docker-compose build frontend

if %errorlevel% neq 0 (
    echo ❌ ERRO ao reconstruir imagem!
    pause
    exit /b 1
)

echo.
echo [2/2] Reiniciando containers...
docker-compose down
docker-compose up -d

echo.
echo ✅ Frontend reconstruído com sucesso!
echo.
echo Aguardando 15 segundos para iniciar serviços...
timeout /t 15 /nobreak

echo.
echo ==========================================
echo Abra o navegador em: http://localhost:3000
echo Login: teste@reciclai.com / senha123
echo ==========================================
echo.
echo 🗺️ Agora com Google Maps API!
echo.
pause

