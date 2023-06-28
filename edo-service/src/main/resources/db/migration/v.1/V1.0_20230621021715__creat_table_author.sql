CREATE TABLE IF NOT EXISTS author
(
    id             BIGSERIAL PRIMARY KEY,
    first_name     VARCHAR(255) NOT NULL,
    last_name      VARCHAR(255) NOT NULL,
    middle_name    VARCHAR(255),
    address        VARCHAR(255),
    snils          VARCHAR(255) UNIQUE,
    mobile_phone   VARCHAR(255),
    email          VARCHAR(255) UNIQUE,
    employment     VARCHAR(255),
    fio_dative     VARCHAR(255),
    fio_genitive   VARCHAR(255),
    fio_nominative VARCHAR(255)
);

COMMENT ON COLUMN author.id IS 'Уникальный идентификатор автора';
COMMENT ON COLUMN author.first_name IS 'Имя (Обязательно)';
COMMENT ON COLUMN author.last_name IS 'Фамилия (Обязательно)';
COMMENT ON COLUMN author.middle_name IS 'Отчество';
COMMENT ON COLUMN author.address IS 'Адрес';
COMMENT ON COLUMN author.snils IS 'СНИЛС';
COMMENT ON COLUMN author.email IS 'Эл.почта';
COMMENT ON COLUMN author.mobile_phone IS 'Мобильный телефон';
COMMENT ON COLUMN author.employment IS 'Рабочий статус (Безработный, Работник, Учащийся)';
COMMENT ON COLUMN author.fio_dative IS 'ФИО в дательном падеже';
COMMENT ON COLUMN author.fio_genitive IS 'ФИО в родительном падеже';
COMMENT ON COLUMN author.fio_nominative IS 'ФИО в именительном падеже';