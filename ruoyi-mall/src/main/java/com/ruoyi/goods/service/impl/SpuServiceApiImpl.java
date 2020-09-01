package com.ruoyi.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.IteratorUtils;
import com.ruoyi.common.utils.WeChatAppletUtils;
import com.ruoyi.common.utils.bean.WeChatAppletCodeRequest;
import com.ruoyi.common.utils.bean.WechatSetting;
import com.ruoyi.goods.domain.*;
import com.ruoyi.goods.service.*;
import com.ruoyi.goods.vo.CombinationDetail;
import com.ruoyi.goods.vo.SpuDetail;
import com.ruoyi.goods.vo.SpuDetailItem;
import com.ruoyi.marketing.domain.GoodsCombination;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.MarketingItem;
import com.ruoyi.marketing.domain.MarketingSetting;
import com.ruoyi.marketing.service.*;
import com.ruoyi.marketing.vo.CrowdFundingSpuDetail;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.WxAppletAccessTokenService;
import com.ruoyi.order.domain.OmsLogisticsTemplate;
import com.ruoyi.order.service.IOmsLogisticsTemplateService;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.order.vo.SkuMarketPriceDetail;
import com.ruoyi.setting.bean.WechatPaySet;
import com.ruoyi.setting.service.BaseInfoSetService;
import com.ruoyi.setting.service.ILsPaySettingService;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.ITStoreCommentService;
import com.ruoyi.store.service.ITStoreInfoService;
import com.ruoyi.store.service.StoreInfoServiceApi;
import com.ruoyi.store.vo.StoreItem;
import com.ruoyi.util.CalcFreightUtil;
import com.ruoyi.util.CommonConstant;
import org.apache.commons.lang.ArrayUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by 魔金商城 on 17/11/22.
 * 商品服务接口实现
 */
@Service
public class SpuServiceApiImpl implements SpuServiceApi {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(SpuServiceApiImpl.class);

    /**
     * 注入商品服务接口
     */
    @Autowired
    private IPmsGoodsService spuService;

    /**
     * 注入店铺服务接口
     */
    @Autowired
    private StoreInfoServiceApi storeInfoServiceApi;

    /**
     * 单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入商品服务接口
     */
    @Autowired
    private IPmsGoodsServiceSupportService spuServicceSupportService;

    /**
     * 注入商品属性值服务接口
     */
    @Autowired
    private IPmsGoodsAttributeValueService spuAttributeValueService;

    /**
     * 注入营销查询接口
     */
    @Autowired
    private MarketingQueryService marketingQueryService;

    /**
     * 注入店铺信息服务
     */
    @Autowired
    private ITStoreInfoService storeInfoService;


    /**
     * 注入众筹进度服务接口
     */
    @Autowired
    private CrowdfundingProgressService crowdfundingProgressService;

    /**
     * 注入单品会员价格服务接口
     */
    @Autowired
    private IPmsSkuMemberPriceService skuMemberPriceServicce;

    /**
     * 注入会员服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 优惠券接口
     */
    @Autowired
    private CouponService couponService;

    /**
     * 注入规格服务接口
     */
    @Autowired
    private IPmsSpecService specService;

    /**
     * 注入评论service
     */
    @Autowired
    private IPmsCommentService commentService;

    /**
     * 商品关注服务接口
     */
    @Autowired
    private IPmsAttentionService attentionService;

    /**
     * 注入分类服务接口
     */
    @Autowired
    private IPmsCategoryService categoryService;

    /**
     * 注入商品组合服务接口
     */
    @Autowired
    private GoodsCombinationService goodsCombinationService;

    /**
     * 注入店铺品论服务接口
     */
    @Autowired
    private ITStoreCommentService storeCommentService;

    /**
     * 注入品牌服务接口
     */
    @Autowired
    private IPmsBrandService brandService;

    /**
     * 注入基本信息接口
     */
    @Autowired
    private BaseInfoSetService baseInfoSetService;

    /**
     * 注入运费模板服务
     */
    @Autowired
    private IOmsLogisticsTemplateService logisticsTemplateService;

    /**
     * 注入营销设置服务
     */
    @Autowired
    private MarketingSettingService marketingSettingService;

    /**
     * 注入订单服务
     */
    @Autowired
    private IOmsOrderService orderService;

    /**
     * 注入支付设置服务
     */
    @Autowired
    private ILsPaySettingService paySetService;


    /**
     * 注入又拍云服务接口
     */
    // @Autowired
    // private OssService ossService;

    /**
     * 注入微信小程序access_token服务接口
     */
    @Autowired
    private WxAppletAccessTokenService wxAppletAccessTokenService;


    @Override
    public Optional<SpuDetail> queryGoodsDetail(Long goodsId, long customerId, SpuDetailItem... spuDetailItems) {
        logger.debug("querySpuDetail and goodsId:{} \r\n customerId:{} \r\n spuDetailItems:{}", goodsId, customerId, spuDetailItems);

        // 商品信息
        PmsGoods spu = spuService.querySimpleSpu(goodsId, CommonConstant.QUERY_WITH_NO_STORE);
        if (spu==null || spu.getId()==null) {
            logger.error("querySpuDetail fail due to spu validate fail...spu:{}", spu);
            return Optional.empty();
        }
        // 查询单品的信息
        PmsSku sku = null;

        List<PmsSku> skuList = skuService.querySkuBySpuId(spu.getId(), spu.getStoreId());
        if (skuList != null && skuList.size() > 0) {
            sku = skuList.get(0);
        }
        // 校验单品
        if (!validateSku(sku)) {
            logger.error("querySpuDetail fail due to sku validate fail...sku:{}", sku);
            return Optional.empty();
        }

        // 设置单品详情
        skuService.setSkuDetail(sku, PmsSkuItem.IMAGE, PmsSkuItem.SPEC, PmsSkuItem.BATCH);

        // 商品详情
        SpuDetail spuDetail = SpuDetail.build(Optional.of(sku)).addSkuImages(sku.getSkuImages()).addStoreInfo(storeInfoServiceApi.queryStoreInfo(sku.getStoreId(), StoreItem.ATTENNUM)).
                addServiceSupports(spuServicceSupportService.queryBySpuId(sku.getSpuId(), SpuServiceSupportItem.SERVICE_SUPPORT)).addMarketPrice(this.calcMarketingPrice(sku, sku.getPrice(), customerId)).
                addSkuSpecValues(sku.getSkuSpecValues()).addSpuAttributeValues(spuAttributeValueService.queryBySpuId(sku.getSpuId())).addTypeId(spu).addVideoInfo(spu);

        spuDetail.setMarketings(marketingQueryService.queryMarketingsBySkuId(sku.getId(), true, MarketingItem.FULL_DOWN_MARKETING, MarketingItem.FULL_DISCOUNT_MARKETING, MarketingItem.FULL_GIFT_MARKETING, MarketingItem.DEPOSIT_PRE_SALE_MARKETING, MarketingItem.FULL_PRE_SALE_MARKETING, MarketingItem.GROUP_MARKETING,MarketingItem.FALL_MARKETING));
        spuDetail.setSkuList(skuList);
        //设置单品信息
        spuDetail.setSku(sku);

        //设置营销规则
        MarketingSetting marketingSetting = marketingSettingService.queryMarketingSetting();
        if (Objects.nonNull(marketingSetting)) {
            spuDetail.setPreSaleRule(marketingSetting.getPreSaleRule());
        }

        // 设置商品的优惠券
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.COUPON)) {
            spuDetail.addCoupons(couponService.queryCouponForSpu(sku.getStoreId(), true, 1));
        }

        // 设置商品的所有规格信息
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.SPU_SPECS)) {
            spuDetail.setSpecs(specService.querySpuSpecs(spuDetail.getSpuId()));
        }

        // 商品被评论的数量
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.SKU_COMMENT_NUM)) {
            spuDetail.setSkuCommentNum(commentService.queryCommentCountBySkuId(sku.getId()));
        }

        // 是否收藏该商品
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.FOLLOW)) {
            spuDetail.setHasAtten(attentionService.hasAttention(customerId, sku.getId()));
        }

        // 设置商品的分类信息
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.CATE)) {
            spuDetail.setCategories(categoryService.queryAllParentCategoryById(spu.getThirdCateId()));
        }

        // 设置店铺评分
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.STORE_SCORE)) {
            spuDetail.setStoreScore(storeCommentService.queryStoreScoreWithStoreInfo(spu.getStoreId()));
        }

        // 设置商品品牌
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.BRAND)) {
            spuDetail.setBrand(brandService.queryBrandById(spu.getBrandId(), CommonConstant.QUERY_WITH_NO_STORE));
        }

        // 设置pc版详情
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.PC_DESC)) {
            spuDetail.setPcDesc(spu.getPcDesc());
        }

        // 设置mobile版详情
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.MOBILE_DESC)) {
            spuDetail.setMobileDesc(spu.getMobileDesc());
        }

        return Optional.of(spuDetail);
    }

    @Override
    public Optional<SpuDetail> querySpuDetail(String skuId, long customerId, SpuDetailItem... spuDetailItems) {
        logger.debug("querySpuDetail and skuId:{} \r\n customerId:{} \r\n spuDetailItems:{}", skuId, customerId, spuDetailItems);

        // 查询单品的信息
        PmsSku sku = skuService.querySkuById(skuId);

        // 校验单品
        if (!validateSku(sku)) {
            logger.error("querySpuDetail fail due to sku validate fail...sku:{}", sku);
            return Optional.empty();
        }

        // 设置单品详情
        skuService.setSkuDetail(sku, PmsSkuItem.IMAGE, PmsSkuItem.SPEC, PmsSkuItem.BATCH);

        // 商品信息
        PmsGoods spu = spuService.querySimpleSpu(sku.getSpuId(), CommonConstant.QUERY_WITH_NO_STORE);

        // 商品详情
        SpuDetail spuDetail = SpuDetail.build(Optional.of(sku)).addMarketings(marketingQueryService.queryMarketingsBySkuId(skuId, true, MarketingItem.FULL_DOWN_MARKETING, MarketingItem.FULL_DISCOUNT_MARKETING, MarketingItem.FULL_GIFT_MARKETING, MarketingItem.DEPOSIT_PRE_SALE_MARKETING, MarketingItem.FULL_PRE_SALE_MARKETING, MarketingItem.GROUP_MARKETING)).addSkuImages(sku.getSkuImages()).addStoreInfo(storeInfoServiceApi.queryStoreInfo(sku.getStoreId(), StoreItem.ATTENNUM)).
                addServiceSupports(spuServicceSupportService.queryBySpuId(sku.getSpuId(), SpuServiceSupportItem.SERVICE_SUPPORT)).addMarketPrice(this.calcMarketingPrice(sku, sku.getPrice(), customerId)).
                addSkuSpecValues(sku.getSkuSpecValues()).addSpuAttributeValues(spuAttributeValueService.queryBySpuId(sku.getSpuId())).addTypeId(spu).addVideoInfo(spu);

        //设置单品信息
        spuDetail.setSku(sku);

        //设置营销规则
        MarketingSetting marketingSetting = marketingSettingService.queryMarketingSetting();
        if (Objects.nonNull(marketingSetting)) {
            spuDetail.setPreSaleRule(marketingSetting.getPreSaleRule());
        }

        // 设置商品的优惠券
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.COUPON)) {
            spuDetail.addCoupons(couponService.queryCouponForSpu(sku.getStoreId(), true, 1));
        }

        // 设置商品的所有规格信息
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.SPU_SPECS)) {
            spuDetail.setSpecs(specService.querySpuSpecs(spuDetail.getSpuId()));
        }

        // 商品被评论的数量
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.SKU_COMMENT_NUM)) {
            spuDetail.setSkuCommentNum(commentService.queryCommentCountBySkuId(skuId));
        }

        // 是否收藏该商品
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.FOLLOW)) {
            spuDetail.setHasAtten(attentionService.hasAttention(customerId, skuId));
        }

        // 设置商品的分类信息
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.CATE)) {
            spuDetail.setCategories(categoryService.queryAllParentCategoryById(spu.getThirdCateId()));
        }

        // 设置店铺评分
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.STORE_SCORE)) {
            spuDetail.setStoreScore(storeCommentService.queryStoreScoreWithStoreInfo(spu.getStoreId()));
        }

        // 设置商品品牌
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.BRAND)) {
            spuDetail.setBrand(brandService.queryBrandById(spu.getBrandId(), CommonConstant.QUERY_WITH_NO_STORE));
        }

        // 设置pc版详情
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.PC_DESC)) {
            spuDetail.setPcDesc(spu.getPcDesc());
        }

        // 设置mobile版详情
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.MOBILE_DESC)) {
            spuDetail.setMobileDesc(spu.getMobileDesc());
        }

        return Optional.of(spuDetail);
    }

    @Override
    public CrowdFundingSpuDetail queryCrowdFundingSpuDetail(long marketingId, String skuId, boolean isNeedMarketingDetail, SpuDetailItem... spuDetailItems) {
        logger.debug("queryCrowdFundingSpuDetail and marketingId:{} \r\n skuId:{} \r\n isNeedMarketingDetail:{} \r\n spuDetailItems:{}", marketingId, skuId, isNeedMarketingDetail, spuDetailItems);
        Marketing marketing = marketingQueryService.queryMarketingById(marketingId, CommonConstant.QUERY_WITH_NO_STORE);
        if (Objects.isNull(marketing)) {
            logger.error("queryCrowdFundingSpuDetail fail: no marketing");
            return null;
        }
        if (marketing.getStartTime().isAfter(LocalDateTime.now())) {
            logger.error("queryCrowdFundingSpuDetail fail:  marketing is not start");
            return null;
        }
        if (CollectionUtils.isEmpty(marketing.getMarketingSkus())) {
            logger.error("queryCrowdFundingSpuDetail fail : no marketing sku");
            return null;
        }
        if (StringUtils.isEmpty(skuId)) {
            skuId = marketing.getMarketingSkus().get(0).getSkuId();
        }
        PmsSku sku = skuService.querySkuById(skuId);
        // 设置单品详情
        skuService.setSkuDetail(sku, PmsSkuItem.IMAGE, PmsSkuItem.SPEC);
        if (Objects.isNull(sku)) {
            logger.error("queryCrowdFundingSpuDetail fail : no sku");
            return null;
        }
        sku.setPrice(marketing.getCrowdFundingSkuPrice(skuId));
        // 商品信息
        PmsGoods spu = spuService.querySimpleSpu(sku.getSpuId(), CommonConstant.QUERY_WITH_NO_STORE);

        CrowdFundingSpuDetail crowdFundingSpuDetail = CrowdFundingSpuDetail.build(sku).addServiceSupports(spuServicceSupportService.queryBySpuId(sku.getSpuId(), SpuServiceSupportItem.SERVICE_SUPPORT))
                .addSpuAttributeValues(spuAttributeValueService.queryBySpuId(sku.getSpuId())).addCategories(categoryService.queryAllParentCategoryById(spu.getThirdCateId()))
                .addStoreName(storeInfoService.queryStoreInfo(sku.getStoreId()));

        // 设置商品的所有规格信息
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.SPU_SPECS)) {
            crowdFundingSpuDetail.setSpecs(specService.querySpuSpecs(crowdFundingSpuDetail.getSpuId()));
        }
        // 设置pc版详情
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.PC_DESC)) {
            crowdFundingSpuDetail.setPcDesc(spu.getPcDesc());
        }
        // 设置mobile版详情
        if (ArrayUtils.contains(spuDetailItems, SpuDetailItem.MOBILE_DESC)) {
            crowdFundingSpuDetail.setMobileDesc(spu.getMobileDesc());
        }
        //设置众筹促销信息
        if (isNeedMarketingDetail) {
            crowdFundingSpuDetail.buildForSite(marketing,
                    crowdfundingProgressService.queryCrowdfundingProgressByMarketingId(marketingId, CommonConstant.QUERY_WITH_NO_STORE),
                    marketingSettingService.queryMarketingSetting(), orderService.queryCrowFundingCustomerCount(marketingId, CommonConstant.QUERY_WITH_NO_STORE));
        }
        return crowdFundingSpuDetail;

    }


    /**
     * 计算单品的价格(会员价格,抢购价格和直降)
     * <p>
     * 规则 抢购>直降>会员价格
     * 如果有全款预售或者定金预售 则直接返回原价
     *
     * @param sku        单品
     * @param price      原价
     * @param customerId 会员id
     * @return 返回单品信息
     */
    @Override
    public SkuMarketPriceDetail calcMarketingPrice(PmsSku sku, BigDecimal price, long customerId) {

       // logger.debug("calcMarketingPrice and sku:{} \r\n and price:{} \r\n and customerId:{}", sku, price, customerId);

        // 如果价格或者单品为空 则直接返回
        if (Objects.isNull(price) || StringUtils.isEmpty(sku)) {
            logger.error("calcMarketingPrice fail due to params is error..");
            return new SkuMarketPriceDetail();
        }
        String skuId = sku.getId();
        // 价格详情
        SkuMarketPriceDetail skuMarketPriceDetail = SkuMarketPriceDetail.build(price);

        // 单品的促销信息 直降,抢购,预售
        List<Marketing> marketings = marketingQueryService.queryMarketingsBySkuId(skuId, true, MarketingItem.FALL_MARKETING, MarketingItem.PANIC_BUY_MARKETING, MarketingItem.DEPOSIT_PRE_SALE_MARKETING, MarketingItem.FULL_PRE_SALE_MARKETING);

        // 没有促销则计算会员价格 有促销则计算促销价格(促销和会员并存的时候 拿促销价格)
        if (CollectionUtils.isEmpty(marketings)) {
            // 计算会员价格
            return this.calcMemberPrice(skuId, price, customerId);
        }

        // 匹配定金预售 定金预售优先
        Optional<Marketing> marketing = IteratorUtils.filterMatch(marketings, Marketing::isDepositPreSaleMarketing);

        // 匹配全款预售
        if (!marketing.isPresent()) {
            marketing = IteratorUtils.filterMatch(marketings, Marketing::isFullPreSaleMarkting);
        }

        // 匹配抢购
        if (!marketing.isPresent()) {
            marketing = IteratorUtils.filterMatch(marketings, Marketing::isPanicBuyMarketing);
        }

        // 则匹配直降
        if (!marketing.isPresent()) {
            marketing = IteratorUtils.filterMatch(marketings, Marketing::isFallMarketing);
        }

        // 如果没促销则直接返回
        if (!marketing.isPresent()) {
            return skuMarketPriceDetail;
        }

        //返回计算折扣后的价格
        return skuMarketPriceDetail.setPriceToMarketPrice(marketing.get(), sku);

    }

    @Override
    public SkuMarketPriceDetail calcMemberPrice(String skuId, BigDecimal price, long customerId) {

        logger.debug("calcMemberPrice and skuId:{} \r\n price:{} \r\n customerId:{}", skuId, price, customerId);

        SkuMarketPriceDetail skuMarketPriceDetail = SkuMarketPriceDetail.build(price);

        // 如果会员价格没有开启 则直接返回
        if (!baseInfoSetService.queryBaseInfoSet().isMemberPriceOpen()) {
            logger.info("MemberPrice is not open...");
            return skuMarketPriceDetail;
        }

        // 获得会员价格
        PmsSkuMemberPrice skuMemberPrice = getSkuMemberPrice(skuId, customerId);

        // 如果会员价格不为空 则直接设置会员价格
        if (Objects.nonNull(skuMemberPrice)) {
            // 设置会员价格然后返回
            return skuMarketPriceDetail.setPriceToMemberPrice(skuMemberPrice.getPrice());
        }

        return skuMarketPriceDetail;
    }

    @Override
    public CombinationDetail queryGoodsCombinationBySkuId(String skuId, long customerId) {
        logger.debug("queryGoodsCombinationBySkuId and skuId:{},customerId{}", skuId, customerId);

        if (StringUtils.isEmpty(skuId)) {
            logger.error("queryGoodsCombinationBySkuId fail due to skuId is empty....");
            return CombinationDetail.buildNoCombination();
        }

        // 商品组合
        GoodsCombination goodsCombination = goodsCombinationService.querySkusBySku(skuId);

        //如果商品组合不存在 则直接返回
        if (Objects.isNull(goodsCombination) || CollectionUtils.isEmpty(goodsCombination.getSkuIds())) {
            return CombinationDetail.buildNoCombination();
        }

        // 排除自己  自己不需要返回
        List<String> skuIds = goodsCombination.getSkuIds().stream().filter(s -> !s.equals(skuId)).collect(Collectors.toList());

        logger.debug("begin to get spudetail and skuIds:{}", skuIds);

        return CombinationDetail.buildHasCombination(goodsCombination, skuIds.parallelStream().map(id -> this.queryCombinationSpuDetail(id, customerId)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()));
    }

    @Override
    public List<SpuDetail> querySpuDetailList(String[] skuIds, SpuDetailItem... spuDetailItems) {
        logger.debug("querySpuDetailList and skuIds:{} \r\n ", Arrays.toString(skuIds));
        if (ArrayUtils.isEmpty(skuIds)) {
            logger.error("querySpuDetailList fail :skuIds is empty");
            return Collections.emptyList();
        }
        List<SpuDetail> finalList = new ArrayList<>();
        Arrays.asList(skuIds).forEach(s ->
                finalList.add(this.querySpuDetail(s, CommonConstant.NO_CUSTOMER_ID, spuDetailItems).orElse(null))
        );
        return finalList;
    }

    @Override
    public int updateSpu(PmsGoods spu, Consumer<Long> consumer) {
        if (Objects.isNull(spu)) {
            logger.error("updateSpu fail due to goods is null....");
            return 0;
        }
        if (spu.getSkus().stream().anyMatch(PmsSku::hasBatchPriceAndMemberPrice)) {
            logger.error("updateSpu fail due to exist sku has member price and batch both");
            return -3;
        }
        if (marketingQueryService.queryExclusionMarketingCountBySkuIds(spu.getHasMemberPriceSkuIds(), CommonConstant.MEMBER_PRICE_EXCLUSION) > 0) {
            logger.error("updateSpu fail: member price has exclusion marketing");
            return -1;
        }
        if (marketingQueryService.queryExclusionMarketingCountBySkuIds(spu.getHasBatchSkuIds(), CommonConstant.BATCH_SKU_EXCLUSION) > 0) {
            logger.error("updateSpu fail: batch sku has exclusion marketing");
            return -2;
        }
        return spuService.updateSpu(spu, consumer);
    }

    @Override
    public BigDecimal calculateFreight(String skuId, long storeId, long cityId, int num) {
        logger.debug("calculateFreight and skuId:{} \r\n storeId:{} \r\n cityId:{} \r\n num:{} ", skuId, storeId, cityId, num);

        // 查询单品信息
        PmsSku sku = skuService.querySkuById(skuId);

        // 虚拟商品不计算运费
        if (Objects.isNull(sku) || sku.isVirtualSku()) {
            logger.error("calculateFreight fail : no sku or sku is virtual");
            return new BigDecimal(0L);
        }

        // 查询单品的物流模版
        List<OmsLogisticsTemplate> logisticsTemplates = logisticsTemplateService.queryLogisticsTemplateByCityIdAndId(new HashSet<>(Arrays.asList(sku.getLogisticsTemplateId())), cityId);

        if (CollectionUtils.isEmpty(logisticsTemplates)) {
            logger.error("calculateFreight fail due to no logisticsTemplates");
            return BigDecimal.ZERO;
        }

        return CalcFreightUtil.calcFreightPrice(logisticsTemplates.get(0), num, sku.getWeight());
    }

    @Override
    public Void exportCheckedSpu(OutputStream os, Long[] ids, long storeId) {
        logger.debug("exportCheckedSpu and ids:{} \r\n storeId:{}", ids, storeId);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("exportCheckedOrder fail :ids is empty");
            return null;
        }
        exportSpu(fillExportSpuOtherInfo(spuService.querySpuByIdsForExport(Arrays.asList(ids), storeId)), os);
        return null;
    }

    @Override
    public Void exportAllSpu(OutputStream os, long storeId) {
        logger.debug("exportAllSpu and storeId:{}", storeId);
        exportSpu(fillExportSpuOtherInfo(spuService.queryAllSpuForExport(storeId)), os);
        return null;
    }

    @Override
    public String getWeChatAppletCode(WeChatAppletCodeRequest weChatAppletCodeRequest) {
        logger.debug("getWeChatAppletCode and weChatAppletCodeRequest:{} ", weChatAppletCodeRequest);
        if (Objects.isNull(weChatAppletCodeRequest)) {
            return null;
        }
        ByteArrayInputStream inputStream = null;
        String imageUrl = "";
        try {
            // 获取微信小程序支付设置
            WechatPaySet wechatAppletPaySet = paySetService.queryPaySet().getWechatAppletPaySet();
            WechatSetting wechatSetting = new WechatSetting();
            wechatSetting.setAppId(wechatAppletPaySet.getAppId());
            wechatSetting.setAppSecret(wechatAppletPaySet.getAppSecret());
            // 设置获取微信小程序码接口调用凭证
            String accessToken = wxAppletAccessTokenService.getWxAppletAccessToken(wechatSetting);
            if (StringUtils.isEmpty(accessToken)) {
                logger.error("getWeChatAppletCode fail due to accessToken is null");
                return null;
            }
            // 获取微信小程序分享码请求地址
            String getShareAppletCodeUrl = WeChatAppletUtils.getWeChatAppletShareCodeUrl(accessToken);
            if (StringUtils.isEmpty(getShareAppletCodeUrl)) {
                logger.error("getWeChatAppletCode fail due to getShareAppletCodeUrl is null");
                return null;
            }
            inputStream = WeChatAppletUtils.getJsonRequestResult(getShareAppletCodeUrl, JSON.toJSONString(weChatAppletCodeRequest));

            //这里判断的是返回的图片还是错误信息，一般错误信息不会大于200
            if (inputStream.available() <= 200) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                int i;
                byte[] buffer = new byte[200];
                while ((i = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, i);
                }
                String str = new String(byteArrayOutputStream.toByteArray());
                JSONObject jsonObject = JSONObject.parseObject(str);
                byteArrayOutputStream.close();
                if (!StringUtils.isEmpty(jsonObject.getString("errcode"))) {
                    if (!StringUtils.isEmpty(jsonObject.getString("errmsg"))) {
                        logger.error("getWeChatAppletCode Fail and errmsg:{}", jsonObject.getString("errmsg"));
                    }
                    return null;
                }
            }
            imageUrl = uploadToOssForBase64(WeChatAppletUtils.getBase64FromInputStream(inputStream));
            //  imageUrl = uploadToUpYunForBase64(WeChatAppletUtils.getBase64FromInputStream(inputStream));
            //这里我选择是上传到了oss，你也可以选择输出到本地
//                String fileName = "noilCode_userNo" + ".jpeg";
//                String path = "wxcode/noilCode";
//                String imgUrl = ossClientUtil.UploadImgAndReturnImgUrlInputStream(inputStream, fileName, path);
            //输出到本地的代码
//            FileOutputStream fileOutputStream = new FileOutputStream("D:/123.png");
//            int i;
//            byte[] buffer = new byte[200];
//            while ((i = inputStream.read(buffer)) != -1) {
//                fileOutputStream.write(buffer, 0, i);
//            }
//            fileOutputStream.flush();
//            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return imageUrl;
    }


    /**
     * 上传base64图片
     *
     * @return 返回图片在又拍云的地址
     * @throws Exception
     */
    private String uploadToOssForBase64(String image) throws Exception {
        if (StringUtils.isEmpty(image)) {
            return null;
        }
        // base64转为二进制数组
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] date_blob = decoder.decodeBuffer(image);
        for (int i = 0; i < date_blob.length; ++i) {
            if (date_blob[i] < 0) {
                date_blob[i] += 256;
            }
        }
        return null;
//        return ossService.uploadToQqOssForBase64(Arrays.asList(UploadData.build(null, date_blob, null, "0",null))).stream().findFirst().orElse("");
    }

    /**
     * 查询商品组合的商品详情
     *
     * @param skuId      单品id
     * @param customerId 会员id
     * @return 返回商品详情
     */
    private Optional<SpuDetail> queryCombinationSpuDetail(String skuId, long customerId) {
        logger.debug("queryCombinationSpuDetail and skuId:{} , customerId:{}", skuId, customerId);

        // 查询单品的信息
        PmsSku sku = skuService.querySkuById(skuId);

        // 校验单品
        if (!validateSku(sku)) {
            logger.error("querySpuDetail fail due to sku validate fail...sku:{}", sku);
            return Optional.empty();
        }
        skuService.setSkuDetail(sku, PmsSkuItem.BATCH);
        return Optional.of(SpuDetail.build(Optional.of(sku)).addMarketPrice((this.calcMarketingPrice(sku, sku.getPrice(), customerId))).addSkuImages(Arrays.asList(PmsSkuImage.build(sku))));
    }

    /**
     * 获得用户的会员价格
     *
     * @param skuId      单品id
     * @param customerId 用户id
     * @return 返回用户的会员价格
     */
    private PmsSkuMemberPrice getSkuMemberPrice(String skuId, long customerId) {

        // 查询会员信息
        UmsMember customer = customerService.queryCustomerWithCustomerLevel(customerId);

        if (Objects.isNull(customer)) {
            return null;
        }

        // 查询单品的会员价格
        List<PmsSkuMemberPrice> skuMemberPrices = skuMemberPriceServicce.queryBySkuId(skuId);

        if (CollectionUtils.isEmpty(skuMemberPrices)) {
            return null;
        }

        return skuMemberPrices.stream().filter(skuMemberPrice -> skuMemberPrice.getMemberLevelId() == customer.getCustomerLevelId()).findFirst().orElse(null);
    }


    /**
     * 校验单品信息
     *
     * @param sku 单品信息
     * @return 主要校验 1 单品是否存在 2 单品是否上架状态  3 单品是否审核通过的 4 店铺是否在有效期内
     */
    private boolean validateSku(final PmsSku sku) {
        if (Objects.isNull(sku)) {
            return false;
        }
        //判断店铺是否有效
        if (!storeInfoService.isEffective(sku.getStoreId())) {
            logger.error("validateSku fail : store is not effective");
            return false;
        }
        return sku.validate(false);
    }


    /**
     * 导出商品信息
     *
     * @param spus 商品信息集合
     * @param os   输出流
     */
    private void exportSpu(final List<PmsGoods> spus, final OutputStream os) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("商品信息");
        // 创建excel的基本信息
        createExecleBase(wb, sheet);
        // 创建导出的数据信息
        createExecleData(sheet, spus, 1);
        try {
            // 放入输出流
            wb.write(os);
        } catch (IOException e) {
            logger.error("export goods fail", e);
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
        style.setAlignment(HorizontalAlignment.CENTER);
        List<Integer> widthList = Arrays.asList(6000, 6000, 6000, 6000, 6000, 10000, 6000, 6000, 6000, 6000);
        List<String> cellNameList = Arrays.asList("SPU名称", "SKU名称", "规格", "虚拟商品", "状态", "销售价", "分类", "品牌", "库存", "店铺名称");
        HSSFCell temp;
        for (int i = 0; i < widthList.size(); i++) {
            // 设置宽度
            sheet.setColumnWidth(i, widthList.get(i));
            // 设置列头信息
            temp = row.createCell(i);
            temp.setCellStyle(style);
            temp.setCellValue(cellNameList.get(i));
        }
    }

    /**
     * 创建导入execel的数据
     *
     * @param sheet  excel中的sheet对象
     * @param spus   商品信息集合
     * @param offset 从第几行开始输出数据
     */
    private void createExecleData(final HSSFSheet sheet, final List<PmsGoods> spus, final int offset) {
        if (CollectionUtils.isEmpty(spus)) {
            return;
        }
        final StringBuilder skip = new StringBuilder("0");
        IntStream.range(0, spus.size()).forEach(index -> {
            HSSFRow row = sheet.createRow(offset + index + Integer.parseInt(skip.toString()));
            PmsGoods spu = spus.get(index);
            if (!StringUtils.isEmpty(spu.getName())) {
                row.createCell(0).setCellValue(spu.getName());
            }
            if (CollectionUtils.isEmpty(spu.getSkus())) {
                return;
            }
            IntStream.range(0, spu.getSkus().size()).forEach(index2 -> {
                HSSFRow tempRow = row;
                if (index2 != 0) {
                    tempRow = sheet.createRow(offset + index + Integer.parseInt(skip.toString()) + index2);
                }
                PmsSku sku = spu.getSkus().get(index2);
                if (!StringUtils.isEmpty(sku.getName())) {
                    tempRow.createCell(1).setCellValue(sku.getName());
                }
                if (!CollectionUtils.isEmpty(sku.getSkuSpecValues())) {
                    tempRow.createCell(2).setCellValue(sku.getSpecValuesString());
                }
                if (!StringUtils.isEmpty(sku.getIsVirtual())) {
                    tempRow.createCell(3).setCellValue("1".equals(sku.getIsVirtual()) ? "是" : "否");
                }
                if (!StringUtils.isEmpty(sku.getShelvesStatus())) {
                    tempRow.createCell(4).setCellValue("0".equals(sku.getShelvesStatus()) ? "下架" : "1".equals(sku.getShelvesStatus()) ? "上架" : "违规下架");
                }
                if (!StringUtils.isEmpty(sku.getPrice())) {
                    tempRow.createCell(5).setCellValue(getSkuPriceExportInfo(sku));
                }
                if (Objects.nonNull(spu.getThirdCategory())) {
                    tempRow.createCell(6).setCellValue(spu.getThirdCategory().getName());
                }
                if (Objects.nonNull(spu.getBrand())) {
                    tempRow.createCell(7).setCellValue(spu.getBrand().getName());
                }
                tempRow.createCell(8).setCellValue(sku.getStock());
                if (!StringUtils.isEmpty(spu.getStoreName())) {
                    tempRow.createCell(9).setCellValue(spu.getStoreName());
                } else if (spu.getStoreId() == CommonConstant.ADMIN_STOREID) {
                    tempRow.createCell(9).setCellValue("自营");
                } else {
                    tempRow.createCell(9).setCellValue("");
                }
            });

            int intSkip = Integer.parseInt(skip.toString()) + spu.getSkus().size();
            skip.delete(0, skip.length());
            skip.append(String.valueOf(intSkip));
        });
    }


    /**
     * 填充导出商品的其他信息
     *
     * @param spuList 商品信息
     * @return 商品信息
     */
    private List<PmsGoods> fillExportSpuOtherInfo(List<PmsGoods> spuList) {
        spuList.forEach(spu -> {
            if (spu.getStoreId() != CommonConstant.ADMIN_STOREID) {
                TStoreInfo storeInfo = storeInfoService.queryStoreInfo(spu.getStoreId());
                spu.setStoreName(storeInfo == null ? "" : storeInfo.getStoreName());
            }
            spu.setThirdCategory(categoryService.queryCategoryById(spu.getThirdCateId()));
            spu.setBrand(brandService.queryBrandById(spu.getBrandId(), CommonConstant.QUERY_WITH_NO_STORE));
            spu.setSkus(skuService.querySkuBySpuId(spu.getId(), spu.getStoreId(), PmsSkuItem.SPEC, PmsSkuItem.BATCH));
        });
        return spuList;
    }

    /**
     * 获取单品价格导出信息
     *
     * @param sku 单品信息
     * @return 价格导出信息
     */
    private String getSkuPriceExportInfo(PmsSku sku) {
        StringBuilder price = new StringBuilder();
        if (sku.isBatchSku()) {
            sku.getSkuBatchList().forEach(skuBatch ->
                    price.append(skuBatch.getBatchInterval()).append("件").append(":").append(String.format("%.2f", skuBatch.getBatchPrice())).append(" | ")
            );
        } else {
            price.append(String.format("%.2f", sku.getPrice())).append("|");
        }
        return price.substring(0, price.lastIndexOf("|"));
    }

}
