# 🌿 Reciclai - Plataforma de Reciclagem

![Versão](https://img.shields.io/badge/versão-1.0.0-green)
![Kotlin](https://img.shields.io/badge/Kotlin-Multiplatform-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Arquitetura](#arquitetura)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [APIs e Serviços](#apis-e-serviços)
- [Como Executar](#como-executar)
- [Scripts Disponíveis](#scripts-disponíveis)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Endpoints da API](#endpoints-da-api)
- [Troubleshooting](#troubleshooting)
- [Desenvolvimento](#desenvolvimento)

---

## 🌍 Sobre o Projeto

O **Reciclai** é uma plataforma multiplataforma (Web e Android) que conecta pessoas e empresas aos pontos de coleta de materiais recicláveis. O projeto promove a sustentabilidade através da tecnologia, facilitando a localização de pontos de coleta e educando sobre práticas de reciclagem.

### 🎯 Funcionalidades Principais

- 📍 **Mapa de Pontos de Coleta**: Localização em tempo real de pontos de reciclagem
- 🔐 **Autenticação Segura**: Sistema de login/registro com JWT
- 📚 **Conteúdo Educativo**: Artigos sobre reciclagem e sustentabilidade
- 👤 **Perfil de Usuário**: Acompanhamento de contribuições
- 🏆 **Sistema de Conquistas**: Gamificação para incentivar a reciclagem
- 🌐 **Multiplataforma**: Web (Kotlin/JS) e Android (Kotlin/Compose)

---

## 🏗️ Arquitetura

O projeto utiliza uma **arquitetura de microserviços** com os seguintes componentes:

```
┌─────��───────────┐         ┌─────────────────┐
│   Web Frontend  │         │  Android App    │
│  (Kotlin/JS)    │         │(Kotlin/Compose) │
│   Port: 3000    │         │                 │
└────────┬────────┘         └────────┬────────┘
         │                           │
         └───────────┬───────────────┘
                     │
         ┌───────────▼───────────────┐
         │      NGINX / Proxy        │
         └───────────┬───────────────┘
                     │
         ┏━━━━━━━━━━━┻━━━━━━━━━━━━━┓
         ▼                          ▼
┌─────────────────┐      ┌─────────────────┐
│  Auth Service   │      │  Backend API    │
│  (Spring Boot)  │      │  (Spring Boot)  │
│   Port: 8082    │      │   Port: 8080    │
└────────┬────────┘      └────────┬────────┘
         │                         │
         └──────────┬──────────────┘
                    ▼
         ┌─────────────────────┐
         │   PostgreSQL DB     │
         │    Port: 5434       │
         └─────────────────────┘
```

### 📦 Microserviços

1. **Auth Service** (`:8082`)
   - Autenticação e autorização
   - Geração e validação de tokens JWT
   - Gerenciamento de usuários

2. **Backend Service** (`:8080`)
   - Gerenciamento de pontos de coleta
   - Conteúdo educativo
   - Histórico e conquistas

3. **Frontend Web** (`:3000`)
   - Interface web responsiva
   - Mapa interativo
   - Dashboard de usuário

4. **Android App**
   - App nativo com Jetpack Compose
   - Sincronização offline
   - Notificações push

---

## 💻 Tecnologias Utilizadas

### Backend
- **Kotlin** - Linguagem principal
- **Spring Boot 3.x** - Framework para microserviços
- **PostgreSQL** - Banco de dados relacional
- **JWT** - Autenticação stateless
- **Docker & Docker Compose** - Containerização

### Frontend Web
- **Kotlin/JS** - Compilação para JavaScript
- **Kotlin HTML DSL** - Construção de UI
- **NGINX** - Servidor web

### Android
- **Kotlin** - Linguagem nativa
- **Jetpack Compose** - UI declarativa
- **Kotlin Coroutines** - Programação assíncrona
- **Google Maps SDK** - Mapas

### Shared
- **Kotlin Multiplatform** - Código compartilhado
- **Ktor Client** - Cliente HTTP multiplataforma

---

## 📁 Estrutura do Projeto

```
Reciclai/
├── 📂 auth-service/              # Microserviço de Autenticação
│   ├── src/main/kotlin/
│   │   └── com/example/reciclai/authservice/
│   │       ├── controller/       # AuthController.kt
│   │       ├── service/          # AuthService.kt
│   │       ├── repository/       # UserRepository.kt
│   │       ├── model/            # User.kt
│   │       ├── dto/              # DTOs.kt (LoginRequest, RegisterRequest)
│   │       ├── security/         # JwtTokenProvider.kt
│   │       └── config/           # SecurityConfig.kt
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── 📂 backend/                   # Backend API Principal
│   ├── src/main/kotlin/
│   │   └── com/example/reciclai/backend/
│   │       ├── controller/       # Controllers de Pontos e Conteúdo
│   │       ├── service/          # Lógica de negócio
│   │       ├── repository/       # Repositórios JPA
│   │       ├── model/            # Entidades (RecyclingPoint, Content)
│   │       └── config/           # Configurações (CORS, etc)
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── 📂 webApp/                    # Frontend Web (Kotlin/JS)
│   ├── src/jsMain/kotlin/
│   │   ├── Main.kt              # Ponto de entrada
│   │   ├── Screens.kt           # Telas (Login, Register, Map, Dashboard)
│   │   └── ApiService.kt        # Cliente HTTP para consumir APIs
│   ├── Dockerfile
│   └── build.gradle.kts
│
├── 📂 androidApp/                # Aplicativo Android
│   ├── src/main/java/com/example/reciclai/
│   │   ├── ui/screens/          # Telas Compose
│   │   │   ├── LoginScreen.kt
│   │   │   ├── RegisterScreen.kt
│   │   │   ├── ForgotPasswordScreen.kt
│   │   │   ├── MapScreen.kt
│   │   │   ├── ProfileScreen.kt
│   │   │   ├── AboutScreen.kt
│   │   │   └── ...
│   │   ├── viewmodel/           # ViewModels (AuthViewModel, MapViewModel)
│   │   ├── repository/          # Repositórios
│   │   ├── navigation/          # Navegação
│   │   ├── di/                  # Injeção de dependência
│   │   └── MainActivity.kt
│   └── build.gradle.kts
│
├── 📂 shared/                    # Código Compartilhado (KMP)
│   ├── src/commonMain/kotlin/
│   │   └── com/example/reciclai/shared/
│   │       ├── model/           # Models.kt (User, RecyclingPoint, Content)
│   │       ├── network/         # ApiService.kt (cliente HTTP compartilhado)
│   │       ├── repository/      # Repositories.kt
│   │       └── storage/         # StorageService.kt
│   └── build.gradle.kts
│
├── 📂 database/
│   └── init.sql                 # Script de inicialização do DB
│
├── 📄 docker-compose.yml         # Orquestração de containers
├── 📄 build.gradle.kts          # Build raiz
├── 📄 settings.gradle.kts       # Configuração de módulos
└── 📄 README.md                 # Este arquivo
```

---

## 🔌 APIs e Serviços

### 🔐 Auth Service (Port 8082)

**Base URL**: `http://localhost:8082/api`

| Endpoint | Método | Descrição | Consumido por |
|----------|--------|-----------|---------------|
| `/auth/register` | POST | Cadastro de novo usuário | Web: `ApiService.kt:161`<br>Android: `AuthViewModel.kt` |
| `/auth/login` | POST | Login de usuário | Web: `ApiService.kt:142`<br>Android: `LoginScreen.kt` |
| `/auth/health` | GET | Health check | Docker healthcheck |

**Localização do consumo:**
- **Web**: `webApp/src/jsMain/kotlin/ApiService.kt` (linhas 140-180)
- **Android**: `androidApp/src/main/java/com/example/reciclai/ui/screens/LoginScreen.kt`
- **Android**: `androidApp/src/main/java/com/example/reciclai/ui/screens/RegisterScreen.kt`
- **Android**: `androidApp/src/main/java/com/example/reciclai/viewmodel/AuthViewModel.kt`

**Exemplo de Request - Login:**
```json
POST /api/auth/login
Content-Type: application/json

{
  "email": "usuario@reciclai.com",
  "password": "senha123"
}
```

**Exemplo de Response:**
```json
{
  "success": true,
  "message": "Login realizado com sucesso",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "user": {
      "id": "uuid-123",
      "name": "João Silva",
      "email": "usuario@reciclai.com"
    }
  }
}
```

---

### 🌍 Backend Service (Port 8080)

**Base URL**: `http://localhost:8080/api`

| Endpoint | Método | Descrição | Consumido por |
|----------|--------|-----------|---------------|
| `/points` | GET | Lista pontos de coleta | Web: `ApiService.kt:70`<br>Android: `MapViewModel.kt` |
| `/points/{id}` | GET | Detalhes de um ponto | Web: `Screens.kt`<br>Android: `MapScreen.kt` |
| `/points` | POST | Criar novo ponto | Web/Android (autenticado) |
| `/contents` | GET | Lista conteúdos educativos | Web: `ApiService.kt:85`<br>Android: `ContentViewModel.kt` |
| `/user/profile` | GET | Perfil do usuário | Web: `ApiService.kt:100` |
| `/user/history` | GET | Histórico de reciclagem | Android: `HistoryScreen.kt` |

**Localização do consumo:**
- **Web**: `webApp/src/jsMain/kotlin/ApiService.kt` (linhas 60-120)
- **Web**: `webApp/src/jsMain/kotlin/Screens.kt` (função `showMapScreen`)
- **Android**: `androidApp/src/main/java/com/example/reciclai/viewmodel/MapViewModel.kt`
- **Android**: `androidApp/src/main/java/com/example/reciclai/viewmodel/ContentViewModel.kt`
- **Shared**: `shared/src/commonMain/kotlin/com/example/reciclai/shared/network/ApiService.kt`

**Exemplo de Request - Listar Pontos:**
```json
GET /api/points
Authorization: Bearer {token}
```

**Exemplo de Response:**
```json
{
  "success": true,
  "message": null,
  "data": [
    {
      "id": "uuid-123",
      "name": "EcoPonto Boa Vista",
      "address": "Av. Conde da Boa Vista, 1234",
      "latitude": -8.06317,
      "longitude": -34.89152,
      "acceptedMaterials": ["Papel", "Plástico", "Vidro"],
      "schedule": "Seg-Sex: 8h-18h",
      "phone": "81987654321",
      "rating": 4.5,
      "isActive": true
    }
  ]
}
```

---

## 🚀 Como Executar

### Pré-requisitos

- **Docker Desktop** (Windows/Mac) ou Docker Engine (Linux)
- **Java 17+** (para desenvolvimento local)
- **Node.js 16+** (para frontend web)
- **Android Studio** (para desenvolvimento Android)
- **Mínimo 4GB de RAM disponível** (recomendado 8GB)

### 🐳 Executar com Docker (Recomendado)

#### Opção 1: Script Automático (Mais Fácil)

```bash
# Limpe os arquivos .bat desnecessários (opcional, primeira vez)
EXECUTAR_LIMPEZA.bat

# Inicie tudo automaticamente
INICIAR_DOCKER.bat
```

O script `INICIAR_DOCKER.bat` vai:
1. ✅ Verificar se o Docker está instalado
2. 🛑 Parar daemons antigos do Gradle
3. 🔨 Compilar auth-service, backend e webApp separadamente
4. 🐳 Construir e iniciar todos os containers

**Tempo estimado**: 5-10 minutos na primeira execução.

#### Opção 2: Manual (Passo a Passo)

```bash
# 1. Parar daemons do Gradle
gradlew.bat --stop

# 2. Compilar cada módulo separadamente
gradlew.bat :auth-service:build -x test --no-daemon
gradlew.bat :backend:build -x test --no-daemon
gradlew.bat :webApp:build --no-daemon

# 3. Iniciar containers
docker-compose up -d --build
```

### 🛠️ Desenvolvimento Local

#### Backend (Auth Service + Backend)

```bash
# Auth Service
cd auth-service
../gradlew.bat bootRun

# Backend
cd backend
../gradlew.bat bootRun
```

#### Frontend Web

```bash
cd webApp
../gradlew.bat jsRun
```

#### Android App

1. Abra o projeto no Android Studio
2. Aguarde a sincronização do Gradle
3. Execute: `Run > Run 'androidApp'`

---

## 📜 Scripts Disponíveis

### Scripts Principais

| Script | Descrição | Quando usar |
|--------|-----------|-------------|
| `INICIAR_DOCKER.bat` | 🚀 Compila tudo e inicia containers | Primeira vez ou após mudanças |
| `PARAR_DOCKER.bat` | 🛑 Para todos os containers | Quando terminar de usar |
| `EXECUTAR_LIMPEZA.bat` | 🧹 Move ~40 .bat antigos para backup | Uma vez, para organizar |

### Scripts de Compilação

| Script | Descrição | Tempo |
|--------|-----------|-------|
| `COMPILAR_BACKEND.bat` | Compila apenas auth-service + backend | ~2 min |
| `COMPILAR_FRONTEND.bat` | Compila apenas webApp | ~1 min |
| `COMPILAR_ANDROID.bat` | Compila apenas Android App | ~3 min |
| `LIMPAR_GRADLE.bat` | Limpa cache e força rebuild | ~30 seg |

### Exemplos de Uso

```bash
# Cenário 1: Mudou algo no backend
COMPILAR_BACKEND.bat
docker-compose up -d --build auth-service backend

# Cenário 2: Mudou algo no frontend web
COMPILAR_FRONTEND.bat
docker-compose up -d --build frontend

# Cenário 3: Gradle dando problema
LIMPAR_GRADLE.bat
INICIAR_DOCKER.bat

# Cenário 4: Testar Android localmente
COMPILAR_ANDROID.bat
# Depois abra no Android Studio e execute
```

---

## 🔧 Variáveis de Ambiente

### Auth Service (application.properties)
```properties
server.port=8082
spring.datasource.url=jdbc:postgresql://postgres:5432/reciclai
spring.datasource.username=reciclai_user
spring.datasource.password=reciclai_pass_2024
jwt.secret=reciclai_super_secret_key_2024
jwt.expiration=86400000
```

### Backend Service (application.properties)
```properties
server.port=8080
spring.datasource.url=jdbc:postgresql://localhost:5432/reciclai
spring.datasource.username=reciclai_user
spring.datasource.password=reciclai_pass_2024
cors.allowed-origins=http://localhost:3000,http://localhost:8081
```

### Frontend Web (ApiService.kt)
```kotlin
AUTH_SERVICE_URL = "http://localhost:8082/api"
BACKEND_SERVICE_URL = "http://localhost:8080/api"
```

---

## 📡 Endpoints da API

### Códigos de Status HTTP

| Código | Descrição |
|--------|-----------|
| 200 | Sucesso |
| 201 | Recurso criado |
| 400 | Requisição inválida (verifique o JSON enviado) |
| 401 | Não autenticado (token inválido/expirado) |
| 403 | Sem permissão |
| 404 | Recurso não encontrado |
| 500 | Erro interno do servidor |

### Padrão de Resposta

Todas as respostas seguem o padrão:

```json
{
  "success": true/false,
  "message": "Mensagem descritiva",
  "data": { ... }
}
```

---

## 🧪 Testes

### Teste de Login (Web)

1. Acesse: http://localhost:3000
2. Clique em "Cadastre-se" para criar uma conta
3. Preencha os dados e clique em "Criar Conta"
4. Faça login com as credenciais criadas

### Teste de Pontos de Coleta

```bash
curl -X GET http://localhost:8080/api/points \
  -H "Content-Type: application/json"
```

### Teste de Autenticação

```bash
curl -X POST http://localhost:8082/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teste@reciclai.com",
    "password": "senha123"
  }'
```

---

## 🐛 Troubleshooting

### ❌ Gradle daemon disappeared / crashed

**Problema**: `Gradle build daemon disappeared unexpectedly`

**Causa**: Memória insuficiente ou muitos processos Gradle rodando

**Solução**:
```bash
# 1. Pare todos os daemons
gradlew.bat --stop

# 2. Limpe o cache
LIMPAR_GRADLE.bat

# 3. Compile módulos separadamente
COMPILAR_BACKEND.bat
COMPILAR_FRONTEND.bat

# 4. Se ainda crashar, ajuste gradle.properties:
# org.gradle.jvmargs=-Xmx1536m -XX:MaxMetaspaceSize=384m
```

**Se seu PC tem menos de 8GB RAM**: Edite `gradle.properties` e reduza memória:
```properties
org.gradle.jvmargs=-Xmx1536m -XX:MaxMetaspaceSize=384m
kotlin.daemon.jvmargs=-Xmx1024m -XX:MaxMetaspaceSize=256m
```

### ❌ Erro 400 no Login (Web)

**Problema**: `Failed to load resource: the server responded with a status of 400`

**Causa**: Dados enviados incorretos ou campos faltando

**Solução**:
1. Verifique se o email está no formato correto
2. Verifique se a senha tem no mínimo 6 caracteres
3. Veja os logs do backend:
   ```bash
   docker logs reciclai_auth_service -f
   ```

### ❌ Botão "Cadastre-se" não funciona (Web)

**Problema**: Nada acontece ao clicar

**Solução**:
1. Abra o DevTools (F12)
2. Veja o console para erros
3. Recompile o frontend:
   ```bash
   COMPILAR_FRONTEND.bat
   docker-compose up -d --build frontend
   ```

### ❌ Erro de compilação Android

**Problema**: `Unresolved reference` ou erros de sintaxe

**Solução**:
```bash
# 1. Limpe o projeto
LIMPAR_GRADLE.bat

# 2. Recompile Android
COMPILAR_ANDROID.bat

# 3. No Android Studio:
# File > Invalidate Caches / Restart
```

### ❌ Containers não iniciam

**Problema**: Docker não sobe os serviços

**Solução**:
```bash
# Pare tudo e limpe
docker-compose down -v

# Verifique se as portas estão livres
netstat -ano | findstr "3000 8080 8082 5434"

# Reconstrua
INICIAR_DOCKER.bat
```

### ❌ JVM crash log found (hs_err_pid*.log)

**Problema**: Gradle crashou e criou arquivo de log de erro

**Solução**:
```bash
# 1. Delete logs antigos
del hs_err_pid*.log

# 2. Reduza memória do Gradle (edite gradle.properties)
org.gradle.jvmargs=-Xmx1536m

# 3. Recompile
gradlew.bat --stop
INICIAR_DOCKER.bat
```

---

## 📱 Recursos por Plataforma

| Recurso | Web | Android |
|---------|-----|---------|
| Login/Registro | ✅ | ✅ |
| Esqueci Senha | ✅ | ✅ |
| Mapa de Pontos | ✅ | ✅ |
| Filtros de Materiais | ✅ | ✅ |
| Perfil de Usuário | ✅ | ✅ |
| Conteúdo Educativo | ✅ | ✅ |
| Histórico | ✅ | ✅ |
| Conquistas | ✅ | ✅ |
| Modo Offline | ❌ | ✅ |
| Notificações | ❌ | ✅ |
| Geolocalização | ✅ | ✅ |

---

## 👥 Contribuindo

1. Fork o projeto
2. Crie uma branch: `git checkout -b feature/nova-funcionalidade`
3. Commit suas mudanças: `git commit -m 'Adiciona nova funcionalidade'`
4. Push para a branch: `git push origin feature/nova-funcionalidade`
5. Abra um Pull Request

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---

## 📞 Contato

- 📧 Email: contato@reciclai.com.br
- 🌐 Site: www.reciclai.com.br
- 📱 Instagram: @reciclai

---

## 🙏 Agradecimentos

Desenvolvido com 💚 para um planeta melhor.

**© 2025 Reciclai. Todos os direitos reservados.**
