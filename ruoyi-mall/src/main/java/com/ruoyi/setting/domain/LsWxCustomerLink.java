package com.ruoyi.setting.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 微信登录和商城用户的关联对象 ls_wx_customer_link
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
public class LsWxCustomerLink extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 微信的联合登录id
     */
    @Excel(name = "微信的联合登录id")
    private String wxUnionId;

    /**
     * 用户id
     */
    @Excel(name = "用户id")
    private Long customerId;

    /**
     * 微信的openid
     */
    @Excel(name = "微信的openid")
    private String wxOpenId;

    /**
     * 小程序的openid
     */
    @Excel(name = "小程序的openid")
    private String wxAppletOpenId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getWxAppletOpenId() {
        return wxAppletOpenId;
    }

    public void setWxAppletOpenId(String wxAppletOpenId) {
        this.wxAppletOpenId = wxAppletOpenId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("wxUnionId", getWxUnionId())
                .append("customerId", getCustomerId())
                .append("wxOpenId", getWxOpenId())
                .append("wxAppletOpenId", getWxAppletOpenId())
                .toString();
    }
}
