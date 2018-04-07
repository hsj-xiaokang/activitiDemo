/*
Navicat MySQL Data Transfer

Source Server         : test_username_password
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : activitidemo

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-04-07 15:33:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for city
-- ----------------------------
DROP TABLE IF EXISTS `city`;
CREATE TABLE `city` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '城市编号',
  `province_id` int(10) unsigned NOT NULL COMMENT '省份编号',
  `city_name` varchar(25) DEFAULT NULL COMMENT '城市名称',
  `description` varchar(25) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4404 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of city
-- ----------------------------
INSERT INTO `city` VALUES ('1101', '11', '北京市', '以前工作');
INSERT INTO `city` VALUES ('4301', '43', '长沙市', '以后定居地');
INSERT INTO `city` VALUES ('4403', '44', '深圳市', '工作城市');
