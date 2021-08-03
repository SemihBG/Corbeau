DROP TABLE IF EXISTS comments;
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS moderators;
DROP TABLE IF EXISTS roles;

CREATE TABLE IF NOT EXISTS roles
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS moderators
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)  NOT NULL UNIQUE,
    password   VARCHAR(256) NOT NULL,
    email      VARCHAR(128) NOT NULL UNIQUE,
    role_id    INT          NOT NULL,
    created_at BIGINT       NOT NULL DEFAULT 0,
    updated_at BIGINT       NOT NULL DEFAULT 0,
    FOREIGN KEY (role_id) REFERENCES roles (id)
);
CREATE TABLE IF NOT EXISTS subjects
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32) NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT      NOT NULL DEFAULT 0,
    updated_at BIGINT      NOT NULL DEFAULT 0
);
CREATE TABLE IF NOT EXISTS posts
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(64) NOT NULL,
    content    LONGTEXT,
    subject_id INT         NOT NULL,
    activated  BOOLEAN              DEFAULT FALSE,
    endpoint   VARCHAR(64) NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT      NOT NULL DEFAULT 0,
    updated_at BIGINT      NOT NULL DEFAULT 0,
    UNIQUE (title, subject_id),
    FOREIGN KEY (subject_id) REFERENCES subjects (id)
);
CREATE TABLE IF NOT EXISTS images
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(64) NOT NULL,
    extension  VARCHAR(4)  NOT NULL,
    width      INT         NOT NULL,
    height     INT         NOT NULL,
    size       LONG        NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT      NOT NULL DEFAULT 0,
    updated_at BIGINT      NOT NULL DEFAULT 0,
    UNIQUE (name, extension)
);
CREATE TABLE IF NOT EXISTS comments
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)  NOT NULL,
    surname    VARCHAR(32)  NOT NULL,
    email      VARCHAR(64)  NOT NULL,
    content    VARCHAR(256) NOT NULL,
    post_id    INT          NOT NULL,
    created_at BIGINT       NOT NULL DEFAULT 0,
    updated_at BIGINT       NOT NULL DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES posts (id)
);
