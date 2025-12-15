@echo off
chcp 65001 >nul
echo ==========================================
echo   🛑 PARAR DOCKER - RECICLAI
echo ==========================================
echo.
echo Parando containers...
docker-compose down

if errorlevel 1 (
    echo ❌ Erro ao parar containers!
    pause
    exit /b 1
)

echo.
echo ==========================================
echo ✅ CONTAINERS PARADOS COM SUCESSO!
echo.
echo Para iniciar novamente use: INICIAR_DOCKER.bat
echo ==========================================
pause

