#Drop tables
DROP TABLE IF EXISTS corbeau.posts;
DROP TABLE IF EXISTS corbeau.subjects;
DROP TABLE IF EXISTS corbeau.images;
DROP TABLE IF EXISTS corbeau.moderator_role_join;
DROP TABLE IF EXISTS corbeau.moderators;
DROP TABLE IF EXISTS corbeau.roles;

#Create tables
CREATE TABLE IF NOT EXISTS corbeau.roles
(
    id   INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS corbeau.moderators
(
    id         INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)      NOT NULL UNIQUE,
    password   VARCHAR(256)     NOT NULL,
    email      VARCHAR(128)     NOT NULL UNIQUE,
    created_by INT(7) UNSIGNED  NOT NULL,
    updated_by INT(7) UNSIGNED,
    created_at INT(10) UNSIGNED NOT NULL,
    updated_at INT(10) UNSIGNED DEFAULT 0,
    FOREIGN KEY (created_by) REFERENCES corbeau.moderators (id),
    FOREIGN KEY (updated_by) REFERENCES corbeau.moderators (id)
);
CREATE TABLE IF NOT EXISTS corbeau.moderator_role_join
(
    moderator_id INT(7) UNSIGNED NOT NULL,
    role_id      INT(7) UNSIGNED NOT NULL,
    FOREIGN KEY (moderator_id) REFERENCES corbeau.moderators (id),
    FOREIGN KEY (role_id) REFERENCES corbeau.roles (id)
);
CREATE TABLE IF NOT EXISTS corbeau.subjects
(
    id         INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)      NOT NULL UNIQUE,
    created_by INT(7) UNSIGNED  NOT NULL,
    updated_by INT(7) UNSIGNED,
    created_at INT(10) UNSIGNED NOT NULL,
    updated_at INT(10) UNSIGNED DEFAULT 0,
    FOREIGN KEY (created_by) REFERENCES corbeau.moderators (id),
    FOREIGN KEY (updated_by) REFERENCES corbeau.moderators (id)
);
CREATE TABLE IF NOT EXISTS corbeau.posts
(
    id         INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(128)     NOT NULL UNIQUE,
    content    LONGTEXT,
    subject_id INT(7) UNSIGNED  NOT NULL,
    created_by INT(7) UNSIGNED  NOT NULL,
    updated_by INT(7) UNSIGNED,
    created_at INT(10) UNSIGNED NOT NULL,
    updated_at INT(10) UNSIGNED DEFAULT 0,
    FOREIGN KEY (subject_id) REFERENCES corbeau.subjects (id),
    FOREIGN KEY (created_by) REFERENCES corbeau.moderators (id),
    FOREIGN KEY (updated_by) REFERENCES corbeau.moderators (id)
);
CREATE TABLE IF NOT EXISTS corbeau.images
(
    id         INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(64)      NOT NULL UNIQUE,
    format     VARCHAR(4)       NOT NULL,
    created_by INT(7) UNSIGNED  NOT NULL,
    updated_by INT(7) UNSIGNED,
    created_at INT(10) UNSIGNED NOT NULL,
    updated_at INT(10) UNSIGNED DEFAULT 0,
    FOREIGN KEY (created_by) REFERENCES corbeau.moderators (id),
    FOREIGN KEY (updated_by) REFERENCES corbeau.moderators (id)
);

#Insert records
INSERT INTO corbeau.roles
VALUES (1, 'prime'),
       (2, 'support');
