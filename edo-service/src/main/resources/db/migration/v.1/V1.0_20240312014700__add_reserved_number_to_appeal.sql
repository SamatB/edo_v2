-- Добавление колонки reserved_number
-- удаление ограничения NOT NULL для столбца number
-- установка ограничения NOT NULL для столбца nomenclature

ALTER TABLE IF EXISTS appeal
    ADD COLUMN IF NOT EXISTS reserved_number VARCHAR(50),
    ALTER COLUMN number DROP NOT NULL,
    ALTER COLUMN nomenclature_id SET NOT NULL;
