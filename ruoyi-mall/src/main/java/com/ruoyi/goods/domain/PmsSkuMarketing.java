package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 单品和营销的关联对象 pms_sku_marketing
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsSkuMarketing extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 单品id
     */
    @Excel(name = "单品id")
    private String skuid;

    /**
     * 营销id
     */
    @Excel(name = "营销id")
    private Long marketingId;

    /**
     * 促销价格 （在众筹的时候使用，众筹价格）
     */
    @Excel(name = "促销价格 ", readConverterExp = "在=众筹的时候使用，众筹价格")
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuid() {
        return skuid;
    }

    public void setSkuid(String skuid) {
        this.skuid = skuid;
    }

    public Long getMarketingId() {
        return marketingId;
    }

    public void setMarketingId(Long marketingId) {
        this.marketingId = marketingId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("skuid", getSkuid())
                .append("marketingId", getMarketingId())
                .append("price", getPrice())
                .toString();
    }
}
