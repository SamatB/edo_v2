CREATE TABLE IF NOT EXISTS tag (
        id            BIGSERIAL PRIMARY KEY,
        name    VARCHAR(500) NOT NULL,
        creator_id BIGINT NOT NULL REFERENCES employee(id),
        creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
        appeal_id BIGINT REFERENCES appeal(id)
        );

    comment on column tag.id is 'Идентификатор метки';
    comment on column tag.name is 'Название метки';
    comment on column tag.creator_id is 'Идентификатор создателя метки';
    COMMENT ON COLUMN tag.creation_date IS 'Дата создания метки.';
    comment on column tag.appeal_id is 'Идентификатор создателя метки';

