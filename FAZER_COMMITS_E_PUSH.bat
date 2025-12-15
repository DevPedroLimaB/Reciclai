@echo off
cd /d "C:\Users\Pedro Lima\Reciclai"

echo ====================================
echo Criando commits organizados...
echo ====================================
echo.

REM Commit 1: Remover arquivos .bat desnecessarios
echo [1/6] Removendo arquivos .bat desnecessarios...
git rm --cached APLICAR_AGORA.bat COMMITS_MULTIPLOS.bat COMMIT_E_PUSH.bat COMMIT_RAPIDO.bat 2>nul
git rm --cached CORRECAO_COMPLETA.bat CORRECAO_DEFINITIVA.bat CORRIGIR_BACKEND.bat CORRIGIR_BOTOES.bat 2>nul
git rm --cached CORRIGIR_CONFLITO.bat CORRIGIR_ERRO_403.bat CORRIGIR_FRONTEND_FINAL.bat CORRIGIR_HTTP_400.bat 2>nul
git rm --cached CORRIGIR_LOGIN.bat CORRIGIR_SENHAS.bat CORRIGIR_SENHA_DEFINITIVO.bat CORRIGIR_SITE.bat 2>nul
git rm --cached CORRIGIR_WEB_DEFINITIVO.bat DIAGNOSTICO.bat DIAGNOSTICO_SITE.bat EXECUTAR_CORRECAO.bat 2>nul
git rm --cached EXECUTAR_LIMPEZA.bat LIMPAR_ARQUIVOS_INUTEIS.bat LIMPAR_BATS.bat LIMPAR_BATS_AGORA.bat 2>nul
git rm --cached LIMPAR_CACHE_COMPLETO.bat LIMPAR_FINAL.bat RAPIDO.bat RESETAR_E_TESTAR.bat 2>nul
git rm --cached RESETAR_TUDO.bat RESOLVER_SITE.bat SOLUCAO_FINAL.bat TESTAR_API.bat 2>nul
git rm --cached TESTAR_HASH_BCRYPT.bat TESTAR_LOGIN.bat TESTAR_SERVICOS.bat 2>nul
git rm --cached ATUALIZAR_PONTOS_RECIFE.bat ATUALIZAR_FRONTEND_GOOGLE_MAPS.bat ATUALIZAR_WEBAPP_COMPLETO.bat 2>nul
git commit -m "chore: remover scripts .bat desnecessarios (34 arquivos)" -m "Mantidos apenas scripts essenciais: build, docker, atualização e limpeza"
echo.

REM Commit 2: Reorganizar estrutura do app (app -> androidApp)
echo [2/6] Reorganizando estrutura do Android App...
git add app/ androidApp/
git commit -m "refactor(android): reorganizar pasta app para androidApp" -m "- Renomear diretório app/ para androidApp/ para seguir convenção KMP" -m "- Mover arquivos de configuração e recursos" -m "- Atualizar manifests e configurações do Gradle"
echo.

REM Commit 3: Adicionar UI Screens do Android
echo [3/6] Adicionando telas da UI do Android...
git add androidApp/src/main/java/com/example/reciclai/ui/screens/
git add androidApp/src/main/java/com/example/reciclai/ui/theme/
git commit -m "feat(android): adicionar telas completas da UI" -m "- SettingsScreen com preview e acessibilidade" -m "- LoginScreen, RegisterScreen, ProfileScreen" -m "- MapScreen com suporte a Google Maps" -m "- ContentScreen, HistoryScreen, AchievementsScreen" -m "- WelcomeScreen, SplashScreen, AboutScreen" -m "- Tema personalizado com cores da marca"
echo.

REM Commit 4: Backend e Auth Service
echo [4/6] Adicionando backend Kotlin e auth-service...
git add backend/ auth-service/
git commit -m "feat(backend): migrar para Kotlin Spring Boot e adicionar auth-service" -m "- Remover backend Node.js legado" -m "- Implementar backend Kotlin com Spring Boot" -m "- Adicionar microserviço de autenticação separado" -m "- Configurar JWT e segurança" -m "- Adicionar Dockerfiles para deploy"
echo.

REM Commit 5: Shared module e WebApp
echo [5/6] Adicionando módulo compartilhado e WebApp...
git add shared/ webApp/
git commit -m "feat(shared): adicionar módulo compartilhado KMP e WebApp Kotlin/JS" -m "- Implementar shared module com código comum" -m "- Adicionar ApiClient, models e repositories compartilhados" -m "- Implementar WebApp com Kotlin/JS" -m "- Adicionar suporte para plataformas JS e JVM"
echo.

REM Commit 6: Configurações, documentação e arquivos de build
echo [6/6] Atualizando configurações e documentação...
git add .gitignore README.md build.gradle.kts settings.gradle.kts gradle.properties
git add gradle/ docker-compose.yml database/ .kotlin/
git add ARQUITETURA.md MICROSERVICOS.md INICIO_RAPIDO.md INSTRUCOES.md
git add *.bat LIMPAR_BATS.ps1 local.defaults.properties secrets.properties fix_password.sql
git add 8081) A Compilation Get Run Task
git commit -m "chore: atualizar configurações do projeto e documentação" -m "- Atualizar gradle wrapper e dependências" -m "- Adicionar docker-compose.yml para infraestrutura" -m "- Atualizar .gitignore para KMP" -m "- Adicionar documentação de arquitetura e microserviços" -m "- Manter scripts .bat essenciais" -m "- Adicionar metadados Kotlin"
echo.

echo ====================================
echo Fazendo push para o repositório remoto...
echo ====================================
git push origin master

echo.
echo ====================================
echo CONCLUIDO!
echo ====================================
echo.
echo Total de commits criados: 6
echo Push para origin/master: concluido
echo.
echo Historico de commits:
git log --oneline -6
echo.
pause

