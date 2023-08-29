CREATE TABLE IF NOT EXISTS nomenclature
(
    id                      BIGSERIAL PRIMARY KEY,
    nomenclature_index      VARCHAR(20),
    description             VARCHAR(500),
    template                VARCHAR(200),
    current_value           BIGINT,
    archived_date           TIMESTAMP WITHOUT TIME ZONE,
    creation_date           TIMESTAMP WITHOUT TIME ZONE,
    department_id           BIGINT REFERENCES department(id)
);

comment ON COLUMN nomenclature.id IS 'Идентификатор номенклатуры';
comment ON COLUMN nomenclature.nomenclature_index IS 'Код-номер обращения (первая часть номера)';
comment ON COLUMN nomenclature.description IS 'Описание ';
comment ON COLUMN nomenclature.template IS 'Шаблон номера документа ';
comment ON COLUMN nomenclature.current_value IS 'Текущее максимальное значение для номера в рамках номенклатурного индекса';
comment ON COLUMN nomenclature.archived_date IS 'Дата переноса в архив';
comment ON COLUMN nomenclature.creation_date IS 'Дата создания';
comment ON COLUMN nomenclature.department_id IS 'Идентификатор департамента';