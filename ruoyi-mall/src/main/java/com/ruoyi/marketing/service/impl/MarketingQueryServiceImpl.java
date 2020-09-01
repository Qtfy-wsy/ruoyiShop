package com.ruoyi.marketing.service.impl;

import com.ruoyi.goods.service.IPmsSkuBatchService;
import com.ruoyi.goods.service.IPmsSkuImageService;
import com.ruoyi.goods.service.IPmsSkuMemberPriceService;
import com.ruoyi.marketing.domain.*;
import com.ruoyi.marketing.mapper.MarketingMapper;
import com.ruoyi.marketing.service.*;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.MarketingConstant;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by 魔金商城 on 17/6/8.
 * 促销查询实现接口
 */
@Service
public class MarketingQueryServiceImpl implements MarketingQueryService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(MarketingQueryServiceImpl.class);

    /**
     * 促销数据库接口
     */
    @Autowired
    private MarketingMapper marketingMapper;

    /**
     * 注入促销单品服务接口
     */
    @Autowired
    private MarketingSkuService marketingSkuService;

    /**
     * 注入抢购服务接口
     */
    @Resource(name = "panicBuyService")
    private MarketingService panicBuyService;

    /**
     * 注入直降服务接口
     */
    @Resource(name = "fallService")
    private MarketingService fallService;

    /**
     * 注入满减促销服务接口
     */
    @Resource(name = "fullDownService")
    private MarketingService fullDownService;

    /**
     * 注入满折促销服务接口
     */
    @Resource(name = "fullDiscountService")
    private MarketingService fullDiscountService;


    /**
     * 注入满赠服务接口
     */
    @Resource(name = "fullGiftService")
    private MarketingService fullGiftService;

    /**
     * 注入定金预售服务
     */
    @Resource(name = "depositPreSaleService")
    private MarketingService depositPreSaleService;

    /**
     * 注入全款预售服务
     */
    @Resource(name = "fullPreSaleService")
    private MarketingService fullPreSaleService;

    /**
     * 注入试用促销服务
     */
    @Resource(name = "tryService")
    private MarketingService tryService;

    /**
     * 注入拼团促销服务
     */
    @Resource(name = "groupMarketingService")
    private MarketingService groupMarketingService;

    /**
     * 注入众筹促销服务
     */
    @Resource(name = "crowdFundingService")
    private MarketingService crowdFundingService;

    /**
     * 注入单品图片服务接口
     */
    @Autowired
    private IPmsSkuImageService skuImageService;

    /**
     * 注入单品会员价服务
     */
    @Autowired
    private IPmsSkuMemberPriceService skuMemberPriceServicce;

    /**
     * 注入商品组合服务
     */
    @Autowired
    private GoodsCombinationService goodsCombinationService;

    /**
     * 注入促销设置服务
     */
    @Autowired
    private MarketingSettingService marketingSettingService;

    /**
     * 注入单品批发规则服务
     */
    @Autowired
    private IPmsSkuBatchService skuBatchService;

    /**
     * 注入抢购服务接口
     */
    @Autowired
    private PanicBuyService iPanicBuyService;


    @Override
    public PageHelper<Marketing> queryMarketings(PageHelper<Marketing> pageHelper, String name, String type, long storeId, String status) {
        logger.debug("queryMarketings and pageHelper:{} \r\n name:{} \r\n type:{} \r\n storeId:{}", pageHelper, name, type, storeId);

        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        params.put("type", type);
        params.put("storeId", storeId);
        params.put("status", status);
        return pageHelper.setListDates(marketingMapper.queryMarketings(pageHelper.getQueryParams(params, marketingMapper.queryMarketingCount(params))));
    }

    @Override
    public PageHelper<Marketing> queryCrowdFundingMarketings(PageHelper<Marketing> pageHelper, CrowdFunding.QueryCriteria queryCriteria) {
        logger.debug("queryCrowdFundingMarketings and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        return pageHelper.setListDates(marketingMapper.queryCrowdFundingMarketings(pageHelper.getQueryParams(queryCriteria.getQueryMap(), marketingMapper.queryCrowdFundingMarketingCount(queryCriteria.getQueryMap()))));
    }

    @Override
    public List<Marketing> queryEndCrowdFundingMarketingList() {
        logger.debug("queryEndCrowdFundingMarketingList......");
        List<Marketing> marketingList = marketingMapper.queryEndCrowdFundingMarketingList();
        //促销详细信息
        marketingList.forEach(this::setMarketingDetail);
        return marketingList;
    }

    @Override
    public PageHelper<Marketing> queryStoreCrowdFundingMarketings(PageHelper<Marketing> pageHelper, CrowdFunding.QueryCriteria queryCriteria) {
        logger.debug("queryStoreCrowdFundingMarketings and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        return pageHelper.setListDates(marketingMapper.queryStoreCrowdFundingMarketings(pageHelper.getQueryParams(queryCriteria.getQueryMap(), marketingMapper.queryStoreCrowdFundingMarketingCount(queryCriteria.getQueryMap()))));
    }

    @Override
    public PageHelper<Marketing> queryPanicBuy(PageHelper<Marketing> pageHelper, int cateId) {
        logger.debug("queryPanicBuy and pageHelper:{}", pageHelper);
        Map<String, Object> params = new HashMap<>();
        params.put("type", CommonConstant.PANIC_BUY);
        params.put("storeId", CommonConstant.QUERY_WITH_NO_STORE);
        params.put("status", CommonConstant.NOT_PAST);
        params.put("cateId", cateId);
        List<Marketing> marketings = marketingMapper.queryMarketings(pageHelper.getQueryParams(params, marketingMapper.queryMarketingCount(params)));
        //促销详细信息
        marketings.stream().forEach(this::setMarketingDetail);
        //促销单品信息
        marketings.stream().forEach(marketing -> marketing.setMarketingSkus(marketingSkuService.queryMarketingSkusByMarketingId(marketing.getId(), CommonConstant.QUERY_WITH_NO_STORE)));
        return pageHelper.setListDates(marketings);
    }

    @Override
    public Marketing queryMarketingById(long id, long storeId) {

        logger.debug("queryMarketingById and id :{} , storeId:{}", id, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);


        // 促销基本信息
        Marketing marketing = marketingMapper.queryMarketingById(params);

        if (Objects.isNull(marketing)) {
            return marketing;
        }

        // 促销单品信息
        marketing.setMarketingSkus(marketingSkuService.queryMarketingSkusByMarketingId(id, storeId));

        // 设置促销的详细信息
        setMarketingDetail(marketing);

        return marketing;
    }

    @Override
    public Marketing querySimpleMarketingById(long id, long storeId) {
        logger.debug("querySimpleMarketingById and id :{} \r\n storeId :{}", id, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        return marketingMapper.queryMarketingById(params);
    }


    @Override
    public Marketing queryMarketingForShoppingCart(String skuId) {
        logger.debug("queryMarketingForShoppingCart and skuId:{}", skuId);
        return marketingMapper.queryMarketingForShoppingCart(skuId);
    }

    @Override
    public List<Marketing> queryMarketingsBySkuId(String skuId, boolean isNeedDetail, MarketingItem... marketingItems) {
        logger.debug("queryMarketingsBySkuId and skuId:{} \r\n  isNeedDetail:{} \r\n marketingItems:{}", skuId, isNeedDetail, marketingItems);

        if (ArrayUtils.isEmpty(marketingItems)) {
            logger.error("queryMarketingsBySkuId fail due to marketingItems is empty....");
            return Collections.emptyList();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        params.put("types", getQueryMarketingTypes(marketingItems));

        // 查询促销信息
        List<Marketing> marketings = marketingMapper.queryMarketingsBySkuId(params);

        // 设置促销详情
        if (!CollectionUtils.isEmpty(marketings) && isNeedDetail) {
            marketings.stream().forEach(this::setMarketingDetail);
        }

        return marketings;
    }

    @Override
    public List<Marketing> queryTimeOutAndUnAuditTryMarketings() {
        logger.debug("queryTimeOutAndUnAuditTryMarketings......");
        List<Marketing> marketings = marketingMapper.queryTimeOutAndUnAuditTryMarketings();
        // 设置促销详情
        if (!CollectionUtils.isEmpty(marketings)) {
            marketings.stream().forEach(this::setMarketingDetail);
        }
        return marketings;
    }


    /**
     * 获得查询促销的条件
     *
     * @param marketingItems 查询条件
     * @return 返回查询促销的条件
     */
    private List<String> getQueryMarketingTypes(MarketingItem... marketingItems) {
        // 查询条件
        List<String> types = new ArrayList<>();

        // 直降促销
        if (ArrayUtils.contains(marketingItems, MarketingItem.FALL_MARKETING)) {
            types.add(MarketingConstant.FALL_MARKETING);
        }

        // 满赠促销
        if (ArrayUtils.contains(marketingItems, MarketingItem.FULL_GIFT_MARKETING)) {
            types.add(MarketingConstant.FULL_GIFT_MARKETING);
        }

        // 抢购促销
        if (ArrayUtils.contains(marketingItems, MarketingItem.PANIC_BUY_MARKETING)) {
            types.add(MarketingConstant.PANIC_BUY_MARKETING);
        }

        // 满减促销
        if (ArrayUtils.contains(marketingItems, MarketingItem.FULL_DOWN_MARKETING)) {
            types.add(MarketingConstant.FULL_DOWN_MARKETING);
        }

        // 满折促销
        if (ArrayUtils.contains(marketingItems, MarketingItem.FULL_DISCOUNT_MARKETING)) {
            types.add(MarketingConstant.FULL_DISCOUNT_MARKETING);
        }

        // 包邮促销
        if (ArrayUtils.contains(marketingItems, MarketingItem.FREE_SHIP_MARKETING)) {
            types.add(MarketingConstant.FREE_SHIP_MARKETING);
        }

        // 定金预售
        if (ArrayUtils.contains(marketingItems, MarketingItem.DEPOSIT_PRE_SALE_MARKETING)) {
            types.add(MarketingConstant.DEPOSIT_PRE_SALE_MARKETING);
        }

        // 全款预售
        if (ArrayUtils.contains(marketingItems, MarketingItem.FULL_PRE_SALE_MARKETING)) {
            types.add(MarketingConstant.FULL_PRE_SALE_MARKETING);
        }

        // 试用促销
        if (ArrayUtils.contains(marketingItems, MarketingItem.TRY_MARKETING)) {
            types.add(MarketingConstant.TRY_MARKETING);
        }

        // 拼团促销
        if (ArrayUtils.contains(marketingItems, MarketingItem.GROUP_MARKETING)) {
            types.add(MarketingConstant.GROUP_MARKETING);
        }

        if (ArrayUtils.contains(marketingItems, MarketingItem.CROWD_FUNDING_MARKETING)) {
            types.add(MarketingConstant.CROWD_FUNDING_MARKETING);
        }
        return types;
    }


    @Override
    public Marketing queryPanicBuyMaketingBySkuId(String skuId) {
        logger.debug("queryPanicBuyMaketingBySkuId and skuId", skuId);
        Marketing marketing = marketingMapper.queryPanicBuyMaketingBySkuId(skuId);
        if (ObjectUtils.isEmpty(marketing)) {
            logger.info("queryPanicBuyMaketingBySkuId fail : marketing is nul");
        } else {
            setMarketingDetail(marketing);
        }
        return marketing;
    }


    /**
     * 预售，拼团（前端用）
     */
    @Override
    public PageHelper<Marketing> queryMarketingsForSite(PageHelper<Marketing> pageHelper, List<Integer> types, int cateId) {
        logger.debug("queryMarketingsForSite and pageHelper:{} \r\n types:{} \r\n cateId:{}", pageHelper, types, cateId);
        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("types", types);

        // 促销信息
        List<Marketing> marketings = marketingMapper.queryMarketingsForSite(pageHelper.getQueryParams(params, marketingMapper.queryMarketingsForSiteCount(params)));

        // 如果促销为空 则直接返回
        if (CollectionUtils.isEmpty(marketings)) {
            return pageHelper;
        }

        marketings.stream().forEach(marketing -> {
            // 设置促销的详情
            this.setMarketingDetail(marketing);

            // 设置促销的单品图片
            marketing.getSku().setSkuImages(skuImageService.queryBySkuId(marketing.getSku().getId()));
        });

        return pageHelper.setListDates(marketings);
    }

    @Override
    public PageHelper<Marketing> queryCrowdFundingMarketingsForSite(PageHelper<Marketing> pageHelper, int cateId) {
        logger.debug("queryCrowdFundingMarketingsForSite and pageHelper:{}  \r\n cateId:{}", pageHelper, cateId);
        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("type", MarketingConstant.CROWD_FUNDING_MARKETING);

        // 促销信息
        List<Marketing> marketings = marketingMapper.queryCrowdFundingMarketingsForSite(pageHelper.getQueryParams(params, marketingMapper.queryCrowdFundingMarketingsForSiteCount(params)));

        // 如果促销为空 则直接返回
        if (CollectionUtils.isEmpty(marketings)) {
            return pageHelper;
        }

        marketings.stream().forEach(marketing -> {
            // 设置促销的详情
            this.setMarketingDetail(marketing);
        });

        return pageHelper.setListDates(marketings);
    }

    @Override
    public PageHelper<Marketing> queryTryMarketingsForSite(PageHelper<Marketing> pageHelper, int cateId) {
        logger.debug("queryTryMarketingsForSite and pageHelper:{}  \r\n cateId:{}", pageHelper, cateId);
        Map<String, Object> params = new HashMap<>();
        params.put("cateId", cateId);
        params.put("type", MarketingConstant.TRY_MARKETING);

        // 促销信息
        List<Marketing> marketings = marketingMapper.queryTryMarketingsForSite(pageHelper.getQueryParams(params, marketingMapper.queryTryMarketingsForSiteCount(params)));

        // 如果促销为空 则直接返回
        if (CollectionUtils.isEmpty(marketings)) {
            return pageHelper;
        }

        marketings.stream().forEach(marketing -> {
            // 设置促销的详情
            this.setMarketingDetail(marketing);

            // 设置促销的单品信息
            marketing.setSku(marketing.getTryMarkting().getNormalSku());
        });

        return pageHelper.setListDates(marketings);
    }

    @Override
    public int queryExclusionMarketingCount(Marketing marketing) {
        logger.debug("queryExclusionMarketingCount and marketing:{}", marketing);
        if (Objects.isNull(marketing)) {
            logger.error("queryExclusionMarketingCount fail :marketing is null");
            return -1;
        }
        marketing.setMarketingType();
        //如果是众筹促销，则先查询含有相同商品的众筹促销数量
        if (marketing.isCrowdFundingMarketingType()) {
            if (marketingMapper.querySpuExclusionMarketingCount(marketing.getQueryExclusionMap()) > 0) {
                logger.error("queryExclusionMarketingCount : querySpuExclusionMarketingCount over 0");
                return -4;
            }
        }
        //查询交叉时间内含有相同单品的互斥促销数量
        if (marketing.isExclusionMarketing()) {
            logger.info("queryExclusionMarketingCount......");
            //预售与商品组合互斥
            if (marketing.isDepositPreSaleMarketing() || marketing.isFullPreSaleMarkting()) {
                if (goodsCombinationService.queryGoodsCombinationCountBySkuIds(marketing.getMarketingSkuIds()) > 0) {
                    logger.error("queryExclusionMarketingCount : GoodsCombination over 0");
                    return -2;
                }
            }
            //会员价与促销互斥
            if (skuMemberPriceServicce.querySkuMemberPriceCountBySkuIds(marketing.getMarketingSkuIds()) > 0) {
                logger.error("queryExclusionMarketingCount : memberPrice sku over 0");
                return -3;
            }
            //如果不是直降，判断是否有批发单品
            if (!marketing.isFallMarketing() && skuBatchService.querySkuBatchCountBySkuIds(marketing.getMarketingSkuIds()) > 0) {
                logger.error("queryExclusionMarketingCount : batch sku over 0");
                return -5;
            }
            return marketingMapper.queryExclusionMarketingCount(marketing.getQueryExclusionMap());
        }
        return 0;
    }

    @Override
    public int queryExclusionMarketingCountBySkuIds(List<String> skuIds, Long from) {
        logger.debug("queryExclusionMarketingCount and skuIds:{}", skuIds);
        Map<String, Object> params = new HashMap<>();
        if (!CollectionUtils.isEmpty(skuIds)) {
            params.put("skuIds", skuIds);
        }
        if (ObjectUtils.isEmpty(from) || CommonConstant.MEMBER_PRICE_EXCLUSION == from) {
            params.put("types", MarketingConstant.EXCLUSION_MARKETING_TYPES);
        }
        if (CommonConstant.COMBINATION_EXCLUSION == from) {
            params.put("types", Arrays.asList(MarketingConstant.FULL_PRE_SALE_MARKETING, MarketingConstant.DEPOSIT_PRE_SALE_MARKETING));
        }
        if (CommonConstant.BATCH_SKU_EXCLUSION == from) {
            params.put("types", MarketingConstant.BATCH_EXCLUSION_MARKETING_TYPES);
        }
        return marketingMapper.queryExclusionMarketingCount(params);
    }

    @Override
    public Marketing queryCrowdFundingByIdAndSkuId(long crowdFundingId, String skuId) {

        // 查询众筹促销
        List<Marketing> marketings = this.queryMarketingsBySkuId(skuId, true, MarketingItem.CROWD_FUNDING_MARKETING);

        // 如果没有众筹促销 直接返回
        if (CollectionUtils.isEmpty(marketings)) {
            logger.error("has no CrowdFunding and crowdFundingId:{}", crowdFundingId);
            return null;
        }

        // 找出对应的众筹促销
        Optional<Marketing> marketingOptional = marketings.stream().filter(marketing -> marketing.getId() == crowdFundingId).findFirst();

        // 如果众筹不存在则直接返回
        if (!marketingOptional.isPresent()) {
            logger.error("has no CrowdFunding......");
            return null;
        }

        // 设置众筹的单品信息
        marketingOptional.get().setMarketingSkus(marketingSkuService.queryCrowdFundingSkuByIdAndSkuId(crowdFundingId, skuId));

        return marketingOptional.get();
    }

    @Override
    public PageHelper<PanicBuy> queryStorePanicBuyList(PageHelper<PanicBuy> pageHelper, long storeId) {
        logger.debug("queryStorePanicBuyList and pageHelper :{} \r\n storeId :{}", pageHelper, storeId);
        return iPanicBuyService.queryStorePanicBuyList(pageHelper, storeId);
    }

    /**
     * 设置促销详细信息
     *
     * @param marketing 促销信息
     */
    private void setMarketingDetail(Marketing marketing) {
        // 设置促销的详细信息
        switch (marketing.getType()) {
            case MarketingConstant.FALL_MARKETING:
                // 直降
                fallService.setMarketingDetail(marketing);
                break;
            case MarketingConstant.PANIC_BUY_MARKETING:
                // 抢购促销
                panicBuyService.setMarketingDetail(marketing);
                break;
            case MarketingConstant.FULL_DOWN_MARKETING:
                // 满减
                fullDownService.setMarketingDetail(marketing);
                break;
            case MarketingConstant.FULL_DISCOUNT_MARKETING:
                // 满折
                fullDiscountService.setMarketingDetail(marketing);
                break;
            case MarketingConstant.FULL_GIFT_MARKETING:
                // 满赠
                fullGiftService.setMarketingDetail(marketing);
                break;
            case MarketingConstant.DEPOSIT_PRE_SALE_MARKETING:
                // 定金预售
                depositPreSaleService.setMarketingDetail(marketing);
                break;
            case MarketingConstant.FULL_PRE_SALE_MARKETING:
                // 全款预售
                fullPreSaleService.setMarketingDetail(marketing);
                break;
            case MarketingConstant.TRY_MARKETING:
                // 试用促销
                tryService.setMarketingDetail(marketing);
                //设置营销规则
                MarketingSetting marketingSetting = marketingSettingService.queryMarketingSetting();
                //将试用商品信心转化为实体
                if (Objects.nonNull(marketing.getTryMarkting())) {
                    marketing.getTryMarkting().convertJsonToObject(Objects.nonNull(marketingSetting) ? marketingSetting.getTryRule() : "");
                }

                break;
            case MarketingConstant.GROUP_MARKETING:
                //拼团促销
                groupMarketingService.setMarketingDetail(marketing);
                break;
            case MarketingConstant.CROWD_FUNDING_MARKETING:
                //众筹促销
                crowdFundingService.setMarketingDetail(marketing);
                break;
            default:
                logger.error("no marketing detail....");
        }
    }


}
