/*
SQLyog Community
MySQL - 5.7.12 : Database - istart
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `author` */

DROP TABLE IF EXISTS `author`;

CREATE TABLE `author` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `birth_date` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `author` */

LOCK TABLES `author` WRITE;

insert  into `author`(`id`,`name`,`birth_date`) values 
(1,'jun','2016-05-05');

UNLOCK TABLES;

/*Table structure for table `book` */

DROP TABLE IF EXISTS `book`;

CREATE TABLE `book` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `publication_date` date DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_book_author_id` (`author_id`),
  CONSTRAINT `fk_book_author_id` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `book` */

LOCK TABLES `book` WRITE;

UNLOCK TABLES;

/*Table structure for table `databasechangelog` */

DROP TABLE IF EXISTS `databasechangelog`;

CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `databasechangelog` */

LOCK TABLES `databasechangelog` WRITE;

insert  into `databasechangelog`(`ID`,`AUTHOR`,`FILENAME`,`DATEEXECUTED`,`ORDEREXECUTED`,`EXECTYPE`,`MD5SUM`,`DESCRIPTION`,`COMMENTS`,`TAG`,`LIQUIBASE`,`CONTEXTS`,`LABELS`) values 
('00000000000001','jhipster','classpath:config/liquibase/changelog/00000000000000_initial_schema.xml','2016-04-18 20:50:06',1,'EXECUTED','7:aa34426271b7c3bfc66299c13139843c','createTable, createIndex (x2), createTable (x2), addPrimaryKey, createTable, addForeignKeyConstraint (x3), loadData, dropDefaultValue, loadData (x2), createTable (x2), addPrimaryKey, createIndex (x2), addForeignKeyConstraint','',NULL,'3.4.2',NULL,NULL),
('20160418163414','jhipster','classpath:config/liquibase/changelog/20160418163414_added_entity_Author.xml','2016-04-19 12:11:35',2,'EXECUTED','7:cc501608a59970ff6e635c0e27ea04f3','createTable','',NULL,'3.4.2',NULL,NULL),
('20160418163833','jhipster','classpath:config/liquibase/changelog/20160418163833_added_entity_Book.xml','2016-04-19 12:11:36',3,'EXECUTED','7:856646a0c2a7a1053d7df3fec8bb4c5c','createTable, addForeignKeyConstraint','',NULL,'3.4.2',NULL,NULL),
('20160420055009','jhipster','classpath:config/liquibase/changelog/20160420055009_added_entity_Role.xml','2016-04-20 13:57:36',4,'EXECUTED','7:e187e17eec57511e69db914838150e22','createTable','',NULL,'3.4.2',NULL,NULL),
('20160420064024','jhipster','classpath:config/liquibase/changelog/20160420064024_added_entity_Sysres.xml','2016-04-20 14:46:08',5,'EXECUTED','7:d72105e92cdab8045ba2e797c45f501f','createTable','',NULL,'3.4.2',NULL,NULL),
('20160420092005','jhipster','classpath:config/liquibase/changelog/20160420092005_added_entity_Version.xml','2016-04-20 17:21:23',6,'EXECUTED','7:eff60ff6e75190357186f9a3df5ac0da','createTable, dropDefaultValue','',NULL,'3.4.2',NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `databasechangeloglock` */

DROP TABLE IF EXISTS `databasechangeloglock`;

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `databasechangeloglock` */

LOCK TABLES `databasechangeloglock` WRITE;

insert  into `databasechangeloglock`(`ID`,`LOCKED`,`LOCKGRANTED`,`LOCKEDBY`) values 
(1,'\0',NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `jhi_persistent_audit_event` */

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;

CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(255) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `jhi_persistent_audit_event` */

LOCK TABLES `jhi_persistent_audit_event` WRITE;

insert  into `jhi_persistent_audit_event`(`event_id`,`principal`,`event_date`,`event_type`) values 
(1,'admin','2016-04-18 20:52:44','AUTHENTICATION_SUCCESS'),
(2,'admin','2016-04-18 21:13:01','AUTHENTICATION_SUCCESS'),
(3,'admin','2016-04-19 14:48:38','AUTHENTICATION_SUCCESS'),
(4,'admin','2016-04-20 10:02:38','AUTHENTICATION_SUCCESS'),
(5,'admin','2016-04-20 13:54:04','AUTHENTICATION_SUCCESS'),
(6,'admin','2016-04-20 14:28:49','AUTHENTICATION_SUCCESS'),
(7,'admin','2016-04-20 14:46:50','AUTHENTICATION_SUCCESS'),
(8,'admin','2016-04-20 15:04:54','AUTHENTICATION_SUCCESS'),
(9,'admin','2016-04-20 15:21:32','AUTHENTICATION_SUCCESS'),
(10,'admin','2016-04-20 15:49:22','AUTHENTICATION_SUCCESS'),
(11,'admin','2016-04-20 16:09:40','AUTHENTICATION_SUCCESS'),
(12,'admin','2016-04-20 16:37:06','AUTHENTICATION_SUCCESS'),
(13,'admin','2016-04-20 17:23:56','AUTHENTICATION_SUCCESS'),
(14,'admin','2016-04-20 18:01:52','AUTHENTICATION_SUCCESS');

UNLOCK TABLES;

/*Table structure for table `jhi_persistent_audit_evt_data` */

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;

CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jhi_persistent_audit_evt_data` */

LOCK TABLES `jhi_persistent_audit_evt_data` WRITE;

insert  into `jhi_persistent_audit_evt_data`(`event_id`,`name`,`value`) values 
(1,'remoteAddress','0:0:0:0:0:0:0:1'),
(1,'sessionId','03F35206E65AAD5EB04C1D0D1C5938EB'),
(2,'remoteAddress','0:0:0:0:0:0:0:1'),
(2,'sessionId','9308C71D4033B217444F93B1C5216252'),
(3,'remoteAddress','0:0:0:0:0:0:0:1'),
(3,'sessionId','2FCEB1C9F73643F261D9879FFB3A45AF'),
(4,'remoteAddress','0:0:0:0:0:0:0:1'),
(4,'sessionId','F9D943FF82860E045C2B60497EBF9C85'),
(5,'remoteAddress','127.0.0.1'),
(5,'sessionId','48E3212C8DBCA33A49B162749FF19F05'),
(6,'remoteAddress','0:0:0:0:0:0:0:1'),
(6,'sessionId','F1D7165D4BA11C194CFFF77BC93FA614'),
(7,'remoteAddress','0:0:0:0:0:0:0:1'),
(7,'sessionId','5B1A69D517FEB2D216808D5D0192125D'),
(8,'remoteAddress','127.0.0.1'),
(8,'sessionId','52530937D6EFBABC32856F30CB508417'),
(9,'remoteAddress','127.0.0.1'),
(9,'sessionId','6BE181A30E62FF1247122C3FE449DC82'),
(10,'remoteAddress','0:0:0:0:0:0:0:1'),
(10,'sessionId','18591B6BD47F2DD95BD68E875DFAA04C'),
(11,'remoteAddress','0:0:0:0:0:0:0:1'),
(11,'sessionId','FBC96F7AB6A85F8B1A2A46FEE942C615'),
(12,'remoteAddress','0:0:0:0:0:0:0:1'),
(12,'sessionId','26465583FC077553AB1C8C3CCFE34E65'),
(13,'remoteAddress','0:0:0:0:0:0:0:1'),
(13,'sessionId','1BA1117A342A064A5D9EB3FE45E53717'),
(14,'remoteAddress','127.0.0.1'),
(14,'sessionId','9FC116E9F2C0C0A39B923723652F2E85');

UNLOCK TABLES;

/*Table structure for table `jhi_persistent_token` */

DROP TABLE IF EXISTS `jhi_persistent_token`;

CREATE TABLE `jhi_persistent_token` (
  `series` varchar(255) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `token_value` varchar(255) NOT NULL,
  `token_date` date DEFAULT NULL,
  `ip_address` varchar(39) DEFAULT NULL,
  `user_agent` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`series`),
  KEY `fk_user_persistent_token` (`user_id`),
  CONSTRAINT `fk_user_persistent_token` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `jhi_persistent_token` */

LOCK TABLES `jhi_persistent_token` WRITE;

insert  into `jhi_persistent_token`(`series`,`user_id`,`token_value`,`token_date`,`ip_address`,`user_agent`) values 
('CIfw+brvFU2RTQBGUL7nlw==',3,'4HSgp66QVSyyzJiTNYtS9A==','2016-04-18','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0'),
('GjL96cx8r0Mad55Dhms3Fg==',3,'wOpKPFZtYpPCMDY8hjs0rQ==','2016-04-20','127.0.0.1','Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0'),
('gWNSXir4UdZkP1SYEDy77w==',3,'Adz03zea3yHVrigIxaLbdw==','2016-04-18','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0'),
('RXClMrTbM7eyB76A1VE6pg==',3,'zWnUC2rrCdAODwftCOY9cw==','2016-04-20','127.0.0.1','Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0');

UNLOCK TABLES;

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `seq` int(11) DEFAULT NULL,
  `data_status` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

/*Data for the table `role` */

LOCK TABLES `role` WRITE;

insert  into `role`(`id`,`name`,`description`,`seq`,`data_status`) values 
(1,'ROLE_ADMIN','管理员',1,''),
(2,'ROLE_USER','用户',2,''),
(3,'ROLE_USER2','匿名用户',3,'');

UNLOCK TABLES;

/*Table structure for table `sysres` */

DROP TABLE IF EXISTS `sysres`;

CREATE TABLE `sysres` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `resdesc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `sysres` */

LOCK TABLES `sysres` WRITE;

UNLOCK TABLES;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `idx_user_login` (`login`),
  UNIQUE KEY `idx_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

LOCK TABLES `user` WRITE;

insert  into `user`(`id`,`login`,`password_hash`,`first_name`,`last_name`,`email`,`activated`,`lang_key`,`activation_key`,`reset_key`,`created_by`,`created_date`,`reset_date`,`last_modified_by`,`last_modified_date`) values 
(1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','','en',NULL,NULL,'system','2016-04-18 20:50:05',NULL,NULL,NULL),
(2,'anonymousUser','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','','en',NULL,NULL,'system','2016-04-18 20:50:05',NULL,NULL,NULL),
(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','','en',NULL,NULL,'system','2016-04-18 20:50:05',NULL,NULL,NULL),
(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','','en',NULL,NULL,'system','2016-04-18 20:50:05',NULL,NULL,NULL);

UNLOCK TABLES;

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`role_id`),
  KEY `fk_role_id` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `user_role` */

LOCK TABLES `user_role` WRITE;

insert  into `user_role`(`id`,`user_id`,`role_id`) values 
(1,1,1),
(3,1,2),
(2,3,1),
(4,3,2),
(5,4,2);

UNLOCK TABLES;

/*Table structure for table `version` */

DROP TABLE IF EXISTS `version`;

CREATE TABLE `version` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `deprecated` bit(1) DEFAULT NULL,
  `create_time` timestamp NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

/*Data for the table `version` */

LOCK TABLES `version` WRITE;

insert  into `version`(`id`,`name`,`url`,`deprecated`,`create_time`,`description`) values 
(29,'1.0.0.1','/image/20160323155931e72530fe-3be4-463b-bd80-4e79cbf7a58f.zip','','2016-03-23 15:59:31',''),
(30,'1.0.0.2','/image/201603231559395b922945-cebe-4b17-b464-ce98a52df332.zip','','2016-03-23 15:59:39',''),
(31,'1.0.0.3','/image/201603231559464a832d15-533f-4049-bcd0-a3fdf1202445.zip','','2016-03-23 15:59:46',''),
(32,'1.0.0.4','/image/2016032315595873a193f1-b760-40fe-8713-784aa1eb78bc.zip','','2016-03-23 15:59:58','1.0.0.4'),
(33,'1.0.0.5','/image/201603281527066b1b3123-280a-4aee-8b66-b830f286e8e3.zip','\0','2016-03-28 15:27:06','解决自动回复BUG以及收货地址确认屏蔽'),
(34,'1.0.0.6','/image/2016040114032013271443-7ede-4b77-a358-7e63bced4a29.zip','','2016-04-01 14:03:20','修改无标题窗口发送消息失败BUG');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
