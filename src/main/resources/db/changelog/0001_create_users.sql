--liquibase formatted sql

--changeset Amir:0001_create_users

CREATE TABLE users
(
    username      VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR            NOT NULL,
    player_tag    VARCHAR(20),
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT users_tag_pk PRIMARY KEY (player_tag)

);

--rollback DROP TABLE users;