ALTER TABLE employee ADD COLUMN IF NOT EXISTS department_id BIGINT;
ALTER TABLE employee ADD CONSTRAINT fk_employee_department FOREIGN KEY (department_id) REFERENCES department (id);