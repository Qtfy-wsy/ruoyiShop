package com.ruoyi.order.service.impl;


import com.ruoyi.order.service.StoreSettlementService;
import com.ruoyi.order.service.StoreShoppingCartServiceApi;
import com.ruoyi.order.vo.StoreOrderSettlement;
import com.ruoyi.order.vo.StoreShoppingCartResponse;
import com.ruoyi.order.vo.StoreStoreSettlement;
import com.ruoyi.order.vo.StoreSubmitOrderParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by 魔金商城 on 2018/4/10.
 * 门店结算
 */
@Slf4j
@Service
public class StoreSettlementServiceImpl implements StoreSettlementService {

    /**
     * 注入门店购物车服务接口
     */
    @Autowired
    private StoreShoppingCartServiceApi storeShoppingCartServiceApi;


    @Override
    public StoreOrderSettlement orderSettlement(StoreSubmitOrderParams storeSubmitOrderParams) {

        log.debug("orderSettlement and storeSubmitOrderParams:{}", storeSubmitOrderParams);

        return StoreOrderSettlement.build(getStoreStoreSettlements(storeSubmitOrderParams), storeSubmitOrderParams.getIds(), storeSubmitOrderParams.getReservationIds());
    }


    /**
     * 获得每个店铺的结算信息
     *
     * @param storeSubmitOrderParams 门店订单提交的参数
     * @return 返回每个店铺的结算信息
     */
    private List<StoreStoreSettlement> getStoreStoreSettlements(StoreSubmitOrderParams storeSubmitOrderParams) {

        log.debug("getStoreStoreSettlements and storeSubmitOrderParams:{} ", storeSubmitOrderParams);

        // 获得结算信息
        List<StoreStoreSettlement> storeStoreSettlements = convertToStoreStoreSettlements(getStoreShoppingCartResponse(storeSubmitOrderParams));

        if (CollectionUtils.isEmpty(storeStoreSettlements)) {
            log.error("storeStoreSettlements is empty.....");
            return Collections.emptyList();
        }

        return storeStoreSettlements;
    }


    /**
     * 获得门店购物车信息
     *
     * @param storeSubmitOrderParams 门店订单提交的参数
     * @return 返回门店购物车信息
     */
    private List<StoreShoppingCartResponse> getStoreShoppingCartResponse(StoreSubmitOrderParams storeSubmitOrderParams) {

        // 购物车购买
        if (ArrayUtils.isNotEmpty(storeSubmitOrderParams.getIds())) {
            return storeShoppingCartServiceApi.queryShoppingCartsByIds(storeSubmitOrderParams.getIds(), storeSubmitOrderParams.getCustomerId());
        }

        // 预约下单
        if (ArrayUtils.isNotEmpty(storeSubmitOrderParams.getReservationIds())) {
            return storeShoppingCartServiceApi.queryShoppingCartsByReservationIds(storeSubmitOrderParams.getReservationIds(), storeSubmitOrderParams.getCustomerId(), storeSubmitOrderParams.getStoreInfos().get(0).getStoreId());
        }

        return Collections.emptyList();
    }


    /**
     * 生成门店结算对象
     *
     * @param storeShoppingCartResponses 门店购物响应实体集合
     * @return 返回门店结算对象
     */
    private List<StoreStoreSettlement> convertToStoreStoreSettlements(List<StoreShoppingCartResponse> storeShoppingCartResponses) {
        if (CollectionUtils.isEmpty(storeShoppingCartResponses)) {
            return Collections.emptyList();
        }
        return storeShoppingCartResponses.stream().map(StoreStoreSettlement::build).collect(Collectors.toList());
    }
}
