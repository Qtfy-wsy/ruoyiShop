package com.ruoyi.member.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户抢购记录对象 ums_member_panic_record
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
public class UmsMemberPanicRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 会员id
     */
    @Excel(name = "会员id")
    private Long customerId;

    /**
     * 抢购id 注意是对应sms_marketing_panicbuy表中的id
     */
    @Excel(name = "抢购id 注意是对应sms_marketing_panicbuy表中的id")
    private Long marketingId;

    /**
     * 用户已经购买抢购的数量
     */
    @Excel(name = "用户已经购买抢购的数量")
    private Long useNum;

    /**
     * 该抢购的会员限购数量
     */
    @Excel(name = "该抢购的会员限购数量")
    private Long panicNum;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private Long orderId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public Long getUseNum() {
        return useNum;
    }

    public void setUseNum(Long useNum) {
        this.useNum = useNum;
    }

    public Long getPanicNum() {
        return panicNum;
    }

    public void setPanicNum(Long panicNum) {
        this.panicNum = panicNum;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customerId", getCustomerId())
                .append("marketingId", getMarketingId())
                .append("useNum", getUseNum())
                .append("panicNum", getPanicNum())
                .append("orderId", getOrderId())
                .toString();
    }
}
