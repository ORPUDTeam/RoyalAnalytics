--liquibase formatted sql

--changeset Amir:0002_create_user_cache
CREATE TABLE user_cache
(
    player_tag      VARCHAR(20) NOT NULL,
    trophies        INTEGER,
    rewards         JSONB,
    updated_at      TIMESTAMP,
    main_deck_id    INTEGER,

    CONSTRAINT user_cache_pk PRIMARY KEY (player_tag),
    CONSTRAINT user_cache_deck_fk FOREIGN KEY (main_deck_id)
        REFERENCES user_decks (id) ON DELETE SET NULL
);

--rollback DROP TABLE user_cache;