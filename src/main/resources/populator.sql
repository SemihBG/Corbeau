DROP TABLE IF EXISTS db.posts;
DROP TABLE IF EXISTS db.subjects;
DROP TABLE IF EXISTS db.images;
DROP TABLE IF EXISTS db.moderators;
DROP TABLE IF EXISTS db.roles;

CREATE TABLE IF NOT EXISTS db.roles
(
    id   INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS db.moderators
(
    id         INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)  NOT NULL UNIQUE,
    password   VARCHAR(256) NOT NULL,
    email      VARCHAR(128) NOT NULL UNIQUE,
    role_id INT UNSIGNED NOT NULL ,
    created_at BIGINT UNSIGNED,
    updated_at BIGINT UNSIGNED,
    FOREIGN KEY (role_id) REFERENCES db.roles(id)
);
CREATE TABLE IF NOT EXISTS db.subjects
(
    id         INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32) NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED,
    updated_at BIGINT UNSIGNED
);
CREATE TABLE IF NOT EXISTS db.posts
(
    id         INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(128) NOT NULL UNIQUE,
    content    LONGTEXT,
    subject_id INT UNSIGNED NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED,
    updated_at BIGINT UNSIGNED,
    FOREIGN KEY (subject_id) REFERENCES db.subjects (id)
);
CREATE TABLE IF NOT EXISTS db.images
(
    id         INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(64) NOT NULL UNIQUE,
    format     VARCHAR(4)  NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED,
    updated_at BIGINT UNSIGNED
);
