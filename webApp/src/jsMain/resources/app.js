// ============================================
// RECICLAI WEB APP - JavaScript Puro
// Design idêntico ao App Android
// ============================================

// Função para mostrar tela
function showScreen(screenId) {
    // Esconder todas as telas
    document.querySelectorAll('.screen').forEach(screen => {
        screen.classList.remove('active');
    });

    // Mostrar tela selecionada
    document.getElementById(screenId).classList.add('active');

    // Atualizar navegação ativa
    updateNavigation(screenId);

    // Carregar dados se necessário
    if (screenId === 'contentScreen') {
        loadContent();
    } else if (screenId === 'mapScreen') {
        loadCollectPoints();
    }
}

// Atualizar navegação ativa
function updateNavigation(screenId) {
    document.querySelectorAll('.nav-item').forEach(item => {
        item.classList.remove('active');
    });
}

// Handle Login
function handleLogin(event) {
    event.preventDefault();
    showScreen('contentScreen');
}

// Handle Register
function handleRegister(event) {
    event.preventDefault();
    showScreen('contentScreen');
}

// Handle Logout
function handleLogout() {
    showScreen('welcomeScreen');
}

// Carregar Conteúdo Educativo
async function loadContent() {
    const contentList = document.getElementById('contentList');

    try {
        // Tentar carregar da API
        const response = await fetch('https://reciclai-api.example.com/content');

        if (!response.ok) {
            throw new Error('API não disponível');
        }

        const contents = await response.json();

        if (contents && contents.length > 0) {
            contentList.innerHTML = '';
            contents.forEach(content => {
                contentList.innerHTML += createContentCard(content);
            });
        } else {
            showEmptyContent();
        }
    } catch (error) {
        // Se API não disponível, mostrar conteúdo de exemplo
        showSampleContent();
    }
}

// Mostrar conteúdo de exemplo
function showSampleContent() {
    const contentList = document.getElementById('contentList');

    const sampleContents = [
        {
            title: "Como Separar o Lixo Corretamente",
            summary: "Aprenda a identificar e separar diferentes tipos de resíduos para facilitar a reciclagem e contribuir para um planeta mais limpo.",
            readTime: 5,
            category: "Reciclagem Básica"
        },
        {
            title: "Os Benefícios da Reciclagem",
            summary: "Descubra como a reciclagem ajuda a preservar recursos naturais, economizar energia e reduzir a poluição ambiental.",
            readTime: 7,
            category: "Sustentabilidade"
        },
        {
            title: "Plástico: O Que Pode e Não Pode Reciclar",
            summary: "Entenda quais tipos de plástico são recicláveis e como identificá-los através dos símbolos e números nas embalagens.",
            readTime: 6,
            category: "Materiais"
        },
        {
            title: "Compostagem: Transforme Lixo em Adubo",
            summary: "Aprenda a fazer compostagem em casa e transformar resíduos orgânicos em adubo natural para suas plantas.",
            readTime: 8,
            category: "Compostagem"
        },
        {
            title: "Economia Circular e Sustentabilidade",
            summary: "Conheça o conceito de economia circular e como ele pode revolucionar a forma como consumimos e descartamos produtos.",
            readTime: 10,
            category: "Conceitos"
        }
    ];

    contentList.innerHTML = '';
    sampleContents.forEach(content => {
        contentList.innerHTML += createContentCard(content);
    });
}

// Criar card de conteúdo
function createContentCard(content) {
    return `
        <div class="content-card">
            <h3 class="card-title">${content.title}</h3>
            <p class="card-text">${content.summary}</p>
            <div class="card-footer">
                <span>📖 ${content.readTime} min</span>
                <span>🏷️ ${content.category}</span>
            </div>
        </div>
    `;
}

// Mostrar estado vazio de conteúdo
function showEmptyContent() {
    const contentList = document.getElementById('contentList');
    contentList.innerHTML = `
        <div class="empty-state">
            <div class="empty-icon">📚</div>
            <p class="empty-text">Nenhum conteúdo disponível no momento.</p>
        </div>
    `;
}

// Carregar Pontos de Coleta
async function loadCollectPoints() {
    const mapList = document.getElementById('mapList');

    try {
        // Tentar carregar da API
        const response = await fetch('https://reciclai-api.example.com/collect-points');

        if (!response.ok) {
            throw new Error('API não disponível');
        }

        const points = await response.json();

        if (points && points.length > 0) {
            mapList.innerHTML = '';
            points.forEach(point => {
                mapList.innerHTML += createCollectPointCard(point);
            });
        } else {
            showEmptyPoints();
        }
    } catch (error) {
        // Se API não disponível, mostrar pontos de exemplo
        showSamplePoints();
    }
}

// Mostrar pontos de exemplo
function showSamplePoints() {
    const mapList = document.getElementById('mapList');

    const samplePoints = [
        {
            name: "EcoPonto Central",
            address: "Av. Principal, 1234 - Centro",
            operatingHours: "Seg-Sex: 8h-18h, Sáb: 8h-12h",
            acceptedMaterials: ["Papel", "Plástico", "Metal", "Vidro"]
        },
        {
            name: "Cooperativa Verde Vida",
            address: "Rua das Flores, 567 - Bairro Jardim",
            operatingHours: "Seg-Sex: 7h-17h",
            acceptedMaterials: ["Papel", "Papelão", "Plástico", "Metal"]
        },
        {
            name: "Ponto de Coleta Municipal",
            address: "Praça da Reciclagem, s/n - Centro",
            operatingHours: "24 horas",
            acceptedMaterials: ["Todos os materiais recicláveis"]
        },
        {
            name: "Reciclagem Sustentável",
            address: "Rua Ecológica, 890 - Zona Norte",
            operatingHours: "Seg-Sáb: 8h-17h",
            acceptedMaterials: ["Eletrônicos", "Pilhas", "Baterias", "Lâmpadas"]
        },
        {
            name: "Centro de Triagem EcoVida",
            address: "Av. Verde, 2345 - Zona Sul",
            operatingHours: "Seg-Sex: 9h-16h",
            acceptedMaterials: ["Papel", "Plástico", "Metal", "Óleo de Cozinha"]
        }
    ];

    mapList.innerHTML = '';
    samplePoints.forEach(point => {
        mapList.innerHTML += createCollectPointCard(point);
    });
}

// Criar card de ponto de coleta
function createCollectPointCard(point) {
    return `
        <div class="content-card">
            <h3 class="card-title">${point.name}</h3>
            <p class="card-text">📍 ${point.address}</p>
            <p class="card-text">🕒 ${point.operatingHours}</p>
            <div class="card-footer">
                <span style="font-size: 12px;">Materiais: ${point.acceptedMaterials.join(", ")}</span>
            </div>
        </div>
    `;
}

// Mostrar estado vazio de pontos
function showEmptyPoints() {
    const mapList = document.getElementById('mapList');
    mapList.innerHTML = `
        <div class="empty-state">
            <div class="empty-icon">📍</div>
            <p class="empty-text">Nenhum ponto de coleta disponível no momento.</p>
        </div>
    `;
}

// Inicialização
window.onload = function() {
    console.log('🌱 Reciclai Web App carregado com sucesso!');
    console.log('Design idêntico ao App Android');
    console.log('Cores: Verde Sustentável (#2E7D32, #4CAF50)');

    // Carregar dados iniciais
    loadContent();
    loadCollectPoints();
};

