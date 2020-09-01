package com.ruoyi.order.service.impl;


import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.goods.service.SpuServiceApi;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.MarketingItem;
import com.ruoyi.marketing.service.MarketingQueryService;
import com.ruoyi.order.domain.OmsShoppingCart;
import com.ruoyi.order.service.CustomerPanicRecordService;
import com.ruoyi.order.service.IOmsShoppingCartService;
import com.ruoyi.order.service.ShoppingCartServiceApi;
import com.ruoyi.order.service.StoreShoppingCartServiceApi;
import com.ruoyi.order.vo.*;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.util.CommonConstant;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 魔金商城 on 17/8/9.
 * 购物车服务实现接口
 */
@Service
public class ShoppingCartServiceApiImpl implements ShoppingCartServiceApi {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(ShoppingCartServiceApiImpl.class);

    /**
     * 注入购物车服务接口
     */
    @Autowired
    private IOmsShoppingCartService shoppingCartService;

    /**
     * 注入店铺服务接口
     */
    @Autowired
    private ITStoreInfoService storeInfoService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入促销查询服务接口
     */
    @Autowired
    private MarketingQueryService marketingQueryService;

    /**
     * 商品服务接口
     */
    @Autowired
    private SpuServiceApi spuServiceApi;

    /**
     * 注入用户抢购纪录
     */
    @Autowired
    private CustomerPanicRecordService customerPanicRecordService;

    /**
     * 注入门店购物车服务接口
     */
    @Autowired
    private StoreShoppingCartServiceApi storeShoppingCartServiceApi;


    @Override
    public int addShoppingCart(OmsShoppingCart shoppingCart) {

        logger.debug("addShoppingCart and shoppingCart:{}", shoppingCart);

        // 校验参数
        if (Objects.isNull(shoppingCart) || !shoppingCart.validate()) {
            logger.error("addShoppingCart fail due to params is error....");
            return -3;
        }

        // 设置购物车中单品信息
        shoppingCart.setSku(skuService.querySkuById(shoppingCart.getSkuId()));

        // 校验单品信息 主要校验单品是否存在和状态
        int validateResult = validateSku(shoppingCart);

        // 单品校验不通过直接返回失败
        if (validateResult != 0) {
            logger.debug("addShoppingCart fail due to validateSku fail.....");
            return validateResult;
        }
        //设置批发规则
        skuService.setSkuDetail(shoppingCart.getSku(), PmsSkuItem.BATCH);

        // 判断加入的单品是否是预售商品 预售商品不能加入购物车只能立即购买
        if (!CollectionUtils.isEmpty(marketingQueryService.queryMarketingsBySkuId(shoppingCart.getSkuId(), false, MarketingItem.DEPOSIT_PRE_SALE_MARKETING, MarketingItem.FULL_PRE_SALE_MARKETING))) {
            logger.error("addShoppingCart fail due to sku has presale marketing....");
            return -6;
        }

        // 用户购物车中该单品已经有的数量
        int count = shoppingCartService.queryBySkuIdAndCustomerIdCount(shoppingCart.getSkuId(), shoppingCart.getCustomerId());
        //校验起订数量
        shoppingCart.setNum(Integer.max(shoppingCart.getNum(), shoppingCart.getSku().getLimitBatchNum()));
        // 判断是否达到了限购
        if (!validatePanicLimitNum(shoppingCart.getSkuId(), shoppingCart.getNum() + count, shoppingCart.getCustomerId())) {
            logger.error("sku panic limit buy");
            return -5;
        }

        //把用户购物车中已经拥有的数量加上用户购买的数量和库存对比判断库存是否足够
        if (!shoppingCart.getSku().validateStock(shoppingCart.getNum() + count)) {
            logger.error("sku stock is not enough.....");
            return -1;
        }

        // 判断用户购物车中是否已经有该商品
        if (count != 0) {
            // 更新购物车
            logger.info("member shoppingcart has alerdy exist this sku and skuId:{} \r\n customerId:{}", shoppingCart.getSkuId(), shoppingCart.getCustomerId());

            Map<String, Object> params = new HashMap<>();
            params.put("skuId", shoppingCart.getSkuId());
            params.put("num", shoppingCart.getNum());
            params.put("customerId", shoppingCart.getCustomerId());

            return shoppingCartService.updateAddShoppingCartNum(params);

        } else {
            // 新增购物车
            logger.info("member shoppingcart has no this sku...and begin to add....");
            // 设置购物车的店铺id
            shoppingCart.setCartStoreId();

            // 设置购物车中单品的默认促销(满减和满折,满赠,促销随机选择一个)
            setDefaultMarketing(shoppingCart);

            return shoppingCartService.addShoppingCart(shoppingCart);
        }
    }

    @Override
    public List<ShoppingCartResponse> queryShoppingCarts(long customerId) {
        logger.debug("queryShoppingCarts and customerId:{}", customerId);

        return getShoppingCartDetails(shoppingCartService.queryByCustomerId(customerId));
    }


    @Override
    public List<ShoppingCartResponse> queryShoppingCartsByIds(Long[] ids, Long customerId) {
        logger.debug("queryShoppingCartsByIds and ids:{} \r\n customerId:{}", ids, customerId);

        if (ArrayUtils.isEmpty(ids)) {
            logger.error("queryShoppingCartsByIds fail due to ids is empty....");
            return Collections.emptyList();
        }

        return getShoppingCartDetails(shoppingCartService.queryByIds(ids, customerId));
    }


    @Override
    public List<ShoppingCartResponse> queryShoppingCartsBySkuId(String skuInfo, Long customerId, Long crowdfundingId) {
        logger.debug("queryShoppingCartsBySkuId and skuInfo:{} \r\n customerId:{} \r\n crowdfundingId:{}", skuInfo, customerId, crowdfundingId);

        // 虚拟购物车信息
        OmsShoppingCart shoppingCart = buildVirtualShoppingCart(skuInfo, customerId);

        // 设置众筹id
        shoppingCart.setCrowdfundingId(crowdfundingId);

        return Objects.isNull(shoppingCart) ? Collections.emptyList() : getShoppingCartDetails(Arrays.asList(shoppingCart));
    }

    @Override
    public List<SkuResponse> queryMiniShoppingCarts(long customerId) {
        logger.debug("queryMiniShoppingCarts and customerId:{}", customerId);

        return getMiniShoppingCarts(this.queryShoppingCarts(customerId), storeShoppingCartServiceApi.queryShoppingCarts(customerId));
    }


    /**
     * 获得mini购物车的返回实体
     *
     * @param shoppingCartResponses      商城购物车
     * @param storeShoppingCartResponses 门店购物车
     * @return 返回mini购物车的实体
     */
    private List<SkuResponse> getMiniShoppingCarts(List<ShoppingCartResponse> shoppingCartResponses, List<StoreShoppingCartResponse> storeShoppingCartResponses) {

        // mini购物车的返回实体
        List<SkuResponse> skuResponses = new ArrayList<>();

        if (!CollectionUtils.isEmpty(shoppingCartResponses)) {
            skuResponses.addAll(shoppingCartResponses.stream().flatMap(x -> x.getNormalSkus().stream()).map(skuResponse -> {
                skuResponse.setType("1");
                return skuResponse;
            }).collect(Collectors.toList()));

            skuResponses.addAll(shoppingCartResponses.stream().flatMap(x -> x.getMarketings().stream()).flatMap(y -> y.getSkus().stream()).map(skuResponse -> {
                skuResponse.setType("1");
                return skuResponse;
            }).collect(Collectors.toList()));

        }

        if (!CollectionUtils.isEmpty(storeShoppingCartResponses)) {
            skuResponses.addAll(storeShoppingCartResponses.stream().flatMap(x -> x.getNormalSkus().stream()).map(skuResponse -> {
                skuResponse.setType("2");
                return skuResponse;
            }).collect(Collectors.toList()));
        }

        return skuResponses;

    }


    /**
     * 构造虚拟购物车(立即购买的时候使用)
     *
     * @param skuInfo    单品信息  格式  单品id,单品件数
     * @param customerId 会员id
     * @return 返回虚拟购物车
     */
    private OmsShoppingCart buildVirtualShoppingCart(String skuInfo, long customerId) {
        logger.debug("buildVirtualShoppingCart and skuInfo:{} \r\n customerId:{}", skuInfo, customerId);
        if (StringUtils.isEmpty(skuInfo) || !skuInfo.contains(",") || skuInfo.split(",").length != 2 || !NumberUtils.isNumber(skuInfo.split(",")[1])) {
            return null;
        }

        // 购物车信息
        OmsShoppingCart shoppingCart = OmsShoppingCart.build(skuInfo.split(",")[0], Integer.parseInt(skuInfo.split(",")[1]), customerId);

        // 查询单品信息
        PmsSku sku = skuService.querySkuById(skuInfo.split(",")[0]);

        // 单品信息不存在 直接返回
        if (Objects.isNull(sku)) {
            logger.error("buildVirtualShoppingCart fail: sku is null");
            return null;
        }

        //校验limitBatchNum
        skuService.setSkuDetail(sku, PmsSkuItem.BATCH);
        if (Integer.parseInt(skuInfo.split(",")[1]) < sku.getLimitBatchNum()) {
            logger.error("buildVirtualShoppingCart fail: num is lower than limitBatchNum");
            return null;
        }
        shoppingCart.setSpuId(sku.getSpuId());
        // 设置单品信息
        shoppingCart.setSku(sku);
        // 设置店铺id
        shoppingCart.setCartStoreId();
        // 设置默认的促销
        setDefaultMarketing(shoppingCart);
        return shoppingCart;
    }

    /**
     * 获得购物车的详细信息(主要是优惠 和价格)
     *
     * @param shoppingCarts 购物车
     * @return 返回购物车详细
     */
    private List<ShoppingCartResponse> getShoppingCartDetails(List<OmsShoppingCart> shoppingCarts) {

        logger.debug("getShoppingCartDetails and shoppingCarts:{}", shoppingCarts);

        // 用户没有购物车 则直接返回
        if (CollectionUtils.isEmpty(shoppingCarts)) {
            logger.error("getShoppingCartDetails fail due to shoppingCarts is empty...");
            return Collections.emptyList();
        }

        // 所有店铺的购物车信息List<List<ShoppingCart>> List中的list<ShoppingCart> 是同一个店铺下的所有购物车信息,组织这样的数据模型方便后面的并行运算,每个店铺的购物车都并行计算
        List<List<OmsShoppingCart>> allStoreShoppingCarts = new ArrayList<>();

        // 根据店铺进行购物后的分类,一个店铺的购物车放一起
        shoppingCarts.stream().collect(Collectors.groupingBy(OmsShoppingCart::getStoreId)).forEach((key, value) -> allStoreShoppingCarts.add(value));

        // 并行计算每个店铺的购物车信息 然后进行汇总
        return allStoreShoppingCarts.parallelStream().map(this::getShoppingCartResponse).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * 获得单个店铺的购物车信息
     *
     * @param shoppingCarts 单个店铺下的购物车
     * @return 返回单个店铺的购物车信息
     */
    private ShoppingCartResponse getShoppingCartResponse(List<OmsShoppingCart> shoppingCarts) {

        logger.debug("getShoppingCartResponse and shoppingCarts:{}", shoppingCarts);

        // 店铺购物车的返回数据 一个店铺返回一个实体
        ShoppingCartResponse shoppingCartResponse = ShoppingCartResponse.build(storeInfoService.queryStoreInfo(shoppingCarts.stream().findFirst().get().getStoreId()));

        // 单品返回信息(根据购物车中的单品组装成返回的单品信息 并且计算过促销折后的价格,促销只包含 会员价,抢购,直降 )
        List<SkuResponse> skuResponses = shoppingCarts.parallelStream().map(this::getsSkuResponse).filter(Objects::nonNull).collect(Collectors.toList());

       // logger.debug("skuResponses:{}", skuResponses);

        // 如果没有单品信息 则直接返回
        if (CollectionUtils.isEmpty(skuResponses)) {
            logger.warn("shoppingcart no skus....");
            return null;
        }

        // 查询所有单品的促销信息(满减,满折,满赠)
        List<MarketingResponse> marketingResponses = shoppingCarts.stream().map(this::getMarketingResponses).collect(ArrayList::new, List::addAll, List::addAll);

      //  logger.debug("all sku marketings:{}", marketingResponses);

        // 没有促销直接返回(满减,满折,满赠)
        if (CollectionUtils.isEmpty(marketingResponses)) {
            shoppingCartResponse.setNormalSkus(skuResponses);
            return shoppingCartResponse;
        }

        // 设置满减,满折,满赠的促销 以及促销下的单品信息
        setFullDiscountOrDownsMarketingResponses(marketingResponses, skuResponses, shoppingCartResponse);

        // 设置没有促销的单品信息
        shoppingCartResponse.setNormalSkus(skuResponses.stream().filter(SkuResponse::hasNoMarketing).collect(Collectors.toList()));

        // 满减 满折 满赠 同一类的进行归类
        shoppingCartResponse.categorizedMarketings();

       // logger.debug("last shoppingCartResponse:{}", shoppingCartResponse);

        return shoppingCartResponse;
    }

    /**
     * 设置满减,满折的促销 以及促销下的单品信息
     *
     * @param marketingResponses   所有促销信息
     * @param skuResponses         单品信息
     * @param shoppingCartResponse 店铺购物车信息
     */
    private void setFullDiscountOrDownsMarketingResponses(List<MarketingResponse> marketingResponses, List<SkuResponse> skuResponses, ShoppingCartResponse shoppingCartResponse) {
        if (CollectionUtils.isEmpty(marketingResponses)) {
            return;
        }

        // 设置满减或者满折促销的单品信息
        marketingResponses.stream().forEach(fullDiscountOrDown -> skuResponses.stream().forEach(skuResponse -> {
            if (fullDiscountOrDown.getSkuId().equals(skuResponse.getSkuId()) && fullDiscountOrDown.getId() == skuResponse.getMarketingId()) {
                skuResponse.setHasMarketing(true);
                fullDiscountOrDown.getSkus().add(skuResponse);
            }
        }));

        shoppingCartResponse.getMarketings().addAll(removeNoSkuMarketing(marketingResponses));
    }

    /**
     * 去除没有单品的促销信息
     *
     * @param marketingResponses 促销信息
     * @return 返回有单的促销信息
     */
    private List<MarketingResponse> removeNoSkuMarketing(List<MarketingResponse> marketingResponses) {
        return marketingResponses.stream().filter(marketingResponse -> !CollectionUtils.isEmpty(marketingResponse.getSkus())).collect(Collectors.toList());
    }


    /**
     * 获得单品的返回实体
     *
     * @param shoppingCart 购物车
     * @return 返回单品的实体
     */
    private SkuResponse getsSkuResponse(OmsShoppingCart shoppingCart) {

        // 单品信息
        PmsSku sku = skuService.setSkuDetail(skuService.querySkuById(shoppingCart.getSkuId()), PmsSkuItem.SPEC, PmsSkuItem.BATCH);

        if (Objects.isNull(sku) || !sku.validate(shoppingCart.isCrowdfunding())) {
            logger.warn("shoppingCart :{} no sku ....", shoppingCart);
            return null;
        }

        // 设置购物车中单品的信息
        shoppingCart.setSku(sku);
        // 单品的最终价格实体
        SkuMarketPriceDetail skuMarketPriceDetail = spuServiceApi.calcMarketingPrice(sku, shoppingCart.getSkuPrice(), shoppingCart.getCustomerId());

        return setPanicInfo(SkuResponse.build(shoppingCart).setMarketingPrice(skuMarketPriceDetail), shoppingCart.getCustomerId(), skuMarketPriceDetail);
    }

    /**
     * 设置抢购信息(主要是设置抢购的限购数量和抢购的id)
     *
     * @param skuResponse          单品购物车信息
     * @param customerId           用户id
     * @param skuMarketPriceDetail 单品促销价格详情实体(主要是会员价,抢购,团购)
     * @return 返回单品新
     */
    private SkuResponse setPanicInfo(SkuResponse skuResponse, long customerId, SkuMarketPriceDetail skuMarketPriceDetail) {
      //  logger.debug("getPanicLimitNum and skuResponse:{} \r\n customerId:{} \r\n skuMarketPriceDetail:{}", skuResponse, customerId, skuMarketPriceDetail);
        // 如果没使用抢购则直接返回
        if (Objects.isNull(skuMarketPriceDetail) || !skuMarketPriceDetail.isPanic()) {
            logger.info("no panic exist");
            return skuResponse;
        }

        // 存在抢购首先查询用户可以使用的抢购数量
        int canUseNum = customerPanicRecordService.queryCanUseNum(customerId, skuMarketPriceDetail.getMarketing().getPanicBuy().getId());

        // 如果用户还没有购买过该抢购 则直接返回该抢购的限购个数,否则返回用户可以购买的抢购个数
        canUseNum = CommonConstant.NO_USE_PANIC == canUseNum ? skuMarketPriceDetail.getMarketing().getPanicBuy().getLimitNum() : canUseNum;

        // 设置抢购可以使用的数量
        skuResponse.setLimitStock(canUseNum);

        // 设置单品抢购的id
        skuResponse.setPanicMarketId(skuMarketPriceDetail.getMarketing().getPanicBuy().getId());
        return skuResponse;
    }

    /**
     * 获得单品的促销信息
     *
     * @param shoppingCart 购物车信息
     * @return 返回促销信息
     */
    private List<MarketingResponse> getMarketingResponses(OmsShoppingCart shoppingCart) {

        // 查询单品所有的促销(满减和满折和满赠,预售)
        List<Marketing> marketings = marketingQueryService.queryMarketingsBySkuId(shoppingCart.getSkuId(), true, MarketingItem.FULL_DOWN_MARKETING, MarketingItem.FULL_DISCOUNT_MARKETING, MarketingItem.FULL_GIFT_MARKETING, MarketingItem.DEPOSIT_PRE_SALE_MARKETING, MarketingItem.FULL_PRE_SALE_MARKETING);

        // 如果没有促销 或者有预售促销 则直接返回
        if (CollectionUtils.isEmpty(marketings) || !CollectionUtils.isEmpty(marketings.stream().filter(marketing -> marketing.isDepositPreSaleMarketing() || marketing.isFullPreSaleMarkting()).collect(Collectors.toList()))) {
            return Collections.emptyList();
        }

        return marketings.stream().map(MarketingResponse::build).map(marketingResponse -> marketingResponse.setSkuId(shoppingCart.getSkuId())).collect(Collectors.toList());
    }


    /**
     * 校验抢购限制数  校验通过返回true  没通过返回false
     *
     * @param skuId      单品id
     * @param buyNum     用户购买的数量
     * @param customerId 会员id
     * @return 没达到抢购数量 则校验通过 否则校验不通过
     */

    private boolean validatePanicLimitNum(String skuId, int buyNum, long customerId) {
        logger.debug("validatePanicLimitNum and skuId:{} and buyNum:{} and customerId:{}", skuId, buyNum, customerId);
        // 首先根据单品id查询单品的抢购促销
        Marketing marketing = marketingQueryService.queryPanicBuyMaketingBySkuId(skuId);
        // 没有抢购信息或者抢购信息不在有效事件内则直接返回true  校验通过
        if (Objects.isNull(marketing) || !marketing.maketingTimeValidate()) {
            logger.info("sku has no panic marketing info...");
            return true;
        }


        // 查询用户可以购买抢购的数量
        int canBuyNum = getCanBuyPanicNum(customerId, marketing.getPanicBuy().getId());

        // 用户还没购买过抢购 则将可以购买的数量设置抢购的限购数量
        if (canBuyNum == CommonConstant.NO_USE_PANIC) {
            logger.info("this sku has panic and user has not buyed...");
            canBuyNum = marketing.getPanicBuy().getLimitNum();
        }

        logger.info("user can use num:{} and user buy num:{}", canBuyNum, buyNum);

        // 可以购买的数量>= 用户购买的数量返回true
        return canBuyNum >= buyNum;
    }

    /**
     * 获得用户可以购买抢购的数量
     *
     * @param customerId  会员id
     * @param marketingId 抢购id
     * @return 返回用户可以购买抢购的数量
     */
    private int getCanBuyPanicNum(long customerId, long marketingId) {
        return customerPanicRecordService.queryCanUseNum(customerId, marketingId);
    }


    /**
     * 校验单品信息 主要校验单品是否存在和单品状态
     *
     * @param shoppingCart 购物车信息
     * @return 成功返回0  -2 单品不存在 -4 单品状态不对 -7 店铺状态异常
     */
    private int validateSku(OmsShoppingCart shoppingCart) {
        PmsSku sku = shoppingCart.getSku();
        if (Objects.isNull(sku)) {
            logger.error("validateSku fail due to sku is not exist");
            return -2;
        }

        // 校验单品是否是上架状态
        if (!sku.validateStatus()) {
            logger.error("validateSku fail due to  sku status is error...");
            return -4;
        }

        //校验店铺是否有效
        if (!storeInfoService.isEffective(sku.getStoreId())) {
            logger.error("validateSku fail : store is not effective");
            return -7;
        }
        return 0;
    }


    /**
     * 设置购物车中单品的默认促销(满减和满折,满赠促销随机选择一个)
     *
     * @param shoppingCart 购物车
     */
    private void setDefaultMarketing(OmsShoppingCart shoppingCart) {
        getDefaultMarketingId(shoppingCart.getSkuId()).ifPresent(shoppingCart::setMarketingId);
    }


    /**
     * 获得默认的促销id
     *
     * @param skuId 单品id
     * @return 返回默认的促销id (促销是 满减和满折)
     */
    private Optional<Long> getDefaultMarketingId(String skuId) {

        // 单品有效的满减和满折,满赠促销 (随机查出一个)
        Marketing marketing = marketingQueryService.queryMarketingForShoppingCart(skuId);

        if (Objects.isNull(marketing)) {
            return Optional.empty();
        } else {
            return Optional.of(marketing.getId());
        }
    }


}
