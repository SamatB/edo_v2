CREATE TABLE IF NOT EXISTS region
(
    id                    BIGSERIAL PRIMARY KEY,
    region_name           VARCHAR(100) NOT NULL,
    federal_district_name VARCHAR(100) NOT NULL
);

COMMENT ON COLUMN region.id IS 'Идентификатор региона';
COMMENT ON COLUMN region.region_name IS 'Наименование региона';
COMMENT ON COLUMN region.federal_district_name IS 'Наименование федерального округа';