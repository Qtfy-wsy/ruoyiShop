package com.ruoyi.setting.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.StringUtils;

/**
 * 支付设置接口-银联支付接口设置实体类
 *
 * @author 伊甸园商城 on 2017/5/17.
 */

public class UnionPaySet {
    /**
     * 商户号
     */
    private String merchantNum;
    /**
     * API-KEY
     */
    private String apiKey;
    /**
     * 是否启用 1启用 0不启用
     */

    private String isUse;

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
     * 清理敏感信息
     */
    public void clearSensitiveInfo() {
        this.merchantNum = "";
        this.apiKey = "";
    }


    /**
     * 检测是否启用 true 启用，反之不启用
     */
    @JsonIgnore
    public boolean checkIsUse() {
        return StringUtils.isEmpty(isUse) || "1".equals(isUse);
    }

}
