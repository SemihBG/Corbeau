#Drop tables
DROP TABLE IF EXISTS db.posts;
DROP TABLE IF EXISTS db.subjects;
DROP TABLE IF EXISTS db.images;
DROP TABLE IF EXISTS db.moderator_role_join;
DROP TABLE IF EXISTS db.moderators;
DROP TABLE IF EXISTS db.roles;

#Create tables
CREATE TABLE IF NOT EXISTS db.roles
(
    id   INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS db.moderators
(
    id         INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32)  NOT NULL UNIQUE,
    password   VARCHAR(256) NOT NULL,
    email      VARCHAR(128) NOT NULL UNIQUE,
    created_by INT(7) UNSIGNED,
    updated_by INT(7) UNSIGNED,
    created_at INT(10) UNSIGNED,
    updated_at INT(10) UNSIGNED,
    FOREIGN KEY (created_by) REFERENCES db.moderators (id),
    FOREIGN KEY (updated_by) REFERENCES db.moderators (id)
);
CREATE TABLE IF NOT EXISTS db.moderator_role_join
(
    moderator_id INT(7) UNSIGNED NOT NULL,
    role_id      INT(7) UNSIGNED NOT NULL,
    FOREIGN KEY (moderator_id) REFERENCES db.moderators (id),
    FOREIGN KEY (role_id) REFERENCES db.roles (id)
);
CREATE TABLE IF NOT EXISTS db.subjects
(
    id         INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(32) NOT NULL UNIQUE,
    created_by INT(7) UNSIGNED,
    updated_by INT(7) UNSIGNED,
    created_at INT(10) UNSIGNED,
    updated_at INT(10) UNSIGNED,
    FOREIGN KEY (created_by) REFERENCES db.moderators (id),
    FOREIGN KEY (updated_by) REFERENCES db.moderators (id)
);
CREATE TABLE IF NOT EXISTS db.posts
(
    id         INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    title      VARCHAR(128)    NOT NULL UNIQUE,
    content    LONGTEXT,
    subject_id INT(7) UNSIGNED NOT NULL,
    created_by INT(7) UNSIGNED,
    updated_by INT(7) UNSIGNED,
    created_at INT(10) UNSIGNED,
    updated_at INT(10) UNSIGNED,
    FOREIGN KEY (subject_id) REFERENCES db.subjects (id),
    FOREIGN KEY (created_by) REFERENCES db.moderators (id),
    FOREIGN KEY (updated_by) REFERENCES db.moderators (id)
);
CREATE TABLE IF NOT EXISTS db.images
(
    id         INT(7) UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(64) NOT NULL UNIQUE,
    format     VARCHAR(4)  NOT NULL,
    created_by INT(7) UNSIGNED,
    updated_by INT(7) UNSIGNED,
    created_at INT(10) UNSIGNED,
    updated_at INT(10) UNSIGNED,
    FOREIGN KEY (created_by) REFERENCES db.moderators (id),
    FOREIGN KEY (updated_by) REFERENCES db.moderators (id)
);

#Insert records
INSERT INTO db.roles
VALUES (1, 'prime'),
       (2, 'support');
