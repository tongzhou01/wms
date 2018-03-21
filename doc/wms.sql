/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : wms

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-03-12 17:57:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for cargo_charge
-- ----------------------------
DROP TABLE IF EXISTS `cargo_charge`;
CREATE TABLE `cargo_charge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `expire_time` tinyint(4) DEFAULT NULL COMMENT '免仓管费时间',
  `cargo_fee` decimal(10,2) DEFAULT NULL COMMENT '计费',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓管费配置表';

-- ----------------------------
-- Records of cargo_charge
-- ----------------------------

-- ----------------------------
-- Table structure for cargo_info
-- ----------------------------
DROP TABLE IF EXISTS `cargo_info`;
CREATE TABLE `cargo_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cn_no` varchar(50) DEFAULT NULL COMMENT '国内单号',
  `intl_no` varchar(50) DEFAULT NULL COMMENT '国际单号',
  `order_no` varchar(50) DEFAULT NULL COMMENT '单号',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `customer_no` varchar(50) DEFAULT NULL COMMENT '客户编码',
  `express_company_code` varchar(50) DEFAULT NULL COMMENT '快递公司',
  `express_company` varchar(50) DEFAULT NULL COMMENT '快递公司',
  `shelf_no` varchar(50) DEFAULT NULL COMMENT '货架号',
  `destination` varchar(50) DEFAULT NULL COMMENT '目的地国家',
  `weight` decimal(10,2) DEFAULT NULL COMMENT '重量',
  `length` decimal(10,2) DEFAULT NULL COMMENT '长',
  `wide` decimal(10,2) DEFAULT NULL COMMENT '宽',
  `height` decimal(10,2) DEFAULT NULL COMMENT '高',
  `volume_weight` decimal(10,2) DEFAULT NULL COMMENT '体积重',
  `cargo_charge` decimal(10,2) DEFAULT NULL COMMENT '仓管费',
  `express_charge` decimal(10,2) DEFAULT NULL COMMENT '快递费',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='货物信息表';

-- ----------------------------
-- Records of cargo_info
-- ----------------------------

-- ----------------------------
-- Table structure for customer_info
-- ----------------------------
DROP TABLE IF EXISTS `customer_info`;
CREATE TABLE `customer_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `customer_no` varchar(50) DEFAULT NULL COMMENT '客户编码',
  `customer_name` varchar(50) DEFAULT NULL COMMENT '客户名称',
  `customer_mobile` varchar(50) DEFAULT NULL COMMENT '客户手机号',
  `customer_company` varchar(50) DEFAULT NULL COMMENT '客户公司',
  `customer_postcode` varchar(50) DEFAULT NULL COMMENT '客户邮编',
  `customer_address` varchar(255) DEFAULT NULL COMMENT '客户地址',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modify` datetime DEFAULT NULL COMMENT '修改时间',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息';

-- ----------------------------
-- Records of customer_info
-- ----------------------------

-- ----------------------------
-- Table structure for express_company
-- ----------------------------
DROP TABLE IF EXISTS `express_company`;
CREATE TABLE `express_company` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名',
  `company_code` varchar(255) DEFAULT NULL COMMENT '公司编码',
  `remark` varchar(255) DEFAULT NULL,
  `is_deleted` tinyint(11) DEFAULT '0',
  `type` tinyint(11) DEFAULT '0' COMMENT '类型：0国内快递，1国际快递',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of express_company
-- ----------------------------

-- ----------------------------
-- Table structure for operation_record
-- ----------------------------
DROP TABLE IF EXISTS `operation_record`;
CREATE TABLE `operation_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `cargo_id` bigint(20) DEFAULT NULL COMMENT '货物ID',
  `operator` varchar(20) DEFAULT NULL COMMENT '操作人',
  `outbound_time` datetime DEFAULT NULL COMMENT '扫描时间',
  `type` datetime DEFAULT NULL COMMENT '类型（0表示入库，1表示出库）',
  `outbound_type_id` bigint(20) DEFAULT NULL COMMENT '出库类型ID',
  `is_deleted` tinyint(4) DEFAULT '0' COMMENT '逻辑删除（0未删除，1已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='入库出库记录';

-- ----------------------------
-- Records of operation_record
-- ----------------------------

-- ----------------------------
-- Table structure for outbound_type
-- ----------------------------
DROP TABLE IF EXISTS `outbound_type`;
CREATE TABLE `outbound_type` (
  `id` bigint(20) NOT NULL,
  `outbound_type` varchar(50) DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modify` datetime DEFAULT NULL,
  `is_deleted` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='出库类型配置';

-- ----------------------------
-- Records of outbound_type
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `role_id` bigint(11) DEFAULT NULL COMMENT '角色ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户名',
  `real_name` varchar(50) DEFAULT '' COMMENT '真实姓名',
  `password` varchar(50) DEFAULT '' COMMENT '加密密码MD5',
  `sex` tinyint(11) DEFAULT NULL COMMENT '性别（0男，1女）',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` bigint(11) DEFAULT NULL COMMENT '创建人ID',
  `birthday` datetime DEFAULT NULL COMMENT '出生年月',
  `department` varchar(20) DEFAULT '' COMMENT '部门',
  `position` varchar(20) DEFAULT '' COMMENT '职位',
  `mobile` varchar(20) DEFAULT '' COMMENT '手机号码',
  `telephone` varchar(20) DEFAULT '' COMMENT '办公电话',
  `emall` varchar(50) DEFAULT '' COMMENT '邮箱',
  `remark` varchar(250) DEFAULT '' COMMENT '备注',
  `is_deleted` tinyint(11) DEFAULT '0' COMMENT '是否删除 1已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', '1', '1', '嘎嘎', 'c4ca4238a0b923820dcc509a6f75849b', '1', '2017-09-22 14:20:32', '1', '2017-09-22 14:20:36', '', '', '18255458650', '', '', '', '0');
