--liquibase formatted sql

--changeset Amir:0007_create_tournaments_cache

CREATE TABLE tournaments_cache
(
    id          SERIAL NOT NULL ,
    name        VARCHAR(100) NOT NULL,
    description TEXT,
    duration    interval,
    game_mod    VARCHAR(20),
    password    varchar(50) default null,
    start_time TIMESTAMP NOT NULL,

    CONSTRAINT tournaments_cache_id_pk PRIMARY KEY (id)

);

--rollback DROP TABLE tournaments_cache;