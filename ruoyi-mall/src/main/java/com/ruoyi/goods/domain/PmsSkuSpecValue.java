package com.ruoyi.goods.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 单品的规格值对象 pms_sku_spec_value
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public class PmsSkuSpecValue extends BaseEntity {
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
     * 单品id   对应pms_sku表中的id
     */
    @Excel(name = "单品id   对应pms_sku表中的id")
    private String skuId;

    /**
     * 规格id 对应pms_spec表中的id
     */
    @Excel(name = "规格id 对应pms_spec表中的id")
    private Long specId;

    /**
     * 规格值id  对应pms_spec_value 表中的id
     */
    @Excel(name = "规格值id  对应pms_spec_value 表中的id")
    private String specValueId;

    /**
     * 规格值
     */
    @Excel(name = "规格值")
    private String valueRemark;

    /**
     * 删除标记 0 未删除 1 删除  默认0
     */
    private int delFlag;


    private PmsSpec spec;

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

    public PmsSpec getSpec() {
        return spec;
    }

    public void setSpec(PmsSpec spec) {
        this.spec = spec;
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

    public Long getSpecId() {
        return specId;
    }

    public void setSpecId(Long specId) {
        this.specId = specId;
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
    }

    public String getValueRemark() {
        return valueRemark;
    }

    public void setValueRemark(String valueRemark) {
        this.valueRemark = valueRemark;
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
                .append("specId", getSpecId())
                .append("specValueId", getSpecValueId())
                .append("valueRemark", getValueRemark())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
