CREATE TABLE role (
                      id BIGSERIAL PRIMARY KEY,
                      name VARCHAR(50) NOT NULL,
                      CONSTRAINT uk_role_name UNIQUE (name)
);
INSERT INTO role (name) VALUES
                            ('ROLE_USER'),
                            ('ROLE_ADMIN'),
                            ('ROLE_ORGANIZER');

CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(20) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255) NOT NULL,
                       role_id int4 NOT NULL,
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       tags VARCHAR(20),
                       CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES public.role(id)
);
INSERT INTO users (email, username, "password", full_name, role_id, created_at, tags)
VALUES ('teste@gmail.com', 'alexandre.pitang','$2a$12$I0O9..BbVeniB2y1MQj/4eZ54FN02kKI6n9lms4MzM.wE9LVOX6j2'
,'Alexandre Oliveira Teste', 2,'2025-05-20 18:24:44.492977-03',null);


CREATE TABLE events (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        location VARCHAR(50),
                        start_date TIMESTAMP WITH TIME ZONE,
                        end_date TIMESTAMP WITH TIME ZONE,
                        organizer_id BIGINT REFERENCES users(id),
                        metadata JSONB,
                        created_at TIMESTAMPTZ NOT NULL,
                        deleted boolean null,
                        deleted_at TIMESTAMPTZ null
);
INSERT INTO public.events
(id, title, description, "location", start_date, end_date, organizer_id, metadata, created_at, deleted, deleted_at)
VALUES(	nextval('events_id_seq'::regclass),
           'Desafio Pintang',
           'Criar uma aplicação de gerenciamento de festas de formatura que deve utilizar tecnologias do mundo Java.',
           'Remoto', '2025-05-17 10:00:00', '2025-05-21 23:59:00', 1, null, NOW(), false, NULL);