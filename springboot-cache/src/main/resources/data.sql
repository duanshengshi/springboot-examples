DROP DATABASE IF EXISTS `cacheTest`;
CREATE DATABASE `cacheTest`;
USE `cacheTest`;
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int primary key,
  `name` varchar(32),
  `mobile` varchar(11),
  `sex` varchar(8)
);
INSERT INTO student(id,name,mobile,sex) VALUES (1, 'dss','12345678901','man');