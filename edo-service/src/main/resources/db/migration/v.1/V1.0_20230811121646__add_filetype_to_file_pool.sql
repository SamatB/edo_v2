ALTER TABLE file_pool
    ADD COLUMN IF NOT EXISTS file_type VARCHAR(15);