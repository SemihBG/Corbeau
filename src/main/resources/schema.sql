
CREATE TABLE IF NOT EXISTS db.subjects(
    id VARCHAR (36) PRIMARY KEY ,
    name VARCHAR (128) UNIQUE,
    createDate INT(11),
    updateDate INT(11)
)

CREATE TABLE IF NOT EXISTS db.posts(
    id VARCHAR (36) PRIMARY KEY ,
    title VARCHAR (128) UNIQUE,
    content VARCHAR(2080),
    subjectId VARCHAR (20),
    createDate INT(11),
    updateDate INT(11),
    FOREIGN KEY (subjectId) REFERENCES  db.subjects(id)
)

CREATE TABLE IF NOT EXISTS db.images (
    id VARCHAR (36) PRIMARY KEY ,
    name VARCHAR (128) UNIQUE

);

