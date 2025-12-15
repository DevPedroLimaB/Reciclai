@echo off
chcp 65001 >nul
cls
echo ==========================================
echo   ATUALIZAR FRONTEND COM MAPA
echo ==========================================
echo.
echo Criando interface com:
echo   - Mapa interativo Leaflet
echo   - Integracao com API de pontos
echo   - 3 telas: Mapa, Conteudo, Perfil
echo   - Bottom navigation
echo.
pause

echo.
echo [1/4] Parando frontend...
docker-compose stop frontend

echo.
echo [2/4] Recompilando WebApp com novo layout...
call gradlew.bat :webApp:clean :webApp:jsBrowserProductionWebpack --no-daemon

if errorlevel 1 (
    echo.
    echo ERRO na compilacao!
    pause
    exit /b 1
)

echo.
echo [3/4] Reconstruindo imagem do frontend...
docker-compose build --no-cache frontend

echo.
echo [4/4] Iniciando frontend atualizado...
docker-compose up -d frontend

echo.
echo ==========================================
echo   FRONTEND ATUALIZADO!
echo ==========================================
echo.
echo NOVO LAYOUT INCLUI:
echo.
echo  Tela do Mapa (Principal):
echo    - Mapa interativo com Leaflet
echo    - Marcadores dos pontos de reciclagem
echo    - Lista de pontos proximos
echo    - Barra de busca
echo.
echo  Tela de Conteudo:
echo    - Artigos educativos sobre reciclagem
echo    - Categorias e tempo de leitura
echo.
echo  Tela de Perfil:
echo    - Informacoes do usuario
echo    - Estatisticas (pontos, nivel, kg reciclado)
echo    - Configuracoes
echo.
echo Acesse: http://localhost:3000
echo.
echo Credenciais:
echo   Email: teste@reciclai.com
echo   Senha: senha123
echo.
pause

