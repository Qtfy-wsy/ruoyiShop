package com.ruoyi.order.service;


import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.vo.ApplyReturnParams;

import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 17/11/14.
 * 退单服务接口
 */
public interface BackOrderServiceApi {


    /**
     * 申请退款
     *
     * @param customerId 用户id
     * @param orderId    订单id
     * @param reason     退款原因
     * @param desc       退款说明
     * @return -1 订单状态错误  成功>0  失败= 0
     */
    int applyRefundOrder(long customerId, long orderId, String reason, String desc);

    /**
     * 众筹失败，订单自动申请退款
     *
     * @param customerId 用户id
     * @param orderId    订单id
     * @param reason     退款原因
     * @param desc       退款说明
     * @return -1 订单状态错误  成功>0  失败= 0
     */
    int crowdFundingFailApplyRefundOrder(long customerId, long orderId, String reason, String desc);

    /**
     * 查询订单详情(给申请退货使用)
     *
     * @param customerId 会员id
     * @param orderId    订单id
     * @return 返回订单详情
     */
    OmsOrder queryOrderForReturn(long customerId, long orderId);

    /**
     * 申请退货
     *
     * @param applyReturnParams 退货请求
     * @return -1  0 失败  成功1 -2 超过可以退货的时间
     */
    int applyReturnOrder(ApplyReturnParams applyReturnParams);

    /**
     * 同意退款
     *
     * @param backOrderId 退单id
     * @param storeId     店铺id
     * @param message     消息
     * @return 成功返回1  失败返回0  当前退单状态不对 返回-1
     */
    int agreeToRefund(long backOrderId, long storeId, String message);

    /**
     * 同意确认退货
     *
     * @param backOrderId 退单id
     * @param storeId     店铺id
     * @param message     留言
     * @param money       实退金额
     * @return 成功返回1 失败返回0 当前退单状态不对 返回-1
     */
    int confirmReturn(long backOrderId, long storeId, String message, BigDecimal money);
}
