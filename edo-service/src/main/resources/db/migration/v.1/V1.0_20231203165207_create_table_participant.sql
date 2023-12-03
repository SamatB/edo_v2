CREATE TABLE IF NOT EXISTS participant (
    id BIGSERIAL PRIMARY KEY,
    type TEXT,
    create_date TIMESTAMP WITH TIME ZONE,
    until_date TIMESTAMP WITH TIME ZONE,
    accept_date TIMESTAMP WITH TIME ZONE,
    expired_date TIMESTAMP WITH TIME ZONE,
    number BIGINT,
    employee_id BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee (id)
);

COMMENT ON COLUMN  participant.id IS 'Первичный ключ участника согласования';
COMMENT ON COLUMN participant.type IS 'Тип участника согласования';
COMMENT ON COLUMN participant.create_date IS 'Дата создания участника';
COMMENT ON COLUMN participant.until_date IS 'Дата, до которой должно быть исполнено';
COMMENT ON COLUMN participant.accept_date IS 'Дата получения обращения';
COMMENT ON COLUMN participant.expired_date IS 'Дата завершения действия';
COMMENT ON COLUMN participant.number IS 'Номер по порядку согласования и порядку отображения на UI';
COMMENT ON COLUMN participant.employee_id IS 'Внешний ключ на сотрудника';

