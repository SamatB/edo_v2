CREATE TABLE IF NOT EXISTS facsimile
(
    id             BIGSERIAL PRIMARY KEY,
    employee_id    BIGINT,
    department_id  BIGINT,
    file_pool_id   BIGINT,
    archived    BOOLEAN,
    FOREIGN KEY (employee_id) REFERENCES employee(id),
    FOREIGN KEY (department_id) REFERENCES department(id),
    FOREIGN KEY (file_pool_id) REFERENCES file_pool(id)
    );
comment on column facsimile.id is 'Идентификатор facsimile';
comment on column facsimile.employee_id is 'Идентификатор работника';
comment on column facsimile.department_id is 'Идентификатор департамента';
comment on column facsimile.file_pool_id is 'Идентификатор FilePool';
comment on column facsimile.archived is 'Флаг, архивирован ли facsimile';
