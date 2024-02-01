CREATE TABLE IF NOT EXISTS report
(
    id            BIGSERIAL     PRIMARY KEY,
    creation_date TIMESTAMP     NOT NULL,
    comment       VARCHAR(1000),
    result        BOOLEAN,
    executor_id   BIGINT,
    resolution_id BIGINT,
    FOREIGN KEY (executor_id) REFERENCES employee(id),
    FOREIGN KEY (resolution_id) REFERENCES resolution(id)
);
comment on column report.id is 'Идентификатор отчета';
comment on column report.creation_date is 'Дата создания отчета';
comment on column report.comment is 'комментарий к отчету';
comment on column report.result is 'итог выполнения(выполнено или не выполнено)';
comment on column report.executor_id is 'идентификатор исполнителя';
comment on column report.resolution_id is 'идентификатор резолюции';
