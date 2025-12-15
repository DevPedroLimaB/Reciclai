@echo off
chcp 65001 >nul
echo ==========================================
echo   🔧 COMPILAR BACKEND APENAS
echo ==========================================
echo.

echo [1/2] Parando daemons do Gradle...
call gradlew.bat --stop

echo.
echo [2/2] Compilando auth-service e backend...

echo.
echo → Auth Service...
call gradlew.bat :auth-service:clean :auth-service:build -x test --no-daemon
if errorlevel 1 (
    echo ❌ Erro ao compilar auth-service!
    pause
    exit /b 1
)

echo.
echo → Backend...
call gradlew.bat :backend:clean :backend:build -x test --no-daemon
if errorlevel 1 (
    echo ❌ Erro ao compilar backend!
    pause
    exit /b 1
)

echo.
echo ==========================================
echo ✅ BACKEND COMPILADO COM SUCESSO!
echo.
echo JARs gerados:
echo   - auth-service/build/libs/auth-service.jar
echo   - backend/build/libs/backend.jar
echo ==========================================
pause

