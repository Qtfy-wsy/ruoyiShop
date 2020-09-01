package com.ruoyi.order.vo;

import lombok.Data;

import java.util.List;

/**
 * Created by 魔金商城 on 17/11/13.
 * 确认订单支付请求参数实体
 */
@Data
public class ConfirmOrderParams {

    /**
     * 订单id
     */
    private long id;

    /**
     * 店铺id
     */
    private long storeId;

    /**
     * 操作人
     */
    private String operationName;

    /**
     * 会员id
     */
    private long customerId;

    /**
     * 是否预存款 0 否 1 是
     */
    private int isPredepositPay;

    /**
     * 订单code
     */
    private List<String> orderCodes;

    /**
     * 确认订单支付请求来源 0 用户  1 管理员
     */
    private int source;

    /**
     * 原因
     */
    private String reason;

    /**
     * 交易流水号
     */
    private String transCode;

    /**
     * 支付渠道
     */
    private String channel;

    /**
     * 构造来源用户确认订单支付的请求
     *
     * @param customerId      用户id
     * @param isPredepositPay 是否预存款支付
     * @param orderCodes      订单code
     * @param transCode       交易流水号
     * @param channel         支付渠道
     * @return 返回确认订单支付请求参数
     */
    public static ConfirmOrderParams buildCustomerSource(long customerId, int isPredepositPay, List<String> orderCodes, String transCode, String channel) {
        ConfirmOrderParams confirmOrderParams = new ConfirmOrderParams();
        confirmOrderParams.customerId = customerId;
        confirmOrderParams.isPredepositPay = isPredepositPay;
        confirmOrderParams.orderCodes = orderCodes;
        confirmOrderParams.source = 0;
        confirmOrderParams.transCode = transCode;
        confirmOrderParams.channel = channel;
        return confirmOrderParams;
    }

    /**
     * 构造来源管理员确认订单支付的请求
     *
     * @param id            订单id
     * @param storeId       店铺id
     * @param reason        原因
     * @param operationName 操作人
     * @return 返回确认订单支付请求参数
     */
    public static ConfirmOrderParams buildManagerSource(long id, long storeId, String reason, String operationName) {
        ConfirmOrderParams confirmOrderParams = new ConfirmOrderParams();
        confirmOrderParams.id = id;
        confirmOrderParams.storeId = storeId;
        confirmOrderParams.reason = reason;
        confirmOrderParams.operationName = operationName;
        confirmOrderParams.source = 1;
        return confirmOrderParams;
    }

    /**
     * 判断是否来自用户
     *
     * @return 来自用户返回true
     */
    public boolean isFromCustomer() {
        return this.source == 0;
    }
}
