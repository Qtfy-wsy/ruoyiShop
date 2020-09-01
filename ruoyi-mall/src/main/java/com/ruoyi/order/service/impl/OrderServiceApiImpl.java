package com.ruoyi.order.service.impl;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.md5.MD5Utils;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IteratorUtils;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.goods.domain.PmsComment;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsCommentService;
import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.integral.service.PointSetingService;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.RedEnvelopeCode;
import com.ruoyi.marketing.domain.TrySkuApply;
import com.ruoyi.marketing.service.*;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.domain.UmsMemberAddress;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.SmsService;
import com.ruoyi.order.domain.*;
import com.ruoyi.order.service.*;
import com.ruoyi.order.vo.*;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.ITStoreCommentService;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import com.ruoyi.util.ThreadTask;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by 魔金商城 on 17/11/7.
 * 订单服务接口
 */
@Service
public class OrderServiceApiImpl implements OrderServiceApi {

    /**
     * 订单状态map
     */
    private static final Map<String, String> statusMap = new HashMap<>();

    static {
        statusMap.put("1", "待付款");
        statusMap.put("2", "待发货");
        statusMap.put("3", "待收货");
        statusMap.put("4", "已完成");
        statusMap.put("5", "已取消");
        statusMap.put("6", "退款通过");
        statusMap.put("7", "退货通过");
    }

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OrderServiceApiImpl.class);
    /**
     * 注入结算服务接口
     */
    @Autowired
    private SettlementService settlementService;
    /**
     * 注入序号生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;
    /**
     * 注入订单服务接口
     */
    @Autowired
    private IOmsOrderService orderService;
    /**
     * 注入积分设置服务接口
     */
    @Autowired
    private PointSetingService pointSetingService;
    /**
     * 注入用户积分服务接口
     */
    @Autowired
    private CustomerPointService customerPointService;
    /**
     * 注入购物车服务接口
     */
    @Autowired
    private IOmsShoppingCartService shoppingCartService;
    /**
     * 注入购物车聚合服务
     */
    @Autowired
    private ShoppingCartServiceApi shoppingCartServiceApi;
    /**
     * 注入优惠券服务接口
     */
    @Autowired
    private CouponService couponService;
    /**
     * 订单操作日志服务接口
     */
    @Autowired
    private IOmsOrderOperationLogService orderOperatonLogService;
    /**
     * 注入单品评分服务接口
     */
    @Autowired
    private IPmsCommentService commentService;
    /**
     * 注入店铺评分服务接口
     */
    @Autowired
    private ITStoreCommentService storeCommentService;
    /**
     * 注入会员抢购纪录
     */
    @Autowired
    private CustomerPanicRecordService customerPanicRecordService;
    /**
     * 注入会员服务接口
     */
    @Autowired
    private IUmsMemberService customerService;
    /**
     * 注入店铺服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;
    /**
     * 注入红包服务接口
     */
    @Autowired
    private RedEnvelopeService redEnvelopeService;
    /**
     * 试用申请服务接口
     */
    @Autowired
    private TrySkuApplyService trySkuApplyService;
    /**
     * 注入退单聚合服务接口
     */
    @Autowired
    private BackOrderServiceApi backOrderServiceApi;
    /**
     * 注入订单设置服务
     */
    @Autowired
    private IOmsOrderSettingService orderSettingService;
    /**
     * 注入佣金记录服务
     */
    @Autowired
    private IOmsCommissionRecordsService commissionRecordService;
    /**
     * 注入众筹服务接口
     */
    @Resource(name = "crowdFundingService")
    private MarketingService crowdFundingService;
    /**
     * 注入促销查询服务
     */
    @Autowired
    private MarketingQueryService marketingQueryService;

    /**
     * 社区团购服务器接口TODO
     */
    // @Autowired
    // private CommunityBuyService communityBuyService;
    /**
     * 注入退单服务接口
     */
    @Autowired
    private IOmsBackOrderService backOrderService;
    /**
     * 注入短信服务接口
     */
    @Autowired
    private SmsService smsService;
    /**
     * 注入物流公司服务接口
     */
    @Autowired
    private IOmsLogisticsCompanyService logisticsCompanyService;
    /**
     * 注入账单记录服务接口
     */
    @Autowired
    private IOmsBillingRecordsService billingRecordService;

    /**
     * 注入商品服务接口
     */
    @Autowired
    private IPmsGoodsService spuService;

    @Transactional
    @Override
    public SubmitOrderResponse submitOrder(SubmitOrderParams submitOrderParams) {
        if (Objects.isNull(submitOrderParams)) {
            logger.debug("submitOrder fail due to params is empty....");
            return SubmitOrderResponse.buildFail("R-0000011");
        }

        logger.debug("submitOrder and submitOrderParams :{}", submitOrderParams);

        // 获得订单的每个店铺的结算信息
        OrderSettlement orderSettlement = settlementService.orderSettlement(OrderSettlementRequest.build(submitOrderParams.getIds(), submitOrderParams.getSkuInfo(), submitOrderParams.getIsGroup(), submitOrderParams.getGroupId(), submitOrderParams.getAddressId(), submitOrderParams.getCrowdfundingId(), submitOrderParams.getCrowdfundingType(), submitOrderParams.getCrowdfundingMoney()).addCustomerId(submitOrderParams.getCustomerId()));

        // 订单中没有单品信息
        if (!orderSettlement.validateSkus()) {
            logger.error("submitOrder fail due to order has no goods....");
            throw new ServiceException("R-000006");
        }
        //店铺状态异常，没有开店或者已过有效期
        if (orderSettlement.getStoreSettlements().stream().anyMatch(storeSettlement -> !storeInfoService.isEffective(storeSettlement.getStoreId()))) {
            logger.error("submitOrder fail due to have store that is not effective....");
            throw new ServiceException("R-000018");
        }
        // 没有收货地址(众筹的无回报支持不需要收货地址)
        if (!orderSettlement.validateCustomerAddress() && !submitOrderParams.isNoRetrun()) {
            logger.error("submitOrder fail due to has no address...");
            throw new ServiceException("R-000007");
        }

        //如果存在实购数量小于最小起订数量的单品
        if (orderSettlement.existNumLowerThanBatchSkuLimitNumSku()) {
            logger.error("submitOrder fail due to existNumLowerThanBatchSkuLimitNumSku...");
            throw new ServiceException("R-000017");
        }

        // 订单信息
        List<OmsOrder> orders = getOrderInfos(fillOrderSettlement(orderSettlement, submitOrderParams), submitOrderParams);

        // 计算红包的价格
        calcRedEnvelopePrice(submitOrderParams, orders, orderSettlement);

        // 保存订单信息
        saveOrders(orders, StringUtils.isEmpty(submitOrderParams.getSkuInfo()));

        // 返回订单信息
        return getSubmitOrderResponse(orders);
    }

    /**
     * 计算红包的价格
     *
     * @param submitOrderParams 提交订单参数
     * @param orders            订单信息
     * @param orderSettlement   订单信息
     */
    private void calcRedEnvelopePrice(SubmitOrderParams submitOrderParams, List<OmsOrder> orders, OrderSettlement orderSettlement) {

        logger.debug("get orders finish and beign to calc redenvelope....");

        // 如果没有使用红包 则直接返回
        if (!submitOrderParams.hasUseRedEnvelope()) {
            logger.error("order no use redenvelope");
            return;
        }

        // 如果没有可以使用的红包 则直接返回false
        if (CollectionUtils.isEmpty(orderSettlement.getRedEnvelopeCodes())) {
            logger.error("no RedEnvelopes can use.....");
            return;
        }

        // 如果有红包 则找出用户使用的红包
        Optional<RedEnvelopeCode> redEnvelopeCodeOptional = IteratorUtils.filterMatch(orderSettlement.getRedEnvelopeCodes(), redEnvelopeCode1 -> redEnvelopeCode1.getCode().equals(submitOrderParams.getRedEnvelopeCode()));

        // 如果没有找到可以使用的红包 返回false
        if (!redEnvelopeCodeOptional.isPresent()) {
            logger.error("user use RedEnvelope error....");
            return;
        }

        // 用户使用的红包
        RedEnvelopeCode redEnvelopeCode = redEnvelopeCodeOptional.get();

        // 同一个红包的店铺订单
        List<OmsOrder> redEnvelopeOrders = new ArrayList<>();

        // 同一个红包的店铺订单放入一个集合
        redEnvelopeCode.getRedEnvelopeStoreId().forEach(redEnvelopeStoreId -> orders.stream().filter(order -> order.getStoreId() == redEnvelopeStoreId).findFirst().ifPresent(order1 -> redEnvelopeOrders.add(order1)));

        //同一个红包的店铺订单的总价格 (不算运费的)
        BigDecimal allPrice = redEnvelopeOrders.stream().map(OmsOrder::getPriceWhitNoFreightPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        // 判断用户的订单价格是否满足了红包使用的条件
        if (allPrice.compareTo(redEnvelopeCode.getRedEnvelope().getFullPrice()) < 0) {
            logger.error("user can not use redenvelope due to order money is not enough....");
            return;
        }

        // 如果红包减去的金额比订单的金额都要大 为了防止负数 则将红包减去的订单设置成为订单的金额
        if (redEnvelopeCode.getRedEnvelope().getPrice().compareTo(allPrice) > 0) {
            redEnvelopeCode.getRedEnvelope().setPrice(allPrice);
        }

        // 如果合法则重新计算店铺订单的价格
        redEnvelopeOrders.stream().forEach(redEnvelopeOrder -> {
            // 店铺订单红包优惠的价格
            BigDecimal price = (redEnvelopeOrder.getPriceWhitNoFreightPrice().divide(allPrice, 10, RoundingMode.HALF_EVEN)).multiply(redEnvelopeCode.getRedEnvelope().getPrice()).setScale(2, RoundingMode.HALF_EVEN);

            // 设置订单使用的红包的券吗
            redEnvelopeOrder.setRedEnvelopeCode(redEnvelopeCode.getCode());

            // 设置订单红包的优惠价格
            redEnvelopeOrder.setRedEnvelopePrice(price);

            //将红包优惠的价格平均到订单下的单品上
            redEnvelopeOrder.calcRedEnvelopeEverySkuPrice();

            // 总的优惠价上加入红包的优惠价格
            redEnvelopeOrder.setConcessionalRate(redEnvelopeOrder.getConcessionalRate().add(price));

            // 订单不算运费的价格
            BigDecimal orderPrice = redEnvelopeOrder.getPriceWhitNoFreightPrice().subtract(price);

            // 店铺订单的结算信息
            StoreSettlement storeSettlementOrder = orderSettlement.getStoreSettlements().stream().filter(storeSettlement -> storeSettlement.getStoreId().equals(redEnvelopeOrder.getStoreId())).findFirst().get();

            // 不包邮加上运费
            if (!storeSettlementOrder.isFreeShip(orderPrice)) {
                redEnvelopeOrder.setPrice(orderPrice.add(redEnvelopeOrder.getFreightPrice()));
            } else {
                // 包邮免运费
                redEnvelopeOrder.setPrice(orderPrice);
                redEnvelopeOrder.setFreightPrice(new BigDecimal(0));
            }
        });

    }


    @Transactional
    @Override
    public int cancleOrder(CancelOrderParams cancelOrderParams) {
        logger.debug("cancleOrder and  cancelOrderParams {}", cancelOrderParams);

        if (Objects.isNull(cancelOrderParams)) {
            logger.error("cancleOrder fail due to cancelOrderParams is null...");
            return 0;
        }

        // 查询订单信息
        OmsOrder order = this.queryOrderDetailById(cancelOrderParams.getOrderId(), cancelOrderParams.getCustomerId(), cancelOrderParams.getStoreId(), OrderItem.SKUS);

        // 没有查询到订单返回错误
        if (Objects.isNull(order)) {
            logger.error("cancleOrder fail  due to order is not exist");
            return -1;
        }

        //  如果是定金预售订单 则不给取消订单
        if (order.isDepositPresaleOrder()) {
            logger.error("order is DepositPresaleOrder can not cancel order....");
            return -1;
        }

        // 如果订单来自前端  并且订单的会员id和用户的会员id不一致 则直接返回
        if (cancelOrderParams.isFromCustomer() && cancelOrderParams.getCustomerId() != order.getCustomerId()) {
            logger.error("cancleOrder fail due to order customerId is not match...");
            return -2;
        }

        // 取消订单失败
        if (orderService.cancelOrder(cancelOrderParams.getOrderId(), cancelOrderParams.getStoreId(), cancelOrderParams.getReason()) == 0) {
            logger.error("cancleOrder fail....");
            return 0;
        }

        // 返回积分
        if (order.isUsedPoint()) {
            customerPointService.addCustomerPoint(CustomerPoint.buildForCancelOrder(order.getCustomerId(), order.getUsePoint()));
        }

        // 返回优惠券
        if (order.isCouponUsed()) {
            couponService.backCoupon(order.getCustomerId(), order.getCouponNo());
        }

        // 社区团购订单 修改社区团购的单品库存 普通订单修改普通订单的单品库存
        if (order.isCommunityBuyOrder()) {
            // 修改社区团购单品库存
            //    order.getOrderSkus().forEach(orderSku -> communityBuyService.increaseSkuStock(orderSku.getSkuId(), orderSku.getNum(), order.getCommunityBuyId()));
        } else {
            //修改普通单品库存
            order.getOrderSkus().forEach(orderSku -> skuService.increaseSkuStock(orderSku.getSkuId(), orderSku.getNum()));
        }

        // 如果不是前端的订单 则纪录操作日志(只有管理员操作的订单才需要纪录日志)
        if (!cancelOrderParams.isFromCustomer()) {
            orderOperatonLogService.insertOmsOrderOperationLog(OmsOrderOperationLog.buildForCancelOrdere(cancelOrderParams.getOrderId(), cancelOrderParams.getOperationName()));
        }

        return 1;
    }

    @Override
    public int cancelDepositPreSaleOrder(CancelOrderParams cancelOrderParams) {
        if (Objects.isNull(cancelOrderParams)) {
            logger.error("cancelDepositPreSale fail due to cancelOrderParams is null...");
            return 0;
        }

        // 查询订单信息
        OmsOrder order = this.queryOrderDetailById(cancelOrderParams.getOrderId(), cancelOrderParams.getCustomerId(), cancelOrderParams.getStoreId(), OrderItem.SKUS);

        // 没有查询到订单返回错误
        if (Objects.isNull(order)) {
            logger.error("cancelDepositPreSale fail  due to order is not exist");
            return -1;
        }

        // 如果订单来自前端  并且订单的会员id和用户的会员id不一致 则直接返回
        if (cancelOrderParams.isFromCustomer() && cancelOrderParams.getCustomerId() != order.getCustomerId()) {
            logger.error("cancelDepositPreSale fail due to order customerId is not match...");
            return -2;
        }

        // 取消订单失败
        if (orderService.cancelOrder(cancelOrderParams.getOrderId(), cancelOrderParams.getStoreId(), cancelOrderParams.getReason()) == 0) {
            logger.error("cancelDepositPreSale fail....");
            return 0;
        }

        // 返回积分
        if (order.isUsedPoint()) {
            customerPointService.addCustomerPoint(CustomerPoint.buildForCancelOrder(order.getCustomerId(), order.getUsePoint()));
        }

        // 返回优惠券
        if (order.isCouponUsed()) {
            couponService.backCoupon(order.getCustomerId(), order.getCouponNo());
        }

        // 如果不是前端的订单 则纪录操作日志(只有管理员操作的订单才需要纪录日志)
        if (!cancelOrderParams.isFromCustomer()) {
            orderOperatonLogService.insertOmsOrderOperationLog(OmsOrderOperationLog.buildForCancelOrdere(cancelOrderParams.getOrderId(), cancelOrderParams.getOperationName()));
        }

        // 记录账单
        billingRecordService.insertOmsBillingRecords(OmsBillingRecords.buildForCanclePresaleOrder(order));

        return 1;
    }

    @Transactional
    @Override
    public int confirmOrderPayed(ConfirmOrderParams confirmOrderParams) {
        logger.debug("confirmOrderPayed and confirmOrderParams:{}", confirmOrderParams);

        if (Objects.isNull(confirmOrderParams)) {
            logger.error("confirmOrderPayed fail due to confirmOrderParams is null...");
            return 0;
        }

        // 修改结果
        int result;

        // 请求来源前端用户
        if (confirmOrderParams.isFromCustomer()) {
            result = orderService.updateOrderPayed(confirmOrderParams.getOrderCodes(), confirmOrderParams.getCustomerId(), confirmOrderParams.getIsPredepositPay(), confirmOrderParams.getTransCode(), confirmOrderParams.getChannel(), getGroupOrderConsumer(), getCrowdfundingConsumer());
        } else {
            //请求来源后端管理员
            result = orderService.confirmOrder(confirmOrderParams.getId(), confirmOrderParams.getStoreId(), getGroupOrderConsumer(), getCrowdfundingConsumer());
        }

        // 修改失败
        if (result == 0) {
            logger.error("confirmOrderPayed faill...");
            throw new ServiceException("update orderfail....");
        }

        // 修改成功后 如果是管理员确认支付 则纪录操作日志
        if (!confirmOrderParams.isFromCustomer()) {
            orderOperatonLogService.insertOmsOrderOperationLog(OmsOrderOperationLog.buildForConfirmOrder(confirmOrderParams.getId(), confirmOrderParams.getReason(), confirmOrderParams.getOperationName()));
        }

        // 如果是虚拟商品订单 则发送核销短信
        ThreadTask.getInstance().addTask(() -> {
            // 订单信息
            List<OmsOrder> orders = new ArrayList<>();
            if (confirmOrderParams.isFromCustomer()) {
                confirmOrderParams.getOrderCodes().stream().forEach(orderCode -> orders.addAll(orderService.queryOrderByOrderCode(orderCode, confirmOrderParams.getCustomerId(), OrderItem.ATTR)));
            } else {
                orders.add(orderService.queryOrderDetailById(confirmOrderParams.getId(), CommonConstant.QUERY_WITH_NO_CUSTOMER, confirmOrderParams.getStoreId(), OrderItem.ATTR));
            }

            orders.stream().forEach(order -> {
                if (order.isVirtualOrder()) {
                    // 查询店铺信息
                    TStoreInfo storeInfo = storeInfoService.queryStoreInfo(order.getStoreId());
                    smsService.sendVirtualOrderWiteOffCodeSms(order.getOrderAttr().getReceiptMobile(), order.getOrderCode(), order.getWriteOffCode(), Objects.nonNull(storeInfo) ? storeInfo.getStoreName() : "");
                }
            });
        });

        return 1;
    }


    /**
     * 获得众筹订单的回调
     *
     * @return 返回众筹订单的回调
     */
    private Consumer<OmsOrder> getCrowdfundingConsumer() {
        return order -> {
            // 如果不是众筹订单 直接返回
            if (!order.isCrowdfundingOrder()) {
                return;
            }

            // 新增众筹金额
            //  crowdFundingService.addCrowdFundingAlerdyMoney(order.getPrice(), order.getCrowdFundingId());

            // 如果是无回报订单 这边直接新增结算记录
            if (order.isNoReturnOrder()) {
                billingRecordService.insertOmsBillingRecords(OmsBillingRecords.buildForConfrimOrder(setOrderStoreName(order)));
            }
        };
    }

    /**
     * 获得拼团订单的回调
     *
     * @return 返回拼团订单回调接口
     */
    private Consumer<OmsOrder> getGroupOrderConsumer() {
        return order -> {

            // 如果不是拼团订单 直接返回
            if (!order.isGroupOrder()) {
                return;
            }

            // 首先改变拼团订单的状态为未成团
            orderService.updateGroupStatus(order.getId(), "0", order.isNeedUpdateTime());

            //将用户这团团的还未支付的订单改成取消订单 因为同一个团用户只能参加一次
            orderService.calcOrderByGroupIdAndCustomerId(order.getGroupId(), order.getCustomerId());
            // 如果达到了成团的条件 则将团修改成为已成团
            if (order.getGroupNum() == orderService.queryGroupNum(order.getGroupId())) {
                orderService.updateGroupSuccess(order.getGroupId());
            }

        };
    }

    @Transactional
    @Override
    public int addOrderEvaluation(Evaluation evaluationParams) {
        logger.debug("addOrderEvaluation and evaluationParams:{}", evaluationParams);

        if (Objects.isNull(evaluationParams)) {
            logger.error("addOrderEvaluation fail due to params is null...");
            return 0;
        }

        // 查询需要评分的订单信息
        OmsOrder order = this.queryOrderDetailById(evaluationParams.getOrderId(), evaluationParams.getCustomerId(), CommonConstant.QUERY_WITH_NO_STORE);

        // 如果没有查询到订单信息 则直接返回
        if (Objects.isNull(order)) {
            logger.debug("addOrderEvaluation fail due to order is not exist....");
            return -1;
        }

        // 判断订单是否可以评论的状态
        if (!order.isCanEvaluation()) {
            logger.error("addOrderEvaluation fail due to order status is error...");
            return -2;
        }

        // 设置店铺id和会员id
        evaluationParams.setStoreIdAndCustomerId(order.getStoreId());

        // 新增单品评分
        commentService.addComments(evaluationParams.getComments());

        // 新增店铺评分
        storeCommentService.insertTStoreComment(evaluationParams.getStoreComment());

        // 修改订单状态为已评论
        if (orderService.updateOrderEvluation(evaluationParams.getOrderId(), evaluationParams.getCustomerId()) == 0) {
            logger.error("addOrderEvaluation fail due to update orderStatus fail...");
            // 修改失败 抛出异常回滚
            throw new ServiceException("R-000010");
        }

        return 1;
    }

    @Override
    public Evaluation queryOrderEvaluation(long orderId, long customerId) {
        logger.debug("queryOrderEvaluation and orderId:{} \r\n customerId:{}", orderId, customerId);

        // 订单信息
        OmsOrder order = this.queryOrderDetailById(orderId, customerId, CommonConstant.QUERY_WITH_NO_STORE, OrderItem.SKUS);

        if (Objects.isNull(order) || order.getEvaluationStatus().equals("1")) {
            logger.error("queryOrderEvaluation due to order is not exist....");
            return null;
        }

        // 单品评论
        List<PmsComment> comments = commentService.queryByOrderIdAndCustomerId(orderId, customerId);

        // 评论不为空 则设置单品信息
        if (!CollectionUtils.isEmpty(comments)) {
            IteratorUtils.zip(comments, order.getOrderSkus(), (comment, orderSku) -> comment.getSkuId().equals(orderSku.getSkuId()), (comment1, orderSku1) -> {
                PmsSku sku = new PmsSku();
                sku.setName(orderSku1.getSkuName());
                sku.setUrl(orderSku1.getSkuImage());
                sku.setPrice(orderSku1.getPrice());
                sku.setSpecValues(orderSku1.getSkuSpecs());
                comment1.setSku(sku);
            });
        }


        return Evaluation.build(comments, storeCommentService.queryByOrderIdAndCutomerId(orderId, customerId));
    }


    @Transactional
    @Override
    public int buyAgain(long orderId, long customerId) {
        logger.debug("buyAgain and orderId:{},customerId:{}", orderId, customerId);
        OmsOrder order = this.queryOrderDetailById(orderId, customerId, CommonConstant.QUERY_WITH_NO_STORE, OrderItem.SKUS);

        // 订单或者订单下的单品不存在 直接返回
        if (Objects.isNull(order) || CollectionUtils.isEmpty(order.getOrderSkus())) {
            logger.error("buyAgain fail due to order is not exist..");
            return -7;
        }

        for (OmsOrderSku orderSku : order.getOrderSkus()) {
            int result = this.addOrderSkuToShoppingCart(orderSku, customerId);
            if (result != 1) {
                logger.debug("addToShoppingCartFail...and skuId:{}", orderSku.getSkuId());
                throw new ServiceException(String.valueOf(result), "");
            }
        }

        return 1;
    }

    @Transactional
    @Override
    public int confirmReceipt(long orderId, long customerId) {
        logger.debug("confirmReceipt and orderId:{} \r\n customerId:{}", orderId, customerId);

        // 查询订单信息
        OmsOrder order = this.queryOrderDetailById(orderId, customerId, CommonConstant.QUERY_WITH_NO_STORE, OrderItem.SKUS);

        // 如果订单信息没有 则直接返回
        if (Objects.isNull(order)) {
            logger.error("confirmReceipt fail due to no order exsit");
            return 0;
        }

        // 社区团购订单 不需要发货直接确认收货
        if (order.isCommunityBuyOrder()) {
            // 确认收货失败 则直接返回
            if (orderService.confirmCommunityOrderReceipt(orderId, customerId) == 0) {
                logger.error("confirmReceipt due to order statsu is error....");
                return 0;
            }
        } else {
            // 普通订单发货必须是订单状态为待发货状态 确认收货失败 则直接返回
            if (orderService.confirmReceipt(orderId, customerId) == 0) {
                logger.error("confirmReceipt due to order statsu is error....");
                return 0;
            }
        }

        // 改变会员消费总金额
        customerService.updateCustomerConsumptionAmount(customerId, order.getPrice());

        // 如果是佣金订单并且需要计算佣金（众筹订单只有全款需要计算佣金） 则修改推荐人的佣金
        if (order.hasRecommendedCommission() && order.isNeedCommission()) {
            logger.debug("order has recommonded and to update recommond commission");
            //添加佣金记录CommissionRecord
            commissionRecordService.insertOmsCommissionRecords(OmsCommissionRecords.buildForAdd(order.getRecommended(), order.getOrderCommission(), null, order.getOrderCode()));
            //更新佣金
            customerService.updateCustomerCommission(order.getRecommended(), order.getOrderCommission());

            // 新增分销订单账单记录
            billingRecordService.insertOmsBillingRecords(OmsBillingRecords.buildForRecommondOrder(setOrderStoreName(order)));

            // 如果订单有二级推荐人
            if (order.hasSRecommondedCommission()) {

                // 添加二级佣金记录
                commissionRecordService.insertOmsCommissionRecords(OmsCommissionRecords.buildForAdd(order.getSRecommended(), order.getOrderSCommission(), null, order.getOrderCode()));

                // 更新二级会员佣金
                customerService.updateCustomerCommission(order.getSRecommended(), order.getOrderSCommission());

                // 新增二级分销订单账单记录
                billingRecordService.insertOmsBillingRecords(OmsBillingRecords.buildForSRecommondOrder(setOrderStoreName(order)));
            }
        }

        // 新增账单记录
        billingRecordService.insertOmsBillingRecords(OmsBillingRecords.buildForConfrimOrder(setOrderStoreName(order)));

        return 1;
    }

    @Override
    public OmsOrder queryOrderDetailById(long id, long customerId, long storeId, OrderItem... orderItems) {
        logger.debug("queryOrderDetailById and id:{} \r\n customerId:{} \r\n storeId:{} \r\n orderItems:{}", id, customerId, storeId, orderItems);

        // 查询订单详情
        OmsOrder order = orderService.queryOrderDetailById(id, customerId, storeId, orderItems);

        if (Objects.isNull(order)) {
            logger.error("order is not exist");
            return order;
        }

        // 填充店铺信息
        if (ArrayUtils.contains(orderItems, OrderItem.STORE_INFO)) {
            TStoreInfo storeInfo = storeInfoService.queryStoreInfo(order.getStoreId());
            if (Objects.nonNull(storeInfo)) {
                order.setStoreName(storeInfo.getStoreName());
            }
        }

        // 填充会员信息
        if (ArrayUtils.contains(orderItems, OrderItem.CUSTOMER)) {
            UmsMember customer = customerService.queryCustomerWithNoPasswordById(order.getCustomerId());
            if (Objects.nonNull(customer)) {
                order.setCustomerName(customer.getUsername());
                order.setCustomerMobile(customer.getMobile());
            } else {
                order.setCustomerName("");
                order.setCustomerMobile("");
            }
        }

        // 填充社区团购团长信息
        if (ArrayUtils.contains(orderItems, OrderItem.COMMUNITYHEAD)) {
            UmsMember customer = customerService.queryCustomerWithNoPasswordById(order.getCommunityBuyCustomerId());
            if (Objects.nonNull(customer)) {
                order.setCommunityBuyHeadMobile(customer.getMobile());
            } else {
                order.setCommunityBuyHeadMobile("");
            }
        }

        return order;
    }


    @Override
    public Void exportCheckedOrder(OutputStream outputStream, String status, Long[] ids, long storeId) {
        logger.debug("exportCheckedOrder and ids:{} \r\n storeId:{} \r\n status:{}", Arrays.toString(ids), storeId, status);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("exportCheckedOrder fail :ids is empty");
            return null;
        }
        exportOrder(orderService.queryOrdersByIdsWithOrderSku(Arrays.asList(ids), status, storeId), outputStream);
        return null;
    }

    @Override
    public Void exportAllOrder(OutputStream outputStream, String status, long storeId, Long marketingId) {
        logger.debug("exportAllOrder and storeId:{} \r\n status:{} \r\n marketingId:{}", storeId, status, marketingId);
        exportOrder(orderService.queryAllOrderWithOrderSku(status, storeId, marketingId), outputStream);
        return null;
    }

    @Override
    public Void exportCheckedGroupOrder(OutputStream outputStream, String status, Long[] ids, long storeId) {
        logger.debug("exportCheckedGroupOrder and status:{} \r\n ids:{} \r\n storeId:{}", status, Arrays.toString(ids), storeId);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("exportCheckedGroupOrder fail :ids is empty");
            return null;
        }
        if ("-1".equals(status)) {
            //未付款的按普通订单导出
            exportOrder(orderService.queryNotPayGroupOrdersByIdsWithOrderSku(Arrays.asList(ids), storeId), outputStream);
        } else {
            exportGroupOrder(orderService.queryGroupOrdersByIdsWithOrderSku(Arrays.asList(ids), status, storeId), outputStream);
        }
        return null;
    }

    @Override
    public Void exportAllGroupOrder(OutputStream outputStream, String status, long storeId) {
        logger.debug("exportAllGroupOrder and status:{} \r\n storeId:{}", status, storeId);
        if ("-1".equals(status)) {
            //未付款的按普通订单导出
            exportOrder(orderService.queryAllNotPayGroupOrderWithOrderSku(storeId), outputStream);
        } else {
            exportGroupOrder(orderService.queryAllGroupOrderWithOrderSku(status, storeId), outputStream);
        }
        return null;
    }

    @Override
    public PageHelper<OmsOrder> querySpreadOrdersForBack(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria) {
        logger.debug("querySpreadOrdersForBack and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        if ("商城自营".equals(queryCriteria.getStoreName())) {
            logger.info("querySpreadOrdersForBack and storeName is '商城自营'");
            queryCriteria.setStoreName(null);
            queryCriteria.setStoreId(0);
        }
        return pageHelper.setListDates(orderService.querySpreadOrders(pageHelper, queryCriteria).getList().stream().map(order -> {
            UmsMember customer = customerService.queryCustomerWithCustomerLevel(order.getCustomerId());
            if (Objects.nonNull(customer)) {
                order.setCustomerName(customer.getUsername());
            }
            return order;
        }).collect(Collectors.toList()));
    }

    @Override
    public int deliverOrder(long id, long storeId, String waybillCode, String operationName, long companyId) {
        logger.debug("deliverOrder and id:{} \r\n storeId:{} \r\n waybillCode:{} \r\n operationName \r\n companyId:{}", id, storeId, waybillCode, operationName, companyId);


        //判断订单号是否含有中文，含有返回-1
        if (MD5Utils.getInstance().isContainChinese(waybillCode)) {
            logger.error("deliverOrder fail due to waybillCode has Chinese....");
            return -1;
        }

        // 判断是否可以发货
        int result = isCanDeliverOrder(id);

        if (result != 0) {
            logger.error("deliverOrder fail ....");
            return result;
        }

        // 查询物流公司信息
        OmsLogisticsCompany logisticsCompany = logisticsCompanyService.selectOmsLogisticsCompanyById(companyId);

        // 如果物流公司不存在则直接返回
        if (Objects.isNull(logisticsCompany)) {
            logger.error("deliverOrder fail due to logisticsCompany is not exist.....");
            return -6;
        }


        // 发货失败  (这边失败主要是订单状态不对)
        if (orderService.deliverOrder(id, storeId, waybillCode, logisticsCompany.getName(), logisticsCompany.getCode()) == 0) {
            logger.error("deliverOrder fail....");
            return 0;
        }

        orderOperatonLogService.insertOmsOrderOperationLog(OmsOrderOperationLog.buildForDeliverOrder(id, operationName));
        return 1;
    }


    @Override
    public int writeOffVirtualOrder(long id, long storeId, String writeOffCode, String operationName) {
        logger.debug("writeOffVirtualOrder and id :{} \r\n storeId:{} \r\n writeOffCode:{} \r\n operationName:{}");

        // 查询订单信息
        OmsOrder order = orderService.queryOrderDetailById(id, CommonConstant.QUERY_WITH_NO_CUSTOMER, CommonConstant.QUERY_WITH_NO_STORE);

        // 核销吗没有直接返回失败
        if (StringUtils.isEmpty(writeOffCode)) {
            logger.error("writeOffVirtualOrder fail due to writeOffCode is null....");
            return -1;
        }

        // 没有订单信息直接返回失败
        if (Objects.isNull(order)) {
            logger.error("writeOffVirtualOrder fail due to no order exist....");
            return 0;
        }

        // 核销吗错误 直接返回
        if (!writeOffCode.equals(order.getWriteOffCode())) {
            logger.error("writeOffVirtualOrder fail due to writeOffCode is error....");
            return -1;
        }

        // 修改订单为发货状态
        orderService.deliverOrder(order.getId(), order.getStoreId(), "", "", "");

        // 将订单修改成确认收货状态
        orderOperatonLogService.insertOmsOrderOperationLog(OmsOrderOperationLog.buildForWriteOffOrder(id, operationName));

        // 确认收货订单
        return this.confirmReceipt(id, order.getCustomerId());
    }

    /**
     * 判断该订单是否可以发货
     *
     * @param id 订单id
     * @return -5 订单不存在 -3 拼团未成功 -4 众筹未成功 0 可以返回
     */
    private int isCanDeliverOrder(long id) {

        // 查询订单信息
        OmsOrder order = orderService.queryOrderDetailById(id, CommonConstant.QUERY_WITH_NO_CUSTOMER, CommonConstant.QUERY_WITH_NO_STORE);

        if (Objects.isNull(order)) {
            logger.error("isCanDeliverOrder fail due to order is not exist and id:{}", id);
            return -5;
        }

        //  如果是拼团订单 则只有拼团成功了才能发货
        if (!order.isCanDeliverOrder()) {
            logger.error("can not DeliverOrder due to group order has not finished....");
            return -3;
        }

        // 如果是众筹订单 只有众筹成功后才能发货
      /*  if (order.isCrowdfundingOrder() && !hasCrowdfundingSuccess(order.getCrowdFundingId())) {
            logger.error("can not DeliverOrder due to Crowdfunding is not success");
            return -4;
        }*/


        // 根据订单id查询该订单的退单纪录
        List<OmsBackOrder> backOrders = backOrderService.queryByOrderId(id, CommonConstant.QUERY_WITH_NO_CUSTOMER);

        // 如果没有退单纪录 则直接返回true
        if (CollectionUtils.isEmpty(backOrders)) {
            return 0;
        }

        // 没有退单纪录是成功或者进行中的 则就可以发货 返回true 否则返回false
        return CollectionUtils.isEmpty(backOrders.stream().filter(OmsBackOrder::isRefundSuccessOrProcess).collect(Collectors.toList())) ? 0 : -2;

    }

    /**
     * 判断众筹是否成功
     *
     * @param marketingId 众筹id
     * @return 成功返回true  是否返回false
     */
    private boolean hasCrowdfundingSuccess(long marketingId) {
        // 根据众筹id查询到众筹信息
        Marketing marketing = marketingQueryService.queryMarketingById(marketingId, CommonConstant.QUERY_WITH_NO_STORE);

        // 没有促销信息直接返回
        if (Objects.isNull(marketing) || Objects.isNull(marketing.getCrowdFunding())) {
            logger.error("hasCrowdfundingSuccess fail due to no marketing info.....");
            return false;
        }

        return marketing.getCrowdFunding().isSuccess(marketing.getEndTime());
    }

    /**
     * 将订单下的单品加入购物车
     *
     * @param orderSku   订单下的单品
     * @param customerId 会员id
     * @return 返回码: 1 成功 0 失败 -1 库存不足  -2 单品不存在 -3 参数错误 -4 单品已下架 -5 超出商品抢购限购数量 -6 预售商品不能加入购物车
     */
    private int addOrderSkuToShoppingCart(OmsOrderSku orderSku, long customerId) {
        return shoppingCartServiceApi.addShoppingCart(OmsShoppingCart.build(orderSku.getSkuId(), orderSku.getNum(), customerId));
    }


    /**
     * 获得订单返回信息
     *
     * @param orders 订单信息
     * @return 返回订单信息
     */
    private SubmitOrderResponse getSubmitOrderResponse(List<OmsOrder> orders) {

        // 不需要付款的订单
        List<OmsOrder> NoNeedPayOrders = orders.stream().filter(OmsOrder::isNoNeedPay).collect(Collectors.toList());

        // 需要付款的订单
        List<OmsOrder> needPayOrders = orders.stream().filter(order -> !order.isNoNeedPay()).collect(Collectors.toList());

        // 如果有不需要付款的订单(并且订单状态为待支付的，因为货到付款的订单也不需要付款但是状态是待发货) 则修改不需要付款订单的状态为已支付（其实就是订单金额为0的订单）
        if (!CollectionUtils.isEmpty(NoNeedPayOrders)) {

            NoNeedPayOrders.stream().filter(OmsOrder::isWaitToPay).forEach(order -> this.confirmOrderPayed(ConfirmOrderParams.
                    buildCustomerSource(order.getCustomerId(), 0, Arrays.asList(order.getOrderCode()), "", "")));
        }

        // 没有需要付款的订单 直接返回
        if (CollectionUtils.isEmpty(needPayOrders)) {
            return SubmitOrderResponse.buildNoNeedPayResponse(NoNeedPayOrders.stream().map(OmsOrder::getPrice).reduce(new BigDecimal(0), BigDecimal::add), NoNeedPayOrders.get(0).getOrderCode());
        }

        // 需要支付的金额
        BigDecimal orderMoney;

        if (!CollectionUtils.isEmpty(needPayOrders.stream().filter(OmsOrder::isDepositPresaleOrder).collect(Collectors.toList()))) {
            orderMoney = needPayOrders.stream().map(OmsOrder::getPresalePrice).reduce(new BigDecimal(0), BigDecimal::add);
        } else {
            orderMoney = needPayOrders.stream().map(OmsOrder::getPrice).reduce(new BigDecimal(0), BigDecimal::add);
        }

        // 有需要付款的订单 则返回需要付款的实体  注意 订单号拿的的主订单号
        return SubmitOrderResponse.buildNeedPayResponse(needPayOrders.get(0).getMasterOrderCode(), orderMoney);
    }

    /**
     * 保存订单信息
     *
     * @param orders 订单
     */
    @Override
    public void saveOrders(List<OmsOrder> orders, boolean needCheckShoppingCartDelNum) {
        logger.debug("saveOrders and orders:{}", orders);

        // 保存订单
        orders.forEach(order -> saveOrder(order, needCheckShoppingCartDelNum));
    }

    @Transactional
    @Override
    public int autoCancelGroupOrder() {
        logger.debug("autoCancelGroupOrder......");
        // 查询已经开团24小时以上的（未成团或未支付）的未处理拼团订单
        orderService.queryOpenGroupOrderForCancel().stream().forEach(groupOrder -> {
            cancelGroupOrder(groupOrder);
            //查询团员订单
            orderService.queryGroupMemberOrders(groupOrder.getGroupId(), false).stream().forEach(groupOrder2 ->
                    cancelGroupOrder(groupOrder2)
            );
        });
        // 查询创建时间24小时以上的未支付的未处理拼团订单
        orderService.queryNotOpenGroupOrderForCancel().forEach(groupOrder -> {
            //取消订单
            orderService.cancelOrder(groupOrder.getId(), groupOrder.getStoreId(), "0");
            //更改订单为已处理状态
            orderService.updateAutoHandleStatus(groupOrder.getId());
        });

        return 1;
    }

    @Override
    @Transactional
    public int autoCancelDepositPreSaleOrder() {
        logger.debug("autoCancelDepositPreSaleOrder......");
        //查询超过第二阶段付款时间24小时以上的定金预售订单
        List<OmsOrder> orderList = orderService.queryDepositPreSaleOrdersForCancel();
        if (!CollectionUtils.isEmpty(orderList)) {
            //批量取消订单
            orderList.forEach(order -> cancelDepositPreSaleOrder(CancelOrderParams.buildAutoCancelSource(order.getId(), order.getStoreId())));
        }
        return 1;
    }

    @Override
    public int autoConfirmReceipt() {
        logger.debug("autoConfirmReceipt......");
        OmsOrderSetting orderSetting = orderSettingService.selectOmsOrderSettingById(1L);
        if (ObjectUtils.isEmpty(orderSetting)) {
            logger.error("autoConfirmReceipt fail : no orderSetting");
            return -1;
        }
        List<OmsOrder> orderList = orderService.queryOrdersForConfirmReceipt(orderSetting.getAotuConfirm());
        if (!CollectionUtils.isEmpty(orderList)) {
            //批量确认收货
            orderList.forEach(order -> confirmReceipt(order.getId(), order.getCustomerId()));
        }
        return 1;
    }


    @Override
    public int autoCancelOrder() {
        logger.debug("autoCancelOrder......");
        List<OmsOrder> orderList = orderService.queryOrdersForCancel();
        if (!CollectionUtils.isEmpty(orderList)) {
            //批量取消订单
            orderList.forEach(order -> cancleOrder(CancelOrderParams.buildAutoCancelSource(order.getId(), order.getStoreId())));
        }
        return 1;
    }

    @Override
    @Transactional
    public int autoHandleCrowdFundingOrder() {
        logger.debug("autoHandleCrowdFundingOrder......");
        List<Marketing> marketingList = marketingQueryService.queryEndCrowdFundingMarketingList();
        marketingList.forEach(this::dealEndCrowdFundingOrder);
        return 1;
    }


    /**
     * 处理已结束的众筹订单
     *
     * @param marketing 众筹促销详情
     */
    private void dealEndCrowdFundingOrder(Marketing marketing) {
        List<OmsOrder> orderList = orderService.queryAllCrowdFundingOrderList(marketing.getId(), CommonConstant.QUERY_WITH_NO_STORE);
        //取消待付款的订单
        orderList.stream().filter(OmsOrder::isWaitToPay).forEach(order -> cancleOrder(CancelOrderParams.buildAutoCancelSource(order.getId(), order.getStoreId())));
        // 已经付款待发货的订单
        List<OmsOrder> toDeliverOrder = orderList.stream().filter(OmsOrder::isToDeliver).collect(Collectors.toList());
        // 众筹成功
        if (marketing.getCrowdFunding().isSuccess(marketing.getEndTime())) {
            //如果一元抽奖开启
            if (marketing.getCrowdFunding().isLotteryOpen()) {
                //众筹成功，进行一元支持的抽奖
                List<OmsOrder> oneCoinSupportOrder = toDeliverOrder.stream().filter(OmsOrder::isOneCoinSupportType).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(oneCoinSupportOrder)) {
                    Collections.shuffle(oneCoinSupportOrder);
                    //抽奖人数
                    int extractNum = marketing.getCrowdFunding().getLotteryNum();
                    if (extractNum > oneCoinSupportOrder.size()) {
                        extractNum = oneCoinSupportOrder.size();
                    }
                    IntStream.range(0, extractNum).forEach(index -> orderService.updateCrowdFundingOrderLotteryStatus(oneCoinSupportOrder.get(index).getId()));
                }
            }

        } else {
            //众筹失败，将全款支持的订单生成退款单
            toDeliverOrder.stream().filter(OmsOrder::isFullSupportType).forEach(order -> backOrderServiceApi.crowdFundingFailApplyRefundOrder(order.getCustomerId(), order.getId(), "9", "众筹失败，系统自动申请退款"));
        }
        //更新众筹促销已处理状态
        crowdFundingService.updateCrowdFundingAutoHandleStatus(marketing.getId());
    }

    /**
     * 取消拼团订单
     *
     * @param groupOrder 拼团订单实体
     */
    private void cancelGroupOrder(GroupOrder groupOrder) {
        if (groupOrder.isInGroup()) {
            //申请退款
            backOrderServiceApi.applyRefundOrder(groupOrder.getCustomerId(), groupOrder.getId(), "9", "系统自动申请退款");
        } else {
            //取消订单
            orderService.cancelOrder(groupOrder.getId(), groupOrder.getStoreId(), "0");
        }
        //更改订单为已处理状态
        orderService.updateAutoHandleStatus(groupOrder.getId());
    }


    /**
     * 保存订单
     *
     * @param order 订单信息
     */
    private void saveOrder(OmsOrder order, boolean needCheckShoppingCartDelNum) {

        logger.debug("saveOrder and order:{}", order);

        // 如果是无回报支持的订单 不需要减去商品的库存
        if (!order.isNoReturnOrder()) {

            // 社区团购订单
            if (order.isCommunityBuyOrder()) {
                // 减去订单下单品的库存
                reduceCommunityBuySkusStock(order.getOrderSkus(), order.getCommunityBuyId());
            } else {
                // 减去订单下单品的库存
                reduceSkusStock(order.getOrderSkus());
            }
        }

        // 保存订单信息
        orderService.saveOrder(order);

        // 更新用户的申请试用促销信息
        updateTryApplyOrderd(order.getOrderSkus());

        // 减去用户可以使用的抢购数量
        reduceSkusPanicLimitNum(order.getOrderSkus());

        // 删除购物车信息
        int delNum = shoppingCartService.deleteShoppingCart(order.getCustomerId(), order.getCartIds());

        if (0 == delNum && needCheckShoppingCartDelNum) {
            logger.error("submitOrder fail due to resubmit...");
            throw new ServiceException("R-000019");
        }

        // 用户使用了积分则扣除积分
        if (order.isUsedPoint()) {
            customerPointService.addCustomerPoint(CustomerPoint.buildForOrder(order.getCustomerId(), order.getUsePoint()));
        }

        // 用户使用了优惠券则设置优惠券为已使用
        if (order.isCouponUsed()) {
            couponService.setCouponUsed(order.getCustomerId(), order.getCouponNo(),order.getId());
        }

        // 用户使用了红包 则设置红包为已使用
        if (order.isRedEnvelopeUsed()) {
            redEnvelopeService.setRedEnvelopeUsed(order.getCustomerId(), order.getRedEnvelopeCode());
        }

    }

    /**
     * 更新用户的申请促销为已下单
     *
     * @param orderSkus 订单单品
     */
    private void updateTryApplyOrderd(List<OmsOrderSku> orderSkus) {

        // 订单单品中有试用申请的订单单品
        List<OmsOrderSku> hasTryApplyOrderSku = orderSkus.stream().filter(orderSku -> orderSku.getApplyTryId() != CommonConstant.NO_SKU_TRY_APPLY).collect(Collectors.toList());

        // 如果没有申请试用的订单单品 则直接返回
        if (CollectionUtils.isEmpty(hasTryApplyOrderSku)) {
            logger.debug("order sku has no tryapply......");
        } else {
            // 有申请试用的订单单品 则更新申请表为已下单
            trySkuApplyService.updateTryApplyOrderd(hasTryApplyOrderSku.stream().map(this::getTrySkuApply).collect(Collectors.toList()));
        }
    }

    /**
     * 根据订单单品 获得单品的试用申请
     *
     * @param orderSku 订单单品
     * @return 返回单品的试用申请
     */
    private TrySkuApply getTrySkuApply(OmsOrderSku orderSku) {
        TrySkuApply trySkuApply = new TrySkuApply();
        trySkuApply.setCustomerId(orderSku.getCustomerId());
        trySkuApply.setId(orderSku.getApplyTryId());
        trySkuApply.setOrderId(orderSku.getOrderId());
        trySkuApply.setSkuId(orderSku.getSkuId());
        return trySkuApply;
    }


    /**
     * 减去用户可以使用的抢购数量
     *
     * @param orderSkus 单品信息
     */
    private void reduceSkusPanicLimitNum(List<OmsOrderSku> orderSkus) {
        logger.debug("reduceSkusPanicLimitNum and orderSkus:{}", orderSkus);
        // 有抢购的订单单品信息
        List<OmsOrderSku> hasPanicOrderSkus = orderSkus.stream().filter(OmsOrderSku::isHasPanic).collect(Collectors.toList());

        // 如果没有有抢购的订单单品信息 则直接返回
        if (CollectionUtils.isEmpty(hasPanicOrderSkus)) {
            logger.info("this is no sku has panic and return ...");
            return;
        }

        hasPanicOrderSkus.stream().forEach(orderSku -> {
            // 如果用户的抢购数量被限制则抛出异常
            if (customerPanicRecordService.updateCustomerPanicRecord(orderSku) == 0) {
                throw new ServiceException("R-000011");
            }
        });

    }

    /**
     * 减去单品的库存
     *
     * @param orderSkus 单品信息
     */
    private void reduceSkusStock(List<OmsOrderSku> orderSkus) {
        logger.debug("reduceSkusStock and orderSkus:{}", orderSkus);
        // 首先减去订单下每个单品的库存
        orderSkus.stream().forEach(orderSku -> {
            // 减去订单下单品的库存 如果库存不足 则抛出异常
            if (skuService.reduceSkuStock(orderSku.getSkuId(), orderSku.getNum()) == 0) {
                throw new ServiceException("R-000005");
            }
        });
    }

    /**
     * 减去社区团购单品的库存
     *
     * @param orderSkus      单品信息
     * @param communityBuyId 团购id
     */
    private void reduceCommunityBuySkusStock(List<OmsOrderSku> orderSkus, long communityBuyId) {
        logger.debug("reduceCommunityBuySkusStock and orderSkus:{}", orderSkus);
        orderSkus.stream().forEach(orderSku -> {
            // 减去订单下单品的库存 如果库存不足 则抛出异常
            /*if (communityBuyService.reduceSkuStock(orderSku.getSkuId(), orderSku.getNum(), communityBuyId) == 0) {
                throw new ServiceException("R-000005");
            }*/
        });
    }


    /**
     * 填充结算信息 主要是设置 1.使用的优惠券 2.使用的积分 3.支付方式
     *
     * @param orderSettlement   订单结算信息
     * @param submitOrderParams 用户提交的参数
     */
    private OrderSettlement fillOrderSettlement(OrderSettlement orderSettlement, SubmitOrderParams submitOrderParams) {

        logger.debug("fillOrderSettlement and orderSettlement:{} \r\n submitOrderParams:{}", orderSettlement, submitOrderParams);

        IteratorUtils.zip(orderSettlement.getStoreSettlements(), submitOrderParams.getStoreInfos(), (x, y) -> x.getStoreId().equals(y.getStoreId()), (storeSettlement, storeInfo) -> {

            // 如果可以使用积分 则设置用户使用的积分
            if (storeSettlement.isPointCanUse()) {
                // 设置用户使用的积分
                storeSettlement.setUsePoints(storeInfo.getUsePoints());
            }

            // 如果支付货到付款 则设置用户的支付方式
            if ("0".equals(storeSettlement.getCashonDelivery())) {
                // 设置用户选择的支付类型
                storeSettlement.setChoosedPayType(storeInfo.getPayType());
            }

            // 设置用户使用的优惠券
            storeSettlement.setCanUseCoupon(storeInfo.getCouponCode());

            // 设置备注信息
            storeSettlement.setRemark(storeInfo.getRemark());
        });

        return orderSettlement;
    }


    /**
     * 获得订单信息
     *
     * @param orderSettlement   订单结算
     * @param submitOrderParams 用户提交订单的参数
     * @return 返回订单信息
     */
    private List<OmsOrder> getOrderInfos(OrderSettlement orderSettlement, SubmitOrderParams submitOrderParams) {
        // 主订单号,必须放在这里
        Long masterOrderCode = snowflakeIdWorker.nextId();
        return orderSettlement.getStoreSettlements().parallelStream().
                map(storeSettlement -> getOrderDetail(masterOrderCode, storeSettlement, submitOrderParams, orderSettlement.getCustomerAddress())).collect(Collectors.toList());

    }

    /**
     * 获得订单类型
     *
     * @param storeSettlement 结算信息
     * @return 返回订单类型
     */
    private String getOrderType(StoreSettlement storeSettlement) {
        return storeSettlement.hasGroupMarekting() ? "3" : storeSettlement.hasPreSaleMarketing() ? storeSettlement.getPreMarketing().isDepositPreSaleMarketing() ? "1" : "2" : storeSettlement.hasCrowdfunding() ? storeSettlement.getCrowdfundingType() : storeSettlement.isVirtualSettlment() ? "7" : "0";
    }


    /**
     * 获得订单的详细信息
     *
     * @param masterOrderId     主订单号
     * @param storeSettlement   结算信息
     * @param submitOrderParams 提交订单的参数
     * @param customerAddress   用户的收货地址
     * @return 返回订单详情
     */
    private OmsOrder getOrderDetail(Long masterOrderId, StoreSettlement storeSettlement, SubmitOrderParams submitOrderParams, UmsMemberAddress customerAddress) {

        // 生成订单
        OmsOrder order = new OmsOrder();

        // 设置这个订单下的购物车id
        order.setCartIds(storeSettlement.getCartIds());

        // 设置订单的用户id
        order.setCustomerId(submitOrderParams.getCustomerId());

        // 设置订单号
        order.setOrderCode(String.valueOf(snowflakeIdWorker.nextId()));

        // 设置主订单号
        order.setMasterOrderCode(String.valueOf(masterOrderId));

        // 设置订单类型
        order.setOrderType(getOrderType(storeSettlement));

        //设置众筹id
        //  order.setCrowdFundingId(storeSettlement.getCrowdfundingId());

        // 订单的原始价格(每个单品详情页面的价格)
        order.setOriginalPrice(storeSettlement.getOriginTotalPrice());

        // 设置优惠券抵消的价格
        order.setCouponPrice(storeSettlement.getUseCouponPrice());

        // 订单减去优惠券的价格
        BigDecimal totalPrice = calcPriceAfterCoupon(storeSettlement.getTotalPrice(), order.getCouponPrice());

        // 如果用户使用了积分 则设置用户使用的积分
        if (storeSettlement.isUsedPoint()) {
            order.setUsePoint(getCustomerCanUsePoints(submitOrderParams.getCustomerId(), totalPrice, storeSettlement.getUsePoints()));
            // 设置积分抵消的价格
            order.setPointPrice(getPointPrice(order.getUsePoint()));

            // 订单减去积分的价格
            totalPrice = calcPriceAfterPoint(totalPrice, order.getPointPrice());

            // 设置使用积分的数量
            order.setUsePoint(storeSettlement.getUsePoints());
        }

        // 如果是预售订单
        if (storeSettlement.hasPreSaleMarketing()) {
            // 设置预售的时间
            order.setPresaleTime(storeSettlement.getPreMarketing().getEndTime());
            // 如果是定金预售 则设置第一阶段应该付款的金额
            if (storeSettlement.hasDepositPresaleMarketing()) {
                order.setPresalePrice(totalPrice.multiply(storeSettlement.getPreMarketing().getPreSale().getDepositPre()).setScale(2, RoundingMode.HALF_EVEN));
            }
        }

        // 判断是否免运费 如果不免运费 则最终的价格  还需要加上运费
        if (!storeSettlement.isFreeShip(totalPrice)) {
            totalPrice = totalPrice.add(storeSettlement.getFreightPrice());

            // 设置订单的运费
            order.setFreightPrice(storeSettlement.getFreightPrice());
        } else {
            order.setFreightPrice(new BigDecimal(0));
        }

        // 订单的最终价格
        order.setPrice(totalPrice);

        // 订单总的促销价格 (优惠券的价格+积分的价格+满减或者满折的价格)
        order.setConcessionalRate(order.getPointPrice().add(order.getCouponPrice()).add(storeSettlement.getTotalDiscountPrice()));

        // 设置使用的优惠券的券吗
        order.setCouponNo(storeSettlement.getUsedCouponCode());

        // 设置支付方式
        order.setPayType(storeSettlement.getChoosedPayType());

        // 设置店铺的店铺id
        order.setStoreId(storeSettlement.getStoreId());

        // 订单来源
        order.setSource(submitOrderParams.getSource());

        // 设置订单状态
        order.setCreateOrderStatus();

        // 设置订单的属性
        order.setOrderAttr(getOrderAttr(submitOrderParams, customerAddress, storeSettlement));

        // 设置订单的每个单品信息
        order.setOrderSkus(getOrderSkus(storeSettlement, order.getCustomerId()));

        // 设置优惠券下每个单品的平均优惠金额
        order.calcCouponEverySkuPrice();

        // 设置积分下每个单品的平均优惠金额
        order.calcPointsEverySkuPrice();

        // 设置拼团信息
        setGroupInfo(storeSettlement, order);

        // 设置订单的推荐人
        setOrderRecommoned(order);

        // 设置虚拟订单的核销码
        order.setVirtualOrderWriteOffCode();

        return order;
    }

    @Override
    public List<OmsOrder> queryPrintOrderDetails(List<Long> ids) {
        logger.debug("queryPrintOrderDetails and ids:{}", ids);
        return queryPrintOrderDetailsForStore(ids, CommonConstant.QUERY_WITH_NO_STORE);
    }

    @Override
    public List<OmsOrder> queryPrintOrderDetailsForStore(List<Long> ids, long storeId) {
        logger.debug("queryPrintOrderDetails and ids:{} \r\n storeId:{}", ids, storeId);
        if (CollectionUtils.isEmpty(ids)) {
            logger.error("queryPrintOrderDetails fail due to ids is empty....");
            return Collections.emptyList();
        }

        return ids.parallelStream().map(id -> this.queryOrderDetailById(id, CommonConstant.NO_CUSTOMER_ID, storeId, OrderItem.ATTR, OrderItem.SKUS, OrderItem.STORE_INFO, OrderItem.CUSTOMER)).collect(Collectors.toList());
    }


    /**
     * 设置订单的推荐人
     *
     * @param order 订单信息
     */
    @Override
    public void setOrderRecommoned(OmsOrder order) {

        // 首先根据订单的会员id查询会员信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(order.getCustomerId());

        // 订单下的单品 是否有单品设置了一级佣金比例
        if (order.orderSkuHasCommissionRate()) {
            // 如果有单品设置了一级佣金比例 那么就设置订单的一级推荐人id
            order.setRecommended(customer.getRecommended());

            // 如果单品设置了二级佣金比例
            if (order.orderSkuHasSCommissionRate()) {
                // 设置二级推荐人的会员id
                order.setSRecommended(customer.getSRecommended());
            }
        }

    }

    /**
     * 设置拼团信息
     *
     * @param storeSettlement 店铺结算信息
     * @param order           订单信息
     */
    private void setGroupInfo(StoreSettlement storeSettlement, OmsOrder order) {

        // 没有拼团促销 直接返回
        if (!storeSettlement.hasGroupMarekting()) {
            return;
        }

        // 拼团信息 (拼团只可能有一个单品)
        Marketing marketing = storeSettlement.getAllSkus().get(0).getMarketing();
        // 如果没有拼团id  则当前订单就是团长订单
        if (StringUtils.isEmpty(storeSettlement.getGroupId())) {
            order.setGroupHead("0");
            order.setGroupId(String.valueOf(snowflakeIdWorker.nextId()));
        } else {
            order.setGroupHead("1");
            order.setGroupId(storeSettlement.getGroupId());

            // 团长订单信息
            GroupOrder groupOrder = orderService.queryGroupHeadByGroupId(storeSettlement.getGroupId());

            // 校验拼团信息
            validateGroup(groupOrder, order.getCustomerId());

            // 设置该团的成团时间
            order.setOpenGroupTime(groupOrder.getOpenGroupTime());
        }

        order.setGroupMarketingId(marketing.getId());
        order.setGroupSkuId(storeSettlement.getAllSkus().get(0).getSkuId());
        order.setGroupNum(marketing.getGroupMarketing().getGroupNum());
    }


    /**
     * 校验拼团
     *
     * @param groupOrder 团长订单信息
     * @param customerId 会员id
     */
    private void validateGroup(GroupOrder groupOrder, long customerId) {

        // 如果没有查出团长信息 则直接抛出异常
        if (Objects.isNull(groupOrder)) {
            logger.error("group has no group head...");
            throw new ServiceException("has no group head...");
        }

        // 校验这个团的团长是否是自己,如果是自己的话 则抛出异常
        if (Objects.nonNull(groupOrder) && groupOrder.getCustomerId() == customerId) {
            logger.error("you are this group's grouphead.....");
            throw new ServiceException("you are this group's grouphead.....");
        }

        // 判断这个团 是否已成团 如果已成团的话 则抛出异常
        if (groupOrder.isGroupSuccess()) {
            logger.error("this group has alerdy group success");
            throw new ServiceException("this group has alerdy group success");
        }

        // 校验用户是否已经参与了这个团的团购
        if (Objects.nonNull(orderService.queryByGroupIdAndCustomerId(groupOrder.getGroupId(), customerId))) {
            logger.error("you has alerdy in this group....");
            throw new ServiceException("you has alerdy in this group.....");
        }


    }


    /**
     * 获得用户可以使用的积分
     *
     * @param customerId 用户id
     * @param price      订单不算运费的价格
     * @param point      用户前端使用的积分
     * @return 返回用户的最终可以使用的积分
     */
    private int getCustomerCanUsePoints(Long customerId, BigDecimal price, int point) {

        // 用户没有使用积分则直接返回
        if (point == 0 || point == -1) {
            return 0;
        }

        // 用户最多可以使用的积分
        int maxPoint = this.calcCustomerUsePoints(customerId, price);

        // 如果用户使用的积分小于等于可用的最大积分 则返回用户的积分 否则返回最大的积分
        return point <= maxPoint ? point : maxPoint;
    }

    /**
     * 获得订单下面的单品信息
     *
     * @param storeSettlement 结算信息
     * @param customerId      会员id
     * @return 返回订单下面的单品信息
     */
    private List<OmsOrderSku> getOrderSkus(StoreSettlement storeSettlement, long customerId) {

        // 订单下的单品信息
        List<OmsOrderSku> orderSkus = new ArrayList<>();

        // 购物车信息
        ShoppingCartResponse shoppingCartResponse = storeSettlement.getShoppingCartResponse();

        if (Objects.isNull(shoppingCartResponse)) {
            return orderSkus;
        }

        // 获得没有优惠的单品信息
        if (!CollectionUtils.isEmpty(shoppingCartResponse.getNormalSkus())) {
            orderSkus.addAll(shoppingCartResponse.getNormalSkus().stream().map(skuResponse -> getOrderSku(skuResponse, null, customerId)).collect(Collectors.toList()));
        }

        // 获得有优惠的单品信息 如果满足优惠的条件 则需要计算优惠下每个单品平均优惠的价格
        if (!CollectionUtils.isEmpty(shoppingCartResponse.getMarketings())) {
            shoppingCartResponse.getMarketings().stream().forEach(marketingResponse -> {

                // 有优惠 但是没有达到优惠的条件
                if (!marketingResponse.isUsedMarketing()) {
                    orderSkus.addAll(marketingResponse.getSkus().stream().map(skuResponse -> getOrderSku(skuResponse, null, customerId)).collect(Collectors.toList()));
                } else {
                    // 有优惠 并且达到了优惠的条件 则计算每个单品平均优惠的价格
                    orderSkus.addAll(marketingResponse.getSkus().stream().map(skuResponse -> getOrderSku(skuResponse, new SkuPriceDetail(marketingResponse.getSkuPriceDetailType(), skuResponse.getDiscountPrice()), customerId)).collect(Collectors.toList()));
                }

            });
        }

        return orderSkus;
    }

    /**
     * 获得订单下的单品信息
     *
     * @param skuResponse    单品在购物车中的信息
     * @param customerId     会员id
     * @param skuPriceDetail 单品价格的详情(主要是使用优惠的时候平均减去的优惠)
     * @return 返回订单下的单品信息
     */
    private OmsOrderSku getOrderSku(SkuResponse skuResponse, SkuPriceDetail skuPriceDetail, long customerId) {
        OmsOrderSku orderSku = new OmsOrderSku();
        orderSku.setPrice(skuResponse.getTotalPrice());
        // 有平均的优惠 则最终价格 减去平均的优惠
        if (Objects.nonNull(skuPriceDetail)) {
            orderSku.setPrice(orderSku.getPrice().subtract(skuPriceDetail.getPrice()));
        }
        orderSku.setSpuId(skuResponse.getSpuId());
        orderSku.setSkuId(skuResponse.getSkuId());
        orderSku.setNum(skuResponse.getNum());
        orderSku.setSkuPrice(skuResponse.getPrice());
        orderSku.setSkuName(skuResponse.getName());
        orderSku.setSkuImage(skuResponse.getImage());
        orderSku.setCommissionRate(skuResponse.getCommissionRate());
        // 查询单品信息
        PmsSku sku = skuService.querySkuByIdWithSpecs(skuResponse.getSkuId(), CommonConstant.QUERY_WITH_NO_STORE);
        if (Objects.nonNull(sku)) {
            orderSku.setSkuNo(sku.getSkuNo());
            orderSku.setSkuSpecs(sku.getSpecValuesString());
            orderSku.setSCommissionRate(sku.getsCommissionRate());
            orderSku.setSpuId(sku.getSpuId());
        }
        orderSku.addSkuPriceDetail(skuPriceDetail);
        orderSku.setPanicMarketingId(skuResponse.getPanicMarketId());
        orderSku.setPanicLimitNum(skuResponse.getLimitStock());
        orderSku.setCustomerId(customerId);

        // 如果有试用促销的申请则设置试用促销申请的id
        if (Objects.nonNull(skuResponse.getTrySkuApply())) {
            orderSku.setApplyTryId(skuResponse.getTrySkuApply().getId());
        }

        // 设置分类扣率
        orderSku.setCateRate(spuService.queryCateRateBySkuId(orderSku.getSkuId()));

        return orderSku;
    }

    /**
     * 获得订单的属性信息
     *
     * @param submitOrderParams 用户提交的参数
     * @param customerAddress   用户收货地址
     * @param storeSettlement   结算信息
     * @return 返回订单属性
     */
    private OmsOrderAttr getOrderAttr(SubmitOrderParams submitOrderParams, UmsMemberAddress customerAddress, StoreSettlement storeSettlement) {
        OmsOrderAttr orderAttr = new OmsOrderAttr();

        // 订单备注
        orderAttr.setRemark(storeSettlement.getRemark());

        // 设置订单的用户收货地址
        if (Objects.nonNull(customerAddress)) {
            orderAttr.setReceiptName(customerAddress.getName());
            orderAttr.setReceiptAddress(customerAddress.getAddress());
            orderAttr.setReceiptDetailAddress(customerAddress.getDetailAddress());
            orderAttr.setReceiptMobile(customerAddress.getOriginMobile());
            orderAttr.setReceiptPhone(customerAddress.getOriginPhone());
            orderAttr.setReceiptZipCode(customerAddress.getZipCode());
        }

        // 设置订单的发票信息
        if (Objects.nonNull(submitOrderParams.getInvoice())) {
            SubmitOrderParams.Invoice invoice = submitOrderParams.getInvoice();
            orderAttr.setInvoiceType(String.valueOf(invoice.getType()));
            orderAttr.setInvoiceTitle(invoice.getTitle());
            orderAttr.setInvoiceContent(invoice.getContent());
            orderAttr.setInvoiceTaxid(invoice.getTaxId());
            orderAttr.setInvoiceTitleType(invoice.getTitleType());
            orderAttr.setInvoiceCompanyName(invoice.getInvoiceCompanyName());
            orderAttr.setInvoiceRegisterAddress(invoice.getInvoiceRegisterAddress());
            orderAttr.setInvoiceRegisterMobile(invoice.getInvoiceRegisterMobile());
            orderAttr.setInvoiceOpenBank(invoice.getInvoiceOpenBank());
            orderAttr.setInvoiceBankAccount(invoice.getInvoiceBankAccount());
        }

        // 设置赠品信息
        orderAttr.setGiftInfos(storeSettlement.getGiftSkus());

        // 设置捐赠寄语
        orderAttr.setDonationMessage(submitOrderParams.getDonationMessage());
        return orderAttr;
    }


    /**
     * 获得使用优惠后的价格
     *
     * @param price       没有使用优惠券前的价格
     * @param couponPrice 优惠券的价格
     * @return 返回 减去优惠的价格
     */
    private BigDecimal calcPriceAfterCoupon(BigDecimal price, BigDecimal couponPrice) {
        return price.subtract(couponPrice);
    }

    /**
     * 获得使用积分后的价格
     *
     * @param price      订单的价格
     * @param pointPrice 积分的价格
     * @return 返回减去积分的价格
     */
    private BigDecimal calcPriceAfterPoint(BigDecimal price, BigDecimal pointPrice) {
        return price.subtract(pointPrice);
    }

    /**
     * 获得积分抵消的价格
     *
     * @param points 积分数量
     * @return 返回积分抵消的价格
     */
    private BigDecimal getPointPrice(int points) {
        return pointSetingService.queryPointSeting().getPointPirce(points);
    }

    /**
     * 计算用户可以使用的最大积分
     *
     * @param customerId 用户id
     * @param orderPrice 订单价格
     * @return 返回用户可以使用的最大积分
     */
    private int calcCustomerUsePoints(Long customerId, BigDecimal orderPrice) {
        logger.debug("calcCustomerUsePoints and customerId:{} \r\n orderPrice:{}", customerId, orderPrice);


        if (Objects.isNull(customerId) || Objects.isNull(orderPrice) || orderPrice.equals(new BigDecimal(0))) {
            logger.error("no member or orderprice.....");
            return 0;
        }


        // 首先查询用户拥有的总积分
        int customerAllPoint = customerPointService.queryCustomerPointCount(customerId);

        logger.debug("member customerId :{} \r\n  has point : {}", customerId, customerAllPoint);

        // 如果用户没有积分 则直接返回
        if (customerAllPoint == 0) {
            return 0;
        }

        return pointSetingService.queryPointSeting().getMaxPoint(orderPrice, customerAllPoint);
    }


    /**
     * 导出订单信息
     *
     * @param orders 订单信息集合
     * @param os     输出流
     */
    private void exportOrder(final List<OmsOrder> orders, final OutputStream os) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("订单信息");
        // 创建excel的基本信息
        createExecleBase(wb, sheet);
        // 创建导出的数据信息
        createExecleData(sheet, orders, 1);
        try {
            // 放入输出流
            wb.write(os);
        } catch (IOException e) {
            logger.error("export order fail \r\n", e);
        }
    }

    /**
     * 创建excel的基本信息
     *
     * @param wb    excel对象
     * @param sheet excel中的sheet对象
     */
    private void createExecleBase(final HSSFWorkbook wb, final HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        //  style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置宽度
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 4000);
        // 设置列头信息
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("订单编号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("收货人/联系电话");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("下单时间");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("订单状态");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("实付金额");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("商家");
        cell.setCellStyle(style);
    }

    /**
     * 创建导入execel的数据
     *
     * @param sheet  excel中的sheet对象
     * @param orders 订单信息集合
     * @param offset 从第几行开始输出数据
     */
    private void createExecleData(final HSSFSheet sheet, final List<OmsOrder> orders, final int offset) {
        if (CollectionUtils.isEmpty(orders)) {
            return;
        }
        final StringBuilder skip = new StringBuilder("0");
        IntStream.range(0, orders.size()).forEach(index -> {
            HSSFRow row = sheet.createRow(offset + index + Integer.parseInt(skip.toString()));
            OmsOrder order = orders.get(index);
            if (!StringUtils.isEmpty(order.getOrderCode())) {
                row.createCell(0).setCellValue(order.getOrderCode());
            }

            if (!StringUtils.isEmpty(order.getShippingName())) {
                if (!StringUtils.isEmpty(order.getShippingMobile())) {
                    row.createCell(1).setCellValue(order.getShippingName() + "/" + order.getShippingMobile());
                } else {
                    row.createCell(1).setCellValue(order.getShippingName());
                }
            }

            if (Objects.nonNull(order.getCreateTime())) {
                row.createCell(2).setCellValue(DateUtils.parseDateToStr("yyyy-MM-dd", order.getCreateTime()));
            }

            if (!StringUtils.isEmpty(order.getStatus())) {
                row.createCell(3).setCellValue(statusMap.get(order.getStatus()));
                if (order.isOneCoinSupportType() && order.isToDeliver() && !order.isCrowdFundingWinning()) {
                    row.createCell(3).setCellValue("待抽奖");
                }
            }

            if (Objects.nonNull(order.getPrice())) {
                row.createCell(4).setCellValue(order.getPrice() + "");
            }

            if (!StringUtils.isEmpty(order.getStoreName())) {
                row.createCell(5).setCellValue(order.getStoreName());
            } else if (order.getStoreId() == CommonConstant.ADMIN_STOREID) {
                row.createCell(5).setCellValue("Admin");
            } else {
                row.createCell(5).setCellValue("");
            }

            if (CollectionUtils.isEmpty(order.getOrderSkus())) {
                return;
            }

            IntStream.range(0, order.getOrderSkus().size()).forEach(index2 -> {
                if (index2 == 0) {
                    HSSFRow row2 = sheet.createRow(offset + index + 1 + Integer.parseInt(skip.toString()));
                    row2.createCell(2).setCellValue("商品名称");
                    row2.createCell(3).setCellValue("商品编号");
                }

                HSSFRow row3 = sheet.createRow(offset + index + 2 + Integer.parseInt(skip.toString()) + index2);
                if (!StringUtils.isEmpty(order.getOrderSkus().get(index2).getSkuName())) {
                    row3.createCell(2).setCellValue(order.getOrderSkus().get(index2).getSkuName());
                }
                if (!StringUtils.isEmpty(order.getOrderSkus().get(index2).getSkuNo())) {
                    row3.createCell(3).setCellValue(order.getOrderSkus().get(index2).getSkuNo());
                }
            });

            int intSkip = Integer.parseInt(skip.toString()) + order.getOrderSkus().size() + 1;
            skip.delete(0, skip.length());
            skip.append(String.valueOf(intSkip));
        });
    }

    /**
     * 导出订单信息
     *
     * @param orders 订单信息集合
     * @param os     输出流
     */
    private void exportGroupOrder(final List<GroupOrder> orders, final OutputStream os) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("拼团订单信息");
        // 创建excel的基本信息
        createGroupExecleBase(wb, sheet);
        // 创建导出的数据信息
        createGroupExecleData(sheet, orders, 1);
        try {
            // 放入输出流
            wb.write(os);
        } catch (IOException e) {
            logger.error("export order fail \r\n", e);
        }
    }

    /**
     * 创建excel的基本信息
     *
     * @param wb    excel对象
     * @param sheet excel中的sheet对象
     */
    private void createGroupExecleBase(final HSSFWorkbook wb, final HSSFSheet sheet) {
        HSSFRow row = sheet.createRow(0);
        HSSFCellStyle style = wb.createCellStyle();
        //   style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 设置宽度
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 6000);
        sheet.setColumnWidth(3, 6000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 4000);
        sheet.setColumnWidth(6, 4000);
        // 设置列头信息
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("拼团编号");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("订单编号");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("收货人/联系电话");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("下单时间");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("订单状态");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("实付金额");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("商家");
        cell.setCellStyle(style);
    }

    /**
     * 创建导入execel的数据
     *
     * @param sheet  excel中的sheet对象
     * @param orders 订单信息集合
     * @param offset 从第几行开始输出数据
     */
    private void createGroupExecleData(final HSSFSheet sheet, final List<GroupOrder> orders, final int offset) {
        if (CollectionUtils.isEmpty(orders)) {
            return;
        }
        final StringBuilder skip = new StringBuilder("0");
        final StringBuilder subSkip = new StringBuilder("0");
        IntStream.range(0, orders.size()).forEach(index -> {
            HSSFRow row = sheet.createRow(offset + index + Integer.parseInt(subSkip.toString()) + Integer.parseInt(skip.toString()));
            GroupOrder order = orders.get(index);
            if (!StringUtils.isEmpty(order.getGroupId())) {
                row.createCell(0).setCellValue(order.getGroupId());
            }
            createGroupOrderData(sheet, offset, skip, index + Integer.parseInt(subSkip.toString()), row, order);
            if (!CollectionUtils.isEmpty(order.getGroupMemberOrders())) {
                IntStream.range(0, order.getGroupMemberOrders().size()).forEach(subIndex -> {
                    HSSFRow subRow = sheet.createRow(offset + subIndex + index + 1 + Integer.parseInt(subSkip.toString()) + Integer.parseInt(skip.toString()));
                    GroupOrder subOrder = order.getGroupMemberOrders().get(subIndex);
                    createGroupOrderData(sheet, offset, skip, subIndex + index + 1 + Integer.parseInt(subSkip.toString()), subRow, subOrder);
                });
                int intSubSkip = Integer.parseInt(subSkip.toString()) + order.getGroupMemberOrders().size();
                subSkip.delete(0, subSkip.length());
                subSkip.append(String.valueOf(intSubSkip));
            }

        });
    }

    /**
     * @param sheet  excel中的sheet对象
     * @param offset 从第几行开始输出数据
     * @param skip   跳过
     * @param index  下标
     * @param row    行实体
     * @param order  订单信息
     */
    private void createGroupOrderData(HSSFSheet sheet, int offset, StringBuilder skip, int index, HSSFRow row, GroupOrder order) {
        if (!StringUtils.isEmpty(order.getOrderCode())) {
            row.createCell(1).setCellValue(order.getOrderCode());
        }
        if (!StringUtils.isEmpty(order.getShippingName())) {
            if (!StringUtils.isEmpty(order.getShippingMobile())) {
                row.createCell(2).setCellValue(order.getShippingName() + "/" + order.getShippingMobile());
            } else {
                row.createCell(2).setCellValue(order.getShippingName());
            }
        }

        if (Objects.nonNull(order.getCreateTime())) {
            row.createCell(3).setCellValue(order.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        if (!StringUtils.isEmpty(order.getStatus())) {
            row.createCell(4).setCellValue(statusMap.get(order.getStatus()));
        }

        if (Objects.nonNull(order.getPrice())) {
            row.createCell(5).setCellValue(order.getPrice() + "");
        }

        if (!StringUtils.isEmpty(order.getStoreName())) {
            row.createCell(6).setCellValue(order.getStoreName());
        } else if (order.getStoreId() == CommonConstant.ADMIN_STOREID) {
            row.createCell(6).setCellValue("Admin");
        } else {
            row.createCell(6).setCellValue("");
        }

        if (CollectionUtils.isEmpty(order.getOrderSkus())) {
            return;
        }

        IntStream.range(0, order.getOrderSkus().size()).forEach(index2 -> {
            if (index2 == 0) {
                HSSFRow row2 = sheet.createRow(offset + index + 1 + Integer.parseInt(skip.toString()));
                row2.createCell(3).setCellValue("商品名称");
                row2.createCell(4).setCellValue("商品编号");
            }

            HSSFRow row3 = sheet.createRow(offset + index + 2 + Integer.parseInt(skip.toString()) + index2);
            if (!StringUtils.isEmpty(order.getOrderSkus().get(index2).getSkuName())) {
                row3.createCell(3).setCellValue(order.getOrderSkus().get(index2).getSkuName());
            }
            if (!StringUtils.isEmpty(order.getOrderSkus().get(index2).getSkuNo())) {
                row3.createCell(4).setCellValue(order.getOrderSkus().get(index2).getSkuNo());
            }
        });

        int intSkip = Integer.parseInt(skip.toString()) + order.getOrderSkus().size() + 1;
        skip.delete(0, skip.length());
        skip.append(String.valueOf(intSkip));

    }


    /**
     * 设置订单店铺的名称
     *
     * @param order 订单信息
     * @return 返回订单信息
     */
    private OmsOrder setOrderStoreName(OmsOrder order) {
        logger.debug("begin to setOrderStoreName....");
        TStoreInfo storeInfo = storeInfoService.queryStoreInfo(order.getStoreId());
        if (Objects.isNull(storeInfo)) {
            logger.error("setOrderStoreName fail due to store is not exist....");
            return order;
        }

        order.setStoreName(storeInfo.getStoreName());
        return order;
    }

}
