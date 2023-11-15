CREATE TABLE IF NOT EXISTS deadline
(
    id            BIGSERIAL PRIMARY KEY,
    deadline_date TIMESTAMP,
    description_of_deadline_muving VARCHAR(1000),
    id_resolution BIGSERIAL
    );

ALTER TABLE deadline ADD CONSTRAINT fk_deadline_resolution FOREIGN KEY (id_resolution) REFERENCES resolution (id);

CREATE TABLE IF NOT EXISTS deadline_employee_singers (
    id_deadline BIGSERIAL,
    id_employee BIGSERIAL,
    FOREIGN KEY (id_deadline) REFERENCES deadline (id),
    FOREIGN KEY (id_employee) REFERENCES employee (id)
    );

comment on column deadline.id is 'Идентификатор дэдлайна';
comment on column deadline.deadline_date is 'Дата дэдлайна';
comment on column deadline.id_resolution is 'Номер резолюции';
comment on column deadline.description_of_deadline_muving is 'Причина переноса дедлайна';
