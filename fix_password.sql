UPDATE users
SET password_hash = '$2a$10$N9qo8uLOickgx2ZMRZoMye6J.P6dLxCXqXhJhYGJLPPdNLVG4P0Uy'
WHERE email IN ('teste@reciclai.com', 'admin@reciclai.com', 'joao@reciclai.com');

-- Verificar
SELECT name, email, 'senha123' as senha_deve_ser FROM users WHERE email = 'teste@reciclai.com';

