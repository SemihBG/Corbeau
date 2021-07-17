#Drop tables if exists
DROP TABLE IF EXISTS db.posts;
DROP TABLE IF EXISTS db.subjects;
DROP TABLE IF EXISTS db.images;
DROP TABLE IF EXISTS db.moderator_role_join;
DROP TABLE IF EXISTS db.moderators;
DROP TABLE IF EXISTS db.roles;

#Create tables
CREATE TABLE IF NOT EXISTS db.subjects
(
    id         INT(9) PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32) NOT NULL UNIQUE,
    createDate INT(11),
    updateDate INT(11)
);
CREATE TABLE IF NOT EXISTS db.posts
(
    id         INT(9) PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(128) NOT NULL UNIQUE,
    content    LONGTEXT,
    subjectId  VARCHAR(20),
    createDate INT(11),
    updateDate INT(11),
    FOREIGN KEY (subjectId) REFERENCES db.subjects (id)
);
CREATE TABLE IF NOT EXISTS db.images
(
    id     INT(9) PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(64) NOT NULL UNIQUE,
    format VARCHAR(4)  NOT NULL
);
CREATE TABLE IF NOT EXISTS db.roles
(
    id   INT(9) PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS db.moderators
(
    id       INT(9) PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR(32)  NOT NULL UNIQUE,
    password VARCHAR(256) NOT NULL,
    email    VARCHAR(128) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS db.moderator_role_join
(
    moderator_id INT(9) NOT NULL,
    role_id      INT(9) NOT NULL,
    FOREIGN KEY (moderator_id) REFERENCES db.moderators (id),
    FOREIGN KEY (role_id) REFERENCES db.roles (id)
);

#Insert records
INSERT INTO db.roles
VALUES (1, 'prime'),
       (2, 'support');
