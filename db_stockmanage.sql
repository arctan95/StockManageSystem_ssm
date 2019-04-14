/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.49-community : Database - db_stockmanage
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_stockmanage` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db_stockmanage`;

/*Table structure for table `t_export` */

DROP TABLE IF EXISTS `t_export`;

CREATE TABLE `t_export` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsId` int(20) DEFAULT NULL,
  `stockId` int(20) DEFAULT NULL,
  `expoPrice` varchar(20) DEFAULT NULL,
  `expoDate` datetime DEFAULT NULL,
  `expoNum` varchar(20) DEFAULT NULL,
  `expoDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t_export` (`goodsId`),
  KEY `Fk_export_importId` (`stockId`),
  CONSTRAINT `FK_t_export` FOREIGN KEY (`goodsId`) REFERENCES `t_goods` (`id`),
  CONSTRAINT `Fk_export_importId` FOREIGN KEY (`stockId`) REFERENCES `t_stock` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

/*Data for the table `t_export` */

insert  into `t_export`(`id`,`goodsId`,`stockId`,`expoPrice`,`expoDate`,`expoNum`,`expoDesc`) values (9,1,37,'150.0','2016-12-06 00:00:00','2','fff'),(10,1,38,'450.0','2016-12-07 00:00:00','4','fda');

/*Table structure for table `t_goods` */

DROP TABLE IF EXISTS `t_goods`;

CREATE TABLE `t_goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsId` int(20) DEFAULT NULL,
  `goodsName` varchar(20) DEFAULT NULL,
  `expireTime` int(11) DEFAULT NULL,
  `proId` int(20) DEFAULT NULL,
  `typeId` int(20) DEFAULT NULL,
  `goodsDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t_goods` (`typeId`),
  KEY `FK_t1_goods` (`proId`),
  CONSTRAINT `FK_t1_goods` FOREIGN KEY (`proId`) REFERENCES `t_provider` (`id`),
  CONSTRAINT `FK_t_goods` FOREIGN KEY (`typeId`) REFERENCES `t_goodstype` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

/*Data for the table `t_goods` */

insert  into `t_goods`(`id`,`goodsId`,`goodsName`,`expireTime`,`proId`,`typeId`,`goodsDesc`) values (1,22,'java01',2,22,1,'java01'),(2,23,'java02',1,23,2,'java02'),(3,24,'java03',2,24,3,'java03'),(4,25,'java04',1,25,4,'java04'),(5,26,'java05',2,26,5,'java05'),(6,27,'java06',1,27,1,'java06'),(7,28,'java07',3,28,2,'java07'),(8,29,'java08',1,29,3,'java08'),(9,30,'java09',3,22,4,'java09'),(23,31,'java10',3,22,1,'fffa');

/*Table structure for table `t_goodstype` */

DROP TABLE IF EXISTS `t_goodstype`;

CREATE TABLE `t_goodstype` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `typeName` varchar(20) DEFAULT NULL,
  `typeDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

/*Data for the table `t_goodstype` */

insert  into `t_goodstype`(`id`,`typeName`,`typeDesc`) values (1,'计算机书籍1','关于计算机1'),(2,'计算机书籍2','关于计算机22'),(3,'计算机书籍3','关于计算机3'),(4,'计算机书籍4','关于计算机4'),(5,'计算机书籍5','关于计算机5'),(7,'计算机书籍6','关于计算机6'),(8,'计算机书籍7','关于计算机7'),(9,'计算机书籍8','关于计算机8'),(10,'计算机书籍9','关于计算机9'),(11,'计算机书籍10','关于计算机10'),(12,'计算机书籍11','关于计算机11'),(13,'计算机书籍12','关于计算机12'),(14,'计算机书籍13','关于计算机13'),(15,'计算机书籍14','关于计算机14'),(16,'计算机书籍15','关于计算机15'),(17,'计算机书籍16','关于计算机16'),(18,'计算机书籍17','关于计算机17'),(20,'Java书籍','关于java'),(21,'C++书籍1','关于C++1'),(22,'C++书籍123','1123');

/*Table structure for table `t_import` */

DROP TABLE IF EXISTS `t_import`;

CREATE TABLE `t_import` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsId` int(20) DEFAULT NULL,
  `impoPrice` varchar(20) DEFAULT NULL,
  `impoDate` datetime DEFAULT NULL,
  `impoNum` varchar(20) DEFAULT NULL,
  `impoDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t_import` (`goodsId`),
  CONSTRAINT `Fk_goodsId` FOREIGN KEY (`goodsId`) REFERENCES `t_goods` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

/*Data for the table `t_import` */

insert  into `t_import`(`id`,`goodsId`,`impoPrice`,`impoDate`,`impoNum`,`impoDesc`) values (45,1,'100','2016-12-05 00:00:00','300','aa'),(46,1,'300','2016-12-06 00:00:00','400','fasd'),(47,1,'300','2015-12-01 00:00:00','600','aaa');

/*Table structure for table `t_provider` */

DROP TABLE IF EXISTS `t_provider`;

CREATE TABLE `t_provider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `proId` int(20) DEFAULT NULL,
  `proName` varchar(20) DEFAULT NULL,
  `linkman` varchar(20) DEFAULT NULL,
  `proPhone` varchar(20) DEFAULT NULL,
  `proDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Data for the table `t_provider` */

insert  into `t_provider`(`id`,`proId`,`proName`,`linkman`,`proPhone`,`proDesc`) values (22,103,'公司3','103@qq.com','103@qq.com','常用3'),(23,104,'公司4','104@qq.com','104@qq.com','常用4'),(24,105,'公司5','105@qq.com','105@qq.com','常用5'),(25,106,'公司6','106@qq.com','106@qq.com','常用6'),(26,107,'公司7','107@qq.com','107@qq.com','常用7'),(27,108,'公司8','108@qq.com','108@qq.com','常用8'),(28,109,'公司9','109@qq.com','109@qq.com','常用9'),(29,110,'公司10','110@qq.com','110@qq.com','常用10'),(30,111,'AB公司','123','11111','AB1');

/*Table structure for table `t_stock` */

DROP TABLE IF EXISTS `t_stock`;

CREATE TABLE `t_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsId` int(20) DEFAULT NULL,
  `importId` int(20) DEFAULT NULL,
  `stockNum` int(20) DEFAULT NULL,
  `limitNum` int(20) DEFAULT '0',
  `impoPrice` varchar(20) DEFAULT NULL,
  `expoPrice` varchar(20) DEFAULT NULL,
  `stockDesc` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_t_stock` (`goodsId`),
  KEY `Fk_importId` (`importId`),
  CONSTRAINT `FK_t_stock` FOREIGN KEY (`goodsId`) REFERENCES `t_goods` (`id`),
  CONSTRAINT `Fk_importId` FOREIGN KEY (`importId`) REFERENCES `t_import` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

/*Data for the table `t_stock` */

insert  into `t_stock`(`id`,`goodsId`,`importId`,`stockNum`,`limitNum`,`impoPrice`,`expoPrice`,`stockDesc`) values (37,1,45,298,300,'100','150.0','aa'),(38,1,46,396,0,'300','450.0','fasd'),(39,1,47,600,0,'300','450.0','aaa');

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`id`,`userName`,`password`) values (1,'tan','123456'),(2,'java','123');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
