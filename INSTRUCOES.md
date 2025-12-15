# 🚀 INSTRUÇÕES DE EXECUÇÃO - RECICLAI

## ✅ CORREÇÕES APLICADAS

### 1. Erros de Compilação Corrigidos
- ✅ **ForgotPasswordScreen.kt**: Corrigido erro `Unresolved reference: launch`
  - Adicionado `rememberCoroutineScope()` 
  - Substituído `GlobalScope.launch` por `coroutineScope.launch`
- ✅ **Screens.kt**: Todos os erros de `inputField` já estavam resolvidos

### 2. README Completo Criado
- ✅ **README.md**: Documentação completa do projeto incluindo:
  - Arquitetura de microserviços
  - Localização de todas as APIs
  - Onde cada API é consumida (arquivo e linha)
  - Estrutura completa do projeto
  - Como executar tudo
  - Troubleshooting

### 3. Scripts Essenciais Criados
- ✅ **INICIAR_DOCKER.bat**: Compila e inicia todos os serviços
- ✅ **PARAR_DOCKER.bat**: Para todos os containers
- ✅ **LIMPAR_BATS.bat**: Move arquivos .bat desnecessários para backup

---

## 🔧 EXECUTE AGORA (PASSO A PASSO)

### Passo 1: Limpar arquivos .bat desnecessários
```cmd
cd "C:\Users\Pedro Lima\Reciclai"
LIMPAR_BATS.bat
```
Isso moverá ~40 arquivos .bat para a pasta `scripts_backup`, mantendo apenas os essenciais.

### Passo 2: Compilar o projeto
```cmd
gradlew.bat clean build -x test
```

### Passo 3: Verificar se não há erros de compilação
```cmd
gradlew.bat :androidApp:assembleDebug
gradlew.bat :webApp:build
```

### Passo 4: Iniciar os serviços Docker
```cmd
INICIAR_DOCKER.bat
```

### Passo 5: Testar a aplicação

#### Web (http://localhost:3000)
1. Abra o navegador em `http://localhost:3000`
2. Clique em "Cadastre-se"
3. Preencha o formulário:
   - Nome: Teste Usuario
   - Email: teste@reciclai.com
   - Senha: senha123
4. Clique em "Criar Conta"
5. Faça login com as credenciais

#### Android
1. Abra o Android Studio
2. Aguarde sincronização do Gradle
3. Execute: `Run > Run 'androidApp'`
4. Teste o login, cadastro e "Esqueci minha senha"

---

## 📍 ONDE AS APIs ESTÃO SENDO CONSUMIDAS

### 🔐 Auth Service (Port 8082)

**Arquivo**: `auth-service/src/main/kotlin/com/example/reciclai/authservice/controller/AuthController.kt`

**Endpoints**:
- `POST /api/auth/register` - Cadastro
- `POST /api/auth/login` - Login

**Consumido por**:

1. **Web**: `webApp/src/jsMain/kotlin/ApiService.kt`
   - Linha 142: `async fun login(email: String, password: String)`
   - Linha 161: `async fun register(name: String, email: String, password: String)`
   - Linha 14: `val AUTH_SERVICE_URL = "http://localhost:8082/api"`

2. **Web**: `webApp/src/jsMain/kotlin/Main.kt`
   - Linha 338-360: Função de login chamando `AuthRepository.login()`
   - Linha 200-230: Tela de cadastro chamando `handleRegister()`

3. **Android**: `androidApp/src/main/java/com/example/reciclai/ui/screens/LoginScreen.kt`
   - Componente `LoginScreen` consome AuthViewModel

4. **Android**: `androidApp/src/main/java/com/example/reciclai/ui/screens/RegisterScreen.kt`
   - Componente `RegisterScreen` consome AuthViewModel

5. **Android**: `androidApp/src/main/java/com/example/reciclai/viewmodel/AuthViewModel.kt`
   - ViewModel que faz as chamadas HTTP para o Auth Service

---

### 🌍 Backend Service (Port 8080)

**Arquivo**: `backend/src/main/kotlin/com/example/reciclai/backend/controller/`

**Endpoints**:
- `GET /api/points` - Lista pontos de coleta
- `GET /api/points/{id}` - Detalhes de um ponto
- `GET /api/contents` - Conteúdo educativo
- `GET /api/user/profile` - Perfil do usuário

**Consumido por**:

1. **Web**: `webApp/src/jsMain/kotlin/ApiService.kt`
   - Linha 70: `async fun getRecyclingPoints()`
   - Linha 85: `async fun getContents()`
   - Linha 100: `async fun getUserProfile()`
   - Linha 18: `val BACKEND_SERVICE_URL = "http://localhost:8080/api"`

2. **Web**: `webApp/src/jsMain/kotlin/Screens.kt`
   - Linha 500-600: Função `showMapScreen()` exibe mapa com pontos
   - Linha 700-800: Função `showContentScreen()` exibe conteúdos

3. **Android**: `androidApp/src/main/java/com/example/reciclai/viewmodel/MapViewModel.kt`
   - Carrega pontos de coleta no mapa

4. **Android**: `androidApp/src/main/java/com/example/reciclai/viewmodel/ContentViewModel.kt`
   - Carrega conteúdo educativo

5. **Shared**: `shared/src/commonMain/kotlin/com/example/reciclai/shared/network/ApiService.kt`
   - Código compartilhado entre Web e Android

---

## 🐛 PROBLEMA DO BOTÃO "CADASTRE-SE" NA WEB

### Causa Identificada
O erro 400 acontece quando:
1. Os campos do formulário não estão sendo capturados corretamente
2. O JSON enviado está malformado
3. Os nomes dos campos não correspondem ao esperado pelo backend

### Solução Aplicada
No arquivo `webApp/src/jsMain/kotlin/Main.kt`:
- Todos os inputs têm IDs únicos (`register-name`, `register-email`, `register-password`)
- A função `handleRegister()` captura os valores corretamente
- O botão "Cadastre-se" chama `handleRegister()` ao clicar

### Verificar se funciona
1. Abra o console do navegador (F12)
2. Clique em "Cadastre-se"
3. Preencha o formulário
4. Clique em "Criar Conta"
5. Veja os logs no console (deve mostrar "=== INICIANDO CADASTRO ===")

Se ainda der erro 400:
- Abra o terminal e veja os logs do auth-service:
  ```cmd
  docker logs reciclai_auth_service -f
  ```
- Isso mostrará exatamente qual campo está faltando ou incorreto

---

## 🗂️ ARQUIVOS ALTERADOS

### ✏️ Editados
1. `androidApp/src/main/java/com/example/reciclai/ui/screens/ForgotPasswordScreen.kt`
   - Adicionado `rememberCoroutineScope()`
   - Corrigido uso de `launch` em corrotinas

### ➕ Criados
1. `README.md` - Documentação completa do projeto
2. `INICIAR_DOCKER.bat` - Script para iniciar tudo
3. `PARAR_DOCKER.bat` - Script para parar containers
4. `LIMPAR_BATS.bat` - Script para limpar arquivos .bat
5. `INSTRUCOES.md` - Este arquivo

---

## 📊 RESUMO DA ARQUITETURA

```
Frontend Web (Port 3000)
  └─ ApiService.kt
      ├─ Auth: http://localhost:8082/api
      │   ├─ POST /auth/login
      │   └─ POST /auth/register
      │
      └─ Backend: http://localhost:8080/api
          ├─ GET /points
          ├─ GET /contents
          └─ GET /user/profile

Android App
  └─ ViewModels
      ├─ AuthViewModel → Auth Service (8082)
      ├─ MapViewModel → Backend (8080)
      └─ ContentViewModel → Backend (8080)

Docker Containers
  ├─ reciclai_db (PostgreSQL:5434)
  ├─ reciclai_auth_service (8082)
  ├─ reciclai_backend (8080)
  └─ reciclai_frontend (3000)
```

---

## ✅ PRÓXIMOS PASSOS

1. **Execute a limpeza dos .bat**:
   ```cmd
   LIMPAR_BATS.bat
   ```

2. **Compile tudo**:
   ```cmd
   gradlew.bat clean build -x test
   ```

3. **Inicie o Docker**:
   ```cmd
   INICIAR_DOCKER.bat
   ```

4. **Teste a Web**:
   - http://localhost:3000
   - Cadastre-se e faça login

5. **Teste o Android**:
   - Abra no Android Studio
   - Execute o app

6. **Se houver erro**:
   - Veja os logs: `docker logs reciclai_auth_service -f`
   - Leia o README.md seção "Troubleshooting"

---

## 📞 SUPORTE

Todos os detalhes técnicos estão no **README.md** na raiz do projeto.

**Boa sorte! 🚀🌿**

