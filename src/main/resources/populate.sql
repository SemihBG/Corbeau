#Drop tables if exists
DROP TABLE IF EXISTS db.posts;
DROP TABLE IF EXISTS db.subjects;
DROP TABLE IF EXISTS db.images;


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




