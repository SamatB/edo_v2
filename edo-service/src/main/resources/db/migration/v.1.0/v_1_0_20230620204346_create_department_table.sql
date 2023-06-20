CREATE SCHEMA IF NOT EXISTS edo;

CREATE TABLE IF NOT EXISTS edo.department
(
    external_id          BIGINT PRIMARY KEY,
    full_name            VARCHAR(50) NOT NULL,
    short_name           VARCHAR(25),
    address              VARCHAR(50),
    phone                VARCHAR(11),
    creation_date        TIMESTAMP,
    archived_date        TIMESTAMP,
    parent_department_id BIGINT REFERENCES edo.department (external_id)
);

comment on column edo.department.external_id is 'Идентификатор департамента';
comment on column edo.department.full_name is 'Полное название департамента';
comment on column edo.department.short_name is 'Краткое название департамента';
comment on column edo.department.address is 'Адрес департамента';
comment on column edo.department.phone is 'Номер телефона департамента';
comment on column edo.department.creation_date is 'Дата создания';
comment on column edo.department.archived_date is 'Дата архивации';
comment on column edo.department.parent_department_id is 'Идентификатор родительского департамента';


