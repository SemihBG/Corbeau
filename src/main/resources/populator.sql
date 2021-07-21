DROP TABLE IF EXISTS db.posts;
DROP TABLE IF EXISTS db.subjects;
DROP TABLE IF EXISTS db.images;
DROP TABLE IF EXISTS db.moderators;
DROP TABLE IF EXISTS db.roles;

CREATE TABLE IF NOT EXISTS db.roles
(
    id   INT PRIMARY KEY AUTO_INCREMENT,

    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS db.moderators
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL UNIQUE,
    password   VARCHAR(256)    NOT NULL,
    email      VARCHAR(128)    NOT NULL UNIQUE,
    role_id    INT             NOT NULL,
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    FOREIGN KEY (role_id) REFERENCES db.roles (id)
);
CREATE TABLE IF NOT EXISTS db.subjects
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)     NOT NULL UNIQUE,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0
);
CREATE TABLE IF NOT EXISTS db.posts
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(128)    NOT NULL UNIQUE,
    content    LONGTEXT,
    subject_id INT             NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    FOREIGN KEY (subject_id) REFERENCES db.subjects (id)
);
CREATE TABLE IF NOT EXISTS db.images
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(64)     NOT NULL UNIQUE,
    format     VARCHAR(4)      NOT NULL,
    created_by VARCHAR(32),
    updated_by VARCHAR(32),
    created_at BIGINT UNSIGNED NOT NULL DEFAULT 0,
    updated_at BIGINT UNSIGNED NOT NULL DEFAULT 0
);

INSERT INTO db.subjects (id, name)
VALUES (1, 'Java'),
       (2, 'Go'),
       (3, 'Kotlin');

INSERT INTO db.posts(title, content, subject_id, created_at, updated_at)
VALUES ('postJava01', 'content', 1, UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postGo01', 'content', 2,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postKotlin01', 'content', 3,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postJava02', 'content', 1,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postGo02', 'content', 2, UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postKotlin02', 'content', 3,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postJava03', 'content', 1,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postGo03', 'content', 2,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postKotlin03', 'content', 3,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postJava04', 'content', 1,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postGo04', 'content', 2,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postKotlin04', 'content', 3,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postJava05', 'content', 1,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postGo05', 'content', 2,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postKotlin05', 'content', 3,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postJava06', 'content', 1,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postGo06', 'content', 2,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postKotlin06', 'content', 3,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postJava07', 'content', 1,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postGo07', 'content', 2,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postKotlin07', 'content', 3,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postJava08', 'content', 1,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000)),
       ('postGo08', 'content', 2,  UNIX_TIMESTAMP()*1000,UNIX_TIMESTAMP()*1000+(RAND()*3600000));
