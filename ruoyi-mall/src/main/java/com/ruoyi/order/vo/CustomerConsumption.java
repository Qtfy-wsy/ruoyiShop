package com.ruoyi.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 魔金商城 on 18/2/5.
 * 会员消费额统计
 */
@Data
@ApiModel(description = "会员消费额统计")
public class CustomerConsumption {

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
     * 消费额
     */
    @ApiModelProperty(value = "消费额")
    private BigDecimal consumption;


}
