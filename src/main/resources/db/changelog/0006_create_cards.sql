--liquibase formatted sql

--changeset Amir:0006_create_cards

CREATE TABLE cards
(
    id         SERIAL NOT NULL,
    name       VARCHAR(100) NOT NULL,
    elixir     INTEGER NOT NULL,
    rarity     VARCHAR(20),
    arena      INTEGER NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT cards_id_pk PRIMARY KEY (id),
    CONSTRAINT cards_elixir_check CHECK (elixir >= 1 AND elixir <= 10),
    CONSTRAINT cards_rarity_check CHECK (rarity IN ('Common', 'Rare', 'Epic', 'Legendary', 'Champion')),
    CONSTRAINT cards_arena_check CHECK (arena >= 0 AND arena <= 20)

);

--rollback DROP TABLE cards;