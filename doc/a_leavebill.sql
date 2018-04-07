/*
Navicat MySQL Data Transfer

Source Server         : test_username_password
Source Server Version : 50621
Source Host           : localhost:3306
Source Database       : activitidemo

Target Server Type    : MYSQL
Target Server Version : 50621
File Encoding         : 65001

Date: 2018-04-07 15:33:30
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for a_leavebill
-- ----------------------------
DROP TABLE IF EXISTS `a_leavebill`;
CREATE TABLE `a_leavebill` (
  `id` int(32) NOT NULL,
  `days` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `leavedate` timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
  `state` int(11) DEFAULT NULL,
  `user_id` int(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of a_leavebill
-- ----------------------------
