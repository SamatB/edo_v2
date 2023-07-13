CREATE TABLE IF NOT EXISTS notification (
                                            id BIGSERIAL PRIMARY KEY,
                                            creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
                                            type TEXT NOT NULL,
                                            employee_id BIGINT NOT NULL REFERENCES employee(id)
);

COMMENT ON COLUMN notification.creation_date IS 'Дата создания оповещения.';
COMMENT ON COLUMN notification.type IS 'Тип оповещения.';
COMMENT ON COLUMN notification.employee_id IS 'Идентификатор сотрудника, которому предназначено оповещение.';
