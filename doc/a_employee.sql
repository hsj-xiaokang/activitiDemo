/*
Navicat MySQL Data Transfer

Source Server         : test_username_password
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : activitidemo

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-04-07 15:33:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for a_employee
-- ----------------------------
DROP TABLE IF EXISTS `a_employee`;
CREATE TABLE `a_employee` (
  `id` int(32) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `manager_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of a_employee
-- ----------------------------
INSERT INTO `a_employee` VALUES ('1', '王中军', '123', 'ee@163.com', 'boss', null);
INSERT INTO `a_employee` VALUES ('2', '冯小刚经纪人', '123', 'dd@163.com', 'manager', '1');
INSERT INTO `a_employee` VALUES ('3', '范冰冰经纪人', '123', 'cc@163.com', 'manager', '1');
INSERT INTO `a_employee` VALUES ('4', '冯小刚', '123', 'bb@163.com', 'user', '2');
INSERT INTO `a_employee` VALUES ('5', '范冰冰', '123', 'aa@163.com', 'user', '3');
