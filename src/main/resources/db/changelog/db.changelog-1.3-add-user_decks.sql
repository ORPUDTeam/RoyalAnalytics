CREATE TABLE user_decks
(
    id         SERIAL PRIMARY KEY,
    user_id    INTEGER      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    name       VARCHAR(100) NOT NULL,
    card_id                 NOT NULL, -- массив id карт
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
