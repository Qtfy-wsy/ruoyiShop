package com.ruoyi.order.service;


import com.ruoyi.order.vo.RecommendSku;

import java.util.List;

/**
 * 推荐单品服务接口
 */
public interface RecommendSkuService {

    /**
     * 查找推荐单品
     *
     * @param num 数量
     */
    List<RecommendSku> queryRecommendSkus(int num);

    /**
     * 查询店铺30天内热销商品
     *
     * @param storeId 店铺id
     * @return 热销商品
     */
    List<RecommendSku> queryRecommentSkusThirtyDays(long storeId);

    /**
     * 查询门店30天内热销商品
     *
     * @param storeId 店铺id
     * @return 热销商品
     */
    List<RecommendSku> queryStoreRecommentSkusThirtyDays(long storeId);

}
