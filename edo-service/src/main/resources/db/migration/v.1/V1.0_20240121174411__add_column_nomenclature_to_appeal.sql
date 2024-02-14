/*
 добавление столбца nomenclature в таблице appeal
 */
ALTER TABLE appeal
    ADD COLUMN IF NOT EXISTS nomenclature_id BIGINT,
    ADD CONSTRAINT fk_appeal_nomenclature FOREIGN KEY (nomenclature_id) REFERENCES nomenclature(id);