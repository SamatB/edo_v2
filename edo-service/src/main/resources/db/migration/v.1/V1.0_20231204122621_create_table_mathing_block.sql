CREATE TABLE IF NOT EXISTS matching_block(

    id             BIGSERIAL PRIMARY KEY,
    number         BIGINT NOT NULL,
    type           TEXT NOT NULL,
    agreement_list_id BIGINT,
    FOREIGN KEY (agreement_list_id) REFERENCES agreement_list (id)



    );
comment on column matching_block.id is 'Идентификатор блока согласования';
comment on column matching_block.number is 'Номер согласования';
comment on column matching_block.type is 'Тип согласования';
comment on column matching_block.agreement_list_id is 'Связь с листом согласования';

CREATE TABLE IF NOT EXISTS matching_block_participant (
                                                 matching_block_id BIGINT  REFERENCES matching_block(id),
                                                 participant_id BIGINT  REFERENCES participant(id),
                                                   PRIMARY KEY (matching_block_id, participant_id)
);

comment on column matching_block_participant.matching_block_id is 'Идентификатор блока согласования';
comment on column matching_block_participant.participant_id is 'Идентификатор участника согласования';
