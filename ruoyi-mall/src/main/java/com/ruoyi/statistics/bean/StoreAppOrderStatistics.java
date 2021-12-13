package com.ruoyi.statistics.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by 伊甸园商城 on 2019/4/2.
 * 商家app端统计
 */
@Data
@Builder
@ApiModel(description = "商家app端统计")
public class StoreAppOrderStatistics {

    /**
     * 待付款订单总数
     */
    @ApiModelProperty(value = "待付款订单总数")
    private int toPayed;

    /**
     * 待发货订单总数
     */
    @ApiModelProperty(value = "待发货订单总数")
    private int toDelivered;

    /**
     * 处理中的退单数量
     */
    @ApiModelProperty(value = "处理中的退单数量")
    private int processingBackOrder;
}
