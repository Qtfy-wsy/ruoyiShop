package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OmsTransRecords;
import com.ruoyi.order.service.IOmsTransRecordsService;
import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.order.vo.StoreSaleAmount;
import com.ruoyi.store.domain.TStoreOrder;
import com.ruoyi.store.mapper.TStoreOrderMapper;
import com.ruoyi.store.service.ITStoreOrderAttrService;
import com.ruoyi.store.service.ITStoreOrderOperationLogService;
import com.ruoyi.store.service.ITStoreOrderService;
import com.ruoyi.store.service.ITStoreOrderSkuService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 门店订单Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Slf4j
@Service
public class TStoreOrderServiceImpl implements ITStoreOrderService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreOrderServiceImpl.class);
    @Autowired
    private TStoreOrderMapper tStoreOrderMapper;
    /**
     * 门店订单数据库接口
     */
    @Autowired
    private TStoreOrderMapper storeOrderMapper;
    /**
     * 注入门店订单属性服务接口
     */
    @Autowired
    private ITStoreOrderAttrService storeOrderAttrService;
    /**
     * 注入门店订单单品服务接口
     */
    @Autowired
    private ITStoreOrderSkuService storeOrderSkuService;
    /**
     * 注入门店订单操作日志服务
     */
    @Autowired
    private ITStoreOrderOperationLogService storeOrderOperationLogService;
    /**
     * 注入交易流水服务接口
     */
    @Autowired
    private IOmsTransRecordsService transRecordsService;

    /**
     * 查询门店订单
     *
     * @param id 门店订单ID
     * @return 门店订单
     */
    @Override
    public TStoreOrder selectTStoreOrderById(Long id) {
        return tStoreOrderMapper.selectTStoreOrderById(id);
    }

    @Override
    public PageHelper<TStoreOrder> queryStoreOrderList(PageHelper<TStoreOrder> pageHelper, TStoreOrder.QueryCriteria queryCriteria) {
        log.debug("queryStoreOrderList and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        return pageHelper.setListDates(storeOrderMapper.queryStoreOrderList(pageHelper.getQueryParams(queryCriteria.getQueryMap(), storeOrderMapper.queryStoreOrderListCount(queryCriteria.getQueryMap()))));
    }

    @Override
    public PageHelper<TStoreOrder> queryStoreOrderListForSite(PageHelper<TStoreOrder> pageHelper, TStoreOrder.QueryCriteria queryCriteria) {
        log.debug("queryStoreOrderListForSite and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        return pageHelper.setListDates(storeOrderMapper.queryStoreOrderListForSite(pageHelper.getQueryParams(queryCriteria.getQueryMap(),
                storeOrderMapper.queryStoreOrderListCountForSite(queryCriteria.getQueryMap()))).stream()
                .map(storeOrder -> storeOrder.buildStoreOrderSkus(storeOrderSkuService.queryByStoreOrderId(storeOrder.getId()))).collect(Collectors.toList()));
    }

    @Transactional
    @Override
    public int pickUpGoods(long orderId, long storeId) {
        log.debug("pickUpGoods and orderId:{} \r\n storeId:{}", orderId, storeId);
        // 提货
        return storeOrderMapper.pickUpGoods(orderId, storeId);
    }

    @Override
    public TStoreOrder queryStoreOrderByOrderIdAndWriteOffCode(long orderId, String writeOffCode, long storeId) {
        log.debug("queryStoreOrderByOrderIdAndWriteOffCode and orderId:{} \r\n writeOffCode:{} \r\n storeId:{}", orderId, writeOffCode, storeId);
        return storeOrderMapper.queryStoreOrderByOrderIdAndWriteOffCode(orderId, writeOffCode, storeId);
    }

    @Override
    public int cancelStoreOrder(long orderId, long storeId, String cancelReason, long customerId) {
        log.debug("cancelStoreOrder and orderId:{} \r\n storeId:{} \r\n cancelReason:{} \r\n customerId:{}", orderId, storeId, cancelReason, customerId);
        return storeOrderMapper.cancelStoreOrder(orderId, storeId, cancelReason, customerId);
    }

    @Transactional
    @Override
    public int saveOrder(TStoreOrder storeOrder) {
        log.debug("saveOrder and storeOrder:{}", storeOrder);
        if (Objects.isNull(storeOrder)) {
            return 0;
        }

        // 保存订单主表
        storeOrderMapper.saveStoreOrder(storeOrder);

        // 设置订单id
        storeOrder.setEveryOrderId();

        // 保存订单属性表
        storeOrderAttrService.insertTStoreOrderAttr(storeOrder.getStoreOrderAttr());

        // 保存订单单品表
        storeOrderSkuService.saveStoreOrderSkus(storeOrder.getStoreOrderSkus());

        return 1;
    }

    @Override
    public TStoreOrder queryByOrderId(long orderId, long storeId, long customerId, OrderItem... orderItems) {
        log.debug("queryByOrderId and orderId:{} \r\n storeId:{} \r\n customerId:{} \r\n orderItems:{}", orderId, storeId, customerId, orderItems);

        // 查询门店订单
        TStoreOrder storeOrder = storeOrderMapper.queryByOrderId(orderId, storeId, customerId);

        // 如果没有门店订单 直接返回
        if (Objects.isNull(storeOrder)) {
            log.error("queryByOrderId fail....order is not exist...");
            return null;
        }

        // 填充信息
        fillOrderOtherInfos(storeOrder, orderItems);

        return storeOrder;
    }

    @Override
    public List<TStoreOrder> queryToPayStoreOrder(String orderCode, long customerId, OrderItem... orderItems) {
        log.debug("queryToPayStoreOrder and orderCode:{} \r\n customerId:{} \r\n orderItems:{}", orderCode, customerId, orderItems);
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("customerId", customerId);

        // 待支付的订单信息
        List<TStoreOrder> storeOrderList = storeOrderMapper.queryToPayStoreOrder(params);

        // 没有待支付的订单 直接返回
        if (CollectionUtils.isEmpty(storeOrderList)) {
            log.error("this has no order to pay...");
            return new ArrayList<>();
        }

        // 设置订单其他信息
        storeOrderList.stream().forEach(storeOrder -> this.fillOrderOtherInfos(storeOrder, orderItems));

        return storeOrderList;
    }

    @Transactional
    @Override
    public int confirmOrderPayed(long customerId, List<String> orderCodes, String isPredepositPay, String transCode, String channel) {
        log.debug("confirmOrderPayed and customerId:{} \r\n orderCodes:{} \r\n isPredepositPay:{} \r\n transCode:{} \r\n channel:{}", customerId, orderCodes, isPredepositPay, transCode, channel);

        // 修改订单状态
        return orderCodes.stream().map(code -> new BigDecimal(this.confirmOrderPayed(customerId, code, isPredepositPay, transCode, channel))).
                reduce(BigDecimal.ZERO, BigDecimal::add).intValue() == orderCodes.size() ? 1 : 0;
    }


    @Override
    public List<TStoreOrder> queryOrderByOrderCode(String orderCode, long customerId, OrderItem... orderItems) {
        log.debug("queryOrderByOrderCode and orderCode :{} \r\n customerId:{} \r\n orderItems", orderItems);
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("customerId", customerId);

        List<TStoreOrder> storeOrders = storeOrderMapper.queryOrderByOrderCode(params);

        if (CollectionUtils.isEmpty(storeOrders)) {
            log.error("queryOrderByOrderCode faill....");
            return storeOrders;
        }

        //设置订单其他信息
        storeOrders.stream().forEach(storeOrder -> this.fillOrderOtherInfos(storeOrder, orderItems));

        return storeOrders;
    }

    @Override
    public int toPayOrderCount(long customerId) {
        log.debug("toPayOrderCount and customerId:{}", customerId);
        return storeOrderMapper.toPayOrderCount(customerId);
    }

    @Override
    public int toPickUpOrderCount(long customerId) {
        log.debug("toPickUpOrderCount and customerId:{}", customerId);
        return storeOrderMapper.toPickUpOrderCount(customerId);
    }

    @Override
    public int toEvaluateOrderCount(long customerId) {
        log.debug("toPickUpOrderCount and customerId:{}", customerId);
        return storeOrderMapper.toEvaluateOrderCount(customerId);
    }

    @Override
    public BigDecimal queryStoreSaleAmountToday(long storeId) {
        log.debug("queryStoreSaleAmountToday and storeId :{}", storeId);
        return storeOrderMapper.queryStoreSaleAmountToday(storeId);
    }

    @Override
    public int queryStoreSaleCountToday(long storeId) {
        log.debug("queryStoreSaleCountToday and storeId :{}", storeId);
        return storeOrderMapper.queryStoreSaleCountToday(storeId);
    }

    @Override
    public BigDecimal queryStoreSaleAmountThisWeek(long storeId) {
        log.debug("queryStoreSaleAmountThisWeek and storeId :{}", storeId);
        return storeOrderMapper.queryStoreSaleAmountThisWeek(storeId);
    }

    @Override
    public List<StoreSaleAmount> queryStoreSaleAmountThisWeekGroupByDay(long storeId) {
        log.debug("queryStoreSaleAmountThisWeekGroupByDay and storeId :{}", storeId);
        return storeOrderMapper.queryStoreSaleAmountThisWeekGroupByDay(storeId);
    }

    /**
     * 修改订单为已支付状态
     *
     * @param customerId      会员id
     * @param orderCode       订单code
     * @param isPredepositPay 是否预存款支付 0 否 1 是
     * @param transCode       交易流水号
     * @param channel         交易渠道
     * @return 成功返回1 失败返回0
     */
    private int confirmOrderPayed(long customerId, String orderCode, String isPredepositPay, String transCode, String channel) {
        log.debug("confirmOrderPayed and customerId:{} \r\n orderCode:{} \r\n isPredepositPay:{} \r\n transCode:{} \r\n channel:{}", customerId, orderCode, isPredepositPay, transCode, channel);

        List<TStoreOrder> storeOrders = this.queryOrderByOrderCode(orderCode, customerId);

        if (CollectionUtils.isEmpty(storeOrders)) {
            log.error("confirmOrder fail due to storeOrders is not exist");
            return 0;
        }

        // 只可能有一个订单信息 因为这个的code 都是子订单号
        TStoreOrder storeOrder = storeOrders.get(0);

        if (!StringUtils.isEmpty(transCode)) {
            transRecordsService.insertOmsTransRecords(OmsTransRecords.builder().money(storeOrder.getPrice()).orderId(storeOrder.getId()).lsTransCode(orderCode).transCode(transCode).channel(channel).type(CommonConstant.STORE_ORDER).payTime(new Date()).build());
        }

        Map<String, Object> params = new HashMap<>();
        params.put("customerId", customerId);
        params.put("orderCode", orderCode);
        params.put("isPredepositPay", isPredepositPay);
        return storeOrderMapper.confirmOrderPayed(params);
    }

    /**
     * 填充订单的其他信息
     *
     * @param storeOrder 订单信息
     * @param orderItems 填充条件
     */
    private void fillOrderOtherInfos(TStoreOrder storeOrder, OrderItem... orderItems) {

        // 设置订单下的单品信息
        if (ArrayUtils.contains(orderItems, OrderItem.SKUS)) {
            storeOrder.setStoreOrderSkus(storeOrderSkuService.queryByStoreOrderId(storeOrder.getId()));
        }

        // 设置订单的附属信息
        if (ArrayUtils.contains(orderItems, OrderItem.ATTR)) {
            storeOrder.setStoreOrderAttr(storeOrderAttrService.queryByStoreOrderId(storeOrder.getId()));
        }

        // 设置订单的日志信息
        if (ArrayUtils.contains(orderItems, OrderItem.LOG)) {
            storeOrder.setStoreOrderOperationLogs(storeOrderOperationLogService.queryStoreOrderOperationLogByOrderId(storeOrder.getId()));
        }

    }

    /**
     * 查询门店订单列表
     *
     * @param tStoreOrder 门店订单
     * @return 门店订单
     */
    @Override
    public List<TStoreOrder> selectTStoreOrderList(TStoreOrder tStoreOrder) {
        return tStoreOrderMapper.selectTStoreOrderList(tStoreOrder);
    }

    /**
     * 新增门店订单
     *
     * @param tStoreOrder 门店订单
     * @return 结果
     */
    @Override
    public int insertTStoreOrder(TStoreOrder tStoreOrder) {
        tStoreOrder.setCreateTime(DateUtils.getNowDate());
        return tStoreOrderMapper.insertTStoreOrder(tStoreOrder);
    }

    /**
     * 修改门店订单
     *
     * @param tStoreOrder 门店订单
     * @return 结果
     */
    @Override
    public int updateTStoreOrder(TStoreOrder tStoreOrder) {
        return tStoreOrderMapper.updateTStoreOrder(tStoreOrder);
    }

    /**
     * 批量删除门店订单
     *
     * @param ids 需要删除的门店订单ID
     * @return 结果
     */
    @Override
    public int deleteTStoreOrderByIds(Long[] ids) {
        return tStoreOrderMapper.deleteTStoreOrderByIds(ids);
    }

    /**
     * 删除门店订单信息
     *
     * @param id 门店订单ID
     * @return 结果
     */
    @Override
    public int deleteTStoreOrderById(Long id) {
        return tStoreOrderMapper.deleteTStoreOrderById(id);
    }
}
