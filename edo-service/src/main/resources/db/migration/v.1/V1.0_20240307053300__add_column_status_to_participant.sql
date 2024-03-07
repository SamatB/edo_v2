-- Добавление колонки status
ALTER TABLE participant ADD COLUMN IF NOT EXISTS status VARCHAR(15) DEFAULT 'PASSIVE';
COMMENT ON COLUMN participant.status IS 'Статус участника согласования';