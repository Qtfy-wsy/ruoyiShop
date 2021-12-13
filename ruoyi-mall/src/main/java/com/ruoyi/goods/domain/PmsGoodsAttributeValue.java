package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 商品下面的属性值对象 pms_goods_attribute_value
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsGoodsAttributeValue extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 商品id
     */
    @Excel(name = "商品id")
    private Long spuId;

    /**
     * 属性id
     */
    @Excel(name = "属性id")
    private String attributeId;

    /**
     * 属性名称
     */
    @Excel(name = "属性名称")
    private String attributeName;

    /**
     * 属性值id
     */
    @Excel(name = "属性值id")
    private String attributeValueId;

    /**
     * 属性值
     */
    @Excel(name = "属性值")
    private String attributeValue;

    /**
     * 删除标记 0 未删除 1删除 默认0
     */
    private int delFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSpuId() {
        return spuId;
    }

    public void setSpuId(Long spuId) {
        this.spuId = spuId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValueId() {
        return attributeValueId;
    }

    public void setAttributeValueId(String attributeValueId) {
        this.attributeValueId = attributeValueId;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("spuId", getSpuId())
                .append("attributeId", getAttributeId())
                .append("attributeName", getAttributeName())
                .append("attributeValueId", getAttributeValueId())
                .append("attributeValue", getAttributeValue())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
