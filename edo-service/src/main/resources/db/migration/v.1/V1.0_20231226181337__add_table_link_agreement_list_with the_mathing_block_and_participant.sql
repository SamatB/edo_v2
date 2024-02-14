ALTER TABLE agreement_list
    ADD COLUMN IF NOT EXISTS initiator_id                   BIGINT,
    ADD CONSTRAINT fk_agreement_list_initiator FOREIGN KEY (initiator_id) REFERENCES participant (id),
    ADD COLUMN IF NOT EXISTS matching_block_signatory_id    BIGINT,
    ADD CONSTRAINT fk_agreement_list_signatory FOREIGN KEY (matching_block_signatory_id) REFERENCES matching_block (id),
    ADD COLUMN IF NOT EXISTS matching_block_coordinating_id BIGINT,
    ADD CONSTRAINT fk_agreement_list_coordinating FOREIGN KEY (matching_block_coordinating_id) REFERENCES matching_block (id);

ALTER TABLE matching_block
    ADD COLUMN IF NOT EXISTS participant_id    BIGINT,
    ADD CONSTRAINT fk_matching_block_participant FOREIGN KEY (participant_id) REFERENCES participant (id),
    ADD COLUMN IF NOT EXISTS agreement_list_id BIGINT,
    ADD FOREIGN KEY (agreement_list_id) REFERENCES agreement_list (id);


