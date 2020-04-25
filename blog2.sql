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
  `content` mediumtext COMMENT '文章内容',
  `view_count` int(11) DEFAULT '0' COMMENT '点击量',
  `title` varchar(255) DEFAULT NULL COMMENT '文章标题',
  `create_time` datetime DEFAULT NULL COMMENT '发布时间',
  `last_edit_time` datetime DEFAULT NULL COMMENT '最后修改时间',
  `is_article` int(11) DEFAULT NULL COMMENT '是否是文章',
  `readOnly` int(11) DEFAULT '1' COMMENT '阅读权限',
  `summary` varchar(1000) DEFAULT NULL COMMENT '文章概述',
  PRIMARY KEY (`id`),
  KEY `article_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `article` */

insert  into `article`(`id`,`user_id`,`content`,`view_count`,`title`,`create_time`,`last_edit_time`,`is_article`,`readOnly`,`summary`) values (1,5,'中文测试Testing',2334,'中文测试Testing 搜索','2019-12-01 16:46:03',NULL,1,1,NULL),(2,1,'asdasdasdasd',3222,'中文测试 新Testing搜索答案','2019-12-01 17:04:31',NULL,1,0,NULL),(3,6,'<blockquote><div>Testing中文 啊asdasdasdasd实打实的asda<strong>dasdasd<em>asda</em></strong></div></blockquote>',0,'测试Testing 答案','2020-02-29 00:24:47',NULL,1,1,'回你妈的车啊'),(6,5,'<h1>中<code><strong>你妈逼文</strong></code>空&nbsp; &nbsp; &nbsp;格测<em>试</em></h1><p>回</p><p>车</p><p>键</p><p>测</p><p>试</p><p style=\"padding-left: 40px;\">缩进测试&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;</p><p style=\"padding-left: 40px; text-align: center;\">居中测试</p><p style=\"padding-left: 40px; text-align: left;\">居左Testing</p><p style=\"padding-left: 40px; text-align: right;\">居右测试</p><p style=\"padding-left: 40px; text-align: justify;\">两端对齐</p><p><span style=\"color: #e32f2f;\">文</span><span style=\"background-color: #e03e2d;\">字</span><span style=\"color: #f1c40f;\">颜</span><span style=\"color: #34495e;\">色</span><span style=\"text-decoration: underline;\">格式</span><span style=\"text-decoration: line-through;\">测试</span></p>',0,'新的title 新的标题 搜','2020-03-02 16:56:52',NULL,1,1,'中文空&nbsp; &nbsp; &nbsp;格测试回车键测试缩进测试&nbsp; &nbsp; &nbsp; &nbsp;&nbsp;居中测试居左Testing居右测试两端对齐文字颜色格式测试');

/*Table structure for table `article_category_ref` */

DROP TABLE IF EXISTS `article_category_ref`;

CREATE TABLE `article_category_ref` (
  `article_id` int(11) unsigned NOT NULL COMMENT '文章id',
  `category_id` int(11) unsigned NOT NULL COMMENT '分类id',
  PRIMARY KEY (`article_id`,`category_id`),
  KEY `article_category_ref_category` (`category_id`),
  CONSTRAINT `article_category_ref_article` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_category_ref_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `article_category_ref` */

insert  into `article_category_ref`(`article_id`,`category_id`) values (1,1),(2,1),(2,2),(3,1),(3,6),(6,8);

/*Table structure for table `article_tag_ref` */

DROP TABLE IF EXISTS `article_tag_ref`;

CREATE TABLE `article_tag_ref` (
  `tag_id` int(11) unsigned NOT NULL COMMENT '标签id',
  `article_id` int(11) unsigned NOT NULL COMMENT '文章id',
  PRIMARY KEY (`tag_id`,`article_id`),
  KEY `article_tag_ref_article_id` (`article_id`),
  CONSTRAINT `article_tag_ref_article_id` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `article_tag_ref_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `article_tag_ref` */

insert  into `article_tag_ref`(`tag_id`,`article_id`) values (1,1),(1,2),(1,3),(1,6),(3,1),(6,1),(8,6);

/*Table structure for table `category` */

DROP TABLE IF EXISTS `category`;

CREATE TABLE `category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `category_name` varchar(255) NOT NULL COMMENT '分类名',
  `category_description` varchar(255) DEFAULT NULL COMMENT '分类描述',
  `category_icon` varchar(255) DEFAULT NULL COMMENT '分类图标',
  `category_pid` int(11) DEFAULT NULL COMMENT '夫类id',
  `creator_id` int(11) NOT NULL COMMENT '创建者id',
  `create_time` date NOT NULL COMMENT '创建时间',
  `is_order` int(11) DEFAULT '0' COMMENT '目录显示',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `category` */

insert  into `category`(`id`,`category_name`,`category_description`,`category_icon`,`category_pid`,`creator_id`,`create_time`,`is_order`) values (1,'JAVA','java相关',NULL,0,5,'2020-02-10',1),(2,'数据库','数据库相关',NULL,0,5,'2020-02-10',1),(3,'MYSQL','MYSQL相关',NULL,2,5,'2020-02-11',0),(5,'JAVA2',NULL,NULL,0,5,'2020-02-12',1),(6,'测试类',NULL,NULL,0,5,'2020-02-28',0),(8,'jquery',NULL,NULL,0,5,'2020-03-02',0),(9,'JAVA23',NULL,NULL,0,5,'2020-03-25',1),(10,'asdas',NULL,NULL,0,5,'2020-03-25',0),(11,'ghfghfg',NULL,NULL,0,5,'2020-03-25',0);

/*Table structure for table `collect` */

DROP TABLE IF EXISTS `collect`;

CREATE TABLE `collect` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `collect_article_id` int(10) unsigned NOT NULL COMMENT '收藏文章id',
  `collect_user_id` int(10) unsigned NOT NULL COMMENT '收藏用户id',
  `create_time` date DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `collect_article_user_id` (`collect_article_id`,`collect_user_id`),
  KEY `collect_user_id` (`collect_user_id`),
  CONSTRAINT `collect_article_id` FOREIGN KEY (`collect_article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `collect_user_id` FOREIGN KEY (`collect_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `collect` */

insert  into `collect`(`id`,`collect_article_id`,`collect_user_id`,`create_time`) values (1,1,5,'2020-02-29');

/*Table structure for table `comment` */

DROP TABLE IF EXISTS `comment`;

CREATE TABLE `comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `comment_content` varchar(1000) NOT NULL COMMENT '评论内容',
  `comment_user_id` int(11) unsigned NOT NULL COMMENT '评论用户id',
  `comment_article_id` int(11) unsigned NOT NULL COMMENT '评论文章id',
  `comment_pid` int(11) DEFAULT '0' COMMENT '评论对象id',
  `create_time` datetime DEFAULT NULL COMMENT '评论时间',
  PRIMARY KEY (`id`),
  KEY `comment_user_id` (`comment_user_id`),
  KEY `comment_article_id` (`comment_article_id`),
  CONSTRAINT `comment_article_id` FOREIGN KEY (`comment_article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `comment_user_id` FOREIGN KEY (`comment_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4;

/*Data for the table `comment` */

insert  into `comment`(`id`,`comment_content`,`comment_user_id`,`comment_article_id`,`comment_pid`,`create_time`) values (24,'新的评论2',5,2,0,'2020-03-25 20:12:12'),(25,'回复你妈逼评论',5,2,23,'2020-03-25 20:12:56'),(26,'回复评论',1,1,23,'2020-03-25 21:48:58');

/*Table structure for table `follow` */

DROP TABLE IF EXISTS `follow`;

CREATE TABLE `follow` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `follow_user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `follow_pid` int(11) unsigned NOT NULL COMMENT '被关注者id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `follow_userid_pid` (`follow_user_id`,`follow_pid`),
  KEY `follow_pid` (`follow_pid`),
  KEY `follow_user_id` (`follow_user_id`),
  CONSTRAINT `follow_pid` FOREIGN KEY (`follow_pid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `follow_user_id` FOREIGN KEY (`follow_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `follow` */

insert  into `follow`(`id`,`follow_user_id`,`follow_pid`) values (9,1,5),(4,5,6);

/*Table structure for table `history` */

DROP TABLE IF EXISTS `history`;

CREATE TABLE `history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `history_article_id` int(10) unsigned NOT NULL COMMENT '文章id',
  `history_user_id` int(10) unsigned NOT NULL COMMENT '用户id',
  `create_time` date DEFAULT NULL COMMENT '访问时间',
  `access_time` datetime DEFAULT NULL COMMENT '访问详细时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `history_article_user` (`history_article_id`,`history_user_id`,`create_time`),
  KEY `history_user_id` (`history_user_id`),
  CONSTRAINT `history_article_id` FOREIGN KEY (`history_article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `history_user_id` FOREIGN KEY (`history_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4;

/*Data for the table `history` */

insert  into `history`(`id`,`history_article_id`,`history_user_id`,`create_time`,`access_time`) values (33,2,1,'2020-03-01','2020-03-01 23:14:17'),(34,1,1,'2020-03-10','2020-03-10 22:25:04'),(40,1,5,'2020-03-25','2020-03-25 22:19:19');

/*Table structure for table `likes` */

DROP TABLE IF EXISTS `likes`;

CREATE TABLE `likes` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `likes_user_id` int(11) unsigned NOT NULL COMMENT '点赞用户id',
  `likes_article_id` int(11) unsigned NOT NULL COMMENT '点赞文章id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `status` int(11) DEFAULT '1' COMMENT '是否点赞或取消',
  PRIMARY KEY (`id`),
  KEY `like_user_id` (`likes_user_id`),
  KEY `like_article_id` (`likes_article_id`),
  CONSTRAINT `like_article_id` FOREIGN KEY (`likes_article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `like_user_id` FOREIGN KEY (`likes_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `likes` */

insert  into `likes`(`id`,`likes_user_id`,`likes_article_id`,`create_time`,`update_time`,`status`) values (1,1,1,'2020-02-23 16:59:37','2020-02-23 16:59:39',1),(4,1,2,NULL,'2020-03-08 23:29:32',1),(5,1,6,NULL,'2020-03-24 18:30:51',1);

/*Table structure for table `media` */

DROP TABLE IF EXISTS `media`;

CREATE TABLE `media` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `file_name` varchar(100) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(255) NOT NULL COMMENT '文件路径',
  `file_type` int(11) NOT NULL DEFAULT '0' COMMENT '文件类型',
  `article_id` int(10) DEFAULT '0' COMMENT '文章id',
  `create_time` datetime NOT NULL COMMENT '上传时间',
  `user_id` int(11) DEFAULT NULL COMMENT '用户id',
  `pid` int(11) DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `media` */

/*Table structure for table `message` */

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `sender_id` int(11) unsigned NOT NULL COMMENT '发送者id',
  `receiver_id` int(11) unsigned NOT NULL COMMENT '接收者id',
  `content` varchar(1000) NOT NULL COMMENT '私信内容',
  `create_time` datetime DEFAULT NULL COMMENT '发送时间',
  `type` int(11) DEFAULT '0' COMMENT '私信类型',
  `isRead` int(11) DEFAULT '0' COMMENT '是否已读',
  PRIMARY KEY (`id`),
  KEY `sender_id` (`sender_id`),
  KEY `receiver` (`receiver_id`),
  CONSTRAINT `receiver` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `sender_id` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

/*Data for the table `message` */

insert  into `message`(`id`,`sender_id`,`receiver_id`,`content`,`create_time`,`type`,`isRead`) values (8,5,1,'这是收到的私信','2020-03-25 21:12:16',0,0),(9,1,5,'这是收到的私信','2020-03-25 21:13:28',0,1),(10,1,5,'这是收到的私信2','2020-03-25 21:13:54',0,1);

/*Table structure for table `permission` */

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `type` int(11) DEFAULT '0' COMMENT '权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

/*Data for the table `permission` */

insert  into `permission`(`id`,`url`,`type`) values (1,'/Blog/sensitive/save',2),(2,'/Blog/user/updatePassword',1),(5,'/Blog/toast/isRead',1),(6,'/Blog/article/save',1),(7,'/Blog/article/delete',1),(8,'/Blog/article/get',2);

/*Table structure for table `sensitiveword` */

DROP TABLE IF EXISTS `sensitiveword`;

CREATE TABLE `sensitiveword` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `word` varchar(10) NOT NULL COMMENT '敏感词',
  PRIMARY KEY (`id`),
  KEY `word` (`word`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

/*Data for the table `sensitiveword` */

insert  into `sensitiveword`(`id`,`word`) values (8,'你妈'),(5,'你妈逼'),(1,'傻逼'),(4,'叼你只西'),(2,'叼你老母'),(3,'扑街'),(6,'草你妈'),(10,'车啊');

/*Table structure for table `tag` */

DROP TABLE IF EXISTS `tag`;

CREATE TABLE `tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `tag_name` varchar(255) NOT NULL COMMENT '标签名',
  `tag_description` varchar(255) DEFAULT NULL COMMENT '标签描述',
  `creator_id` int(11) NOT NULL COMMENT '创建者id',
  `create_time` date NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `tag` */

insert  into `tag`(`id`,`tag_name`,`tag_description`,`creator_id`,`create_time`) values (1,'JAVA','java相关',5,'2020-02-08'),(2,'数据库','MYSQL',1,'2020-02-08'),(3,'高达','高达相关',1,'2020-02-08'),(6,'高达2',NULL,5,'2020-02-09'),(8,'新的Tag',NULL,5,'2020-03-02'),(9,'高达3',NULL,5,'2020-03-25'),(10,'asd',NULL,5,'2020-03-25'),(11,'aqq',NULL,5,'2020-03-25');

/*Table structure for table `toast` */

DROP TABLE IF EXISTS `toast`;

CREATE TABLE `toast` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `toast_user_id` int(11) unsigned NOT NULL COMMENT '用户id',
  `toast_sender_id` int(11) unsigned NOT NULL COMMENT '发送者id',
  `create_time` datetime DEFAULT NULL COMMENT '发送时间',
  `is_read` int(11) DEFAULT '1' COMMENT '是否已读',
  `toast_type` int(11) DEFAULT NULL COMMENT '消息类型',
  `toast_article_id` int(11) unsigned NOT NULL COMMENT '文章id',
  `toast_comment_id` int(11) unsigned NOT NULL COMMENT '评论id',
  PRIMARY KEY (`id`),
  KEY `toast_user_id` (`toast_user_id`),
  KEY `toast_sender_id` (`toast_sender_id`),
  KEY `toast_article_id` (`toast_article_id`),
  KEY `toast_comment_id` (`toast_comment_id`),
  CONSTRAINT `toast_article_id` FOREIGN KEY (`toast_article_id`) REFERENCES `article` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `toast_comment_id` FOREIGN KEY (`toast_comment_id`) REFERENCES `comment` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `toast_sender_id` FOREIGN KEY (`toast_sender_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `toast_user_id` FOREIGN KEY (`toast_user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;

/*Data for the table `toast` */

insert  into `toast`(`id`,`toast_user_id`,`toast_sender_id`,`create_time`,`is_read`,`toast_type`,`toast_article_id`,`toast_comment_id`) values (15,1,5,'2020-03-25 20:12:12',1,1,2,24),(16,5,5,'2020-03-25 20:12:56',0,2,2,25),(17,5,1,'2020-03-25 21:48:58',0,1,1,26);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `user_mail` varchar(255) NOT NULL COMMENT '用户邮箱',
  `image` varchar(255) DEFAULT '/static/etc_image/user.png' COMMENT '用户头像路径',
  `register_time` date NOT NULL COMMENT '注册时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '上次登录时间',
  `profile` varchar(1000) DEFAULT NULL COMMENT '用户简介',
  `gender` int(11) DEFAULT '0' COMMENT '性别',
  `forget` varchar(200) DEFAULT NULL,
  `isAdmin` int(11) DEFAULT '0' COMMENT '是否管理员',
  PRIMARY KEY (`id`),
  KEY `username` (`username`(191)),
  KEY `email` (`user_mail`(191))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`user_mail`,`image`,`register_time`,`last_login_time`,`profile`,`gender`,`forget`,`isAdmin`) values (1,'East','4297f44b13955235245b2497399d7a93','123123123@qq.com','/static/images/60899b1ef74346398580adac6a21da11.jpg','2020-02-08',NULL,'更新自我介绍321asdasd',1,NULL,0),(5,'East_ZD','4297f44b13955235245b2497399d7a93','850222009@qq.com','/static/images/91e6ae4b3dbd4456ac7b8cd11b5b2296.png','2020-02-08',NULL,'新的自我介绍',1,NULL,1),(6,'East_ZD6','4297f44b13955235245b2497399d7a93','321@qq.com','66666','2020-02-04',NULL,NULL,0,NULL,0),(7,'east7','4297f44b13955235245b2497399d7a93','234234@qq.com','7ea63f8d918d46a982de3fef813d561b.jpeg','2020-03-06',NULL,'',1,NULL,0),(8,'谭志东','8ed358a7da3cc760364909d4aaf7321e','345345@qq.com','/static/images/0c4548de4ea64053b5596ecb58d47594.jpg','2020-03-13',NULL,'撒大苏打asdasd',1,NULL,0),(10,'Testing','4297f44b13955235245b2497399d7a93','123456789@qq.com','/static/etc_image/user.png','2020-03-25',NULL,NULL,0,NULL,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
