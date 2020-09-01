package com.ruoyi.setting.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 社区团购设置对象 s_community_buy_setting
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public class SCommunityBuySetting extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 团长名称
     */
    @Excel(name = "团长名称")
    private String name;

    /**
     * 是否开启审核  0 开启 1 关闭 默认0
     */
    @Excel(name = "是否开启审核  0 开启 1 关闭 默认0")
    private String audit;

    /**
     * 审核结果通知  0 通知 1 不通知 默认0
     */
    @Excel(name = "审核结果通知  0 通知 1 不通知 默认0 ")
    private String smsAuditNotice;

    /**
     * 打款通知 0 通知 1 不通知默认0
     */
    @Excel(name = "打款通知 0 通知 1 不通知默认0 ")
    private String smsPayNotice;

    /**
     * 佣金结算通知 0 通知 1 不通知默认0
     */
    @Excel(name = "佣金结算通知 0 通知 1 不通知默认0 ")
    private String smsCommissionNotice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getSmsAuditNotice() {
        return smsAuditNotice;
    }

    public void setSmsAuditNotice(String smsAuditNotice) {
        this.smsAuditNotice = smsAuditNotice;
    }

    public String getSmsPayNotice() {
        return smsPayNotice;
    }

    public void setSmsPayNotice(String smsPayNotice) {
        this.smsPayNotice = smsPayNotice;
    }

    public String getSmsCommissionNotice() {
        return smsCommissionNotice;
    }

    public void setSmsCommissionNotice(String smsCommissionNotice) {
        this.smsCommissionNotice = smsCommissionNotice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("audit", getAudit())
                .append("smsAuditNotice", getSmsAuditNotice())
                .append("smsPayNotice", getSmsPayNotice())
                .append("smsCommissionNotice", getSmsCommissionNotice())
                .toString();
    }
}
