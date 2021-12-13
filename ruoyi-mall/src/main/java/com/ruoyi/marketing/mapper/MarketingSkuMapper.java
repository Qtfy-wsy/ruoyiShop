package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.MarketingSku;

import java.util.List;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 17/6/8.
 * 促销单品数据库
 */
public interface MarketingSkuMapper {

    /**
     * 新增促销单品信息
     *
     * @param marketingSkus 促销单品
     */

    void addMarketingSkus(List<MarketingSku> marketingSkus);

    /**
     * 根据促销id查询促销单品
     *
     * @param marketingId 促销id
     * @return 返回促销关联的促销单品
     */

    List<MarketingSku> queryMarketingSkusByMarketingId(long marketingId);

    /**
     * 根据促销id删除单品促销
     *
     * @param marketingId 促销id
     */

    void deleteByMarketingId(long marketingId);

    /**
     * 根据单品id和促销id查询促销的单品信息
     *
     * @param params 参数
     * @return 返回促销单品信息
     */

    List<MarketingSku> queryCrowdFundingSkuByIdAndSkuId(Map<String, Object> params);
}
