package com.ruoyi.store.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 在售门店搜索参数
 *
 * @author SK
 * @since 2018/4/12
 */
@Data
@ApiModel(description = "在售门店搜索参数")
public class OnSaleStoreQueryParam {

    /**
     * 单品id
     */
    @ApiModelProperty(value = "单品id")
    private String skuId;

    /**
     * 市id
     */
    @ApiModelProperty(value = "市id")
    private long cityId;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
}
