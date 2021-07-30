DROP TABLE IF EXISTS testdb.comments;
DROP TABLE IF EXISTS testdb.posts;
DROP TABLE IF EXISTS testdb.subjects;
DROP TABLE IF EXISTS testdb.images;
DROP TABLE IF EXISTS testdb.moderators;
DROP TABLE IF EXISTS testdb.roles;

CREATE TABLE IF NOT EXISTS testdb.roles
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS testdb.moderators
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL UNIQUE,
    password   VARCHAR(256)    NOT NULL,
    email      VARCHAR(128)    NOT NULL UNIQUE,
    role_id    INT             NOT NULL,
    created_at BIGINT NOT NULL DEFAULT 0,
    updated_at BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (role_id) REFERENCES testdb.roles (id)
);
CREATE TABLE IF NOT EXISTS testdb.subjects
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT NOT NULL DEFAULT 0,
    updated_at BIGINT NOT NULL DEFAULT 0
);
CREATE TABLE IF NOT EXISTS testdb.posts
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(64)     NOT NULL,
    content    LONGTEXT,
    subject_id INT             NOT NULL,
    activated  BOOLEAN                  DEFAULT FALSE,
    endpoint   VARCHAR(64)     NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT NOT NULL DEFAULT 0,
    updated_at BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (subject_id) REFERENCES testdb.subjects (id)
);
CREATE TABLE IF NOT EXISTS testdb.images
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(64)     NOT NULL,
    extension  VARCHAR(4)      NOT NULL,
    width      INT             NOT NULL,
    height     INT             NOT NULL,
    size       LONG            NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT NOT NULL DEFAULT 0,
    updated_at BIGINT NOT NULL DEFAULT 0
);
CREATE TABLE IF NOT EXISTS testdb.comments
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL,
    surname    VARCHAR(32)     NOT NULL,
    email      VARCHAR(64)     NOT NULL,
    content    VARCHAR(256)    NOT NULL,
    post_id    INT             NOT NULL,
    created_at BIGINT NOT NULL DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES testdb.posts (id)
);




