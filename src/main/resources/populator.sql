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
    role_id    INT UNSIGNED NOT NULL,
    created_at BIGINT UNSIGNED,
    updated_at BIGINT UNSIGNED,
    FOREIGN KEY (role_id) REFERENCES db.roles (id)
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

INSERT INTO db.subjects (id, name)
VALUES (1, 'Java'),
       (2, 'Go'),
       (3, 'Kotlin');

INSERT INTO db.posts(title, content, subject_id)
VALUES ('postJava01', 'content', 1),
       ('postGo01', 'content', 2),
       ('postKotlin01', 'content', 3),
       ('postJava02', 'content', 1),
       ('postGo02', 'content', 2),
       ('postKotlin02', 'content', 3),
       ('postJava03', 'content', 1),
       ('postGo03', 'content', 2),
       ('postKotlin03', 'content', 3),
       ('postJava04', 'content', 1),
       ('postGo04', 'content', 2),
       ('postKotlin04', 'content', 3),
       ('postJava05', 'content', 1),
       ('postGo05', 'content', 2),
       ('postKotlin05', 'content', 3),
       ('postJava06', 'content', 1),
       ('postGo06', 'content', 2),
       ('postKotlin06', 'content', 3),
       ('postJava07', 'content', 1),
       ('postGo07', 'content', 2),
       ('postKotlin07', 'content', 3),
       ('postJava08', 'content', 1),
       ('postGo08', 'content', 2);
