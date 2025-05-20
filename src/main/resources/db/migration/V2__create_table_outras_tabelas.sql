CREATE TABLE attendance (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            event_id BIGINT NOT NULL,
                            user_id BIGINT NOT NULL,
                            registration_date DATETIME NOT NULL,
                            CONSTRAINT fk_attendance_event FOREIGN KEY (event_id) REFERENCES event(id),
                            CONSTRAINT fk_attendance_user FOREIGN KEY (user_id) REFERENCES user(id),
                            CONSTRAINT uk_attendance_event_user UNIQUE (event_id, user_id)
);
CREATE INDEX idx_attendance_event ON attendance(event_id);
CREATE INDEX idx_attendance_user ON attendance(user_id);



CREATE TABLE payment (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         transaction_id VARCHAR(255) NOT NULL,
                         amount DECIMAL(19, 2) NOT NULL,
                         payment_method VARCHAR(50) NOT NULL,
                         payment_date DATETIME NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         event_id BIGINT NOT NULL,
                         user_id BIGINT NOT NULL,
                         CONSTRAINT uk_payment_transaction UNIQUE (transaction_id),
                         CONSTRAINT fk_payment_event FOREIGN KEY (event_id) REFERENCES event(id),
                         CONSTRAINT fk_payment_user FOREIGN KEY (user_id) REFERENCES user(id)
);
CREATE INDEX idx_payment_transaction ON payment(transaction_id);
CREATE INDEX idx_payment_event ON payment(event_id);
CREATE INDEX idx_payment_user ON payment(user_id);
CREATE INDEX idx_payment_status ON payment(status);



CREATE TABLE role (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      name VARCHAR(50) NOT NULL,
                      CONSTRAINT uk_role_name UNIQUE (name)
);
INSERT INTO role (name) VALUES
                            ('ROLE_USER'),
                            ('ROLE_ADMIN'),
                            ('ROLE_ORGANIZER');