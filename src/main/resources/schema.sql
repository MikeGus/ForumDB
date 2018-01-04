-- noinspection SqlResolveForFile

SET SYNCHRONOUS_COMMIT = 'off';

CREATE EXTENSION IF NOT EXISTS CITEXT;

DROP TABLE IF EXISTS users  CASCADE;
DROP TABLE IF EXISTS forums CASCADE;
DROP TABLE IF EXISTS threads CASCADE;
DROP TABLE IF EXISTS posts  CASCADE;
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
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE NOT NULL,
  user_nickname CITEXT               NOT NULL
);

CREATE TABLE IF NOT EXISTS forum_visitors (
  user_id     INTEGER REFERENCES users(id) ON DELETE CASCADE NOT NULL,
  forum_id    INTEGER REFERENCES forums(id) ON DELETE CASCADE NOT NULL,
  UNIQUE (user_id, forum_id)
);

CREATE TABLE IF NOT EXISTS threads (
  user_id   INTEGER       REFERENCES users(id)  ON DELETE CASCADE NOT NULL,
  user_nickname CITEXT                                            NOT NULL,
  created   TIMESTAMPTZ   DEFAULT now(),
  forum_id  INTEGER       REFERENCES forums(id) ON DELETE CASCADE NOT NULL,
  forum_slug    CITEXT                                            NOT NULL,
  id        SERIAL        PRIMARY KEY,
  message   TEXT                                                  NOT NULL,
  slug      CITEXT        UNIQUE DEFAULT NULL,
  title     TEXT                                                  NOT NULL,
  votes     INTEGER       DEFAULT 0
);

CREATE TABLE IF NOT EXISTS posts (
  user_id   INTEGER     REFERENCES users(id)  ON DELETE CASCADE NOT NULL,
  user_nickname  CITEXT                                         NOT NULL,
  created   TIMESTAMPTZ DEFAULT now()                           NOT NULL,
  forum_id    INTEGER     REFERENCES forums(id) ON DELETE CASCADE NOT NULL,
  forum_slug  CITEXT                                            NOT NULL,
  id        BIGSERIAL   PRIMARY KEY,
  is_edited BOOLEAN     NOT NULL DEFAULT FALSE,
  message   TEXT                                                NOT NULL,
  parent_id BIGINT      DEFAULT 0                               NOT NULL,
  thread_id INTEGER     REFERENCES threads(id) ON DELETE CASCADE,
  path      BIGINT[]    NOT NULL,
  root_id   BIGINT      NOT NULL
);

CREATE TABLE IF NOT EXISTS votes (
  user_id   INTEGER     REFERENCES users(id)   ON DELETE CASCADE NOT NULL,
  thread_id INTEGER     REFERENCES threads(id) ON DELETE CASCADE NOT NULL,
  voice     SMALLINT     DEFAULT 0,
  UNIQUE (user_id, thread_id)
);

DROP INDEX IF EXISTS forum_lower_slug_idx;
CREATE INDEX forum_lower_slug_idx ON forums(LOWER(slug));

DROP INDEX IF EXISTS users_lower_nickname_idx;
CREATE INDEX users_lower_nickname_idx ON users(LOWER(nickname));

DROP INDEX IF EXISTS users_nickname_idx;
CREATE INDEX users_nickname_idx ON users(nickname);

DROP INDEX IF EXISTS threads_lower_forum_slug_created_idx;
CREATE INDEX threads_lower_forum_slug_created_idx ON threads(LOWER(forum_slug), created);

DROP INDEX IF EXISTS threads_forum_id_idx;
CREATE INDEX threads_forum_id_idx ON threads(forum_id);

DROP INDEX IF EXISTS posts_user_id_idx;
CREATE INDEX posts_user_id_idx ON posts(user_id);

DROP INDEX IF EXISTS posts_thread_id_id_idx;
CREATE INDEX posts_thread_id_id_idx ON posts(thread_id, id);

DROP INDEX IF EXISTS posts_thread_id_path_idx;
CREATE INDEX posts_thread_id_path_idx ON posts(thread_id, path);

DROP INDEX IF EXISTS posts_root_id_path_idx;
CREATE INDEX posts_root_id_path_idx ON posts(root_id, path);

DROP INDEX IF EXISTS posts_thread_id_parent_id_path_idx;
CREATE INDEX posts_thread_id_parent_id_path ON posts(thread_id, parent_id, path);

DROP INDEX IF EXISTS posts_path_root_id_idx;
CREATE INDEX posts_path_root_id_idx ON POSTS(path, root_id);

DROP INDEX IF EXISTS forum_visitors_forum_id_idx;
CREATE INDEX forum_visitors_forum_id_idx ON forum_visitors(forum_id);