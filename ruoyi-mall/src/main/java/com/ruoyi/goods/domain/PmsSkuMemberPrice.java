package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 单品的会员价对象 pms_sku_member_price
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public class PmsSkuMemberPrice extends BaseEntity {
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
     * 单品id
     */
    @Excel(name = "单品id")
    private String skuId;

    /**
     * 会员等级id
     */
    @Excel(name = "会员等级id")
    private Long memberLevelId;

    /**
     * 会员价格
     */
    @Excel(name = "会员价格")
    private BigDecimal price;

    /**
     * 删除标记 0未删除1 删除 默认0
     */
    private int delFlag;

    /**
     * 设置单品id和商品id
     *
     * @param skuId 单品id
     * @param spuId 商品id
     */
    public void setSkuIdAndSpuId(String skuId, long spuId) {
        this.skuId = skuId;
        this.spuId = spuId;
    }

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

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public Long getMemberLevelId() {
        return memberLevelId;
    }

    public void setMemberLevelId(Long memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
                .append("skuId", getSkuId())
                .append("memberLevelId", getMemberLevelId())
                .append("price", getPrice())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
