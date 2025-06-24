DROP TABLE IF EXISTS kasukabe_shogi_db.public.lists;
DROP TABLE IF EXISTS kasukabe_shogi_db.public.templates;
DROP TABLE IF EXISTS kasukabe_shogi_db.public.pieces;
DROP TABLE IF EXISTS kasukabe_shogi_db.public.players;

CREATE TABLE IF NOT EXISTS kasukabe_shogi_db.public.players (
    id   INTEGER PRIMARY KEY,
    name VARCHAR(20),
    password VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS kasukabe_shogi_db.public.pieces (
    id       INTEGER PRIMARY KEY,
    name     VARCHAR(2),
    promoted_name VARCHAR(2),
    promoted BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS kasukabe_shogi_db.public.templates (
    id         INTEGER PRIMARY KEY,
    player_id  INTEGER REFERENCES kasukabe_shogi_db.public.players(id),
    name       VARCHAR(20),
    play_first BOOLEAN NOT NULL,
    available  BOOLEAN NOT NULL,
    content    VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS kasukabe_shogi_db.public.lists (
    id           INTEGER PRIMARY KEY,
    template_id  INTEGER REFERENCES kasukabe_shogi_db.public.templates(id),
    num          INTEGER,
    before_id    INTEGER,
    before_x     INTEGER,
    before_y     INTEGER,
    target_x     INTEGER,
    target_y     INTEGER,
    is_promoted  BOOLEAN
);
