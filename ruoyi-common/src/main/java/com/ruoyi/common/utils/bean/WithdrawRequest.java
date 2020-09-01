package com.ruoyi.common.utils.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 魔金商城 on 18/2/7.
 * 提现请求实体
 */
@Data
public class WithdrawRequest {

    /**
     * 交流流水号
     */
    private String tradeNo;

    /**
     * 支付包账户
     */
    private String account;

    /**
     * 支付包实名认证姓名
     */
    private String name;

    /**
     * 提现金额
     */
    private BigDecimal money;
}
