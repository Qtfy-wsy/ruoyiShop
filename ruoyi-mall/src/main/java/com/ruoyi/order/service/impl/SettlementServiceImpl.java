package com.ruoyi.order.service.impl;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.integral.service.PointSetingService;
import com.ruoyi.marketing.domain.*;
import com.ruoyi.marketing.service.*;
import com.ruoyi.member.domain.UmsMemberAddress;
import com.ruoyi.member.service.IUmsMemberAddressService;
import com.ruoyi.order.domain.OmsLogisticsTemplate;
import com.ruoyi.order.service.IOmsLogisticsTemplateService;
import com.ruoyi.order.service.IOmsOrderSettingService;
import com.ruoyi.order.service.SettlementService;
import com.ruoyi.order.service.ShoppingCartServiceApi;
import com.ruoyi.order.vo.*;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 17/11/2.
 * 计算服务接口实现
 */
@Service
public class SettlementServiceImpl implements SettlementService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(SettlementServiceImpl.class);

    /**
     * 注入购物车服务接口
     */
    @Autowired
    private ShoppingCartServiceApi shoppingCartServiceApi;

    /**
     * 注入运费模版服务接口
     */
    @Autowired
    private IOmsLogisticsTemplateService logisticsTemplateService;

    /**
     * 注入优惠券服务接口
     */
    @Autowired
    private CouponService couponService;

    /**
     * 注入订单设置服务接口
     */
    @Autowired
    private IOmsOrderSettingService orderSettingService;

    /**
     * 注入包邮服务接口
     */
    @Autowired
    private FreeShipService freeShipService;

    /**
     * 注入用户积分服务接口
     */
    @Autowired
    private CustomerPointService customerPointService;

    /**
     * 注入积分设置服务接口
     */
    @Autowired
    private PointSetingService pointSetingService;

    /**
     * 注入用户收货地址服务接口
     */
    @Autowired
    private IUmsMemberAddressService customerAddressService;

    /**
     * 注入红包接口
     */
    @Autowired
    private RedEnvelopeService redEnvelopeService;

    /**
     * 注入试用申请服务接口
     */
    @Autowired
    private TrySkuApplyService trySkuApplyService;

    /**
     * 注入促销查询接口
     */
    @Autowired
    private MarketingQueryService marketingQueryService;


    @Override
    public OrderSettlement orderSettlement(OrderSettlementRequest orderSettlementRequest) {
        logger.debug("orderSettlement and orderSettlementRequest:{}", orderSettlementRequest);

        // 用户的收货地址
        UmsMemberAddress customerAddress = getCustomerAddress(orderSettlementRequest.getAddressId(), orderSettlementRequest.getCustomerId());

        return OrderSettlement.build(orderSettlementRequest.getIds(), orderSettlementRequest.getSkuInfo(), customerAddress, getStoreSettlements(customerAddress, orderSettlementRequest)).addRedEnvelope(redEnvelopeService.queryCustomerCanUseRedEnvelope(orderSettlementRequest.getCustomerId()));
    }


    /**
     * 获得用户的收货地址
     *
     * @param addressId 收货地址id
     * @return 返回用户的收货地址
     */
    private UmsMemberAddress getCustomerAddress(Long addressId, Long customerId) {

        // 首先查询用户选择的收货地址
        UmsMemberAddress customerAddress = customerAddressService.queryCustomerAddressById(customerId, addressId);

        if (Objects.nonNull(customerAddress)) {
            logger.debug("member choose address....");
            return customerAddress;
        }

        // 如果没有收货地址则使用用户的默认选中的收货地址
        return customerAddressService.queryCustomerDefaultChosenAddress(customerId);
    }


    /**
     * 获得每个店铺的结算信息
     *
     * @param customerAddress        用户收货地址
     * @param orderSettlementRequest 结算请求信息
     * @return 返回每个店铺的结算信息
     */
    private List<StoreSettlement> getStoreSettlements(UmsMemberAddress customerAddress, OrderSettlementRequest orderSettlementRequest) {

        logger.debug("getStoreSettlements orderSettlementRequest:{} \r\n customerAddress:{}", orderSettlementRequest, customerAddress);

        // 获得结算信息
        List<StoreSettlement> storeSettlements = convertToStoreSettlements(getShoppingCartResponse(orderSettlementRequest.getIds(), orderSettlementRequest.getSkuInfo(), orderSettlementRequest.getCustomerId(), orderSettlementRequest.getCrowdfundingId()));

        if (CollectionUtils.isEmpty(storeSettlements)) {
            logger.error("storeSettlements is empty.....");
            return Collections.emptyList();
        }

        // 设置店铺的详细信息
        storeSettlements.stream().forEach(storeSettlement -> setStoreSettlementDetails(storeSettlement, customerAddress, orderSettlementRequest));

        return storeSettlements;
    }

    /**
     * 获得购物车信息
     *
     * @param ids            购物车id
     * @param skuInfo        单品信息(立即购买的时候使用)
     * @param customerId     会员id
     * @param crowdfundingId 众筹id
     * @return 返回购物车信息
     */
    private List<ShoppingCartResponse> getShoppingCartResponse(Long[] ids, String skuInfo, Long customerId, Long crowdfundingId) {

        // 购物车购买
        if (ArrayUtils.isNotEmpty(ids)) {
            return shoppingCartServiceApi.queryShoppingCartsByIds(ids, customerId);
        }

        // 立即购买
        if (!StringUtils.isEmpty(skuInfo)) {
            return shoppingCartServiceApi.queryShoppingCartsBySkuId(skuInfo, customerId, crowdfundingId);
        }

        return Collections.emptyList();
    }

    /**
     * 设置店铺的详细信息 主要有(1.店铺的价格 2.店铺可以使用的优惠券 3.店铺可以使用的积分)
     *
     * @param storeSettlement        店铺信息
     * @param customerAddress        用户收货地址
     * @param orderSettlementRequest 下订单参数
     */
    private void setStoreSettlementDetails(StoreSettlement storeSettlement, UmsMemberAddress customerAddress, OrderSettlementRequest orderSettlementRequest) {

        // 设置是否是虚拟商品结算
        setStoreSettlementVirtual(storeSettlement);

        // 设置众筹信息
        setCrowdfundingMarketing(storeSettlement, orderSettlementRequest);

        // 设置拼团信息
        setGroupMarketing(storeSettlement, orderSettlementRequest.getIsGroup(), orderSettlementRequest.getGroupId());

        // 如果单品有试用促销申请的话 则设置单品的试用促销申请
        setTryMarketing(storeSettlement.getAllSkus(), orderSettlementRequest.getCustomerId());

        // 设置预售信息
        findPreSaleMarketing(storeSettlement);

        // 计算每个店铺的价格
        calcStoreSettlementPrices(storeSettlement, customerAddress);

        // 获得每个店铺可以使用的优惠券
        setCanUseCouponCode(storeSettlement, orderSettlementRequest.getCustomerId());

        // 如果是平台的订单 并且订单不是预售和众筹 (预售和众筹商品不能使用积分,不能货到付款)
        if (storeSettlement.isPlatform() && !storeSettlement.hasPreSaleMarketing() && !storeSettlement.hasCrowdfunding()) {
            //则判断系统是否设置支持货到付款  ,不是平台的不支持货到付款L
            storeSettlement.setCashonDelivery(orderSettingService.selectOmsOrderSettingById(1L).getCashonDelivery());

            // 设置积分信息
            setPointInfo(storeSettlement, orderSettlementRequest.getCustomerId());
        }

        // 如果订单是拼团订单或者虚拟订单 则不支持货到付款
        if (storeSettlement.hasGroupMarekting() || storeSettlement.isVirtualSettlment()) {
            storeSettlement.setCashonDelivery("1");
        }
    }

    /**
     * 设置是否是虚拟商品的结算
     *
     * @param storeSettlement 结算信息
     */
    private void setStoreSettlementVirtual(StoreSettlement storeSettlement) {

        // 没有商品信息 直接返回
        if (CollectionUtils.isEmpty(storeSettlement.getAllSkus())) {
            return;
        }

        // 结算信息里面既有虚拟商品又有实体商品 则直接报错
        if (storeSettlement.getAllSkus().stream().filter(SkuResponse::isVirtualSku).collect(Collectors.toList()).size() != 0 && storeSettlement.getAllSkus().stream().filter(skuResponse -> !skuResponse.isVirtualSku()).collect(Collectors.toList()).size() != 0) {
            logger.error("Settlement contain Virtual sku and entity sku....");
            throw new ServiceException("R-000016");
        }

        // 设置结算是否是虚拟商品
        storeSettlement.setIsVirtual(storeSettlement.getAllSkus().get(0).getIsVirtual());
    }

    /**
     * 设置众筹信息
     *
     * @param storeSettlement        店铺结算信息
     * @param orderSettlementRequest 下单参数
     */
    private void setCrowdfundingMarketing(StoreSettlement storeSettlement, OrderSettlementRequest orderSettlementRequest) {
        // 如果不是众筹或者结算的单品不是一个（众筹下单的时候 单品只可能有一个）
        if (!orderSettlementRequest.isCrowdfunding() || storeSettlement.getAllSkus().size() > 1) {
            logger.debug("do not need to setCrowdfundingMarketing and orderSettlementRequest:{} \r\n and skuResponses size:{}", orderSettlementRequest, storeSettlement.getAllSkus().size());
            return;
        }

        // 众筹的单品信息 ,众筹的时候单品只有一个
        SkuResponse skuResponse = storeSettlement.getAllSkus().get(0);

        // 根据单品id和众筹id 查询众筹信息
        Marketing marketing = marketingQueryService.queryCrowdFundingByIdAndSkuId(orderSettlementRequest.getCrowdfundingId(), skuResponse.getSkuId());

        // 如果没有众筹信息则直接返回
        if (!validateCrowdfunding(orderSettlementRequest, marketing)) {
            logger.error("no Crowdfunding exist .....");
            throw new ServiceException("R-000015");
        }

        // 众筹单品 这个肯定会有值 如果没有就走不到这边
        MarketingSku marketingSku = marketing.getMarketingSkus().get(0);

        // 如果是众筹 则设置单品的众筹价格
        skuResponse.setPrice(getCrowdfundingPrice(orderSettlementRequest, marketingSku));
        skuResponse.setOldPrice(getCrowdfundingPrice(orderSettlementRequest, marketingSku));

        // 设置众筹信息
        storeSettlement.setCrowdfundingInfo(orderSettlementRequest.getCrowdfundingId(), orderSettlementRequest.getCrowdfundingType());
    }

    /**
     * 校验众筹
     *
     * @param orderSettlementRequest 请求参数
     * @param marketing              众筹促销
     * @return 成功返回true 失败返回false
     */
    private boolean validateCrowdfunding(OrderSettlementRequest orderSettlementRequest, Marketing marketing) {
        logger.debug("validateCrowdfunding and orderSettlementRequest:{} \r\n marketing:{} \r\n", orderSettlementRequest, marketing);

        // 没有众筹促销直接返回
        if (Objects.isNull(marketing)) {
            logger.error("validateCrowdfunding fail ..due to marketing is not exits.....");
            return false;
        }

        // 如果是1元支持 则判断1元支持是否开启
        if ("5".equals(orderSettlementRequest.getCrowdfundingType())) {
            return marketing.isOneMoneyOpen();
        } else if ("6".equals(orderSettlementRequest.getCrowdfundingType())) {
            // 如果是无回报支持 则判断无回报支持是否开启
            return marketing.isNoReturnOpen();
        } else if ("4".equals(orderSettlementRequest.getCrowdfundingType())) {
            // 直接返回true  全款默认支持
            return true;
        }

        return false;
    }

    /**
     * 获得众筹的价格
     *
     * @param orderSettlementRequest 请求参数
     * @param marketingSku           众筹的单品
     * @return 返回众筹的价格
     */
    private BigDecimal getCrowdfundingPrice(OrderSettlementRequest orderSettlementRequest, MarketingSku marketingSku) {

        // 全款支持 直接返回众筹单品的价格
        if ("4".equals(orderSettlementRequest.getCrowdfundingType())) {
            return marketingSku.getPrice();
        } else if ("5".equals(orderSettlementRequest.getCrowdfundingType())) {
            // 1元支付  返回1 元
            return new BigDecimal(1);
        } else if ("6".equals(orderSettlementRequest.getCrowdfundingType())) {
            // 无回报支持 直接返回无回报支持金额
            return orderSettlementRequest.getCrowdfundingMoney();
        }

        // 有钱任性？
        return new BigDecimal(10000000);
    }

    /**
     * 设置拼团促销
     *
     * @param storeSettlement 店铺结算信息
     * @param isGroup         是否拼团 0 否 1 是
     * @param groupId         团id
     */
    private void setGroupMarketing(StoreSettlement storeSettlement, int isGroup, String groupId) {
        // 如果不是拼团 或者 结算的单品不是一个(拼团下单的时候 单品只可能有一个)
        if (isGroup == 0 || storeSettlement.getAllSkus().size() > 1) {
            logger.debug("do not need to setGroupMarketing and isGroup:{} \r\n and skuResponses size:{}", isGroup, storeSettlement.getAllSkus().size());
            return;
        }

        // 拼团的单品信息 ,拼团的时候单品只有一个
        SkuResponse skuResponse = storeSettlement.getAllSkus().get(0);

        // 根据单品信息查询拼团促销
        List<Marketing> list = marketingQueryService.queryMarketingsBySkuId(skuResponse.getSkuId(), true, MarketingItem.GROUP_MARKETING);

        // 如果该单品没有查到拼团信息 则直接返回
        if (CollectionUtils.isEmpty(list)) {
            logger.info("sku has no groupMarketing.....");
            return;
        }

        // 如果有拼团信息 则把购物车的促销商品清空设置成为普通商品
        storeSettlement.getShoppingCartResponse().setMarketings(new ArrayList<>());
        storeSettlement.getShoppingCartResponse().setNormalSkus(Arrays.asList(skuResponse));

        // 设置团购详情
        skuResponse.setGroupMarketingDetail(list.get(0));

        // 设置拼团信息
        storeSettlement.setGroupInfo(isGroup, groupId);
    }

    /**
     * 设置试用促销
     *
     * @param skuResponses 单品信息
     */
    private void setTryMarketing(List<SkuResponse> skuResponses, long customerId) {
        skuResponses.stream().forEach(skuResponse -> {
            // 申请通过的单品试用申请
            TrySkuApply trySkuApply = trySkuApplyService.queryPassedApplyByCustomerIdAndSkuId(customerId, skuResponse.getSkuId());
            if (Objects.nonNull(trySkuApply)) {
                skuResponse.setTrySkuApply(trySkuApply);
            }
        });

    }

    /**
     * 找出结算信息下的预售信息
     */
    private void findPreSaleMarketing(StoreSettlement storeSettlement) {
        // 结算中的预售促销
        List<Marketing> preMarketings = storeSettlement.getShoppingCartResponse().getNormalSkus().stream().filter(SkuResponse::hasPreSale).map(SkuResponse::getMarketing).collect(Collectors.toList());

        // 如果有预售促销 则直接获取第一个  (预售下单 只可能是立即购买)
        if (!CollectionUtils.isEmpty(preMarketings)) {
            storeSettlement.setPreMarketing(preMarketings.get(0));
        }
    }

    /**
     * 设置积分信息
     *
     * @param storeSettlement 店铺结算实体
     * @param customerId      用户id
     */
    private void setPointInfo(StoreSettlement storeSettlement, Long customerId) {
        storeSettlement.setCustomerAllPoints(customerPointService.queryCustomerPointCount(customerId));
        storeSettlement.setPointSeting(pointSetingService.queryPointSeting());
    }

    /**
     * 设置店铺的包邮优惠
     *
     * @param storeSettlement 店铺结算信息
     */
    private void setFreeShipMarketing(StoreSettlement storeSettlement) {
        storeSettlement.setFreeShip(freeShipService.queryEffectiveFreeShip(storeSettlement.getStoreId()));
    }

    /**
     * 设置店铺用户可以使用的优惠券券码
     *
     * @param storeSettlement 店铺信息
     * @param customerId      用户id
     */
    private void setCanUseCouponCode(StoreSettlement storeSettlement, Long customerId) {

        // 如果有预售或者众筹 则直接返回 预售和众筹不能使用优惠券
        if (storeSettlement.hasPreSaleMarketing() || storeSettlement.hasCrowdfunding()) {
            logger.info("order has preSaleMarketing or crowdfundingMarketing and setCanUseCouponCode no use.....");
            return;
        }

        // 首先获得用户当前店铺拥有的未使用的优惠券信息
        List<CouponCode> coupons = couponService.queryCustomerCanUserCoupon(storeSettlement.getStoreId(), customerId);

        if (CollectionUtils.isEmpty(coupons)) {
            return;
        }

        // 用户可以使用的优惠券信息
        List<CouponCode> canUseCouponCodes = new ArrayList<>();

        // 过滤出直降的优惠券
        List<CouponCode> fallCouponCode = coupons.stream().filter(CouponCode::isFall).collect(Collectors.toList());

        // 然后过滤出满减情况下满足条件的优惠券
        List<CouponCode> fullCouponCode = coupons.stream().filter(CouponCode::isFullType).filter(couponInfo -> storeSettlement.getTotalPrice().compareTo(couponInfo.getFullPrice()) >= 0).collect(Collectors.toList());

        canUseCouponCodes.addAll(fallCouponCode);
        canUseCouponCodes.addAll(fullCouponCode);

        // 设置优惠券的店铺名称
        canUseCouponCodes.stream().forEach(canUseCouponCode -> canUseCouponCode.setStoreName(storeSettlement.getStoreName()));

        // 设置可以使用的优惠券信息
        storeSettlement.setCanUseCouponInfo(canUseCouponCodes);
    }


    /**
     * 设置每个店铺的结算价格
     *
     * @param storeSettlement 结算信息
     */
    private void calcStoreSettlementPrices(StoreSettlement storeSettlement, UmsMemberAddress customerAddress) {
        logger.debug("calcStoreSettlementPrices and storeSettlement:{} and customerAddress:{}", storeSettlement, customerAddress);

        // 计算店铺的价格
        storeSettlement.calcPrice();

        // 判断是否需要计算店铺运费
        if (storeSettlement.isNeedFreight()) {
            // 计算店铺的运费
            storeSettlement.calcFreightPrices(this.getLogisticsTemplates(storeSettlement.getLogisticsPrice().keySet(), customerAddress));
        }
    }

    /**
     * 根据用户的收货地址得到运费模版
     *
     * @param customerAddress 用户收货地址
     * @return 返回运费模版
     */
    private List<OmsLogisticsTemplate> getLogisticsTemplates(Set<Long> logisticsTemplateIds, UmsMemberAddress customerAddress) {
        return logisticsTemplateService.queryLogisticsTemplateByCityIdAndId(logisticsTemplateIds, Objects.isNull(customerAddress) ? -1l : customerAddress.getCityId());
    }

    /**
     * 生成结算对象
     *
     * @param shoppingCartResponses 购物车信息
     * @return 返回结算对象
     */
    private List<StoreSettlement> convertToStoreSettlements(List<ShoppingCartResponse> shoppingCartResponses) {
        if (CollectionUtils.isEmpty(shoppingCartResponses)) {
            return Collections.emptyList();
        }
        return shoppingCartResponses.stream().map(shoppingCartResponse -> new StoreSettlement(shoppingCartResponse)).collect(Collectors.toList());
    }

}
