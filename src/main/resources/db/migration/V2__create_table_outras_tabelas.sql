CREATE TABLE attendance (
                            id BIGSERIAL PRIMARY KEY,
                            event_id int4 NOT NULL,
                            user_id int4 NOT NULL,
                            registration_date timestamptz NOT NULL,
                            CONSTRAINT fk_attendance_event FOREIGN KEY (event_id) REFERENCES events(id),
                            CONSTRAINT fk_attendance_user FOREIGN KEY (user_id) REFERENCES users(id),
                            CONSTRAINT uk_attendance_event_user UNIQUE (event_id, user_id)
);
CREATE INDEX idx_attendance_event ON attendance(event_id);
CREATE INDEX idx_attendance_user ON attendance(user_id);


CREATE TABLE payment (
                         id BIGSERIAL PRIMARY KEY,
                         transaction_id VARCHAR(255) NOT NULL,
                         amount DECIMAL(19, 2) NOT NULL,
                         payment_method VARCHAR(50) NOT NULL,
                         payment_date timestamptz NOT NULL,
                         status VARCHAR(20) NOT NULL,
                         event_id int4 NOT NULL,
                         user_id int4 NOT NULL,
                         CONSTRAINT uk_payment_transaction UNIQUE (transaction_id),
                         CONSTRAINT fk_payment_event FOREIGN KEY (event_id) REFERENCES events(id),
                         CONSTRAINT fk_payment_user FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE INDEX idx_payment_transaction ON payment(transaction_id);
CREATE INDEX idx_payment_event ON payment(event_id);
CREATE INDEX idx_payment_user ON payment(user_id);
CREATE INDEX idx_payment_status ON payment(status);
