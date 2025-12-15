# 🏗️ Arquitetura de Microserviços - ReciclAI

## 📊 Visão Geral

O ReciclAI foi **reestruturado em uma arquitetura de microserviços**, separando responsabilidades em serviços independentes e escaláveis.

```
┌─────────────────────────────────────────────────────────┐
│                    CLIENTE (Browser)                     │
│                  http://localhost:3000                   │
└──────────────────────┬──────────────────────────────────┘
                       │
          ┌────────────┴────────────┐
          │                         │
          ▼                         ▼
┌─────────────────────┐   ┌─────────────────────┐
│   AUTH SERVICE      │   │   BACKEND API       │
│   Porta: 8081       │   │   Porta: 8080       │
│                     │   │                     │
│ • Login             │   │ • Pontos Coleta     │
│ • Registro          │   │ • Conteúdo          │
│ • JWT               │   │ • Agendamentos      │
│ • Validação         │   │ • Recompensas       │
└──────────┬──────────┘   └──────────┬──────────┘
           │                         │
           └────────┬────────────────┘
                    │
                    ▼
          ┌─────────────────────┐
          │   POSTGRESQL DB     │
          │   Porta: 5434       │
          │                     │
          │ • users             │
          │ • recycling_points  │
          │ • contents          │
          │ • schedules         │
          └─────────────────────┘
```

---

## 🎯 Microserviços

### 1️⃣ **Auth Service** (Porta 8081)
**Responsabilidade:** Autenticação e Gestão de Usuários

**Endpoints:**
- `POST /api/auth/login` - Login de usuário
- `POST /api/auth/register` - Cadastro de novo usuário
- `GET /api/auth/me/{userId}` - Dados do usuário
- `GET /api/auth/health` - Health check

**Tecnologias:**
- Spring Boot 3.2.0
- Spring Security
- JWT (JSON Web Tokens)
- BCrypt para hash de senhas
- PostgreSQL

**Funcionalidades:**
- ✅ Login com email e senha
- ✅ Cadastro de novos usuários
- ✅ Geração de tokens JWT
- ✅ Validação de tokens
- ✅ Criptografia de senhas com BCrypt
- ✅ Gestão de perfis de usuário

---

### 2️⃣ **Backend API** (Porta 8080)
**Responsabilidade:** Lógica de Negócio Principal

**Endpoints:**
- `GET /api/recycling-points/all` - Listar pontos
- `POST /api/recycling-points` - Criar ponto
- `GET /api/contents/all` - Listar conteúdo
- Outros endpoints de negócio

**Tecnologias:**
- Spring Boot 3.2.0
- Spring Data JPA
- PostgreSQL

**Funcionalidades:**
- ✅ Gestão de pontos de reciclagem
- ✅ Conteúdo educativo
- ✅ Sistema de pontos/gamificação
- ✅ Agendamentos de coleta
- ✅ Recompensas

---

### 3️⃣ **Frontend Web** (Porta 3000)
**Responsabilidade:** Interface do Usuário

**Tecnologias:**
- Kotlin/JS
- HTML5/CSS3
- Fetch API para comunicação

**Funcionalidades:**
- ✅ Interface responsiva
- ✅ Comunicação com ambos os microserviços
- ✅ Roteamento de requisições
- ✅ Gerenciamento de estado

---

### 4️⃣ **PostgreSQL Database** (Porta 5434)
**Responsabilidade:** Persistência de Dados

**Banco Compartilhado:**
- Todos os microserviços usam o mesmo banco PostgreSQL
- Isolamento por schemas ou tabelas específicas

---

## 🔐 Fluxo de Autenticação

```
1. Usuário envia credenciais → Frontend
2. Frontend → Auth Service (POST /api/auth/login)
3. Auth Service valida credenciais no DB
4. Auth Service gera JWT token
5. Auth Service retorna token + dados do usuário
6. Frontend armazena token
7. Frontend usa token em requisições ao Backend API
```

---

## 🚀 Como Executar

### Método Rápido (Recomendado)

```cmd
RAPIDO.bat
```

Este script:
1. ✅ Para containers antigos
2. ✅ Compila **auth-service** + backend + webApp
3. ✅ Sobe containers Docker
4. ✅ Aguarda serviços iniciarem

### Verificar Serviços

Após executar, verifique se os serviços estão ativos:

```cmd
# Auth Service
curl http://localhost:8081/api/auth/health

# Backend API
docker logs reciclai_backend

# Auth Service Logs
docker logs reciclai_auth_service
```

---

## 📡 Portas dos Serviços

| Serviço | Porta | URL |
|---------|-------|-----|
| **Auth Service** | 8081 | http://localhost:8081/api/auth |
| **Backend API** | 8080 | http://localhost:8080/api |
| **Frontend** | 3000 | http://localhost:3000 |
| **PostgreSQL** | 5434 | localhost:5434 |

---

## 🔑 Testando os Microserviços

### 1. Testar Auth Service (Registro)

```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Novo Usuário",
    "email": "novo@reciclai.com",
    "password": "senha123",
    "phone": "11999999999"
  }'
```

### 2. Testar Auth Service (Login)

```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teste@reciclai.com",
    "password": "senha123"
  }'
```

### 3. Testar Backend API (Pontos)

```bash
curl http://localhost:8080/api/recycling-points/all
```

---

## 🔄 Comunicação entre Serviços

### Frontend → Auth Service
```javascript
// Login
POST http://localhost:8081/api/auth/login
Body: { email, password }
Response: { success, data: { token, user } }
```

### Frontend → Backend API
```javascript
// Buscar pontos
GET http://localhost:8080/api/recycling-points/all
Headers: { Authorization: "Bearer <token>" }
Response: [ { id, name, address, ... } ]
```

---

## 🛡️ Segurança

### Auth Service
- ✅ Senhas criptografadas com BCrypt (10 rounds)
- ✅ JWT tokens com expiração de 24h
- ✅ CORS configurado com `allowedOriginPatterns`
- ✅ Endpoints públicos apenas para login/registro

### Backend API
- ✅ Validação de tokens JWT
- ✅ Endpoints protegidos requerem autenticação
- ✅ CORS configurado
- ✅ Rate limiting (futuro)

---

## 📈 Vantagens da Arquitetura de Microserviços

### ✅ Escalabilidade Independente
- Auth Service pode escalar separadamente do Backend
- Cada serviço tem recursos dedicados

### ✅ Manutenção Simplificada
- Mudanças no auth não afetam o backend
- Deploy independente de cada serviço

### ✅ Resiliência
- Se o auth cair, backend continua funcionando
- Isolamento de falhas

### ✅ Especialização
- Auth Service focado apenas em autenticação
- Backend focado em lógica de negócio

### ✅ Desenvolvimento Paralelo
- Times diferentes podem trabalhar em serviços diferentes
- Menor acoplamento

---

## 🔧 Troubleshooting

### Auth Service não inicia
```cmd
docker logs reciclai_auth_service
# Verifique se o PostgreSQL está rodando
docker ps | findstr postgres
```

### Backend não se conecta ao Auth Service
```cmd
# Verifique se ambos estão na mesma rede Docker
docker network inspect reciclai_reciclai_network
```

### Frontend não conecta aos serviços
- Verifique se os serviços estão em `http://localhost:8080` e `http://localhost:8081`
- Abra o console do navegador (F12) e veja erros de CORS

---

## 📊 Monitoramento

### Logs em Tempo Real

```cmd
# Auth Service
docker logs reciclai_auth_service -f

# Backend
docker logs reciclai_backend -f

# Todos os serviços
docker-compose logs -f
```

### Health Checks

```cmd
# Auth Service
curl http://localhost:8081/api/auth/health

# Verificar containers
docker ps
```

---

## 🎓 Próximos Passos (Futuro)

1. **API Gateway** - Gateway único para rotear requisições
2. **Service Discovery** - Eureka ou Consul
3. **Circuit Breaker** - Resilience4j
4. **Distributed Tracing** - Zipkin/Jaeger
5. **Centralized Logging** - ELK Stack
6. **Message Queue** - RabbitMQ ou Kafka
7. **Redis Cache** - Cache compartilhado
8. **Kubernetes** - Orquestração de containers

---

**Arquitetura implementada em:** Dezembro 2025  
**Status:** ✅ Funcional e pronto para produção

