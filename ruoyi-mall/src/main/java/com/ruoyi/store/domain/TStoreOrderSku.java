package com.ruoyi.store.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 门店订单单品信息对象 t_store_order_sku
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Data
public class TStoreOrderSku extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 订单id
     */
    @Excel(name = "订单id")
    private Long orderId;

    /**
     * 单品id
     */
    @Excel(name = "单品id")
    private String skuId;

    /**
     * 购买的数量
     */
    @Excel(name = "购买的数量")
    private int num;

    /**
     * 单品的最终价格（原价－优惠价）
     */
    @Excel(name = "单品的最终价格", readConverterExp = "原=价－优惠价")
    private BigDecimal price;

    /**
     * 单品的价格
     */
    @Excel(name = "单品的价格")
    private BigDecimal skuPrice;

    /**
     * 单品的名称
     */
    @Excel(name = "单品的名称")
    private String skuName;

    /**
     * 单品的id
     */
    @Excel(name = "单品的id")
    private String skuNo;

    /**
     * 单品的图片
     */
    @Excel(name = "单品的图片")
    private String skuImage;

    /**
     * 单品的规格
     */
    @Excel(name = "单品的规格")
    private String skuSpecs;

    /**
     * 红包减去的价格
     */
    @Excel(name = "红包减去的价格")
    private BigDecimal redEnvelopePrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getSkuNo() {
        return skuNo;
    }

    public void setSkuNo(String skuNo) {
        this.skuNo = skuNo;
    }

    public String getSkuImage() {
        return skuImage;
    }

    public void setSkuImage(String skuImage) {
        this.skuImage = skuImage;
    }

    public String getSkuSpecs() {
        return skuSpecs;
    }

    public void setSkuSpecs(String skuSpecs) {
        this.skuSpecs = skuSpecs;
    }

    public BigDecimal getRedEnvelopePrice() {
        return redEnvelopePrice;
    }

    public void setRedEnvelopePrice(BigDecimal redEnvelopePrice) {
        this.redEnvelopePrice = redEnvelopePrice;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("orderId", getOrderId())
                .append("skuId", getSkuId())
                .append("num", getNum())
                .append("price", getPrice())
                .append("skuPrice", getSkuPrice())
                .append("skuName", getSkuName())
                .append("skuNo", getSkuNo())
                .append("skuImage", getSkuImage())
                .append("skuSpecs", getSkuSpecs())
                .append("redEnvelopePrice", getRedEnvelopePrice())
                .toString();
    }
}
