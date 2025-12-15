-- Script de inicialização do banco de dados Reciclai
-- PostgreSQL 15+

-- Criação da extensão para UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    profile_image_url TEXT,
    points INTEGER DEFAULT 0,
    level INTEGER DEFAULT 1,
    total_recycled DECIMAL(10, 2) DEFAULT 0.0,
    co2_saved DECIMAL(10, 2) DEFAULT 0.0,
    visited_points INTEGER DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela de Pontos de Reciclagem
CREATE TABLE IF NOT EXISTS recycling_points (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    address TEXT NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    accepted_materials TEXT[] NOT NULL,
    schedule VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    description TEXT,
    rating DECIMAL(3, 2) DEFAULT 0.0,
    is_active BOOLEAN DEFAULT TRUE,
    is_verified BOOLEAN DEFAULT FALSE,
    created_by UUID REFERENCES users(id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela de Conteúdos Educativos
CREATE TABLE IF NOT EXISTS contents (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    summary TEXT NOT NULL,
    content TEXT NOT NULL,
    category VARCHAR(100) NOT NULL,
    author VARCHAR(255),
    read_time INTEGER DEFAULT 5,
    image_url TEXT,
    tags TEXT[],
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela de Agendamentos
CREATE TABLE IF NOT EXISTS schedules (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    collect_point_id UUID NOT NULL REFERENCES recycling_points(id) ON DELETE CASCADE,
    scheduled_date DATE NOT NULL,
    time_slot VARCHAR(50) NOT NULL,
    materials TEXT[] NOT NULL,
    estimated_weight DECIMAL(10, 2),
    status VARCHAR(50) DEFAULT 'PENDING',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP
);

-- Tabela de Recompensas
CREATE TABLE IF NOT EXISTS rewards (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    points_cost INTEGER NOT NULL,
    category VARCHAR(100) NOT NULL,
    image_url TEXT,
    is_available BOOLEAN DEFAULT TRUE,
    expires_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Conquistas
CREATE TABLE IF NOT EXISTS achievements (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    icon_url TEXT,
    points_reward INTEGER DEFAULT 0,
    requirement_type VARCHAR(100) NOT NULL,
    requirement_value INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de Conquistas dos Usuários
CREATE TABLE IF NOT EXISTS user_achievements (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    achievement_id UUID NOT NULL REFERENCES achievements(id) ON DELETE CASCADE,
    unlocked_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, achievement_id)
);

-- Tabela de Avaliações de Pontos
CREATE TABLE IF NOT EXISTS point_reviews (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    point_id UUID NOT NULL REFERENCES recycling_points(id) ON DELETE CASCADE,
    rating INTEGER CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, point_id)
);

-- Índices para performance
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_recycling_points_location ON recycling_points(latitude, longitude);
CREATE INDEX IF NOT EXISTS idx_recycling_points_active ON recycling_points(is_active);
CREATE INDEX IF NOT EXISTS idx_schedules_user ON schedules(user_id);
CREATE INDEX IF NOT EXISTS idx_schedules_date ON schedules(scheduled_date);
CREATE INDEX IF NOT EXISTS idx_contents_category ON contents(category);

-- Dados iniciais de exemplo
-- IMPORTANTE: Senha BCrypt REAL e VÁLIDA
-- Senha: senha123
-- Hash gerado com BCrypt round 10: $2a$10$N9qo8uLOickgx2ZMRZoMye6J.P6dLxCXqXhJhYGJLPPdNLVG4P0Uy
INSERT INTO users (name, email, password_hash, phone, points, level)
VALUES
    ('Usuário Teste', 'teste@reciclai.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye6J.P6dLxCXqXhJhYGJLPPdNLVG4P0Uy', '11999999999', 230, 3),
    ('Admin ReciclAI', 'admin@reciclai.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye6J.P6dLxCXqXhJhYGJLPPdNLVG4P0Uy', '11988888888', 500, 5),
    ('João Silva', 'joao@reciclai.com', '$2a$10$N9qo8uLOickgx2ZMRZoMye6J.P6dLxCXqXhJhYGJLPPdNLVG4P0Uy', '11977777777', 150, 2)
ON CONFLICT (email) DO NOTHING;

-- Pontos de reciclagem de exemplo (RECIFE - PE)
INSERT INTO recycling_points (name, address, latitude, longitude, accepted_materials, schedule, phone, is_verified)
VALUES
    -- Centro do Recife
    ('EcoPonto Boa Vista', 'Av. Conde da Boa Vista, 1234 - Boa Vista', -8.063170, -34.891520, ARRAY['Papel', 'Plástico', 'Vidro', 'Metal'], 'Seg-Sex: 8h-18h', '81987654321', true),

    -- Zona Norte
    ('Recicla Norte Shopping', 'Av. Norte Miguel Arraes de Alencar, 100 - Tamarineira', -8.033260, -34.914200, ARRAY['Papel', 'Papelão', 'Plástico', 'Alumínio'], 'Seg-Sab: 10h-22h', '81976543210', true),

    -- Zona Sul
    ('Verde Vida Boa Viagem', 'Av. Boa Viagem, 5000 - Boa Viagem', -8.126890, -34.899450, ARRAY['Eletrônicos', 'Pilhas', 'Baterias'], 'Ter-Sex: 9h-17h', '81965432109', true),

    -- Pina
    ('EcoPonto Pina', 'Av. Herculano Bandeira, 800 - Pina', -8.096340, -34.882140, ARRAY['Papel', 'Plástico', 'Vidro', 'Metal', 'Papelão'], 'Seg-Sex: 8h-18h, Sáb: 8h-12h', '81954321098', true),

    -- Imbiribeira
    ('Recicla Fácil Imbiribeira', 'Av. Mascarenhas de Morais, 3450 - Imbiribeira', -8.120450, -34.911230, ARRAY['Plástico', 'Metal', 'Alumínio', 'Latas'], 'Seg-Sex: 7h-17h', '81943210987', true),

    -- Casa Forte
    ('Centro de Reciclagem Casa Forte', 'Praça de Casa Forte, 250 - Casa Forte', -8.021560, -34.918970, ARRAY['Papel', 'Papelão', 'Plástico', 'Vidro'], 'Seg-Sáb: 8h-18h', '81932109876', true),

    -- Espinheiro
    ('EcoPoint Espinheiro', 'Rua Benfica, 120 - Espinheiro', -8.043920, -34.893410, ARRAY['Eletrônicos', 'Pilhas', 'Baterias', 'Lâmpadas'], 'Ter-Sex: 9h-16h', '81921098765', true),

    -- Aflitos
    ('Recicla Aflitos', 'Rua Antônio Falcão, 789 - Aflitos', -8.049830, -34.901720, ARRAY['Papel', 'Plástico', 'Metal', 'Vidro'], 'Seg-Sex: 8h-17h', '81910987654', true),

    -- Torre
    ('EcoPonto Torre', 'Av. Beira Rio, 1500 - Torre', -8.050560, -34.912340, ARRAY['Óleo de Cozinha', 'Papel', 'Plástico'], 'Seg-Sex: 8h-18h', '81909876543', true),

    -- Madalena
    ('Verde Recicla Madalena', 'Av. Caxangá, 2100 - Madalena', -8.053670, -34.914890, ARRAY['Papel', 'Papelão', 'Plástico', 'Vidro', 'Metal'], 'Seg-Sáb: 8h-18h', '81998765432', true),

    -- Cordeiro
    ('EcoCordeiro', 'Rua Visconde de Jequitinhonha, 450 - Cordeiro', -8.052340, -34.898120, ARRAY['Eletrônicos', 'Plástico', 'Metal'], 'Seg-Sex: 9h-17h', '81987654322', true),

    -- Encruzilhada
    ('Recicla Encruzilhada', 'Av. Recife, 1850 - Encruzilhada', -8.048920, -34.904560, ARRAY['Papel', 'Plástico', 'Vidro'], 'Seg-Sex: 8h-17h', '81976543211', true),

    -- Jaqueira
    ('EcoPonto Jaqueira', 'Rua do Futuro, 650 - Graças', -8.032450, -34.900780, ARRAY['Papel', 'Plástico', 'Metal', 'Alumínio'], 'Seg-Sáb: 8h-18h', '81965432110', true),

    -- Recife Antigo
    ('Centro de Reciclagem Marco Zero', 'Praça Rio Branco, 50 - Recife', -8.063090, -34.871230, ARRAY['Papel', 'Plástico', 'Vidro', 'Metal'], 'Seg-Sex: 8h-18h', '81954321099', true),

    -- Setúbal
    ('Recicla Setúbal', 'Av. José Rufino, 900 - Setúbal', -8.068450, -34.882340, ARRAY['Eletrônicos', 'Pilhas', 'Baterias'], 'Ter-Sex: 9h-17h', '81943210988', true)
ON CONFLICT (id) DO NOTHING;

-- Conteúdos educativos de exemplo
INSERT INTO contents (title, summary, content, category, author, read_time, tags)
VALUES
    (
        'Como Separar Corretamente o Lixo Reciclável',
        'Aprenda a separar seu lixo de forma correta e contribua para um planeta mais sustentável.',
        '<h2>Separação Correta do Lixo</h2><p>A separação correta do lixo é fundamental para a reciclagem eficiente...</p>',
        'Reciclagem',
        'Equipe Reciclai',
        5,
        ARRAY['reciclagem', 'sustentabilidade', 'meio ambiente']
    ),
    (
        'O Impacto do Plástico no Meio Ambiente',
        'Entenda como o plástico afeta nosso planeta e o que podemos fazer a respeito.',
        '<h2>Plástico e Meio Ambiente</h2><p>O plástico é um dos maiores problemas ambientais da atualidade...</p>',
        'Meio Ambiente',
        'Dr. Carlos Mendes',
        7,
        ARRAY['plástico', 'poluição', 'oceanos']
    )
ON CONFLICT (id) DO NOTHING;

-- Recompensas
INSERT INTO rewards (title, description, points_cost, category)
VALUES
    ('Desconto 10% Eco Store', 'Ganhe 10% de desconto em produtos sustentáveis', 100, 'Desconto'),
    ('Muda de Árvore Grátis', 'Receba uma muda de árvore para plantar', 200, 'Ecológico'),
    ('Ecobag Exclusiva', 'Ecobag personalizada Reciclai', 150, 'Produto')
ON CONFLICT (id) DO NOTHING;

-- Conquistas
INSERT INTO achievements (title, description, points_reward, requirement_type, requirement_value)
VALUES
    ('Primeiro Passo', 'Cadastrou-se no Reciclai', 10, 'REGISTRATION', 1),
    ('Reciclador Iniciante', 'Visitou 5 pontos de coleta', 50, 'VISITS', 5),
    ('Reciclador Expert', 'Visitou 20 pontos de coleta', 100, 'VISITS', 20),
    ('Eco Guerreiro', 'Reciclou 50kg de material', 200, 'WEIGHT', 50)
ON CONFLICT (id) DO NOTHING;

-- Função para atualizar updated_at automaticamente
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers para updated_at
CREATE TRIGGER update_users_updated_at BEFORE UPDATE ON users
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_recycling_points_updated_at BEFORE UPDATE ON recycling_points
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_contents_updated_at BEFORE UPDATE ON contents
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_schedules_updated_at BEFORE UPDATE ON schedules
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
