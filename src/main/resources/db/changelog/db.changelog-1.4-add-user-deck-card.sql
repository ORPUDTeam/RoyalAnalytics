CREATE TABLE user_deck_cards (
                                 user_deck_id INTEGER NOT NULL REFERENCES user_decks(id) ON DELETE CASCADE,
                                 card_id      INTEGER NOT NULL REFERENCES cards(id)      ON DELETE CASCADE,
                                 PRIMARY KEY (user_deck_id, card_id)
);

CREATE TABLE generated_deck_cards (
                                      generated_deck_id INTEGER NOT NULL REFERENCES generated_decks(id) ON DELETE CASCADE,
                                      card_id           INTEGER NOT NULL REFERENCES cards(id)           ON DELETE CASCADE,
                                      PRIMARY KEY (generated_deck_id, card_id)
);