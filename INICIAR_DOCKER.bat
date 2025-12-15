@echo off
chcp 65001 >nul
echo ==========================================
echo   🚀 INICIAR DOCKER - RECICLAI
echo ==========================================
echo.
echo [1/4] Verificando Docker...
docker --version >nul 2>&1
if errorlevel 1 (
    echo ❌ Docker não encontrado!
    echo    Instale o Docker Desktop primeiro.
    pause
    exit /b 1
)

echo ✅ Docker OK
echo.
echo [2/4] Parando daemons antigos do Gradle...
call gradlew.bat --stop

echo.
echo [3/4] Compilando JARs (pode demorar 3-5 minutos)...
echo    Compilando módulos individualmente para evitar crash...

REM Compilar auth-service
echo.
echo    → Compilando auth-service...
call gradlew.bat :auth-service:clean :auth-service:build -x test --no-daemon
if errorlevel 1 (
    echo ❌ Erro ao compilar auth-service!
    pause
    exit /b 1
)

REM Compilar backend
echo.
echo    → Compilando backend...
call gradlew.bat :backend:clean :backend:build -x test --no-daemon
if errorlevel 1 (
    echo ❌ Erro ao compilar backend!
    pause
    exit /b 1
)

REM Compilar webApp
echo.
echo    → Compilando webApp...
call gradlew.bat :webApp:clean :webApp:build --no-daemon
if errorlevel 1 (
    echo ❌ Erro ao compilar webApp!
    pause
    exit /b 1
)

echo.
echo [4/4] Iniciando containers...
docker-compose up -d --build

if errorlevel 1 (
    echo ❌ Erro ao iniciar containers!
    pause
    exit /b 1
)

echo.
echo ==========================================
echo ✅ APLICAÇÃO INICIADA COM SUCESSO!
echo.
echo 🌐 Web: http://localhost:3000
echo 🔐 Auth API: http://localhost:8082/api
echo 📍 Backend API: http://localhost:8080/api
echo 🗄️ PostgreSQL: localhost:5434
echo.
echo Use PARAR_DOCKER.bat para parar os serviços
echo ==========================================
echo.
pause
