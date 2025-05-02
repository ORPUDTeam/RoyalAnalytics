CREATE TABLE generated_decks (
                                 id SERIAL PRIMARY KEY,
                                 user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                                 description TEXT,
                                 cards INTEGER[] NOT NULL,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);