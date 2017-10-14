CREATE EXTENSION IF NOT EXISTS CITEXT;

DROP TABLE IF EXISTS users CASCADE;

CREATE TABLE IF NOT EXISTS users (
  id        SERIAL  PRIMARY KEY,
  about     TEXT,
  email     TEXT    UNIQUE        NOT NULL,
  fullname  TEXT                  NOT NULL,
  nickname  CITEXT  UNIQUE

);

DROP TABLE IF EXISTS forums CASCADE;

CREATE TABLE IF NOT EXISTS forums (
  id      SERIAL  PRIMARY KEY,
  posts   BIGINT  DEFAULT 0,
  slug    CITEXT  UNIQUE       NOT NULL,
  threads INTEGER DEFAULT 0,
  title   TEXT                 NOT NULL,
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE NOT NULL
);

DROP TABLE IF EXISTS threads CASCADE;

CREATE TABLE IF NOT EXISTS threads (
  user_id   INTEGER       REFERENCES users(id)  ON DELETE CASCADE NOT NULL,
  created   TIMESTAMPTZ   DEFAULT now(),
  forum_id  INTEGER       REFERENCES forums(id) ON DELETE CASCADE NOT NULL,
  id        SERIAL        PRIMARY KEY,
  message   TEXT                                                  NOT NULL,
  slug      CITEXT        DEFAULT NULL,
  title     TEXT                                                  NOT NULL,
  votes     INTEGER       DEFAULT 0
);

DROP TABLE IF EXISTS posts CASCADE;

CREATE TABLE IF NOT EXISTS posts (
  user_id   INTEGER     REFERENCES users(id)  ON DELETE CASCADE NOT NULL,
  created   TIMESTAMPTZ DEFAULT now()                           NOT NULL,
  forum     INTEGER     REFERENCES forums(id) ON DELETE CASCADE NOT NULL,
  id        BIGSERIAL   PRIMARY KEY,
  is_edited BOOLEAN     DEFAULT FALSE                           NOT NULL,
  message   TEXT                                                NOT NULL,
  parent_id BIGINT      REFERENCES posts(id)   ON DELETE CASCADE DEFAULT NULL,
  full_path INTEGER[],
  thread_id INTEGER     REFERENCES threads(id) ON DELETE CASCADE
);

DROP TABLE IF EXISTS votes  CASCADE;

CREATE TABLE IF NOT EXISTS votes (
  user_id   INTEGER     REFERENCES users(id)   ON DELETE CASCADE NOT NULL,
  thread_id INTEGER     REFERENCES threads(id) ON DELETE CASCADE NOT NULL,
  voice     SMALLINT     DEFAULT 0,
  UNIQUE (user_id, thread_id)
);