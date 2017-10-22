-- noinspection SqlResolveForFile

CREATE EXTENSION IF NOT EXISTS CITEXT;

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS forums CASCADE;
DROP TABLE IF EXISTS threads CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS votes  CASCADE;

CREATE TABLE IF NOT EXISTS users (
  id        SERIAL  PRIMARY KEY,
  about     TEXT,
  email     CITEXT    UNIQUE        NOT NULL,
  fullname  TEXT                  NOT NULL,
  nickname  CITEXT  UNIQUE
);

CREATE TABLE IF NOT EXISTS forums (
  id      SERIAL  PRIMARY KEY,
  posts   BIGINT  DEFAULT 0,
  slug    CITEXT  UNIQUE       NOT NULL,
  threads INTEGER DEFAULT 0,
  title   TEXT                 NOT NULL,
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE NOT NULL
);

CREATE TABLE IF NOT EXISTS threads (
  user_id   INTEGER       REFERENCES users(id)  ON DELETE CASCADE NOT NULL,
  created   TIMESTAMPTZ   DEFAULT now(),
  forum_id  INTEGER       REFERENCES forums(id) ON DELETE CASCADE NOT NULL,
  id        SERIAL        PRIMARY KEY,
  message   TEXT                                                  NOT NULL,
  slug      CITEXT        UNIQUE DEFAULT NULL,
  title     TEXT                                                  NOT NULL,
  votes     INTEGER       DEFAULT 0
);


CREATE TABLE IF NOT EXISTS posts (
  user_id   INTEGER     REFERENCES users(id)  ON DELETE CASCADE NOT NULL,
  created   TIMESTAMPTZ DEFAULT now()                           NOT NULL,
  forum_id    INTEGER     REFERENCES forums(id) ON DELETE CASCADE NOT NULL,
  id        BIGSERIAL   PRIMARY KEY,
  is_edited BOOLEAN     NOT NULL DEFAULT FALSE,
  message   TEXT                                                NOT NULL,
  parent_id BIGINT      DEFAULT 0,
  thread_id INTEGER     REFERENCES threads(id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS votes (
  user_id   INTEGER     REFERENCES users(id)   ON DELETE CASCADE NOT NULL,
  thread_id INTEGER     REFERENCES threads(id) ON DELETE CASCADE NOT NULL,
  voice     SMALLINT     DEFAULT 0,
  UNIQUE (user_id, thread_id)
);