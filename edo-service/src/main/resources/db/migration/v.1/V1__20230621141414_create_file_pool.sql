CREATE SCHEMA IF NOT EXISTS edo;

CREATE TABLE IF NOT EXISTS edo.file_pool (
    storage_file_id serial8 primary key,
    name VARCHAR(255) not null ,
    extension VARCHAR(10) not null ,
    size bigint not null ,
    page_count int not null ,
    upload_date timestamp not null ,
    archived_date timestamp
);

comment on column edo.file_pool.storage_file_id is 'id файла в таблице';
comment on column edo.file_pool.name is 'Имя файла';
comment on column edo.file_pool.extension is 'Расширение файла';
comment on column edo.file_pool.size is 'размер файла';
comment on column edo.file_pool.page_count is 'количество страниц в файле';
comment on column edo.file_pool.upload_date is 'дата загрузки на сервер';
comment on column edo.file_pool.archived_date is 'дата архивации';