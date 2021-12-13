package com.ruoyi.order.service.impl;


import com.ruoyi.common.utils.IteratorUtils;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.member.domain.UmsPreDepositRecord;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.order.domain.OmsBackOrder;
import com.ruoyi.order.domain.OmsBillingRecords;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.service.*;
import com.ruoyi.order.vo.ApplyReturnParams;
import com.ruoyi.order.vo.BackOrderItem;
import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.order.vo.ReturnSku;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 17/11/14.
 * 退单服务接口
 */
@Service
public class BackOrderServiceApiImpl implements BackOrderServiceApi {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(BackOrderServiceApiImpl.class);

    /**
     * 注入订单服务接口
     */
    @Autowired
    private IOmsOrderService orderService;

    /**
     * 注入订单服务混合接口
     */
    @Autowired
    private OrderServiceApi orderServiceApi;

    /**
     * 注入退款/退货服务接口
     */
    @Autowired
    private IOmsBackOrderService backOrderService;

    /**
     * 注入序号生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 注入预存款服务接口
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;

    /**
     * 注入订单商品服务
     */
    @Autowired
    private IOmsOrderSkuService orderSkuService;

    /**
     * 注入账单记录服务接口
     */
    @Autowired
    private IOmsBillingRecordsService billingRecordService;

    /**
     * 注入店铺服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;

    @Transactional
    @Override
    public int applyRefundOrder(long customerId, long orderId, String reason, String desc) {
        logger.debug("applyRefundOrder and customerId:{} \r\n orderId:{}", customerId, orderId);

        // 首先根据用户id和订单id查询订单信息
        OmsOrder order = orderServiceApi.queryOrderDetailById(orderId, customerId, CommonConstant.QUERY_WITH_NO_STORE);

        logger.debug("applyRefundOrder queryOrder order:{}", order);

        // 如果订单不存在 或者订单状态不是待发货状态 则返回失败
        if (Objects.isNull(order) || !order.isToDeliver()) {
            logger.error("applyRefundOrder fail due to order stats is error...");
            return -1;
        }

        // 判断订单是否可以退款
        if (!backOrderService.isCanApplyRefund(orderId, customerId, order.getOrderType())) {
            logger.error("applyRefundOrder fail due to order has refuned ");
            return -1;
        }

        // 新增退款纪录
        return backOrderService.addBackOrder(OmsBackOrder.buildRefund(order, String.valueOf(snowflakeIdWorker.nextId()), reason, desc));
    }

    @Override
    public int crowdFundingFailApplyRefundOrder(long customerId, long orderId, String reason, String desc) {
        logger.debug("crowdFundingFailApplyRefundOrder and customerId:{} \r\n orderId:{} \r\n reason:{} \r\n desc:{}", customerId, orderId, reason, desc);
        // 首先根据用户id和订单id查询订单信息
        OmsOrder order = orderServiceApi.queryOrderDetailById(orderId, customerId, CommonConstant.QUERY_WITH_NO_STORE);

        logger.debug("crowdFundingFailApplyRefundOrder queryOrder order:{}", order);

        // 如果订单不存在 或者订单状态不是待发货状态 则返回失败
        if (Objects.isNull(order) || !order.isToDeliver()) {
            logger.error("crowdFundingFailApplyRefundOrder fail due to order stats is error...");
            return -1;
        }

        // 新增退款纪录
        return backOrderService.addBackOrder(OmsBackOrder.buildRefund(order, String.valueOf(snowflakeIdWorker.nextId()), reason, desc));
    }


    @Override
    public OmsOrder queryOrderForReturn(long customerId, long orderId) {
        logger.debug("queryOrderForReturn and customerId:{} \r\n orderId :{}", customerId, orderId);

        // 查询订单信息
        OmsOrder order = orderServiceApi.queryOrderDetailById(orderId, customerId, CommonConstant.QUERY_WITH_NO_STORE, OrderItem.SKUS);

        // 设置订单下可以退货的单品信息
        order.setOrderSkus(backOrderService.getCanRetrunOrderSkus(order));

        return order;
    }

    @Override
    public int applyReturnOrder(ApplyReturnParams applyReturnParams) {
        logger.debug("applyReturnOrder and applyReturnParams:{}", applyReturnParams);

        if (Objects.isNull(applyReturnParams)) {
            logger.error("applyReturnOrder fail due to applyReturnParams is error...");
            return -1;
        }

        // 查询订单信息
        OmsOrder order = orderServiceApi.queryOrderDetailById(applyReturnParams.getOrderId(), applyReturnParams.getCustomerId(), CommonConstant.QUERY_WITH_NO_STORE, OrderItem.SKUS);

        logger.debug("applyReturnOrder and order:{}", order);

        if (Objects.isNull(order) || !order.isFinished()) {
            logger.error("applyReturnOrder fail due to order status is error...");
            return -1;
        }

        // 当前订单下可以退货的单品信息
        List<OmsOrderSku> canReturnSkus = backOrderService.getCanRetrunOrderSkus(order);

        if (CollectionUtils.isEmpty(canReturnSkus)) {
            logger.error("applyReturnOrder fail... due to order no skus can return ...");
            return -1;
        }

        // 超过可以退货的时间
        if (order.overCanReturnDays()) {
            logger.error("applyReturnOrder fail:  over can return days");
            return -2;
        }

        // 对比用户需要退货的单品和单品的数量是否满足当前订单下可以退货的单品和单品数量
        if (!isCanReturn(canReturnSkus, applyReturnParams.getReturnSkus())) {
            logger.error("a ha  what are you doing....");
            return -1;
        }


        // 新增退货纪录
        return backOrderService.addBackOrder(applyReturnParams.buildBackOrder(order, String.valueOf(snowflakeIdWorker.nextId())));
    }

    @Transactional
    @Override
    public int agreeToRefund(long backOrderId, long storeId, String message) {
        logger.debug("agreeToRefund  and backOrderId:{} \r\n storeId:{} \r\n message:{}", backOrderId, storeId, message);

        // 同意退款
        if (backOrderService.agreeToRefund(backOrderId, storeId, message) != 1) {
            logger.error("agreeToRefund fail ....");
            return -1;
        }

        // 查询退单信息
        OmsBackOrder backOrder = backOrderService.queryBackOrderById(backOrderId, storeId, CommonConstant.QUERY_WITH_NO_CUSTOMER, BackOrderItem.SKUS);

        //如果是预存款则退还到用户的预存款中
        if (backOrder.isPreepositPay()) {
            logger.debug("order is PreepositPay and bean to return money to user PreepositPay account");
            predepositRecordService.addPredepositRecord(UmsPreDepositRecord.buildOrderBack(backOrder.getBackPrice(), backOrder.getCustomerId()));
        }

        // 修改订单状态
        orderService.updateOrderBack(backOrder.getOrderId(), "6");

        //修改库存
        backOrder.getOrderSkus().forEach(orderSku -> skuService.increaseSkuStock(orderSku.getSkuId(), orderSku.getNum()));

        // 记录账单
        billingRecordService.insertOmsBillingRecords(OmsBillingRecords.buildForRefundOrder(setBackOrderStoreName(backOrder)));

        return 1;
    }


    @Transactional
    @Override
    public int confirmReturn(long backOrderId, long storeId, String message, BigDecimal money) {
        logger.debug("confirmReturn and backOrderId:{} \r\n ,storeId:{} \r\n ,message:{} \r\n , money:{}", backOrderId, storeId, message, money);

        // 退货平台同意收货
        if (backOrderService.confirmReturn(backOrderId, storeId, message, money) != 1) {
            logger.error("confirmReturn fail....");
            return -1;
        }

        // 查询退单信息
        OmsBackOrder backOrder = backOrderService.queryBackOrderById(backOrderId, storeId, CommonConstant.QUERY_WITH_NO_CUSTOMER, BackOrderItem.SKUS);


        // 如果是预存款的 则把钱退至用户预存款账户中
        if (backOrder.isPreepositPay()) {
            logger.debug("order is PreepositPay and bean to return money to user PreepositPay account");
            predepositRecordService.addPredepositRecord(UmsPreDepositRecord.buildOrderBack(money, backOrder.getCustomerId()));
        }

        // 判断是否需要修改订单状态
        if (isNeedUpdateOrderStatus(backOrder)) {
            orderService.updateOrderBack(backOrder.getOrderId(), "7");
        }

        //修改库存
        backOrder.getOrderSkus().forEach(orderSku -> skuService.increaseSkuStock(orderSku.getSkuId(), orderSku.getNum()));

        // 记录账单服务接口
        billingRecordService.insertOmsBillingRecords(OmsBillingRecords.buildForReturnOrder(setBackOrderStoreName(backOrder)));

        billingRecordService.insertOmsBillingRecords(OmsBillingRecords.buildForReturnOrderExpenditure(setBackOrderStoreName(backOrder)));

        return 1;
    }

    /**
     * 判断订单是否需要设置成退货成功状态
     *
     * @param backOrder 退单信息
     * @return 如果订单下的单品全部都退货成功了 则返回true  否则返回false
     */
    private boolean isNeedUpdateOrderStatus(OmsBackOrder backOrder) {
        // 查出该退单所属的订单下的所有退货纪录
        List<OmsBackOrder> backOrders = backOrderService.queryByOrderId(backOrder.getOrderId(), CommonConstant.QUERY_WITH_NO_CUSTOMER);

        // 过滤出退货成功的纪录
        List<OmsBackOrder> successBackOrders = backOrders.stream().filter(OmsBackOrder::isReturnSuccess).collect(Collectors.toList());

        // 没有退货成功的纪录 直接返回false
        if (CollectionUtils.isEmpty(successBackOrders)) {
            return false;
        }

        // 设置退单下的单品信息
        successBackOrders.stream().forEach(backOrderService::setBackOrderSkus);


        // 获得总的成功退货的单品和单品数量
        List<ReturnSku> returnSkus = successBackOrders.stream().flatMap(successBackOrder -> successBackOrder.getOrderSkus().stream().map(orderSku -> {
            ReturnSku returnSku = new ReturnSku();
            returnSku.setSkuId(orderSku.getSkuId());
            returnSku.setNum(orderSku.getReturnNum());
            return returnSku;
        })).collect(Collectors.toList());


        // 该订单下一个的单品和单品数量
        List<ReturnSku> originReturnSkus = orderSkuService.queryByOrderId(backOrder.getOrderId()).stream().map(orderSku -> {
            ReturnSku returnSku = new ReturnSku();
            returnSku.setSkuId(orderSku.getSkuId());
            returnSku.setNum(orderSku.getNum());
            returnSku.setLeftover(orderSku.getNum());
            return returnSku;
        }).collect(Collectors.toList());


        IteratorUtils.zip(originReturnSkus, returnSkus, (returnSku, returnSku2) -> returnSku.getSkuId().equals(returnSku2.getSkuId()), (returnSku1, returnSku21) -> {
            returnSku1.setLeftover(returnSku1.getLeftover() - returnSku21.getNum());
        });

        // 所有的单品都退货完成了 返回true
        return originReturnSkus.stream().noneMatch(originReturnSkus1 -> !originReturnSkus1.hasFinishReturn());
    }


    /**
     * 对比用户需要退货的单品和单品的数量是否满足当前订单下可以退货的单品和单品数量
     *
     * @param canReturnSkus 订单可以退货的单品
     * @param returnSkus    用户需要退货的单品
     * @return 用户需要退货的单品 在可以退货单品的列表里面 并且数量也满足 返回true  否则返回false
     */
    private boolean isCanReturn(List<OmsOrderSku> canReturnSkus, List<ReturnSku> returnSkus) {

        IteratorUtils.zip(returnSkus, canReturnSkus, (returnSku, orderSku) -> returnSku.getSkuId().equals(orderSku.getSkuId()), (returnSku1, orderSku1) -> returnSku1.setSubNum(orderSku1.getReturnNum() - returnSku1.getNum()));

        // 只要用户想退货的单品只要有一个不能退货就不能退货(没有不能退货的单品 则说明可以退货 返回true)
        return CollectionUtils.isEmpty(returnSkus.stream().filter(returnSku -> !returnSku.isCanReturn()).collect(Collectors.toList()));
    }

    /**
     * 设置退单的店铺名称
     *
     * @param backOrder 退单信息
     * @return 返回退单的信息
     */
    private OmsBackOrder setBackOrderStoreName(OmsBackOrder backOrder) {
        logger.debug("begin to setBackOrderStoreName....");
        // 店铺信息
        TStoreInfo storeInfo = storeInfoService.queryStoreInfo(backOrder.getStoreId());

        if (Objects.isNull(storeInfo)) {
            logger.error("setBackOrderStoreName fail ...due to store is not exist...");
            return backOrder;
        }

        backOrder.setStoreName(storeInfo.getStoreName());

        return backOrder;
    }
}
