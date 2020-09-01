package com.ruoyi.common.utils.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 待付订单信息
 */
@Data
public class OrderInfoForPay {

    /**
     * 订单code（或主订单code,必传）
     */
    private String orderCode;

    /**
     * 单品名称（必传）
     */
    private String goodsName;

    /**
     * 单品ID 扫码支付必传（微信支付）
     */
    private long goodsId;

    /**
     * 订单的最终价格 (用户实际付款的金额,必传)
     */
    private BigDecimal price;

    /**
     * 支付类型 1:订单支付  2:预存款充值(必传)
     */
    private int type;

}
