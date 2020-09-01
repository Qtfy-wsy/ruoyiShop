package com.ruoyi.order.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 订单操作日志对象 oms_order_operation_log
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class OmsOrderOperationLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单id 对应oms_order 表中的id
     */
    @Excel(name = "订单id 对应oms_order 表中的id")
    private Long orderId;
    /**
     * 操作说明
     */
    @ApiModelProperty(value = "操作说明")
    private String remark;
    /**
     * 操作类型 1 确认付款 2 修改金额 3 发货  4取消订单 5修改物流单号 6 核销虚拟商品订单
     */
    @Excel(name = "操作类型 1 确认付款 2 修改金额 3 发货  4取消订单 5修改物流单号 6 核销虚拟商品订单")
    private String type;

    /**
     * 操作人
     */
    @Excel(name = "操作人")
    private String operationName;

    /**
     * 构造取消订单的操作日志
     *
     * @param orderId       订单id
     * @param operationName 操作人
     * @return 返回订单操作日志
     */
    public static OmsOrderOperationLog buildForCancelOrdere(long orderId, String operationName) {
        OmsOrderOperationLog orderOperatonLog = new OmsOrderOperationLog();
        orderOperatonLog.orderId = orderId;
        orderOperatonLog.type = "4";
        orderOperatonLog.operationName = operationName;

        return orderOperatonLog;
    }

    /**
     * 构造发货的订单操作日志
     *
     * @param orderId       订单id
     * @param operationName 操作人
     * @return 返回订单操作日志
     */
    public static OmsOrderOperationLog buildForDeliverOrder(long orderId, String operationName) {
        OmsOrderOperationLog orderOperatonLog = new OmsOrderOperationLog();
        orderOperatonLog.orderId = orderId;
        orderOperatonLog.type = "3";
        orderOperatonLog.operationName = operationName;

        return orderOperatonLog;
    }

    /**
     * 构造核销的订单操作日志
     *
     * @param orderId       订单id
     * @param operationName 操作人
     * @return 返回订单操作日志
     */
    public static OmsOrderOperationLog buildForWriteOffOrder(long orderId, String operationName) {
        OmsOrderOperationLog orderOperatonLog = new OmsOrderOperationLog();
        orderOperatonLog.orderId = orderId;
        orderOperatonLog.type = "6";
        orderOperatonLog.operationName = operationName;

        return orderOperatonLog;
    }

    /**
     * 构造修改物流单号的订单操作日志
     *
     * @param orderId       订单id
     * @param operationName 操作人
     * @return 返回订单操作日志
     */
    public static OmsOrderOperationLog buildForChangeExpressNo(long orderId, String operationName) {
        OmsOrderOperationLog orderOperatonLog = new OmsOrderOperationLog();
        orderOperatonLog.orderId = orderId;
        orderOperatonLog.type = "5";
        orderOperatonLog.operationName = operationName;

        return orderOperatonLog;
    }

    /**
     * 构造修改价格的订单操作日志
     *
     * @param orderId       订单id
     * @param remark        备注
     * @param operationName 操作人
     * @return 返回订单操作日志
     */
    public static OmsOrderOperationLog buildForModifyPrice(long orderId, String remark, String operationName) {
        OmsOrderOperationLog orderOperatonLog = new OmsOrderOperationLog();
        orderOperatonLog.orderId = orderId;
        orderOperatonLog.type = "2";
        orderOperatonLog.remark = remark;
        orderOperatonLog.operationName = operationName;

        return orderOperatonLog;
    }

    /**
     * 构造确认付款的订单操作日志
     *
     * @param orderId       订单id
     * @param remark        备注
     * @param operationName 操作人
     * @return 返回订单操作日志
     */
    public static OmsOrderOperationLog buildForConfirmOrder(long orderId, String remark, String operationName) {
        OmsOrderOperationLog orderOperatonLog = new OmsOrderOperationLog();
        orderOperatonLog.orderId = orderId;
        orderOperatonLog.type = "1";
        orderOperatonLog.remark = remark;
        orderOperatonLog.operationName = operationName;

        return orderOperatonLog;
    }
}
