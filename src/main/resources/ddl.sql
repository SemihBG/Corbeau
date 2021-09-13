CREATE TABLE IF NOT EXISTS corbeau.roles
(
    id   INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS corbeau.moderators
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL UNIQUE,
    password   VARCHAR(256)    NOT NULL,
    email      VARCHAR(128)    NOT NULL UNIQUE,
    role_id    INT             NOT NULL,
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    FOREIGN KEY (role_id) REFERENCES corbeau.roles (id)
);

CREATE TABLE IF NOT EXISTS corbeau.subjects
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS corbeau.posts
(
    id                 INT PRIMARY KEY AUTO_INCREMENT,
    title              VARCHAR(64)     NOT NULL,
    content            LONGTEXT,
    subject_id         INT             NOT NULL,
    activated          BOOLEAN                  DEFAULT FALSE,
    endpoint           VARCHAR(64)     NOT NULL UNIQUE,
    thumbnail_endpoint VARCHAR(64)              DEFAULT NULL,
    description        VARCHAR(256)             DEFAULT NULL,
    created_by         VARCHAR(32),
    updated_by         VARCHAR(32),
    created_at         BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at         BIGINT UNSIGNED NOT NULL DEFAULT 0,
    UNIQUE KEY title_subject (title, subject_id),
    FOREIGN KEY (subject_id) REFERENCES corbeau.subjects (id)
);

CREATE TABLE IF NOT EXISTS corbeau.tags
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS corbeau.tags_posts_join
(
    tag_id  INT NOT NULL,
    post_id INT NOT NULL,
    FOREIGN KEY (tag_id) REFERENCES corbeau.tags (id),
    FOREIGN KEY (post_id) REFERENCES corbeau.posts (id),
    UNIQUE KEY tag_post (tag_id, post_id)
);

CREATE TABLE IF NOT EXISTS corbeau.images
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(64)     NOT NULL,
    extension  VARCHAR(4)      NOT NULL,
    width      INT             NOT NULL,
    height     INT             NOT NULL,
    size       LONG            NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    UNIQUE KEY name_extension (name, extension)
);

CREATE TABLE IF NOT EXISTS corbeau.comments
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL,
    surname    VARCHAR(32)     NOT NULL,
    email      VARCHAR(64)     NOT NULL,
    content    VARCHAR(256)    NOT NULL,
    post_id    INT             NOT NULL,
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    FOREIGN KEY (post_id) REFERENCES corbeau.posts (id)
);

ALTER TABLE corbeau.posts
    ADD FULLTEXT (title);