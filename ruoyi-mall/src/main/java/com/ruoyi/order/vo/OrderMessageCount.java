package com.ruoyi.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 17/11/13.
 * 订单消息总数返回实体
 */
@Data
@ApiModel(description = "订单消息总数返回实体")
public class OrderMessageCount {

    /**
     * 待支付的订单总数
     */
    @ApiModelProperty(value = "待支付的订单总数")
    private int toPayCount;

    /**
     * 待发货的订单总数
     */
    @ApiModelProperty(value = "待发货的订单总数")
    private int toDeliverCount;

    /**
     * 待确认收货的订单总数
     */
    @ApiModelProperty(value = "待确认收货的订单总数")
    private int toReceiptCount;

    /**
     * 待评论的订单总数
     */
    @ApiModelProperty(value = "待评论的订单总数")
    private int toEvaluateCount;

    /**
     * 退款/退货状态中退单的数量
     */
    @ApiModelProperty(value = "退款/退货状态中退单的数量")
    private int backOrderCount;

    /**
     * 待支付的门店订单总数
     */
    @ApiModelProperty(value = "待支付的门店订单总数")
    private int toStorePayCount;

    /**
     * 待取货的门店订单总数
     */
    @ApiModelProperty(value = "待取货的门店订单总数")
    private int toStorePickUpCount;


    /**
     * 待评论的门店订单总数
     */
    @ApiModelProperty(value = "待评论的门店订单总数")
    private int toStoreEvaluateCount;

    /**
     * 购物车数量
     */
    @ApiModelProperty(value = "购物车数量")
    private int cartNum;

    /**
     * 优惠券数量
     */
    @ApiModelProperty(value = "优惠券数量")
    private int couponNum;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal blance=new BigDecimal(0);

    private String commissonMoney; // 累计佣金

    private String  withdrawMoney;// 累计提现
    /**
     * 增加余额
     *
     * @param commissonMoney 购物车数量
     * @return 返回当前对象
     */
    public OrderMessageCount addToCommissonMoney(String commissonMoney) {
        this.commissonMoney = commissonMoney;
        return this;
    }
    /**
     * 增加余额
     *
     * @param withdrawMoney 购物车数量
     * @return 返回当前对象
     */
    public OrderMessageCount addToWithdrawMoney(String withdrawMoney) {
        this.withdrawMoney = withdrawMoney;
        return this;
    }
    /**
     * 增加余额
     *
     * @param blance 购物车数量
     * @return 返回当前对象
     */
    public OrderMessageCount addToBlance(BigDecimal blance) {
        this.blance = blance;
        return this;
    }

    /**
     * 增加购物车数量
     *
     * @param cartNum 购物车数量
     * @return 返回当前对象
     */
    public OrderMessageCount addToCartNum(int cartNum) {
        this.cartNum = cartNum;
        return this;
    }

    /**
     * 增加优惠券数量
     *
     * @param couponNum 优惠券数量
     * @return 优惠券数量
     */
    public OrderMessageCount addToCouponNum(int couponNum) {
        this.couponNum = couponNum;
        return this;
    }

    /**
     * 构造订单消息总数返回实体
     *
     * @return 返回订单消息总数返回实体
     */
    public static OrderMessageCount build() {
        OrderMessageCount orderMessageCount = new OrderMessageCount();
        return orderMessageCount;
    }

    /**
     * 增加待支付的订单总数
     *
     * @param toPayCount 待支付的订单总数
     * @return 返回当前对象
     */
    public OrderMessageCount addToPayCount(int toPayCount) {
        this.toPayCount = toPayCount;
        return this;
    }

    /**
     * 增加 退款/退货状态中退单的数量
     *
     * @param backOrderCount 退款/退货状态中退单的数量
     * @return 返回当前对象
     */
    public OrderMessageCount addBackOrderCount(int backOrderCount) {
        this.backOrderCount = backOrderCount;
        return this;
    }

    /**
     * 增加待发货的订单总数
     *
     * @param toDeliverCount 待发货的订单总数
     * @return 返回当前对象
     */
    public OrderMessageCount addToDeliverCount(int toDeliverCount) {
        this.toDeliverCount = toDeliverCount;
        return this;
    }

    /**
     * 增加待确认收货的订单总数
     *
     * @param toReceiptCount 待确认收货的订单总数
     * @return 返回当前对象
     */
    public OrderMessageCount addToReceiptCount(int toReceiptCount) {
        this.toReceiptCount = toReceiptCount;
        return this;
    }

    /**
     * 增加待评论的订单总数
     *
     * @param toEvaluateCount 待评论的订单总数
     * @return 返回当前对象
     */
    public OrderMessageCount addToEvaluateCount(int toEvaluateCount) {
        this.toEvaluateCount = toEvaluateCount;
        return this;
    }

    /**
     * 增加店铺待评价的订单总数
     *
     * @param toStorePayCount 待评价的订单总数
     * @return 返回当前对象
     */
    public OrderMessageCount addToStorePayCount(int toStorePayCount) {
        this.toStorePayCount = toStorePayCount;
        return this;
    }

    /**
     * 增加门店待取货的订单总数
     *
     * @param toStorePickUpCount 待取货的订单总数
     * @return 返回当前对象
     */
    public OrderMessageCount addToStorePickUpCount(int toStorePickUpCount) {
        this.toStorePickUpCount = toStorePickUpCount;
        return this;
    }

    /**
     * 增加门店待评价的订单总数
     *
     * @param toStoreEvaluateCount 待评价的订单总数
     * @return 返回当前对象
     */
    public OrderMessageCount addToStoreEvaluateCount(int toStoreEvaluateCount) {
        this.toStoreEvaluateCount = toStoreEvaluateCount;
        return this;
    }
}
