package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.MarketingSku;

import java.util.List;

/**
 * Created by 魔金商城 on 17/6/8.
 * 促销单品服务接口
 */
public interface MarketingSkuService {

    /**
     * 新增促销单品
     *
     * @param marketingSkus 促销单品
     */
    void addMarketingSkus(List<MarketingSku> marketingSkus);

    /**
     * 根据促销id查询促销单品
     *
     * @param marketingId 促销id
     * @param storeId     店铺id
     * @return 返回促销关联的促销单品
     */
    List<MarketingSku> queryMarketingSkusByMarketingId(long marketingId, long storeId);

    /**
     * 更新促销单品
     *
     * @param marketingSkus 促销单品
     * @param marketingId   促销id
     */
    void updateMarketingSkus(List<MarketingSku> marketingSkus, long marketingId);

    /**
     * 根据众筹id和单品id查询
     *
     * @param marketingId 众筹id
     * @param skuId       单品id
     * @return 返回众筹的单品信息
     */
    List<MarketingSku> queryCrowdFundingSkuByIdAndSkuId(long marketingId, String skuId);
}
