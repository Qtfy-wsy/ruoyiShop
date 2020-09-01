package com.ruoyi.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayParam implements Serializable {
    /**
     * @param type      支付类型 1 订单支付 3 门店订单支付
     * @param orderCode 订单code
     * @param orderType 订单类型
     * @param orderId   订单id
     */
    String orderType;
    String orderId;
    int type;
    String orderCode;
}
