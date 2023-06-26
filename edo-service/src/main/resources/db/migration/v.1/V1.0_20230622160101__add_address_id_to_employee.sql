ALTER TABLE employee ADD COLUMN IF NOT EXISTS address_id BIGINT;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_employee_address') THEN
ALTER TABLE employee ADD CONSTRAINT fk_employee_address FOREIGN KEY (address_id) REFERENCES address (id);
END IF;
END $$;