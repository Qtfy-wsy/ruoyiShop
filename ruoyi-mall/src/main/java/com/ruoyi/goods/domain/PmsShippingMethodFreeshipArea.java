package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.setting.domain.LsCity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 运费方式包邮关联的区域对象 pms_shipping_method_freeship_area
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Data
public class PmsShippingMethodFreeshipArea extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 运费模版包邮id
     */
    @Excel(name = "运费模版包邮id")
    private Long shippingMethodFreeshipId;

    /**
     * 运费模版id
     */
    @Excel(name = "运费模版id")
    private Long templateId;

    /**
     * 市id
     */
    @Excel(name = "市id")
    private Long cityId;

    /**
     * 市信息
     */
    @ApiModelProperty(value = "市信息")
    private LsCity city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShippingMethodFreeshipId() {
        return shippingMethodFreeshipId;
    }

    public void setShippingMethodFreeshipId(Long shippingMethodFreeshipId) {
        this.shippingMethodFreeshipId = shippingMethodFreeshipId;
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
                .append("shippingMethodFreeshipId", getShippingMethodFreeshipId())
                .append("templateId", getTemplateId())
                .append("cityId", getCityId())
                .toString();
    }
}
