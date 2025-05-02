CREATE TABLE cards (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL,
                       elixir INTEGER CHECK (elixir BETWEEN 1 AND 10),
                       rarity VARCHAR(20) CHECK (rarity IN (
                                                            'Common','Rare','Epic','Legendary','Champion')),
                       arena INTEGER CHECK (arena BETWEEN 0 AND 20),
                       data JSONB NOT NULL,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);