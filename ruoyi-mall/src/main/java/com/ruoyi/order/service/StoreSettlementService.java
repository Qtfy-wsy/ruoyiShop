package com.ruoyi.order.service;


import com.ruoyi.order.vo.StoreOrderSettlement;
import com.ruoyi.order.vo.StoreSubmitOrderParams;

/**
 * Created by 魔金商城 on 2018/4/10.
 * 门店结算接口
 */
public interface StoreSettlementService {

    /**
     * 门店订单结算
     *
     * @param storeSubmitOrderParams 门店订单提交的参数
     * @return 返回门店订单结算信息
     */
    StoreOrderSettlement orderSettlement(StoreSubmitOrderParams storeSubmitOrderParams);
}
