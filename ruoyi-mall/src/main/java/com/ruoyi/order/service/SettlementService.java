package com.ruoyi.order.service;


import com.ruoyi.order.vo.OrderSettlement;
import com.ruoyi.order.vo.OrderSettlementRequest;

/**
 * Created by 伊甸园商城 on 17/11/2.
 * 结算服务接口
 */
public interface SettlementService {

    /**
     * 订单结算
     *
     * @param orderSettlementRequest 订单结算请求参数
     * @return 返回订单结算实体
     */
    OrderSettlement orderSettlement(OrderSettlementRequest orderSettlementRequest);

}
