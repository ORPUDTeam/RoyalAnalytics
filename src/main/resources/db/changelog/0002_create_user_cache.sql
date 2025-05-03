--liquibase formatted sql

--changeset Amir:0002_create_user_cache

CREATE TABLE user_cache (
    name VARCHAR(50),
    player_tag VARCHAR(20) NOT NULL,
    trophies INTEGER,
    current_deck_id INTEGER,
    rewards JSONB,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,


    CONSTRAINT user_cache_tag_pk PRIMARY KEY (player_tag),
    CONSTRAINT user_cache_tag_fk FOREIGN KEY (player_tag) REFERENCES users(player_tag)
    );

--rollback DROP TABLE user_cache;