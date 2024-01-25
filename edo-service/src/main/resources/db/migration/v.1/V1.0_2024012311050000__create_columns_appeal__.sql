-- Добавление колонок registration_date и appeal_status
ALTER TABLE appeal ADD COLUMN registration_date TIMESTAMP;
ALTER TABLE appeal ADD COLUMN appeal_status VARCHAR(15);
