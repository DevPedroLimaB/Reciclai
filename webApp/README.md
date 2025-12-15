# 🌱 Reciclai Web App - Kotlin/JS

## 📋 Como Executar o Site

### Opção 1: Modo Desenvolvimento (Recomendado - Hot Reload)
```cmd
cd "C:\Users\Pedro Lima\Reciclai"
gradlew.bat :webApp:jsBrowserDevelopmentRun
```
✅ Compila automaticamente
✅ Abre no navegador
✅ Hot reload ao salvar

### Opção 2: Build e Deploy
```cmd
cd "C:\Users\Pedro Lima\Reciclai"
gradlew.bat :webApp:jsBrowserProductionWebpack
```
Depois acesse: `webApp\build\dist\js\productionExecutable\index.html`

### Opção 3: Servidor Python (Build Manual)
```cmd
# 1. Compilar primeiro
cd "C:\Users\Pedro Lima\Reciclai"
gradlew.bat :webApp:jsBrowserDevelopmentWebpack

# 2. Ir para pasta compilada
cd webApp\build\dist\js\developmentExecutable

# 3. Servir com Python
python -m http.server 8000
```
Depois abra: http://localhost:8000

## 🎨 Design Idêntico ao App Android

- ✅ Cores verde sustentável (#2E7D32, #4CAF50, etc)
- ✅ Gradientes idênticos
- ✅ Layout responsivo
- ✅ Navegação inferior
- ✅ Todas as telas do app

## 📱 Telas Implementadas

1. **Welcome** - Tela de boas-vindas
2. **Login** - Autenticação
3. **Register** - Cadastro
4. **Content** - Conteúdo educativo
5. **Map** - Pontos de coleta
6. **Profile** - Perfil do usuário

## 🚀 URL Padrão
http://localhost:8080

---
**Desenvolvido com Kotlin/JS + kotlinx-html**

