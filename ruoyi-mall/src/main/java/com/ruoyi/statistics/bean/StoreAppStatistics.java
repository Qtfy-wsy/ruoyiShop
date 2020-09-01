package com.ruoyi.statistics.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 魔金商城 on 2019/3/5.
 * 店铺app统计
 */
@Data
@Builder
@ApiModel(description = "店铺app统计")
public class StoreAppStatistics {

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String storeName;

    /**
     * 销售额
     */
    @ApiModelProperty(value = "销售额")
    private BigDecimal saleMoney;

    /**
     * 订单数量
     */
    @ApiModelProperty(value = "订单数量")
    private int orderNum;

    /**
     * 今日付款订单数
     */
    @ApiModelProperty(value = "今日付款订单数")
    private int payedOrderNum;

    /**
     * 今天待发货订单数
     */
    @ApiModelProperty(value = "今天待发货订单数")
    private int toDeliveryOrderNum;

}
