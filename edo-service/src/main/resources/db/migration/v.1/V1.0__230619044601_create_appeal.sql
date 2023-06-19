CREATE SCHEMA IF NOT EXISTS edo;

CREATE TABLE IF NOT EXISTS edo.appeal (
                                          id BIGSERIAL PRIMARY KEY,
                                          creation_date TIMESTAMP NOT NULL,
                                          archived_date TIMESTAMP,
                                          number VARCHAR(50) NOT NULL,
                                          annotation VARCHAR(1000)
);