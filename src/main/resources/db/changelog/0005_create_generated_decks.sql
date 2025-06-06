--liquibase formatted sql

--changeset Amir:0005_create_generated_decks

CREATE TABLE generated_decks
(
    id          SERIAL NOT NULL,
    player_tag  varchar(20) NOT NULL,
    description TEXT,
    cards       INTEGER[]   NOT NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status      Boolean,

    CONSTRAINT generated_decks_id_pk PRIMARY KEY (id),
    CONSTRAINT generated_decks_tag_fk FOREIGN KEY (player_tag) REFERENCES users (player_tag) ON DELETE CASCADE
);

--rollback DROP TABLE generated_decks;