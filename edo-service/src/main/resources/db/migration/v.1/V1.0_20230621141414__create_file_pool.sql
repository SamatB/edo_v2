CREATE TABLE IF NOT EXISTS file_pool
(
    id              serial8 primary key,
    storage_file_id uuid,
    name            VARCHAR(255) not null,
    extension       VARCHAR(4)   not null,
    size            bigint       not null,
    page_count      int          not null,
    upload_date     timestamptz    not null,
    archived_date   timestamptz
);

comment on column file_pool.id is 'id файла в таблице';
comment on column file_pool.storage_file_id is 'uuid файла';
comment on column file_pool.name is 'Имя файла';
comment on column file_pool.extension is 'Расширение файла';
comment on column file_pool.size is 'размер файла';
comment on column file_pool.page_count is 'количество страниц в файле';
comment on column file_pool.upload_date is 'дата загрузки на сервер';
comment on column file_pool.archived_date is 'дата архивации';