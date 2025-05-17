CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       full_name VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL CHECK (role IN ('USER', 'ADMIN', 'ORGANIZER')),
                       created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                       tags TEXT[]
);

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