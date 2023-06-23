CREATE TABLE IF NOT EXISTS employee (
                                        id BIGSERIAL PRIMARY KEY,
                                        first_name TEXT,
                                        last_name TEXT,
                                        middle_name TEXT,
                                        address TEXT,
                                        photo_url TEXT,
                                        fio_dative TEXT,
                                        fio_nominative TEXT,
                                        fio_genitive TEXT,
                                        external_id TEXT,
                                        phone TEXT,
                                        work_phone TEXT,
                                        birth_date DATE,
                                        username TEXT,
                                        creation_date TIMESTAMP WITH TIME ZONE,
                                        archived_date TIMESTAMP WITH TIME ZONE,
                                        appeal_id BIGSERIAL,
                                        FOREIGN KEY (appeal_id) REFERENCES edo_db."edo-2".appeal(id)
);

COMMENT ON COLUMN employee.first_name IS 'Имя сотрудника';
COMMENT ON COLUMN employee.last_name IS 'Фамилия сотрудника';
COMMENT ON COLUMN employee.middle_name IS 'Отчество сотрудника';
COMMENT ON COLUMN employee.address IS 'Адрес сотрудника';
COMMENT ON COLUMN employee.photo_url IS 'URL фотографии сотрудника';
COMMENT ON COLUMN employee.fio_dative IS 'ФИО сотрудника в дательном падеже';
COMMENT ON COLUMN employee.fio_nominative IS 'ФИО сотрудника в именительном падеже';
COMMENT ON COLUMN employee.fio_genitive IS 'ФИО сотрудника в родительном падеже';
COMMENT ON COLUMN employee.external_id IS 'Внешний идентификатор сотрудника';
COMMENT ON COLUMN employee.phone IS 'Номер мобильного телефона сотрудника';
COMMENT ON COLUMN employee.work_phone IS 'Рабочий номер телефона сотрудника';
COMMENT ON COLUMN employee.birth_date IS 'Дата рождения сотрудника';
COMMENT ON COLUMN employee.username IS 'Имя пользователя сотрудника';
COMMENT ON COLUMN employee.creation_date IS 'Дата создания сотрудника';
COMMENT ON COLUMN employee.archived_date IS 'Дата архивации сотрудника';
COMMENT ON COLUMN employee.appeal_id IS 'Внешний ключ на таблицу appeal';