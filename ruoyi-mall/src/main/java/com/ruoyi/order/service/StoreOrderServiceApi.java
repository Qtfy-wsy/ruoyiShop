package com.ruoyi.order.service;


import com.ruoyi.order.vo.StoreOrderDetail;
import com.ruoyi.order.vo.StoreSubmitOrderParams;
import com.ruoyi.order.vo.SubmitOrderResponse;

import java.util.List;

/**
 * Created by 魔金商城 on 2018/4/10.
 * 门店订单服务接口
 */
public interface StoreOrderServiceApi {

    /**
     * 提交订单
     *
     * @param storeSubmitOrderParams 用户提交的参数
     * @return 返回订单响应实体
     */
    SubmitOrderResponse submitOrder(StoreSubmitOrderParams storeSubmitOrderParams);


    /**
     * 查询门店订单详情（前台用）
     *
     * @param orderId    订单id
     * @param customerId 用户id
     * @return 门店订单详情
     */
    StoreOrderDetail queryStoreOrderDetail(long orderId, long customerId);

    /**
     * 修改订单为已支付状态
     *
     * @param customerId      会员id
     * @param orderCodes      订单code
     * @param isPredepositPay 是否预存款支付 0 否 1 是
     * @param transCode       交易流水号
     * @param channel         支付渠道
     * @return 成功返回1 失败返回0
     */
    int confirmOrderPayed(long customerId, List<String> orderCodes, String isPredepositPay, String transCode, String channel);


    /**
     * 后台修改门店订单状态为已支付
     *
     * @param orderId       门店订单id
     * @param storeId       门店id
     * @param remark        备注
     * @param operationName 操作人账号名
     * @return 1成功，否则失败
     */
    int confirmOrderPayedByBackstage(long orderId, long storeId, String remark, String operationName);

    /**
     * 后台取消门店订单
     *
     * @param orderId       门店订单id
     * @param storeId       门店id
     * @param cancelReason  取消原因
     * @param operationName 操作人账号名
     * @return 1成功，否则失败
     */
    int cancelOrderByBackstage(long orderId, long storeId, String cancelReason, String operationName);

    /**
     * 提货
     *
     * @param orderId       订单id
     * @param storeId       门店id
     * @param writeOffCode  核销码
     * @param operationName 操作人
     * @return 0成功 否则失败 -1订单不存在 -2订单已完成 -3订单未付款或已取消
     */
    int pickUpGoods(long orderId, long storeId, String writeOffCode, String operationName);

}
