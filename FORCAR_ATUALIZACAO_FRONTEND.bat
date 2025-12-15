@echo off
chcp 65001 >nul
cls
echo ==========================================
echo   FORCAR ATUALIZACAO DO FRONTEND
echo ==========================================
echo.
echo O frontend foi compilado com sucesso mas
echo o navegador pode estar usando cache antigo.
echo.
echo Este script vai forcar a atualizacao completa.
echo.
pause

echo.
echo [1/5] Parando frontend...
docker-compose stop frontend

echo.
echo [2/5] Removendo container e imagem antiga...
docker rm -f reciclai_frontend 2>nul
docker rmi reciclai-frontend:latest 2>nul

echo.
echo [3/5] Limpando cache do Docker...
docker builder prune -f

echo.
echo [4/5] Reconstruindo imagem do ZERO (sem cache)...
docker-compose build --no-cache --pull frontend

echo.
echo [5/5] Iniciando frontend com versao nova...
docker-compose up -d frontend

echo.
echo Aguardando 5 segundos para o servico iniciar...
timeout /t 5 /nobreak >nul

echo.
echo ==========================================
echo   FRONTEND ATUALIZADO!
echo ==========================================
echo.
echo IMPORTANTE: Para ver as mudancas, faca:
echo.
echo 1. Abra o navegador em MODO ANONIMO (Ctrl+Shift+N no Chrome)
echo    OU
echo.
echo 2. Limpe o cache do navegador:
echo    - Chrome: Ctrl+Shift+Delete
echo    - Selecione "Imagens e arquivos em cache"
echo    - Clique em "Limpar dados"
echo.
echo 3. Acesse: http://localhost:3000
echo.
echo 4. Faca login:
echo    Email: teste@reciclai.com
echo    Senha: senha123
echo.
echo Voce deve ver:
echo    - Mapa interativo Leaflet
echo    - 3 pontos de reciclagem marcados
echo    - Bottom navigation (Mapa, Conteudo, Perfil)
echo    - Interface estilo app mobile
echo.
echo Se ainda nao funcionar, pressione Ctrl+F5 na pagina
echo para forcar recarregamento completo!
echo.
pause

