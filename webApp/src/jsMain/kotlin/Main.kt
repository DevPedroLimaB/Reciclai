import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.*
import org.w3c.dom.*

var currentUser: User? = null

// Flag para evitar múltiplos logins simultâneos
var isLoggingIn = false

fun main() {
    window.onload = {
        renderApp()
    }
}

fun renderApp() {
    val root = document.getElementById("root") as? HTMLDivElement ?: return
    root.innerHTML = ""

    root.append {
        div {
            style = """
                background: linear-gradient(135deg, #2E7D32, #4CAF50);
                min-height: 100vh;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                color: white;
                font-family: Arial, sans-serif;
                padding: 20px;
            """.trimIndent()

            div {
                style = """
                    width: 120px;
                    height: 120px;
                    background: white;
                    border-radius: 60px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    font-size: 64px;
                    box-shadow: 0 8px 24px rgba(0,0,0,0.2);
                    margin-bottom: 32px;
                """.trimIndent()
                +"♻️"
            }

            h1 {
                style = "font-size: 48px; margin-bottom: 16px; text-align: center;"
                +"ReciclAI"
            }

            p {
                style = "font-size: 20px; text-align: center; max-width: 600px; margin-bottom: 40px;"
                +"Transforme o mundo, uma reciclagem por vez"
            }

            div {
                style = "display: flex; gap: 20px; flex-wrap: wrap; justify-content: center;"

                button {
                    style = """
                        padding: 16px 32px;
                        font-size: 18px;
                        font-weight: bold;
                        background: white;
                        color: #2E7D32;
                        border: none;
                        border-radius: 12px;
                        cursor: pointer;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.2);
                    """.trimIndent()
                    +"🚀 Começar"

                    onClickFunction = {
                        showLoginForm()
                    }
                }

                button {
                    style = """
                        padding: 16px 32px;
                        font-size: 18px;
                        font-weight: bold;
                        background: rgba(255,255,255,0.2);
                        color: white;
                        border: 2px solid white;
                        border-radius: 12px;
                        cursor: pointer;
                    """.trimIndent()
                    +"📚 Sobre o Projeto"

                    onClickFunction = {
                        showAbout()
                    }
                }
            }

            div {
                style = """
                    margin-top: 60px;
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                    gap: 20px;
                    max-width: 800px;
                """.trimIndent()

                featureCard("🌍", "Ajude o Planeta", "Contribua para um futuro sustentável")
                featureCard("📍", "Encontre Pontos", "Localize pontos de coleta próximos")
                featureCard("🏆", "Ganhe Recompensas", "Acumule pontos e ganhe prêmios")
            }
        }
    }
}

fun TagConsumer<HTMLElement>.featureCard(icon: String, title: String, description: String) {
    div {
        style = """
            background: rgba(255,255,255,0.15);
            padding: 24px;
            border-radius: 16px;
            text-align: center;
            backdrop-filter: blur(10px);
        """.trimIndent()

        div {
            style = "font-size: 48px; margin-bottom: 12px;"
            +icon
        }

        h3 {
            style = "font-size: 18px; font-weight: bold; margin-bottom: 8px;"
            +title
        }

        p {
            style = "font-size: 14px; opacity: 0.9;"
            +description
        }
    }
}

fun showLoginForm() {
    val root = document.getElementById("root") as? HTMLDivElement ?: return
    root.innerHTML = ""

    root.append {
        div {
            style = """
                background: linear-gradient(135deg, #2E7D32, #4CAF50);
                min-height: 100vh;
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: center;
                padding: 20px;
                font-family: Arial, sans-serif;
            """.trimIndent()

            div {
                style = """
                    background: white;
                    padding: 40px;
                    border-radius: 24px;
                    box-shadow: 0 8px 24px rgba(0,0,0,0.2);
                    max-width: 400px;
                    width: 100%;
                """.trimIndent()

                h1 {
                    style = "color: #2E7D32; font-size: 32px; margin-bottom: 24px; text-align: center;"
                    +"🔐 Login"
                }

                div {
                    style = "margin-bottom: 20px;"

                    label {
                        style = "display: block; font-weight: bold; color: #2E7D32; margin-bottom: 8px;"
                        +"Email"
                    }

                    input(type = InputType.email) {
                        id = "email"
                        placeholder = "seu@email.com"
                        style = """
                            width: 100%;
                            padding: 14px;
                            border: 2px solid #E8F5E8;
                            border-radius: 12px;
                            font-size: 16px;
                            box-sizing: border-box;
                        """.trimIndent()
                    }
                }

                div {
                    style = "margin-bottom: 24px;"

                    label {
                        style = "display: block; font-weight: bold; color: #2E7D32; margin-bottom: 8px;"
                        +"Senha"
                    }

                    input(type = InputType.password) {
                        id = "password"
                        placeholder = "••••••••"
                        style = """
                            width: 100%;
                            padding: 14px;
                            border: 2px solid #E8F5E8;
                            border-radius: 12px;
                            font-size: 16px;
                            box-sizing: border-box;
                        """.trimIndent()
                    }
                }

                button {
                    id = "login-btn"
                    style = """
                        width: 100%;
                        padding: 16px;
                        font-size: 18px;
                        font-weight: bold;
                        background: #2E7D32;
                        color: white;
                        border: none;
                        border-radius: 12px;
                        cursor: pointer;
                        box-shadow: 0 4px 12px rgba(46, 125, 50, 0.3);
                    """.trimIndent()
                    +"Entrar"

                    onClickFunction = {
                        handleLogin()
                    }
                }

                // Botão Cadastre-se
                button {
                    style = """
                        width: 100%;
                        padding: 16px;
                        font-size: 18px;
                        font-weight: bold;
                        background: transparent;
                        color: #2E7D32;
                        border: 2px solid #2E7D32;
                        border-radius: 12px;
                        cursor: pointer;
                        margin-top: 12px;
                        transition: all 0.3s;
                    """.trimIndent()
                    +"✨ Cadastre-se"

                    onClickFunction = {
                        val root = document.getElementById("root") as? HTMLDivElement
                        root?.let { renderRegisterScreen(it) }
                    }

                    onMouseOverFunction = {
                        val btn = it.target as HTMLButtonElement
                        btn.style.background = "#2E7D32"
                        btn.style.color = "white"
                    }
                    onMouseOutFunction = {
                        val btn = it.target as HTMLButtonElement
                        btn.style.background = "transparent"
                        btn.style.color = "#2E7D32"
                    }
                }

                // Botão Entrar como Convidado
                button {
                    style = """
                        width: 100%;
                        padding: 14px;
                        font-size: 16px;
                        font-weight: 600;
                        background: rgba(46, 125, 50, 0.1);
                        color: #2E7D32;
                        border: 2px solid transparent;
                        border-radius: 12px;
                        cursor: pointer;
                        margin-top: 12px;
                    """.trimIndent()
                    +"🚀 Entrar como Convidado"

                    onClickFunction = {
                        console.log("Entrando como convidado...")
                        // Criar usuário convidado
                        currentUser = User(
                            id = 0L,
                            name = "Convidado",
                            email = "convidado@reciclai.com",
                            points = 0
                        )
                        val root = document.getElementById("root") as? HTMLDivElement
                        if (root != null) {
                            renderMapScreen(root)
                        }
                    }
                }
            }
        }
    }
}

fun handleLogin() {
    // Prevenir múltiplas execuções simultâneas
    if (isLoggingIn) {
        console.log("Login já em andamento, ignorando...")
        return
    }

    val email = (document.getElementById("email") as? HTMLInputElement)?.value ?: ""
    val password = (document.getElementById("password") as? HTMLInputElement)?.value ?: ""
    val loginBtn = document.getElementById("login-btn") as? HTMLButtonElement

    if (email.isBlank() || password.isBlank()) {
        window.alert("❌ Por favor, preencha todos os campos!")
        return
    }

    // Marcar que está fazendo login
    isLoggingIn = true

    // Mostrar loading
    loginBtn?.innerHTML = "⏳ Entrando..."
    loginBtn?.disabled = true

    console.log("=== INICIANDO LOGIN ===")
    console.log("Email:", email)
    console.log("Auth Service URL:", ApiConfig.AUTH_SERVICE_URL)

    // Timeout de segurança - se demorar mais de 10 segundos, reseta
    val timeoutId = window.setTimeout({
        if (isLoggingIn) {
            console.error("TIMEOUT: Login demorou mais de 10 segundos!")
            isLoggingIn = false
            loginBtn?.innerHTML = "Entrar"
            loginBtn?.disabled = false
            window.alert("⏱️ Tempo esgotado!\n\nO backend não respondeu em 10 segundos.\n\nVerifique se o Docker está rodando:\ndocker ps")
        }
    }, 10000)

    // Tentar login com a API
    launchAsync {
        try {
            console.log("Chamando AuthRepository.login...")
            val result = AuthRepository.login(email, password)

            // Cancelar timeout
            window.clearTimeout(timeoutId)

            result.onSuccess { response ->
                console.log("✅ Login bem-sucedido!")
                console.log("Usuário:", response.user.name)

                currentUser = response.user
                isLoggingIn = false

                // Navegar para o mapa
                val root = document.getElementById("root") as? HTMLDivElement
                if (root != null) {
                    console.log("Navegando para MapScreen...")
                    renderMapScreen(root)
                } else {
                    console.error("❌ Elemento root não encontrado!")
                    loginBtn?.innerHTML = "Entrar"
                    loginBtn?.disabled = false
                    window.alert("Erro interno: Elemento root não encontrado!")
                }
            }.onFailure { error ->
                console.error("❌ Erro no login:", error)

                isLoggingIn = false
                loginBtn?.innerHTML = "Entrar"
                loginBtn?.disabled = false

                val errorMsg = error.message ?: "Erro desconhecido"
                window.alert("❌ Falha no login!\n\n$errorMsg\n\n🔍 Possíveis causas:\n• Backend offline (verifique: docker ps)\n• Senha incorreta\n• Email não existe\n\n✅ Credenciais de teste:\nEmail: teste@reciclai.com\nSenha: senha123")
            }
        } catch (e: Exception) {
            console.error("❌ EXCEÇÃO durante login:", e)
            console.error("Stack:", e.stackTraceToString())

            // Cancelar timeout
            window.clearTimeout(timeoutId)

            isLoggingIn = false
            loginBtn?.innerHTML = "Entrar"
            loginBtn?.disabled = false

            window.alert("❌ Erro crítico!\n\n${e.message}\n\n⚠️ O backend provavelmente está OFFLINE!\n\nExecute:\n1. docker-compose ps\n2. docker logs reciclai_backend")
        }
    }
}

fun showDashboard(screen: String = "map") {
    val root = document.getElementById("root") as? HTMLDivElement ?: return
    when (screen) {
        "map" -> renderMapScreen(root)
        "profile" -> renderProfileScreen(root)
        "achievements" -> renderAchievementsScreen(root)
        "history" -> renderHistoryScreen(root)
        "settings" -> renderSettingsScreen(root)
        "about" -> renderAboutScreen(root)
        else -> renderMapScreen(root)
    }
}

fun TagConsumer<HTMLElement>.statsCard(icon: String, value: String, label: String) {
    div {
        style = """
            background: white;
            padding: 24px;
            border-radius: 16px;
            text-align: center;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        """.trimIndent()

        div {
            style = "font-size: 40px; margin-bottom: 12px;"
            +icon
        }

        div {
            style = "font-size: 32px; font-weight: bold; color: #2E7D32; margin-bottom: 8px;"
            +value
        }

        div {
            style = "font-size: 14px; color: #666;"
            +label
        }
    }
}

fun TagConsumer<HTMLElement>.pointCard(name: String, address: String, materials: String) {
    div {
        style = """
            background: white;
            padding: 20px;
            border-radius: 16px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
            transition: transform 0.2s;
            cursor: pointer;
        """.trimIndent()

        onMouseOverFunction = {
            (it.target as? HTMLElement)?.style?.transform = "translateY(-4px)"
        }

        onMouseOutFunction = {
            (it.target as? HTMLElement)?.style?.transform = "translateY(0)"
        }

        h4 {
            style = "color: #2E7D32; font-size: 18px; margin-bottom: 8px;"
            +"📍 $name"
        }

        p {
            style = "color: #666; font-size: 14px; margin-bottom: 12px;"
            +address
        }

        p {
            style = "color: #2E7D32; font-size: 13px; font-weight: bold;"
            +"Aceita: $materials"
        }
    }
}

fun showAbout() {
    val root = document.getElementById("root") as? HTMLDivElement ?: return
    root.innerHTML = ""

    root.append {
        div {
            style = """
                background: linear-gradient(135deg, #2E7D32, #4CAF50);
                min-height: 100vh;
                padding: 40px 20px;
                color: white;
                font-family: Arial, sans-serif;
            """.trimIndent()

            div {
                style = "max-width: 800px; margin: 0 auto;"

                h1 {
                    style = "font-size: 48px; margin-bottom: 24px; text-align: center;"
                    +"📚 Sobre o ReciclAI"
                }

                div {
                    style = """
                        background: rgba(255,255,255,0.15);
                        padding: 32px;
                        border-radius: 24px;
                        backdrop-filter: blur(10px);
                        margin-bottom: 20px;
                    """.trimIndent()

                    p {
                        style = "font-size: 18px; line-height: 1.8; margin-bottom: 16px;"
                        +"O ReciclAI é uma plataforma completa de reciclagem que conecta pessoas a pontos de coleta, promove educação ambiental e recompensa ações sustentáveis."
                    }

                    h3 {
                        style = "font-size: 24px; margin: 24px 0 16px;"
                        +"✨ Funcionalidades"
                    }

                    ul {
                        style = "font-size: 16px; line-height: 2;"
                        li { +"🗺️ Mapa interativo com pontos de reciclagem" }
                        li { +"📱 Aplicativo Android nativo" }
                        li { +"🌐 Versão web responsiva" }
                        li { +"🗄️ Banco de dados PostgreSQL real" }
                        li { +"🔐 Autenticação JWT segura" }
                        li { +"🚀 API REST completa (Spring Boot)" }
                        li { +"🐳 Deploy com Docker" }
                    }
                }

                button {
                    style = """
                        padding: 16px 32px;
                        font-size: 18px;
                        font-weight: bold;
                        background: white;
                        color: #2E7D32;
                        border: none;
                        border-radius: 12px;
                        cursor: pointer;
                        display: block;
                        margin: 0 auto;
                    """.trimIndent()
                    +"← Voltar ao Início"

                    onClickFunction = {
                        renderApp()
                    }
                }
            }
        }
    }
}
