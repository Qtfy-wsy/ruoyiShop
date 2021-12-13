package com.ruoyi.statistics.service.impl;


import com.ruoyi.order.mapper.OmsOrderMapper;
import com.ruoyi.order.mapper.OmsOrderSkuMapper;
import com.ruoyi.order.vo.*;
import com.ruoyi.statistics.service.OrderStatisticsService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by 伊甸园商城 on 18/2/2.
 * 订单统计服务接口
 */
@Service
public class OrderStatisticsServiceImpl implements OrderStatisticsService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OrderStatisticsServiceImpl.class);

    /**
     * 注入订单数据库接口
     */
    @Autowired
    private OmsOrderMapper orderMapper;

    /**
     * 注入订单单品数据库接口
     */
    @Autowired
    private OmsOrderSkuMapper orderSkuMapper;


    @Override
    public PageHelper<CustomerOrderAmount> queryCustomerOrderAmounts(PageHelper<CustomerOrderAmount> pageHelper, String startTime, String endTime) {

        logger.debug("queryCustomerOrderAmounts and startTime:{} \r\n endTime:{}", startTime, endTime);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        return pageHelper.setListDates(orderMapper.queryCustomerOrderAmounts(pageHelper.getQueryParams(params, orderMapper.queryCustomerOrderAmountsCount(params))));
    }

    @Override
    public PageHelper<CustomerConsumption> queryCustomerConsumption(PageHelper<CustomerConsumption> pageHelper, String startTime, String endTime) {
        logger.debug("queryCustomerConsumption and pageHelper:{} \r\n startTime:{} \r\n endTime:{}", pageHelper, startTime, endTime);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return pageHelper.setListDates(orderMapper.queryCustomerConsumption(pageHelper.getQueryParams(params, orderMapper.queryCustomerConsumptionCount(params))));
    }

    @Override
    public PageHelper<SkuSaleAmount> querySkuSaleVolume(PageHelper<SkuSaleAmount> pageHelper, String startTime, String endTime, long storeId) {
        logger.debug("querySkuSaleVolume and startTime:{} \r\n endTime:{} \r\n storeId:{}", startTime, endTime, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("storeId", storeId);
        return pageHelper.setListDates(orderSkuMapper.querySkuSaleVolume(pageHelper.getQueryParams(params, orderSkuMapper.querySkuSaleCountByTime(params))));
    }

    @Override
    public PageHelper<SkuSaleAmount> querySkuSaleAmount(PageHelper<SkuSaleAmount> pageHelper, String startTime, String endTime, long storeId) {
        logger.debug("querySkuSaleAmount and startTime:{} \r\n endTime:{} \r\n storeId:{}", startTime, endTime, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        params.put("storeId", storeId);
        return pageHelper.setListDates(orderSkuMapper.querySkuSaleAmount(pageHelper.getQueryParams(params, orderSkuMapper.querySkuSaleCountByTime(params))));
    }


    @Override
    public PageHelper<StoreSaleAmount> queryStoreSaleVolume(PageHelper<StoreSaleAmount> pageHelper, String startTime, String endTime) {
        logger.debug("queryStoreSaleVolume and startTime:{} \r\n endTime:{} ", startTime, endTime);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return pageHelper.setListDates(orderMapper.queryStoreSaleVolume(pageHelper.getQueryParams(params, orderMapper.queryStoreSaleCountByTime(params))));
    }

    @Override
    public PageHelper<StoreSaleAmount> queryStoreSaleAmount(PageHelper<StoreSaleAmount> pageHelper, String startTime, String endTime) {
        logger.debug("queryStoreSaleAmount and startTime:{} \r\n endTime:{} ", startTime, endTime);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return pageHelper.setListDates(orderMapper.queryStoreSaleAmount(pageHelper.getQueryParams(params, orderMapper.queryStoreSaleCountByTime(params))));
    }

    @Override
    public MarketingOrderStatistics queryMarketingOrderStatistics(String startTime, String endTime, String orderType, long storeId) {
        logger.debug("queryMarketingOrderStatistics and startTime:{} \r\n endTime:{} \r\n orderType:{} \r\n storeId:{}", startTime, endTime, orderType, storeId);
        Map<String, Object> params = new HashMap<>();
        if (!StringUtils.isEmpty(startTime) && !startTime.contains(":")) {
            params.put("startTime", startTime + " 00:00:00");
        } else {
            params.put("startTime", startTime);
        }
        if (!StringUtils.isEmpty(endTime) && !endTime.contains(":")) {
            params.put("endTime", endTime + " 23:59:59");
        } else {
            params.put("endTime", endTime);
        }
        params.put("orderType", orderType);
        params.put("storeId", storeId);
        return orderMapper.queryMarketingOrderStatistics(params);
    }
}
