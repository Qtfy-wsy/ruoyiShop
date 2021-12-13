package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 优惠券直降实体类
 *
 * @author 伊甸园商城 on 2017/6/1.
 */
@Data
@ApiModel(description = "品牌实体")
public class CouponFall {
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
     * 直降金额
     */
    @ApiModelProperty(value = "直降金额")
    private BigDecimal price;
}
