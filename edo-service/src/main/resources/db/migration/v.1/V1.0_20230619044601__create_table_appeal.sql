CREATE TABLE IF NOT EXISTS appeal
(
    id            BIGSERIAL PRIMARY KEY,
    creation_date TIMESTAMP   NOT NULL,
    archived_date TIMESTAMP,
    number        VARCHAR(50) NOT NULL,
    annotation    VARCHAR(1000)
);
comment on column appeal.id is 'Идентификатор обращения';
comment on column appeal.creation_date is 'Дата создания';
comment on column appeal.archived_date is 'Дата архивации';
comment on column appeal.number is 'Номер обращения';
comment on column appeal.annotation is 'Описание обращения';