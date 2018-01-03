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

DROP INDEX IF EXISTS users_id_idx;
CREATE INDEX users_id_idx ON users(id);

DROP INDEX IF EXISTS users_nickname_idx;
CREATE INDEX users_nickname_idx ON users(nickname);

DROP INDEX IF EXISTS users_lower_nickname_idx;
CREATE INDEX users_lower_nickname_idx ON users(LOWER(nickname));

DROP INDEX IF EXISTS users_nick_idx;
CREATE INDEX users_nick_idx ON users(nickname);

DROP INDEX IF EXISTS users_email_idx;
CREATE INDEX users_email_idx ON users(LOWER(email));

CREATE TABLE IF NOT EXISTS forums (
  id      SERIAL  PRIMARY KEY,
  posts   BIGINT  DEFAULT 0,
  slug    CITEXT  UNIQUE       NOT NULL,
  threads INTEGER DEFAULT 0,
  title   TEXT                 NOT NULL,
  user_id INTEGER REFERENCES users(id) ON DELETE CASCADE NOT NULL,
  user_nickname CITEXT               NOT NULL
);

DROP INDEX IF EXISTS forums_id_idx;
CREATE INDEX forums_id_idx ON forums(id);

DROP INDEX IF EXISTS forums_slug_user_idx;
CREATE INDEX forums_slug_user_idx ON forums(LOWER(slug), user_id);

DROP INDEX IF EXISTS forums_user_idx;
CREATE INDEX forums_user_idx ON forums(user_id);


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

DROP INDEX IF EXISTS threads_id_idx;
CREATE INDEX threads_id_idx ON threads(id);

DROP INDEX IF EXISTS threads_slug_idx;
CREATE INDEX threads_slug_idx ON threads(LOWER(slug));

DROP INDEX IF EXISTS threads_user_idx;
CREATE INDEX threads_user_idx ON threads(user_id);

DROP INDEX IF EXISTS threads_created_idx;
CREATE INDEX threads_created_idx ON threads(created);

DROP INDEX IF EXISTS threads_forum_idx;
CREATE INDEX threads_forum_idx ON threads(forum_id);

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

DROP INDEX IF EXISTS posts_id_idx;
CREATE INDEX posts_id_idx ON posts(id);

DROP INDEX IF EXISTS posts_user_idx;
CREATE INDEX posts_user_idx ON posts(user_id);

DROP INDEX IF EXISTS posts_forum_idx;
CREATE INDEX posts_forum_idx ON posts(forum_id);

DROP INDEX IF EXISTS posts_flat_idx;
CREATE INDEX posts_flat_idx ON posts(id, thread_id);

DROP INDEX IF EXISTS posts_tree_idx;
CREATE INDEX posts_tree_idx ON posts(thread_id, path);

DROP INDEX IF EXISTS posts_thread_parent_idx;
CREATE INDEX posts_thread_parent_idx ON posts(thread_id, parent_id);

DROP INDEX IF EXISTS posts_parent_tree_idx;
CREATE INDEX posts_parent_tree_idx ON posts(root_id, path, id);

CREATE TABLE IF NOT EXISTS votes (
  user_id   INTEGER     REFERENCES users(id)   ON DELETE CASCADE NOT NULL,
  thread_id INTEGER     REFERENCES threads(id) ON DELETE CASCADE NOT NULL,
  voice     SMALLINT     DEFAULT 0,
  UNIQUE (user_id, thread_id)
);

DROP INDEX IF EXISTS votes_user_thread_idx;
CREATE INDEX votes_user_thread_idx ON votes(user_id, thread_id);
