CREATE TABLE IF NOT EXISTS question
(
    id            BIGSERIAL PRIMARY KEY,
    creation_date TIMESTAMP   NOT NULL,
    archived_date TIMESTAMP,
    summary       VARCHAR(1000)
);
comment on column question.id is 'Идентификатор вопроса';
comment on column question.creation_date is 'Дата создания';
comment on column question.archived_date is 'Дата архивации';
comment on column question.summary is 'Краткое содержание вопроса'