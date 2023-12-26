ALTER TABLE agreement_list
    ADD COLUMN IF NOT EXISTS initiator_id BIGINT,
    ADD CONSTRAINT fk_participant_agreement_list FOREIGN KEY (initiator_id) REFERENCES participant (id);



CREATE TABLE IF NOT EXISTS matching_block_signatory
(
    id_agreement_list BIGINT REFERENCES agreement_list (id),
    id_matching_block BIGINT REFERENCES matching_block (id),
    PRIMARY KEY (id_agreement_list, id_matching_block)
);


CREATE TABLE IF NOT EXISTS matching_block_coordinating
(
    id_agreement_list BIGINT REFERENCES agreement_list (id),
    id_matching_block BIGINT REFERENCES matching_block (id),
    PRIMARY KEY (id_agreement_list, id_matching_block)
);


CREATE TABLE IF NOT EXISTS matching_block_participant
(
    matching_block_id BIGINT REFERENCES matching_block (id),
    participant_id    BIGINT REFERENCES participant (id),
    PRIMARY KEY (matching_block_id, participant_id)
);


