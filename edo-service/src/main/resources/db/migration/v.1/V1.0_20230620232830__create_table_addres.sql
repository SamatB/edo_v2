CREATE SCHEMA IF NOT EXISTS edo;
CREATE TABLE IF NOT EXISTS edo.address
(
    id           BIGSERIAL PRIMARY KEY,
    full_address VARCHAR(500),
    street       VARCHAR(50),
    house        VARCHAR(50),
    postcode     VARCHAR(50),
    housing      VARCHAR(50),
    building     VARCHAR(50),
    city         VARCHAR(50),
    region       VARCHAR(50),
    country      VARCHAR(50),
    flat         VARCHAR(50)
);
COMMENT ON COLUMN edo.address.id IS 'Уникальный идентификатор адреса';
COMMENT ON COLUMN edo.address.full_address IS 'Полный адрес';
COMMENT ON COLUMN edo.address.street IS 'Название улицы';
COMMENT ON COLUMN edo.address.house IS 'Номер дома';
COMMENT ON COLUMN edo.address.postcode IS 'Почтовый индекс';
COMMENT ON COLUMN edo.address.housing IS 'Корпус здания';
COMMENT ON COLUMN edo.address.building IS 'Строение здания';
COMMENT ON COLUMN edo.address.city IS 'Город';
COMMENT ON COLUMN edo.address.region IS 'Регион или область';
COMMENT ON COLUMN edo.address.country IS 'Страна';
COMMENT ON COLUMN edo.address.flat IS 'Номер квартиры';