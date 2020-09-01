package com.ruoyi.integral.service.impl;

import com.ruoyi.integral.domain.PointMallOrder;
import com.ruoyi.integral.domain.QueryCriteria;
import com.ruoyi.integral.mapper.PointMallOrderMapper;
import com.ruoyi.integral.service.PointMallOrderService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 积分商城订单服务实现类
 */
@Service
public class PointMallOrderServiceImpl implements PointMallOrderService {


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(PointMallOrderServiceImpl.class);

    /**
     * 注入积分商城订单数据库接口
     */
    @Autowired
    private PointMallOrderMapper pointMallOrderMapper;

    @Override
    public PageHelper<PointMallOrder> queryPointMallOrders(PageHelper<PointMallOrder> pointMallOrderPageHelper, QueryCriteria queryCriteria) {
        logger.debug("queryPointMallOrders and queryCriteria:{}", queryCriteria);
        return pointMallOrderPageHelper.setListDates((pointMallOrderMapper.queryPointMallOrders(pointMallOrderPageHelper.getQueryParams(queryCriteria.getQueryMap(), pointMallOrderMapper.queryPointMallOrdersCount(queryCriteria.getQueryMap())))));
    }

    @Override
    public PageHelper<PointMallOrder> queryPointMallOrdersForSite(PageHelper<PointMallOrder> pointMallOrderPageHelper, QueryCriteria queryCriteria) {
        logger.debug("queryPointMallOrdersForSite and queryCriteria:{}", queryCriteria);
        return pointMallOrderPageHelper.setListDates((pointMallOrderMapper.queryPointMallOrders(pointMallOrderPageHelper.getQueryParams(queryCriteria.getQueryMapForSite(), pointMallOrderMapper.queryPointMallOrdersCount(queryCriteria.getQueryMapForSite())))).stream().map(PointMallOrder::convertJsonToObject).collect(Collectors.toList()));
    }

    @Override
    public int deliverPointMallOrder(long id, String companyName, String logisticsCode) {
        logger.debug("deliverPointMallOrder and id:{} \r\n companyName:{} \r\n logisticsCode:{}", id, companyName, logisticsCode);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("companyName", companyName);
        params.put("logisticsCode", logisticsCode);
        return pointMallOrderMapper.deliverPointMallOrder(params);
    }

    @Override
    public PointMallOrder queryPointMallOrderById(long id, Long customerId) {
        logger.debug("queryPointMallOrderById and id:{} \r\n customerId:{} \r\n requestFrom:{}", id, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("customerId", customerId);
        return pointMallOrderMapper.queryPointMallOrderById(params);
    }

    @Override
    public int savePointMallOrder(PointMallOrder pointMallOrder) {
        logger.debug("savePointMallOrder and pointMallOrder:{}", pointMallOrder);
        if (ObjectUtils.isEmpty(pointMallOrder)) {
            logger.error("savePointMallOrder fail and pointMallOrder is empty");
            return -1;
        }
        return pointMallOrderMapper.savePointMallOrder(pointMallOrder);
    }

    @Override
    public int confirmReceipt(long id, long customerId) {
        logger.debug("confirmReceipt and id:{} \r\n customerId:{}", id, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("customerId", customerId);
        return pointMallOrderMapper.confirmReceipt(params);
    }

    @Override
    public List<PointMallOrder> queryHotPointSkusId() {
        logger.debug("queryHotPointSkus....");
        return pointMallOrderMapper.queryHotPointSkusId();
    }

    @Override
    public int toDeliverPointOrderCount(long customerId) {
        logger.debug("toDeliverPointOrderCount and customerId:{}", customerId);
        return pointMallOrderMapper.toDeliverPointOrderCount(customerId);
    }

    @Override
    public int toReceiptPointOrderCount(long customerId) {
        logger.debug("toReceiptPointOrderCount and customerId:{}", customerId);
        return pointMallOrderMapper.toReceiptPointOrderCount(customerId);
    }

}
