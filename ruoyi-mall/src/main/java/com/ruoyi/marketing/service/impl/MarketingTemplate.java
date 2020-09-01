package com.ruoyi.marketing.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.goods.service.IPmsSkuMemberPriceService;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.mapper.MarketingMapper;
import com.ruoyi.marketing.service.GoodsCombinationService;
import com.ruoyi.marketing.service.MarketingQueryService;
import com.ruoyi.marketing.service.MarketingService;
import com.ruoyi.marketing.service.MarketingSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * Created by 魔金商城 on 17/6/7.
 * 促销模版
 */
public abstract class MarketingTemplate implements MarketingService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(MarketingTemplate.class);

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
     * 注入促销查询服务接口
     */
    @Autowired
    private MarketingQueryService marketingQueryService;


    @Transactional
    @Override
    public int addMarketing(Marketing marketing) {

        logger.debug("addMarketing and marketing:{}", marketing);

        if (Objects.isNull(marketing)) {
            logger.error("addMarketing fail due to marketing is null....");
            return 0;
        }
        //查询交叉时间内含有相同单品的互斥促销数量
        queryExclusionMarketingCount(marketing);

        // 新增促销主表
        marketingMapper.addMarketing(marketing);

        // 设置促销id
        marketing.setLinkedMarketingId();

        // 新增促销单品
        marketingSkuService.addMarketingSkus(marketing.getMarketingSkus());

        // 设置具体的促销详情
        return addMarketingDetail(marketing);
    }

    @Transactional
    @Override
    public int updateMarketing(Marketing marketing) {

        logger.debug("updateMarketing and marketing:{}", marketing);

        if (Objects.isNull(marketing)) {
            logger.error("updateMarketing fail due to marketing is null....");
            return 0;
        }
        //查询交叉时间内含有相同单品的互斥促销数量
        queryExclusionMarketingCount(marketing);

        // 查询修改之前的原始促销信息
        Marketing oldMarketing = marketingQueryService.querySimpleMarketingById(marketing.getId(), marketing.getStoreId());

        // 更新促销主表
        if (marketingMapper.updateMarketing(marketing) != 1) {
            logger.error("updateMarketing fail...");
            return 0;
        }

        // 设置促销id
        marketing.setLinkedMarketingId();

        // 更新促销和单品的关系表
        marketingSkuService.updateMarketingSkus(marketing.getMarketingSkus(), marketing.getId());

        return updateMarketingDetail(marketing, oldMarketing);
    }

    /**
     * 添加促销详情  由具体的促销来实现
     *
     * @param marketing 促销信息
     * @return 成功返回1 失败返回0
     */
    public abstract int addMarketingDetail(Marketing marketing);

    /**
     * 更新促销详情  由具体的促销来实现
     *
     * @param marketing    促销信息
     * @param oldMarketing 修改之前的原始促销信息
     * @return 成功返回1 失败返回0
     */
    public abstract int updateMarketingDetail(Marketing marketing, Marketing oldMarketing);

    /**
     * 查询交叉时间内含有相同单品的互斥促销数量
     *
     * @param marketing 促销实体
     */
    private void queryExclusionMarketingCount(Marketing marketing) throws ServiceException {
        //查询交叉时间内含有相同单品的互斥促销数量
        if (marketing.isExclusionMarketing()) {
            logger.info("queryExclusionMarketingCount......");
            //如果是众筹促销，则先查询含有相同商品的众筹促销数量
            if (marketing.isCrowdFundingMarketingType()) {
                if (marketingMapper.querySpuExclusionMarketingCount(marketing.getQueryExclusionMap()) > 0) {
                    logger.error("queryExclusionMarketingCount : querySpuExclusionMarketingCount over 0");
                    throw new ServiceException("queryExclusionMarketingCount : querySpuExclusionMarketingCount over 0");
                }
            }
            //预售与商品组合互斥
            if (marketing.isDepositPreSaleMarketing() || marketing.isFullPreSaleMarkting()) {
                if (goodsCombinationService.queryGoodsCombinationCountBySkuIds(marketing.getMarketingSkuIds()) > 0) {
                    logger.error("queryExclusionMarketingCount : GoodsCombination over 0");
                    throw new ServiceException("queryExclusionMarketingCount : GoodsCombination over 0");
                }
            }
            //会员价与促销互斥
            if (skuMemberPriceServicce.querySkuMemberPriceCountBySkuIds(marketing.getMarketingSkuIds()) > 0) {
                logger.error("queryExclusionMarketingCount : memberPrice over 0");
                throw new ServiceException("queryExclusionMarketingCount : memberPrice over 0");
            }
            if (marketingMapper.queryExclusionMarketingCount(marketing.getQueryExclusionMap()) > 0) {
                logger.error("queryExclusionMarketingCount : marketing over 0");
                throw new ServiceException("queryExclusionMarketingCount : marketing over 0");
            }
        }
    }
}
