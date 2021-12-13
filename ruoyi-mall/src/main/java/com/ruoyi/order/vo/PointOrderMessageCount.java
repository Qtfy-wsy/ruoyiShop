package com.ruoyi.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 积分订单消息总数返回实体
 *
 * @author 伊甸园商城 created on 2020/4/21
 */
@Data
@ApiModel(description = "积分订单消息总数返回实体")
public class PointOrderMessageCount {

    /**
     * 待发货的积分订单总数
     */
    @ApiModelProperty(value = "待发货的订单总数")
    private int toDeliverCount;

    /**
     * 待确认收货的积分订单总数
     */
    @ApiModelProperty(value = "待确认收货的订单总数")
    private int toReceiptCount;

    /**
     * 构造积分订单消息总数返回实体
     *
     * @return 返回积分订单消息总数返回实体
     */
    public static PointOrderMessageCount build() {
        PointOrderMessageCount pointOrderMessageCount = new PointOrderMessageCount();
        return pointOrderMessageCount;
    }

    /**
     * 增加待发货的积分订单总数
     *
     * @param toDeliverCount 待发货的积分订单总数
     * @return 返回当前对象
     */
    public PointOrderMessageCount addToDeliverCount(int toDeliverCount) {
        this.toDeliverCount = toDeliverCount;
        return this;
    }

    /**
     * 增加待确认收货的积分订单总数
     *
     * @param toReceiptCount 待确认收货的积分订单总数
     * @return 返回当前对象
     */
    public PointOrderMessageCount addToReceiptCount(int toReceiptCount) {
        this.toReceiptCount = toReceiptCount;
        return this;
    }

}
