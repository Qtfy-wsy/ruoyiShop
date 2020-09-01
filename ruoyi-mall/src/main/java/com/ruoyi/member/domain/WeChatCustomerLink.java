package com.ruoyi.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.util.CommonConstant;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * 微信用户关联实体类
 */
@Data
public class WeChatCustomerLink implements Serializable {
    /**
     * 主键
     */
    private long id = -1;
    /**
     * 联合登录id
     */
    private String unionId;
    /**
     * 微信用户唯一标识
     */
    private String openId;
    /**
     * 小程序openId
     */
    private String appletOpenId;
    /**
     * 用户id
     */
    private long customerId = CommonConstant.NO_CUSTOMER_ID;

    public WeChatCustomerLink() {
    }

    public WeChatCustomerLink(String openId, String unionId) {
        this.openId = openId;
        this.unionId = unionId;
    }

    /**
     * 是否绑定 绑定返回true
     */
    @JsonIgnore
    public boolean isBind() {
        return this.customerId != CommonConstant.NO_CUSTOMER_ID;
    }

    /**
     * 判断是否有openId
     *
     * @return 有返回true, 否则返回false
     */
    public boolean hasOpenId() {
        return !StringUtils.isEmpty(openId);
    }

    /**
     * 判断是否有小程序openId
     *
     * @return 有返回true, 否则返回false
     */
    public boolean hasAppletOpenId() {
        return !StringUtils.isEmpty(appletOpenId);
    }
}

