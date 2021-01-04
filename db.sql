USE db;

CREATE TABLE IF NOT EXISTS `moderators` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO moderators (id,enabled,username, password) VALUES (1, 'user', '$2a$10$ohpM1ukCByjb4LdET83fvuhXtbfXvQmtLH7530SzHrhlpbYzsi1te');

CREATE TABLE IF NOT EXISTS `permissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

INSERT INTO moderators (id,name) VALUES (1,'admin');

DROP TABLE IF EXISTS `moderator_permission_join`

CREATE TABLE `moderator_permission_join` (
  `moderator_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  KEY `FK1ddiomgcn92uajosm8uof7ffu` (`permission_id`),
  KEY `FKd589rti89a3e7j9wpqx8mtd7v` (`moderator_id`),
  CONSTRAINT `FK1ddiomgcn92uajosm8uof7ffu` FOREIGN KEY (`permission_id`) REFERENCES `permissions` (`id`),
  CONSTRAINT `FKd589rti89a3e7j9wpqx8mtd7v` FOREIGN KEY (`moderator_id`) REFERENCES `moderators` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO moderator_permission_join (moderator_id,permission_id) VALUES(1,1);
