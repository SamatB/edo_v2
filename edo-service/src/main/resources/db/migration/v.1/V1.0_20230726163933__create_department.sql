CREATE TABLE IF NOT EXISTS department
(
    id                   BIGSERIAL PRIMARY KEY,
    full_name            VARCHAR(100) NOT NULL,
    short_name           VARCHAR(50),
    address              VARCHAR(100),
    phone                VARCHAR(11),
    creation_date        TIMESTAMP NOT NULL,
    archived_date        TIMESTAMP,
    external_id          VARCHAR(20),
    parent_department_id BIGINT,
    FOREIGN KEY (parent_department_id) REFERENCES department(id)
);

COMMENT ON COLUMN department.id IS 'Идентификатор департамента';
COMMENT ON COLUMN department.full_name IS 'Полное название департамента';
COMMENT ON COLUMN department.short_name IS 'Краткое название департамента';
COMMENT ON COLUMN department.address IS 'Адрес департамента';
COMMENT ON COLUMN department.phone IS 'Номер телефона департамента';
COMMENT ON COLUMN department.creation_date IS 'Дата создания';
COMMENT ON COLUMN department.archived_date IS 'Дата архивации';
COMMENT ON COLUMN department.external_id IS 'Внешний идентификатор';
COMMENT ON COLUMN department.parent_department_id IS 'Идентификатор вышестоящего департамента';


