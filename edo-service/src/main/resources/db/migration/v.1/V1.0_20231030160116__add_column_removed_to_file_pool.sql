-- Добавление колонки removed
ALTER TABLE file_pool ADD COLUMN IF NOT EXISTS removed BOOLEAN DEFAULT FALSE;

