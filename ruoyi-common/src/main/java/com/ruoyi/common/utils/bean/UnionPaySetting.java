package com.ruoyi.common.utils.bean;

import lombok.Data;

/**
 * 银联支付设置实体
 */
@Data
public class UnionPaySetting {
    /**
     * 商户号
     */
    private String merchantNum;
    /**
     * 前台回调地址
     */
    private String beforeCallbackUrl;
    /**
     * 后台回调地址
     */
    private String backCallbackUrl;

}
