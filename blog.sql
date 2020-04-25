/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 10.1.35-MariaDB : Database - db_blog
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_blog` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `db_blog`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` mediumtext NOT NULL COMMENT '文章内容',
  `view_count` int(11) DEFAULT '0' COMMENT '点击量',
  `title` varchar(255) NOT NULL COMMENT '文章标题',
  `create_time` datetime DEFAULT NULL COMMENT '发布时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `is_article` int(11) DEFAULT NULL COMMENT '是否是文章',
  `readOnly` int(11) DEFAULT NULL COMMENT '阅读权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `article` */

insert  into `article`(`id`,`user_id`,`content`,`view_count`,`title`,`create_time`,`last_edit_time`,`is_article`,`readOnly`) values (1,5,'中文测试Testing',2333,'中文测试Testing','2020-02-08 16:46:03',NULL,1,1);

/*Table structure for table `article_category_ref` */

DROP TABLE IF EXISTS `article_category_ref`;

CREATE TABLE `article_category_ref` (
  `article_id` int(11) NOT NULL COMMENT '文章id',
  `category_id` int(11) NOT NULL COMMENT '分类id',
  PRIMARY KEY (`article_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `article_category_ref` */

/*Table structure for table `article_tag_ref` */

DROP TABLE IF EXISTS `article_tag_ref`;

CREATE TABLE `article_tag_ref` (
  `tag_id` int(11) NOT NULL COMMENT '标签id',
  `article_id` int(11) NOT NULL COMMENT '文章id',
  PRIMARY KEY (`tag_id`,`article_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `article_tag_ref` */

insert  into `article_tag_ref`(`tag_id`,`article_id`) values (2,1);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `category_name` varchar(255) NOT NULL COMMENT '分类名',
  `category_description` varchar(255) DEFAULT NULL COMMENT '分类描述',
  `category_icon` varchar(255) DEFAULT NULL COMMENT '分类图标',
  `category_pid` int(11) DEFAULT NULL COMMENT '夫类id',
  `user_id` int(11) NOT NULL COMMENT '创建者id',
  `create_time` date NOT NULL COMMENT '创建时间',
  `order` int(11) DEFAULT NULL COMMENT '目录显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `category` */

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_name` varchar(255) NOT NULL COMMENT '标签名',
  `tag_description` varchar(255) DEFAULT NULL COMMENT '标签描述',
  `user_id` int(11) NOT NULL COMMENT '创建者id',
  `create_time` date NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tag` */

insert  into `tag`(`id`,`tag_name`,`tag_description`,`user_id`,`create_time`) values (1,'JAVA','java相关',5,'2020-02-08'),(2,'数据库','MYSQL',1,'2020-02-08');

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `user_mail` varchar(255) NOT NULL COMMENT '用户邮箱',
  `image` varchar(255) DEFAULT NULL COMMENT '用户头像路径',
  `register_time` date NOT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`user_mail`,`image`,`register_time`,`last_login_time`) values (1,'East','4297f44b13955235245b2497399d7a93','',NULL,'2020-02-08',NULL),(5,'East_ZD','4297f44b13955235245b2497399d7a93','850222009@qq.com',NULL,'2020-02-04',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
