--liquibase formatted sql

--changeset Amir:0006_create_cards

CREATE TABLE cards
(
    id         SERIAL NOT NULL,
    name       VARCHAR(100) NOT NULL,
    elixir     INTEGER NOT NULL,
    rarity     VARCHAR(20),
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    image_url TEXT NOT NULL,

    CONSTRAINT cards_id_pk PRIMARY KEY (id),
    CONSTRAINT cards_elixir_check CHECK (elixir >= 0 AND elixir <= 10),
    CONSTRAINT cards_rarity_check CHECK (rarity IN ('common', 'rare', 'epic', 'legendary', 'champion'))

);

--rollback DROP TABLE cards;