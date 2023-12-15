CREATE TABLE IF NOT EXISTS matching_block(

    id             BIGSERIAL PRIMARY KEY,
    number         BIGINT NOT NULL,
    type           TEXT NOT NULL

    );
comment on column matching_block.id is 'Идентификатор блока согласования';
comment on column matching_block.number is 'Номер согласования';
comment on column matching_block.type is 'Тип согласования';

