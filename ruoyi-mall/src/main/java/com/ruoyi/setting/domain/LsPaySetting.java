package com.ruoyi.setting.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 支付设置对象 ls_pay_setting
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public class LsPaySetting extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 支付方式 1:支付宝 2:微信 3:银联 4:预存款 5:微信APP支付 6:微信小程序支付
     */
    @Excel(name = "支付方式 1:支付宝 2:微信 3:银联 4:预存款 5:微信APP支付 6:微信小程序支付")
    private String codetype;

    /**
     * 字段名称
     */
    @Excel(name = "字段名称")
    private String columnName;

    /**
     * 字段值
     */
    @Excel(name = "字段值")
    private String columnValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodetype() {
        return codetype;
    }

    public void setCodetype(String codetype) {
        this.codetype = codetype;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("codetype", getCodetype())
                .append("columnName", getColumnName())
                .append("columnValue", getColumnValue())
                .toString();
    }
}
