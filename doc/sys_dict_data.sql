/*
 Navicat Premium Data Transfer

 Source Server         : 51wangshi
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : 51wangshi.com:3306
 Source Schema         : mallplusbak

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 28/07/2020 08:31:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `dict_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int(4) NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 225 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '男', '0', 'sys_user_sex', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别男');
INSERT INTO `sys_dict_data` VALUES (2, 2, '女', '1', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别女');
INSERT INTO `sys_dict_data` VALUES (3, 3, '未知', '2', 'sys_user_sex', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '性别未知');
INSERT INTO `sys_dict_data` VALUES (4, 1, '显示', '0', 'sys_show_hide', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '显示菜单');
INSERT INTO `sys_dict_data` VALUES (5, 2, '隐藏', '1', 'sys_show_hide', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '隐藏菜单');
INSERT INTO `sys_dict_data` VALUES (6, 1, '正常', '0', 'sys_normal_disable', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (7, 2, '停用', '1', 'sys_normal_disable', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (8, 1, '正常', '0', 'sys_job_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (9, 2, '暂停', '1', 'sys_job_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (10, 1, '默认', 'DEFAULT', 'sys_job_group', '', '', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '默认分组');
INSERT INTO `sys_dict_data` VALUES (11, 2, '系统', 'SYSTEM', 'sys_job_group', '', '', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统分组');
INSERT INTO `sys_dict_data` VALUES (12, 1, '是', 'Y', 'sys_yes_no', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认是');
INSERT INTO `sys_dict_data` VALUES (13, 2, '否', 'N', 'sys_yes_no', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统默认否');
INSERT INTO `sys_dict_data` VALUES (14, 1, '通知', '1', 'sys_notice_type', '', 'warning', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知');
INSERT INTO `sys_dict_data` VALUES (15, 2, '公告', '2', 'sys_notice_type', '', 'success', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '公告');
INSERT INTO `sys_dict_data` VALUES (16, 1, '正常', '0', 'sys_notice_status', '', 'primary', 'Y', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (17, 2, '关闭', '1', 'sys_notice_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '关闭状态');
INSERT INTO `sys_dict_data` VALUES (18, 1, '新增', '1', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '新增操作');
INSERT INTO `sys_dict_data` VALUES (19, 2, '修改', '2', 'sys_oper_type', '', 'info', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '修改操作');
INSERT INTO `sys_dict_data` VALUES (20, 3, '删除', '3', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '删除操作');
INSERT INTO `sys_dict_data` VALUES (21, 4, '授权', '4', 'sys_oper_type', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '授权操作');
INSERT INTO `sys_dict_data` VALUES (22, 5, '导出', '5', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导出操作');
INSERT INTO `sys_dict_data` VALUES (23, 6, '导入', '6', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '导入操作');
INSERT INTO `sys_dict_data` VALUES (24, 7, '强退', '7', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '强退操作');
INSERT INTO `sys_dict_data` VALUES (25, 8, '生成代码', '8', 'sys_oper_type', '', 'warning', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '生成操作');
INSERT INTO `sys_dict_data` VALUES (26, 9, '清空数据', '9', 'sys_oper_type', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '清空操作');
INSERT INTO `sys_dict_data` VALUES (27, 1, '成功', '0', 'sys_common_status', '', 'primary', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '正常状态');
INSERT INTO `sys_dict_data` VALUES (28, 2, '失败', '1', 'sys_common_status', '', 'danger', 'N', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '停用状态');
INSERT INTO `sys_dict_data` VALUES (100, 0, '申请中', '0', 'audit_thress', NULL, NULL, 'N', '0', 'admin', '2020-07-20 10:49:35', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (101, 0, '通过', '1', 'audit_thress', NULL, NULL, 'N', '0', 'admin', '2020-07-20 10:50:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (102, 0, '拒绝', '2', 'audit_thress', NULL, NULL, 'N', '0', 'admin', '2020-07-20 10:50:33', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (103, 0, 'no', '0', 'ye_no01', NULL, NULL, 'N', '0', 'admin', '2020-07-24 08:34:12', '', NULL, 'ye_no01');
INSERT INTO `sys_dict_data` VALUES (104, 0, 'yes', '1', 'ye_no01', NULL, NULL, 'N', '0', 'admin', '2020-07-24 08:34:23', '', NULL, 'ye_no01');
INSERT INTO `sys_dict_data` VALUES (105, 0, 'xiajia', '0', 'goods_publish', NULL, NULL, 'N', '0', 'admin', '2020-07-24 08:39:59', '', NULL, '商品上架状态 0 下架  1上架 2违规下架 默认0');
INSERT INTO `sys_dict_data` VALUES (106, 1, 'shangjia', '1', 'goods_publish', NULL, NULL, 'N', '0', 'admin', '2020-07-24 08:40:17', '', NULL, '商品上架状态 0 下架  1上架 2违规下架 默认0');
INSERT INTO `sys_dict_data` VALUES (107, 2, '违规下架', '2', 'goods_publish', NULL, NULL, 'N', '0', 'admin', '2020-07-24 08:40:27', '', NULL, '商品上架状态 0 下架  1上架 2违规下架 默认0');
INSERT INTO `sys_dict_data` VALUES (108, 0, '退款', '1', 'after_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 10:57:45', '', NULL, '1 退款 2 退货');
INSERT INTO `sys_dict_data` VALUES (109, 1, '退货', '2', 'after_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 10:57:54', '', NULL, '1 退款 2 退货');
INSERT INTO `sys_dict_data` VALUES (110, 0, ' 快递返回', '1', 'back_ways', NULL, NULL, 'N', '0', 'admin', '2020-07-24 10:59:02', '', NULL, '返回方式 1 快递返回 目前只有快递返回 （退货的时候用户给商城寄送商品）');
INSERT INTO `sys_dict_data` VALUES (111, 0, '退款申请', '1', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (112, 1, '退款成功（商家同意退款）', '2', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (113, 2, '退款拒绝 （商家拒绝退款）', '3', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (114, 3, '退货申请（用户发起退货请求）', '4', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (115, 4, '退货拒绝（商家拒绝退货）', '5', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (116, 5, '退货审核通过等待用户填写物流', '6', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (117, 6, '待收货  （用户已经寄回商品，等待商家收货确认）', '7', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (118, 7, '退货完成（商家收货并且同意退款给用户）', '8', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (119, 8, '退货失败（商家不同意退款）', '9', 'afterSaleStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:03:45', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (120, 0, '用户', '1', 'operate_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:11:56', '', NULL, '操作人类型 1 用户  2 商家');
INSERT INTO `sys_dict_data` VALUES (121, 1, '商家', '2', 'operate_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:12:06', '', NULL, '操作人类型 1 用户  2 商家');
INSERT INTO `sys_dict_data` VALUES (122, 0, '进', '0', 'bill_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:14:11', '', NULL, '账单进出类型 0 进 1 出');
INSERT INTO `sys_dict_data` VALUES (123, 1, '出', '1', 'bill_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:14:19', '', NULL, '账单进出类型 0 进 1 出');
INSERT INTO `sys_dict_data` VALUES (124, 0, '确认收货', '1', 'bill_records_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:15:13', '', NULL, '账单记录类型 1 确认收货 2 退款订单 3 退货订单佣金  4 订单关闭（只支付定金） 5 推广订单提成 6 退货订单');
INSERT INTO `sys_dict_data` VALUES (125, 1, '退款订单', '2', 'bill_records_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:15:13', '', NULL, '账单记录类型 1 确认收货 2 退款订单 3 退货订单佣金  4 订单关闭（只支付定金） 5 推广订单提成 6 退货订单');
INSERT INTO `sys_dict_data` VALUES (126, 2, '退货订单佣金', '3', 'bill_records_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:15:13', '', NULL, '账单记录类型 1 确认收货 2 退款订单 3 退货订单佣金  4 订单关闭（只支付定金） 5 推广订单提成 6 退货订单');
INSERT INTO `sys_dict_data` VALUES (127, 3, '订单关闭（只支付定金）', '4', 'bill_records_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:15:13', '', NULL, '账单记录类型 1 确认收货 2 退款订单 3 退货订单佣金  4 订单关闭（只支付定金） 5 推广订单提成 6 退货订单');
INSERT INTO `sys_dict_data` VALUES (128, 4, '推广订单提成', '5', 'bill_records_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:15:13', '', NULL, '账单记录类型 1 确认收货 2 退款订单 3 退货订单佣金  4 订单关闭（只支付定金） 5 推广订单提成 6 退货订单');
INSERT INTO `sys_dict_data` VALUES (129, 5, '退货订单', '6', 'bill_records_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:15:13', '', NULL, '账单记录类型 1 确认收货 2 退款订单 3 退货订单佣金  4 订单关闭（只支付定金） 5 推广订单提成 6 退货订单');
INSERT INTO `sys_dict_data` VALUES (130, 0, '未结算', '0', 'jiesuan_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:19:44', '', NULL, '结算状态 0 未结算 1 已结算 默认0 ');
INSERT INTO `sys_dict_data` VALUES (131, 1, '已结算', '1', 'jiesuan_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:19:53', '', NULL, '结算状态 0 未结算 1 已结算 默认0 ');
INSERT INTO `sys_dict_data` VALUES (132, 0, '待付款', '1', 'order_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:21:46', '', NULL, '订单状态');
INSERT INTO `sys_dict_data` VALUES (133, 1, '待发货', '2', 'order_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:21:46', '', NULL, '订单状态');
INSERT INTO `sys_dict_data` VALUES (134, 2, '待收货', '3', 'order_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:21:46', '', NULL, '订单状态');
INSERT INTO `sys_dict_data` VALUES (135, 3, '已完成', '4', 'order_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:21:46', '', NULL, '订单状态');
INSERT INTO `sys_dict_data` VALUES (136, 4, '退款通过', '6', 'order_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:21:46', '', NULL, '订单状态');
INSERT INTO `sys_dict_data` VALUES (137, 5, '退货通过', '7', 'order_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:21:46', '', NULL, '订单状态');
INSERT INTO `sys_dict_data` VALUES (138, 6, '取消订单', '5', 'order_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:21:46', '', NULL, '订单状态');
INSERT INTO `sys_dict_data` VALUES (139, 0, '未评价', '0', 'pingjia_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:24:58', '', NULL, '评价状态  0 未评价 1 已评价  默认为0 未评价');
INSERT INTO `sys_dict_data` VALUES (140, 1, '已评价', '1', 'pingjia_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:25:07', '', NULL, '评价状态  0 未评价 1 已评价  默认为0 未评价');
INSERT INTO `sys_dict_data` VALUES (141, 0, '普通订单', '0', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (142, 8, '社区团购订单', '8', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (143, 1, '定金预售订单', '1', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (144, 2, '全款预售订单', '2', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (145, 3, '拼团订单', '3', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (146, 4, '众筹全款', '4', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (147, 5, '众筹1元', '5', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (148, 6, '众筹无回报', '6', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (149, 7, '虚拟商品订单', '7', 'order_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:26:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (150, 0, '第一阶段未支付', '0', 'pre_sale_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:31:13', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (151, 1, '第一阶段已支付第二阶段未支付', '1', 'pre_sale_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:31:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (152, 2, '第二阶段已支付', '2', 'pre_sale_status', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:31:41', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (153, 0, '在线支付', '0', 'pay_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:58:16', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (154, 1, '货到付款', '1', 'pay_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 11:58:25', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (155, 0, 'PC', '1', 'order_source', NULL, NULL, 'N', '0', 'admin', '2020-07-24 12:00:21', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (156, 1, 'H5', '2', 'order_source', NULL, NULL, 'N', '0', 'admin', '2020-07-24 12:00:32', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (157, 2, 'APP', '3', 'order_source', NULL, NULL, 'N', '0', 'admin', '2020-07-24 12:00:39', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (158, 0, '买家', '0', 'fright_price', NULL, NULL, 'N', '0', 'admin', '2020-07-24 12:10:21', 'admin', '2020-07-24 12:10:59', '买家');
INSERT INTO `sys_dict_data` VALUES (159, 1, '商家', '1', 'fright_price', NULL, NULL, 'N', '0', 'admin', '2020-07-24 12:10:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (160, 0, '按件', '0', 'fright_ways', NULL, NULL, 'N', '0', 'admin', '2020-07-24 12:11:46', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (161, 1, '按重量', '1', 'fright_ways', NULL, NULL, 'N', '0', 'admin', '2020-07-24 12:12:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (162, 0, '确认付款', '1', 'order_operate_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:08:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (163, 6, '核销虚拟商品订单', '6', 'order_operate_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:08:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (164, 2, '修改金额', '2', 'order_operate_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:08:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (165, 3, '发货', '3', 'order_operate_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:08:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (166, 4, '取消订单', '4', 'order_operate_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:08:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (167, 5, '修改物流单号', '5', 'order_operate_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:08:03', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (168, 0, ' 门店订单', '1', 'pay_trans_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:12:32', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (169, 1, '订单', '2', 'pay_trans_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:12:43', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (170, 2, ' 预存款', '3', 'pay_trans_type', NULL, NULL, 'N', '0', 'admin', '2020-07-24 14:12:58', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (171, 0, '保密', '0', 'genderData', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:33:54', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (172, 1, '男', '1', 'genderData', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:34:07', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (173, 2, '女', '2', 'genderData', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:34:15', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (174, 0, '正常', '1', 'userStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:39:01', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (175, 1, '冻结', '2', 'userStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:39:10', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (176, 0, 'PC', '1', 'userRegSource', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:42:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (177, 0, 'APP', '2', 'userRegSource', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:42:16', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (178, 2, 'H5', '3', 'userRegSource', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:42:26', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (179, 4, '管理员后台新增', '4', 'userRegSource', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:42:38', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (180, 0, '收入', '1', 'jifen_type', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:44:18', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (181, 2, '支出', '2', 'jifen_type', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:44:29', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (182, 0, '订单使用', '1', 'jifen_source', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:46:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (183, 1, '订单取消', '2', 'jifen_source', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:46:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (184, 7, '评论', '7', 'jifen_source', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:46:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (185, 2, '操作员修改', '3', 'jifen_source', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:46:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (186, 3, '签到', '4', 'jifen_source', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:46:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (187, 4, '积分商城使用', '5', 'jifen_source', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:46:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (188, 5, '邮箱验证', '6', 'jifen_source', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:46:19', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (189, 0, '在线充值', '1', 'blance_trans_type', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:53:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (190, 2, '订单消费', '2', 'blance_trans_type', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:53:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (191, 3, '订单退款', '3', 'blance_trans_type', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:53:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (192, 4, '管理员增加', '4', 'blance_trans_type', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:53:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (193, 5, '管理员减少', '5', 'blance_trans_type', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:53:12', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (194, 0, '未支付', '0', 'chongzhi_pay_status', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:55:52', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (195, 0, '支付成功', '1', 'chongzhi_pay_status', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:56:00', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (196, 0, '申请', '0', 'withdraw_status', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:57:17', 'admin', '2020-07-25 08:57:54', NULL);
INSERT INTO `sys_dict_data` VALUES (197, 0, '审核通过', '1', 'withdraw_status', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:57:25', '', NULL, '状态 0 申请  1 审核通过 2 拒绝 3 已打款');
INSERT INTO `sys_dict_data` VALUES (198, 0, '拒绝', '2', 'withdraw_status', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:57:34', '', NULL, '状态 0 申请  1 审核通过 2 拒绝 3 已打款');
INSERT INTO `sys_dict_data` VALUES (199, 3, '已打款', '3', 'withdraw_status', NULL, NULL, 'N', '0', 'admin', '2020-07-25 08:57:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (200, 0, '填写资料中', '0', 'storeStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:48:04', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (201, 1, ' 店铺审核中', '1', 'storeStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:48:16', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (202, 2, '审核通过', '2', 'storeStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:48:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (203, 3, '审核不通过', '3', 'storeStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:48:47', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (204, 4, '店铺关闭', '4', 'storeStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:48:57', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (205, 0, '普通店铺', '0', 'storeType', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:49:55', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (206, 1, '加盟', '1', 'storeType', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:50:06', '', NULL, '1 加盟 2连锁');
INSERT INTO `sys_dict_data` VALUES (207, 2, '连锁', '2', 'storeType', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:50:13', '', NULL, '1 加盟 2连锁');
INSERT INTO `sys_dict_data` VALUES (208, 0, '不需要发票', '0', 'invoceType', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:51:13', '', NULL, '0 不需要发票 1普通发票 2增值税发票 默认0');
INSERT INTO `sys_dict_data` VALUES (209, 1, '普通发票', '1', 'invoceType', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:51:24', '', NULL, '1普通发票 2增值税发票 默认0');
INSERT INTO `sys_dict_data` VALUES (210, 0, '增值税发票', '2', 'invoceType', NULL, NULL, 'N', '0', 'admin', '2020-07-27 19:56:52', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (211, 0, '确认付款 ', '1', 'storeOperateType', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:11:11', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (212, 1, '核销（提货） ', '2', 'storeOperateType', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:11:31', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (213, 2, '取消订单 ', '3', 'storeOperateType', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:11:40', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (214, 0, '未领取 ', '0', 'redStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:14:21', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (215, 1, '已领取未使用', '1', 'redStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:14:34', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (216, 2, '已使用', '2', 'redStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:14:47', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (217, 3, '已失效', '3', 'redStatus', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:14:57', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (218, 0, '1分', '1', 'descScop', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:18:57', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (219, 0, '2分', '2', 'descScop', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:19:05', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (220, 2, '3分', '3', 'descScop', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:19:15', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (221, 3, '4分', '4', 'descScop', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:19:24', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (222, 5, '5分', '5', 'descScop', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:19:33', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (223, 0, '企业', '1', 'invoice_head', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:23:15', '', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (224, 2, '个人', '2', 'invoice_head', NULL, NULL, 'N', '0', 'admin', '2020-07-28 08:23:25', '', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `dict_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE INDEX `dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 136 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '用户性别', 'sys_user_sex', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '用户性别列表');
INSERT INTO `sys_dict_type` VALUES (2, '菜单状态', 'sys_show_hide', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '菜单状态列表');
INSERT INTO `sys_dict_type` VALUES (3, '系统开关', 'sys_normal_disable', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统开关列表');
INSERT INTO `sys_dict_type` VALUES (4, '任务状态', 'sys_job_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务状态列表');
INSERT INTO `sys_dict_type` VALUES (5, '任务分组', 'sys_job_group', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '任务分组列表');
INSERT INTO `sys_dict_type` VALUES (6, '系统是否', 'sys_yes_no', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '系统是否列表');
INSERT INTO `sys_dict_type` VALUES (7, '通知类型', 'sys_notice_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知类型列表');
INSERT INTO `sys_dict_type` VALUES (8, '通知状态', 'sys_notice_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '通知状态列表');
INSERT INTO `sys_dict_type` VALUES (9, '操作类型', 'sys_oper_type', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '操作类型列表');
INSERT INTO `sys_dict_type` VALUES (10, '系统状态', 'sys_common_status', '0', 'admin', '2018-03-16 11:33:00', 'ry', '2018-03-16 11:33:00', '登录状态列表');
INSERT INTO `sys_dict_type` VALUES (100, '审核状态', 'audit_thress', '0', 'admin', '2020-07-20 10:49:03', '', NULL, '状态  0 申请中  1通过 2 拒绝');
INSERT INTO `sys_dict_type` VALUES (101, '是否状态', 'ye_no01', '0', 'admin', '2020-07-24 08:33:53', '', NULL, '0 否 1是');
INSERT INTO `sys_dict_type` VALUES (102, '商品上架状态', 'goods_publish', '0', 'admin', '2020-07-24 08:39:02', '', NULL, '商品上架状态 0 下架  1上架 2违规下架 默认0');
INSERT INTO `sys_dict_type` VALUES (103, '是否是虚拟商品', 'is_virture', '0', 'admin', '2020-07-24 08:41:21', '', NULL, '是否是虚拟商品 0 否 1 是默认0');
INSERT INTO `sys_dict_type` VALUES (104, '售后方式', 'after_type', '0', 'admin', '2020-07-24 10:57:24', '', NULL, '1 退款 2 退货');
INSERT INTO `sys_dict_type` VALUES (105, '返回方式', 'back_ways', '0', 'admin', '2020-07-24 10:58:44', '', NULL, '返回方式 1 快递返回 目前只有快递返回 （退货的时候用户给商城寄送商品）');
INSERT INTO `sys_dict_type` VALUES (106, '售后状态', 'afterSaleStatus', '0', 'admin', '2020-07-24 11:00:03', '', NULL, '退款／退货状态  1:退款申请 （用户发送退款请求）2:退款成功（商家同意退款）3:退款拒绝 （商家拒绝退款）4:退货申请 （用户发起退货请求）5:退货拒绝   （商家拒绝退货）6:退货审核通过等待用户填写物流（商家审核通过，等待用户寄回商品）7: 待收货  （用户已经寄回商品，等待商家收货确认）8：退货完成（商家收货并且同意退款给用户）9:退货失败（商家不同意退款）');
INSERT INTO `sys_dict_type` VALUES (107, '操作人类型', 'operate_type', '0', 'admin', '2020-07-24 11:11:38', '', NULL, '操作人类型 1 用户  2 商家');
INSERT INTO `sys_dict_type` VALUES (108, '账单进出类型', 'bill_type', '0', 'admin', '2020-07-24 11:13:54', '', NULL, '账单进出类型 0 进 1 出');
INSERT INTO `sys_dict_type` VALUES (109, '账单记录类型', 'bill_records_type', '0', 'admin', '2020-07-24 11:14:57', '', NULL, '账单记录类型 1 确认收货 2 退款订单 3 退货订单佣金  4 订单关闭（只支付定金） 5 推广订单提成 6 退货订单');
INSERT INTO `sys_dict_type` VALUES (110, '结算状态', 'jiesuan_type', '0', 'admin', '2020-07-24 11:19:27', '', NULL, '结算状态 0 未结算 1 已结算 默认0 ');
INSERT INTO `sys_dict_type` VALUES (111, '订单状态', 'order_status', '0', 'admin', '2020-07-24 11:21:28', '', NULL, '订单状态');
INSERT INTO `sys_dict_type` VALUES (112, '评价状态', 'pingjia_status', '0', 'admin', '2020-07-24 11:24:46', '', NULL, '评价状态  0 未评价 1 已评价  默认为0 未评价');
INSERT INTO `sys_dict_type` VALUES (113, '订单类型', 'order_type', '0', 'admin', '2020-07-24 11:25:53', '', NULL, '订单类型 ');
INSERT INTO `sys_dict_type` VALUES (114, '预售订单状态', 'pre_sale_status', '0', 'admin', '2020-07-24 11:30:10', '', NULL, '预售订单状态   ');
INSERT INTO `sys_dict_type` VALUES (115, '支付类型', 'pay_type', '0', 'admin', '2020-07-24 11:58:03', '', NULL, '支付类型  0在线支付  1货到付款 ');
INSERT INTO `sys_dict_type` VALUES (116, '订单来源', 'order_source', '0', 'admin', '2020-07-24 12:00:02', '', NULL, '订单来源 1pc  2 h5   3 app');
INSERT INTO `sys_dict_type` VALUES (117, '谁承担运费', 'fright_price', '0', 'admin', '2020-07-24 12:10:06', '', NULL, '谁承担运费 0 买家 1 商家 默认 0 买家');
INSERT INTO `sys_dict_type` VALUES (118, '计价方式', 'fright_ways', '0', 'admin', '2020-07-24 12:11:33', '', NULL, '计价方式 0 按件  1 按重量  默认0 ');
INSERT INTO `sys_dict_type` VALUES (119, '订单操作类型', 'order_operate_type', '0', 'admin', '2020-07-24 14:07:43', '', NULL, '操作类型 1 确认付款 2 修改金额 3 发货  4取消订单 5修改物流单号 6 核销虚拟商品订单');
INSERT INTO `sys_dict_type` VALUES (120, '支付流水类型', 'pay_trans_type', '0', 'admin', '2020-07-24 14:12:15', '', NULL, '类型 1 门店订单 2 订单 3 预存款');
INSERT INTO `sys_dict_type` VALUES (121, '性别', 'genderData', '0', 'admin', '2020-07-25 08:33:37', '', NULL, '性别  0 保密 1男 2女 默认0');
INSERT INTO `sys_dict_type` VALUES (122, '用户状态', 'userStatus', '0', 'admin', '2020-07-25 08:38:54', '', NULL, '1正常 2冻结');
INSERT INTO `sys_dict_type` VALUES (123, '用户注册来源', 'userRegSource', '0', 'admin', '2020-07-25 08:41:51', '', NULL, '用户注册来源');
INSERT INTO `sys_dict_type` VALUES (124, '积分类型', 'jifen_type', '0', 'admin', '2020-07-25 08:44:09', '', NULL, '积分类型 1 收入 2支出');
INSERT INTO `sys_dict_type` VALUES (125, '积分纪录来源类型', 'jifen_source', '0', 'admin', '2020-07-25 08:46:01', '', NULL, '积分纪录来源类型1 订单使用2 订单取消3 操作员修改4 签到5 积分商城使用6 邮箱验证 7 评论');
INSERT INTO `sys_dict_type` VALUES (126, '预存款交易类型', 'blance_trans_type', '0', 'admin', '2020-07-25 08:52:57', '', NULL, '交易类型  1:在线充值  2:订单消费  3:订单退款 4:管理员增加 5:管理员减少');
INSERT INTO `sys_dict_type` VALUES (127, '充值支付状态', 'chongzhi_pay_status', '0', 'admin', '2020-07-25 08:55:39', '', NULL, '充值支付状态 0 未支付 1 支付成功');
INSERT INTO `sys_dict_type` VALUES (128, '提现状态', 'withdraw_status', '0', 'admin', '2020-07-25 08:57:01', '', NULL, '状态 0 申请  1 审核通过 2 拒绝 3 已打款');
INSERT INTO `sys_dict_type` VALUES (129, '店铺状态', 'storeStatus', '0', 'admin', '2020-07-27 19:47:45', '', NULL, '店铺状态0 填写资料中 1 店铺审核中2 审核通过3 审核不通过4 店铺关闭');
INSERT INTO `sys_dict_type` VALUES (130, '店铺类型', 'storeType', '0', 'admin', '2020-07-27 19:49:37', '', NULL, '店铺类型 0 普通店铺 1 加盟 2连锁');
INSERT INTO `sys_dict_type` VALUES (131, '发票类型', 'invoceType', '0', 'admin', '2020-07-27 19:51:00', '', NULL, '发票类型  0 不需要发票 1普通发票 2增值税发票 默认0');
INSERT INTO `sys_dict_type` VALUES (132, '门店操作类型', 'storeOperateType', '0', 'admin', '2020-07-28 08:10:55', '', NULL, '操作类型 1 确认付款 2 核销（提货） 3 取消订单 ');
INSERT INTO `sys_dict_type` VALUES (133, '红包卷状态', 'redStatus', '0', 'admin', '2020-07-28 08:14:07', '', NULL, '红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效');
INSERT INTO `sys_dict_type` VALUES (134, '描述相符', 'descScop', '0', 'admin', '2020-07-28 08:18:43', '', NULL, '描述相符 评分1到5分');
INSERT INTO `sys_dict_type` VALUES (135, '抬头类型', 'invoice_head', '0', 'admin', '2020-07-28 08:23:03', '', NULL, '抬头类型  1 企业  2 个人');

SET FOREIGN_KEY_CHECKS = 1;
