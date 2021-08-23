/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.21-log : Database - cloud_disk
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cloud_disk` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cloud_disk`;

/*Table structure for table `file_system` */

DROP TABLE IF EXISTS `file_system`;

CREATE TABLE `file_system` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(100) NOT NULL COMMENT '文件实际名',
  `file_name_map` varchar(100) NOT NULL COMMENT '文件镜像名',
  `file_full_name` varchar(500) NOT NULL COMMENT '文件全路径名',
  `file_full_name_map` varchar(500) NOT NULL COMMENT '文件全路径镜像名',
  `file_is_hidden` int(1) NOT NULL COMMENT '是否是隐藏文件（0/1）',
  `file_type` int(1) NOT NULL COMMENT '文件类型（目录/文件）',
  `parent_path` varchar(500) NOT NULL COMMENT '上级目录',
  `current_path` varchar(500) NOT NULL COMMENT '当前目录',
  `file_status` int(1) NOT NULL DEFAULT '0' COMMENT '文件状态（正常/回收站/彻底删除）',
  `file_size` bigint(64) NOT NULL COMMENT '文件大小',
  `app_id` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) NOT NULL,
  `pass_word` varchar(100) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `app_id` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`,`user_name`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Table structure for table `user_details` */

DROP TABLE IF EXISTS `user_details`;

CREATE TABLE `user_details` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `real_name` varchar(20) DEFAULT NULL,
  `img_path` varchar(100) DEFAULT NULL,
  `app_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user_trajectory` */

DROP TABLE IF EXISTS `user_trajectory`;

CREATE TABLE `user_trajectory` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `trajectory_type` int(11) NOT NULL COMMENT '行为类型',
  `explain` varchar(500) NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `app_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `user_vip_history` */

DROP TABLE IF EXISTS `user_vip_history`;

CREATE TABLE `user_vip_history` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `app_id` varchar(100) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `vip_level` int(11) NOT NULL,
  `vip_code` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Table structure for table `user_vip_status` */

DROP TABLE IF EXISTS `user_vip_status`;

CREATE TABLE `user_vip_status` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `status` int(1) NOT NULL DEFAULT '0',
  `vip_level` int(11) DEFAULT '1',
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL,
  `app_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
