package com.ruoyi.goods.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.CustomPriceDeserializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 单品起批价格标对象 pms_sku_batch
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Data
public class PmsSkuBatch extends BaseEntity {
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
     * 起批量（前闭后开）
     */
    @Excel(name = "起批量", readConverterExp = "前=闭后开")
    private Integer batchNum;

    /**
     * 起批价格
     */
    @Excel(name = "起批价格")
    private BigDecimal batchPrice;
    /**
     * 划线价格
     */
    @JsonDeserialize(using = CustomPriceDeserializer.class)
    @ApiModelProperty(value = "划线价格")
    private BigDecimal oldBatchPrice;

    /**
     * 批发区间
     */
    private String batchInterval;

    /**
     * 删除标记 0未删除 1删除 默认0
     */
    private int delFlag;

    /**
     * 设置skuId和spuId
     *
     * @param skuId 单品id
     * @param spuId 商品id
     */
    public void setSkuIdAndSpuId(String skuId, long spuId) {
        this.skuId = skuId;
        this.spuId = spuId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PmsSkuBatch skuBatch = (PmsSkuBatch) o;
        return batchNum == skuBatch.batchNum;
    }

    @Override
    public int hashCode() {
        return batchNum;
    }


}
