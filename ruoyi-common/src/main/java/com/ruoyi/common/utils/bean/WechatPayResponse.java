package com.ruoyi.common.utils.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 微信支付返回实体（订单支付）
 */
@Data
@ApiModel(description = "微信支付返回实体")
public class WechatPayResponse {

    /**
     * 订单code
     */
    @ApiModelProperty(value = "订单code")
    private String orderCode;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderMoney;

    /**
     * 扫码支付url
     */
    @ApiModelProperty(value = "扫码支付url")
    private String codeUrl;

    /**
     * mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。
     */
    @ApiModelProperty(value = "mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。")
    private String mwebUrl;
}
