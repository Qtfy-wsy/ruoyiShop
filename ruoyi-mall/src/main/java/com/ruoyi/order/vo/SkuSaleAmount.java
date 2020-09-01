package com.ruoyi.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 单品销售实体
 */
@Data
@ApiModel(description = "单品销售实体")
public class SkuSaleAmount {

    /**
     * 单品id
     */
    @ApiModelProperty(value = "单品id")
    private String skuId;

    /**
     * 单品名称
     */
    @ApiModelProperty(value = "单品名称")
    private String skuName;

    /**
     * 店铺id
     */
    @ApiModelProperty(value = "店铺id")
    private long storeId;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 销量
     */
    @ApiModelProperty(value = "销量")
    private int salesVolume;

    /**
     * 销售总额
     */
    @ApiModelProperty(value = "销售总额")
    private BigDecimal salesAmount;

}
