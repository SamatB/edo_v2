ALTER TABLE employee ADD COLUMN IF NOT EXISTS address_id BIGINT;
ALTER TABLE employee ADD CONSTRAINT fk_employee_address FOREIGN KEY (address_id) REFERENCES address (id);