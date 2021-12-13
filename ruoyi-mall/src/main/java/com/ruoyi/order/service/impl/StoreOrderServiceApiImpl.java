package com.ruoyi.order.service.impl;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.IteratorUtils;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.StoreRedEnvelope;
import com.ruoyi.marketing.service.StoreRedEnvelopeService;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.SmsService;
import com.ruoyi.order.service.StoreOrderServiceApi;
import com.ruoyi.order.service.StoreSettlementService;
import com.ruoyi.order.vo.*;
import com.ruoyi.store.domain.*;
import com.ruoyi.store.service.*;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.ThreadTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 2018/4/10.
 * 门店订单服务接口实现
 */
@Slf4j
@Service
public class StoreOrderServiceApiImpl implements StoreOrderServiceApi {

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入门店结算信息服务接口
     */
    @Autowired
    private StoreSettlementService storeSettlementService;

    /**
     * 注入序号生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 注入店铺购物车服务接口
     */
    @Autowired
    private ITStoreShoppingCartService storeShoppingCartService;

    /**
     * 注入门店单品服务接口
     */
    @Autowired
    private ITStoreSkuService storeSkuService;

    /**
     * 注入门店订单服务接口
     */
    @Autowired
    private ITStoreOrderService storeOrderService;

    /**
     * 注入店铺信息接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 注入短信服务接口
     */
    @Autowired
    private SmsService smsService;

    /**
     * 注入会员服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入门店红包服务
     */
    @Autowired
    private StoreRedEnvelopeService storeRedEnvelopeService;

    /**
     * 注入门店预约服务
     */
    @Autowired
    private ITStoreReservationService reservationService;


    /**
     * 注入门店账单服务
     */
    @Autowired
    private ITStoreBillingRecordsService storeBillingRecordService;

    /**
     * 注入门店订单操作日志服务
     */
    @Autowired
    private ITStoreOrderOperationLogService storeOrderOperationLogService;


    @Transactional
    @Override
    public SubmitOrderResponse submitOrder(StoreSubmitOrderParams storeSubmitOrderParams) {
        log.debug("submitOrder and storeSubmitOrderParams:{}", storeSubmitOrderParams);

        if (Objects.isNull(storeSubmitOrderParams)) {
            log.debug("submitOrder fail due to params is empty....");
            return SubmitOrderResponse.buildFail("R-0000011");
        }
        if (Objects.isNull(storeSubmitOrderParams.getCustomerId())) {
            log.debug("submitOrder fail due to customerId is empty....");
            return SubmitOrderResponse.buildFail("R-000004");
        }

        // 获得门店订单的每个门店的结算信息
        StoreOrderSettlement storeOrderSettlement = storeSettlementService.orderSettlement(storeSubmitOrderParams);

        // 订单中没有单品信息
        if (!storeOrderSettlement.validateSkus()) {
            log.error("submitOrder fail due to order has no goods....");
            throw new ServiceException("R-000006");
        }

        // 门店订单信息
        List<TStoreOrder> storeOrders = getStoreOrderInfos(fillStoreOrderSettlement(storeOrderSettlement, storeSubmitOrderParams), storeSubmitOrderParams);

        // 计算红包的价格
        calcStoreRedEnvelopePrice(storeSubmitOrderParams, storeOrders);

        // 保存订单信息
        saveStoreOrders(storeOrders);

        return getSubmitOrderResponse(storeOrders);
    }


    /**
     * 获得订单返回信息
     *
     * @param storeOrders 订单信息
     * @return 返回订单信息
     */
    private SubmitOrderResponse getSubmitOrderResponse(List<TStoreOrder> storeOrders) {

        // 不需要付款的订单
        List<TStoreOrder> NoNeedPayOrders = storeOrders.stream().filter(TStoreOrder::isNoNeedPay).collect(Collectors.toList());

        // 需要付款的订单
        List<TStoreOrder> needPayOrders = storeOrders.stream().filter(storeOrder -> !storeOrder.isNoNeedPay()).collect(Collectors.toList());

        // 没有需要付款的订单 直接返回
        if (CollectionUtils.isEmpty(needPayOrders)) {
            return SubmitOrderResponse.buildNoNeedPayResponse(NoNeedPayOrders.stream().map(TStoreOrder::getPrice).reduce(new BigDecimal(0), BigDecimal::add), NoNeedPayOrders.get(0).getOrderCode());
        }

        // 有需要付款的订单 则返回需要付款的实体  注意 订单号拿的的主订单号
        return SubmitOrderResponse.buildNeedPayResponse(needPayOrders.get(0).getMasterOrderCode(), needPayOrders.stream().map(TStoreOrder::getPrice).reduce(new BigDecimal(0), BigDecimal::add));
    }

    @Override
    public StoreOrderDetail queryStoreOrderDetail(long orderId, long customerId) {
        log.debug("queryStoreOrderDetail and orderId:{} \r\n customerId:{}", orderId, customerId);
        TStoreOrder storeOrder = storeOrderService.queryByOrderId(orderId, CommonConstant.QUERY_WITH_NO_STORE, customerId, OrderItem.SKUS, OrderItem.ATTR);
        if (Objects.isNull(storeOrder)) {
            log.error("queryStoreOrderDetail  fail : no storeOrder");
            return StoreOrderDetail.build(null, null);
        }
        return StoreOrderDetail.build(storeOrder, storeInfoService.selStoreInfo(storeOrder.getStoreId()));
    }

    @Override
    public int confirmOrderPayed(long customerId, List<String> orderCodes, String isPredepositPay, String transCode, String channel) {
        log.debug("confirmOrderPayed and customerId:{} \r\n orderCodes:{} r\n isPredepositPay:{} \r\n transCode:{} \r\n  channel:{}", customerId, orderCodes, isPredepositPay, transCode, channel);

        // 发送核销短信
        int result = storeOrderService.confirmOrderPayed(customerId, orderCodes, isPredepositPay, transCode, channel);

        if (result > 0) {
            log.debug("confirmOrderPayed success and begin to send sms....");
            ThreadTask.getInstance().addTask(() ->
                    orderCodes.stream().forEach(orderCode -> {
                        List<TStoreOrder> storeOrders = storeOrderService.queryOrderByOrderCode(orderCode, customerId);
                        if (!CollectionUtils.isEmpty(storeOrders)) {
                            // 查询用户信息
                            UmsMember customer = customerService.queryCustomerWithNoPasswordById(storeOrders.get(0).getCustomerId());
                            // 查询店铺信息
                            TStoreInfo storeInfo = storeInfoService.queryStoreInfo(storeOrders.get(0).getStoreId());

                            // 这边肯定只有一个记录
                            smsService.sendWiteOffCodeSms(Objects.nonNull(customer) ? customer.getMobile() : "", storeOrders.get(0).getOrderCode(), storeOrders.get(0).getWriteOffCode(), Objects.nonNull(storeInfo) ? storeInfo.getStoreName() : "");
                        }
                    }));
        }

        return 1;
    }

    @Transactional
    @Override
    public int confirmOrderPayedByBackstage(long orderId, long storeId, String remark, String operationName) {
        log.debug("confirmOrderPayedByAdmin and orderId:{} \r\n storeId:{} \r\n remark:{} \r\n operationName:{}", orderId, storeId, remark, operationName);
        TStoreOrder storeOrder = storeOrderService.queryByOrderId(orderId, storeId, CommonConstant.QUERY_WITH_NO_CUSTOMER);
        if (Objects.isNull(storeId)) {
            log.error("confirmOrderPayedByBackstage fail : no storeOrder");
            return 0;
        }
        int res = confirmOrderPayed(CommonConstant.QUERY_WITH_NO_CUSTOMER, Arrays.asList(storeOrder.getOrderCode()), "0", "", "");
        //如果成功，增加门店订单操作日志
        if (res == 1) {
            storeOrderOperationLogService.insertTStoreOrderOperationLog(TStoreOrderOperationLog.buildForConfirmOrderPayed(orderId, remark, operationName));
        }
        return res;
    }

    @Transactional
    @Override
    public int cancelOrderByBackstage(long orderId, long storeId, String cancelReason, String operationName) {
        log.debug("cancelOrderByBackstage and orderId:{} \r\n storeId:{}\r\n cancelReason:{} \r\n operationName:{}", orderId, storeId, cancelReason, operationName);
        int res = storeOrderService.cancelStoreOrder(orderId, storeId, cancelReason, CommonConstant.QUERY_WITH_NO_CUSTOMER);
        //如果成功，增加门店订单操作日志
        if (res == 1) {
            storeOrderOperationLogService.insertTStoreOrderOperationLog(TStoreOrderOperationLog.buildForCancelOrder(orderId, operationName));
        }
        return res;
    }

    @Override
    public int pickUpGoods(long orderId, long storeId, String writeOffCode, String operationName) {
        log.debug("pickUpGoods and orderId:{} \r\n storeId:{} \r\n writeOffCode:{} \r\n operationName:{}", orderId, storeId, writeOffCode, operationName);

        // 门店订单
        TStoreOrder storeOrder = storeOrderService.queryStoreOrderByOrderIdAndWriteOffCode(orderId, writeOffCode, storeId);
        if (Objects.isNull(storeOrder)) {
            log.error("pickUpGoods fail: no storeOrder");
            return -1;
        }
        if (storeOrder.isFinish()) {
            log.error("pickUpGoods fail:  storeOrder already finished ");
            return -2;
        }
        if (!storeOrder.isFinish() && !storeOrder.isWaitForPickUp()) {
            log.error("pickUpGoods fail:  storeOrder is not waitForPickUpStatus ");
            return -3;
        }

        // 改变会员消费总金额
        customerService.updateCustomerConsumptionAmount(storeOrder.getCustomerId(), storeOrder.getPrice());

        // 提货
        storeOrderService.pickUpGoods(orderId, storeId);

        // 新增账单记录
        storeBillingRecordService.insertTStoreBillingRecords(TStoreBillingRecords.buildForPickUp(storeOrder));

        // 新增操作日志
        storeOrderOperationLogService.insertTStoreOrderOperationLog(TStoreOrderOperationLog.buildForPickUpOrder(orderId, operationName));
        return 0;
    }

    /**
     * 填充结算信息 主要填充备注和取货时间
     *
     * @param storeOrderSettlement   门店订单结算信息
     * @param storeSubmitOrderParams 用户提交的参数
     */
    private StoreOrderSettlement fillStoreOrderSettlement(StoreOrderSettlement storeOrderSettlement, StoreSubmitOrderParams storeSubmitOrderParams) {

        log.debug("fillStoreOrderSettlement and storeOrderSettlement:{} \r\n storeSubmitOrderParam:{}", storeOrderSettlement, storeSubmitOrderParams);

        IteratorUtils.zip(storeOrderSettlement.getStoreStoreSettlements(), storeSubmitOrderParams.getStoreInfos(), (x, y) -> x.getStoreId().equals(y.getStoreId()), (storeSettlement, storeInfo) -> {

            // 设置备注信息
            storeSettlement.setRemark(storeInfo.getRemark());

            // 取货时间
            storeSettlement.setPickTime(storeInfo.getPickUpTime());
        });

        return storeOrderSettlement;
    }


    /**
     * 保存订单信息
     *
     * @param storeOrders 订单
     */
    public void saveStoreOrders(List<TStoreOrder> storeOrders) {
        log.debug("saveStoreOrders and storeOrders:{}", storeOrders);

        // 保存订单
        storeOrders.stream().forEach(this::saveStoreOrder);
    }

    /**
     * 保存门店订单
     *
     * @param storeOrder 门店订单
     */
    private void saveStoreOrder(TStoreOrder storeOrder) {
        log.debug("saveStoreOrder storeOrder:{}", storeOrder);

        // 减去订单下单品的库存
        reduceSkusStock(storeOrder.getStoreOrderSkus(), storeOrder.getStoreId());

        // 保存订单信息
        storeOrderService.saveOrder(storeOrder);

        // 删除购物车信息
        if (ArrayUtils.isNotEmpty(storeOrder.getCartIds())) {
            storeShoppingCartService.deleteStoreShoppingCart(storeOrder.getCustomerId(), storeOrder.getCartIds());
        }

        // 删除预约信息
        if (ArrayUtils.isNotEmpty(storeOrder.getReservationIds())) {
            reservationService.cancelReservation(storeOrder.getReservationIds(), storeOrder.getCustomerId(), storeOrder.getStoreId());
            //增加门店代客下单账单记录
            storeBillingRecordService.insertTStoreBillingRecords(TStoreBillingRecords.buildForReservation(storeOrder, storeInfoService.queryStoreInfo(storeOrder.getStoreId()).getStoreName()));
        }

        //如果使用了红包，将红包设置为已使用
        if (storeOrder.isRedEnvelopeUsed()) {
            storeRedEnvelopeService.setStoreRedEnvelopeUsed(storeOrder.getRedEnvelopeCode(), storeOrder.getCustomerId());
        }
    }

    /**
     * 减去单品的库存
     *
     * @param storeOrderSkus 订单下的单品
     * @param storeId        店铺id
     */
    private void reduceSkusStock(List<TStoreOrderSku> storeOrderSkus, long storeId) {
        log.debug("reduceSkusStock and storeOrderSkus:{}", storeOrderSkus);

        // 首先减去订单下每个单品的库存
        storeOrderSkus.stream().forEach(orderSku -> {
            // 减去订单下单品的库存 如果库存不足 则抛出异常
            if (storeSkuService.reduceStoreSkusStock(storeId, orderSku.getSkuId(), orderSku.getNum()) == 0) {
                throw new ServiceException("R-000005");
            }
        });
    }


    /**
     * 获得门店订单信息
     *
     * @param storeOrderSettlement   门店订单结算
     * @param storeSubmitOrderParams 用户提交门店订单的参数
     * @return 返回订单信息
     */
    private List<TStoreOrder> getStoreOrderInfos(StoreOrderSettlement storeOrderSettlement, StoreSubmitOrderParams storeSubmitOrderParams) {
        // 主订单号,必须放在这里
        Long masterOrderCode = snowflakeIdWorker.nextId();
        return storeOrderSettlement.getStoreStoreSettlements().parallelStream().
                map(storeSettlement -> getOrderDetail(masterOrderCode, storeSettlement, storeSubmitOrderParams)).collect(Collectors.toList());

    }


    /**
     * 获得门店订单的详细信息
     *
     * @param masterOrderId          主订单号
     * @param storeStoreSettlement   门店结算信息
     * @param storeSubmitOrderParams 提交门店订单的参数
     * @return 返回门店订单详情
     */
    private TStoreOrder getOrderDetail(Long masterOrderId, StoreStoreSettlement storeStoreSettlement, StoreSubmitOrderParams storeSubmitOrderParams) {

        // 生成门店订单
        TStoreOrder storeOrder = new TStoreOrder();

        // 设置购物车
        storeOrder.setCartIds(storeStoreSettlement.getCartIds());

        //设置预约id
        storeOrder.setReservationIds(storeSubmitOrderParams.getReservationIds());

        // 设置订单的用户id
        storeOrder.setCustomerId(storeSubmitOrderParams.getCustomerId());

        // 设置订单号
        storeOrder.setOrderCode(String.valueOf(snowflakeIdWorker.nextId()));

        // 设置主订单号
        storeOrder.setMasterOrderCode(String.valueOf(masterOrderId));

        // 设置订单的原始价格
        storeOrder.setOriginalPrice(storeStoreSettlement.getTotalPrice());

        // 设置订单的最终价格
        storeOrder.setPrice(storeStoreSettlement.getTotalPrice());

        // 设置订单状态
        storeOrder.setCreateStoreOrderStatus();

        //设置支付类型
        storeOrder.setPayType(storeSubmitOrderParams.getPayType());

        // 设置店铺id
        storeOrder.setStoreId(storeStoreSettlement.getStoreId());

        // 设置评价状态
        storeOrder.setEvaluationStatus("0");

        // 设置核销码
        storeOrder.setWriteOffCode((int) ((Math.random() * 9 + 1) * 100000) + "");

        // 设置订单来源
        storeOrder.setSource(storeSubmitOrderParams.getSource());


        // 设置取货时间
        storeOrder.setPickUpTime(storeStoreSettlement.getPickTime());

        // 设置订单下的单品信息
        storeOrder.setStoreOrderSkus(getStoreOrderSkus(storeStoreSettlement));

        // 设置订单下的附属信息
        storeOrder.setStoreOrderAttr(getStoreOrderAttr(storeStoreSettlement, storeSubmitOrderParams));


        return storeOrder;
    }

    /**
     * 获得门店订单属性
     *
     * @param storeStoreSettlement   门店订单结算实体
     * @param storeSubmitOrderParams 提交的门店订单参数
     * @return 返回门店订单属性
     */
    private TStoreOrderAttr getStoreOrderAttr(StoreStoreSettlement storeStoreSettlement, StoreSubmitOrderParams storeSubmitOrderParams) {
        TStoreOrderAttr storeOrderAttr = new TStoreOrderAttr();
        storeOrderAttr.setInvoiceType(String.valueOf(storeSubmitOrderParams.getInvoice().getType()));
        storeOrderAttr.setInvoiceTitle(storeSubmitOrderParams.getInvoice().getTitle());
        storeOrderAttr.setInvoiceContent(storeSubmitOrderParams.getInvoice().getContent());
        storeOrderAttr.setInvoiceTaxid(storeSubmitOrderParams.getInvoice().getTaxId());
        storeOrderAttr.setRemark(storeStoreSettlement.getRemark());
        storeOrderAttr.setInvoiceTitleType(storeSubmitOrderParams.getInvoice().getTitleType());
        storeOrderAttr.setInvoiceCompanyName(storeSubmitOrderParams.getInvoice().getInvoiceCompanyName());
        storeOrderAttr.setInvoiceRegisterAddress(storeSubmitOrderParams.getInvoice().getInvoiceRegisterAddress());
        storeOrderAttr.setInvoiceRegisterMobile(storeSubmitOrderParams.getInvoice().getInvoiceRegisterMobile());
        storeOrderAttr.setInvoiceOpenBank(storeSubmitOrderParams.getInvoice().getInvoiceOpenBank());
        storeOrderAttr.setInvoiceBankAccount(storeSubmitOrderParams.getInvoice().getInvoiceBankAccount());
        return storeOrderAttr;
    }

    /**
     * 获得门店订单的单品信息
     *
     * @param storeStoreSettlement 门店订单结算信息
     * @return 返回门店订单的单品信息
     */
    private List<TStoreOrderSku> getStoreOrderSkus(StoreStoreSettlement storeStoreSettlement) {

        // 门店订单下的单品信息
        List<TStoreOrderSku> storeOrderSkus = new ArrayList<>();

        // 门店购物车信息
        StoreShoppingCartResponse storeShoppingCartResponse = storeStoreSettlement.getStoreShoppingCartResponse();

        if (Objects.isNull(storeShoppingCartResponse)) {
            return storeOrderSkus;
        }

        if (!CollectionUtils.isEmpty(storeShoppingCartResponse.getNormalSkus())) {
            storeOrderSkus.addAll(storeShoppingCartResponse.getNormalSkus().stream().map(this::getStoreOrderSku).collect(Collectors.toList()));
        }

        return storeOrderSkus;

    }

    /**
     * 获得门店订单下单品的详情信息
     *
     * @param skuResponse 单品信息
     * @return 返回门店订单下单品的详情信息
     */
    private TStoreOrderSku getStoreOrderSku(SkuResponse skuResponse) {
        // 生成门店订单下的单品信息
        TStoreOrderSku storeOrderSku = new TStoreOrderSku();

        // 设置单品的id
        storeOrderSku.setSkuId(skuResponse.getSkuId());

        // 设置购买的数量
        storeOrderSku.setNum(skuResponse.getNum());

        // 设置最终的价格
        storeOrderSku.setPrice(skuResponse.getTotalPrice());

        // 设置单品的价格
        storeOrderSku.setSkuPrice(skuResponse.getPrice());

        // 设置单品的名称
        storeOrderSku.setSkuName(skuResponse.getName());

        // 设置单品的图片
        storeOrderSku.setSkuImage(skuResponse.getImage());

        // 查询单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(skuResponse.getSkuId(), CommonConstant.QUERY_WITH_NO_STORE);
        if (Objects.nonNull(sku)) {
            // 设置单品的编号
            storeOrderSku.setSkuNo(sku.getSkuNo());

            // 设置单品的规格
            storeOrderSku.setSkuSpecs(sku.getSpecValuesString());
        }

        return storeOrderSku;

    }


    /**
     * 计算红包的价格
     *
     * @param storeSubmitOrderParams 提交订单参数
     * @param storeOrders            订单信息
     */
    private void calcStoreRedEnvelopePrice(StoreSubmitOrderParams storeSubmitOrderParams, List<TStoreOrder> storeOrders) {

        log.debug("get storeOrders finish and begin to calc redEnvelope....");

        // 如果没有使用红包 则直接返回
        if (!storeSubmitOrderParams.isUseRedEnvelope()) {
            log.error("storeOrder no use redEnvelope");
            storeOrders.stream().forEach(storeOrder -> {
                //设置订单价格
                if (new BigDecimal(-1).compareTo(storeSubmitOrderParams.getPrice()) != 0) {
                    storeOrder.setPrice(storeSubmitOrderParams.getPrice());
                }
            });
            return;
        }

        String storeRedEnvelopeCode = storeRedEnvelopeService.receiveStoreRedEnvelopeCode(storeSubmitOrderParams.getRedEnvelopeId(), storeSubmitOrderParams.getCustomerId());
        // 如果红包不存在 则直接返回false
        if (StringUtils.isEmpty(storeRedEnvelopeCode)) {
            log.error("receive storeRedEnvelopeCode fail ......");
            throw new ServiceException("R-000014");
        }
        StoreRedEnvelope storeRedEnvelope = storeRedEnvelopeService.queryStoreRedEnvelopeById(storeSubmitOrderParams.getRedEnvelopeId());
        //计算订单总价
        BigDecimal allPrice = storeOrders.stream().map(TStoreOrder::getPrice).reduce(new BigDecimal(0), BigDecimal::add);

        // 判断用户的订单价格是否满足了红包使用的条件
        if (allPrice.compareTo(storeRedEnvelope.getFullPrice()) < 0) {
            log.error("user can not use redEnvelopeCode due to storeOrder money is not enough....");
            return;
        }

        // 如果红包减去的金额比订单的金额都要大 为了防止负数 则将红包减去的订单设置成为订单的金额
        if (storeRedEnvelope.getPrice().compareTo(allPrice) > 0) {
            storeRedEnvelope.setPrice(allPrice);
        }

        // 如果合法则重新计算店铺订单的价格
        storeOrders.stream().forEach(storeOrder -> {
            // 店铺订单红包优惠的价格
            BigDecimal price = (storeOrder.getPrice().divide(allPrice, 10, RoundingMode.HALF_EVEN)).multiply(storeRedEnvelope.getPrice()).setScale(2, RoundingMode.HALF_EVEN);

            // 设置订单使用的红包的券码
            storeOrder.setRedEnvelopeCode(storeRedEnvelopeCode);

            // 设置订单红包的优惠价格
            storeOrder.setRedEnvelopePrice(price);

            //平均分配红包到每个单品
            storeOrder.calcRedEnvelopeEverySkuPrice();

            //设置订单价格
            if (new BigDecimal(-1).compareTo(storeSubmitOrderParams.getPrice()) != 0) {
                storeOrder.setPrice(storeSubmitOrderParams.getPrice());
            } else {
                storeOrder.setPrice(storeOrder.getPrice().subtract(price));
            }

        });

    }
}
