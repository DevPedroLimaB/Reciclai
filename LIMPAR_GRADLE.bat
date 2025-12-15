@echo off
chcp 65001 >nul
echo ==========================================
echo   🧹 LIMPAR CACHE DO GRADLE
echo ==========================================
echo.

echo [1/3] Parando todos os daemons...
call gradlew.bat --stop

echo.
echo [2/3] Limpando cache do Gradle...
call gradlew.bat clean --no-daemon

echo.
echo [3/3] Removendo cache local...
if exist ".gradle" (
    echo Removendo .gradle...
    rmdir /s /q .gradle
)

if exist "build" (
    echo Removendo build...
    rmdir /s /q build
)

echo.
echo ==========================================
echo ✅ CACHE LIMPO COM SUCESSO!
echo.
echo Execute INICIAR_DOCKER.bat para recompilar tudo
echo ==========================================
pause

