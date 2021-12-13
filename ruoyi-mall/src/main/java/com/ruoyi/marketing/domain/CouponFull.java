package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 优惠券满减实体类
 *
 * @author 伊甸园商城 on 2017/6/1.
 */
@Data
@ApiModel(description = "优惠券满减实体")
public class CouponFull {
    /**
     * 主键id
     */
    @ApiModelProperty(value = "主键id")
    private long id;
    /**
     * 优惠券id
     */
    @ApiModelProperty(value = "优惠券id")
    private long couponId;
    /**
     * 满多少钱
     */
    @ApiModelProperty(value = "满多少钱")
    private BigDecimal fullPrice;
    /**
     * 减多少钱
     */
    @ApiModelProperty(value = "减多少钱")
    private BigDecimal price;
}
