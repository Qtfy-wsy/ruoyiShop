package com.ruoyi.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by 伊甸园商城 on 18/2/2.
 * 会员下单量
 */
@Data
@ApiModel(description = "会员下单量")
public class CustomerOrderAmount {

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;

    /**
     * 会员名称
     */
    @ApiModelProperty(value = "会员名称")
    private String customerName;

    /**
     * 会员下单量
     */
    @ApiModelProperty(value = "会员下单量")
    private int orderAmount;

}
