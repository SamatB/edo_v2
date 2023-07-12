ALTER TABLE appeal
    ADD IF NOT EXISTS creator_id BIGSERIAL;
ALTER TABLE appeal
    ADD CONSTRAINT fk_employee_creator FOREIGN KEY (creator_id) REFERENCES employee (id);
CREATE TABLE IF NOT EXISTS appeal_employee_singers
(
    id_appeal   BIGSERIAL,
    id_employee BIGSERIAL,
    FOREIGN KEY (id_appeal) REFERENCES appeal (id),
    FOREIGN KEY (id_employee) REFERENCES employee (id)
);


CREATE TABLE IF NOT EXISTS appeal_employee_addressee
(
    id_appeal   BIGSERIAL,
    id_employee BIGSERIAL,
    FOREIGN KEY (id_appeal) REFERENCES appeal (id),
    FOREIGN KEY (id_employee) REFERENCES employee (id)
);