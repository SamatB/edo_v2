
ALTER TABLE question
    ADD COLUMN IF NOT EXISTS appeal_id BIGINT,
    ADD CONSTRAINT fk_appeal_question FOREIGN KEY (appeal_id) REFERENCES appeal (id);

ALTER TABLE resolution
    ADD COLUMN IF NOT EXISTS question_id BIGINT,
    ADD CONSTRAINT fk_question_resolution FOREIGN KEY (question_id) REFERENCES question (id);