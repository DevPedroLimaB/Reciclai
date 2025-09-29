# Backend Reciclai

API REST para o aplicativo Reciclai.

## Setup

```bash
cd backend
npm install
cp .env.example .env
npm run dev
```

## Endpoints

- `GET /api/health` - Status da API
- `POST /api/auth/register` - Registro
- `POST /api/auth/login` - Login
- `GET /api/collect-points` - Pontos de coleta
- `GET /api/users/profile` - Perfil do usuário