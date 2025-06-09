--liquibase formatted sql

--changeset petr:0007_remove_cards_array_and_create_many_to_many

ALTER TABLE user_decks DROP COLUMN cards;

CREATE TABLE deck_cards (
                            deck_id INTEGER NOT NULL,
                            card_id INTEGER NOT NULL,

                            PRIMARY KEY (deck_id, card_id),
                            CONSTRAINT fk_deck FOREIGN KEY (deck_id) REFERENCES user_decks(id) ON DELETE CASCADE,
                            CONSTRAINT fk_card FOREIGN KEY (card_id) REFERENCES cards(id) ON DELETE CASCADE
);

--rollback ALTER TABLE user_decks ADD COLUMN cards INTEGER[];
--rollback DROP TABLE deck_cards;