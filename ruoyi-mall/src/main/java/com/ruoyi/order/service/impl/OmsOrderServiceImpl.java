package com.ruoyi.order.service.impl;

import com.ruoyi.common.md5.MD5Utils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.*;
import com.ruoyi.order.mapper.GroupOrderMapper;
import com.ruoyi.order.mapper.OmsOrderMapper;
import com.ruoyi.order.service.*;
import com.ruoyi.order.vo.GroupOrder;
import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.order.vo.QueryOrderCriteria;
import com.ruoyi.order.vo.StoreSaleAmount;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.Express100Utils;
import com.ruoyi.util.PageHelper;
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
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 订单Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class OmsOrderServiceImpl implements IOmsOrderService {
    @Autowired
    private OmsOrderMapper omsOrderMapper;
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OmsOrderServiceImpl.class);

    /**
     * 注入订单数据库接口
     */
    @Autowired
    private OmsOrderMapper orderMapper;

    /**
     * 注入运费模版服务接口
     */
    @Autowired
    private IOmsLogisticsTemplateService logisticsTemplateService;

    /**
     * 订单操作日志服务接口
     */
    @Autowired
    private IOmsOrderOperationLogService orderOperatonLogService;

    /**
     * 订单附属信息
     */
    @Autowired
    private IOmsOrderAttrService orderAttrService;

    /**
     * 订单单品服务接口
     */
    @Autowired
    private IOmsOrderSkuService orderSkuService;

    /**
     * 注入退单服务接口
     */
    @Autowired
    private IOmsBackOrderService backOrderService;


    /**
     * 注入拼团订单数据库接口
     */
    @Autowired
    private GroupOrderMapper groupOrderMapper;

    /**
     * 注入交易流水服务接口
     */
    @Autowired
    private IOmsTransRecordsService transRecordsService;


    /**
     * 注入物流公司服务接口
     */
    @Autowired
    private IOmsLogisticsCompanyService logisticsCompanyService;


    @Override
    public PageHelper<OmsOrder> queryOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria, OrderItem... orderItems) {
        logger.debug("queryOrders and pageHelper:{} \r\n queryCriteria:{}\r\n orderItems:{}", pageHelper, queryCriteria, orderItems);

        Map<String, Object> params = queryCriteria.getQueryMap();

        // 订单信息
        List<OmsOrder> orders = orderMapper.queryOrders(pageHelper.getQueryParams(params, orderMapper.queryOrderCount(params)));

        // 订单不为空 填充订单信息
        if (!CollectionUtils.isEmpty(orders)) {
            orders.stream().forEach(order -> fillOrderOtherInfos(order, orderItems));
        }

        return pageHelper.setListDates(orders);
    }


    @Override
    public PageHelper<OmsOrder> queryStoreOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria) {

        logger.debug("queryStoreOrders and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);

        Map<String, Object> params = queryCriteria.getQueryMap();

        return pageHelper.setListDates(orderMapper.queryStoreOrders(pageHelper.getQueryParams(params, orderMapper.queryStoreOrdersCount(params))));
    }

    @Override
    public int confirmOrder(long id, long storeId, Consumer<OmsOrder> orderConsumer, Consumer<OmsOrder> crowdfundingConsumer) {
        logger.debug("confirmOrder and id:{}", id);


        // 订单信息
        OmsOrder order = this.queryOrderDetailById(id, CommonConstant.QUERY_WITH_NO_CUSTOMER, storeId, OrderItem.SKUS);

        if (Objects.isNull(order)) {
            logger.error("confirmOrder fail due to order is not exist");
            return 0;
        }

        // 如果是定金预售订单第二次付款的时候需要判断是否到了可以支付的时间
        if (!order.isPresaleOrderCanPay()) {
            logger.error("confirmOrder fail due to order pay time is error...");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);


        // 如果是拼团订单 则回调
        if (order.isGroupOrder() && Objects.nonNull(orderConsumer)) {
            orderConsumer.accept(order);
        }

        // 如果是众筹订单则回调
        if (order.isCrowdfundingOrder() && Objects.nonNull(crowdfundingConsumer)) {
            crowdfundingConsumer.accept(order);
        }


        // 如果订单是定金预售订单  并且第一阶段还没有付款 则修改订单为第一阶段已付款 否则直接更改订单状态
        if (order.isDepositPresaleOrder() && !order.isPresaleOnePayed()) {
            return orderMapper.confirmPreSaleOrder(params);
        } else if (order.isNoReturnOrder()) {
            // 如果是无回报支持订单则直接修改成为已完成订单
            return orderMapper.confirmOrderFinished(params);
        } else {
            // 确认订单
            return orderMapper.confirmOrder(params);
        }
    }


    @Override
    public int cancelOrder(long id, long storeId, String reason) {
        logger.debug("cancelOrder and id:{} \r\n storeid:{}", id, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        params.put("reason", reason);
        return orderMapper.cancelOrder(params);
    }

    @Transactional
    @Override
    public int modifyPrice(long id, BigDecimal price, String reason, long storeId, String operationName) {
        logger.debug("modifyPrice and id:{} \r\n price:{} \r\n reason:{} \r\n storeId:{}", id, price, reason, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        params.put("price", price);

        // 查询订单信息
        OmsOrder order = this.queryOrderDetailById(id, CommonConstant.NO_LOGIN_CUSTOMERID, storeId, OrderItem.SKUS);

        // 订单信息为空 直接返回
        if (Objects.isNull(order)) {
            logger.error("modifyPrice fail due to order is not exist...");
            return 0;
        }

        if (!order.isCanModifyPrice()) {
            logger.error("modifyPrice fail due to order is presale order....");
            return -2;
        }

        //订单必须支付1毛钱
        if (!order.validateModifyPrice(price)) {
            logger.error("modifyPrice fail due to order must pay 0.1  and order money:{} \r\n and modify price:{}", order.getPrice(), price);
            return -1;
        }

        // 修改订单价格
        orderMapper.modifyPrice(params);

        // 计算每个单品优惠的价格
        order.calcModifyPriceEverySkuPrice(price);

        // 修改订单下每个单品的价格
        orderSkuService.updateOrderSkusPrice(order.getOrderSkus());

        // 增加订单操作日志
        orderOperatonLogService.insertOmsOrderOperationLog(OmsOrderOperationLog.buildForModifyPrice(id, reason, operationName));

        return 1;
    }

    @Override
    public int deliverOrder(long id, long storeId, String waybillCode, String companyName, String companyCode) {
        logger.debug("deliverOrder and id:{} \r\n storeId:{} \r\n companyName:{} \r\n companyCode:{}", id, storeId, companyName, companyCode);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        params.put("waybillCode", waybillCode);
        params.put("companyName", companyName);
        params.put("companyCode", companyCode);
        return orderMapper.deliverOrder(params);
    }

    @Override
    public int changeExpressNo(long id, long storeId, String waybillCode, String operationName, long companyId) {
        logger.debug("changeExpressNo and id:{} \r\n storeId:{} \r\n ,waybillCode:{}  \r\n companyId:{} ", id, storeId, waybillCode, companyId);

        //判断订单号是否含有中文，含有返回-1
        if (MD5Utils.getInstance().isContainChinese(waybillCode)) {
            logger.error("deliverOrder fail due to waybillCode has Chinese....");
            return -1;
        }


        // 查询物流公司信息
        OmsLogisticsCompany logisticsCompany = logisticsCompanyService.selectOmsLogisticsCompanyById(companyId);

        // 如果物流公司不存在则直接返回
        if (Objects.isNull(logisticsCompany)) {
            logger.error("deliverOrder fail due to logisticsCompany is not exist.....");
            return -6;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        params.put("waybillCode", waybillCode);
        params.put("companyName", logisticsCompany.getName());
        params.put("companyCode", logisticsCompany.getCode());

        // 更改物流单号失败  (这边失败主要是订单状态不对)
        if (orderMapper.changeExpressNo(params) == 0) {
            logger.error("deliverOrder fail....");
            return 0;
        }

        orderOperatonLogService.insertOmsOrderOperationLog(OmsOrderOperationLog.buildForChangeExpressNo(id, operationName));
        return 1;
    }


    @Override
    public OmsOrder queryOrderDetailById(long id, long customerId, long storeId, OrderItem... orderItems) {
        logger.debug("queryOrderDetailById and id:{} \r\n storeId:{} \r\n: {} \r\n :{}", id, storeId, orderItems, customerId);

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        params.put("customerId", customerId);

        // 查询订单信息
        OmsOrder order = orderMapper.queryOrderById(params);

        if (Objects.isNull(order)) {
            logger.error("queryOrderById fail...due to order is null....");
            return order;
        }

        // 设置订单的其他信息
        this.fillOrderOtherInfos(order, orderItems);

        return order;
    }

    @Transactional
    @Override
    public void saveOrder(OmsOrder order) {
        logger.debug("saveOrder and order:{}", order);

        if (Objects.isNull(order)) {
            logger.error("saveOrder fail due to order is empty....");
            return;
        }

        // 保存订单主表
        orderMapper.saveOrder(order);

        // 设置订单id
        order.setEveryOrderId();

        // 保存订单属性表
        orderAttrService.insertOmsOrderAttr(order.getOrderAttr());

        // 保存订单单品表
        orderSkuService.saveOrderSkus(order.getOrderSkus());
    }

    @Transactional
    @Override
    public int updateOrderPayed(List<String> orderCode, long customerId, int isPredepositPay, String transCode, String channel, Consumer<OmsOrder> orderConsumer, Consumer<OmsOrder> crowdfundingConsumer) {
        logger.debug("updateOrderPayed and orderCode:{}\r\n customerId:{} \r\n isPredepositPay:{} \r\n transCode:{} \r\n channel:{}", orderCode, customerId, isPredepositPay, transCode, channel);

        if (CollectionUtils.isEmpty(orderCode)) {
            logger.error("updateOrderPayed fail due to orderCode is empty...");
            return 0;
        }
        return orderCode.stream().map(code -> new BigDecimal(this.updateOrderPayed(code, customerId, isPredepositPay, transCode, channel, orderConsumer, crowdfundingConsumer))).
                reduce(BigDecimal.ZERO, BigDecimal::add).intValue() == orderCode.size() ? 1 : 0;
    }

    /**
     * 修改订单为已经支付状态
     *
     * @param orderCode            订单code
     * @param customerId           会员id
     * @param isPredepositPay      是否使用预存款
     * @param transCode            流水号
     * @param channel              支付渠道
     * @param orderConsumer        拼团订单回调
     * @param crowdfundingConsumer 众筹订单回调
     * @return 成功返回1 失败返回0
     */
    private int updateOrderPayed(String orderCode, long customerId, int isPredepositPay, String transCode, String channel, Consumer<OmsOrder> orderConsumer, Consumer<OmsOrder> crowdfundingConsumer) {
        logger.debug("updateOrderPayed and orderCode:{} \r\n customerId:{} \r\n isPredepositPay:{} \r\n transCode:{} \r\n channel:{}", orderCode, customerId, isPredepositPay, transCode, channel);

        // 订单信息
        List<OmsOrder> orders = this.queryOrderByOrderCode(orderCode, customerId, OrderItem.SKUS);

        if (CollectionUtils.isEmpty(orders)) {
            logger.error("confirmOrder fail due to order is not exist");
            return 0;
        }

        // 只可能有一个订单信息 因为这个的code 都是子订单号
        OmsOrder order = orders.get(0);

        // 如果是定金预售订单第二次付款的时候需要判断是否到了可以支付的时间
        if (!order.isPresaleOrderCanPay()) {
            logger.error("confirmOrder fail due to order pay time is error...");
            return 0;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("customerId", customerId);
        params.put("isPredepositPay", isPredepositPay);

        // 如果是拼团订单 则回调
        if (order.isGroupOrder() && Objects.nonNull(orderConsumer)) {
            orderConsumer.accept(order);
        }

        // 如果是众筹订单 则回调
        if (order.isCrowdfundingOrder() && Objects.nonNull(crowdfundingConsumer)) {
            crowdfundingConsumer.accept(order);
        }

        // 如果外部交易流水号不为空 则记录流水表
        if (!StringUtils.isEmpty(transCode)) {
            transRecordsService.insertOmsTransRecords(OmsTransRecords.builder().lsTransCode(orderCode).money(order.getPrice()).orderId(order.getId())
                    .transCode(transCode).channel(channel).type(CommonConstant.ORDINARY_ORDER).payTime(new Date()).build());
        }

        // 如果订单是定金预售订单  并且第一阶段还没有付款 则修改订单为第一阶段已付款 否则直接更改订单状态
        if (order.isDepositPresaleOrder() && !order.isPresaleOnePayed()) {
            return orderMapper.updatePreSaleOrderPayed(params);
        } else if (order.isNoReturnOrder()) {
            // 如果是众筹的无回报支持订单 则直接将订单设置成已完成
            return orderMapper.updateOrderFinished(params);
        } else {
            return orderMapper.updateOrderPayed(params);
        }
    }

    @Override
    public List<OmsOrder> queryToPayOrder(String orderCode, long customerId, OrderItem... orderItems) {
        logger.debug("queryToPayOrder and orderCode:{} \r\n customerId:{}", orderCode, customerId);

        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("customerId", customerId);

        List<OmsOrder> orders = orderMapper.queryToPayOrder(params);

        if (CollectionUtils.isEmpty(orders)) {
            return orders;
        }

        orders.stream().forEach(order -> this.fillOrderOtherInfos(order, orderItems));

        return orders;
    }

    @Override
    public PageHelper<OmsOrder> queryOrdersForSite(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria, OrderItem... orderItems) {
        logger.debug("queryOrdersForSite and pageHelper:{} \r\n queryCriteria:{} \r\n orderItems:{}", pageHelper, queryCriteria, orderItems);
        // 获得查询参数
        Map<String, Object> params = queryCriteria.getQueryMapForSite();

        // 查询出来的订单
        List<OmsOrder> orders = orderMapper.queryOrdersForSite(pageHelper.getQueryParams(params, orderMapper.queryOrderCountForSite(params)));

        // 设置订单的其他信息
        if (!CollectionUtils.isEmpty(orders)) {
            orders.stream().forEach(order -> this.fillOrderOtherInfos(order, ArrayUtils.add(orderItems, OrderItem.BACK_PROGRESS)));
        }

        return pageHelper.setListDates(orders);
    }

    @Override
    public int confirmReceipt(long orderId, long customerId) {
        logger.debug("confirmReceipt and orderId:{} \r\n customerId:{}", orderId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);
        return orderMapper.confirmReceipt(params);
    }

    @Override
    public List<OmsOrder> queryOrdersForCancel() {
        logger.debug("queryOrdersForCancel......");
        return orderMapper.queryOrdersForCancel();
    }


    @Override
    public List<OmsOrder> queryDepositPreSaleOrdersForCancel() {
        logger.debug("queryDepositPreSaleOrdersForCancel......");
        return orderMapper.queryDepositPreSaleOrdersForCancel();
    }


    @Override
    public int toPayOrderCount(long customerId) {
        logger.debug("toPayOrderCount and customerId:{}", customerId);
        return orderMapper.toPayOrderCount(customerId);
    }

    @Override
    public int toDeliverOrderCount(long customerId) {
        logger.debug("toDeliverOrderCount and customerId:{}", customerId);
        return orderMapper.toDeliverOrderCount(customerId);
    }

    @Override
    public int toReceiptOrderCount(long customerId) {
        logger.debug("toReceiptOrderCount and customerId:{}", customerId);
        return orderMapper.toReceiptOrderCount(customerId);
    }

    @Override
    public int toEvaluateOrderCount(long customerId) {
        logger.debug("toEvaluateOrderCount and customerId:{}", customerId);
        return orderMapper.toEvaluateOrderCount(customerId);
    }

    @Override
    public int updateOrderBack(long orderId, String status) {
        logger.debug("updateOrderBack and orderId:{} \r\n status:{}", orderId, status);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("status", status);
        return orderMapper.updateOrderBack(params);
    }

    @Override
    public int saleNum(long storeId) {
        logger.debug("saleNum and storeId:{}", storeId);
        return orderMapper.saleNum(storeId);
    }

    @Override
    public List<OmsOrder> queryOrderByOrderCode(String orderCode, long customerId, OrderItem... orderItems) {
        logger.debug("queryOrderById and orderCode:{} \r\n customerId:{}", orderCode, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("customerId", customerId);
        List<OmsOrder> orders = orderMapper.queryOrderByOrderCode(params);
        if (CollectionUtils.isEmpty(orders)) {
            logger.error("queryOrderById fail : query null");
            return null;
        }
        orders.stream().forEach(order -> this.fillOrderOtherInfos(order, orderItems));

        return orders;
    }

    @Override
    public int updateOrderEvluation(long orderId, long customerId) {
        logger.debug("updateOrderEvluation and orderId:{},customerId:{}", orderId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);
        return orderMapper.updateOrderEvluation(params);
    }

    @Override
    public String getCheckLogisticsUrl(long orderId, long customerId, String callbackUrl) {
        logger.debug("getCheckLogisticsUrl and orderId:{} \r\n customerId:{} \r\n callbackUrl :{}", orderId, customerId, callbackUrl);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);
        OmsOrder order = this.queryOrderDetailById(orderId, customerId, CommonConstant.QUERY_WITH_NO_STORE);
        if (Objects.isNull(order)) {
            logger.error("getCheckLogisticsUrl fail...due to order is null....");
            return null;
        }
        if (StringUtils.isEmpty(order.getWaybillCode())) {
            logger.error("getCheckLogisticsUrl fail...due to waybillCode is null....");
            return null;
        }
        //回跳地址设置返回上一步
        return Express100Utils.formatH5Url(order.getLogisticsCode(), order.getWaybillCode(), callbackUrl);
    }

    @Override
    public PageHelper<GroupOrder> queryGroupOrders(GroupOrder.QueryCriteria queryCriteria, PageHelper<GroupOrder> pageHelper, boolean isNeedMemberOrder, boolean isNeedOrderSku) {
        logger.debug("queryGroupOrders and queryCriteria:{} \r\n pageHelper:{} \r\n isNeedMemberOrder:{} \r\n  isNeedOrderSku:{}", queryCriteria, pageHelper, isNeedMemberOrder, isNeedOrderSku);
        pageHelper.setListDates(groupOrderMapper.queryGroupHeadOrders(pageHelper.getQueryParams(queryCriteria.getQueryParams(), groupOrderMapper.queryGroupHeadOrdersCount(queryCriteria.getQueryParams()))));
        //需要团员订单
        if (isNeedMemberOrder) {
            pageHelper.setListDates(pageHelper.getList().stream().map(groupOrder -> groupOrder.buildGroupMemberOrders(queryGroupMemberOrders(groupOrder.getGroupId(), true))).collect(Collectors.toList()));
        }
        //需要订单商品
        if (isNeedOrderSku) {
            pageHelper.setListDates(pageHelper.getList().stream().map(groupOrder -> {
                groupOrder.setOrderSkus(orderSkuService.queryByOrderId(groupOrder.getId()));
                return groupOrder;
            }).collect(Collectors.toList()));
        }
        return pageHelper;
    }

    @Override
    public PageHelper<GroupOrder> queryNotPayGroupOrders(GroupOrder.QueryCriteria queryCriteria, PageHelper<GroupOrder> pageHelper, boolean isNeedOrderSku) {
        logger.debug("queryNotPayGroupOrders and queryCriteria:{} \r\n pageHelper:{} \r\n isNeedOrderSku:{} ", queryCriteria, pageHelper, isNeedOrderSku);

        List<GroupOrder> noPayGroupOrders = groupOrderMapper.queryNotPayGroupOrders(pageHelper.getQueryParams(queryCriteria.getQueryParams(), groupOrderMapper.queryNotPayGroupOrdersCount(queryCriteria.getQueryParams())));

        if (isNeedOrderSku && !CollectionUtils.isEmpty(noPayGroupOrders)) {
            noPayGroupOrders.stream().forEach(groupOrder -> groupOrder.setOrderSkus(orderSkuService.queryByOrderId(groupOrder.getId())));
        }

        return pageHelper.setListDates(noPayGroupOrders);
    }

    @Override
    public PageHelper<GroupOrder> queryStoreGroupOrders(GroupOrder.QueryCriteria queryCriteria, PageHelper<GroupOrder> pageHelper) {
        logger.debug("queryStoreGroupOrders and queryCriteria:{} \r\n pageHelper:{} ", queryCriteria, pageHelper);
        return pageHelper.setListDates(groupOrderMapper.queryStoreGroupHeadOrders(pageHelper.getQueryParams(queryCriteria.getQueryParams(), groupOrderMapper.queryStoreGroupHeadOrdersCount(queryCriteria.getQueryParams())))
                .stream().map(groupOrder -> groupOrder.buildGroupMemberOrders(queryGroupMemberOrders(groupOrder.getGroupId(), true))).collect(Collectors.toList())
                .stream().map(groupOrder -> {
                    groupOrder.setOrderSkus(orderSkuService.queryByOrderId(groupOrder.getId()));
                    return groupOrder;
                }).collect(Collectors.toList()));
    }

    @Override
    public PageHelper<GroupOrder> queryStoreNotPayGroupOrders(GroupOrder.QueryCriteria queryCriteria, PageHelper<GroupOrder> pageHelper) {
        logger.debug("queryStoreNotPayGroupOrders and queryCriteria:{} \r\n pageHelper:{} ", queryCriteria, pageHelper);
        return pageHelper.setListDates(groupOrderMapper.queryStoreNotPayGroupOrders(pageHelper.getQueryParams(queryCriteria.getQueryParams(), groupOrderMapper.queryStoreNotPayGroupOrdersCount(queryCriteria.getQueryParams()))));
    }

    @Override
    public List<GroupOrder> queryGroupMemberOrders(String groupId, boolean isNeedFilterPay) {
        logger.debug("queryGroupMemberOrders and groupId:{} \r\n isNeedFilterPay:{}", groupId, isNeedFilterPay);
        Map<String, Object> params = new HashMap<>();
        //如果需要过滤付完款的订单
        if (isNeedFilterPay) {
            params.put("status", 1);
        }
        params.put("groupId", groupId);
        return groupOrderMapper.queryGroupMemberOrders(params);
    }

    @Override
    public GroupOrder queryGroupHeadByGroupId(String groupId) {
        logger.debug("queryGroupHeadByGroupId and groupId:{}", groupId);
        return groupOrderMapper.queryGroupHeadByGroupId(groupId);
    }

    @Override
    public GroupOrder queryByGroupIdAndCustomerId(String groupId, long customerId) {
        logger.debug("queryByGroupIdAndCustomerId and groupId:{} \r\n customerId:{}", groupId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("customerId", customerId);
        return groupOrderMapper.queryByGroupIdAndCustomerId(params);
    }

    @Override
    public int updateGroupStatus(long orderId, String status, int isUpdateTime) {
        logger.debug("updateGroupStatus orderId:{} \r\n status:{}", orderId, status);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("status", status);
        params.put("isUpdateTime", isUpdateTime);
        return groupOrderMapper.updateGroupStatus(params);
    }

    @Override
    public int updateAutoHandleStatus(long orderId) {
        logger.debug("updateAutoHandleStatus and orderId:{}", orderId);
        return groupOrderMapper.updateAutoHandleStatus(orderId);
    }

    @Override
    public int queryGroupNum(String groupId) {
        logger.debug("queryGroupNum and groupId:{}", groupId);
        return groupOrderMapper.queryGroupNum(groupId);
    }

    @Override
    public int updateGroupSuccess(String groupId) {
        logger.debug("updateGroupSuccess and groupId:{}", groupId);
        return groupOrderMapper.updateGroupSuccess(groupId);
    }

    @Override
    public int calcOrderByGroupIdAndCustomerId(String groupId, long customerId) {
        logger.debug("calcOrderByGroupIdAndCustomerId and groupId:{} \r\n customerId:{}", groupId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("groupId", groupId);
        params.put("customerId", customerId);
        return groupOrderMapper.calcOrderByGroupIdAndCustomerId(params);
    }

    @Override
    public List<GroupOrder> queryOpenGroupOrderForCancel() {
        logger.debug("queryOpenGroupOrderForCancel......");
        return groupOrderMapper.queryOpenGroupOrderForCancel();
    }

    @Override
    public List<GroupOrder> queryNotOpenGroupOrderForCancel() {
        logger.debug("queryNotOpenGroupOrderForCancel......");
        return groupOrderMapper.queryNotOpenGroupOrderForCancel();
    }

    @Override
    public List<OmsOrder> queryOrdersForConfirmReceipt(int day) {
        logger.debug("queryOrdersForConfirmReceipt and day:{}", day);
        return orderMapper.queryOrdersForConfirmReceipt(day);
    }

    @Override
    public PageHelper<OmsOrder> querySpreadOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria) {
        logger.debug("querySpreadOrders and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        List<OmsOrder> orderList = orderMapper.querySpreadOrders(pageHelper.getQueryParams(queryCriteria.getQueryMap(), orderMapper.querySpreadOrdersCount(queryCriteria.getQueryMap())));
        // 设置订单下的单品信息
        orderList.forEach(order -> this.fillOrderOtherInfos(order, OrderItem.SKUS));
        // 过滤掉没有分销的单品
        orderList.forEach(order -> order.setOrderSkus(order.getOrderSkus().stream().filter(OmsOrderSku::hasSetCommissionRate).collect(Collectors.toList())));
        // 设置分销当前分销单品的佣金比例（主要是设置当前登入的人对于这笔订单是一级还是二级）
        orderList.forEach(order -> this.setSetCommissionRate(order, queryCriteria.getCustomerId()));
        return pageHelper.setListDates(orderList);
    }


    /**
     * 主要是设置当前登入的人对于这笔订单是一级还是二级
     *
     * @param order      订单
     * @param customerId 会员id
     */
    private void setSetCommissionRate(OmsOrder order, long customerId) {
        // 说明当前登入人是这个订单的二级分佣
        if (order.getSRecommended() == customerId && customerId != -1) {
            // 这边把单品的一级佣金改成了二级 这样前端直接拿一级佣金就行了
            order.setCommissionLevel(CommonConstant.SECOND_COMMISSION_LEVEL);
        }
    }

    @Override
    public List<OmsOrder> queryOrdersByIdsWithOrderSku(List<Long> ids, String status, long storeId) {
        logger.debug("queryOrdersByIdsWithOrderSku and ids:{} \r\n status:{} \r\n storeId:{}", Arrays.toString(ids.toArray()), status, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("storeId", storeId);
        params.put("status", status);
        return orderMapper.queryOrdersByIds(params).stream().map(order -> {
            fillOrderOtherInfos(order, OrderItem.SKUS, OrderItem.ATTR);
            return order;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OmsOrder> queryAllOrderWithOrderSku(String status, long storeId, Long marketingId) {
        logger.debug("queryAllOrderWithOrderSku and status:{} \r\n storeId:{} \r\n marketingId:{} ", status, storeId, marketingId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("status", status);
        if (Objects.nonNull(marketingId)) {
            params.put("marketingId", marketingId);
        }
        return orderMapper.queryAllOrder(params).stream().map(order -> {
            fillOrderOtherInfos(order, OrderItem.SKUS, OrderItem.ATTR);
            return order;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OmsOrder> queryNotPayGroupOrdersByIdsWithOrderSku(List<Long> ids, long storeId) {
        logger.debug("queryNotPayGroupOrdersByIdsWithOrderSku and ids:{} \r\n storeId:{}", Arrays.toString(ids.toArray()), storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("storeId", storeId);
        return orderMapper.queryNotPayGroupOrdersByIds(params).stream().map(order -> {
            fillOrderOtherInfos(order, OrderItem.SKUS, OrderItem.ATTR);
            return order;
        }).collect(Collectors.toList());
    }

    @Override
    public List<OmsOrder> queryAllNotPayGroupOrderWithOrderSku(long storeId) {
        logger.debug("queryAllNotPayGroupOrderWithOrderSku and  storeId:{} ", storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        return orderMapper.queryAllNotPayGroupOrder(params).stream().map(order -> {
            fillOrderOtherInfos(order, OrderItem.SKUS, OrderItem.ATTR);
            return order;
        }).collect(Collectors.toList());
    }


    @Override
    public List<GroupOrder> queryGroupOrdersByIdsWithOrderSku(List<Long> ids, String status, long storeId) {
        logger.debug("queryGroupOrdersByIdsWithOrderSku and ids:{} \r\n status:{} \r\n storeId:{}", Arrays.toString(ids.toArray()), status, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("storeId", storeId);
        if (!StringUtils.isEmpty(status)) {
            params.put("status", status);
        }
        return groupOrderMapper.queryGroupHeadOrdersByIds(params)
                .stream().map(groupOrder -> groupOrder.buildGroupMemberOrders(queryGroupMemberOrders(groupOrder.getGroupId(), true))).collect(Collectors.toList())
                .stream().map(groupOrder -> fillGroupOrderOtherInfo(groupOrder, OrderItem.SKUS, OrderItem.ATTR)).collect(Collectors.toList());
    }

    @Override
    public List<GroupOrder> queryAllGroupOrderWithOrderSku(String status, long storeId) {
        logger.debug("queryAllGroupOrderWithOrderSku and status:{} \r\n storeId:{} ", status, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        if (!StringUtils.isEmpty(status)) {
            params.put("status", status);
        }
        return groupOrderMapper.queryAllGroupHeadOrder(params)
                .stream().map(groupOrder -> groupOrder.buildGroupMemberOrders(queryGroupMemberOrders(groupOrder.getGroupId(), true))).collect(Collectors.toList())
                .stream().map(groupOrder -> fillGroupOrderOtherInfo(groupOrder, OrderItem.SKUS, OrderItem.ATTR)).collect(Collectors.toList());
    }

    /**
     * 填充拼团订单的其他信息
     *
     * @param groupOrder 订单信息
     * @param orderItems 填充条件
     */
    private GroupOrder fillGroupOrderOtherInfo(GroupOrder groupOrder, OrderItem... orderItems) {

        // 设置订单下的单品信息
        if (ArrayUtils.contains(orderItems, OrderItem.SKUS)) {
            groupOrder.setOrderSkus(orderSkuService.queryByOrderId(groupOrder.getId()));
            // 设置团员订单下的单品信息
            if (!CollectionUtils.isEmpty(groupOrder.getGroupMemberOrders())) {
                groupOrder.setGroupMemberOrders(groupOrder.getGroupMemberOrders().stream().map(subOrder -> {
                    subOrder.setOrderSkus(groupOrder.getOrderSkus());
                    return subOrder;
                }).collect(Collectors.toList()));
            }
        }

        // 设置订单的附属信息
        if (ArrayUtils.contains(orderItems, OrderItem.ATTR)) {
            // 设置订单的附属信息
            groupOrder.setOrderAttr(orderAttrService.queryByOrderId(groupOrder.getId()));
            // 设置团员订单附属信息
            if (!CollectionUtils.isEmpty(groupOrder.getGroupMemberOrders())) {
                groupOrder.setGroupMemberOrders(groupOrder.getGroupMemberOrders().stream().map(subOrder -> {
                    subOrder.setOrderAttr(orderAttrService.queryByOrderId(groupOrder.getId()));
                    return subOrder;
                }).collect(Collectors.toList()));
            }
        }
        return groupOrder;
    }

    @Override
    public BigDecimal querySaleAmountToday(long storeId) {
        logger.debug("querySaleAmountToday and storeId :{}", storeId);
        return orderMapper.querySaleAmountToday(storeId);
    }

    @Override
    public int querySaleCountToday(long storeId) {
        logger.debug("querySaleCountToday and storeId :{}", storeId);
        return orderMapper.querySaleCountToday(storeId);
    }

    @Override
    public BigDecimal querySaleAmountThisWeek(long storeId) {
        logger.debug("querySaleAmountThisWeek and storeId :{}", storeId);
        return orderMapper.querySaleAmountThisWeek(storeId);
    }

    @Override
    public int querySaleCountThisWeek(long storeId) {
        logger.debug("querySaleCountThisWeek and storeId :{}", storeId);
        return orderMapper.querySaleCountThisWeek(storeId);
    }

    @Override
    public List<StoreSaleAmount> querySaleAmountThisWeekGroupByDay(long storeId) {
        logger.debug("querySaleAmountThisWeekGroupByDay and storeId :{}", storeId);
        return orderMapper.querySaleAmountThisWeekGroupByDay(storeId);
    }

    @Override
    public List<StoreSaleAmount> querySaleCountThisWeekGroupByDay(long storeId) {
        logger.debug("querySaleCountThisWeekGroupByDay and storeId :{}", storeId);
        return orderMapper.querySaleCountThisWeekGroupByDay(storeId);
    }

    @Override
    public int queryCrowdFundingOrderCount(long marketingId, long storeId) {
        logger.debug("queryCrowdFundingOrderCount and marketingId:{} \r\n storeId:{}", marketingId, storeId);
        return orderMapper.queryCrowdFundingOrderCount(marketingId, storeId);
    }

    @Override
    public int queryCrowFundingCustomerCount(long marketingId, long storeId) {
        logger.debug("queryCrowFundingCustomerCount and marketingId:{} \r\n storeId:{}", marketingId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("marketingId", marketingId);
        params.put("storeId", storeId);
        return orderMapper.queryCrowFundingCustomerCount(params);
    }

    @Override
    public List<OmsOrder> queryAllCrowdFundingOrderList(long marketingId, long storeId) {
        logger.debug("queryAllCrowdFundingOrderList and marketingId:{} \r\n storeId:{}", marketingId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("marketingId", marketingId);
        params.put("storeId", storeId);
        return orderMapper.queryAllCrowdFundingOrderList(params);
    }

    @Override
    public int updateCrowdFundingOrderLotteryStatus(long orderId) {
        logger.debug("updateCrowdFundingOrderLotteryStatus and orderId:{}", orderId);
        return orderMapper.updateCrowdFundingOrderLotteryStatus(orderId);
    }

    @Override
    public PageHelper<OmsOrder> queryCrowdFundingOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria) {
        logger.debug("queryCrowdFundingOrders and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        Map<String, Object> params = queryCriteria.getQueryMap();
        return pageHelper.setListDates(orderMapper.queryCrowdFundingOrders(pageHelper.getQueryParams(params, orderMapper.queryCrowdFundingOrdersCount(params))));
    }

    @Override
    public int queryTodayPayedOrderNum(long storeId) {
        logger.debug("queryTodayPayedOrderNum and storeId:{}", storeId);
        return orderMapper.queryTodayPayedOrderNum(storeId);
    }

    @Override
    public int queryToDeliveryOrderNum(long storeId) {
        logger.debug("queryToDeliveryOrderNum and storeId:{}", storeId);
        return orderMapper.queryToDeliveryOrderNum(storeId);
    }

    @Override
    public int queryToPayedOrderNum(long storeId) {
        logger.debug("queryToPayedOrderNum and storeId:{}", storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("status", "1");
        return orderMapper.queryOrderCount(params);
    }

    @Override
    public PageHelper<OmsOrder> queryCustomerCommunityOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria) {
        logger.debug("queryCustomerCommunityOrders and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        Map<String, Object> params = queryCriteria.getQueryMapForCommunity();

        // 订单信息
        List<OmsOrder> orders = orderMapper.queryCustomerCommunityOrders(pageHelper.getQueryParams(params, orderMapper.queryCustomerCommunityOrdersNum(params)));


        if (!CollectionUtils.isEmpty(orders)) {
            orders.stream().forEach(order -> {
                fillOrderOtherInfos(order, OrderItem.SKUS);
            });
        }
        return pageHelper.setListDates(orders);
    }

    @Override
    public List<OmsOrder> queryCommunityOrdersForCancel() {
        logger.debug("begin to queryCommunityOrdersForCancel....");
        return orderMapper.queryCommunityOrdersForCancel();
    }

    @Override
    public PageHelper<OmsOrder> queryHeadCommunityOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria) {
        logger.debug("queryHeadCommunityOrders and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        Map<String, Object> params = queryCriteria.getQueryMapForCommunity();

        // 订单信息
        List<OmsOrder> orders = orderMapper.queryHeadCommunityOrders(pageHelper.getQueryParams(params, orderMapper.queryHeadCommunityOrdersCount(params)));


        if (!CollectionUtils.isEmpty(orders)) {
            orders.stream().forEach(order -> {
                fillOrderOtherInfos(order, OrderItem.SKUS);
            });
        }
        return pageHelper.setListDates(orders);
    }

    @Override
    public List<OmsOrder> queryNoPayOrdersByCommunityBuyId(long communityBuyId) {
        logger.debug("queryNoPayOrdersByCommunityBuyId and communityBuyId");
        Map<String, Object> params = new HashMap<>();
        params.put("communityBuyId", communityBuyId);
        params.put("status", "1");
        List<OmsOrder> orders = orderMapper.queryOrdersByCommunityBuyId(params);

        if (!CollectionUtils.isEmpty(orders)) {
            orders.stream().forEach(order -> this.fillOrderOtherInfos(order, OrderItem.SKUS));
        }
        return orders;
    }

    @Override
    public List<OmsOrder> queryPayedOrdersByCommunityBuyId(long communityBuyId) {
        logger.debug("queryPayedOrdersByCommunityBuyId and communityBuyId:{}", communityBuyId);
        Map<String, Object> params = new HashMap<>();
        params.put("communityBuyId", communityBuyId);
        params.put("status", "2");
        List<OmsOrder> orders = orderMapper.queryOrdersByCommunityBuyId(params);
        if (!CollectionUtils.isEmpty(orders)) {
            orders.stream().forEach(order -> this.fillOrderOtherInfos(order, OrderItem.SKUS));
        }
        return orders;
    }

    @Override
    public List<OmsOrder> queryJoinCommunityOrders(long communityBuyCustomerId, long communityBuyId) {
        logger.debug("queryJoinCommunityOrders and communityBuyCustomerId :{} \r\n communityBuyId :{}", communityBuyCustomerId, communityBuyId);
        Map<String, Object> params = new HashMap<>();
        params.put("communityBuyCustomerId", communityBuyCustomerId);
        params.put("communityBuyId", communityBuyId);
        return orderMapper.queryJoinCommunityOrders(params);
    }

    /**
     * 查询社区团购已参团人数
     *
     * @param communityBuyCustomerId 社区团购的团长会员id
     * @param communityBuyId         社区团购的团购id
     * @return 社区团购已参团人数
     */
    @Override
    public int queryJoinCommunityOrdersCount(long communityBuyCustomerId, long communityBuyId) {
        logger.debug("queryJoinCommunityOrdersCount and communityBuyCustomerId:{} \r\n communityBuyId:{}", communityBuyCustomerId, communityBuyId);
        Map<String, Object> params = new HashMap<>();
        params.put("communityBuyCustomerId", communityBuyCustomerId);
        params.put("communityBuyId", communityBuyId);
        return orderMapper.queryJoinCommunityOrdersCount(params);
    }

    @Override
    public PageHelper<OmsOrder> queryCommunityOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria) {
        logger.debug("queryCommunityOrders and pageHelper :{} \r\n queryCriteria :{}", pageHelper, queryCriteria);
        Map<String, Object> params = queryCriteria.getQueryMapForCommunityAdmin();
        return pageHelper.setListDates(orderMapper.queryCommunityOrders(pageHelper.getQueryParams(params, orderMapper.queryCommunityOrderCount(params))));
    }

    /**
     * 根据团长的会员ID 和 团购活动ID 查询该团长在该活动中所有的销售订单  字段过多 仅查询必要字段
     *
     * @param customerId     团长的会员ID
     * @param communityBuyId 活动ID
     * @return 订单集
     */
    @Override
    public List<OmsOrder> queryCommunityOrders(Long customerId, Long communityBuyId, String status) {
        logger.debug("queryCommunityOrders and customerId and communityBuyId:{}", customerId, communityBuyId);
        Map<String, Object> param = new HashMap<>(3);
        param.put("communityBuyId", communityBuyId);
        param.put("headCustomerId", customerId);
        param.put("status", status);
        return orderMapper.queryCommunityBuyOrders(param);
    }

    @Override
    public List<OmsOrder> queryCommunityOrdersByIds(List<Long> ids) {
        logger.debug("queryCommunityOrdersByIds and ids:{}", Arrays.toString(ids.toArray()));
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        return orderMapper.queryCommunityOrdersByIds(params);
    }

    @Override
    public List<OmsOrder> queryAllCommunityOrders(String mobile) {
        logger.debug("queryAllCommunityOrders and mobile :{}", mobile);
        Map<String, Object> params = new HashMap<>();
        params.put("communityBuyHeadMobile", mobile);
        return orderMapper.queryAllCommunityOrders(params);
    }

    @Override
    public int confirmCommunityOrderReceipt(long orderId, long customerId) {
        logger.debug("confirmCommunityOrderReceipt and orderId:{} \r\n customerId:{}", orderId, customerId);
        Map<String, Object> params = new HashMap<>();
        params.put("orderId", orderId);
        params.put("customerId", customerId);
        return orderMapper.confirmCommunityOrderReceipt(params);
    }

    /**
     * 根据社区团购 id 查询订单 id 集(PC 端计算销量用, 因字段过多 ,  只查询必要信息)
     *
     * @param communityBuyId 社区团购 id
     * @return id 结果集
     */
    @Override
    public List<Long> queryOrderIdsByCommunityBuyId(Long communityBuyId) {
        logger.debug("queryOrderIdsByCommunityBuyId and communityBuyId :{}", communityBuyId);

        return orderMapper.queryOrderIdsByCommunityBuyId(communityBuyId);
    }

    /**
     * 填充订单的其他信息
     *
     * @param order      订单信息
     * @param orderItems 填充条件
     */
    private void fillOrderOtherInfos(OmsOrder order, OrderItem... orderItems) {

        // 设置订单下的单品信息
        if (ArrayUtils.contains(orderItems, OrderItem.SKUS)) {
            order.setOrderSkus(orderSkuService.queryByOrderId(order.getId()));
        }

        // 设置订单的退单状态
        if (ArrayUtils.contains(orderItems, OrderItem.BACK_PROGRESS)) {
            // 根据订单id查询该订单的退单纪录
            List<OmsBackOrder> backOrders = backOrderService.queryByOrderId(order.getId(), CommonConstant.QUERY_WITH_NO_CUSTOMER);

            // 如果存在正在处理的退单，则设置订单退单进行中字段
            if (!CollectionUtils.isEmpty(backOrders) && backOrders.stream().anyMatch(OmsBackOrder::isInBackProgress)) {
                order.setInBackProgress(true);
            }

        }

        // 设置订单的附属信息
        if (ArrayUtils.contains(orderItems, OrderItem.ATTR)) {
            // 设置订单的附属信息
            order.setOrderAttr(orderAttrService.queryByOrderId(order.getId()));

            // 转化赠品信息
            order.convertGiftToList();
        }

        // 设置订单是否可以退款
        if (ArrayUtils.contains(orderItems, OrderItem.CANREFUND)) {
            order.setCanRefund(backOrderService.isCanApplyRefund(order.getId(), order.getCustomerId(), order.getOrderType()));
        }

        //设置订单是否可以退货
        if (ArrayUtils.contains(orderItems, OrderItem.CANRETRUN)) {
            order.setCanReturn(!CollectionUtils.isEmpty(backOrderService.getCanRetrunOrderSkus(order)) && !order.overCanReturnDays());
        }


        // 设置订单操作日志
        if (ArrayUtils.contains(orderItems, OrderItem.LOG)) {
            order.setOrderOperatonLogs(orderOperatonLogService.queryOrderOperatonLogByOrderId(order.getId()));
        }

        //设置快递100查询地址(无回报支持的订单 和虚拟商品订单  和社区团购没有快递信息)
        if (ArrayUtils.contains(orderItems, OrderItem.EXPRESSURL) && !order.isNoReturnOrder() && !order.isVirtualOrder() && !order.isCommunityBuyOrder()) {
            order.setExpress100Url(Express100Utils.getHtmlApiURl(order.getLogisticsCode(), order.getWaybillCode()));
        }
    }

    /**
     * 查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    @Override
    public OmsOrder selectOmsOrderById(Long id) {
        return omsOrderMapper.selectOmsOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param omsOrder 订单
     * @return 订单
     */
    @Override
    public List<OmsOrder> selectOmsOrderList(OmsOrder omsOrder) {
        return omsOrderMapper.selectOmsOrderList(omsOrder);
    }

    /**
     * 新增订单
     *
     * @param omsOrder 订单
     * @return 结果
     */
    @Override
    public int insertOmsOrder(OmsOrder omsOrder) {
        omsOrder.setCreateTime(DateUtils.getNowDate());
        return omsOrderMapper.insertOmsOrder(omsOrder);
    }

    /**
     * 修改订单
     *
     * @param omsOrder 订单
     * @return 结果
     */
    @Override
    public int updateOmsOrder(OmsOrder omsOrder) {
        return omsOrderMapper.updateOmsOrder(omsOrder);
    }

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderByIds(Long[] ids) {
        return omsOrderMapper.deleteOmsOrderByIds(ids);
    }

    /**
     * 删除订单信息
     *
     * @param id 订单ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderById(Long id) {
        return omsOrderMapper.deleteOmsOrderById(id);
    }
}
