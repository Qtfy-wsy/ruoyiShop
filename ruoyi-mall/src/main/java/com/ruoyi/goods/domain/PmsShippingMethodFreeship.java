package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 运费模版包邮对象 pms_shipping_method_freeship
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class PmsShippingMethodFreeship extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 运费模版id
     */
    @Excel(name = "运费模版id")
    private Long templateId;

    /**
     * 包邮类型 0 件数  1 金额
     */
    @Excel(name = "包邮类型 0 件数  1 金额")
    private int type;

    /**
     * 包邮件数
     */
    @Excel(name = "包邮件数")
    private Long num;

    /**
     * 包邮的金额
     */
    @Excel(name = "包邮的金额")
    private BigDecimal money;

    /**
     * 包邮运费方式区域
     */

    private List<PmsShippingMethodFreeshipArea> shippingMethodFreeShipAreas;


    /**
     * 获得市名称
     *
     * @return 返回市名称
     */

    public String getCityNames() {
        if (CollectionUtils.isEmpty(this.shippingMethodFreeShipAreas)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        shippingMethodFreeShipAreas.stream().forEach(shippingMethodArea -> {
            if (Objects.nonNull(shippingMethodArea.getCity())) {
                sb.append(shippingMethodArea.getCity().getName()).append(",");
            }
        });

        if (StringUtils.isEmpty(sb.toString())) {
            return "";
        }
        return sb.toString().substring(0, sb.toString().lastIndexOf(","));
    }


    /**
     * 设置包邮模版运费区域的id
     */
    public void setShippingMethodFreeShipAreaMethodId() {
        if (CollectionUtils.isEmpty(this.shippingMethodFreeShipAreas)) {
            return;
        }
        this.shippingMethodFreeShipAreas.forEach(shippingMethodFreeShipArea -> {
            shippingMethodFreeShipArea.setTemplateId(this.templateId);
            shippingMethodFreeShipArea.setShippingMethodFreeshipId(this.id);
        });

    }

    public List<PmsShippingMethodFreeshipArea> getShippingMethodFreeShipAreas() {
        return shippingMethodFreeShipAreas;
    }

    public void setShippingMethodFreeShipAreas(List<PmsShippingMethodFreeshipArea> shippingMethodFreeShipAreas) {
        this.shippingMethodFreeShipAreas = shippingMethodFreeShipAreas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("templateId", getTemplateId())
                .append("type", getType())
                .append("num", getNum())
                .append("money", getMoney())
                .toString();
    }
}
