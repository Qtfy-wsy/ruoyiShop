package com.ruoyi.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门店订单数量统计
 *
 * @author SK
 * @since 2018/4/9
 */
@Data
@ApiModel(description = "门店订单数量统计")
public class StoreOrderMessageCount {

    /**
     * 待支付的订单总数
     */
    @ApiModelProperty(value = "待支付的订单总数")
    private int toPayCount;

    /**
     * 待取货的订单总数
     */
    @ApiModelProperty(value = "待取货的订单总数")
    private int toPickUpCount;


    /**
     * 待评论的订单总数
     */
    @ApiModelProperty(value = "待评论的订单总数")
    private int toEvaluateCount;


    /**
     * 构造订单消息总数返回实体
     *
     * @return 返回订单消息总数返回实体
     */
    public static StoreOrderMessageCount build() {
        StoreOrderMessageCount storeOrderMessageCount = new StoreOrderMessageCount();
        return storeOrderMessageCount;
    }

    /**
     * 增加待支付的订单总数
     *
     * @param toPayCount 待支付的订单总数
     * @return 返回当前对象
     */
    public StoreOrderMessageCount addToPayCount(int toPayCount) {
        this.toPayCount = toPayCount;
        return this;
    }


    /**
     * 增加待取货的订单总数
     *
     * @param toPickUpCount 待取货的订单总数
     * @return 返回当前对象
     */
    public StoreOrderMessageCount addToPickUpCount(int toPickUpCount) {
        this.toPickUpCount = toPickUpCount;
        return this;
    }


    /**
     * 增加待评论的订单总数
     *
     * @param toEvaluateCount 待评论的订单总数
     * @return 返回当前对象
     */
    public StoreOrderMessageCount addToEvaluateCount(int toEvaluateCount) {
        this.toEvaluateCount = toEvaluateCount;
        return this;
    }
}
