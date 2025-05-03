--liquibase formatted sql

--changeset Amir:0008_create_tournament_registrations

CREATE TABLE tournament_registrations
(
    id            SERIAL      NOT NULL,
    tournament_id INTEGER     NOT NULL,
    player_tag    varchar(20) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT tournament_registrations_id_pk PRIMARY KEY (id),
    CONSTRAINT tournament_registrations_uq_id_tournament_id UNIQUE (tournament_id, id),
    CONSTRAINT tournament_registrations_tournament_id_fk FOREIGN KEY (tournament_id) REFERENCES tournaments_cache(id) ON DELETE CASCADE,
    CONSTRAINT tournament_registrations_tag_fk FOREIGN KEY (player_tag) REFERENCES users(player_tag) ON DELETE CASCADE
);

--rollback DROP TABLE tournament_registrations;