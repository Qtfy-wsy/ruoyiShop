package com.ruoyi.order.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.goods.domain.PmsShippingMethod;
import com.ruoyi.goods.domain.PmsShippingMethodFreeship;
import com.ruoyi.integral.domain.PointSeting;
import com.ruoyi.marketing.domain.*;
import com.ruoyi.order.domain.OmsLogisticsTemplate;
import com.ruoyi.order.domain.OrderGift;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 魔金商城 on 17/11/2.
 * 店铺结算实体
 */
@Data
public class StoreSettlement {

    /**
     * 店铺名称
     */
    private String storeLogo;
    /**
     * 店铺名称
     */
    private String storeName;

    /**
     * 店铺id
     */
    private Long storeId;

    /**
     * 店铺订单总价格(最后不算运费的价格 原始价格-优惠价格  优惠价格是 满减和满折  不算积分和优惠券)
     */
    private BigDecimal totalPrice = new BigDecimal(0);

    /**
     * 店铺订单原始总价格(单品详情页的价格总和)
     */
    private BigDecimal originTotalPrice = new BigDecimal(0);

    /**
     * 店铺订单总优惠价格(满减,满折的价格)
     */
    private BigDecimal totalDiscountPrice = new BigDecimal(0);

    /**
     * 店铺订单的运费
     */
    private BigDecimal freightPrice = new BigDecimal(0);

    /**
     * 用户可以使用的优惠券信息
     */
    private List<CouponCode> canUseCouponInfo = new ArrayList<>();

    /**
     * 店铺订单的购物车信息
     */
    private ShoppingCartResponse shoppingCartResponse;

    /**
     * 是否支持货到付款  0 支付 1 不支持 默认 1 不支持
     */
    private String cashonDelivery = "1";

    /**
     * 用户选择的支付方式 0 在线支付 1 货到付款 默认0
     */
    private String choosedPayType = "0";

    /**
     * 用户选择使用的优惠券
     */
    private CouponCode choosedCoupon;

    /**
     * 店铺所有的单品信息
     */
    @JsonIgnore
    private List<SkuResponse> allSkus;

    /**
     * 店铺一共有几件商品
     */
    private int skuNum;

    /**
     * 店铺的包邮优惠
     */
    private Marketing freeShip;

    /**
     * 使用积分的数量
     */
    private int usePoints = -1;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户拥有的所有积分
     */
    private int customerAllPoints;

    /**
     * 可以使用的最大积分
     */
    private int canUsePoints;

    /**
     * 积分设置
     */
    private PointSeting pointSeting;

    /**
     * 预售促销
     */
    private Marketing preMarketing;

    /**
     * 是否拼团 0 否 1 是
     */
    @JsonIgnore
    private int isGroup;

    /**
     * 拼团已经存在的拼团id
     */
    @JsonIgnore
    private String groupId;

    /**
     * 众筹id
     */
    private Long crowdfundingId;

    /**
     * 众筹类型 4:全款 5:1元支付 6:无回报支持
     */
    private String crowdfundingType;

    /**
     * 每个运费模版下单品的总金额 key是运费模版id  value是这个模版下单品的总金额
     * 这个总金额是优惠后的金额 （优惠主要包含详情页的优惠和购物车的优惠，购物车的优惠需要计算多个单品用一个优惠后的平摊金额）
     */
    @JsonIgnore
    private Map<Long, BigDecimal> logisticsPrice;

    /**
     * 每个运费下单品的总的数量
     */
    private Map<Long, BigDecimal> logisticsSkuNum;

    /**
     * 每个运费下单品的总的重量
     */
    private Map<Long, BigDecimal> logisticsSkuWeight;


    /**
     * 虚拟订单结算  0 否  1 是
     */
    private String isVirtual;

    public StoreSettlement(ShoppingCartResponse shoppingCartResponse) {
        // 设置购物车信息
        this.shoppingCartResponse = shoppingCartResponse;
        if (Objects.nonNull(this.shoppingCartResponse)) {
            this.storeName = this.shoppingCartResponse.getStoreName();
            this.storeId = this.shoppingCartResponse.getStoreId();
            this.storeLogo = this.shoppingCartResponse.getStoreLogo();
            // 所有的单品信息
            List<SkuResponse> allSkus = new ArrayList<>();

            // 有促销的单品
            if (!CollectionUtils.isEmpty(this.shoppingCartResponse.getMarketings())) {
                allSkus = this.shoppingCartResponse.getMarketings().stream().flatMap(marketingResponse -> marketingResponse.getSkus().stream()).collect(Collectors.toList());
            }

            // 没有促销的单品
            if (!CollectionUtils.isEmpty(this.shoppingCartResponse.getNormalSkus())) {
                allSkus.addAll(this.shoppingCartResponse.getNormalSkus());
            }

            // 设置所有的单品
            this.allSkus = allSkus;

            // 设置单品的总件数
            this.skuNum = this.allSkus.stream().map(skuResponse -> new BigDecimal(skuResponse.getNum())).reduce(new BigDecimal(0), BigDecimal::add).intValue();
        }
    }

    /**
     * 判断是否是众筹
     *
     * @return 有返回true  没有则返回false
     */
    public boolean hasCrowdfunding() {
        return Objects.nonNull(crowdfundingId) && !crowdfundingId.equals(0);
    }

    /**
     * 判断用户是否使用了积分
     *
     * @return 使用返回true  没使用返回false
     */
    @JsonIgnore
    public boolean isUsedPoint() {
        return this.usePoints != -1;
    }

    /**
     * 设置拼团信息
     *
     * @param isGroup 是否是拼团 0 否 1 是
     * @param groupId 拼团已经存在的拼团id
     */
    public void setGroupInfo(int isGroup, String groupId) {
        this.isGroup = isGroup;
        this.groupId = groupId;
    }

    /**
     * 设置众筹信息
     *
     * @param crowdfundingId   众筹id
     * @param crowdfundingType 众筹类型
     */
    public void setCrowdfundingInfo(long crowdfundingId, String crowdfundingType) {
        this.crowdfundingId = crowdfundingId;
        this.crowdfundingType = crowdfundingType;
    }

    /**
     * 获得所有的购物车id
     *
     * @return 返回购物车id
     */
    @JsonIgnore
    public Long[] getCartIds() {
        if (CollectionUtils.isEmpty(this.allSkus)) {
            return null;
        }

        return this.allSkus.stream().map(skuResponse -> Long.valueOf(skuResponse.getCartId())).toArray(Long[]::new);
    }

    /**
     * 判断是否是平台自营的
     *
     * @return 平台自营返回true  不是则返回false
     */
    @JsonIgnore
    public boolean isPlatform() {
        return this.storeId.equals(0L);
    }


    /**
     * 设置可以使用的优惠券信息
     *
     * @param couponCode 优惠券code
     */
    public void setCanUseCoupon(String couponCode) {
        // 如果没有符合条件的优惠券 则直接返回
        if (CollectionUtils.isEmpty(this.canUseCouponInfo)) {
            return;
        }

        this.canUseCouponInfo.stream().filter(couponInfo -> couponInfo.getCode().equals(couponCode)).findFirst().ifPresent(this::setChoosedCoupon);
    }

    /**
     * 返回使用优惠券的券吗
     *
     * @return 返回使用优惠券的券吗
     */
    @JsonIgnore
    public String getUsedCouponCode() {
        return Objects.nonNull(this.choosedCoupon) ? this.choosedCoupon.getCode() : "";
    }


    /**
     * 计算促销价格
     */
    private void calcMarketPrice() {

        //总的价格(单品详情页的价格)
        BigDecimal totalPrice = new BigDecimal(0);

        // 没有促销的单品
        if (!CollectionUtils.isEmpty(this.shoppingCartResponse.getNormalSkus())) {
            totalPrice = this.shoppingCartResponse.getNormalSkus().stream().map(SkuResponse::getTotalPrice).reduce(new BigDecimal(0), BigDecimal::add);
        }

        // 临时的存放价格的map
        final Map<String, BigDecimal> tempPriceMap = new HashMap<>();

        // 总的价格(优惠前的价格)
        tempPriceMap.put("totalPrice", totalPrice);

        // 总的优惠的价格
        tempPriceMap.put("discountPrice", new BigDecimal(0));


        // 有促销的单品
        if (!CollectionUtils.isEmpty(this.shoppingCartResponse.getMarketings())) {

            // 遍历促销
            this.shoppingCartResponse.getMarketings().stream().forEach(marketingResponse -> {

                // 一类促销下商品的总价格
                BigDecimal marketSkuPrices = marketingResponse.getSkusTotalPrice();

                // 存入新的总的价格
                tempPriceMap.put("totalPrice", tempPriceMap.get("totalPrice").add(marketSkuPrices));

                if (marketingResponse.isFullDiscount()) {
                    // 获得满足条件的一个满折(获得满足条件的最后一个,因为多级满折是按照从小到大的顺序排练的)
                    Optional<FullDiscount> fullDiscountOptional = marketingResponse.getFullDiscounts().stream().filter(fullDiscount -> marketSkuPrices.compareTo(fullDiscount.getFullPrice()) >= 0).reduce((first, second) -> second);
                    // 如果存在 则计算满折的价格
                    fullDiscountOptional.ifPresent(fullDiscount1 -> {
                        // 设置当前促销 优惠的价格
                        marketingResponse.setDiscountPrice(marketSkuPrices.multiply((new BigDecimal(1).subtract(fullDiscount1.getDiscount()))));
                        tempPriceMap.put("discountPrice", tempPriceMap.get("discountPrice").add(marketingResponse.getDiscountPrice()));
                        marketingResponse.setFullMarketingId(fullDiscount1.getId());

                    });
                } else if (marketingResponse.isFullDown()) {

                    // 获得满足条件的一个满减(获得满足条件的最后一个,因为多级满减是按照从小到大的顺序排练的)
                    Optional<FullDown> fullDownOptional = marketingResponse.getFullDowns().stream().filter(fullDown -> marketSkuPrices.compareTo(fullDown.getFullPrice()) >= 0).reduce((first, second) -> second);
                    fullDownOptional.ifPresent(fullDown1 -> {

                        // 判断如果当前商品总的价格比减的价格少 则设置减的金额为商品的金额
                        if (marketSkuPrices.compareTo(fullDown1.getPrice()) == -1) {
                            fullDown1.setPrice(marketSkuPrices);
                        }

                        // 设置当前促销 优惠的价格
                        marketingResponse.setDiscountPrice(fullDown1.getPrice());
                        marketingResponse.setFullMarketingId(fullDown1.getId());

                        tempPriceMap.put("discountPrice", tempPriceMap.get("discountPrice").add(marketingResponse.getDiscountPrice()));
                    });

                } else if (marketingResponse.isFullGift()) {
                    Optional<FullGift> fullGiftOptional = marketingResponse.getFullGifts().stream().filter(fullGift -> marketSkuPrices.compareTo(fullGift.getFullPrice()) >= 0).reduce((first, second) -> second);
                    fullGiftOptional.ifPresent(fullGift -> {
                        // 设置当前促销id
                        marketingResponse.setFullMarketingId(fullGift.getId());
                    });

                }

                // 计算满减满折后 减去的价格平摊到每个单品的价格
                marketingResponse.calcDiscountEverySkuPrice();
            });
        }

        // 原始总价格(单品详情页价格的总和)
        this.originTotalPrice = tempPriceMap.get("totalPrice");

        // 总的优惠价格
        this.totalDiscountPrice = tempPriceMap.get("discountPrice");

        // 订单的最总价格(不算运费) = 原始价格-优惠价格
        this.totalPrice = this.originTotalPrice.subtract(this.totalDiscountPrice);
    }

    /**
     * 计算店铺中每个运费模版的总运费
     *
     * @param logisticsTemplates 运费模版
     */
    public void calcFreightPrices(List<OmsLogisticsTemplate> logisticsTemplates) {
        if (CollectionUtils.isEmpty(logisticsTemplates)) {
            return;
        }

        // 获得每个运费模版的详细运费
        List<FreightPrice> freightPrices = logisticsTemplates.stream().map(this::calcFreightPrice).filter(Objects::nonNull).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(freightPrices)) {
            this.freightPrice = BigDecimal.ZERO;
            return;
        }

        // 按照首运费从大到小的顺序排序
        List<FreightPrice> sortedFreightPrices = freightPrices.stream().sorted((o1, o2) -> o2.getFirstPrice().compareTo(o1.getFirstPrice())).collect(Collectors.toList());

        // 首件/ 首重的运费实体
        FreightPrice firstFreightPrice = sortedFreightPrices.remove(0);

        //  首件/ 首重的运费
        BigDecimal freightPrice = firstFreightPrice.getFirstPrice().add(firstFreightPrice.getContinuationPrice());

        // 其余的全算续费
        this.freightPrice = freightPrice.add(sortedFreightPrices.stream().map(FreightPrice::getAllContinuationPrice).reduce(BigDecimal.ZERO, BigDecimal::add));
    }


    /**
     * 计算运费
     *
     * @param logisticsTemplate 运费模版
     */
    private FreightPrice calcFreightPrice(OmsLogisticsTemplate logisticsTemplate) {


        // 如果是商家承担运费 则直接返回 运费不计算
        if (logisticsTemplate.isStoreBearFreight()) {
            return FreightPrice.buildNoFreightPrice();
        }

        // 获得运费的运费方式
        PmsShippingMethod shippingMethod = logisticsTemplate.getCustomerShippingMethod();

        // 没有运费方式直接返回
        if (Objects.isNull(shippingMethod)) {
            return FreightPrice.buildNoFreightPrice();
        }

        // 判断单品的优惠后金额是否满足包邮的条件 如果满足包邮的条件直接返回0运费
        PmsShippingMethodFreeship shippingMethodFreeShip = logisticsTemplate.getShippingMethodFreeShip();

        if (Objects.nonNull(shippingMethodFreeShip)) {
            // 如果有包邮 则进行包邮的判断
            switch (shippingMethodFreeShip.getType()) {
                case 0:
                    // 按照件数包邮
                    if (this.logisticsSkuNum.get(logisticsTemplate.getId()).intValue() >= shippingMethodFreeShip.getNum()) {
                        return FreightPrice.buildNoFreightPrice();
                    }
                    break;
                case 1:
                    // 按照金额包邮
                    if (this.logisticsPrice.get(logisticsTemplate.getId()).compareTo(shippingMethodFreeShip.getMoney()) >= 0) {
                        return FreightPrice.buildNoFreightPrice();
                    }
                    break;
                default:
                    break;
            }

        }

        // 计算运费
        switch (logisticsTemplate.getPricintMethod()) {
            case "0":
                // 按件
                return calcNumFreightPrice(shippingMethod);
            case "1":
                // 按重量
                return calcWeightFreightPrice(shippingMethod);
            default:
                // 不计算运费
                return FreightPrice.buildNoFreightPrice();
        }


    }

    /**
     * 计算按件算的运费
     *
     * @param shippingMethod 运费方式
     * @return 返回运费
     */
    private FreightPrice calcNumFreightPrice(PmsShippingMethod shippingMethod) {
        return calcCommonFreightPrice(shippingMethod, this.logisticsSkuNum.get(Long.valueOf(shippingMethod.getTemplateId())));
    }

    /**
     * 计算按重量算的运费
     *
     * @param shippingMethod 运费方式
     * @return 返回运费
     */
    private FreightPrice calcWeightFreightPrice(PmsShippingMethod shippingMethod) {
        return calcCommonFreightPrice(shippingMethod, this.logisticsSkuWeight.get(Long.valueOf(shippingMethod.getTemplateId())));
    }

    /**
     * 计算运费的通用方法
     *
     * @param shippingMethod 运费方式
     * @param factor         可变因子
     * @return 返回运费
     */
    private FreightPrice calcCommonFreightPrice(PmsShippingMethod shippingMethod, BigDecimal factor) {

        // 运费实体
        FreightPrice freightPrice = new FreightPrice();

        // 全部按照续件或者需重的算的运费
        freightPrice.setAllContinuationPrice(factor.multiply(shippingMethod.getMoenyPlu().divide(new BigDecimal(shippingMethod.getFirstPlu()), 10, BigDecimal.ROUND_HALF_EVEN)).setScale(2, RoundingMode.HALF_EVEN));

        // 首件或者首重价格
        freightPrice.setFirstPrice(shippingMethod.getMoney());

        // 只有首件或者首重
        if (factor.compareTo(new BigDecimal(shippingMethod.getFirst())) < 0) {
            return freightPrice;
        }

        // 续件或者续重的价格
        freightPrice.setContinuationPrice(factor.subtract(new BigDecimal(shippingMethod.getFirst())).multiply(shippingMethod.getMoenyPlu().divide(new BigDecimal(shippingMethod.getFirstPlu()), 10, BigDecimal.ROUND_HALF_EVEN)).setScale(2, RoundingMode.HALF_EVEN));

        return freightPrice;
    }

    /**
     * 计算店铺订单的价格
     */
    public void calcPrice() {

        if (Objects.isNull(this.shoppingCartResponse)) {
            return;
        }

        // 计算订单价格
        calcMarketPrice();

        // 设置每个运费模版下单品优惠后的总金额和总的单品数量
        this.setLogisticsTemplateSkuAllPriceAndNum();
    }

    /**
     * 设置每个运费模版下单品的总价格和总的单品数量
     */
    private void setLogisticsTemplateSkuAllPriceAndNum() {

        this.logisticsPrice = new HashMap<>();
        this.logisticsSkuNum = new HashMap<>();
        this.logisticsSkuWeight = new HashMap<>();

        // 按照物流id进行分组 把同一个物流模版的单品放在一起
        Map<Long, List<SkuResponse>> skus = this.allSkus.stream().collect(Collectors.groupingBy(SkuResponse::getLogisticsTemplateId));

        skus.forEach((logisticsTemplateId, skuResponses) -> {
            this.logisticsPrice.put(logisticsTemplateId, skuResponses.stream().map(SkuResponse::getTotalPriceAfterDiscount).reduce(BigDecimal.ZERO, BigDecimal::add));
            this.logisticsSkuNum.put(logisticsTemplateId, skuResponses.stream().map(skuResponse -> new BigDecimal(skuResponse.getNum())).reduce(BigDecimal.ZERO, BigDecimal::add));
            this.logisticsSkuWeight.put(logisticsTemplateId, skuResponses.stream().map(SkuResponse::getLastWeight).reduce(BigDecimal.ZERO, BigDecimal::add));
        });
    }

    /**
     * 获得使用的优惠券的优惠价格
     *
     * @return 返回使用优惠券的优惠价格
     */
    public BigDecimal getUseCouponPrice() {
        // 没有试用优惠券 则直接返回
        if (Objects.isNull(this.choosedCoupon)) {
            return new BigDecimal(0);
        }

        // 使用了优惠券 则看优惠券减去的价格是否比订单的价格大 如果大 则返回订单的价格 否则返回优惠券减去的价格  防止价格出现负数
        if (this.choosedCoupon.getLastPrice().compareTo(this.totalPrice) > 0) {
            return this.totalPrice;
        }

        return this.choosedCoupon.getLastPrice();
    }

    /**
     * 判断是否免运费
     *
     * @param price 订单的最终价格 (商品原价 - 促销价格 -优惠券-积分)
     * @return 免运费
     */
    @JsonIgnore
    public boolean isFreeShip(BigDecimal price) {

        if (Objects.isNull(price)) {
            return false;
        }

        // 如果没有包邮促销 或者 包邮促销没有启用 则返回false
        if (Objects.isNull(this.freeShip) || !this.freeShip.isFreeShipUse()) {
            return false;
        }


        return price.compareTo(this.freeShip.getFreeShip().getFullPrice()) >= 0;
    }

    /**
     * 获得赠品信息
     *
     * @return 返回赠品信息
     */
    @JsonIgnore
    public String getGiftSkus() {
        // 没有促销信息直接返回
        if (CollectionUtils.isEmpty(this.shoppingCartResponse.getMarketings())) {
            return "";
        }

        // 赠品促销
        List<MarketingResponse> fullGiftMarketings = this.shoppingCartResponse.getMarketings().stream().filter(MarketingResponse::isFullGift).filter(marketingResponse -> marketingResponse.getFullMarketingId() != -1).collect(Collectors.toList());

        // 没有赠品促销 直接返回
        if (CollectionUtils.isEmpty(fullGiftMarketings)) {
            return "";
        }

        // 赠品信息
        List<OrderGift> orderGifts = fullGiftMarketings.stream().flatMap(fullGiftMarketing -> fullGiftMarketing.getFullGifts().stream().
                filter(fullGift -> fullGift.getId() == fullGiftMarketing.getFullMarketingId())).flatMap(fullGift1 -> fullGift1.getGiftSkuInfos().stream()).map(giftSkuInfo -> {
            OrderGift orderGift = new OrderGift();
            if (Objects.nonNull(giftSkuInfo.getSku())) {
                orderGift.setSkuId(giftSkuInfo.getSkuId());
                orderGift.setNum(giftSkuInfo.getNum());
                orderGift.setUrl(giftSkuInfo.getSku().getUrl());
                orderGift.setSkuName(giftSkuInfo.getSku().getName());
                orderGift.setSkuNo(giftSkuInfo.getSku().getSkuNo());
                orderGift.setSpecs(giftSkuInfo.getSku().getSpecValuesString());
            }
            return orderGift;
        }).collect(Collectors.toList());

        return CollectionUtils.isEmpty(orderGifts) ? "" : JSON.toJSONString(orderGifts);
    }


    /**
     * 是否有预售促销
     *
     * @return 有返回true  没有返回false
     */
    @JsonIgnore
    public boolean hasPreSaleMarketing() {
        return Objects.nonNull(this.preMarketing);
    }

    /**
     * 是否有拼团促销
     *
     * @return 有返回true  没有返回false
     */
    @JsonIgnore
    public boolean hasGroupMarekting() {
        return 1 == this.isGroup;
    }

    /**
     * 是否有订金预售
     *
     * @return 有返回true 没有返回false
     */
    @JsonIgnore
    public boolean hasDepositPresaleMarketing() {
        return hasPreSaleMarketing() && this.preMarketing.isDepositPreSaleMarketing();
    }

    /**
     * 是否可以使用积分
     *
     * @return 能使用积分的条件 1 平台订单 2 没有预售和众筹
     */
    public boolean isPointCanUse() {
        return 0 == this.storeId && !hasPreSaleMarketing() && !hasCrowdfunding();
    }


    /**
     * 是否是虚拟结算
     *
     * @return 是返回true 否返回false
     */
    @JsonIgnore
    public boolean isVirtualSettlment() {
        return "1".equals(this.isVirtual);
    }

    /**
     * 存在数量小于最小批发数量的单品
     */
    @JsonIgnore
    public boolean existNumLowerThanBatchSkuLimitNumSku() {
        return allSkus.stream().anyMatch(SkuResponse::numLowerThanBatchSkuLimitNum);
    }

    /**
     * 返回是否需要运费 是返回true 不需要返回fasle
     *
     * @return 如果是无回报支持订单或者虚拟订单 和众筹 则不需要运费 返回false
     */
    @JsonIgnore
    public boolean isNeedFreight() {
        if (this.isVirtualSettlment() || this.hasCrowdfunding()) {
            return false;
        }

        return true;
    }
}
