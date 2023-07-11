ALTER TABLE department ADD COLUMN IF NOT EXISTS address_id BIGINT;
ALTER TABLE department ADD CONSTRAINT fk_department_address FOREIGN KEY (address_id) REFERENCES address (id);