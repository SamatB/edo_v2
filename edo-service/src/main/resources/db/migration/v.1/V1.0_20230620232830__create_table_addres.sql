CREATE TABLE IF NOT EXISTS address
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
COMMENT ON COLUMN address.id IS 'Уникальный идентификатор адреса';
COMMENT ON COLUMN address.full_address IS 'Полный адрес';
COMMENT ON COLUMN address.street IS 'Название улицы';
COMMENT ON COLUMN address.house IS 'Номер дома';
COMMENT ON COLUMN address.postcode IS 'Почтовый индекс';
COMMENT ON COLUMN address.housing IS 'Корпус здания';
COMMENT ON COLUMN address.building IS 'Строение здания';
COMMENT ON COLUMN address.city IS 'Город';
COMMENT ON COLUMN address.region IS 'Регион или область';
COMMENT ON COLUMN address.country IS 'Страна';
COMMENT ON COLUMN address.flat IS 'Номер квартиры';