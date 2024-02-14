-- Добавление колонок registration_date и appeal_status
ALTER TABLE appeal ADD COLUMN registration_date TIMESTAMP WITH TIME ZONE;
ALTER TABLE appeal ADD COLUMN status VARCHAR(15);
