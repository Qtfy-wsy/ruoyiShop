package com.ruoyi.setting.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 短信接口设置对象 ls_sms_setting
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Data
public class LsSmsSetting extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * AppSecret
     */
    @Excel(name = "AppSecret")
    private String secret;

    /**
     * 短信接口地址
     */
    @Excel(name = "短信接口地址")
    private String url;

    /**
     * 短信签名
     */
    @Excel(name = "短信签名")
    private String sign;

    /**
     * 模板id
     */
    @Excel(name = "模板id")
    private String templateId;

    /**
     * 核销门店订单的模版id
     */
    @Excel(name = "核销门店订单的模版id")
    private String writeoffTemplateId;

    /**
     * 虚拟订单核销的模版id
     */
    @Excel(name = "虚拟订单核销的模版id")
    private String virtualOrderTemplateId;

    /**
     * AppKey
     */
    @Excel(name = "AppKey")
    private String key;

    /**
     * 社区团购审核结果通知模版id
     */
    @Excel(name = "社区团购审核结果通知模版id")
    private String auditTemplateId;

    /**
     * 社区团购佣金结算模版id
     */
    @Excel(name = "社区团购佣金结算模版id")
    private String settlementTemplateId;

    /**
     * 社区团购提现打款模版id
     */
    @Excel(name = "社区团购提现打款模版id")
    private String withdrawTemplateId;
    @ApiModelProperty("启用状态")
    private Boolean status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getWriteoffTemplateId() {
        return writeoffTemplateId;
    }

    public void setWriteoffTemplateId(String writeoffTemplateId) {
        this.writeoffTemplateId = writeoffTemplateId;
    }

    public String getVirtualOrderTemplateId() {
        return virtualOrderTemplateId;
    }

    public void setVirtualOrderTemplateId(String virtualOrderTemplateId) {
        this.virtualOrderTemplateId = virtualOrderTemplateId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAuditTemplateId() {
        return auditTemplateId;
    }

    public void setAuditTemplateId(String auditTemplateId) {
        this.auditTemplateId = auditTemplateId;
    }

    public String getSettlementTemplateId() {
        return settlementTemplateId;
    }

    public void setSettlementTemplateId(String settlementTemplateId) {
        this.settlementTemplateId = settlementTemplateId;
    }

    public String getWithdrawTemplateId() {
        return withdrawTemplateId;
    }

    public void setWithdrawTemplateId(String withdrawTemplateId) {
        this.withdrawTemplateId = withdrawTemplateId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("secret", getSecret())
                .append("url", getUrl())
                .append("sign", getSign())
                .append("templateId", getTemplateId())
                .append("writeoffTemplateId", getWriteoffTemplateId())
                .append("virtualOrderTemplateId", getVirtualOrderTemplateId())
                .append("key", getKey())
                .append("auditTemplateId", getAuditTemplateId())
                .append("settlementTemplateId", getSettlementTemplateId())
                .append("withdrawTemplateId", getWithdrawTemplateId())
                .toString();
    }
}
