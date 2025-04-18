CREATE SCHEMA IF NOT EXISTS customer;

CREATE TABLE IF NOT EXISTS customer.auth (
    id              BIGSERIAL       PRIMARY KEY NOT NULL,
    refresh_token   TEXT,
    expiration_time TIMESTAMP
);

CREATE TABLE IF NOT EXISTS customer.credential (
    id              BIGSERIAL       PRIMARY KEY NOT NULL,
    email           VARCHAR(64)     NOT NULL UNIQUE,
    phone           VARCHAR(12)     NOT NULL UNIQUE,
    password        TEXT            NOT NULL,
    role            VARCHAR(30)     NOT NULL,
    auth_id         BIGINT          NOT NULL,
    CONSTRAINT fk_auth FOREIGN KEY (auth_id) REFERENCES customer.auth (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS customer.info (
    id              BIGSERIAL       PRIMARY KEY NOT NULL,
    first_name      VARCHAR(60)     NOT NULL,
    last_name       VARCHAR(60)     NOT NULL,
    father_name     VARCHAR(60),
    avatar_url      VARCHAR(60)     NOT NULL,
    birth_date      TIMESTAMP,
    credential_id   BIGINT          NOT NULL,
    CONSTRAINT fk_credential FOREIGN KEY (credential_id) REFERENCES customer.credential (id) ON DELETE CASCADE
);