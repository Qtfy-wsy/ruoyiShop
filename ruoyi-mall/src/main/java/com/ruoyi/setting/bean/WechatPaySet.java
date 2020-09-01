package com.ruoyi.setting.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

/**
 * 支付设置接口-微信支付接口设置实体类
 *
 * @author 魔金商城 on 2017/5/17.
 */

public class WechatPaySet {
    /**
     * 公众号appId
     */

    private String appId;
    /**
     * AppSecret
     */

    private String appSecret;
    /**
     * 商户号
     */

    private String merchantNum;

    /**
     * API密钥
     */

    private String apiKey;

    /**
     * 微信公众号 登录回调地址
     */
    private String loginNotice;

    /**
     * 是否启用 1启用 0不启用
     */

    private String isUse;

    /**
     * 清理敏感信息
     */
    public void clearSensitiveInfo() {
        this.appId = "";
        this.appSecret = "";
        this.merchantNum = "";
        this.apiKey = "";
    }

    public String getLoginNotice() {
        return loginNotice;
    }

    public void setLoginNotice(String loginNotice) {
        this.loginNotice = loginNotice;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getMerchantNum() {
        return merchantNum;
    }

    public void setMerchantNum(String merchantNum) {
        this.merchantNum = merchantNum;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
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
