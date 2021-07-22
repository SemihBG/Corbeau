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
VALUES ('postJava01', 'content', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postGo01', 'content', 2, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postKotlin01', 'content', 3, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postJava02', 'content', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postGo02', 'content', 2, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postKotlin02', 'content', 3, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postJava03', 'content', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postGo03', 'content', 2, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postKotlin03', 'content', 3, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postJava04', 'content', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postGo04', 'content', 2, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postKotlin04', 'content', 3, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postJava05', 'content', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postGo05', 'content', 2, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postKotlin05', 'content', 3, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postJava06', 'content', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postGo06', 'content', 2, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postKotlin06', 'content', 3, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postJava07', 'content', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postGo07', 'content', 2, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postKotlin07', 'content', 3, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postJava08', 'content', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000)),
       ('postGo08', 'content', 2, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (RAND() * 3600000));


INSERT INTO db.posts(title, content, subject_id, created_at, updated_at)
VALUES ('testPost', '
Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur eu nunc nisl. Nulla diam magna, facilisis et viverra id, vehicula at nisl. Donec cursus vestibulum metus, sit amet dapibus elit dapibus blandit. Fusce tincidunt malesuada accumsan. Donec tincidunt consectetur tempor. Pellentesque sed lacus nec nibh convallis imperdiet. Vestibulum ornare, massa at blandit laoreet, libero magna semper metus, at iaculis mauris odio vel dui. Vivamus luctus, leo et placerat eleifend, quam nisi sodales nisi, eu convallis sapien mauris nec mi. Fusce nibh lectus, semper eu tempor a, ullamcorper nec eros. Nam eu finibus mi, vel tempor augue. Integer hendrerit dui non velit tincidunt, non dapibus mauris accumsan. Pellentesque quis nibh at nibh auctor euismod.
Aliquam lobortis lectus sit amet enim ultrices pretium et ut est. Fusce elementum, nibh eu eleifend porta, turpis ipsum cursus magna, at dapibus lacus lectus ac enim. Etiam suscipit tortor at feugiat rhoncus. Maecenas in nunc eget augue faucibus efficitur. Quisque ac tempus nunc, sed dignissim tortor. Quisque vel tortor mauris. Duis laoreet, urna consequat tristique malesuada, dolor mi laoreet metus, non varius orci enim vitae mi. Integer facilisis quis dolor a aliquet. Aenean sed nisl ut sapien gravida euismod vitae a neque. Sed sit amet volutpat ex.
Sed a turpis massa. Curabitur tristique malesuada nisi, vel sagittis libero maximus eget. Nam interdum mauris vel massa fringilla, nec egestas purus maximus. Etiam porta nunc at scelerisque placerat. Nulla a porttitor nisl. Donec eleifend tellus eget enim tincidunt venenatis. Suspendisse volutpat erat vel dolor scelerisque volutpat. Fusce accumsan odio quam, rhoncus ultrices libero aliquet quis. Morbi odio quam, volutpat ac tempus quis, porta quis urna. Nam libero leo, efficitur at orci a, iaculis viverra lorem. Pellentesque condimentum convallis justo, nec tincidunt velit ultrices sit amet. Ut iaculis vel magna vel porttitor. Nunc ac ex rhoncus, porta velit sit amet, rutrum ex.
Maecenas rhoncus accumsan lectus venenatis molestie. Etiam accumsan, enim id consectetur dignissim, metus enim laoreet nibh, vel ultrices mauris quam sed erat. Nam eget fermentum mi. Donec mollis nunc et luctus convallis. Nullam eu elit venenatis, pellentesque metus at, ornare diam. Vestibulum ornare aliquam est ut dictum. Nunc eros magna, vestibulum vel mi sed, fermentum rutrum libero. Ut vitae molestie augue, ut varius augue. Nullam et elementum mauris, eget vehicula eros. Aliquam erat volutpat. Quisque ultrices tempus magna. Praesent gravida nisi non nisi interdum, ullamcorper malesuada ante molestie. Cras sit amet nisl lorem. Ut non ex hendrerit, venenatis dolor sit amet, pulvinar massa. Praesent quis augue aliquam, maximus dolor eu, cursus odio.
Aliquam erat volutpat. Aliquam quis quam placerat, ullamcorper enim quis, lobortis libero. Vestibulum tortor neque, malesuada a tristique id, venenatis vel lacus. Aliquam ut tempus magna, in interdum ligula. Nam condimentum erat id vehicula finibus. Pellentesque arcu elit, blandit quis volutpat ac, aliquam sit amet est. Ut at lectus efficitur enim mattis venenatis fringilla at lacus. Ut luctus facilisis faucibus. Vestibulum nisi metus, convallis non lacus et, condimentum venenatis neque. In at luctus nibh.
', 1, UNIX_TIMESTAMP() * 1000, UNIX_TIMESTAMP() * 1000 + (3600000));
