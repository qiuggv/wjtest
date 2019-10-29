CREATE DATABASE IF NOT EXISTS `demo`;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '自增Id',
  `username` VARCHAR(64) NOT NULL COMMENT '用户名',
  `password` VARCHAR(64) COMMENT '密码',
  `email` VARCHAR(64) COMMENT '邮箱地址',
  `enabled` TINYINT(1) COMMENT '是否可用',
  `expired` TINYINT(1) COMMENT '是否过期',
  PRIMARY KEY (`Id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';


INSERT INTO `user` (username, PASSWORD, email, enabled, expired) VALUES ('zhangsan', '123456', '274429987@qq.com', 1, 0);