CREATE TABLE IF NOT EXISTS regions
(
    id                    BIGSERIAL PRIMARY KEY,
    region_name           VARCHAR(100) NOT NULL,
    federal_district_name VARCHAR(100) NOT NULL
);

COMMENT ON COLUMN regions.id IS 'Идентификатор региона';
COMMENT ON COLUMN regions.region_name IS 'Наименование региона';
COMMENT ON COLUMN regions.federal_district_name IS 'Наименование федерального округа';