# 🏗️ ARQUITETURA DO PROJETO RECICLAI

## ❌ NÃO É MICROSERVIÇOS!

### O projeto utiliza **ARQUITETURA MONOLÍTICA** tradicional

**Por quê?**
- ✅ Um único backend Spring Boot
- ✅ Todas as funcionalidades em um único processo
- ✅ Banco de dados centralizado único
- ✅ Deploy em um único container

---

## 📐 Estrutura Real do Projeto

```
ReciclAI/
│
├── 🎯 CAMADA DE APRESENTAÇÃO (2 clientes)
│   ├── webApp/           → Frontend Web (Kotlin/JS)
│   └── androidApp/       → App Android (Jetpack Compose)
│
├── 🔧 CAMADA DE LÓGICA (Monolito)
│   └── backend/          → API REST única Spring Boot
│       ├── controllers/  → Endpoints REST
│       ├── services/     → Lógica de negócio
│       ├── repositories/ → Acesso a dados
│       └── entities/     → Modelos JPA
│
├── 🗄️ CAMADA DE DADOS
│   └── database/         → PostgreSQL único
│       └── init.sql      → Scripts iniciais
│
└── 🔄 CÓDIGO COMPARTILHADO
    └── shared/           → DTOs e modelos comuns
```

---

## 🆚 Diferença: Microserviços vs Monolito

### **Se fosse MICROSERVIÇOS, teria:**
```
❌ auth-service/       → Serviço de autenticação
❌ points-service/     → Serviço de pontos de coleta
❌ content-service/    → Serviço de conteúdo
❌ user-service/       → Serviço de usuários
❌ notification-service/ → Serviço de notificações
❌ API Gateway         → Gateway único de entrada
```

### **Mas o projeto TEM (Monolito):**
```
✅ backend/            → UM ÚNICO serviço com tudo
   ├── AuthController
   ├── PointsController
   ├── ContentController
   └── UsersController
```

---

## ✅ Vantagens do Monolito para este projeto

1. **Simplicidade de deploy** - Um container só
2. **Mais fácil de desenvolver** - Tudo em um lugar
3. **Menos overhead** - Sem comunicação entre serviços
4. **Ideal para projetos acadêmicos** - Foco no aprendizado
5. **Mais barato** - Menos recursos necessários

---

## 🎓 Para a Apresentação

**Diga ao professor:**

> "O ReciclAI utiliza **arquitetura monolítica** com separação em camadas (Presentation, Business, Data). Optamos por não usar microserviços porque:
> 
> 1. O escopo do projeto não justifica a complexidade
> 2. É mais adequado para um TCC acadêmico
> 3. Facilita o entendimento da arquitetura completa
> 4. Ainda assim, mantemos boas práticas de separação de responsabilidades"

---

## 📊 Comparação de Complexidade

| Aspecto | Monolito (Este projeto) | Microserviços |
|---------|------------------------|---------------|
| **Número de containers** | 3 (frontend, backend, db) | 8+ (cada serviço) |
| **Complexidade** | ⭐⭐ Baixa | ⭐⭐⭐⭐⭐ Alta |
| **Deploy** | Simples | Complexo (Kubernetes) |
| **Comunicação** | Direta | REST/gRPC entre serviços |
| **Banco de dados** | 1 centralizado | Múltiplos (por serviço) |
| **Adequação acadêmica** | ✅ Perfeito | ❌ Complexo demais |

---

**Conclusão:** O projeto está **corretamente estruturado como monolito**, o que é a escolha ideal para um TCC! 🎓

