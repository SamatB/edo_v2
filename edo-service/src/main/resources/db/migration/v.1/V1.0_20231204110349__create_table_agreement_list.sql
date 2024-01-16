CREATE TABLE IF NOT EXISTS agreement_list
(
    id            BIGSERIAL PRIMARY KEY,
    creation_date TIMESTAMP   NOT NULL,
    sent_approval_date      TIMESTAMP  WITH TIME ZONE,
    sign_date               TIMESTAMP  WITH TIME ZONE,
    returned_date           TIMESTAMP  WITH TIME ZONE,
    refund_processing_date  TIMESTAMP  WITH TIME ZONE,
    archive_date            TIMESTAMP  WITH TIME ZONE,
    comment    VARCHAR(1000),
    appeal_id BIGINT,
    FOREIGN KEY (appeal_id) REFERENCES appeal (id)
);

comment on column agreement_list.id is 'Идентификатор обращения';
comment on column agreement_list.creation_date is 'Дата создания';
comment on column agreement_list.sent_approval_date is 'Дата направления на согласование';
comment on column agreement_list.sign_date is 'Дата подписания';
comment on column agreement_list.returned_date is 'Дата возврата на доработку';
comment on column agreement_list.refund_processing_date is 'Дата обработки возврата';
comment on column agreement_list.archive_date is ' Дата архивности';
comment on column agreement_list.comment is 'Комментарий для листа согласования';
comment on column agreement_list.appeal_id is 'Идентификатор обращения';