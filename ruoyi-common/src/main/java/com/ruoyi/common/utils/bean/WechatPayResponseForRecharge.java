package com.ruoyi.common.utils.bean;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 微信支付返回实体（充值）
 */
@Data
public class WechatPayResponseForRecharge {

    /**
     * 订单code
     */
    private String transCode;

    /**
     * 订单总金额
     */
    private BigDecimal money;

    /**
     * 扫码支付url
     */
    private String codeUrl;

    /**
     * mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。
     */
    private String mwebUrl;
}
