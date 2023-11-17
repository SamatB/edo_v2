CREATE TABLE IF NOT EXISTS deadline
(
    id            BIGSERIAL PRIMARY KEY,
    deadline_date TIMESTAMP with time zone,
    description_of_deadline_moving VARCHAR(1000),
    id_resolution BIGINT REFERENCES resolution(id)
    );

ALTER TABLE deadline ADD CONSTRAINT fk_deadline_resolution FOREIGN KEY (id_resolution) REFERENCES resolution (id);

comment on column deadline.id is 'Идентификатор дэдлайна';
comment on column deadline.deadline_date is 'Дата дэдлайна';
comment on column deadline.id_resolution is 'Номер резолюции';
comment on column deadline.description_of_deadline_moving is 'Причина переноса дедлайна';
