package com.ruoyi.store.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 门店红包卷吗对象 t_store_red_envelope_code
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public class TStoreRedEnvelopeCode extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 门店红包id
     */
    @Excel(name = "门店红包id")
    private Long redEnvelopeId;

    /**
     * 红包的卷码
     */
    @Excel(name = "红包的卷码")
    private String code;

    /**
     * 领取红包的会员id
     */
    @Excel(name = "领取红包的会员id")
    private Long customerId;

    /**
     * 红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效
     */
    @Excel(name = "红包卷状态  0 未领取 1已领取未使用 2 已使用 3已失效")
    private String status;

    /**
     * 领取红包
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "领取红包", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receiveTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRedEnvelopeId() {
        return redEnvelopeId;
    }

    public void setRedEnvelopeId(Long redEnvelopeId) {
        this.redEnvelopeId = redEnvelopeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("redEnvelopeId", getRedEnvelopeId())
                .append("code", getCode())
                .append("customerId", getCustomerId())
                .append("status", getStatus())
                .append("receiveTime", getReceiveTime())
                .toString();
    }
}
