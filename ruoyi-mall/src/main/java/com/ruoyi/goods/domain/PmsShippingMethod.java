package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * 运费方式对象 pms_shipping_method
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsShippingMethod extends BaseEntity {
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
     * 首件 或者 首重
     */
    @Excel(name = "首件 或者 首重")
    private Long first;

    /**
     * 首件或者首重的价格
     */
    @Excel(name = "首件或者首重的价格")
    private BigDecimal money;

    /**
     * 增加几件或者几克
     */
    @Excel(name = "增加几件或者几克")
    private Long firstPlu;

    /**
     * 增加的钱
     */
    @Excel(name = "增加的钱")
    private BigDecimal moenyPlu;

    /**
     * 是否默认  0 否 1 是 默认 0
     */
    @Excel(name = "是否默认  0 否 1 是 默认 0 ")
    private String isDefault;


    /**
     * 运费方式的区域
     */
    private String cityNames;


    /**
     * 运费方式的区域id
     */
    private String cityIds;


    /**
     * 运费方式区域
     */
    private List<PmsShippingMethodArea> shippingMethodAreas;

    /**
     * 获得市名称
     *
     * @return 返回市名称
     */
    public String getCityNames() {
        if (CollectionUtils.isEmpty(this.shippingMethodAreas)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();


        shippingMethodAreas.stream().forEach(shippingMethodArea -> {
            if (Objects.nonNull(shippingMethodArea.getCity())) {
                sb.append(shippingMethodArea.getCity().getName()).append(",");
            }
        });

        if (StringUtils.isEmpty(sb.toString())) {
            return "";
        }
        return sb.toString().substring(0, sb.toString().lastIndexOf(","));
    }

    public void setCityNames(String cityNames) {
        this.cityNames = cityNames;
    }

    /**
     * 获得市id
     *
     * @return 返回市id
     */
    public String getCityIds() {
        if (CollectionUtils.isEmpty(this.shippingMethodAreas)) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        shippingMethodAreas.stream().forEach(shippingMethodArea -> sb.append(shippingMethodArea.getCityId()).append(","));
        return sb.toString().substring(0, sb.toString().lastIndexOf(","));
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    /**
     * 设置运费方式区域的运费方式id
     */
    public void setShippingMethodAreaMethodId() {
        if (CollectionUtils.isEmpty(shippingMethodAreas)) {
            return;
        }

        shippingMethodAreas.stream().forEach(shippingMethodArea -> {
            shippingMethodArea.setTemplateId(this.templateId);
            shippingMethodArea.setShippingMethodId(this.id);
        });
    }

    public List<PmsShippingMethodArea> getShippingMethodAreas() {
        return shippingMethodAreas;
    }

    public void setShippingMethodAreas(List<PmsShippingMethodArea> shippingMethodAreas) {
        this.shippingMethodAreas = shippingMethodAreas;
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

    public Long getFirst() {
        return first;
    }

    public void setFirst(Long first) {
        this.first = first;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Long getFirstPlu() {
        return firstPlu;
    }

    public void setFirstPlu(Long firstPlu) {
        this.firstPlu = firstPlu;
    }

    public BigDecimal getMoenyPlu() {
        return moenyPlu;
    }

    public void setMoenyPlu(BigDecimal moenyPlu) {
        this.moenyPlu = moenyPlu;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("templateId", getTemplateId())
                .append("first", getFirst())
                .append("money", getMoney())
                .append("firstPlu", getFirstPlu())
                .append("moenyPlu", getMoenyPlu())
                .append("isDefault", getIsDefault())
                .toString();
    }
}
