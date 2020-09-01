package com.ruoyi.order.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PrepayParam implements Serializable {
    /**
     * 预存款支付
     *
     * @param type      1 订单支付 3 门店订单支付
     * @param orderCode 订单code (可能为主订单号也可能为子订单号)
     * @param password  支付密码
     * @return 返回码说明  -1:用户不存在 -2:支付密码错误 -3:没有待支付的订单 -4:用户预存款金额不足 -6:没有设置支付密码 1 成功
     */
    int type;
    String orderCode;
    String password;
}
