package com.ruoyi.marketing.service.impl;


import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.marketing.domain.*;
import com.ruoyi.marketing.service.*;
import com.ruoyi.marketing.vo.SeckillSku;
import com.ruoyi.order.service.CustomerPanicRecordService;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.order.service.IOmsShoppingCartService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 促销混合api实现
 */
@Service
public class MarketingServiceApiImpl implements MarketingServiceApi {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(MarketingServiceApiImpl.class);

    /**
     * 注入购物车服务
     */
    @Autowired
    private IOmsShoppingCartService shoppingCartService;

    /**
     * 注入促销查询服务
     */
    @Autowired
    private MarketingQueryService marketingQueryService;


    /**
     * 注入抢购服务接口
     */
    @Resource(name = "panicBuyService")
    private MarketingService panicBuyService;

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
     * 注入订单服务
     */
    @Autowired
    private IOmsOrderService orderService;

    /**
     * 注入秒杀场次折扣服务接口
     */
    @Autowired
    private SeckillScenePanicbuyService seckillScenePanicbuyService;

    /**
     * 注入抢购服务接口
     */
    @Autowired
    private PanicBuyService iPanicBuyService;

    /**
     * 注入单品服务接口
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入会员抢购纪录服务接口
     */
    @Autowired
    private CustomerPanicRecordService customerPanicRecordService;

    @Transactional
    @Override
    public int updatePanic(Marketing marketing) {
        logger.debug("updatePanci and marketing:{}", marketing);

        if (Objects.isNull(marketing)) {
            logger.error("updatePanci fail due to markeing is null..");
            return 0;
        }


        if (CollectionUtils.isEmpty(marketing.getPanicBuyList())) {
            logger.error("updatePanci fail due to panicBuyList is empty");
            return -2;
        }

        return panicBuyService.updateMarketing(marketing);
    }

    @Override
    public int addDepositPreSale(Marketing marketing) {
        logger.debug("addDepositPreSale and marketing:{}", marketing);
        depositPreSaleService.addMarketing(marketing);
        //如果促销有效，则根据skuId删除购物车表
        if (marketing.getEndTime().isAfter(LocalDateTime.now())) {
            shoppingCartService.deleteShoppingCartBySkuId(marketing.getMarketingSkus().stream().findFirst().orElse(new MarketingSku()).getSkuId());
        }

        return 1;
    }

    @Override
    public int updateDepositPreSale(Marketing marketing) {
        logger.debug("updateDepositPreSale and marketing:{}", marketing);
        depositPreSaleService.updateMarketing(marketing);
        //如果促销有效，则根据skuId删除购物车表
        if (marketing.getEndTime().isAfter(LocalDateTime.now())) {
            shoppingCartService.deleteShoppingCartBySkuId(marketing.getMarketingSkus().stream().findFirst().orElse(new MarketingSku()).getSkuId());
        }
        return 1;
    }

    @Override
    public int addFullPreSale(Marketing marketing) {
        logger.debug("addFullPreSale and marketing:{}", marketing);
        fullPreSaleService.addMarketing(marketing);
        //如果促销有效，则根据skuId删除购物车表
        if (marketing.getEndTime().isAfter(LocalDateTime.now())) {
            shoppingCartService.deleteShoppingCartBySkuId(marketing.getMarketingSkus().stream().findFirst().orElse(new MarketingSku()).getSkuId());
        }
        return 1;
    }

    @Override
    public int updateFullPreSale(Marketing marketing) {
        logger.debug("updateFullPreSale and marketing:{}", marketing);
        fullPreSaleService.updateMarketing(marketing);
        //如果促销有效，则根据skuId删除购物车表
        if (marketing.getEndTime().isAfter(LocalDateTime.now())) {
            shoppingCartService.deleteShoppingCartBySkuId(marketing.getMarketingSkus().stream().findFirst().orElse(new MarketingSku()).getSkuId());
        }
        return 1;
    }

    @Override
    public PageHelper<Marketing> queryCrowdFundingMarketings(PageHelper<Marketing> pageHelper, CrowdFunding.QueryCriteria queryCriteria) {
        logger.debug("queryCrowdFundingMarketings and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        return pageHelper.setListDates(marketingQueryService.queryCrowdFundingMarketings(pageHelper, queryCriteria).getList()
                .stream().map(marketing -> marketing.buildSupportNum(orderService.queryCrowdFundingOrderCount(marketing.getId(), marketing.getStoreId()))).collect(Collectors.toList()));
    }

    @Override
    public PageHelper<Marketing> queryStoreCrowdFundingMarketings(PageHelper<Marketing> pageHelper, CrowdFunding.QueryCriteria queryCriteria) {
        logger.debug("queryStoreCrowdFundingMarketings and pageHelper:{} \r\n queryCriteria:{}", pageHelper, queryCriteria);
        return pageHelper.setListDates(marketingQueryService.queryStoreCrowdFundingMarketings(pageHelper, queryCriteria).getList()
                .stream().map(marketing -> marketing.buildSupportNum(orderService.queryCrowdFundingOrderCount(marketing.getId(), marketing.getStoreId()))).collect(Collectors.toList()));
    }

    @Override
    public PageHelper<SeckillSku> querySeckillScenePanicbuyForPlatform(PageHelper<SeckillSku> pageHelper, String seckillTime) {
        logger.debug("querySeckillScenePanicbuyForPlatform and pageHelper:{} \r\n seckillTime:{}", pageHelper, seckillTime);

        // 秒杀场次折扣
        PageHelper seckillScenePanicbuys = seckillScenePanicbuyService.querySeckillScenePanicbuyForPlatform(pageHelper, seckillTime);

        if (CollectionUtils.isEmpty(seckillScenePanicbuys.getList())) {
            return seckillScenePanicbuys;
        }
        return pageHelper.setListDates((List) seckillScenePanicbuys.getList().stream().map(seckillScenePanicbuy -> this.getSeckillSku((SeckillScenePanicbuy) seckillScenePanicbuy)).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    @Override
    public PageHelper<PanicBuySku> queryStorePanicBuyListForSite(PageHelper<PanicBuySku> pageHelper, long storeId) {
        logger.debug("queryStorePanicBuyListForSite and pageHelper:{} \r\n storeId:{}", pageHelper, storeId);

        // 店铺抢购列表
        PageHelper panicBuyList = iPanicBuyService.queryStorePanicBuyListForSite(pageHelper, storeId);

        if (CollectionUtils.isEmpty(panicBuyList.getList())) {
            return panicBuyList;
        }
        return pageHelper.setListDates((List) panicBuyList.getList()
                .stream().map(panicBuy -> this.getPanicBuySku((PanicBuy) panicBuy)).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    /**
     * 根据秒杀场次折扣获得秒杀商品
     *
     * @param seckillScenePanicbuy 秒杀上次折扣
     * @return 返回秒杀商品
     */
    private SeckillSku getSeckillSku(SeckillScenePanicbuy seckillScenePanicbuy) {
        logger.debug("getSeckillSku and seckillScenePanicbuy:{}", seckillScenePanicbuy);
        if (Objects.isNull(seckillScenePanicbuy)) {
            logger.error("getSeckillSku fail due to seckillScenePanicbuy is empty..");
            return null;
        }

        // 查询抢购信息
        PanicBuy panicBuy = iPanicBuyService.queryById(seckillScenePanicbuy.getPanicbuyId());

        if (Objects.isNull(panicBuy)) {
            logger.error("getSeckillSku fail due to getSeckillSku is null...");
            return null;
        }

        // 抢购的单品
        PmsSku sku = skuService.querySkuById(panicBuy.getSkuId());

        if (Objects.isNull(sku)) {
            logger.error("getSeckillSku fail due to sku is null...");
            return null;
        }


        return SeckillSku.builder().skuId(sku.getId()).name(sku.getName()).subTitle(sku.getSubtitle()).image(sku.getUrl()).oldPrice(sku.getPrice()).
                price(sku.getPrice().multiply(panicBuy.getDiscount())).saleNum(customerPanicRecordService.queryPanicUsedCount(seckillScenePanicbuy.getPanicbuyId())).build();
    }

    /**
     * 根据抢购活动获得抢购活动单品
     *
     * @param panicBuy 抢购活动
     * @return 返回抢购活动单品
     */
    private PanicBuySku getPanicBuySku(PanicBuy panicBuy) {
        logger.debug("getPanicBuySku and panicBuy:{}", panicBuy);

        if (Objects.isNull(panicBuy)) {
            logger.error("getPanicBuySku fail due to panicBuy is empty..");
            return null;
        }

        // 抢购的单品
        PmsSku sku = skuService.querySkuById(panicBuy.getSkuId());

        if (Objects.isNull(sku)) {
            logger.error("getPanicBuySku fail due to sku is null...");
            return null;
        }

        return PanicBuySku.buildPanicBuySku(panicBuy, sku, customerPanicRecordService.queryPanicUsedCount(panicBuy.getId()));
    }


}
