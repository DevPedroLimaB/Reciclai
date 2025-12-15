# 🚀 ReciclAI - Guia Rápido

## ⚡ Início Rápido (3 comandos)

### 1️⃣ Limpar arquivos inúteis
```bash
LIMPAR_ARQUIVOS_INUTEIS.bat
```

### 2️⃣ Compilar o projeto
```bash
COMPILAR.bat
```

### 3️⃣ Iniciar com Docker
```bash
INICIAR_DOCKER.bat
```

---

## 🌐 Acessar o Sistema

Após executar os comandos acima:

- **Frontend Web**: http://localhost:3000
- **Backend API**: http://localhost:8080
- **Banco de dados**: localhost:5432

---

## 🔐 Credenciais de Teste

**IMPORTANTE: Use estas credenciais para fazer login!**

```
Email: teste@reciclai.com
Senha: senha123
```

**Outras contas disponíveis:**
```
Email: admin@reciclai.com
Senha: senha123

Email: joao@reciclai.com
Senha: senha123
```

Todos os usuários têm a mesma senha: **senha123**

---

## 🛠️ Scripts Disponíveis

| Script | Função |
|--------|--------|
| `COMPILAR.bat` | Compila todo o projeto (shared + webapp + backend) |
| `TESTAR_FRONTEND.bat` | Testa apenas o frontend em modo desenvolvimento |
| `INICIAR_DOCKER.bat` | Sobe todos os containers (frontend + backend + database) |
| `PARAR_DOCKER.bat` | Para todos os containers |
| `LIMPAR_ARQUIVOS_INUTEIS.bat` | Remove arquivos temporários |
| `LIMPAR_CACHE_COMPLETO.bat` | Limpa cache do Gradle |

---

## ❌ Problemas Comuns

### Loop infinito no login
**RESOLVIDO!** O código agora tem proteção contra múltiplos cliques.

### Kotlin daemon crash
**RESOLVIDO!** Aumentamos a memória do daemon para 2GB.

### Backend não responde
Verifique se o Docker está rodando:
```bash
docker ps
```

---

## 📱 Tecnologias

- **Frontend**: Kotlin/JS + HTML5
- **Backend**: Spring Boot (Kotlin)
- **Database**: PostgreSQL
- **Deploy**: Docker + Docker Compose

---

## 📞 Suporte

Se encontrar problemas, verifique os logs:
- Frontend: Console do navegador (F12)
- Backend: `docker logs reciclai-backend`
- Database: `docker logs reciclai-db`
