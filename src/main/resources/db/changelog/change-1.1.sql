--liquibase formatted sql

--changeset petr:0009_remove_cards_array_from_generated_decks
ALTER TABLE generated_decks DROP COLUMN cards;

CREATE TABLE generated_deck_cards (
                                      generated_deck_id INTEGER NOT NULL,
                                      card_id INTEGER NOT NULL,

                                      PRIMARY KEY (generated_deck_id, card_id),
                                      CONSTRAINT fk_generated_deck FOREIGN KEY (generated_deck_id) REFERENCES generated_decks(id) ON DELETE CASCADE,
                                      CONSTRAINT fk_generated_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

--rollback ALTER TABLE generated_decks ADD COLUMN cards INTEGER[];
--rollback DROP TABLE generated_deck_cards;