package com.ruoyi.common.utils.bean;

import lombok.Data;

/**
 * 支付后返回信息实体类
 */
@Data
public class OrderInfoAfterPay {

    /**
     * 订单code
     */
    private String orderCode;

    /**
     * 判断支付是否成功
     */
    private boolean isSuccess = false;

    /**
     * 支付类型 1:订单支付  2:预存款充值
     */
    private int type;

    /**
     * 交易流水号
     */
    private String transCode;

}
