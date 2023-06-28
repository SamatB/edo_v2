ALTER TABLE employee ADD IF NOT EXISTS creator_id BIGSERIAL;
ALTER TABLE employee ADD IF NOT EXISTS singers_id BIGSERIAL;
ALTER TABLE employee ADD IF NOT EXISTS addressee_id BIGSERIAL;
ALTER TABLE employee ADD CONSTRAINT fk_employee_creator FOREIGN KEY (creator_id) REFERENCES appeal (id);
ALTER TABLE employee ADD CONSTRAINT fk_employee_singers FOREIGN KEY (singers_id) REFERENCES appeal (id);
ALTER TABLE employee ADD CONSTRAINT fk_employee_addressee FOREIGN KEY (addressee_id) REFERENCES appeal (id);