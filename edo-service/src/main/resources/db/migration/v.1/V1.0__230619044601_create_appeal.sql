CREATE SCHEMA IF NOT EXISTS edo;

CREATE TABLE IF NOT EXISTS edo.appeal (
                                          id BIGSERIAL PRIMARY KEY,
                                          creation_date TIMESTAMP NOT NULL,
                                          archived_date TIMESTAMP,
                                          number VARCHAR(50) NOT NULL,
                                          annotation VARCHAR(1000)
);

comment on column edo.appeal.id is 'Идентификатор обращения';
comment on column edo.appeal.creation_date is 'Дата создания';
comment on column edo.appeal.archived_date is 'Дата архивации';
comment on column edo.appeal.number is 'Номер обращения';
comment on column edo.appeal.annotation is 'Описание обращения';
