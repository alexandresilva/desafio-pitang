-- Migration para tabela chat_messages baseada no modelo Java
CREATE TABLE chat_messages (
    id BIGSERIAL PRIMARY KEY,
    content VARCHAR(1000) NOT NULL,
    sender_id VARCHAR(255) NOT NULL,
    sender_name VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    type VARCHAR(20) DEFAULT 'MESSAGE' CHECK (type IN ('MESSAGE', 'SYSTEM'))
);

-- Índices para performance
CREATE INDEX idx_chat_messages_sender_id ON chat_messages(sender_id);
CREATE INDEX idx_chat_messages_timestamp ON chat_messages(timestamp);
CREATE INDEX idx_chat_messages_type ON chat_messages(type);

-- Inserir algumas mensagens de exemplo (opcional)
INSERT INTO chat_messages (content, sender_id, sender_name, type)
VALUES 
    ('Bem-vindos ao chat do Desafio Pitang!', '1', 'Sistema', 'SYSTEM'),
    ('Olá pessoal, como estão?', '1', 'Alexandre Oliveira Teste', 'MESSAGE'),
    ('Oi Alexandre! Tudo bem por aqui.', '2', 'Alisson Oliveira Teste', 'MESSAGE');

-- Comentários sobre a estrutura
COMMENT ON TABLE chat_messages IS 'Tabela para armazenar mensagens do chat';
COMMENT ON COLUMN chat_messages.content IS 'Conteúdo da mensagem (máximo 1000 caracteres)';
COMMENT ON COLUMN chat_messages.sender_id IS 'ID do usuário que enviou a mensagem (como String)';
COMMENT ON COLUMN chat_messages.sender_name IS 'Nome do usuário que enviou a mensagem';
COMMENT ON COLUMN chat_messages.timestamp IS 'Data e hora do envio da mensagem';
COMMENT ON COLUMN chat_messages.type IS 'Tipo da mensagem: MESSAGE ou SYSTEM';