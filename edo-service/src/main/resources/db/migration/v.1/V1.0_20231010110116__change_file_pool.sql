-- Добавление колонки appeal_id
ALTER TABLE file_pool ADD COLUMN IF NOT EXISTS appeal_id BIGINT;

