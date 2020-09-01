package com.ruoyi.setting.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

/**
 * 支付设置接口-支付宝支付接口设置实体类
 *
 * @author 魔金商城 on 2017/5/17.
 */

public class AliPaySet {
    /**
     * appid
     */

    private String appId;
    /**
     * 开发者私钥，由开发者自己生成
     */

    private String appPrivateKey;
    /**
     * 支付宝公钥，由支付宝生成
     */

    private String alipayPublicKey;
    /**
     * 是否启用 1启用 0不启用
     */

    private String isUse;

    /**
     * 清理敏感信息
     */
    public void clearSensitiveInfo() {
        this.appId = "";
        this.appPrivateKey = "";
        this.alipayPublicKey = "";
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppPrivateKey() {
        return appPrivateKey;
    }

    public void setAppPrivateKey(String appPrivateKey) {
        this.appPrivateKey = appPrivateKey;
    }

    public String getAlipayPublicKey() {
        return alipayPublicKey;
    }

    public void setAlipayPublicKey(String alipayPublicKey) {
        this.alipayPublicKey = alipayPublicKey;
    }

    public String getIsUse() {
        return isUse;
    }

    public void setIsUse(String isUse) {
        this.isUse = isUse;
    }

    /**
     * 检测是否启用 true 启用，反之不启用
     */
    @JsonIgnore
    public boolean checkIsUse() {
        return StringUtils.isEmpty(isUse) || "1".equals(isUse);
    }
}
