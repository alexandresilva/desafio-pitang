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
VALUES ('teste@gmail.com', 'alexandre.pitang','teste','Alexandre Oliveira Teste', 2,'2025-05-20 18:24:44.492977-03',null);


CREATE TABLE events (
                        id BIGSERIAL PRIMARY KEY,
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        location POINT,
                        start_date TIMESTAMP WITH TIME ZONE,
                        end_date TIMESTAMP WITH TIME ZONE,
                        organizer_id BIGINT REFERENCES users(id),
                        metadata JSONB
);