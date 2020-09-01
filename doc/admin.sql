/*
 Navicat Premium Data Transfer

 Source Server         : 51wangshi
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : 51wangshi.com:3306
 Source Schema         : mallplus

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 27/07/2020 14:32:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin_gen_table
-- ----------------------------
DROP TABLE IF EXISTS `admin_gen_table`;
CREATE TABLE `admin_gen_table`  (
  `table_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '表名称',
  `table_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '表描述',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '实体类名称',
  `tpl_category` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'crud' COMMENT '使用的模板（crud单表操作 tree树表操作）',
  `package_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成包路径',
  `module_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成模块名',
  `business_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成业务名',
  `function_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成功能名',
  `function_author` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '生成功能作者',
  `options` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '其它生成选项',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`table_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 427 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成业务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_gen_table
-- ----------------------------
INSERT INTO `admin_gen_table` VALUES (423, 'oms_order', '订单表', 'OmsOrder', 'crud', 'com.ruoyi.project.marking', 'marking', 'order', '订单', 'mallplus', NULL, 'admin', '2020-06-30 12:06:21', '', NULL, NULL);
INSERT INTO `admin_gen_table` VALUES (424, 'sms_pre_sale', '', 'SmsPreSale', 'crud', 'com.ruoyi.project.marking', 'marking', 'sale', NULL, 'mallplus', NULL, 'admin', '2020-06-30 12:06:21', '', NULL, NULL);
INSERT INTO `admin_gen_table` VALUES (425, 'sys_store_settle_records', '', 'SysStoreSettleRecords', 'crud', 'com.ruoyi.project.marking', 'marking', 'records', NULL, 'mallplus', NULL, 'admin', '2020-06-30 12:06:21', '', NULL, NULL);
INSERT INTO `admin_gen_table` VALUES (426, 'admin_sys_post', '岗位信息表', 'AdminSysPost', 'crud', 'com.ruoyi.project.marking', 'marking', 'post', '岗位信息', 'mallplus', NULL, 'ry', '2020-07-22 11:47:22', '', NULL, NULL);

-- ----------------------------
-- Table structure for admin_gen_table_column
-- ----------------------------
DROP TABLE IF EXISTS `admin_gen_table_column`;
CREATE TABLE `admin_gen_table_column`  (
  `column_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `table_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '归属表编号',
  `column_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列名称',
  `column_comment` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列描述',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '列类型',
  `java_type` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'JAVA类型',
  `java_field` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'JAVA字段名',
  `is_pk` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否主键（1是）',
  `is_increment` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否自增（1是）',
  `is_required` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否必填（1是）',
  `is_insert` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否为插入字段（1是）',
  `is_edit` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否编辑字段（1是）',
  `is_list` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否列表字段（1是）',
  `is_query` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '是否查询字段（1是）',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'EQ' COMMENT '查询方式（等于、不等于、大于、小于、范围）',
  `html_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '显示类型（文本框、文本域、下拉框、复选框、单选框、日期控件）',
  `dict_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`column_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4919 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '代码生成业务表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_gen_table_column
-- ----------------------------
INSERT INTO `admin_gen_table_column` VALUES (4835, '423', 'id', '订单id', 'bigint(20)', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4836, '423', 'member_id', NULL, 'bigint(20)', 'Long', 'memberId', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4837, '423', 'coupon_id', NULL, 'bigint(20)', 'Long', 'couponId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4838, '423', 'order_sn', '订单编号', 'varchar(255)', 'String', 'orderSn', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4839, '423', 'create_time', '提交时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 5, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4840, '423', 'member_username', '用户帐号', 'varchar(512)', 'String', 'memberUsername', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'textarea', '', 6, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4841, '423', 'total_amount', '订单总金额', 'decimal(10,2)', 'Double', 'totalAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 7, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4842, '423', 'pay_amount', '应付金额（实际支付金额）', 'decimal(10,2)', 'Double', 'payAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 8, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4843, '423', 'freight_amount', '运费金额', 'decimal(10,2)', 'Double', 'freightAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 9, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4844, '423', 'promotion_amount', '促销优化金额（促销价、满减、阶梯价）', 'decimal(10,2)', 'Double', 'promotionAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 10, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4845, '423', 'integration_amount', '积分抵扣金额', 'decimal(10,2)', 'Double', 'integrationAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 11, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4846, '423', 'coupon_amount', '优惠券抵扣金额', 'decimal(10,2)', 'Double', 'couponAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 12, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4847, '423', 'discount_amount', '管理员后台调整订单使用的折扣金额', 'decimal(10,2)', 'Double', 'discountAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 13, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4848, '423', 'pay_type', '支付方式：0->未支付；1->支付宝；2->微信', 'int(11)', 'Long', 'payType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 14, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4849, '423', 'source_type', '订单来源：0->PC订单；1->app订单', 'int(11)', 'Long', 'sourceType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 15, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4850, '423', 'status', '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单', 'int(11)', 'Long', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 16, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4851, '423', 'order_type', '订单类型：0->正常订单；1->秒杀订单', 'int(11)', 'Long', 'orderType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 17, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4852, '423', 'delivery_company', '物流公司(配送方式)', 'varchar(64)', 'String', 'deliveryCompany', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 18, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4853, '423', 'delivery_sn', '物流单号', 'varchar(64)', 'String', 'deliverySn', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 19, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4854, '423', 'auto_confirm_day', '自动确认时间（天）', 'int(11)', 'Long', 'autoConfirmDay', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 20, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4855, '423', 'integration', '可以获得的积分', 'int(11)', 'Long', 'integration', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 21, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4856, '423', 'growth', '可以活动的成长值', 'int(11)', 'Long', 'growth', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 22, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4857, '423', 'promotion_info', '活动信息', 'varchar(100)', 'String', 'promotionInfo', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 23, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4858, '423', 'bill_type', '发票类型：0->不开发票；1->电子发票；2->纸质发票', 'int(11)', 'Long', 'billType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 24, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4859, '423', 'bill_header', '发票抬头', 'varchar(200)', 'String', 'billHeader', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 25, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4860, '423', 'bill_content', '发票内容', 'varchar(200)', 'String', 'billContent', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 26, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4861, '423', 'bill_receiver_phone', '收票人电话', 'varchar(32)', 'String', 'billReceiverPhone', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 27, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4862, '423', 'bill_receiver_email', '收票人邮箱', 'varchar(64)', 'String', 'billReceiverEmail', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 28, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4863, '423', 'receiver_name', '收货人姓名', 'varchar(100)', 'String', 'receiverName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 29, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4864, '423', 'receiver_phone', '收货人电话', 'varchar(32)', 'String', 'receiverPhone', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 30, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4865, '423', 'receiver_post_code', '收货人邮编', 'varchar(32)', 'String', 'receiverPostCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 31, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4866, '423', 'receiver_province', '省份/直辖市', 'varchar(32)', 'String', 'receiverProvince', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 32, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4867, '423', 'receiver_city', '城市', 'varchar(32)', 'String', 'receiverCity', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 33, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4868, '423', 'receiver_region', '区', 'varchar(32)', 'String', 'receiverRegion', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 34, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4869, '423', 'receiver_detail_address', '详细地址', 'varchar(200)', 'String', 'receiverDetailAddress', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 35, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4870, '423', 'note', '订单备注', 'varchar(500)', 'String', 'note', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'textarea', '', 36, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4871, '423', 'confirm_status', '确认收货状态：0->未确认；1->已确认', 'int(11)', 'Long', 'confirmStatus', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 37, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4872, '423', 'delete_status', '删除状态：0->未删除；1->已删除', 'int(11)', 'Long', 'deleteStatus', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'radio', '', 38, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4873, '423', 'use_integration', '下单时使用的积分', 'int(11)', 'Long', 'useIntegration', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 39, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4874, '423', 'payment_time', '支付时间', 'datetime', 'Date', 'paymentTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 40, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4875, '423', 'delivery_time', '发货时间', 'datetime', 'Date', 'deliveryTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 41, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4876, '423', 'receive_time', '确认收货时间', 'datetime', 'Date', 'receiveTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 42, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4877, '423', 'comment_time', '评价时间', 'datetime', 'Date', 'commentTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 43, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4878, '423', 'modify_time', '修改时间', 'datetime', 'Date', 'modifyTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 44, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4879, '423', 'prepay_id', NULL, 'varchar(255)', 'String', 'prepayId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 45, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4880, '423', 'supply_id', NULL, 'bigint(20)', 'Long', 'supplyId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 46, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4881, '423', 'goods_id', NULL, 'bigint(20)', 'Long', 'goodsId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 47, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4882, '423', 'goods_name', NULL, 'varchar(255)', 'String', 'goodsName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 48, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4883, '423', 'school_id', NULL, 'bigint(20)', 'Long', 'schoolId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 49, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4884, '423', 'store_id', '所属店铺', 'int(11)', 'Long', 'storeId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 50, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4885, '423', 'receiver_id', NULL, 'bigint(20)', 'Long', 'receiverId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 51, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4886, '423', 'group_id', NULL, 'bigint(20)', 'Long', 'groupId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 52, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4887, '423', 'tax_type', '是否开发票 1=不发票 2=个人发票 3=公司发票', 'smallint(6)', 'Integer', 'taxType', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'select', '', 53, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4888, '423', 'tax_content', '发票内容', 'varchar(255)', 'String', 'taxContent', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 54, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4889, '423', 'tax_code', '税号', 'varchar(50)', 'String', 'taxCode', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 55, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4890, '423', 'tax_title', '发票抬头', 'varchar(50)', 'String', 'taxTitle', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 56, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4891, '423', 'is_comment', '是否评论，1未评论，2已评论', 'tinyint(3) unsigned', 'Integer', 'isComment', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 57, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4892, '423', 'store_name', NULL, 'varchar(255)', 'String', 'storeName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 58, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4893, '423', 'pid', NULL, 'bigint(20)', 'Long', 'pid', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 59, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4894, '423', 'vip_amount', NULL, 'decimal(10,0)', 'Long', 'vipAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 60, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4895, '424', 'id', '编号', 'bigint(20)', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4896, '424', 'percent', '定金百分比', 'int(4)', 'Integer', 'percent', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4897, '424', 'end_time', '尾款支付时间', 'datetime', 'Date', 'endTime', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'datetime', '', 3, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4898, '424', 'demo', '促销描述', 'varchar(255)', 'String', 'demo', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 4, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4899, '424', 'goodsIds', '活动商品', 'varchar(255)', 'String', 'goodsids', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 5, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4900, '424', 'store_id', NULL, 'int(11)', 'Long', 'storeId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 6, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4901, '424', 'create_time', NULL, 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 7, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4902, '425', 'id', NULL, 'bigint(11)', 'Long', 'id', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4903, '425', 'pay_amount', NULL, 'decimal(10,0)', 'Long', 'payAmount', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 2, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4904, '425', 'store_id', NULL, 'int(11)', 'Long', 'storeId', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'input', '', 3, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4905, '425', 'create_time', NULL, 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 4, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4906, '425', 'status', '0 未结算 1 已结算', 'int(4)', 'Integer', 'status', '0', '0', NULL, '1', '1', '1', '1', 'EQ', 'radio', '', 5, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4907, '425', 'update_time', NULL, 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 6, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4908, '425', 'store_name', NULL, 'varchar(255)', 'String', 'storeName', '0', '0', NULL, '1', '1', '1', '1', 'LIKE', 'input', '', 7, 'admin', '2020-06-30 12:06:21', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4909, '426', 'post_id', '岗位ID', 'bigint(20)', 'Long', 'postId', '1', '1', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 1, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4910, '426', 'post_code', '岗位编码', 'varchar(64)', 'String', 'postCode', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 2, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4911, '426', 'post_name', '岗位名称', 'varchar(50)', 'String', 'postName', '0', '0', '1', '1', '1', '1', '1', 'LIKE', 'input', '', 3, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4912, '426', 'post_sort', '显示顺序', 'int(11)', 'Long', 'postSort', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'input', '', 4, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4913, '426', 'status', '状态（0正常 1停用）', 'char(1)', 'String', 'status', '0', '0', '1', '1', '1', '1', '1', 'EQ', 'radio', '', 5, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4914, '426', 'create_by', '创建者', 'varchar(64)', 'String', 'createBy', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'input', '', 6, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4915, '426', 'create_time', '创建时间', 'datetime', 'Date', 'createTime', '0', '0', NULL, '1', NULL, NULL, NULL, 'EQ', 'datetime', '', 7, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4916, '426', 'update_by', '更新者', 'varchar(64)', 'String', 'updateBy', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'input', '', 8, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4917, '426', 'update_time', '更新时间', 'datetime', 'Date', 'updateTime', '0', '0', NULL, '1', '1', NULL, NULL, 'EQ', 'datetime', '', 9, 'ry', '2020-07-22 11:47:22', '', NULL);
INSERT INTO `admin_gen_table_column` VALUES (4918, '426', 'remark', '备注', 'varchar(500)', 'String', 'remark', '0', '0', NULL, '1', '1', '1', NULL, 'EQ', 'textarea', '', 10, 'ry', '2020-07-22 11:47:22', '', NULL);

-- ----------------------------
-- Table structure for admin_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_config`;
CREATE TABLE `admin_sys_config`  (
  `config_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '参数配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_dept`;
CREATE TABLE `admin_sys_dept`  (
  `dept_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父部门id',
  `ancestors` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '部门名称',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `leader` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 226 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '部门表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_dict_data`;
CREATE TABLE `admin_sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(11) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 56 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_dict_type`;
CREATE TABLE `admin_sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_dict_type
-- ----------------------------
INSERT INTO `admin_sys_dict_type` VALUES (26, '证件类型', 'id_type', '0', 'admin', '2020-07-07 10:22:13', '', NULL, NULL);
INSERT INTO `admin_sys_dict_type` VALUES (27, '1', '2333333333311', '0', 'admin', '2020-07-27 11:16:57', '', NULL, '1111111111111111');

-- ----------------------------
-- Table structure for admin_sys_job
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_job`;
CREATE TABLE `admin_sys_job`  (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_job_log`;
CREATE TABLE `admin_sys_job_log`  (
  `job_log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 241 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for admin_sys_logininfor
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_logininfor`;
CREATE TABLE `admin_sys_logininfor`  (
  `info_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `login_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录账号',
  `ipaddr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作系统',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '提示消息',
  `login_time` datetime(0) NULL DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 831 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '系统访问记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_logininfor
-- ----------------------------
INSERT INTO `admin_sys_logininfor` VALUES (232, 'admin', '112.96.37.213', 'XX XX', 'Chrome 8', 'Windows 10', '0', '退出成功', '2020-06-25 19:21:42');
INSERT INTO `admin_sys_logininfor` VALUES (233, 'ry', '183.192.90.26', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-06-25 21:20:54');
INSERT INTO `admin_sys_logininfor` VALUES (234, 'ry', '39.128.224.0', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-06-25 22:12:29');
INSERT INTO `admin_sys_logininfor` VALUES (235, 'ry', '112.80.232.124', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误3次', '2020-06-25 22:57:16');
INSERT INTO `admin_sys_logininfor` VALUES (236, 'admin', '112.80.232.124', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误3次', '2020-06-25 22:57:45');
INSERT INTO `admin_sys_logininfor` VALUES (237, 'ry', '112.80.232.124', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误4次', '2020-06-25 23:03:06');
INSERT INTO `admin_sys_logininfor` VALUES (238, 'admin', '125.85.206.130', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 00:18:16');
INSERT INTO `admin_sys_logininfor` VALUES (239, 'ry', '112.226.16.189', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-06-26 08:29:41');
INSERT INTO `admin_sys_logininfor` VALUES (240, 'admin', '1.182.125.168', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误3次', '2020-06-26 10:30:59');
INSERT INTO `admin_sys_logininfor` VALUES (241, 'ry', '183.211.225.218', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 11:06:06');
INSERT INTO `admin_sys_logininfor` VALUES (242, 'ry', '121.29.112.27', 'XX XX', 'Microsoft Edge', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 11:34:11');
INSERT INTO `admin_sys_logininfor` VALUES (243, 'ry', '113.70.243.196', 'XX XX', 'Firefox 7', 'Windows 10', '1', '密码输入错误4次', '2020-06-26 11:50:41');
INSERT INTO `admin_sys_logininfor` VALUES (244, 'ry', '125.120.12.79', 'XX XX', 'Firefox 7', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-26 11:50:55');
INSERT INTO `admin_sys_logininfor` VALUES (245, 'ry', '116.24.102.185', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 12:18:28');
INSERT INTO `admin_sys_logininfor` VALUES (246, 'ry', '116.24.102.185', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-26 12:24:29');
INSERT INTO `admin_sys_logininfor` VALUES (247, 'admin', '116.24.102.185', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 12:52:03');
INSERT INTO `admin_sys_logininfor` VALUES (248, 'admin', '116.24.102.185', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-26 13:01:08');
INSERT INTO `admin_sys_logininfor` VALUES (249, 'ry', '211.161.245.73', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 13:20:36');
INSERT INTO `admin_sys_logininfor` VALUES (250, 'ry', '211.161.245.73', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-26 13:20:43');
INSERT INTO `admin_sys_logininfor` VALUES (251, 'ry', '112.255.141.179', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-06-26 13:32:39');
INSERT INTO `admin_sys_logininfor` VALUES (252, 'ry', '110.53.98.194', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-26 14:12:30');
INSERT INTO `admin_sys_logininfor` VALUES (253, 'ry', '116.57.230.2', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-26 14:52:00');
INSERT INTO `admin_sys_logininfor` VALUES (254, 'ry', '116.57.230.2', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 14:52:05');
INSERT INTO `admin_sys_logininfor` VALUES (255, 'ry', '116.57.230.2', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-26 14:52:27');
INSERT INTO `admin_sys_logininfor` VALUES (256, 'ry', '222.244.97.254', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 15:34:08');
INSERT INTO `admin_sys_logininfor` VALUES (257, 'ry', '113.111.114.85', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误2次', '2020-06-26 15:39:37');
INSERT INTO `admin_sys_logininfor` VALUES (258, 'ry', '222.244.97.254', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误4次', '2020-06-26 16:35:05');
INSERT INTO `admin_sys_logininfor` VALUES (259, 'ry', '120.230.63.184', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-26 17:14:31');
INSERT INTO `admin_sys_logininfor` VALUES (260, 'admin', '120.230.63.184', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 17:14:38');
INSERT INTO `admin_sys_logininfor` VALUES (261, 'ry', '183.20.57.59', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-26 17:21:28');
INSERT INTO `admin_sys_logininfor` VALUES (262, 'ry', '117.34.177.15', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-26 19:34:12');
INSERT INTO `admin_sys_logininfor` VALUES (263, 'ry', '114.253.172.29', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误2次', '2020-06-26 19:37:22');
INSERT INTO `admin_sys_logininfor` VALUES (264, 'ry', '183.223.254.255', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-26 20:43:18');
INSERT INTO `admin_sys_logininfor` VALUES (265, 'ry', '183.208.51.195', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误3次', '2020-06-26 21:26:30');
INSERT INTO `admin_sys_logininfor` VALUES (266, 'ry', '183.208.51.195', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误4次', '2020-06-26 21:26:30');
INSERT INTO `admin_sys_logininfor` VALUES (267, 'ry', '183.208.51.195', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-06-26 21:26:36');
INSERT INTO `admin_sys_logininfor` VALUES (268, 'ry', '183.208.51.195', 'XX XX', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-06-26 21:51:49');
INSERT INTO `admin_sys_logininfor` VALUES (269, 'ry', '163.179.46.29', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-26 21:57:25');
INSERT INTO `admin_sys_logininfor` VALUES (270, 'admin', '101.80.2.128', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-06-26 22:10:28');
INSERT INTO `admin_sys_logininfor` VALUES (271, 'ry', '27.198.240.97', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-26 22:42:42');
INSERT INTO `admin_sys_logininfor` VALUES (272, 'ry', '27.198.240.97', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-26 22:43:02');
INSERT INTO `admin_sys_logininfor` VALUES (273, 'ry', '27.198.240.97', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误3次', '2020-06-26 22:43:05');
INSERT INTO `admin_sys_logininfor` VALUES (274, 'ry', '121.227.225.123', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-06-27 00:30:38');
INSERT INTO `admin_sys_logininfor` VALUES (275, 'ry', '114.222.37.137', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-27 10:21:09');
INSERT INTO `admin_sys_logininfor` VALUES (276, 'ry', '119.164.29.157', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-27 11:01:30');
INSERT INTO `admin_sys_logininfor` VALUES (277, 'admin', '119.164.29.157', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-27 11:01:48');
INSERT INTO `admin_sys_logininfor` VALUES (278, 'admin', '223.104.131.211', 'XX XX', 'Mobile Safari', 'Mac OS X (iPhone)', '0', '登录成功', '2020-06-27 11:15:13');
INSERT INTO `admin_sys_logininfor` VALUES (279, 'ry', '14.135.220.168', 'XX XX', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-06-27 11:15:19');
INSERT INTO `admin_sys_logininfor` VALUES (280, 'admin', '121.227.225.123', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-06-27 11:53:57');
INSERT INTO `admin_sys_logininfor` VALUES (281, 'ry', '183.160.247.12', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-27 13:42:02');
INSERT INTO `admin_sys_logininfor` VALUES (282, 'ry', '183.160.247.12', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-27 13:42:06');
INSERT INTO `admin_sys_logininfor` VALUES (283, 'ry', '61.156.120.130', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-06-27 15:33:56');
INSERT INTO `admin_sys_logininfor` VALUES (284, 'ry', '223.240.217.176', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-27 15:37:36');
INSERT INTO `admin_sys_logininfor` VALUES (285, 'admin', '182.151.233.246', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-27 15:44:21');
INSERT INTO `admin_sys_logininfor` VALUES (286, 'admin', '116.52.80.189', 'XX XX', 'Chrome Mobile', 'Android Mobile', '0', '登录成功', '2020-06-27 15:55:10');
INSERT INTO `admin_sys_logininfor` VALUES (287, 'ry', '112.86.111.193', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-27 16:10:46');
INSERT INTO `admin_sys_logininfor` VALUES (288, 'ry', '112.86.111.193', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-27 16:10:50');
INSERT INTO `admin_sys_logininfor` VALUES (289, 'ry', '122.97.178.1', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误2次', '2020-06-27 16:18:47');
INSERT INTO `admin_sys_logininfor` VALUES (290, 'admin', '122.97.178.1', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-06-27 16:20:46');
INSERT INTO `admin_sys_logininfor` VALUES (291, 'admin', '122.97.178.1', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误2次', '2020-06-27 16:20:57');
INSERT INTO `admin_sys_logininfor` VALUES (292, 'ry', '27.198.240.25', 'XX XX', 'Chrome', 'Windows 8', '1', '密码输入错误1次', '2020-06-27 19:02:47');
INSERT INTO `admin_sys_logininfor` VALUES (293, 'ry', '27.198.240.25', 'XX XX', 'Apple WebKit', 'Mac OS X (iPhone)', '1', '密码输入错误4次', '2020-06-27 19:11:08');
INSERT INTO `admin_sys_logininfor` VALUES (294, 'ry', '60.255.179.23', 'XX XX', 'Safari', 'Mac OS X', '1', '验证码错误', '2020-06-27 21:22:20');
INSERT INTO `admin_sys_logininfor` VALUES (295, 'ry', '113.66.210.127', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-27 22:09:24');
INSERT INTO `admin_sys_logininfor` VALUES (296, 'ry', '113.66.210.127', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-27 22:09:30');
INSERT INTO `admin_sys_logininfor` VALUES (297, 'ry', '113.240.129.226', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误3次', '2020-06-27 22:27:24');
INSERT INTO `admin_sys_logininfor` VALUES (298, 'admin', '218.17.39.178', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-27 23:37:21');
INSERT INTO `admin_sys_logininfor` VALUES (299, 'ry', '223.104.175.93', 'XX XX', 'Apple WebKit', 'Mac OS X (iPhone)', '1', '密码输入错误1次', '2020-06-28 07:17:17');
INSERT INTO `admin_sys_logininfor` VALUES (300, 'ry', '59.61.216.122', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-06-28 08:07:52');
INSERT INTO `admin_sys_logininfor` VALUES (301, 'ry', '113.250.228.95', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误3次', '2020-06-28 10:02:49');
INSERT INTO `admin_sys_logininfor` VALUES (302, 'admin123', '222.173.73.78', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-28 10:04:50');
INSERT INTO `admin_sys_logininfor` VALUES (303, 'ry', '113.240.223.151', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 10:19:20');
INSERT INTO `admin_sys_logininfor` VALUES (304, 'admin', '112.25.180.234', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-06-28 10:55:25');
INSERT INTO `admin_sys_logininfor` VALUES (305, 'admin', '125.69.40.42', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-28 10:57:54');
INSERT INTO `admin_sys_logininfor` VALUES (306, 'ry', '27.38.177.12', 'XX XX', 'Firefox 7', 'Windows 10', '1', '验证码错误', '2020-06-28 10:59:54');
INSERT INTO `admin_sys_logininfor` VALUES (307, 'admin', '111.200.8.188', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 11:31:07');
INSERT INTO `admin_sys_logininfor` VALUES (308, 'mall', '58.250.17.18', 'XX XX', 'Chrome 8', 'Windows 10', '1', '用户不存在/密码错误', '2020-06-28 11:51:59');
INSERT INTO `admin_sys_logininfor` VALUES (309, 'ry', '116.236.163.170', 'XX XX', 'Firefox 7', 'Ubuntu', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 11:55:38');
INSERT INTO `admin_sys_logininfor` VALUES (310, 'ry', '124.193.126.50', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误4次', '2020-06-28 12:22:28');
INSERT INTO `admin_sys_logininfor` VALUES (311, 'ry', '124.193.126.50', 'XX XX', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-06-28 12:22:37');
INSERT INTO `admin_sys_logininfor` VALUES (312, 'ry', '210.83.220.75', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-06-28 12:49:47');
INSERT INTO `admin_sys_logininfor` VALUES (313, 'admin', '124.195.137.150', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 13:16:35');
INSERT INTO `admin_sys_logininfor` VALUES (314, 'admin', '124.195.137.150', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-06-28 13:17:18');
INSERT INTO `admin_sys_logininfor` VALUES (315, 'ry', '222.85.243.215', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 13:23:58');
INSERT INTO `admin_sys_logininfor` VALUES (316, 'ry', '222.85.243.215', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-28 13:24:04');
INSERT INTO `admin_sys_logininfor` VALUES (317, 'ry', '218.85.121.154', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误5次', '2020-06-28 13:59:01');
INSERT INTO `admin_sys_logininfor` VALUES (318, 'ry', '218.85.121.154', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 13:59:02');
INSERT INTO `admin_sys_logininfor` VALUES (319, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 14:00:29');
INSERT INTO `admin_sys_logininfor` VALUES (320, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-28 14:00:37');
INSERT INTO `admin_sys_logininfor` VALUES (321, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 14:00:53');
INSERT INTO `admin_sys_logininfor` VALUES (322, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-28 14:02:01');
INSERT INTO `admin_sys_logininfor` VALUES (323, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-06-28 14:02:13');
INSERT INTO `admin_sys_logininfor` VALUES (324, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-28 14:02:15');
INSERT INTO `admin_sys_logininfor` VALUES (325, 'ry', '218.85.121.154', 'XX XX', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-06-28 14:03:20');
INSERT INTO `admin_sys_logininfor` VALUES (326, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-28 14:04:54');
INSERT INTO `admin_sys_logininfor` VALUES (327, 'shangjia', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '用户不存在/密码错误', '2020-06-28 14:05:11');
INSERT INTO `admin_sys_logininfor` VALUES (328, 'shangjia', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '用户不存在/密码错误', '2020-06-28 14:05:35');
INSERT INTO `admin_sys_logininfor` VALUES (329, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-28 14:05:45');
INSERT INTO `admin_sys_logininfor` VALUES (330, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误4次', '2020-06-28 14:07:17');
INSERT INTO `admin_sys_logininfor` VALUES (331, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-06-28 14:09:07');
INSERT INTO `admin_sys_logininfor` VALUES (332, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-28 14:10:33');
INSERT INTO `admin_sys_logininfor` VALUES (333, 'ry', '114.84.214.12', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 14:11:25');
INSERT INTO `admin_sys_logininfor` VALUES (334, 'ry', '117.184.77.26', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误4次', '2020-06-28 15:02:30');
INSERT INTO `admin_sys_logininfor` VALUES (335, 'ry', '117.184.77.26', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-06-28 15:07:37');
INSERT INTO `admin_sys_logininfor` VALUES (336, 'ry', '117.159.26.206', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误2次', '2020-06-28 15:47:21');
INSERT INTO `admin_sys_logininfor` VALUES (337, 'ry', '219.145.16.35', 'XX XX', 'Internet Explorer 11', 'Windows 10', '1', '验证码错误', '2020-06-28 16:09:11');
INSERT INTO `admin_sys_logininfor` VALUES (338, 'ry', '219.239.110.14', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-28 16:10:45');
INSERT INTO `admin_sys_logininfor` VALUES (339, 'ry', '123.160.246.143', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 16:40:43');
INSERT INTO `admin_sys_logininfor` VALUES (340, 'ry', '112.224.21.34', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 17:09:20');
INSERT INTO `admin_sys_logininfor` VALUES (341, 'ry', '171.34.166.17', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-28 17:13:29');
INSERT INTO `admin_sys_logininfor` VALUES (342, 'ry', '223.104.23.223', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 17:27:21');
INSERT INTO `admin_sys_logininfor` VALUES (343, 'ry', '210.83.216.36', 'XX XX', 'Microsoft Edge', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 18:00:30');
INSERT INTO `admin_sys_logininfor` VALUES (344, 'ry', '120.37.45.141', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 18:10:33');
INSERT INTO `admin_sys_logininfor` VALUES (345, 'ry', '112.92.210.12', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 18:12:57');
INSERT INTO `admin_sys_logininfor` VALUES (346, 'ry', '114.55.171.170', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-28 18:31:18');
INSERT INTO `admin_sys_logininfor` VALUES (347, 'ry', '124.90.43.2', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-06-28 19:05:49');
INSERT INTO `admin_sys_logininfor` VALUES (348, 'ry', '220.231.206.185', 'XX XX', 'Firefox 7', 'Windows 10', '1', '验证码错误', '2020-06-28 19:20:21');
INSERT INTO `admin_sys_logininfor` VALUES (349, 'admin', '220.231.206.185', 'XX XX', 'Firefox 7', 'Windows 10', '1', '密码输入错误2次', '2020-06-28 19:20:56');
INSERT INTO `admin_sys_logininfor` VALUES (350, 'admin', '117.158.192.60', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 20:57:53');
INSERT INTO `admin_sys_logininfor` VALUES (351, 'admin', '117.158.192.60', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-28 20:57:59');
INSERT INTO `admin_sys_logininfor` VALUES (352, 'ry', '113.218.168.173', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 21:41:16');
INSERT INTO `admin_sys_logininfor` VALUES (353, 'ry', '113.218.168.173', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-28 21:41:19');
INSERT INTO `admin_sys_logininfor` VALUES (354, 'ry', '120.239.196.82', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误4次', '2020-06-28 21:47:40');
INSERT INTO `admin_sys_logininfor` VALUES (355, 'ry', '171.113.110.197', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-28 21:50:00');
INSERT INTO `admin_sys_logininfor` VALUES (356, 'ry', '120.239.196.82', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-28 21:51:55');
INSERT INTO `admin_sys_logininfor` VALUES (357, 'ry', '120.239.196.82', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-28 21:52:02');
INSERT INTO `admin_sys_logininfor` VALUES (358, 'admin', '110.191.241.104', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-28 21:59:42');
INSERT INTO `admin_sys_logininfor` VALUES (359, 'admin', '182.149.203.204', 'XX XX', 'Chrome', 'Windows 8', '1', '密码输入错误1次', '2020-06-28 22:01:43');
INSERT INTO `admin_sys_logininfor` VALUES (360, 'ry', '117.152.211.70', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-28 22:17:58');
INSERT INTO `admin_sys_logininfor` VALUES (361, 'ry', '120.236.240.174', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-29 08:41:58');
INSERT INTO `admin_sys_logininfor` VALUES (362, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-29 10:18:03');
INSERT INTO `admin_sys_logininfor` VALUES (363, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-29 10:18:09');
INSERT INTO `admin_sys_logininfor` VALUES (364, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-29 10:18:16');
INSERT INTO `admin_sys_logininfor` VALUES (365, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-29 10:18:20');
INSERT INTO `admin_sys_logininfor` VALUES (366, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-29 10:19:08');
INSERT INTO `admin_sys_logininfor` VALUES (367, 'test', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '用户不存在/密码错误', '2020-06-29 10:19:25');
INSERT INTO `admin_sys_logininfor` VALUES (368, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-29 10:20:06');
INSERT INTO `admin_sys_logininfor` VALUES (369, 'admin', '180.162.30.82', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-29 11:09:09');
INSERT INTO `admin_sys_logininfor` VALUES (370, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-29 11:14:08');
INSERT INTO `admin_sys_logininfor` VALUES (371, 'ry', '223.221.68.61', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-06-29 11:37:26');
INSERT INTO `admin_sys_logininfor` VALUES (372, 'ry', '223.221.68.61', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误5次', '2020-06-29 11:37:32');
INSERT INTO `admin_sys_logininfor` VALUES (373, 'ry', '113.119.184.155', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-29 11:48:24');
INSERT INTO `admin_sys_logininfor` VALUES (374, 'admin', '182.148.114.180', 'XX XX', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误2次', '2020-06-29 12:16:11');
INSERT INTO `admin_sys_logininfor` VALUES (375, 'admin', '182.148.114.180', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误3次', '2020-06-29 12:16:37');
INSERT INTO `admin_sys_logininfor` VALUES (376, 'ry', '113.109.163.93', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误4次', '2020-06-29 12:49:57');
INSERT INTO `admin_sys_logininfor` VALUES (377, 'admin', '171.217.95.14', 'XX XX', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-06-29 12:57:53');
INSERT INTO `admin_sys_logininfor` VALUES (378, 'ry', '121.13.85.102', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误2次', '2020-06-29 13:18:13');
INSERT INTO `admin_sys_logininfor` VALUES (379, 'ry', '60.191.32.73', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-29 14:00:53');
INSERT INTO `admin_sys_logininfor` VALUES (380, 'admin', '171.217.95.14', 'XX XX', 'Chrome 48', 'Windows 10', '0', '登录成功', '2020-06-29 14:15:21');
INSERT INTO `admin_sys_logininfor` VALUES (381, 'ry', '192.168.1.5', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-29 14:16:15');
INSERT INTO `admin_sys_logininfor` VALUES (382, 'admin', '192.168.1.5', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-29 14:16:44');
INSERT INTO `admin_sys_logininfor` VALUES (383, 'ry', '36.249.180.237', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误4次', '2020-06-29 15:10:06');
INSERT INTO `admin_sys_logininfor` VALUES (384, 'admin', '113.54.222.15', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-29 15:35:45');
INSERT INTO `admin_sys_logininfor` VALUES (385, 'ry', '110.87.118.135', 'XX XX', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-06-29 15:59:24');
INSERT INTO `admin_sys_logininfor` VALUES (386, 'ry', '106.87.10.244', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-29 16:02:27');
INSERT INTO `admin_sys_logininfor` VALUES (387, 'admin', '106.87.10.244', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-06-29 16:03:27');
INSERT INTO `admin_sys_logininfor` VALUES (388, 'ry', '219.128.252.205', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-06-29 16:27:43');
INSERT INTO `admin_sys_logininfor` VALUES (389, 'ry', '219.128.252.205', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-06-29 16:27:57');
INSERT INTO `admin_sys_logininfor` VALUES (390, 'ry', '144.255.145.112', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-29 16:48:52');
INSERT INTO `admin_sys_logininfor` VALUES (391, 'ry', '144.255.145.112', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-29 16:50:57');
INSERT INTO `admin_sys_logininfor` VALUES (392, 'admin', '219.128.252.205', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-29 17:04:53');
INSERT INTO `admin_sys_logininfor` VALUES (393, 'ry', '111.160.133.42', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-06-29 17:07:04');
INSERT INTO `admin_sys_logininfor` VALUES (394, 'ry', '118.120.114.169', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-29 17:12:39');
INSERT INTO `admin_sys_logininfor` VALUES (395, 'ry', '223.88.10.198', 'XX XX', 'Firefox 7', 'Windows 10', '1', '验证码错误', '2020-06-29 17:32:51');
INSERT INTO `admin_sys_logininfor` VALUES (396, 'ry', '183.239.131.122', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-29 17:52:17');
INSERT INTO `admin_sys_logininfor` VALUES (397, 'ry', '222.223.101.208', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-29 18:01:57');
INSERT INTO `admin_sys_logininfor` VALUES (398, 'admin', '222.223.101.208', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-29 18:02:40');
INSERT INTO `admin_sys_logininfor` VALUES (399, 'admin', '222.244.195.112', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-29 19:00:47');
INSERT INTO `admin_sys_logininfor` VALUES (400, 'ry', '36.24.240.156', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误2次', '2020-06-29 19:09:07');
INSERT INTO `admin_sys_logininfor` VALUES (401, 'ry', '119.123.201.113', 'XX XX', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-06-29 19:21:13');
INSERT INTO `admin_sys_logininfor` VALUES (402, 'ry', '119.123.201.113', 'XX XX', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-06-29 19:21:16');
INSERT INTO `admin_sys_logininfor` VALUES (403, 'ry', '112.94.13.55', 'XX XX', 'Safari', 'Mac OS X', '1', '密码输入错误2次', '2020-06-29 19:25:39');
INSERT INTO `admin_sys_logininfor` VALUES (404, 'ry', '58.215.200.58', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-29 20:05:20');
INSERT INTO `admin_sys_logininfor` VALUES (405, 'admin', '58.215.200.58', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-06-29 20:06:02');
INSERT INTO `admin_sys_logininfor` VALUES (406, 'ry', '218.109.202.22', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-29 21:58:18');
INSERT INTO `admin_sys_logininfor` VALUES (407, 'ry', '223.166.205.144', 'XX XX', 'Safari', 'Mac OS X', '1', '密码输入错误2次', '2020-06-29 22:03:58');
INSERT INTO `admin_sys_logininfor` VALUES (408, 'ry', '192.168.1.81', '内网IP', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-06-30 09:41:30');
INSERT INTO `admin_sys_logininfor` VALUES (409, 'admin', '192.168.1.92', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-30 09:41:42');
INSERT INTO `admin_sys_logininfor` VALUES (410, 'admin', '192.168.1.81', '内网IP', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-06-30 09:42:12');
INSERT INTO `admin_sys_logininfor` VALUES (411, 'admin123', '192.168.1.81', '内网IP', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-06-30 09:42:18');
INSERT INTO `admin_sys_logininfor` VALUES (412, 'admin', '192.168.1.81', '内网IP', 'Chrome', 'Windows 7', '0', '登录成功', '2020-06-30 09:43:33');
INSERT INTO `admin_sys_logininfor` VALUES (413, 'admin', '192.168.1.81', '内网IP', 'Chrome', 'Windows 7', '0', '登录成功', '2020-06-30 10:11:20');
INSERT INTO `admin_sys_logininfor` VALUES (414, 'admin', '222.240.228.73', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 10:57:44');
INSERT INTO `admin_sys_logininfor` VALUES (415, 'ry', '115.204.154.205', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-06-30 11:59:46');
INSERT INTO `admin_sys_logininfor` VALUES (416, 'ry', '180.143.127.59', 'XX XX', 'Firefox 7', 'Windows 7', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 12:07:47');
INSERT INTO `admin_sys_logininfor` VALUES (417, 'ry', '117.147.30.212', 'XX XX', 'Apple WebKit', 'Mac OS X (iPhone)', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 15:27:14');
INSERT INTO `admin_sys_logininfor` VALUES (418, 'ry', '45.251.21.162', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 16:19:55');
INSERT INTO `admin_sys_logininfor` VALUES (419, 'ry', '59.108.53.146', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-30 16:46:43');
INSERT INTO `admin_sys_logininfor` VALUES (420, 'admin', '192.168.1.92', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-30 17:00:04');
INSERT INTO `admin_sys_logininfor` VALUES (421, 'admin', '192.168.1.92', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-30 17:02:44');
INSERT INTO `admin_sys_logininfor` VALUES (422, 'ry', '218.249.71.196', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 17:04:24');
INSERT INTO `admin_sys_logininfor` VALUES (423, 'admin', '192.168.1.92', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-30 17:04:32');
INSERT INTO `admin_sys_logininfor` VALUES (424, 'admin', '192.168.1.92', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-06-30 17:04:37');
INSERT INTO `admin_sys_logininfor` VALUES (425, 'admin', '218.249.71.196', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-30 17:07:17');
INSERT INTO `admin_sys_logininfor` VALUES (426, 'ry', '218.249.71.196', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 17:08:43');
INSERT INTO `admin_sys_logininfor` VALUES (427, 'ry', '14.120.107.156', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 17:09:07');
INSERT INTO `admin_sys_logininfor` VALUES (428, 'ry', '220.249.79.138', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 17:18:09');
INSERT INTO `admin_sys_logininfor` VALUES (429, 'ry', '1.207.14.103', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 17:40:13');
INSERT INTO `admin_sys_logininfor` VALUES (430, 'admin', '1.80.0.13', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-06-30 17:57:02');
INSERT INTO `admin_sys_logininfor` VALUES (431, 'ry', '101.226.171.68', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-30 18:41:00');
INSERT INTO `admin_sys_logininfor` VALUES (432, 'ry', '101.226.171.68', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-06-30 18:41:03');
INSERT INTO `admin_sys_logininfor` VALUES (433, 'shangjia', '219.144.169.145', 'XX XX', 'Chrome', 'Windows 7', '1', '用户不存在/密码错误', '2020-06-30 20:02:56');
INSERT INTO `admin_sys_logininfor` VALUES (434, 'ry', '180.164.0.245', 'XX XX', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-06-30 20:39:37');
INSERT INTO `admin_sys_logininfor` VALUES (435, 'ry', '113.127.16.101', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-30 20:57:55');
INSERT INTO `admin_sys_logininfor` VALUES (436, 'ry', '122.188.226.199', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误3次', '2020-06-30 21:01:34');
INSERT INTO `admin_sys_logininfor` VALUES (437, 'admin', '122.188.226.199', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-06-30 21:01:59');
INSERT INTO `admin_sys_logininfor` VALUES (438, 'ry', '122.188.226.199', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误4次', '2020-06-30 21:03:04');
INSERT INTO `admin_sys_logininfor` VALUES (439, 'ry', '112.232.242.52', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-06-30 21:12:37');
INSERT INTO `admin_sys_logininfor` VALUES (440, 'ry', '36.5.117.50', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-30 21:40:51');
INSERT INTO `admin_sys_logininfor` VALUES (441, 'ry', '58.42.229.86', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-06-30 22:08:21');
INSERT INTO `admin_sys_logininfor` VALUES (442, 'ry', '125.111.80.80', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-06-30 22:20:04');
INSERT INTO `admin_sys_logininfor` VALUES (443, 'ry', '121.237.48.138', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-30 23:50:16');
INSERT INTO `admin_sys_logininfor` VALUES (444, 'admin', '121.237.48.138', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-06-30 23:50:27');
INSERT INTO `admin_sys_logininfor` VALUES (445, 'admin', '101.89.90.49', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-01 09:15:17');
INSERT INTO `admin_sys_logininfor` VALUES (446, 'ry', '222.210.14.153', 'XX XX', 'Safari', 'Mac OS X', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-01 10:05:32');
INSERT INTO `admin_sys_logininfor` VALUES (447, 'admin', '121.32.181.93', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-07-01 11:22:50');
INSERT INTO `admin_sys_logininfor` VALUES (448, 'ry', '113.240.251.66', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-01 11:35:28');
INSERT INTO `admin_sys_logininfor` VALUES (449, 'ry', '113.240.251.66', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-01 11:35:34');
INSERT INTO `admin_sys_logininfor` VALUES (450, 'ry', '113.240.251.66', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-01 11:35:36');
INSERT INTO `admin_sys_logininfor` VALUES (451, 'ry', '61.140.236.208', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-01 12:27:52');
INSERT INTO `admin_sys_logininfor` VALUES (452, 'admin', '61.165.251.206', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-01 13:05:20');
INSERT INTO `admin_sys_logininfor` VALUES (453, 'ry', '119.119.41.224', 'XX XX', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-07-01 14:11:20');
INSERT INTO `admin_sys_logininfor` VALUES (454, 'ry', '113.248.148.158', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-01 14:11:56');
INSERT INTO `admin_sys_logininfor` VALUES (455, 'ry', '113.248.148.158', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-01 14:12:05');
INSERT INTO `admin_sys_logininfor` VALUES (456, 'ry', '1.80.234.206', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-01 14:41:21');
INSERT INTO `admin_sys_logininfor` VALUES (457, 'ry', '123.172.82.171', 'XX XX', 'Chrome 49', 'Windows 10', '1', '密码输入错误4次', '2020-07-01 14:53:21');
INSERT INTO `admin_sys_logininfor` VALUES (458, 'ry', '1.80.234.206', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-01 14:56:10');
INSERT INTO `admin_sys_logininfor` VALUES (459, 'ry', '101.85.37.79', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-07-01 15:26:18');
INSERT INTO `admin_sys_logininfor` VALUES (460, 'ry', '101.85.37.79', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误4次', '2020-07-01 15:26:46');
INSERT INTO `admin_sys_logininfor` VALUES (461, 'ry', '223.73.121.65', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误3次', '2020-07-01 16:18:24');
INSERT INTO `admin_sys_logininfor` VALUES (462, 'ry', '27.186.179.58', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-07-01 16:25:29');
INSERT INTO `admin_sys_logininfor` VALUES (463, 'ry', '125.86.92.14', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-01 17:19:45');
INSERT INTO `admin_sys_logininfor` VALUES (464, 'admin', '117.121.101.9', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-01 17:36:03');
INSERT INTO `admin_sys_logininfor` VALUES (465, 'ry', '123.146.3.166', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误4次', '2020-07-01 17:46:29');
INSERT INTO `admin_sys_logininfor` VALUES (466, 'admin', '14.123.194.143', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误4次', '2020-07-01 17:48:51');
INSERT INTO `admin_sys_logininfor` VALUES (467, 'admin', '103.27.25.70', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-07-01 19:26:11');
INSERT INTO `admin_sys_logininfor` VALUES (468, 'shangjia', '103.27.25.70', 'XX XX', 'Chrome', 'Windows 7', '1', '用户不存在/密码错误', '2020-07-01 19:32:16');
INSERT INTO `admin_sys_logininfor` VALUES (469, 'ry', '123.117.250.84', 'XX XX', 'Firefox 7', 'Windows 10', '1', '密码输入错误1次', '2020-07-01 20:58:07');
INSERT INTO `admin_sys_logininfor` VALUES (470, 'ry', '180.175.138.32', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误3次', '2020-07-01 21:47:26');
INSERT INTO `admin_sys_logininfor` VALUES (471, 'ry', '180.175.138.32', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误2次', '2020-07-01 21:47:30');
INSERT INTO `admin_sys_logininfor` VALUES (472, 'admin', '112.65.53.51', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-01 21:52:52');
INSERT INTO `admin_sys_logininfor` VALUES (473, 'ry', '171.221.35.125', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-01 21:59:37');
INSERT INTO `admin_sys_logininfor` VALUES (474, 'ry', '117.139.251.91', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-01 22:07:45');
INSERT INTO `admin_sys_logininfor` VALUES (475, 'ry', '117.154.100.88', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-01 22:13:55');
INSERT INTO `admin_sys_logininfor` VALUES (476, 'ry', '222.209.58.234', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-02 10:12:28');
INSERT INTO `admin_sys_logininfor` VALUES (477, 'ry', '49.79.10.206', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-02 11:18:22');
INSERT INTO `admin_sys_logininfor` VALUES (478, 'admin', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '0', '登录成功', '2020-07-02 11:34:17');
INSERT INTO `admin_sys_logininfor` VALUES (479, 'admin', '124.134.50.166', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-02 13:56:29');
INSERT INTO `admin_sys_logininfor` VALUES (480, 'ry', '192.168.1.91', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-02 14:00:21');
INSERT INTO `admin_sys_logininfor` VALUES (481, 'ry', '192.168.1.91', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-02 14:00:27');
INSERT INTO `admin_sys_logininfor` VALUES (482, 'admin', '192.168.1.91', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-02 14:00:57');
INSERT INTO `admin_sys_logininfor` VALUES (483, 'admin', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '0', '登录成功', '2020-07-02 14:07:47');
INSERT INTO `admin_sys_logininfor` VALUES (484, 'ry', '113.116.20.252', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-02 15:39:56');
INSERT INTO `admin_sys_logininfor` VALUES (485, 'admin', '192.168.1.91', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-02 16:52:51');
INSERT INTO `admin_sys_logininfor` VALUES (486, 'admin', '192.168.1.91', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-02 16:52:58');
INSERT INTO `admin_sys_logininfor` VALUES (487, 'ry', '36.106.245.149', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-02 17:00:36');
INSERT INTO `admin_sys_logininfor` VALUES (488, 'admin', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '0', '退出成功', '2020-07-02 17:09:35');
INSERT INTO `admin_sys_logininfor` VALUES (489, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-02 17:13:02');
INSERT INTO `admin_sys_logininfor` VALUES (490, 'admin', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '0', '登录成功', '2020-07-02 17:13:22');
INSERT INTO `admin_sys_logininfor` VALUES (491, 'admin', '183.48.30.131', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-02 17:18:15');
INSERT INTO `admin_sys_logininfor` VALUES (492, 'ry', '223.166.241.232', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误3次', '2020-07-02 17:31:56');
INSERT INTO `admin_sys_logininfor` VALUES (493, 'ry', '113.87.2.144', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-02 17:39:50');
INSERT INTO `admin_sys_logininfor` VALUES (494, 'ry', '219.152.24.88', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-02 18:10:18');
INSERT INTO `admin_sys_logininfor` VALUES (495, 'admin', '223.166.241.232', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-02 18:45:35');
INSERT INTO `admin_sys_logininfor` VALUES (496, 'ry', '113.111.106.232', 'XX XX', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-07-02 19:18:04');
INSERT INTO `admin_sys_logininfor` VALUES (497, 'ry', '115.60.60.164', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-07-02 19:39:41');
INSERT INTO `admin_sys_logininfor` VALUES (498, 'admin', '115.60.60.164', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误3次', '2020-07-02 19:52:54');
INSERT INTO `admin_sys_logininfor` VALUES (499, 'ry', '115.60.60.164', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误2次', '2020-07-02 19:53:46');
INSERT INTO `admin_sys_logininfor` VALUES (500, 'ry', '115.60.60.164', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-07-02 19:55:43');
INSERT INTO `admin_sys_logininfor` VALUES (501, 'ry', '115.60.60.164', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-07-02 19:55:48');
INSERT INTO `admin_sys_logininfor` VALUES (502, 'ry', '115.60.60.164', 'XX XX', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-07-02 19:56:02');
INSERT INTO `admin_sys_logininfor` VALUES (503, 'ry', '35.220.164.209', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-02 20:11:12');
INSERT INTO `admin_sys_logininfor` VALUES (504, 'ry', '140.243.5.73', 'XX XX', 'Safari', 'Mac OS X', '1', '密码输入错误1次', '2020-07-02 20:22:17');
INSERT INTO `admin_sys_logininfor` VALUES (505, 'admin', '113.111.106.232', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-02 20:48:25');
INSERT INTO `admin_sys_logininfor` VALUES (506, 'ry', '116.21.237.185', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-02 22:17:58');
INSERT INTO `admin_sys_logininfor` VALUES (507, 'admin', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '0', '退出成功', '2020-07-03 09:08:57');
INSERT INTO `admin_sys_logininfor` VALUES (508, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:20');
INSERT INTO `admin_sys_logininfor` VALUES (509, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:21');
INSERT INTO `admin_sys_logininfor` VALUES (510, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:22');
INSERT INTO `admin_sys_logininfor` VALUES (511, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:23');
INSERT INTO `admin_sys_logininfor` VALUES (512, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:24');
INSERT INTO `admin_sys_logininfor` VALUES (513, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:25');
INSERT INTO `admin_sys_logininfor` VALUES (514, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:26');
INSERT INTO `admin_sys_logininfor` VALUES (515, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:27');
INSERT INTO `admin_sys_logininfor` VALUES (516, 'ry', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-03 09:09:28');
INSERT INTO `admin_sys_logininfor` VALUES (517, 'admin', '192.168.1.81', '内网IP', 'Firefox 7', 'Windows 7', '0', '登录成功', '2020-07-03 09:09:49');
INSERT INTO `admin_sys_logininfor` VALUES (518, 'ry', '121.32.199.239', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-07-03 10:36:14');
INSERT INTO `admin_sys_logininfor` VALUES (519, 'ry', '1.198.16.250', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-03 11:21:29');
INSERT INTO `admin_sys_logininfor` VALUES (520, 'ry', '117.35.118.142', 'XX XX', 'Safari', 'Mac OS X', '1', '密码输入错误1次', '2020-07-03 12:28:24');
INSERT INTO `admin_sys_logininfor` VALUES (521, 'admin', '115.60.162.154', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-03 14:03:38');
INSERT INTO `admin_sys_logininfor` VALUES (522, 'ry', '124.205.240.138', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-03 14:16:15');
INSERT INTO `admin_sys_logininfor` VALUES (523, 'ry', '222.209.30.126', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-03 15:11:45');
INSERT INTO `admin_sys_logininfor` VALUES (524, 'ry', '27.42.236.6', 'XX XX', 'Firefox 7', 'Windows 10', '1', '验证码错误', '2020-07-03 15:27:06');
INSERT INTO `admin_sys_logininfor` VALUES (525, 'ry', '113.200.194.107', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-03 16:03:17');
INSERT INTO `admin_sys_logininfor` VALUES (526, 'ry', '114.222.35.204', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-03 16:46:21');
INSERT INTO `admin_sys_logininfor` VALUES (527, 'admin', '121.69.59.82', 'XX XX', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-07-03 17:50:25');
INSERT INTO `admin_sys_logininfor` VALUES (528, 'shangjai', '121.69.59.82', 'XX XX', 'Chrome 8', 'Windows 7', '1', '用户不存在/密码错误', '2020-07-03 17:51:16');
INSERT INTO `admin_sys_logininfor` VALUES (529, 'ry', '113.132.8.77', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-07-03 18:59:08');
INSERT INTO `admin_sys_logininfor` VALUES (530, 'ry', '39.153.150.25', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-03 19:07:19');
INSERT INTO `admin_sys_logininfor` VALUES (531, 'admin', '222.210.61.119', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-03 19:42:28');
INSERT INTO `admin_sys_logininfor` VALUES (532, 'ry', '113.111.112.230', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误3次', '2020-07-03 20:18:08');
INSERT INTO `admin_sys_logininfor` VALUES (533, 'admin', '113.111.112.230', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误2次', '2020-07-03 20:18:48');
INSERT INTO `admin_sys_logininfor` VALUES (534, 'ry', '113.111.112.230', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误5次', '2020-07-03 20:20:39');
INSERT INTO `admin_sys_logininfor` VALUES (535, 'ry', '27.200.196.177', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-03 20:34:10');
INSERT INTO `admin_sys_logininfor` VALUES (536, 'ry', '27.200.196.177', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-03 20:34:15');
INSERT INTO `admin_sys_logininfor` VALUES (537, 'ry', '110.188.93.230', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-03 20:52:37');
INSERT INTO `admin_sys_logininfor` VALUES (538, 'admin', '110.188.93.230', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-03 20:52:51');
INSERT INTO `admin_sys_logininfor` VALUES (539, 'admin', '110.188.93.230', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-03 20:52:55');
INSERT INTO `admin_sys_logininfor` VALUES (540, 'ry', '223.246.42.244', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-03 20:58:39');
INSERT INTO `admin_sys_logininfor` VALUES (541, 'ry', '223.72.49.95', 'XX XX', 'Firefox', 'Mac OS X', '1', '密码输入错误1次', '2020-07-03 21:56:02');
INSERT INTO `admin_sys_logininfor` VALUES (542, 'ry', '124.90.165.103', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误2次', '2020-07-03 21:59:04');
INSERT INTO `admin_sys_logininfor` VALUES (543, 'ry', '113.116.41.159', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-03 22:25:31');
INSERT INTO `admin_sys_logininfor` VALUES (544, 'ry', '183.195.11.141', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-07-03 22:47:33');
INSERT INTO `admin_sys_logininfor` VALUES (545, 'ry', '122.234.229.54', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-04 02:05:16');
INSERT INTO `admin_sys_logininfor` VALUES (546, 'ry', '1.202.126.88', 'XX XX', 'Safari', 'Mac OS X', '1', '密码输入错误2次', '2020-07-04 11:06:33');
INSERT INTO `admin_sys_logininfor` VALUES (547, 'ry', '61.140.161.57', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误4次', '2020-07-04 11:07:27');
INSERT INTO `admin_sys_logininfor` VALUES (548, 'ry', '123.235.113.222', 'XX XX', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误2次', '2020-07-04 11:47:47');
INSERT INTO `admin_sys_logininfor` VALUES (549, 'ry', '221.218.208.59', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-07-04 15:42:56');
INSERT INTO `admin_sys_logininfor` VALUES (550, 'ry', '211.161.249.159', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误2次', '2020-07-04 15:49:19');
INSERT INTO `admin_sys_logininfor` VALUES (551, 'ry', '183.48.30.201', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-04 16:32:22');
INSERT INTO `admin_sys_logininfor` VALUES (552, 'ry', '58.251.16.230', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-04 18:09:50');
INSERT INTO `admin_sys_logininfor` VALUES (553, 'ry', '116.199.51.82', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误2次', '2020-07-04 18:10:58');
INSERT INTO `admin_sys_logininfor` VALUES (554, 'ry', '183.216.24.5', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-07-04 21:10:15');
INSERT INTO `admin_sys_logininfor` VALUES (555, 'ry', '183.216.24.5', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-07-04 21:10:47');
INSERT INTO `admin_sys_logininfor` VALUES (729, 'ry', '192.168.103.82', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-14 11:45:44');
INSERT INTO `admin_sys_logininfor` VALUES (730, 'admin', '192.168.103.82', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-14 11:46:01');
INSERT INTO `admin_sys_logininfor` VALUES (731, 'admin', '192.168.103.49', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-14 11:51:31');
INSERT INTO `admin_sys_logininfor` VALUES (732, 'admin', '106.117.76.13', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-14 11:51:48');
INSERT INTO `admin_sys_logininfor` VALUES (733, 'ry', '183.14.18.141', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-14 14:03:34');
INSERT INTO `admin_sys_logininfor` VALUES (734, 'ry', '222.211.175.227', 'XX XX', 'Firefox 7', 'Mac OS X', '1', '密码输入错误1次', '2020-07-14 19:17:20');
INSERT INTO `admin_sys_logininfor` VALUES (735, 'admin', '222.211.175.227', 'XX XX', 'Firefox 7', 'Mac OS X', '0', '登录成功', '2020-07-14 19:17:35');
INSERT INTO `admin_sys_logininfor` VALUES (736, 'admin', '113.233.194.92', 'XX XX', 'Firefox 7', 'Windows 10', '0', '登录成功', '2020-07-14 20:33:26');
INSERT INTO `admin_sys_logininfor` VALUES (737, 'admin', '125.86.109.164', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-14 21:30:16');
INSERT INTO `admin_sys_logininfor` VALUES (738, 'admin', '27.224.89.117', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-14 21:45:14');
INSERT INTO `admin_sys_logininfor` VALUES (739, 'admin', '27.224.89.117', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-14 21:45:30');
INSERT INTO `admin_sys_logininfor` VALUES (740, 'ry', '27.224.89.117', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-14 21:45:55');
INSERT INTO `admin_sys_logininfor` VALUES (741, 'ry', '113.110.186.109', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-15 09:55:03');
INSERT INTO `admin_sys_logininfor` VALUES (742, 'ry', '121.204.19.199', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-15 20:32:10');
INSERT INTO `admin_sys_logininfor` VALUES (743, 'ry', '121.204.19.199', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-15 20:32:15');
INSERT INTO `admin_sys_logininfor` VALUES (744, 'admin', '220.172.55.145', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-15 20:48:09');
INSERT INTO `admin_sys_logininfor` VALUES (745, 'ry', '106.121.6.79', 'XX XX', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误3次', '2020-07-15 21:06:43');
INSERT INTO `admin_sys_logininfor` VALUES (746, 'ry', '121.98.55.12', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-07-15 21:36:23');
INSERT INTO `admin_sys_logininfor` VALUES (747, 'ry', '221.217.94.106', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-16 15:20:20');
INSERT INTO `admin_sys_logininfor` VALUES (748, 'admin', '218.17.112.191', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-16 15:28:36');
INSERT INTO `admin_sys_logininfor` VALUES (749, 'ry', '118.198.183.236', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-07-16 19:22:27');
INSERT INTO `admin_sys_logininfor` VALUES (750, 'ry', '1.85.233.64', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-16 20:04:54');
INSERT INTO `admin_sys_logininfor` VALUES (751, 'ry', '113.46.248.242', 'XX XX', 'Chrome', 'Windows 8', '1', '密码输入错误1次', '2020-07-16 21:09:45');
INSERT INTO `admin_sys_logininfor` VALUES (752, 'ry', '117.149.10.42', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-17 15:35:19');
INSERT INTO `admin_sys_logininfor` VALUES (753, 'admin', '202.101.166.95', 'XX XX', 'Firefox', 'Windows 10', '1', '密码输入错误3次', '2020-07-17 16:52:49');
INSERT INTO `admin_sys_logininfor` VALUES (754, 'ry', '113.66.55.201', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-17 20:55:44');
INSERT INTO `admin_sys_logininfor` VALUES (755, 'ry', '113.66.55.201', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-17 20:55:47');
INSERT INTO `admin_sys_logininfor` VALUES (756, 'ry', '112.94.53.84', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-07-17 21:52:29');
INSERT INTO `admin_sys_logininfor` VALUES (757, 'ry', '113.65.249.94', 'XX XX', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误1次', '2020-07-18 03:01:34');
INSERT INTO `admin_sys_logininfor` VALUES (758, 'ry', '43.243.12.106', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-18 16:49:48');
INSERT INTO `admin_sys_logininfor` VALUES (759, 'ry', '43.243.12.106', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误2次', '2020-07-18 16:49:56');
INSERT INTO `admin_sys_logininfor` VALUES (760, 'admin', '180.191.101.44', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-07-18 17:06:39');
INSERT INTO `admin_sys_logininfor` VALUES (761, 'ry', '117.143.153.230', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-18 20:23:59');
INSERT INTO `admin_sys_logininfor` VALUES (762, 'admin', '218.72.42.29', 'XX XX', 'Firefox', 'Windows 10', '1', '验证码错误', '2020-07-18 20:50:10');
INSERT INTO `admin_sys_logininfor` VALUES (763, 'ry', '10.1.1.163', '内网IP', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-18 22:32:02');
INSERT INTO `admin_sys_logininfor` VALUES (764, 'ry', '10.1.1.163', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误4次', '2020-07-18 22:32:08');
INSERT INTO `admin_sys_logininfor` VALUES (765, 'ry', '111.0.237.146', 'XX XX', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-07-19 11:40:22');
INSERT INTO `admin_sys_logininfor` VALUES (766, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-07-19 12:51:34');
INSERT INTO `admin_sys_logininfor` VALUES (767, 'admin', '36.98.169.169', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-07-19 20:04:49');
INSERT INTO `admin_sys_logininfor` VALUES (768, 'ry', '171.43.248.106', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-07-19 20:21:13');
INSERT INTO `admin_sys_logininfor` VALUES (769, 'ry', '222.172.156.58', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-07-19 20:50:09');
INSERT INTO `admin_sys_logininfor` VALUES (770, 'admin', '218.18.166.84', 'XX XX', 'Chrome 44', 'Windows 8.1', '1', '密码输入错误1次', '2020-07-19 21:39:48');
INSERT INTO `admin_sys_logininfor` VALUES (771, 'ry', '14.30.1.57', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-19 22:11:15');
INSERT INTO `admin_sys_logininfor` VALUES (772, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-20 15:48:18');
INSERT INTO `admin_sys_logininfor` VALUES (773, 'ry', '111.29.122.105', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误3次', '2020-07-20 20:05:51');
INSERT INTO `admin_sys_logininfor` VALUES (774, 'ry', '123.168.249.177', 'XX XX', 'Chrome Mobile', 'Android 1.x', '1', '密码输入错误2次', '2020-07-20 20:24:02');
INSERT INTO `admin_sys_logininfor` VALUES (775, 'ry', '122.96.45.138', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误3次', '2020-07-20 20:27:09');
INSERT INTO `admin_sys_logininfor` VALUES (776, 'admin', '122.96.45.138', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误3次', '2020-07-20 20:29:28');
INSERT INTO `admin_sys_logininfor` VALUES (777, 'admin', '111.29.122.105', 'XX XX', 'Chrome', 'Windows 7', '1', '密码输入错误1次', '2020-07-20 21:23:46');
INSERT INTO `admin_sys_logininfor` VALUES (778, 'admin', '115.206.146.247', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-20 22:18:29');
INSERT INTO `admin_sys_logininfor` VALUES (779, 'ry', '118.205.213.108', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-21 17:47:07');
INSERT INTO `admin_sys_logininfor` VALUES (780, 'admin', '120.239.173.180', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-07-21 18:08:21');
INSERT INTO `admin_sys_logininfor` VALUES (781, 'ry', '171.223.89.86', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次', '2020-07-21 20:24:11');
INSERT INTO `admin_sys_logininfor` VALUES (782, 'ry', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-21 20:32:47');
INSERT INTO `admin_sys_logininfor` VALUES (783, 'admin', '127.0.0.1', '内网IP', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-21 20:33:01');
INSERT INTO `admin_sys_logininfor` VALUES (784, 'ry', '39.171.16.127', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-21 20:40:09');
INSERT INTO `admin_sys_logininfor` VALUES (785, 'admin', '223.20.0.113', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-21 21:03:59');
INSERT INTO `admin_sys_logininfor` VALUES (786, 'admin', '223.20.0.113', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-21 21:04:01');
INSERT INTO `admin_sys_logininfor` VALUES (787, 'ry', '113.87.27.59', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-21 21:54:16');
INSERT INTO `admin_sys_logininfor` VALUES (788, 'ry', '220.168.55.250', 'XX XX', 'Firefox 7', 'Windows 7', '1', '验证码错误', '2020-07-22 11:03:22');
INSERT INTO `admin_sys_logininfor` VALUES (789, 'ry', '218.104.108.250', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-22 16:06:35');
INSERT INTO `admin_sys_logininfor` VALUES (790, 'ry', '112.19.161.45', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-07-22 18:23:04');
INSERT INTO `admin_sys_logininfor` VALUES (791, 'ry', '120.230.121.29', 'XX XX', 'Firefox 7', 'Mac OS X', '1', '密码输入错误1次', '2020-07-22 21:03:51');
INSERT INTO `admin_sys_logininfor` VALUES (792, 'admin', '183.250.29.129', 'XX XX', 'Firefox 7', 'Windows 7', '1', '密码输入错误1次', '2020-07-22 21:10:52');
INSERT INTO `admin_sys_logininfor` VALUES (793, 'admin', '120.228.64.77', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-22 21:38:36');
INSERT INTO `admin_sys_logininfor` VALUES (794, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-23 10:09:05');
INSERT INTO `admin_sys_logininfor` VALUES (795, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-23 10:09:14');
INSERT INTO `admin_sys_logininfor` VALUES (796, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-23 10:09:18');
INSERT INTO `admin_sys_logininfor` VALUES (797, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-23 10:09:27');
INSERT INTO `admin_sys_logininfor` VALUES (798, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-23 14:06:22');
INSERT INTO `admin_sys_logininfor` VALUES (799, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-23 14:07:43');
INSERT INTO `admin_sys_logininfor` VALUES (800, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-23 14:07:47');
INSERT INTO `admin_sys_logininfor` VALUES (801, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-23 16:00:11');
INSERT INTO `admin_sys_logininfor` VALUES (802, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-07-23 17:07:10');
INSERT INTO `admin_sys_logininfor` VALUES (803, 'ry', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '1', '验证码错误', '2020-07-23 17:07:11');
INSERT INTO `admin_sys_logininfor` VALUES (804, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-07-23 17:07:25');
INSERT INTO `admin_sys_logininfor` VALUES (805, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Mac OS X', '0', '登录成功', '2020-07-23 17:07:32');
INSERT INTO `admin_sys_logininfor` VALUES (806, 'ry', '114.253.245.44', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误5次，帐户锁定10分钟', '2020-07-23 17:41:34');
INSERT INTO `admin_sys_logininfor` VALUES (807, 'ry', '114.253.245.44', 'XX XX', 'Chrome 8', 'Windows 10', '1', '验证码错误', '2020-07-23 17:42:20');
INSERT INTO `admin_sys_logininfor` VALUES (808, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-23 18:37:02');
INSERT INTO `admin_sys_logininfor` VALUES (809, 'ry', '60.255.139.27', 'XX XX', 'Chrome', 'Windows 7', '1', '验证码错误', '2020-07-23 19:56:41');
INSERT INTO `admin_sys_logininfor` VALUES (810, 'ry', '140.206.195.252', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误4次', '2020-07-23 20:11:17');
INSERT INTO `admin_sys_logininfor` VALUES (811, 'ry', '120.36.184.185', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-23 20:51:36');
INSERT INTO `admin_sys_logininfor` VALUES (812, 'ry', '61.170.210.209', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-23 22:00:31');
INSERT INTO `admin_sys_logininfor` VALUES (813, 'ry', '183.39.53.10', 'XX XX', 'Chrome 8', 'Linux', '1', '密码输入错误2次', '2020-07-23 22:08:57');
INSERT INTO `admin_sys_logininfor` VALUES (814, 'admin', '127.0.0.1', '内网IP', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-24 09:07:26');
INSERT INTO `admin_sys_logininfor` VALUES (815, 'admin', '110.179.80.37', 'XX XX', 'Chrome 8', 'Windows 7', '1', '密码输入错误1次', '2020-07-24 20:51:13');
INSERT INTO `admin_sys_logininfor` VALUES (816, 'shangjia', '110.179.80.37', 'XX XX', 'Chrome 8', 'Windows 7', '1', '用户不存在/密码错误', '2020-07-24 20:58:12');
INSERT INTO `admin_sys_logininfor` VALUES (817, 'admin', '171.210.225.12', 'XX XX', 'Chrome 8', 'Windows 10', '0', '登录成功', '2020-07-24 21:15:44');
INSERT INTO `admin_sys_logininfor` VALUES (818, 'ry', '42.239.63.8', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-24 21:27:19');
INSERT INTO `admin_sys_logininfor` VALUES (819, 'ry', '114.253.76.197', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误2次', '2020-07-24 21:34:27');
INSERT INTO `admin_sys_logininfor` VALUES (820, 'admin', '175.43.99.166', 'XX XX', 'Chrome 48', 'Windows 10', '0', '登录成功', '2020-07-25 17:02:30');
INSERT INTO `admin_sys_logininfor` VALUES (821, 'ry', '211.161.241.49', 'XX XX', 'Chrome', 'Windows 10', '1', '验证码错误', '2020-07-26 11:08:08');
INSERT INTO `admin_sys_logininfor` VALUES (822, 'admin', '119.129.230.244', 'XX XX', 'Chrome 8', 'Windows 7', '1', '验证码错误', '2020-07-26 17:43:48');
INSERT INTO `admin_sys_logininfor` VALUES (823, 'ry', '113.246.108.96', 'XX XX', 'Chrome', 'Windows 10', '1', '密码输入错误1次', '2020-07-26 18:37:20');
INSERT INTO `admin_sys_logininfor` VALUES (824, 'ry', '89.208.248.134', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-07-26 20:13:21');
INSERT INTO `admin_sys_logininfor` VALUES (825, 'admin', '89.208.248.134', 'XX XX', 'Chrome 8', 'Mac OS X', '1', '密码输入错误1次', '2020-07-26 20:14:59');
INSERT INTO `admin_sys_logininfor` VALUES (826, 'ry', '183.251.21.60', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-07-26 20:16:20');
INSERT INTO `admin_sys_logininfor` VALUES (827, 'ry', '219.143.174.62', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-26 21:52:55');
INSERT INTO `admin_sys_logininfor` VALUES (828, 'admin', '223.72.49.35', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误1次', '2020-07-26 21:58:53');
INSERT INTO `admin_sys_logininfor` VALUES (829, 'admin', '223.72.49.35', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误2次', '2020-07-26 21:59:26');
INSERT INTO `admin_sys_logininfor` VALUES (830, 'admin', '223.72.49.35', 'XX XX', 'Chrome 8', 'Windows 10', '1', '密码输入错误3次', '2020-07-26 21:59:34');

-- ----------------------------
-- Table structure for admin_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_menu`;
CREATE TABLE `admin_sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '菜单名称',
  `parent_id` bigint(20) NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int(11) NULL DEFAULT 0 COMMENT '显示顺序',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '#' COMMENT '请求地址',
  `target` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '打开方式（menuItem页签 menuBlank新窗口）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2221 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_menu
-- ----------------------------
INSERT INTO `admin_sys_menu` VALUES (1, '系统管理', 0, 1, '#', 'menuItem', 'M', '0', '', 'fa fa-gear', 'admin', '2018-03-16 11:33:00', 'ry', '2020-03-17 11:41:33', '系统管理目录');
INSERT INTO `admin_sys_menu` VALUES (2, '系统监控', 0, 2, '#', 'menuItem', 'M', '0', '', 'fa fa-circle', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-20 22:53:58', '系统监控目录');
INSERT INTO `admin_sys_menu` VALUES (3, '系统工具', 0, 3, '#', '', 'M', '0', '', 'fa fa-bars', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统工具目录');
INSERT INTO `admin_sys_menu` VALUES (100, '用户管理', 1, 1, '/system/user', '', 'C', '0', 'system:user:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户管理菜单');
INSERT INTO `admin_sys_menu` VALUES (101, '角色管理', 1, 2, '/system/role', 'menuItem', 'C', '0', 'system:role:view', '#', 'admin', '2018-03-16 11:33:00', 'admin', '2020-02-19 10:16:29', '角色管理菜单');
INSERT INTO `admin_sys_menu` VALUES (102, '菜单管理', 1, 3, '/system/menu', '', 'C', '0', 'system:menu:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单管理菜单');
INSERT INTO `admin_sys_menu` VALUES (103, '部门管理', 1, 4, '/system/dept', '', 'C', '0', 'system:dept:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '部门管理菜单');
INSERT INTO `admin_sys_menu` VALUES (104, '岗位管理', 1, 5, '/system/post', '', 'C', '0', 'system:post:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '岗位管理菜单');
INSERT INTO `admin_sys_menu` VALUES (105, '字典管理', 1, 6, '/system/dict', '', 'C', '0', 'system:dict:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '字典管理菜单');
INSERT INTO `admin_sys_menu` VALUES (106, '参数设置', 1, 7, '/system/config', '', 'C', '0', 'system:config:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '参数设置菜单');
INSERT INTO `admin_sys_menu` VALUES (107, '通知公告', 1, 8, '/system/notice', '', 'C', '0', 'system:notice:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知公告菜单');
INSERT INTO `admin_sys_menu` VALUES (108, '日志管理', 1, 9, '#', '', 'M', '0', '', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '日志管理菜单');
INSERT INTO `admin_sys_menu` VALUES (109, '在线用户', 2, 1, '/monitor/online', '', 'C', '0', 'monitor:online:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '在线用户菜单');
INSERT INTO `admin_sys_menu` VALUES (110, '定时任务', 2, 2, '/monitor/job', '', 'C', '0', 'monitor:job:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '定时任务菜单');
INSERT INTO `admin_sys_menu` VALUES (111, '数据监控', 2, 3, '/monitor/data', '', 'C', '0', 'monitor:data:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '数据监控菜单');
INSERT INTO `admin_sys_menu` VALUES (112, '服务监控', 2, 3, '/monitor/server', '', 'C', '0', 'monitor:server:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '服务监控菜单');
INSERT INTO `admin_sys_menu` VALUES (113, '表单构建', 3, 1, '/tool/build', '', 'C', '0', 'tool:build:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '表单构建菜单');
INSERT INTO `admin_sys_menu` VALUES (114, '代码生成', 3, 2, '/tool/gen', '', 'C', '0', 'tool:gen:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '代码生成菜单');
INSERT INTO `admin_sys_menu` VALUES (115, '系统接口', 3, 3, '/tool/swagger', '', 'C', '0', 'tool:swagger:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统接口菜单');
INSERT INTO `admin_sys_menu` VALUES (500, '操作日志', 108, 1, '/monitor/operlog', '', 'C', '0', 'monitor:operlog:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作日志菜单');
INSERT INTO `admin_sys_menu` VALUES (501, '登录日志', 108, 2, '/monitor/logininfor', '', 'C', '0', 'monitor:logininfor:view', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录日志菜单');
INSERT INTO `admin_sys_menu` VALUES (1000, '用户查询', 100, 1, '#', '', 'F', '0', 'system:user:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1001, '用户新增', 100, 2, '#', '', 'F', '0', 'system:user:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1002, '用户修改', 100, 3, '#', '', 'F', '0', 'system:user:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1003, '用户删除', 100, 4, '#', '', 'F', '0', 'system:user:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1004, '用户导出', 100, 5, '#', '', 'F', '0', 'system:user:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1005, '用户导入', 100, 6, '#', '', 'F', '0', 'system:user:import', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1006, '重置密码', 100, 7, '#', '', 'F', '0', 'system:user:resetPwd', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1007, '角色查询', 101, 1, '#', '', 'F', '0', 'system:role:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1008, '角色新增', 101, 2, '#', '', 'F', '0', 'system:role:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1009, '角色修改', 101, 3, '#', '', 'F', '0', 'system:role:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1010, '角色删除', 101, 4, '#', '', 'F', '0', 'system:role:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1011, '角色导出', 101, 5, '#', '', 'F', '0', 'system:role:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1012, '菜单查询', 102, 1, '#', '', 'F', '0', 'system:menu:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1013, '菜单新增', 102, 2, '#', '', 'F', '0', 'system:menu:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1014, '菜单修改', 102, 3, '#', '', 'F', '0', 'system:menu:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1015, '菜单删除', 102, 4, '#', '', 'F', '0', 'system:menu:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1016, '部门查询', 103, 1, '#', '', 'F', '0', 'system:dept:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1017, '部门新增', 103, 2, '#', '', 'F', '0', 'system:dept:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1018, '部门修改', 103, 3, '#', '', 'F', '0', 'system:dept:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1019, '部门删除', 103, 4, '#', '', 'F', '0', 'system:dept:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1020, '岗位查询', 104, 1, '#', '', 'F', '0', 'system:post:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1021, '岗位新增', 104, 2, '#', '', 'F', '0', 'system:post:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1022, '岗位修改', 104, 3, '#', '', 'F', '0', 'system:post:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1023, '岗位删除', 104, 4, '#', '', 'F', '0', 'system:post:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1024, '岗位导出', 104, 5, '#', '', 'F', '0', 'system:post:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1025, '字典查询', 105, 1, '#', '', 'F', '0', 'system:dict:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1026, '字典新增', 105, 2, '#', '', 'F', '0', 'system:dict:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1027, '字典修改', 105, 3, '#', '', 'F', '0', 'system:dict:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1028, '字典删除', 105, 4, '#', '', 'F', '0', 'system:dict:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1029, '字典导出', 105, 5, '#', '', 'F', '0', 'system:dict:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1030, '参数查询', 106, 1, '#', '', 'F', '0', 'system:config:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1031, '参数新增', 106, 2, '#', '', 'F', '0', 'system:config:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1032, '参数修改', 106, 3, '#', '', 'F', '0', 'system:config:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1033, '参数删除', 106, 4, '#', '', 'F', '0', 'system:config:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1034, '参数导出', 106, 5, '#', '', 'F', '0', 'system:config:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1035, '公告查询', 107, 1, '#', '', 'F', '0', 'system:notice:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1036, '公告新增', 107, 2, '#', '', 'F', '0', 'system:notice:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1037, '公告修改', 107, 3, '#', '', 'F', '0', 'system:notice:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1038, '公告删除', 107, 4, '#', '', 'F', '0', 'system:notice:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1039, '操作查询', 500, 1, '#', '', 'F', '0', 'monitor:operlog:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1040, '操作删除', 500, 2, '#', '', 'F', '0', 'monitor:operlog:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1041, '详细信息', 500, 3, '#', '', 'F', '0', 'monitor:operlog:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1042, '日志导出', 500, 4, '#', '', 'F', '0', 'monitor:operlog:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1043, '登录查询', 501, 1, '#', '', 'F', '0', 'monitor:logininfor:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1044, '登录删除', 501, 2, '#', '', 'F', '0', 'monitor:logininfor:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1045, '日志导出', 501, 3, '#', '', 'F', '0', 'monitor:logininfor:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1046, '在线查询', 109, 1, '#', '', 'F', '0', 'monitor:online:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1047, '批量强退', 109, 2, '#', '', 'F', '0', 'monitor:online:batchForceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1048, '单条强退', 109, 3, '#', '', 'F', '0', 'monitor:online:forceLogout', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1049, '任务查询', 110, 1, '#', '', 'F', '0', 'monitor:job:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1050, '任务新增', 110, 2, '#', '', 'F', '0', 'monitor:job:add', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1051, '任务修改', 110, 3, '#', '', 'F', '0', 'monitor:job:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1052, '任务删除', 110, 4, '#', '', 'F', '0', 'monitor:job:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1053, '状态修改', 110, 5, '#', '', 'F', '0', 'monitor:job:changeStatus', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1054, '任务详细', 110, 6, '#', '', 'F', '0', 'monitor:job:detail', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1055, '任务导出', 110, 7, '#', '', 'F', '0', 'monitor:job:export', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1056, '生成查询', 114, 1, '#', '', 'F', '0', 'tool:gen:list', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1057, '生成修改', 114, 2, '#', '', 'F', '0', 'tool:gen:edit', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1058, '生成删除', 114, 3, '#', '', 'F', '0', 'tool:gen:remove', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1059, '预览代码', 114, 4, '#', '', 'F', '0', 'tool:gen:preview', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (1060, '生成代码', 114, 5, '#', '', 'F', '0', 'tool:gen:code', '#', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_menu` VALUES (2000, '会员管理', 2044, 1, '/mall/member', 'menuItem', 'C', '0', 'mall:member:view', '#', 'admin', '2018-03-01 00:00:00', 'admin', '2020-06-13 09:22:48', '会员菜单');
INSERT INTO `admin_sys_menu` VALUES (2001, '会员查询', 2000, 1, '#', '', 'F', '0', 'mall:member:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2002, '会员新增', 2000, 2, '#', '', 'F', '0', 'mall:member:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2003, '会员修改', 2000, 3, '#', '', 'F', '0', 'mall:member:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2004, '会员删除', 2000, 4, '#', '', 'F', '0', 'mall:member:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2005, '订单管理', 2021, 3, '/mall/order', '', 'C', '0', 'mall:order:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '订单菜单');
INSERT INTO `admin_sys_menu` VALUES (2006, '订单查询', 2005, 1, '#', '', 'F', '0', 'mall:order:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2007, '订单新增', 2005, 2, '#', '', 'F', '0', 'mall:order:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2008, '订单修改', 2005, 3, '#', '', 'F', '0', 'mall:order:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2009, '订单删除', 2005, 4, '#', '', 'F', '0', 'mall:order:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2010, '商品管理', 2021, 2, '/mall/product', '', 'C', '0', 'mall:product:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '商品信息菜单');
INSERT INTO `admin_sys_menu` VALUES (2011, '商品信息查询', 2010, 1, '#', '', 'F', '0', 'mall:product:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2012, '商品信息新增', 2010, 2, '#', '', 'F', '0', 'mall:product:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2013, '商品信息修改', 2010, 3, '#', 'menuItem', 'F', '0', '', '#', 'admin', '2018-03-01 00:00:00', 'admin123', '2020-06-02 17:08:46', '');
INSERT INTO `admin_sys_menu` VALUES (2014, '商品信息删除', 2010, 4, '#', '', 'F', '0', 'mall:product:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2015, '商户数据统计', 2021, 5, '/mall/statics', '', 'C', '0', 'mall:statics:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '商户数据统计菜单');
INSERT INTO `admin_sys_menu` VALUES (2016, '专题管理', 2021, 4, '/mall/subject', '', 'C', '0', 'mall:subject:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '专题菜单');
INSERT INTO `admin_sys_menu` VALUES (2017, '专题查询', 2016, 1, '#', '', 'F', '0', 'mall:subject:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2018, '专题新增', 2016, 2, '#', '', 'F', '0', 'mall:subject:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2019, '专题修改', 2016, 3, '#', '', 'F', '0', 'mall:subject:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2020, '专题删除', 2016, 4, '#', '', 'F', '0', 'mall:subject:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2021, '商户管理', 0, 0, '#', 'menuItem', 'M', '0', '', 'fa fa-blind', 'admin', '2019-09-17 15:43:56', 'admin', '2020-07-27 14:24:02', '');
INSERT INTO `admin_sys_menu` VALUES (2022, '店铺', 2021, 1, '/mall/store', '', 'C', '0', 'mall:store:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '店铺菜单');
INSERT INTO `admin_sys_menu` VALUES (2023, '店铺查询', 2022, 1, '#', 'menuItem', 'F', '0', 'mall:store:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2020-05-26 19:46:18', '');
INSERT INTO `admin_sys_menu` VALUES (2024, '店铺新增', 2022, 2, '#', '', 'F', '0', 'mall:store:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2025, '店铺修改', 2022, 3, '#', '', 'F', '0', 'mall:store:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2026, '店铺删除', 2022, 4, '#', '', 'F', '0', 'mall:store:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2027, '支付方式', 2021, 1, '/mall/payments', 'menuItem', 'C', '0', 'mall:payments:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2020-05-22 11:14:10', '支付方式菜单');
INSERT INTO `admin_sys_menu` VALUES (2028, '支付方式查询', 2027, 1, '#', '', 'F', '0', 'mall:payments:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2029, '支付方式新增', 2027, 2, '#', '', 'F', '0', 'mall:payments:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2030, '支付方式修改', 2027, 3, '#', '', 'F', '0', 'mall:payments:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2031, '支付方式删除', 2027, 4, '#', '', 'F', '0', 'mall:payments:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2032, '小程序配置', 2021, 1, '/mall/set', '', 'C', '0', 'mall:set:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '小程序配置菜单');
INSERT INTO `admin_sys_menu` VALUES (2033, '小程序配置查询', 2032, 1, '#', '', 'F', '0', 'mall:set:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2035, '小程序配置修改', 2032, 3, '#', '', 'F', '0', 'mall:set:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2037, '轮播广告管理', 1, 1, '/mall/advertise', '', 'C', '0', 'mall:advertise:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '轮播广告管理');
INSERT INTO `admin_sys_menu` VALUES (2038, '首页轮播广告查询', 2037, 1, '#', '', 'F', '0', 'mall:advertise:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2039, '首页轮播广告新增', 2037, 2, '#', '', 'F', '0', 'mall:advertise:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2040, '首页轮播广告修改', 2037, 3, '#', '', 'F', '0', 'mall:advertise:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2041, '首页轮播广告删除', 2037, 4, '#', '', 'F', '0', 'mall:advertise:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2042, '商品管理', 0, 7, '#', 'menuItem', 'M', '0', '', 'fa fa-child', 'admin', '2020-03-03 11:56:14', 'ry', '2020-05-28 17:09:48', '');
INSERT INTO `admin_sys_menu` VALUES (2043, '订单管理', 0, 8, '#', 'menuItem', 'M', '0', NULL, 'fa fa-desktop', 'admin', '2020-03-03 11:56:40', '', NULL, '');
INSERT INTO `admin_sys_menu` VALUES (2044, '会员管理', 0, 9, '#', 'menuItem', 'M', '0', NULL, 'fa fa-address-card-o', 'admin', '2020-03-03 11:57:00', '', NULL, '');
INSERT INTO `admin_sys_menu` VALUES (2125, '用户标签', 2044, 1, '/system/tag', '', 'C', '0', 'system:tag:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '用户标签菜单');
INSERT INTO `admin_sys_menu` VALUES (2126, '用户标签查询', 2125, 1, '#', '', 'F', '0', 'system:tag:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2127, '用户标签新增', 2125, 2, '#', '', 'F', '0', 'system:tag:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2128, '用户标签修改', 2125, 3, '#', '', 'F', '0', 'system:tag:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2129, '用户标签删除', 2125, 4, '#', '', 'F', '0', 'system:tag:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2130, '会员收货地址', 2044, 1, '/system/address', '', 'C', '0', 'system:address:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '会员收货地址菜单');
INSERT INTO `admin_sys_menu` VALUES (2131, '会员收货地址查询', 2130, 1, '#', '', 'F', '0', 'system:address:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2132, '会员收货地址新增', 2130, 2, '#', '', 'F', '0', 'system:address:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2133, '会员收货地址修改', 2130, 3, '#', '', 'F', '0', 'system:address:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2134, '会员收货地址删除', 2130, 4, '#', '', 'F', '0', 'system:address:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2135, '成长值变化', 2044, 1, '/member/growthChangeHistory', '', 'C', '0', 'member:growthChangeHistory:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '成长值变化历史记录菜单');
INSERT INTO `admin_sys_menu` VALUES (2136, '成长值变化历史记录查询', 2135, 1, '#', '', 'F', '0', 'member:growthChangeHistory:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2137, '成长值变化历史记录新增', 2135, 2, '#', '', 'F', '0', 'member:growthChangeHistory:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2138, '成长值变化历史记录修改', 2135, 3, '#', '', 'F', '0', 'member:growthChangeHistory:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2139, '成长值变化历史记录删除', 2135, 4, '#', '', 'F', '0', 'member:growthChangeHistory:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2140, '积分消费设置', 2044, 1, '/system/setting', '', 'C', '0', 'system:setting:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '积分消费设置菜单');
INSERT INTO `admin_sys_menu` VALUES (2141, '积分消费设置查询', 2140, 1, '#', '', 'F', '0', 'system:setting:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2142, '积分消费设置新增', 2140, 2, '#', '', 'F', '0', 'system:setting:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2143, '积分变化历', 2044, 1, '/member/umsIntegrationChangeHistory', '', 'C', '0', 'member:umsIntegrationChangeHistory:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '积分变化历史记录菜单');
INSERT INTO `admin_sys_menu` VALUES (2144, '积分变化历史记录查询', 2143, 1, '#', '', 'F', '0', 'member:umsIntegrationChangeHistory:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2145, '积分变化历史记录新增', 2143, 2, '#', '', 'F', '0', 'member:umsIntegrationChangeHistory:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2146, '积分变化历史记录修改', 2143, 3, '#', '', 'F', '0', 'member:umsIntegrationChangeHistory:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2147, '积分变化历史记录删除', 2143, 4, '#', '', 'F', '0', 'member:umsIntegrationChangeHistory:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2148, '余额记录', 2044, 1, '/member/umsMemberBlanceLog', '', 'C', '0', 'member:umsMemberBlanceLog:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '余额记录菜单');
INSERT INTO `admin_sys_menu` VALUES (2149, '余额记录查询', 2148, 1, '#', '', 'F', '0', 'member:umsMemberBlanceLog:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2150, '余额记录删除', 2148, 4, '#', '', 'F', '0', 'member:umsMemberBlanceLog:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2151, '会员等级', 2044, 1, '/member/umsMemberLevel', '', 'C', '0', 'member:umsMemberLevel:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '会员等级菜单');
INSERT INTO `admin_sys_menu` VALUES (2152, '会员等级查询', 2151, 1, '#', '', 'F', '0', 'member:umsMemberLevel:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2153, '会员等级新增', 2151, 2, '#', '', 'F', '0', 'member:umsMemberLevel:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2154, '会员等级修改', 2151, 3, '#', '', 'F', '0', 'member:umsMemberLevel:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2155, '会员等级删除', 2151, 4, '#', '', 'F', '0', 'member:umsMemberLevel:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2156, '订单操作记录', 2043, 1, '/order/omsOrderOperateHistory', '', 'C', '0', 'order:omsOrderOperateHistory:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '订单操作历史记录菜单');
INSERT INTO `admin_sys_menu` VALUES (2157, '订单操作历史记录查询', 2156, 1, '#', '', 'F', '0', 'order:omsOrderOperateHistory:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2158, '订单操作历史记录新增', 2156, 2, '#', '', 'F', '0', 'order:omsOrderOperateHistory:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2159, '订单操作历史记录修改', 2156, 3, '#', '', 'F', '0', 'order:omsOrderOperateHistory:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2160, '订单操作历史记录删除', 2156, 4, '#', '', 'F', '0', 'order:omsOrderOperateHistory:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2161, '订单退货申请', 2043, 1, '/order/omsOrderReturnApply', '', 'C', '0', 'order:omsOrderReturnApply:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '订单退货申请菜单');
INSERT INTO `admin_sys_menu` VALUES (2162, '订单退货申请查询', 2161, 1, '#', '', 'F', '0', 'order:omsOrderReturnApply:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2163, '订单退货申请修改', 2161, 3, '#', '', 'F', '0', 'order:omsOrderReturnApply:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2164, '订单退货申请删除', 2161, 4, '#', '', 'F', '0', 'order:omsOrderReturnApply:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2165, '退货原因', 2043, 1, '/order/orderReturnReason', '', 'C', '0', 'order:orderReturnReason:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '退货原因菜单');
INSERT INTO `admin_sys_menu` VALUES (2166, '退货原因查询', 2165, 1, '#', '', 'F', '0', 'order:orderReturnReason:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2167, '退货原因新增', 2165, 2, '#', '', 'F', '0', 'order:orderReturnReason:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2168, '退货原因修改', 2165, 3, '#', '', 'F', '0', 'order:orderReturnReason:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2169, '退货原因删除', 2165, 4, '#', '', 'F', '0', 'order:orderReturnReason:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2170, '配送方式', 2043, 1, '/order/orderShip', '', 'C', '0', 'order:orderShip:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '配送方式菜单');
INSERT INTO `admin_sys_menu` VALUES (2171, '配送方式查询', 2170, 1, '#', '', 'F', '0', 'order:orderShip:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2172, '配送方式新增', 2170, 2, '#', '', 'F', '0', 'order:orderShip:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2173, '配送方式修改', 2170, 3, '#', '', 'F', '0', 'order:orderShip:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2174, '配送方式删除', 2170, 4, '#', '', 'F', '0', 'order:orderShip:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2175, '订单设置', 2043, 1, '/order/orderSetting', '', 'C', '0', 'order:orderSetting:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '订单设置菜单');
INSERT INTO `admin_sys_menu` VALUES (2176, '订单设置查询', 2175, 1, '#', '', 'F', '0', 'order:orderSetting:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2177, '订单设置修改', 2175, 3, '#', '', 'F', '0', 'order:orderSetting:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2178, '品牌', 2042, 1, '/goods/pmsBrand', '', 'C', '0', 'goods:pmsBrand:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '品牌菜单');
INSERT INTO `admin_sys_menu` VALUES (2179, '品牌查询', 2178, 1, '#', '', 'F', '0', 'goods:pmsBrand:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2180, '品牌新增', 2178, 2, '#', '', 'F', '0', 'goods:pmsBrand:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2181, '品牌修改', 2178, 3, '#', '', 'F', '0', 'goods:pmsBrand:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2182, '品牌删除', 2178, 4, '#', '', 'F', '0', 'goods:pmsBrand:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2183, '运费模版', 2042, 1, '/goods/pmsFeightTemplate', '', 'C', '0', 'goods:pmsFeightTemplate:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '运费模版菜单');
INSERT INTO `admin_sys_menu` VALUES (2184, '运费模版查询', 2183, 1, '#', '', 'F', '0', 'goods:pmsFeightTemplate:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2185, '运费模版新增', 2183, 2, '#', '', 'F', '0', 'goods:pmsFeightTemplate:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2186, '运费模版修改', 2183, 3, '#', '', 'F', '0', 'goods:pmsFeightTemplate:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2187, '运费模版删除', 2183, 4, '#', '', 'F', '0', 'goods:pmsFeightTemplate:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2188, 'PmsGiftsCategory', 2043, 1, '/goods/pmsGiftsCategory', '', 'C', '0', 'goods:pmsGiftsCategory:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', 'PmsGiftsCategory菜单');
INSERT INTO `admin_sys_menu` VALUES (2189, 'PmsGiftsCategory查询', 2188, 1, '#', '', 'F', '0', 'goods:pmsGiftsCategory:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2190, 'PmsGiftsCategory新增', 2188, 2, '#', '', 'F', '0', 'goods:pmsGiftsCategory:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2191, 'PmsGiftsCategory修改', 2188, 3, '#', '', 'F', '0', 'goods:pmsGiftsCategory:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2192, 'PmsGiftsCategory删除', 2188, 4, '#', '', 'F', '0', 'goods:pmsGiftsCategory:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2193, '活动商品', 2042, 1, '/goods/pmsGifts', '', 'C', '0', 'goods:pmsGifts:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '活动商品菜单');
INSERT INTO `admin_sys_menu` VALUES (2194, '活动商品查询', 2193, 1, '#', '', 'F', '0', 'goods:pmsGifts:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2195, '活动商品新增', 2193, 2, '#', '', 'F', '0', 'goods:pmsGifts:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2196, '活动商品修改', 2193, 3, '#', '', 'F', '0', 'goods:pmsGifts:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2197, '活动商品删除', 2193, 4, '#', '', 'F', '0', 'goods:pmsGifts:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2198, '产品属性分类', 2042, 1, '/goods/pmsProductAttributeCategory', '', 'C', '0', 'goods:pmsProductAttributeCategory:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '产品属性分类菜单');
INSERT INTO `admin_sys_menu` VALUES (2199, '产品属性分类查询', 2198, 1, '#', '', 'F', '0', 'goods:pmsProductAttributeCategory:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2200, '产品属性分类新增', 2198, 2, '#', '', 'F', '0', 'goods:pmsProductAttributeCategory:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2201, '产品属性分类修改', 2198, 3, '#', '', 'F', '0', 'goods:pmsProductAttributeCategory:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2202, '产品属性分类删除', 2198, 4, '#', '', 'F', '0', 'goods:pmsProductAttributeCategory:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2203, '商品属性参数', 2042, 1, '/goods/pmsProductAttribute', '', 'C', '0', 'goods:pmsProductAttribute:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '商品属性参数菜单');
INSERT INTO `admin_sys_menu` VALUES (2204, '商品属性参数查询', 2203, 1, '#', '', 'F', '0', 'goods:pmsProductAttribute:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2205, '商品属性参数新增', 2203, 2, '#', '', 'F', '0', 'goods:pmsProductAttribute:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2206, '商品属性参数修改', 2203, 3, '#', '', 'F', '0', 'goods:pmsProductAttribute:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2207, '商品属性参数删除', 2203, 4, '#', '', 'F', '0', 'goods:pmsProductAttribute:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2208, '产品分类', 2042, 1, '/goods/pmsProductCategory', 'menuItem', 'C', '0', 'goods:pmsProductCategory:view', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2020-05-07 09:00:41', '产品分类菜单');
INSERT INTO `admin_sys_menu` VALUES (2209, '产品分类查询', 2208, 1, '#', '', 'F', '0', 'goods:pmsProductCategory:list', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2210, '产品分类新增', 2208, 2, '#', '', 'F', '0', 'goods:pmsProductCategory:add', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2211, '产品分类修改', 2208, 3, '#', '', 'F', '0', 'goods:pmsProductCategory:edit', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2018-03-01 00:00:00', '');
INSERT INTO `admin_sys_menu` VALUES (2212, '产品分类删除', 2208, 4, '#', 'menuItem', 'C', '0', 'goods:pmsProductCategory:remove', '#', 'admin', '2018-03-01 00:00:00', 'ry', '2020-05-26 15:01:47', '');
INSERT INTO `admin_sys_menu` VALUES (2213, '分销管理', 0, 2, '#', 'menuItem', 'M', '0', NULL, 'fa fa-share-alt', 'ry', '2020-04-29 08:27:08', '', NULL, '');

-- ----------------------------
-- Table structure for admin_sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_notice`;
CREATE TABLE `admin_sys_notice`  (
  `notice_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '公告标题',
  `notice_type` int(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '公告内容',
  `status` int(1) NULL DEFAULT 0 COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_notice
-- ----------------------------
INSERT INTO `admin_sys_notice` VALUES (6, '测试我的ml通知', 1, '测试我的ml通知', 1, '', '2020-07-04 13:46:39', '', NULL, '测试我的ml通知');

-- ----------------------------
-- Table structure for admin_sys_oper_log
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_oper_log`;
CREATE TABLE `admin_sys_oper_log`  (
  `oper_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '模块标题',
  `business_type` int(11) NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求方式',
  `operator_type` int(11) NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '请求参数',
  `status` int(11) NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime(0) NULL DEFAULT NULL COMMENT '操作时间',
  PRIMARY KEY (`oper_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 166 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '操作日志记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_oper_log
-- ----------------------------
INSERT INTO `admin_sys_oper_log` VALUES (103, '个人信息', 2, 'com.ruoyi.project.system.user.controller.ProfileController.update()', 'POST', 1, 'admin', NULL, '/system/user/profile/update', '112.96.37.213', 'XX XX', '{\"id\":[\"\"],\"userName\":[\"mallplus管理员\"],\"phonenumber\":[\"15888888888\"],\"email\":[\"951449465@qq.com\"],\"sex\":[\"1\"]}', 0, NULL, '2020-06-25 19:20:35');
INSERT INTO `admin_sys_oper_log` VALUES (104, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '183.160.247.12', 'XX XX', '{\"id\":[\"3\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-27 14:13:12');
INSERT INTO `admin_sys_oper_log` VALUES (105, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '183.160.247.12', 'XX XX', '{\"id\":[\"8\"],\"verifyStatus\":[\"0\"]}', 0, NULL, '2020-06-27 14:13:20');
INSERT INTO `admin_sys_oper_log` VALUES (106, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '119.62.24.138', 'XX XX', '{\"id\":[\"1425\"],\"isBoutique\":[\"1\"]}', 0, NULL, '2020-06-28 13:57:17');
INSERT INTO `admin_sys_oper_log` VALUES (107, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '119.62.24.138', 'XX XX', '{\"id\":[\"1425\"],\"isBoutique\":[\"0\"]}', 0, NULL, '2020-06-28 13:57:28');
INSERT INTO `admin_sys_oper_log` VALUES (108, '用户管理', 2, 'com.ruoyi.project.mall.advertise.controller.SmsHomeAdvertiseController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/advertise/changeStatus', '222.244.195.112', 'XX XX', '{\"id\":[\"48\"],\"status\":[\"0\"]}', 0, NULL, '2020-06-29 19:02:50');
INSERT INTO `admin_sys_oper_log` VALUES (109, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1434\"],\"isBoutique\":[\"0\"]}', 0, NULL, '2020-06-30 09:45:26');
INSERT INTO `admin_sys_oper_log` VALUES (110, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1432\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:29');
INSERT INTO `admin_sys_oper_log` VALUES (111, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1416\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:38');
INSERT INTO `admin_sys_oper_log` VALUES (112, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1416\"],\"isBoutique\":[\"0\"]}', 0, NULL, '2020-06-30 09:45:40');
INSERT INTO `admin_sys_oper_log` VALUES (113, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1417\"],\"isBoutique\":[\"0\"]}', 0, NULL, '2020-06-30 09:45:42');
INSERT INTO `admin_sys_oper_log` VALUES (114, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1418\"],\"isBoutique\":[\"0\"]}', 0, NULL, '2020-06-30 09:45:43');
INSERT INTO `admin_sys_oper_log` VALUES (115, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1418\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:45');
INSERT INTO `admin_sys_oper_log` VALUES (116, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1419\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:46');
INSERT INTO `admin_sys_oper_log` VALUES (117, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1419\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:46');
INSERT INTO `admin_sys_oper_log` VALUES (118, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1420\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:49');
INSERT INTO `admin_sys_oper_log` VALUES (119, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1421\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:50');
INSERT INTO `admin_sys_oper_log` VALUES (120, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1421\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:50');
INSERT INTO `admin_sys_oper_log` VALUES (121, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1422\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:52');
INSERT INTO `admin_sys_oper_log` VALUES (122, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1423\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:54');
INSERT INTO `admin_sys_oper_log` VALUES (123, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1424\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:56');
INSERT INTO `admin_sys_oper_log` VALUES (124, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1425\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:45:58');
INSERT INTO `admin_sys_oper_log` VALUES (125, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '192.168.1.81', '内网IP', '{\"id\":[\"1431\"],\"status\":[\"1\"]}', 0, NULL, '2020-06-30 09:46:01');
INSERT INTO `admin_sys_oper_log` VALUES (126, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.81', '内网IP', '{\"id\":[\"8\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-30 09:50:21');
INSERT INTO `admin_sys_oper_log` VALUES (127, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.81', '内网IP', '{\"id\":[\"9\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-30 09:50:23');
INSERT INTO `admin_sys_oper_log` VALUES (128, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.81', '内网IP', '{\"id\":[\"10\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-30 09:50:26');
INSERT INTO `admin_sys_oper_log` VALUES (129, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.81', '内网IP', '{\"id\":[\"12\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-30 09:50:28');
INSERT INTO `admin_sys_oper_log` VALUES (130, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.81', '内网IP', '{\"id\":[\"13\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-30 09:50:33');
INSERT INTO `admin_sys_oper_log` VALUES (131, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.81', '内网IP', '{\"id\":[\"15\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-30 09:50:34');
INSERT INTO `admin_sys_oper_log` VALUES (132, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.81', '内网IP', '{\"id\":[\"16\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-30 09:50:36');
INSERT INTO `admin_sys_oper_log` VALUES (133, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.81', '内网IP', '{\"id\":[\"16\"],\"publishStatus\":[\"0\"]}', 0, NULL, '2020-06-30 09:50:36');
INSERT INTO `admin_sys_oper_log` VALUES (134, '支付方式', 2, 'com.ruoyi.project.mall.payments.controller.OmsPaymentsController.editSave()', 'POST', 1, 'admin', NULL, '/mall/payments/edit', '192.168.1.91', '内网IP', '{\"id\":[\"1\"],\"code\":[\"wechatpay\"],\"name\":[\"微信支付QAQ\"],\"isOnline\":[\"1\"],\"sort\":[\"1\"],\"memo\":[\"微信支付QAQ\"],\"status\":[\"\"],\"paramss\":[\"{\\\"appId\\\":\\\"123456\\\",\\\"appSecret\\\":\\\"123456\\\",\\\"mchId\\\":\\\"1\\\",\\\"partnerKey\\\":\\\"123456\\\",\\\"certPath\\\":\\\"/var/local/cert\\\", \\\"domain\\\":\\\"testPay\\\"}\"]}', 0, NULL, '2020-07-02 16:55:54');
INSERT INTO `admin_sys_oper_log` VALUES (135, '支付方式', 2, 'com.ruoyi.project.mall.payments.controller.OmsPaymentsController.editSave()', 'POST', 1, 'admin', NULL, '/mall/payments/edit', '192.168.1.91', '内网IP', '{\"id\":[\"1\"],\"code\":[\"wechatpay\"],\"name\":[\"微信支付\"],\"isOnline\":[\"1\"],\"sort\":[\"1\"],\"memo\":[\"微信支付\"],\"status\":[\"\"],\"paramss\":[\"{\\\"appId\\\":\\\"123456\\\",\\\"appSecret\\\":\\\"123456\\\",\\\"mchId\\\":\\\"1\\\",\\\"partnerKey\\\":\\\"123456\\\",\\\"certPath\\\":\\\"/var/local/cert\\\", \\\"domain\\\":\\\"testPay\\\"}\"]}', 0, NULL, '2020-07-02 16:56:20');
INSERT INTO `admin_sys_oper_log` VALUES (136, '品牌', 1, 'com.ruoyi.project.goods.pmsBrand.controller.PmsBrandController.addSave()', 'POST', 1, 'admin', NULL, '/goods/pmsBrand/add', '192.168.1.81', '内网IP', '{\"name\":[\"\"],\"firstLetter\":[\"\"],\"sort\":[\"\"],\"productCount\":[\"\"],\"productCommentCount\":[\"\"],\"logo\":[\"\"],\"bigPic\":[\"\"],\"brandStory\":[\"\"],\"storeId\":[\"\"]}', 1, '\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1\n### The error may involve com.ruoyi.project.goods.pmsBrand.mapper.PmsBrandMapper.insertPmsBrand-Inline\n### The error occurred while setting parameters\n### SQL: insert into pms_brand\n### Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1', '2020-07-03 09:43:31');
INSERT INTO `admin_sys_oper_log` VALUES (137, '品牌', 1, 'com.ruoyi.project.goods.pmsBrand.controller.PmsBrandController.addSave()', 'POST', 1, 'admin', NULL, '/goods/pmsBrand/add', '192.168.1.81', '内网IP', '{\"name\":[\"\"],\"firstLetter\":[\"\"],\"sort\":[\"\"],\"productCount\":[\"\"],\"productCommentCount\":[\"\"],\"logo\":[\"\"],\"bigPic\":[\"\"],\"brandStory\":[\"\"],\"storeId\":[\"\"]}', 1, '\n### Error updating database.  Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1\n### The error may involve com.ruoyi.project.goods.pmsBrand.mapper.PmsBrandMapper.insertPmsBrand-Inline\n### The error occurred while setting parameters\n### SQL: insert into pms_brand\n### Cause: java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1\n; bad SQL grammar []; nested exception is java.sql.SQLSyntaxErrorException: You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near \'\' at line 1', '2020-07-03 09:43:51');
INSERT INTO `admin_sys_oper_log` VALUES (138, '商品属性参数', 3, 'com.ruoyi.project.goods.pmsProductAttribute.controller.PmsProductAttributeController.remove()', 'POST', 1, 'admin', NULL, '/goods/pmsProductAttribute/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"270\"]}', 0, NULL, '2020-07-03 10:11:16');
INSERT INTO `admin_sys_oper_log` VALUES (139, '商品属性参数', 3, 'com.ruoyi.project.goods.pmsProductAttribute.controller.PmsProductAttributeController.remove()', 'POST', 1, 'admin', NULL, '/goods/pmsProductAttribute/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"291\"]}', 0, NULL, '2020-07-03 10:11:22');
INSERT INTO `admin_sys_oper_log` VALUES (140, '商品属性参数', 2, 'com.ruoyi.project.goods.pmsProductAttribute.controller.PmsProductAttributeController.editSave()', 'POST', 1, 'admin', NULL, '/goods/pmsProductAttribute/edit', '192.168.1.81', '内网IP', '{\"id\":[\"271\"],\"productAttributeCategoryId\":[\"1005020\"],\"name\":[\"时长\"]}', 0, NULL, '2020-07-03 10:18:32');
INSERT INTO `admin_sys_oper_log` VALUES (141, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'ry', NULL, '/mall/store/changeStatus', '115.60.57.17', 'XX XX', '{\"id\":[\"1451\"],\"status\":[\"3\"]}', 0, NULL, '2020-07-03 16:33:44');
INSERT INTO `admin_sys_oper_log` VALUES (142, '角色管理', 4, 'com.ruoyi.project.system.role.controller.RoleController.cancelAuthUser()', 'POST', 1, 'admin', NULL, '/system/role/authUser/cancel', '119.129.118.28', 'XX XX', '{\"roleId\":[\"1\"],\"userId\":[\"104\"]}', 0, NULL, '2020-07-05 15:06:14');
INSERT INTO `admin_sys_oper_log` VALUES (143, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '1.83.233.129', 'XX XX', '{\"id\":[\"1460\"],\"status\":[\"3\"]}', 0, NULL, '2020-07-06 20:36:16');
INSERT INTO `admin_sys_oper_log` VALUES (144, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"104\"]}', 0, NULL, '2020-07-07 17:13:01');
INSERT INTO `admin_sys_oper_log` VALUES (145, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"109\"]}', 0, NULL, '2020-07-07 17:13:09');
INSERT INTO `admin_sys_oper_log` VALUES (146, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"103\"]}', 0, NULL, '2020-07-07 17:13:14');
INSERT INTO `admin_sys_oper_log` VALUES (147, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"109\"]}', 0, NULL, '2020-07-07 17:13:19');
INSERT INTO `admin_sys_oper_log` VALUES (148, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"105\"]}', 0, NULL, '2020-07-07 17:13:24');
INSERT INTO `admin_sys_oper_log` VALUES (149, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"106\"]}', 0, NULL, '2020-07-07 17:14:29');
INSERT INTO `admin_sys_oper_log` VALUES (150, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"107\"]}', 0, NULL, '2020-07-07 17:14:43');
INSERT INTO `admin_sys_oper_log` VALUES (151, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"2\"]}', 0, NULL, '2020-07-07 17:14:48');
INSERT INTO `admin_sys_oper_log` VALUES (152, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"110\"]}', 0, NULL, '2020-07-07 17:14:52');
INSERT INTO `admin_sys_oper_log` VALUES (153, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"100\"]}', 0, NULL, '2020-07-07 17:14:58');
INSERT INTO `admin_sys_oper_log` VALUES (154, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"101\"]}', 0, NULL, '2020-07-07 17:15:00');
INSERT INTO `admin_sys_oper_log` VALUES (155, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"102\"]}', 0, NULL, '2020-07-07 17:15:04');
INSERT INTO `admin_sys_oper_log` VALUES (156, '角色管理', 3, 'com.ruoyi.project.system.role.controller.RoleController.remove()', 'POST', 1, 'admin', NULL, '/system/role/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"108\"]}', 0, NULL, '2020-07-07 17:15:07');
INSERT INTO `admin_sys_oper_log` VALUES (157, '用户管理', 3, 'com.ruoyi.project.system.user.controller.UserController.remove()', 'POST', 1, 'admin', NULL, '/system/user/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"100\"]}', 0, NULL, '2020-07-07 19:30:26');
INSERT INTO `admin_sys_oper_log` VALUES (158, '用户管理', 3, 'com.ruoyi.project.system.user.controller.UserController.remove()', 'POST', 1, 'admin', NULL, '/system/user/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"101\"]}', 0, NULL, '2020-07-07 19:30:30');
INSERT INTO `admin_sys_oper_log` VALUES (159, '用户管理', 3, 'com.ruoyi.project.system.user.controller.UserController.remove()', 'POST', 1, 'admin', NULL, '/system/user/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"102\"]}', 0, NULL, '2020-07-07 19:30:39');
INSERT INTO `admin_sys_oper_log` VALUES (160, '用户管理', 3, 'com.ruoyi.project.system.user.controller.UserController.remove()', 'POST', 1, 'admin', NULL, '/system/user/remove', '192.168.1.81', '内网IP', '{\"ids\":[\"103\"]}', 0, NULL, '2020-07-07 19:30:41');
INSERT INTO `admin_sys_oper_log` VALUES (161, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.100', '内网IP', '{\"id\":[\"3\"],\"verifyStatus\":[\"0\"]}', 0, NULL, '2020-07-07 19:32:54');
INSERT INTO `admin_sys_oper_log` VALUES (162, '商品信息', 2, 'com.ruoyi.project.goods.product.controller.PmsProductController.editSave()', 'POST', 1, 'admin', NULL, '/mall/product/edit', '192.168.1.100', '内网IP', '{\"id\":[\"3\"],\"verifyStatus\":[\"1\"]}', 0, NULL, '2020-07-07 19:32:57');
INSERT INTO `admin_sys_oper_log` VALUES (163, '重置密码', 2, 'com.ruoyi.project.system.user.controller.UserController.resetPwd()', 'GET', 1, 'admin', NULL, '/system/user/resetPwd/2', '192.168.1.81', '内网IP', '{}', 0, NULL, '2020-07-08 09:14:50');
INSERT INTO `admin_sys_oper_log` VALUES (164, '商品属性参数', 1, 'com.ruoyi.project.goods.pmsProductAttribute.controller.PmsProductAttributeController.addSave()', 'POST', 1, 'admin', NULL, '/goods/pmsProductAttribute/add', '127.0.0.1', '内网IP', '{\"productAttributeCategoryId\":[\"1\"],\"name\":[\"2\"],\"storeId\":[\"3\"]}', 0, NULL, '2020-07-13 15:47:48');
INSERT INTO `admin_sys_oper_log` VALUES (165, '用户管理', 2, 'com.ruoyi.project.mall.store.controller.StoreController.changeStatus()', 'POST', 1, 'admin', NULL, '/mall/store/changeStatus', '223.20.0.113', 'XX XX', '{\"id\":[\"1478\"],\"isBoutique\":[\"0\"]}', 0, NULL, '2020-07-21 21:04:21');

-- ----------------------------
-- Table structure for admin_sys_post
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_post`;
CREATE TABLE `admin_sys_post`  (
  `post_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '岗位名称',
  `post_sort` int(11) NOT NULL COMMENT '显示顺序',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '岗位信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_post
-- ----------------------------
INSERT INTO `admin_sys_post` VALUES (1, 'ceo', '董事长', 1, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2020-05-26 06:22:40', '');
INSERT INTO `admin_sys_post` VALUES (2, 'se', '项目经理', 2, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2020-07-15 15:44:11', '');
INSERT INTO `admin_sys_post` VALUES (3, 'hr', '人力资源', 3, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '');
INSERT INTO `admin_sys_post` VALUES (4, 'user', '普通员工', 4, '0', 'admin', '2018-03-16 11:33:00', 'ry', '2020-07-15 15:44:29', '');
INSERT INTO `admin_sys_post` VALUES (5, 'buyer', '采购部', 5, '0', 'admin', '2020-02-19 10:19:05', 'admin', '2020-02-19 10:19:54', '');
INSERT INTO `admin_sys_post` VALUES (7, '测试', '测试123', 1, '0', 'ry', '2020-05-13 14:24:19', 'ry', '2020-07-15 15:45:10', '');
INSERT INTO `admin_sys_post` VALUES (8, 'adsa', 'dsada', 22, '0', 'ry', '2020-05-15 20:27:15', '', NULL, NULL);

-- ----------------------------
-- Table structure for admin_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_role`;
CREATE TABLE `admin_sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色权限字符串',
  `role_sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 111 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_role
-- ----------------------------
INSERT INTO `admin_sys_role` VALUES (1, '管理员', 'admin', 1, '1', '0', '0', 'admin', '2018-03-16 11:33:00', 'admin', '2020-07-05 11:11:54', '管理员');
INSERT INTO `admin_sys_role` VALUES (2, '普通角色', 'common', 2, '2', '0', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2020-04-29 11:47:07', '普通角色');
INSERT INTO `admin_sys_role` VALUES (100, '技术', 'rule', 1, '2', '0', '0', 'admin', '2019-09-28 13:49:54', 'ry', '2020-04-26 15:56:02', '');
INSERT INTO `admin_sys_role` VALUES (101, '经理', '@RequiresRoles(\"\")', 1, '1', '0', '0', 'ry', '2020-04-26 20:51:56', '', NULL, NULL);
INSERT INTO `admin_sys_role` VALUES (102, '角色', 'role1', 1, '1', '0', '0', 'ry', '2020-05-04 14:20:39', '', NULL, '备注');
INSERT INTO `admin_sys_role` VALUES (103, '1111', '@RequiresRoles(\"**\")', 1, '1', '0', '2', 'ry', '2020-05-09 11:14:59', '', NULL, '111');
INSERT INTO `admin_sys_role` VALUES (104, '啊', '撒的发', 1, '1', '0', '2', 'ry', '2020-05-10 10:07:32', '', NULL, NULL);
INSERT INTO `admin_sys_role` VALUES (105, '123', '123', 1, '1', '0', '2', 'ry', '2020-05-12 10:47:01', '', NULL, NULL);
INSERT INTO `admin_sys_role` VALUES (106, '范文', '2332323', 1, '1', '0', '2', 'ry', '2020-05-12 10:47:25', '', NULL, NULL);
INSERT INTO `admin_sys_role` VALUES (107, '1', '1', 1, '1', '0', '2', 'ry', '2020-05-25 14:07:55', '', NULL, NULL);
INSERT INTO `admin_sys_role` VALUES (108, '测试管理员', '@RequiresRoles(\"111\")', 1, '1', '0', '0', 'ry', '2020-05-28 18:27:55', '', NULL, NULL);
INSERT INTO `admin_sys_role` VALUES (109, '1231', '23rsa', 1, '1', '0', '0', 'ry', '2020-05-29 18:17:48', '', NULL, '21');
INSERT INTO `admin_sys_role` VALUES (110, 'test', '@RequiresRoles(\"test\")', 1, '1', '0', '2', 'ry', '2020-06-11 11:16:38', '', NULL, NULL);

-- ----------------------------
-- Table structure for admin_sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_role_dept`;
CREATE TABLE `admin_sys_role_dept`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `dept_id` bigint(20) NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色和部门关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_role_dept
-- ----------------------------
INSERT INTO `admin_sys_role_dept` VALUES (2, 100);
INSERT INTO `admin_sys_role_dept` VALUES (2, 101);
INSERT INTO `admin_sys_role_dept` VALUES (2, 105);

-- ----------------------------
-- Table structure for admin_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_role_menu`;
CREATE TABLE `admin_sys_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_role_menu
-- ----------------------------
INSERT INTO `admin_sys_role_menu` VALUES (1, 1);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2);
INSERT INTO `admin_sys_role_menu` VALUES (1, 3);
INSERT INTO `admin_sys_role_menu` VALUES (1, 100);
INSERT INTO `admin_sys_role_menu` VALUES (1, 101);
INSERT INTO `admin_sys_role_menu` VALUES (1, 102);
INSERT INTO `admin_sys_role_menu` VALUES (1, 103);
INSERT INTO `admin_sys_role_menu` VALUES (1, 104);
INSERT INTO `admin_sys_role_menu` VALUES (1, 105);
INSERT INTO `admin_sys_role_menu` VALUES (1, 106);
INSERT INTO `admin_sys_role_menu` VALUES (1, 107);
INSERT INTO `admin_sys_role_menu` VALUES (1, 108);
INSERT INTO `admin_sys_role_menu` VALUES (1, 109);
INSERT INTO `admin_sys_role_menu` VALUES (1, 110);
INSERT INTO `admin_sys_role_menu` VALUES (1, 111);
INSERT INTO `admin_sys_role_menu` VALUES (1, 112);
INSERT INTO `admin_sys_role_menu` VALUES (1, 113);
INSERT INTO `admin_sys_role_menu` VALUES (1, 114);
INSERT INTO `admin_sys_role_menu` VALUES (1, 115);
INSERT INTO `admin_sys_role_menu` VALUES (1, 500);
INSERT INTO `admin_sys_role_menu` VALUES (1, 501);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1000);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1001);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1002);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1003);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1004);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1005);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1006);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1007);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1008);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1009);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1010);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1011);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1012);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1013);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1014);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1015);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1016);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1017);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1018);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1019);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1020);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1021);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1022);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1023);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1024);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1025);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1026);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1027);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1028);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1029);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1030);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1031);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1032);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1033);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1034);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1035);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1036);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1037);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1038);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1039);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1040);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1041);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1042);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1043);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1044);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1045);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1046);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1047);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1048);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1049);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1050);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1051);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1052);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1053);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1054);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1055);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1056);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1057);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1058);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1059);
INSERT INTO `admin_sys_role_menu` VALUES (1, 1060);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2000);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2001);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2002);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2003);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2004);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2005);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2006);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2007);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2008);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2009);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2015);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2016);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2017);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2018);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2019);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2020);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2024);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2025);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2026);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2027);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2028);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2029);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2030);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2031);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2035);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2037);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2038);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2039);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2040);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2041);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2042);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2043);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2044);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2125);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2126);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2127);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2128);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2129);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2130);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2131);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2132);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2133);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2134);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2135);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2136);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2137);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2138);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2139);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2140);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2141);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2142);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2143);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2144);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2145);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2146);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2147);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2148);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2149);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2150);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2151);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2152);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2153);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2154);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2155);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2156);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2157);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2158);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2159);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2160);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2161);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2162);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2163);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2164);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2165);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2166);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2167);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2168);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2169);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2170);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2171);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2172);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2173);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2174);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2175);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2176);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2177);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2178);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2179);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2180);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2181);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2182);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2183);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2184);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2185);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2186);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2187);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2188);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2189);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2190);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2191);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2192);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2193);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2194);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2195);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2196);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2197);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2198);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2199);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2200);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2201);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2202);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2203);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2204);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2205);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2206);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2207);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2208);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2209);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2210);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2211);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2212);
INSERT INTO `admin_sys_role_menu` VALUES (1, 2213);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1);
INSERT INTO `admin_sys_role_menu` VALUES (2, 2);
INSERT INTO `admin_sys_role_menu` VALUES (2, 3);
INSERT INTO `admin_sys_role_menu` VALUES (2, 100);
INSERT INTO `admin_sys_role_menu` VALUES (2, 101);
INSERT INTO `admin_sys_role_menu` VALUES (2, 102);
INSERT INTO `admin_sys_role_menu` VALUES (2, 103);
INSERT INTO `admin_sys_role_menu` VALUES (2, 104);
INSERT INTO `admin_sys_role_menu` VALUES (2, 105);
INSERT INTO `admin_sys_role_menu` VALUES (2, 106);
INSERT INTO `admin_sys_role_menu` VALUES (2, 107);
INSERT INTO `admin_sys_role_menu` VALUES (2, 108);
INSERT INTO `admin_sys_role_menu` VALUES (2, 109);
INSERT INTO `admin_sys_role_menu` VALUES (2, 110);
INSERT INTO `admin_sys_role_menu` VALUES (2, 111);
INSERT INTO `admin_sys_role_menu` VALUES (2, 112);
INSERT INTO `admin_sys_role_menu` VALUES (2, 113);
INSERT INTO `admin_sys_role_menu` VALUES (2, 114);
INSERT INTO `admin_sys_role_menu` VALUES (2, 115);
INSERT INTO `admin_sys_role_menu` VALUES (2, 500);
INSERT INTO `admin_sys_role_menu` VALUES (2, 501);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1000);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1001);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1002);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1003);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1004);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1005);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1006);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1007);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1008);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1009);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1010);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1011);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1012);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1013);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1014);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1015);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1016);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1017);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1018);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1019);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1020);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1021);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1022);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1023);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1024);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1025);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1026);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1027);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1028);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1029);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1030);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1031);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1032);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1033);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1034);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1035);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1036);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1037);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1038);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1039);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1040);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1041);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1042);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1043);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1044);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1045);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1046);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1047);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1048);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1049);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1050);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1051);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1052);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1053);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1054);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1055);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1056);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1057);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1058);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1059);
INSERT INTO `admin_sys_role_menu` VALUES (2, 1060);
INSERT INTO `admin_sys_role_menu` VALUES (2, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (2, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (2, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (2, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (2, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (2, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2);
INSERT INTO `admin_sys_role_menu` VALUES (100, 3);
INSERT INTO `admin_sys_role_menu` VALUES (100, 100);
INSERT INTO `admin_sys_role_menu` VALUES (100, 101);
INSERT INTO `admin_sys_role_menu` VALUES (100, 102);
INSERT INTO `admin_sys_role_menu` VALUES (100, 103);
INSERT INTO `admin_sys_role_menu` VALUES (100, 104);
INSERT INTO `admin_sys_role_menu` VALUES (100, 105);
INSERT INTO `admin_sys_role_menu` VALUES (100, 106);
INSERT INTO `admin_sys_role_menu` VALUES (100, 107);
INSERT INTO `admin_sys_role_menu` VALUES (100, 108);
INSERT INTO `admin_sys_role_menu` VALUES (100, 109);
INSERT INTO `admin_sys_role_menu` VALUES (100, 110);
INSERT INTO `admin_sys_role_menu` VALUES (100, 111);
INSERT INTO `admin_sys_role_menu` VALUES (100, 112);
INSERT INTO `admin_sys_role_menu` VALUES (100, 113);
INSERT INTO `admin_sys_role_menu` VALUES (100, 114);
INSERT INTO `admin_sys_role_menu` VALUES (100, 115);
INSERT INTO `admin_sys_role_menu` VALUES (100, 500);
INSERT INTO `admin_sys_role_menu` VALUES (100, 501);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1000);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1001);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1002);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1003);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1004);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1005);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1006);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1007);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1008);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1009);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1010);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1011);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1012);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1013);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1014);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1015);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1016);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1017);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1018);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1019);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1020);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1021);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1022);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1023);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1024);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1025);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1026);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1027);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1028);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1029);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1030);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1031);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1032);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1033);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1034);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1035);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1036);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1037);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1038);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1039);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1040);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1041);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1042);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1043);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1044);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1045);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1046);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1047);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1048);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1049);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1050);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1051);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1052);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1053);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1054);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1055);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1056);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1057);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1058);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1059);
INSERT INTO `admin_sys_role_menu` VALUES (100, 1060);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2000);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2001);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2002);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2003);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2004);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2005);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2006);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2007);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2008);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2009);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2015);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2016);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2017);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2018);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2019);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2020);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2024);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2025);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2026);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2027);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2028);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2029);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2030);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2031);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2035);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2037);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2038);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2039);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2040);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2041);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2042);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2208);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2209);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2210);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2211);
INSERT INTO `admin_sys_role_menu` VALUES (100, 2212);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2);
INSERT INTO `admin_sys_role_menu` VALUES (102, 109);
INSERT INTO `admin_sys_role_menu` VALUES (102, 110);
INSERT INTO `admin_sys_role_menu` VALUES (102, 111);
INSERT INTO `admin_sys_role_menu` VALUES (102, 112);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1046);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1047);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1048);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1049);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1050);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1051);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1052);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1053);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1054);
INSERT INTO `admin_sys_role_menu` VALUES (102, 1055);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2000);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2001);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2002);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2003);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2004);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2005);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2006);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2007);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2008);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2009);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2015);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2016);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2017);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2018);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2019);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2020);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2024);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2025);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2026);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2027);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2028);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2029);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2030);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2031);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (102, 2035);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2);
INSERT INTO `admin_sys_role_menu` VALUES (103, 3);
INSERT INTO `admin_sys_role_menu` VALUES (103, 100);
INSERT INTO `admin_sys_role_menu` VALUES (103, 101);
INSERT INTO `admin_sys_role_menu` VALUES (103, 102);
INSERT INTO `admin_sys_role_menu` VALUES (103, 103);
INSERT INTO `admin_sys_role_menu` VALUES (103, 104);
INSERT INTO `admin_sys_role_menu` VALUES (103, 105);
INSERT INTO `admin_sys_role_menu` VALUES (103, 106);
INSERT INTO `admin_sys_role_menu` VALUES (103, 107);
INSERT INTO `admin_sys_role_menu` VALUES (103, 108);
INSERT INTO `admin_sys_role_menu` VALUES (103, 109);
INSERT INTO `admin_sys_role_menu` VALUES (103, 110);
INSERT INTO `admin_sys_role_menu` VALUES (103, 111);
INSERT INTO `admin_sys_role_menu` VALUES (103, 112);
INSERT INTO `admin_sys_role_menu` VALUES (103, 113);
INSERT INTO `admin_sys_role_menu` VALUES (103, 114);
INSERT INTO `admin_sys_role_menu` VALUES (103, 115);
INSERT INTO `admin_sys_role_menu` VALUES (103, 500);
INSERT INTO `admin_sys_role_menu` VALUES (103, 501);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1000);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1001);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1002);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1003);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1004);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1005);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1006);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1007);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1008);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1009);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1010);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1011);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1012);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1013);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1014);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1015);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1016);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1017);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1018);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1019);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1020);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1021);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1022);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1023);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1024);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1025);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1026);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1027);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1028);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1029);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1030);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1031);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1032);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1033);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1034);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1035);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1036);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1037);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1038);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1039);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1040);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1041);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1042);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1043);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1044);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1045);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1046);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1047);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1048);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1049);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1050);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1051);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1052);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1053);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1054);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1055);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1056);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1057);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1058);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1059);
INSERT INTO `admin_sys_role_menu` VALUES (103, 1060);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2000);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2001);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2002);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2003);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2004);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2005);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2006);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2007);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2008);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2009);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2015);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2016);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2017);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2018);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2019);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2020);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2024);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2025);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2026);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2027);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2028);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2029);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2030);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2031);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2035);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2037);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2038);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2039);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2040);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2041);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2042);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2208);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2209);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2210);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2211);
INSERT INTO `admin_sys_role_menu` VALUES (103, 2212);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2000);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2001);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2002);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2003);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2004);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2005);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2006);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2007);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2008);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2009);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2015);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2016);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2017);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2018);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2019);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2020);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2024);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2025);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2026);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2027);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2028);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2029);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2030);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2031);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (104, 2035);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2);
INSERT INTO `admin_sys_role_menu` VALUES (105, 3);
INSERT INTO `admin_sys_role_menu` VALUES (105, 100);
INSERT INTO `admin_sys_role_menu` VALUES (105, 101);
INSERT INTO `admin_sys_role_menu` VALUES (105, 102);
INSERT INTO `admin_sys_role_menu` VALUES (105, 103);
INSERT INTO `admin_sys_role_menu` VALUES (105, 104);
INSERT INTO `admin_sys_role_menu` VALUES (105, 105);
INSERT INTO `admin_sys_role_menu` VALUES (105, 106);
INSERT INTO `admin_sys_role_menu` VALUES (105, 107);
INSERT INTO `admin_sys_role_menu` VALUES (105, 108);
INSERT INTO `admin_sys_role_menu` VALUES (105, 109);
INSERT INTO `admin_sys_role_menu` VALUES (105, 110);
INSERT INTO `admin_sys_role_menu` VALUES (105, 111);
INSERT INTO `admin_sys_role_menu` VALUES (105, 112);
INSERT INTO `admin_sys_role_menu` VALUES (105, 113);
INSERT INTO `admin_sys_role_menu` VALUES (105, 114);
INSERT INTO `admin_sys_role_menu` VALUES (105, 115);
INSERT INTO `admin_sys_role_menu` VALUES (105, 500);
INSERT INTO `admin_sys_role_menu` VALUES (105, 501);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1000);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1001);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1002);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1003);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1004);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1005);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1006);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1007);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1008);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1009);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1010);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1011);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1012);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1013);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1014);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1015);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1016);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1017);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1018);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1019);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1020);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1021);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1022);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1023);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1024);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1025);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1026);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1027);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1028);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1029);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1030);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1031);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1032);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1033);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1034);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1035);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1036);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1037);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1038);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1039);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1040);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1041);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1042);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1043);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1044);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1045);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1046);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1047);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1048);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1049);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1050);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1051);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1052);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1053);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1054);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1055);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1056);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1057);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1058);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1059);
INSERT INTO `admin_sys_role_menu` VALUES (105, 1060);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2000);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2001);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2002);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2003);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2004);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2005);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2006);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2007);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2008);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2009);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2015);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2016);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2017);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2018);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2019);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2020);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2024);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2025);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2026);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2027);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2028);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2029);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2030);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2031);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2035);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2037);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2038);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2039);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2040);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2041);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2042);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2208);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2209);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2210);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2211);
INSERT INTO `admin_sys_role_menu` VALUES (105, 2212);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2);
INSERT INTO `admin_sys_role_menu` VALUES (106, 3);
INSERT INTO `admin_sys_role_menu` VALUES (106, 100);
INSERT INTO `admin_sys_role_menu` VALUES (106, 101);
INSERT INTO `admin_sys_role_menu` VALUES (106, 102);
INSERT INTO `admin_sys_role_menu` VALUES (106, 103);
INSERT INTO `admin_sys_role_menu` VALUES (106, 104);
INSERT INTO `admin_sys_role_menu` VALUES (106, 105);
INSERT INTO `admin_sys_role_menu` VALUES (106, 106);
INSERT INTO `admin_sys_role_menu` VALUES (106, 107);
INSERT INTO `admin_sys_role_menu` VALUES (106, 108);
INSERT INTO `admin_sys_role_menu` VALUES (106, 109);
INSERT INTO `admin_sys_role_menu` VALUES (106, 110);
INSERT INTO `admin_sys_role_menu` VALUES (106, 111);
INSERT INTO `admin_sys_role_menu` VALUES (106, 112);
INSERT INTO `admin_sys_role_menu` VALUES (106, 113);
INSERT INTO `admin_sys_role_menu` VALUES (106, 114);
INSERT INTO `admin_sys_role_menu` VALUES (106, 115);
INSERT INTO `admin_sys_role_menu` VALUES (106, 500);
INSERT INTO `admin_sys_role_menu` VALUES (106, 501);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1000);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1001);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1002);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1003);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1004);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1005);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1006);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1007);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1008);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1009);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1010);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1011);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1012);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1013);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1014);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1015);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1016);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1017);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1018);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1019);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1020);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1021);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1022);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1023);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1024);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1025);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1026);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1027);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1028);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1029);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1030);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1031);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1032);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1033);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1034);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1035);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1036);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1037);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1038);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1039);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1040);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1041);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1042);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1043);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1044);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1045);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1046);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1047);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1048);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1049);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1050);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1051);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1052);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1053);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1054);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1055);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1056);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1057);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1058);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1059);
INSERT INTO `admin_sys_role_menu` VALUES (106, 1060);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2000);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2001);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2002);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2003);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2004);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2005);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2006);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2007);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2008);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2009);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2015);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2016);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2017);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2018);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2019);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2020);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2024);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2025);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2026);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2027);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2028);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2029);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2030);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2031);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2035);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2037);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2038);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2039);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2040);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2041);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2042);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2208);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2209);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2210);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2211);
INSERT INTO `admin_sys_role_menu` VALUES (106, 2212);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2);
INSERT INTO `admin_sys_role_menu` VALUES (108, 3);
INSERT INTO `admin_sys_role_menu` VALUES (108, 100);
INSERT INTO `admin_sys_role_menu` VALUES (108, 101);
INSERT INTO `admin_sys_role_menu` VALUES (108, 102);
INSERT INTO `admin_sys_role_menu` VALUES (108, 103);
INSERT INTO `admin_sys_role_menu` VALUES (108, 104);
INSERT INTO `admin_sys_role_menu` VALUES (108, 105);
INSERT INTO `admin_sys_role_menu` VALUES (108, 106);
INSERT INTO `admin_sys_role_menu` VALUES (108, 107);
INSERT INTO `admin_sys_role_menu` VALUES (108, 108);
INSERT INTO `admin_sys_role_menu` VALUES (108, 109);
INSERT INTO `admin_sys_role_menu` VALUES (108, 110);
INSERT INTO `admin_sys_role_menu` VALUES (108, 111);
INSERT INTO `admin_sys_role_menu` VALUES (108, 112);
INSERT INTO `admin_sys_role_menu` VALUES (108, 113);
INSERT INTO `admin_sys_role_menu` VALUES (108, 114);
INSERT INTO `admin_sys_role_menu` VALUES (108, 115);
INSERT INTO `admin_sys_role_menu` VALUES (108, 500);
INSERT INTO `admin_sys_role_menu` VALUES (108, 501);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1000);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1001);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1002);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1003);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1004);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1005);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1006);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1007);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1008);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1009);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1010);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1011);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1012);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1013);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1014);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1015);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1016);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1017);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1018);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1019);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1020);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1021);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1022);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1023);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1024);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1025);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1026);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1027);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1028);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1029);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1030);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1031);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1032);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1033);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1034);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1035);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1036);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1037);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1038);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1039);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1040);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1041);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1042);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1043);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1044);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1045);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1046);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1047);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1048);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1049);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1050);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1051);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1052);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1053);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1054);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1055);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1056);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1057);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1058);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1059);
INSERT INTO `admin_sys_role_menu` VALUES (108, 1060);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2000);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2001);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2002);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2003);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2004);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2005);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2006);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2007);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2008);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2009);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2010);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2011);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2012);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2013);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2014);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2015);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2016);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2017);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2018);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2019);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2020);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2024);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2025);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2026);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2027);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2028);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2029);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2030);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2031);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2035);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2037);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2038);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2039);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2040);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2041);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2042);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2208);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2209);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2210);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2211);
INSERT INTO `admin_sys_role_menu` VALUES (108, 2212);
INSERT INTO `admin_sys_role_menu` VALUES (109, 2021);
INSERT INTO `admin_sys_role_menu` VALUES (109, 2022);
INSERT INTO `admin_sys_role_menu` VALUES (109, 2023);
INSERT INTO `admin_sys_role_menu` VALUES (109, 2032);
INSERT INTO `admin_sys_role_menu` VALUES (109, 2033);
INSERT INTO `admin_sys_role_menu` VALUES (109, 2035);

-- ----------------------------
-- Table structure for admin_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_user`;
CREATE TABLE `admin_sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `login_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录账号',
  `user_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '用户昵称',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '手机号码',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '头像路径',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '密码',
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '盐加密',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '最后登陆IP',
  `login_date` datetime(0) NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 105 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_user
-- ----------------------------
INSERT INTO `admin_sys_user` VALUES (1, 103, 'admin', 'mallplus管理员', '00', '951449465@qq.com', '15888888888', '0', 'http://yjlive160322.oss-cn-beijing.aliyuncs.com/web-20200114-042f7c8678064ced9039039c17ec4eb0.blob', '29c67a30398638269fe600f73a054934', '111111', '0', '0', '111.59.18.31', '2020-07-27 14:26:26', 'admin', '2018-03-16 11:33:00', 'ry', '2020-07-27 14:26:25', '管理员');
INSERT INTO `admin_sys_user` VALUES (2, 105, 'ry', '管理员', '00', '95144@qq.com', '15666666666', '0', 'http://yjlive160322.oss-cn-beijing.aliyuncs.com/web-20200319-550208f3978446f58c7580a137477571.blob', 'a479759386bdcfe22a7e2d5b74e065c6', '8e6798', '0', '0', '113.128.85.30', '2020-07-27 00:16:28', 'admin', '2018-03-16 11:33:00', 'ry', '2020-07-27 00:16:28', '测试员');
INSERT INTO `admin_sys_user` VALUES (100, 103, 'chen', '星', '00', '18874859685@163.com', '18874859685', '0', '', '0d8a8c4aebf92701ba6e6a8ba598c1b3', '236d88', '0', '2', '', '2019-09-28 13:53:30', 'admin', '2019-09-28 13:51:43', 'ry', '2020-03-06 23:33:43', '');
INSERT INTO `admin_sys_user` VALUES (101, 213, 'admin123', 'admin', '00', 'abc@163.com', '13433944755', '0', '', '3238b199a5d360dffc55483be2379811', '250ea1', '0', '2', '171.221.146.228', '2020-07-04 14:44:07', 'admin', '2020-06-02 17:04:10', 'admin', '2020-07-04 14:44:07', '');
INSERT INTO `admin_sys_user` VALUES (102, 213, '123456', 'lishuazhifu', '00', '285058481@qq.com', '15392120042', '0', '', '2d3d5f6ff49171a76a4c9984f84e87de', '6b3e85', '0', '2', '110.87.117.196', '2020-06-09 16:20:26', 'admin', '2020-06-09 16:19:26', 'admin', '2020-06-09 19:00:08', '');
INSERT INTO `admin_sys_user` VALUES (103, 213, 'scc', 'scc', '00', '903377566@qq.com', '18064099052', '0', '', 'de703ee07454d9df93cbcb40cf63708e', '2c7738', '0', '2', '59.175.75.208', '2020-06-20 10:39:33', 'admin', '2020-06-20 10:39:08', '', '2020-06-20 10:39:33', NULL);
INSERT INTO `admin_sys_user` VALUES (104, 221, 'admin88888', 'madan', '00', '11111@160.com', '15888888881', '0', '', '1e30cbaa4b847eead97280ebddd6a866', '17d760', '0', '2', '', NULL, 'admin123', '2020-06-21 10:23:08', '', NULL, NULL);

-- ----------------------------
-- Table structure for admin_sys_user_online
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_user_online`;
CREATE TABLE `admin_sys_user_online`  (
  `sessionId` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户会话id',
  `login_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录账号',
  `dept_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '部门名称',
  `ipaddr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '操作系统',
  `status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '在线状态on_line在线off_line离线',
  `start_timestamp` datetime(0) NULL DEFAULT NULL COMMENT 'session创建时间',
  `last_access_time` datetime(0) NULL DEFAULT NULL COMMENT 'session最后访问时间',
  `expire_time` int(11) NULL DEFAULT 0 COMMENT '超时时间，单位为分钟',
  PRIMARY KEY (`sessionId`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '在线用户记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_user_online
-- ----------------------------
INSERT INTO `admin_sys_user_online` VALUES ('cb713f50-351d-40a5-888f-58cbe9f2a6f4', 'admin', NULL, '116.230.117.204', 'XX XX', 'Chrome 8', 'Windows 10', 'on_line', '2020-07-27 14:10:22', '2020-07-27 14:13:22', 1800000);

-- ----------------------------
-- Table structure for admin_sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_user_post`;
CREATE TABLE `admin_sys_user_post`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`, `post_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户与岗位关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_user_post
-- ----------------------------
INSERT INTO `admin_sys_user_post` VALUES (1, 1);
INSERT INTO `admin_sys_user_post` VALUES (2, 2);
INSERT INTO `admin_sys_user_post` VALUES (100, 4);
INSERT INTO `admin_sys_user_post` VALUES (101, 1);
INSERT INTO `admin_sys_user_post` VALUES (101, 2);
INSERT INTO `admin_sys_user_post` VALUES (101, 3);
INSERT INTO `admin_sys_user_post` VALUES (101, 4);
INSERT INTO `admin_sys_user_post` VALUES (101, 5);
INSERT INTO `admin_sys_user_post` VALUES (102, 4);
INSERT INTO `admin_sys_user_post` VALUES (103, 2);
INSERT INTO `admin_sys_user_post` VALUES (104, 2);

-- ----------------------------
-- Table structure for admin_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `admin_sys_user_role`;
CREATE TABLE `admin_sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin_sys_user_role
-- ----------------------------
INSERT INTO `admin_sys_user_role` VALUES (1, 1);
INSERT INTO `admin_sys_user_role` VALUES (2, 100);
INSERT INTO `admin_sys_user_role` VALUES (100, 1);
INSERT INTO `admin_sys_user_role` VALUES (100, 2);
INSERT INTO `admin_sys_user_role` VALUES (100, 100);
INSERT INTO `admin_sys_user_role` VALUES (101, 1);
INSERT INTO `admin_sys_user_role` VALUES (101, 2);
INSERT INTO `admin_sys_user_role` VALUES (101, 100);
INSERT INTO `admin_sys_user_role` VALUES (101, 101);
INSERT INTO `admin_sys_user_role` VALUES (101, 102);
INSERT INTO `admin_sys_user_role` VALUES (101, 108);
INSERT INTO `admin_sys_user_role` VALUES (102, 100);
INSERT INTO `admin_sys_user_role` VALUES (102, 109);
INSERT INTO `admin_sys_user_role` VALUES (103, 100);
INSERT INTO `admin_sys_user_role` VALUES (104, 100);

SET FOREIGN_KEY_CHECKS = 1;
