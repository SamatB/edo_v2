-- Добавление колонки appeal_id
ALTER TABLE author ADD COLUMN IF NOT EXISTS appeal_id BIGINT;

