package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.setting.domain.LsCity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 运费方式关联的区域对象 pms_shipping_method_area
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class PmsShippingMethodArea extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 运费方式id
     */
    @Excel(name = "运费方式id")
    private Long shippingMethodId;

    /**
     * 模版id
     */
    @Excel(name = "模版id")
    private Long templateId;

    /**
     * 市id
     */
    @Excel(name = "市id")
    private Long cityId;

    private LsCity city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShippingMethodId() {
        return shippingMethodId;
    }

    public void setShippingMethodId(Long shippingMethodId) {
        this.shippingMethodId = shippingMethodId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("shippingMethodId", getShippingMethodId())
                .append("templateId", getTemplateId())
                .append("cityId", getCityId())
                .toString();
    }
}
