package com.ruoyi.order.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 17/11/7.
 * 单品使用各种促销后的详情
 */
@Data
@ApiModel(description = "订单单品信息实体")
public class SkuPriceDetail {

    /**
     * 促销类型 目前 1 满减 2满折 3 优惠券 4积分 5 管理员修改订单价格 6 红包
     */
    @ApiModelProperty(value = "促销类型 目前 1 满减 2满折 3 优惠券 4积分 5 管理员修改订单价格 6 红包")
    private int type;

    /**
     * 优惠的价格
     */
    @ApiModelProperty(value = "优惠的价格")
    private BigDecimal price;


    public SkuPriceDetail() {
    }

    public SkuPriceDetail(int type, BigDecimal price) {
        this.type = type;
        this.price = price;
    }
}
