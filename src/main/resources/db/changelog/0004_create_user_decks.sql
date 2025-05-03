--liquibase formatted sql

--changeset Amir:0004_create_user_decks

CREATE TABLE user_decks
(
    id         SERIAL NOT NULL,
    player_tag varchar(20)  NOT NULL,
    name       VARCHAR(100) NOT NULL,
    status     boolean,
    cards      INTEGER[]    NOT NULL, -- массив id карт
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ---------------------------------------------------
    CONSTRAINT user_decks_id_pk PRIMARY KEY (id),
    CONSTRAINT user_decks_tag_fk FOREIGN KEY (player_tag) REFERENCES users (player_tag) ON DELETE CASCADE
);

--rollback DROP TABLE user_decks;