--liquibase formatted sql

--changeset Amir:0003_create_rating_changes

CREATE TABLE rating_changes
(
    id         SERIAL PRIMARY KEY,
    player_tag varchar(20) NOT NULL,
    rating     INTEGER     NOT NULL,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -----------------------------------------------
    CONSTRAINT rating_changes_tag_fk FOREIGN KEY (player_tag) REFERENCES users (player_tag) ON DELETE CASCADE
);

--rollback DROP TABLE rating_changes;