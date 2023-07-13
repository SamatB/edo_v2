CREATE TABLE IF NOT EXISTS resolution (
                                          id BIGSERIAL PRIMARY KEY,
                                          creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                          archived_date TIMESTAMP WITH TIME ZONE,
                                          last_action_date TIMESTAMP WITH TIME ZONE,
                                          type TEXT NOT NULL,
                                          creator_id BIGINT NOT NULL REFERENCES employee(id),
                                          signer_id BIGINT NOT NULL REFERENCES employee(id),
                                          curator_id BIGINT NOT NULL REFERENCES employee(id),
                                          serial_number INT NOT NULL
);

COMMENT ON COLUMN resolution.creation_date IS 'Дата создания резолюции.';
COMMENT ON COLUMN resolution.archived_date IS 'Дата архивации резолюции.';
COMMENT ON COLUMN resolution.last_action_date IS 'Дата последнего действия по резолюции.';
COMMENT ON COLUMN resolution.type IS 'Тип резолюции.';
COMMENT ON COLUMN resolution.creator_id IS 'Идентификатор создателя резолюции.';
COMMENT ON COLUMN resolution.signer_id IS 'Идентификатор подписывающего резолюцию.';
COMMENT ON COLUMN resolution.curator_id IS 'Идентификатор куратора резолюции.';
COMMENT ON COLUMN resolution.serial_number IS 'Серийный номер резолюции.';

CREATE TABLE IF NOT EXISTS resolution_executor (
                                          resolution_id BIGINT NOT NULL REFERENCES resolution(id),
                                          employee_id BIGINT NOT NULL REFERENCES employee(id),
                                          PRIMARY KEY (resolution_id, employee_id)
);

COMMENT ON COLUMN resolution_executor.resolution_id IS 'Идентификатор резолюции.';
COMMENT ON COLUMN resolution_executor.employee_id IS 'Идентификатор исполнителя резолюции.';