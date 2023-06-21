CREATE TABLE IF NOT EXISTS department
(
    external_id          VARCHAR(20) PRIMARY KEY,
    full_name            VARCHAR(50) NOT NULL,
    short_name           VARCHAR(25),
    address              VARCHAR(50),
    phone                VARCHAR(11),
    creation_date        TIMESTAMP,
    archived_date        TIMESTAMP,
    parent_department_id VARCHAR(20),
    FOREIGN KEY (parent_department_id) REFERENCES department (external_id)
);

COMMENT ON COLUMN department.external_id IS 'Идентификатор департамента';
COMMENT ON COLUMN department.full_name IS 'Полное название департамента';
COMMENT ON COLUMN department.short_name IS 'Краткое название департамента';
COMMENT ON COLUMN department.address IS 'Адрес департамента';
COMMENT ON COLUMN department.phone IS 'Номер телефона департамента';
COMMENT ON COLUMN department.creation_date IS 'Дата создания';
COMMENT ON COLUMN department.archived_date IS 'Дата архивации';
COMMENT ON COLUMN department.parent_department_id IS 'Идентификатор родительского департамента';


