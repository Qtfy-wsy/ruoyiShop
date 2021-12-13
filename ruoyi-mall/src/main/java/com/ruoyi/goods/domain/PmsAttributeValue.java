package com.ruoyi.goods.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.StringUtils;

/**
 * 属性值对象 pms_attribute_value
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsAttributeValue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private String id;

    /**
     * 属性id
     */
    @Excel(name = "属性id")
    private String attributeId;

    /**
     * 类型id
     */
    @Excel(name = "类型id")
    private long typeId;

    /**
     * 属性值
     */
    @Excel(name = "属性值")
    private String value;

    /**
     * 删除标记 0 未删除 1 删除 默认0
     */
    private Long delFlag;


    /**
     * 设置自定义id 和 属性id
     *
     * @param attributeId 属性id
     * @param index       索引
     */
    public void setCustomIdAndAttributeId(String attributeId, int index, long typeId) {
        this.attributeId = attributeId;
        this.typeId = typeId;
        setCustomId(index);
    }

    /**
     * 设置自定义id
     *
     * @param index 索引
     */
    public void setCustomId(int index) {
        if (!hasId()) {
            this.id = String.valueOf(System.currentTimeMillis()) + this.attributeId + index;
        }
    }

    /**
     * 判断是否已经存在主键
     *
     * @return 存在返回true  不存在返回false
     */
    @JsonIgnore
    public boolean hasId() {
        return !StringUtils.isEmpty(this.id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Long delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("attributeId", getAttributeId())
                .append("typeId", getTypeId())
                .append("value", getValue())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
