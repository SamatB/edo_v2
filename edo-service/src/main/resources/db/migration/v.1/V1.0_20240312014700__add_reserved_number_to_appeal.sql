-- Добавление колонок registration_date и appeal_status
ALTER TABLE IF EXISTS appeal ADD COLUMN IF NOT EXISTS reserved_number VARCHAR(50);
