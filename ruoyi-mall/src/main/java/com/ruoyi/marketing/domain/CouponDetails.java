package com.ruoyi.marketing.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 优惠券详情实体类
 *
 * @author 魔金商城 on 2017/6/6.
 */
@Data
@ApiModel(description = "优惠券详情实体")
public class CouponDetails {
    /**
     * 优惠券信息实体类
     */
    @ApiModelProperty(value = "优惠券信息实体类")
    private Coupon coupon;
    /**
     * 优惠券券码实体类
     */
    @ApiModelProperty(value = "优惠券券码实体类")
    private List<CouponCode> couponCode;

    public CouponDetails(Coupon coupon, List<CouponCode> couponCode) {
        super();
        this.coupon = coupon;
        this.couponCode = couponCode;
    }
}
