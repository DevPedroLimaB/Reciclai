import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.*
import kotlinx.html.dom.append
import kotlinx.html.js.*
import org.w3c.dom.*

// ====== TELA REGISTRO ======
fun renderRegisterScreen(root: HTMLDivElement) {
    root.innerHTML = ""  // Limpar antes de renderizar

    root.append {
        div("register-screen") {
            style = """
                background: linear-gradient(135deg, #2E7D32, #4CAF50);
                min-height: 100vh;
                padding: 20px;
            """.trimIndent()

            // Botão voltar
            button {
                style = """
                    background: rgba(255,255,255,0.2);
                    border: none;
                    color: white;
                    font-size: 24px;
                    width: 40px;
                    height: 40px;
                    border-radius: 20px;
                    cursor: pointer;
                    margin-bottom: 20px;
                """.trimIndent()
                +"←"
                onClickFunction = { e ->
                    e.preventDefault()
                    showLoginForm()
                }
            }

            div {
                style = "max-width: 500px; margin: 20px auto; text-align: center;"

                // Logo
                div {
                    style = """
                        width: 80px;
                        height: 80px;
                        background: white;
                        border-radius: 40px;
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        font-size: 40px;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.2);
                    """.trimIndent()
                    +"♻️"
                }

                h1 {
                    style = "margin-top: 24px; color: white; font-size: 32px; font-weight: bold;"
                    +"Criar Conta"
                }

                p {
                    style = "margin-top: 8px; color: rgba(255,255,255,0.9);"
                    +"Junte-se à comunidade sustentável"
                }

                // Card formulário
                div {
                    style = """
                        background: white;
                        border-radius: 24px;
                        padding: 32px;
                        margin-top: 32px;
                        box-shadow: 0 8px 24px rgba(0,0,0,0.2);
                        text-align: left;
                    """.trimIndent()

                    div {
                        id = "error-message"
                        style = "display: none; background: #FFEBEE; color: #D32F2F; padding: 12px; border-radius: 8px; margin-bottom: 16px;"
                    }

                    // Nome
                    div {
                        style = "margin-bottom: 20px;"

                        label {
                            style = "display: block; font-weight: 600; color: #1B5E20; margin-bottom: 8px;"
                            +"Nome completo"
                        }

                        input {
                            id = "register-name"
                            type = InputType.text
                            placeholder = "Seu nome"
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

                    // Email
                    div {
                        style = "margin-bottom: 20px;"

                        label {
                            style = "display: block; font-weight: 600; color: #1B5E20; margin-bottom: 8px;"
                            +"E-mail"
                        }

                        input {
                            id = "register-email"
                            type = InputType.email
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

                    // Senha
                    div {
                        style = "margin-bottom: 20px;"

                        label {
                            style = "display: block; font-weight: 600; color: #1B5E20; margin-bottom: 8px;"
                            +"Senha"
                        }

                        input {
                            id = "register-password"
                            type = InputType.password
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

                    // Confirmar senha
                    div {
                        style = "margin-bottom: 20px;"

                        label {
                            style = "display: block; font-weight: 600; color: #1B5E20; margin-bottom: 8px;"
                            +"Confirmar senha"
                        }

                        input {
                            id = "register-confirm-password"
                            type = InputType.password
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

                    // Botão
                    button {
                        id = "register-btn"
                        style = """
                            width: 100%;
                            padding: 16px;
                            font-size: 18px;
                            font-weight: 600;
                            background: #2E7D32;
                            color: white;
                            border: none;
                            border-radius: 12px;
                            cursor: pointer;
                            margin-top: 24px;
                            transition: all 0.3s;
                        """.trimIndent()
                        +"✨ Criar Conta"

                        onClickFunction = { e ->
                            e.preventDefault()
                            handleRegister()
                        }

                        onMouseOverFunction = { e ->
                            (e.target as? HTMLButtonElement)?.style?.background = "#1B5E20"
                        }
                        onMouseOutFunction = { e ->
                            (e.target as? HTMLButtonElement)?.style?.background = "#2E7D32"
                        }
                    }

                    p {
                        style = "margin-top: 24px; text-align: center; color: #666;"
                        +"Já tem conta? "
                        span {
                            style = "color: #2E7D32; font-weight: 600; cursor: pointer;"
                            +"Faça login"
                            onClickFunction = { e ->
                                e.preventDefault()
                                showLoginForm()
                            }
                        }
                    }
                }
            }
        }
    }
}

fun handleRegister() {
    val name = (document.getElementById("register-name") as? HTMLInputElement)?.value ?: ""
    val email = (document.getElementById("register-email") as? HTMLInputElement)?.value ?: ""
    val password = (document.getElementById("register-password") as? HTMLInputElement)?.value ?: ""
    val confirmPassword = (document.getElementById("register-confirm-password") as? HTMLInputElement)?.value ?: ""
    val registerBtn = document.getElementById("register-btn") as? HTMLButtonElement

    console.log("🔥 handleRegister chamado!")
    console.log("Nome:", name)
    console.log("Email:", email)
    console.log("Password length:", password.length)

    if (name.isBlank() || email.isBlank() || password.isBlank()) {
        window.alert("❌ Preencha todos os campos")
        return
    }

    if (!email.contains("@")) {
        window.alert("❌ E-mail inválido")
        return
    }

    if (password.length < 6) {
        window.alert("❌ Senha deve ter no mínimo 6 caracteres")
        return
    }

    if (password != confirmPassword) {
        window.alert("❌ Senhas não coincidem")
        return
    }

    // Mostrar loading
    registerBtn?.innerHTML = "⏳ Criando conta..."
    registerBtn?.disabled = true

    // Chamar API de registro
    launchAsync {
        try {
            console.log("🔐 Iniciando cadastro...")
            console.log("Nome:", name)
            console.log("Email:", email)

            val result = AuthRepository.register(name, email, password)

            result.onSuccess { loginResponse ->
                console.log("✅ Cadastro realizado com sucesso!")
                console.log("Token recebido:", loginResponse.token)
                console.log("Usuário:", loginResponse.user)

                // Salvar usuário logado
                currentUser = loginResponse.user

                window.alert("✅ Conta criada com sucesso!\n\n👤 Bem-vindo, ${loginResponse.user.name}!\n📧 ${loginResponse.user.email}\n\nVocê já está logado!")

                // Redirecionar para o dashboard
                showDashboard("map")
            }

            result.onFailure { error ->
                console.error("❌ Erro no cadastro:", error)
                val errorMessage = error.message ?: "Erro desconhecido"

                registerBtn?.innerHTML = "✨ Criar Conta"
                registerBtn?.disabled = false

                if (errorMessage.contains("já existe") || errorMessage.contains("already exists")) {
                    window.alert("❌ Este e-mail já está cadastrado!\n\nTente fazer login ou use outro e-mail.")
                } else {
                    window.alert("❌ Erro ao criar conta:\n\n$errorMessage\n\nTente novamente.")
                }
            }
        } catch (e: Exception) {
            console.error("❌ Exceção no cadastro:", e)
            registerBtn?.innerHTML = "✨ Criar Conta"
            registerBtn?.disabled = false
            window.alert("❌ Erro inesperado ao criar conta.\n\nVerifique sua conexão e tente novamente.")
        }
    }
}

// ====== TELA DO MAPA (DASHBOARD PRINCIPAL) ======
fun renderMapScreen(root: HTMLDivElement) {
    root.innerHTML = ""

    root.append {
        div {
            style = """
                display: flex;
                flex-direction: column;
                height: 100vh;
                background: #f5f5f5;
            """.trimIndent()

            // Header
            div {
                style = """
                    background: linear-gradient(135deg, #2E7D32, #4CAF50);
                    padding: 16px 20px;
                    color: white;
                    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                """.trimIndent()

                div {
                    style = "display: flex; align-items: center; gap: 12px;"

                    div {
                        style = """
                            width: 40px;
                            height: 40px;
                            background: white;
                            border-radius: 20px;
                            display: flex;
                            align-items: center;
                            justify-content: center;
                            font-size: 24px;
                        """.trimIndent()
                        +"♻️"
                    }

                    div {
                        h2 {
                            style = "font-size: 24px; margin: 0;"
                            +"ReciclAI"
                        }
                        p {
                            style = "font-size: 12px; opacity: 0.9; margin: 0;"
                            +"Olá, ${currentUser?.name ?: "Usuário"}!"
                        }
                    }
                }

                div {
                    style = "display: flex; gap: 8px; align-items: center;"

                    // Pontos do usuário
                    div {
                        style = """
                            background: rgba(255,255,255,0.2);
                            padding: 8px 16px;
                            border-radius: 20px;
                            font-weight: bold;
                        """.trimIndent()
                        +"⭐ ${currentUser?.points ?: 0} pts"
                    }
                }
            }

            // Conteúdo principal com mapa
            div {
                style = """
                    flex: 1;
                    display: flex;
                    flex-direction: column;
                    overflow: hidden;
                """.trimIndent()

                // Barra de busca
                div {
                    style = """
                        padding: 16px;
                        background: white;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                    """.trimIndent()

                    input {
                        type = InputType.text
                        placeholder = "🔍 Buscar pontos de reciclagem..."
                        style = """
                            width: 100%;
                            padding: 12px 16px;
                            border: 2px solid #E0E0E0;
                            border-radius: 24px;
                            font-size: 16px;
                            outline: none;
                        """.trimIndent()
                    }
                }

                // Container do mapa
                div {
                    id = "map"
                    style = """
                        flex: 1;
                        position: relative;
                    """.trimIndent()
                }

                // Lista de pontos (colapsável)
                div {
                    id = "points-list"
                    style = """
                        background: white;
                        max-height: 40vh;
                        overflow-y: auto;
                        box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
                    """.trimIndent()

                    div {
                        style = """
                            padding: 16px;
                            border-bottom: 1px solid #E0E0E0;
                            font-weight: bold;
                            color: #2E7D32;
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                        """.trimIndent()

                        span { +"📍 Pontos Próximos" }
                        span {
                            id = "points-count"
                            style = "font-size: 14px; opacity: 0.7;"
                            +"Carregando..."
                        }
                    }

                    div {
                        id = "points-container"
                        style = "padding: 8px;"
                    }
                }
            }

            // Bottom Navigation
            div {
                style = """
                    background: white;
                    padding: 12px 8px 8px 8px;
                    box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    justify-content: space-around;
                """.trimIndent()

                navButton("🗺️", "Mapa", true) { showDashboard("map") }
                navButton("👤", "Perfil", false) { showDashboard("profile") }
            }
        }
    }

    // Inicializar mapa após renderização
    window.setTimeout({
        initMap()
        loadRecyclingPoints()
    }, 100)
}

fun TagConsumer<HTMLElement>.navButton(icon: String, label: String, active: Boolean, onClick: () -> Unit) {
    button {
        style = """
            flex: 1;
            background: ${if (active) "rgba(46, 125, 50, 0.1)" else "transparent"};
            border: none;
            padding: 12px;
            border-radius: 12px;
            cursor: pointer;
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 4px;
            transition: all 0.2s;
        """.trimIndent()

        div {
            style = "font-size: 24px;"
            +icon
        }
        div {
            style = """
                font-size: 12px;
                color: ${if (active) "#2E7D32" else "#666"};
                font-weight: ${if (active) "bold" else "normal"};
            """.trimIndent()
            +label
        }

        onClickFunction = { onClick() }
    }
}

// ====== TELA DE CONQUISTAS ======
fun renderAchievementsScreen(root: HTMLDivElement) {
    root.innerHTML = ""

    root.append {
        div {
            style = """
                display: flex;
                flex-direction: column;
                height: 100vh;
                background: #f5f5f5;
            """.trimIndent()

            // Header
            div {
                style = """
                    background: linear-gradient(135deg, #2E7D32, #4CAF50);
                    padding: 20px;
                    color: white;
                    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    align-items: center;
                    gap: 16px;
                """.trimIndent()

                button {
                    style = """
                        background: rgba(255,255,255,0.2);
                        border: none;
                        color: white;
                        font-size: 24px;
                        width: 40px;
                        height: 40px;
                        border-radius: 20px;
                        cursor: pointer;
                    """.trimIndent()
                    +"←"
                    onClickFunction = { showDashboard("profile") }
                }

                div {
                    h2 {
                        style = "font-size: 28px; margin: 0 0 4px 0;"
                        +"🎯 Minhas Conquistas"
                    }
                    p {
                        style = "opacity: 0.9; margin: 0;"
                        +"${currentUser?.points ?: 0} pontos acumulados"
                    }
                }
            }

            // Conquistas
            div {
                style = """
                    flex: 1;
                    overflow-y: auto;
                    padding: 16px;
                """.trimIndent()

                achievementCard("🌟", "Primeira Reciclagem", "Complete sua primeira reciclagem", true)
                achievementCard("🔥", "Em Chamas", "Recicle 5 vezes em uma semana", false)
                achievementCard("🏆", "Mestre da Reciclagem", "Alcance 1000 pontos", false)
                achievementCard("🌍", "Guardião do Planeta", "Recicle 100kg de material", false)
                achievementCard("💚", "Eco Warrior", "Use o app por 30 dias seguidos", false)
                achievementCard("⭐", "Colecionador", "Desbloqueie todas as conquistas", false)
            }

            // Bottom Navigation
            div {
                style = """
                    background: white;
                    padding: 12px 8px 8px 8px;
                    box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    justify-content: space-around;
                """.trimIndent()

                navButton("🗺️", "Mapa", false) { showDashboard("map") }
                navButton("👤", "Perfil", false) { showDashboard("profile") }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.achievementCard(icon: String, title: String, description: String, unlocked: Boolean) {
    div {
        style = """
            background: white;
            padding: 20px;
            border-radius: 12px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 16px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            opacity: ${if (unlocked) "1" else "0.5"};
        """.trimIndent()

        div {
            style = """
                font-size: 48px;
                width: 64px;
                height: 64px;
                display: flex;
                align-items: center;
                justify-content: center;
                background: ${if (unlocked) "rgba(46, 125, 50, 0.1)" else "rgba(150, 150, 150, 0.1)"};
                border-radius: 16px;
            """.trimIndent()
            +icon
        }

        div {
            style = "flex: 1;"

            div {
                style = "font-weight: bold; color: #333; margin-bottom: 4px; font-size: 16px;"
                +title
            }
            div {
                style = "font-size: 13px; color: #666;"
                +description
            }
            if (unlocked) {
                div {
                    style = "font-size: 12px; color: #2E7D32; margin-top: 4px; font-weight: bold;"
                    +"✓ Desbloqueada"
                }
            }
        }
    }
}

// ====== TELA DE HISTÓRICO ======
fun renderHistoryScreen(root: HTMLDivElement) {
    root.innerHTML = ""

    root.append {
        div {
            style = """
                display: flex;
                flex-direction: column;
                height: 100vh;
                background: #f5f5f5;
            """.trimIndent()

            // Header
            div {
                style = """
                    background: linear-gradient(135deg, #2E7D32, #4CAF50);
                    padding: 20px;
                    color: white;
                    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    align-items: center;
                    gap: 16px;
                """.trimIndent()

                button {
                    style = """
                        background: rgba(255,255,255,0.2);
                        border: none;
                        color: white;
                        font-size: 24px;
                        width: 40px;
                        height: 40px;
                        border-radius: 20px;
                        cursor: pointer;
                    """.trimIndent()
                    +"←"
                    onClickFunction = { showDashboard("profile") }
                }

                div {
                    h2 {
                        style = "font-size: 28px; margin: 0 0 4px 0;"
                        +"📊 Histórico de Reciclagem"
                    }
                    p {
                        style = "opacity: 0.9; margin: 0;"
                        +"Suas últimas atividades"
                    }
                }
            }

            // Histórico
            div {
                style = """
                    flex: 1;
                    overflow-y: auto;
                    padding: 16px;
                """.trimIndent()

                historyItem("📦", "Papel e Papelão", "2.5 kg reciclados", "Há 2 dias", "+25 pontos")
                historyItem("🥤", "Plástico", "1.8 kg reciclados", "Há 5 dias", "+18 pontos")
                historyItem("🍾", "Vidro", "3.2 kg reciclados", "Há 1 semana", "+32 pontos")
                historyItem("🔩", "Metal", "0.9 kg reciclados", "Há 2 semanas", "+15 pontos")

                // Mensagem se vazio
                div {
                    style = """
                        text-align: center;
                        padding: 40px 20px;
                        color: #999;
                    """.trimIndent()

                    div {
                        style = "font-size: 64px; margin-bottom: 16px;"
                        +"📊"
                    }
                    div {
                        style = "font-size: 16px;"
                        +"Continue reciclando para ver seu histórico crescer!"
                    }
                }
            }

            // Bottom Navigation
            div {
                style = """
                    background: white;
                    padding: 12px 8px 8px 8px;
                    box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    justify-content: space-around;
                """.trimIndent()

                navButton("🗺️", "Mapa", false) { showDashboard("map") }
                navButton("👤", "Perfil", false) { showDashboard("profile") }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.historyItem(icon: String, material: String, amount: String, date: String, points: String) {
    div {
        style = """
            background: white;
            padding: 16px;
            border-radius: 12px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 16px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        """.trimIndent()

        div {
            style = """
                font-size: 40px;
                width: 56px;
                height: 56px;
                display: flex;
                align-items: center;
                justify-content: center;
                background: rgba(46, 125, 50, 0.1);
                border-radius: 12px;
            """.trimIndent()
            +icon
        }

        div {
            style = "flex: 1;"

            div {
                style = "font-weight: bold; color: #333; margin-bottom: 4px;"
                +material
            }
            div {
                style = "font-size: 13px; color: #666;"
                +amount
            }
            div {
                style = "font-size: 12px; color: #999; margin-top: 4px;"
                +date
            }
        }

        div {
            style = "font-weight: bold; color: #2E7D32;"
            +points
        }
    }
}

// ====== TELA DE CONFIGURAÇÕES ======
fun renderSettingsScreen(root: HTMLDivElement) {
    root.innerHTML = ""

    root.append {
        div {
            style = """
                display: flex;
                flex-direction: column;
                height: 100vh;
                background: #f5f5f5;
            """.trimIndent()

            // Header
            div {
                style = """
                    background: linear-gradient(135deg, #2E7D32, #4CAF50);
                    padding: 20px;
                    color: white;
                    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    align-items: center;
                    gap: 16px;
                """.trimIndent()

                button {
                    style = """
                        background: rgba(255,255,255,0.2);
                        border: none;
                        color: white;
                        font-size: 24px;
                        width: 40px;
                        height: 40px;
                        border-radius: 20px;
                        cursor: pointer;
                    """.trimIndent()
                    +"←"
                    onClickFunction = { showDashboard("profile") }
                }

                h2 {
                    style = "font-size: 28px; margin: 0;"
                    +"⚙️ Configurações"
                }
            }

            // Configurações
            div {
                style = """
                    flex: 1;
                    overflow-y: auto;
                    padding: 16px;
                """.trimIndent()

                settingsSection("Notificações")
                settingsToggle("🔔", "Notificações Push", "Receber alertas sobre pontos próximos", true)
                settingsToggle("📧", "Email", "Receber newsletter semanal", false)

                settingsSection("Privacidade")
                settingsToggle("📍", "Localização", "Permitir acesso à localização", true)
                settingsToggle("👥", "Perfil Público", "Mostrar no ranking público", false)

                settingsSection("Preferências")
                settingsOption("🌍", "Idioma", "Português (Brasil)")
                settingsOption("🎨", "Tema", "Claro")
                settingsOption("📏", "Unidade", "Quilogramas (kg)")

                settingsSection("Conta")
                settingsButton("🔑", "Alterar Senha", "#FF9800") {
                    window.alert("Funcionalidade em desenvolvimento")
                }
                settingsButton("🗑️", "Excluir Conta", "#F44336") {
                    if (window.confirm("Tem certeza que deseja excluir sua conta? Esta ação não pode ser desfeita.")) {
                        window.alert("Conta excluída com sucesso")
                        currentUser = null
                        ApiService.clearAuthToken()
                        renderApp()
                    }
                }
            }

            // Bottom Navigation
            div {
                style = """
                    background: white;
                    padding: 12px 8px 8px 8px;
                    box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    justify-content: space-around;
                """.trimIndent()

                navButton("🗺️", "Mapa", false) { showDashboard("map") }
                navButton("👤", "Perfil", false) { showDashboard("profile") }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.settingsSection(title: String) {
    div {
        style = """
            font-weight: bold;
            color: #666;
            font-size: 14px;
            margin: 24px 0 12px 8px;
            text-transform: uppercase;
        """.trimIndent()
        +title
    }
}

fun TagConsumer<HTMLElement>.settingsToggle(icon: String, title: String, description: String, enabled: Boolean) {
    div {
        style = """
            background: white;
            padding: 16px;
            border-radius: 12px;
            margin-bottom: 8px;
            display: flex;
            align-items: center;
            gap: 16px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        """.trimIndent()

        div {
            style = "font-size: 24px;"
            +icon
        }

        div {
            style = "flex: 1;"

            div {
                style = "font-weight: bold; color: #333; margin-bottom: 4px;"
                +title
            }
            div {
                style = "font-size: 12px; color: #666;"
                +description
            }
        }

        div {
            style = """
                width: 50px;
                height: 28px;
                background: ${if (enabled) "#2E7D32" else "#ccc"};
                border-radius: 14px;
                position: relative;
                cursor: pointer;
                transition: background 0.3s;
            """.trimIndent()

            div {
                style = """
                    width: 24px;
                    height: 24px;
                    background: white;
                    border-radius: 12px;
                    position: absolute;
                    top: 2px;
                    left: ${if (enabled) "24px" else "2px"};
                    transition: left 0.3s;
                """.trimIndent()
            }
        }
    }
}

fun TagConsumer<HTMLElement>.settingsOption(icon: String, title: String, value: String) {
    div {
        style = """
            background: white;
            padding: 16px;
            border-radius: 12px;
            margin-bottom: 8px;
            display: flex;
            align-items: center;
            gap: 16px;
            cursor: pointer;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        """.trimIndent()

        div {
            style = "font-size: 24px;"
            +icon
        }

        div {
            style = "flex: 1;"

            div {
                style = "font-weight: bold; color: #333; margin-bottom: 4px;"
                +title
            }
            div {
                style = "font-size: 13px; color: #666;"
                +value
            }
        }

        div {
            style = "color: #999; font-size: 20px;"
            +"›"
        }
    }
}

fun TagConsumer<HTMLElement>.settingsButton(icon: String, title: String, color: String, onClick: () -> Unit) {
    div {
        style = """
            background: white;
            padding: 16px;
            border-radius: 12px;
            margin-bottom: 8px;
            display: flex;
            align-items: center;
            gap: 16px;
            cursor: pointer;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        """.trimIndent()

        onClickFunction = { onClick() }

        div {
            style = "font-size: 24px;"
            +icon
        }

        div {
            style = "flex: 1; font-weight: bold; color: $color;"
            +title
        }

        div {
            style = "color: #999; font-size: 20px;"
            +"›"
        }
    }
}

// ====== TELA SOBRE O APP ======
fun renderAboutScreen(root: HTMLDivElement) {
    root.innerHTML = ""

    root.append {
        div {
            style = """
                display: flex;
                flex-direction: column;
                height: 100vh;
                background: #f5f5f5;
            """.trimIndent()

            // Header
            div {
                style = """
                    background: linear-gradient(135deg, #2E7D32, #4CAF50);
                    padding: 20px;
                    color: white;
                    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    align-items: center;
                    gap: 16px;
                """.trimIndent()

                button {
                    style = """
                        background: rgba(255,255,255,0.2);
                        border: none;
                        color: white;
                        font-size: 24px;
                        width: 40px;
                        height: 40px;
                        border-radius: 20px;
                        cursor: pointer;
                    """.trimIndent()
                    +"←"
                    onClickFunction = { showDashboard("profile") }
                }

                h2 {
                    style = "font-size: 28px; margin: 0;"
                    +"ℹ️ Sobre o App"
                }
            }

            // Conteúdo
            div {
                style = """
                    flex: 1;
                    overflow-y: auto;
                    padding: 16px;
                """.trimIndent()

                // Logo e Nome
                div {
                    style = """
                        background: white;
                        padding: 32px;
                        border-radius: 12px;
                        text-align: center;
                        margin-bottom: 16px;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                    """.trimIndent()

                    div {
                        style = """
                            width: 100px;
                            height: 100px;
                            background: linear-gradient(135deg, #2E7D32, #4CAF50);
                            border-radius: 24px;
                            display: inline-flex;
                            align-items: center;
                            justify-content: center;
                            font-size: 48px;
                            margin-bottom: 16px;
                        """.trimIndent()
                        +"♻️"
                    }

                    h2 {
                        style = "font-size: 28px; color: #2E7D32; margin: 0 0 8px 0;"
                        +"Reciclai"
                    }

                    div {
                        style = "color: #666; font-size: 16px;"
                        +"Versão 1.0.0"
                    }
                }

                // Descrição
                div {
                    style = """
                        background: white;
                        padding: 24px;
                        border-radius: 12px;
                        margin-bottom: 16px;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                    """.trimIndent()

                    div {
                        style = "font-weight: bold; color: #333; font-size: 18px; margin-bottom: 12px;"
                        +"Nossa Missão"
                    }
                    p {
                        style = "color: #666; line-height: 1.6; margin: 0;"
                        +"O Reciclai é uma plataforma que conecta pessoas e empresas aos pontos de coleta de materiais recicláveis, promovendo a sustentabilidade e a preservação do meio ambiente."
                    }
                }

                // Estatísticas
                div {
                    style = """
                        background: white;
                        padding: 24px;
                        border-radius: 12px;
                        margin-bottom: 16px;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                    """.trimIndent()

                    div {
                        style = "font-weight: bold; color: #333; font-size: 18px; margin-bottom: 16px;"
                        +"Impacto Global"
                    }

                    aboutStat("🌍", "10.000+", "Usuários ativos")
                    aboutStat("📍", "500+", "Pontos de coleta")
                    aboutStat("♻️", "50 ton", "Material reciclado")
                    aboutStat("🌳", "2.500 ton", "CO₂ evitado")
                }

                // Links
                div {
                    style = """
                        background: white;
                        padding: 24px;
                        border-radius: 12px;
                        margin-bottom: 16px;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                    """.trimIndent()

                    div {
                        style = "font-weight: bold; color: #333; font-size: 18px; margin-bottom: 16px;"
                        +"Links Úteis"
                    }

                    aboutLink("🌐", "Site Oficial", "www.reciclai.com.br")
                    aboutLink("📧", "Suporte", "contato@reciclai.com.br")
                    aboutLink("📱", "Instagram", "@reciclai")
                    aboutLink("📘", "Termos de Uso", "Ver documento")
                    aboutLink("🔒", "Política de Privacidade", "Ver documento")
                }

                // Créditos
                div {
                    style = """
                        background: white;
                        padding: 24px;
                        border-radius: 12px;
                        text-align: center;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.05);
                    """.trimIndent()

                    div {
                        style = "color: #999; font-size: 14px; line-height: 1.6;"
                        +"Desenvolvido com 💚 para um planeta melhor"
                    }
                    div {
                        style = "color: #999; font-size: 12px; margin-top: 8px;"
                        +"© 2025 Reciclai. Todos os direitos reservados."
                    }
                }
            }

            // Bottom Navigation
            div {
                style = """
                    background: white;
                    padding: 12px 8px 8px 8px;
                    box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    justify-content: space-around;
                """.trimIndent()

                navButton("🗺️", "Mapa", false) { showDashboard("map") }
                navButton("👤", "Perfil", false) { showDashboard("profile") }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.aboutStat(icon: String, value: String, label: String) {
    div {
        style = """
            display: flex;
            align-items: center;
            gap: 16px;
            padding: 12px 0;
            border-bottom: 1px solid #f0f0f0;
        """.trimIndent()

        div {
            style = "font-size: 32px;"
            +icon
        }

        div {
            style = "flex: 1;"

            div {
                style = "font-size: 24px; font-weight: bold; color: #2E7D32; margin-bottom: 4px;"
                +value
            }
            div {
                style = "font-size: 14px; color: #666;"
                +label
            }
        }
    }
}

fun TagConsumer<HTMLElement>.aboutLink(icon: String, title: String, subtitle: String) {
    div {
        style = """
            display: flex;
            align-items: center;
            gap: 16px;
            padding: 12px;
            margin: 8px 0;
            border-radius: 8px;
            cursor: pointer;
            transition: background 0.2s;
        """.trimIndent()

        onMouseOverFunction = {
            (it.target as? HTMLElement)?.style?.background = "#f5f5f5"
        }
        onMouseOutFunction = {
            (it.target as? HTMLElement)?.style?.background = "transparent"
        }

        div {
            style = "font-size: 24px;"
            +icon
        }

        div {
            style = "flex: 1;"

            div {
                style = "font-weight: bold; color: #333; margin-bottom: 2px;"
                +title
            }
            div {
                style = "font-size: 13px; color: #666;"
                +subtitle
            }
        }

        div {
            style = "color: #999; font-size: 20px;"
            +"›"
        }
    }
}

// ====== TELA DE PERFIL ======
fun renderProfileScreen(root: HTMLDivElement) {
    root.innerHTML = ""

    root.append {
        div {
            style = """
                display: flex;
                flex-direction: column;
                height: 100vh;
                background: #f5f5f5;
            """.trimIndent()

            // Header com gradiente
            div {
                style = """
                    background: linear-gradient(135deg, #2E7D32, #4CAF50);
                    padding: 40px 20px;
                    color: white;
                    text-align: center;
                """.trimIndent()

                div {
                    style = """
                        width: 80px;
                        height: 80px;
                        background: white;
                        border-radius: 40px;
                        display: inline-flex;
                        align-items: center;
                        justify-content: center;
                        font-size: 40px;
                        box-shadow: 0 4px 12px rgba(0,0,0,0.2);
                        margin-bottom: 16px;
                    """.trimIndent()
                    +"👤"
                }

                h2 {
                    style = "font-size: 24px; margin: 0 0 4px 0;"
                    +(currentUser?.name ?: "Usuário")
                }
                p {
                    style = "opacity: 0.9; margin: 0;"
                    +(currentUser?.email ?: "")
                }
            }

            // Stats
            div {
                style = """
                    display: grid;
                    grid-template-columns: repeat(2, 1fr);
                    gap: 16px;
                    padding: 16px;
                    background: white;
                    margin: -20px 16px 16px 16px;
                    border-radius: 16px;
                    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                """.trimIndent()

                statCard("⭐", "${currentUser?.points ?: 0}", "Pontos")
                statCard("🏆", "Nível 1", "Conquista")
                statCard("♻️", "0 kg", "Reciclado")
                statCard("🌱", "0 kg CO₂", "Economizado")
            }

            // Opções do perfil
            div {
                style = """
                    flex: 1;
                    padding: 16px;
                    overflow-y: auto;
                """.trimIndent()

                profileOption("🎯", "Minhas Conquistas", "Ver todas conquistas") {
                    showDashboard("achievements")
                }
                profileOption("📊", "Histórico", "Ver histórico de reciclagem") {
                    showDashboard("history")
                }
                profileOption("⚙️", "Configurações", "Preferências do app") {
                    showDashboard("settings")
                }
                profileOption("ℹ️", "Sobre o App", "Versão e informações") {
                    showDashboard("about")
                }

                // Botão sair
                button {
                    style = """
                        width: 100%;
                        padding: 16px;
                        background: #F44336;
                        color: white;
                        border: none;
                        border-radius: 12px;
                        font-size: 16px;
                        font-weight: bold;
                        cursor: pointer;
                        margin-top: 16px;
                    """.trimIndent()
                    +"🚪 Sair"

                    onClickFunction = {
                        currentUser = null
                        ApiService.clearAuthToken()
                        renderApp()
                    }
                }
            }

            // Bottom Navigation (SEM CONTEÚDO!)
            div {
                style = """
                    background: white;
                    padding: 12px 8px 8px 8px;
                    box-shadow: 0 -2px 8px rgba(0,0,0,0.1);
                    display: flex;
                    justify-content: space-around;
                """.trimIndent()

                navButton("🗺️", "Mapa", false) { showDashboard("map") }
                navButton("👤", "Perfil", true) { showDashboard("profile") }
            }
        }
    }
}

fun TagConsumer<HTMLElement>.statCard(icon: String, value: String, label: String) {
    div {
        style = """
            text-align: center;
            padding: 16px;
        """.trimIndent()

        div {
            style = "font-size: 32px; margin-bottom: 8px;"
            +icon
        }
        div {
            style = "font-size: 20px; font-weight: bold; color: #2E7D32; margin-bottom: 4px;"
            +value
        }
        div {
            style = "font-size: 12px; color: #666;"
            +label
        }
    }
}

fun TagConsumer<HTMLElement>.profileOption(icon: String, title: String, subtitle: String, onClick: () -> Unit) {
    div {
        style = """
            background: white;
            padding: 16px;
            border-radius: 12px;
            margin-bottom: 12px;
            display: flex;
            align-items: center;
            gap: 16px;
            cursor: pointer;
            transition: transform 0.2s;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
        """.trimIndent()

        onClickFunction = { onClick() }

        onMouseOverFunction = {
            (it.target as? HTMLElement)?.style?.transform = "translateX(4px)"
        }
        onMouseOutFunction = {
            (it.target as? HTMLElement)?.style?.transform = "translateX(0)"
        }

        div {
            style = """
                font-size: 32px;
                width: 48px;
                height: 48px;
                display: flex;
                align-items: center;
                justify-content: center;
                background: rgba(46, 125, 50, 0.1);
                border-radius: 12px;
            """.trimIndent()
            +icon
        }

        div {
            style = "flex: 1;"

            div {
                style = "font-weight: bold; color: #333; margin-bottom: 4px;"
                +title
            }
            div {
                style = "font-size: 13px; color: #666;"
                +subtitle
            }
        }

        div {
            style = "color: #999; font-size: 20px;"
            +"›"
        }
    }
}

// ====== TELA DO MAPA ======

// ====== FUNÇÕES AUXILIARES PARA O MAPA ======

fun initMap() {
    val mapElement = document.getElementById("map") ?: return

    // Verificar se Leaflet está carregado
    if (js("typeof L === 'undefined'") as Boolean) {
        console.log("⏳ Aguardando Leaflet carregar...")
        window.setTimeout({ initMap() }, 500)
        return
    }

    // Verificar se o mapa já foi inicializado
    if (js("window.recyclingMap != null") as Boolean) {
        console.log("✅ Mapa já inicializado")
        return
    }

    try {
        console.log("🗺️ Inicializando Leaflet Map...")

        // Criar o mapa usando Leaflet (GRATUITO - sem billing!)
        // Centralizado em Recife - PE
        js("""
            window.recyclingMap = L.map('map').setView([-8.063170, -34.891520], 12);
            
            L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
                attribution: '© OpenStreetMap contributors',
                maxZoom: 19
            }).addTo(window.recyclingMap);
            
            window.recyclingMarkers = [];
        """)

        console.log("✅ Leaflet Map inicializado com sucesso!")
    } catch (e: Exception) {
        console.error("❌ Erro ao inicializar mapa:", e)
    }
}

fun loadRecyclingPoints() {
    launchAsync {
        try {
            val result = RecyclingPointRepository.getAllPoints()

            result.onSuccess { points ->
                console.log("✅ Pontos carregados:", points.size)

                // Atualizar contador
                val countElement = document.getElementById("points-count")
                countElement?.textContent = "${points.size} pontos encontrados"

                // Verificar se Leaflet está disponível
                if (js("typeof L === 'undefined'") as Boolean) {
                    console.error("❌ Leaflet não está carregado!")
                    window.setTimeout({ loadRecyclingPoints() }, 1000)
                    return@onSuccess
                }

                // Limpar marcadores antigos
                js("""
                    if (window.recyclingMarkers) {
                        window.recyclingMarkers.forEach(function(marker) {
                            marker.remove();
                        });
                        window.recyclingMarkers = [];
                    }
                """)

                val map = window.asDynamic().recyclingMap
                if (map == null) {
                    console.error("❌ Mapa não inicializado!")
                    return@onSuccess
                }

                // Adicionar marcadores no Leaflet
                points.forEach { point ->
                    val lat = point.latitude
                    val lng = point.longitude

                    val popupHtml = """
                        <div style="font-family: Arial, sans-serif; max-width: 250px;">
                            <h3 style="color: #2E7D32; margin: 0 0 8px 0; font-size: 16px;">${point.name}</h3>
                            <p style="margin: 4px 0; font-size: 13px; color: #666;"><strong>📍</strong> ${point.address}</p>
                            <p style="margin: 4px 0; font-size: 13px; color: #666;"><strong>🕒</strong> ${point.operatingHours}</p>
                            <p style="margin: 4px 0; font-size: 13px; color: #2E7D32;"><strong>♻️</strong> ${point.acceptedMaterials.joinToString(", ")}</p>
                            ${if (point.phone != null) "<p style='margin: 4px 0; font-size: 13px; color: #666;'><strong>📞</strong> ${point.phone}</p>" else ""}
                        </div>
                    """.trimIndent()

                    // Criar marcador usando Leaflet
                    js("""
                        var marker = L.marker([lat, lng]).addTo(map);
                        marker.bindPopup(popupHtml);
                        window.recyclingMarkers.push(marker);
                    """)
                }

                console.log("✅ Marcadores adicionados ao mapa:", points.size)

                // Renderizar lista de pontos
                renderPointsList(points)
            }

            result.onFailure { error ->
                console.error("❌ Erro ao carregar pontos:", error)
                val countElement = document.getElementById("points-count")
                countElement?.textContent = "Erro ao carregar"

                // Mensagem amigável
                val container = document.getElementById("points-container")
                container?.innerHTML = """
                    <div style="text-align: center; padding: 20px; color: #999;">
                        <div style="font-size: 48px; margin-bottom: 12px;">📡</div>
                        <div style="font-size: 14px;">Não foi possível conectar ao servidor.</div>
                        <div style="font-size: 12px; margin-top: 8px;">Verifique se o backend está rodando.</div>
                    </div>
                """.trimIndent()
            }
        } catch (e: Exception) {
            console.error("❌ Exceção ao carregar pontos:", e)
        }
    }
}

fun renderPointsList(points: List<RecyclingPoint>) {
    val container = document.getElementById("points-container") ?: return
    container.innerHTML = ""

    points.forEach { point ->
        container.append {
            div {
                style = """
                    background: #f9f9f9;
                    padding: 16px;
                    border-radius: 12px;
                    margin-bottom: 8px;
                    cursor: pointer;
                    transition: all 0.2s;
                    border-left: 4px solid #2E7D32;
                """.trimIndent()

                val lat = point.latitude
                val lng = point.longitude

                onClickFunction = {
                    // Centralizar no mapa usando Leaflet
                    val map = window.asDynamic().recyclingMap
                    if (map != null) {
                        js("""
                            map.setView([lat, lng], 16);
                        """)
                    }
                }

                onMouseOverFunction = {
                    (it.target as? HTMLElement)?.style?.background = "#e8f5e9"
                }
                onMouseOutFunction = {
                    (it.target as? HTMLElement)?.style?.background = "#f9f9f9"
                }

                h4 {
                    style = "color: #2E7D32; margin: 0 0 8px 0; font-size: 16px;"
                    +"📍 ${point.name}"
                }
                p {
                    style = "color: #666; font-size: 13px; margin: 4px 0;"
                    +point.address
                }
                p {
                    style = "color: #2E7D32; font-size: 12px; margin: 8px 0 0 0; font-weight: bold;"
                    +"♻️ ${point.acceptedMaterials.joinToString(", ")}"
                }
            }
        }
    }
}
