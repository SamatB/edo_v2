ALTER TABLE IF EXISTS appeal
    ADD COLUMN IF NOT EXISTS region_id BIGINT,
    ADD CONSTRAINT fk_appeal_region FOREIGN KEY (region_id) REFERENCES region (id);
