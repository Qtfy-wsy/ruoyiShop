package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.Marketing;

import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 17/6/7.
 * 促销数据库接口
 */
public interface MarketingMapper {

    /**
     * 查询促销的总记录数
     *
     * @param params 查询参数
     * @return 返回促销总记录数
     */

    int queryMarketingCount(Map<String, Object> params);


    /**
     * 分页查询众筹促销信息
     *
     * @param params 参数
     * @return 返回众筹促销信息
     */

    List<Marketing> queryCrowdFundingMarketings(Map<String, Object> params);


    /**
     * 查询众筹促销的总记录数
     *
     * @param params 查询参数
     * @return 返回众筹促销总记录数
     */

    int queryCrowdFundingMarketingCount(Map<String, Object> params);


    /**
     * 查询已结束的众筹促销
     *
     * @return 已结束的众筹促销信息
     */

    List<Marketing> queryEndCrowdFundingMarketingList();

    /**
     * 分页查询店铺众筹促销信息
     *
     * @param params 参数
     * @return 返回店铺众筹促销信息
     */

    List<Marketing> queryStoreCrowdFundingMarketings(Map<String, Object> params);

    /**
     * 查询店铺众筹促销的总记录数
     *
     * @param params 查询参数
     * @return 返回店铺众筹促销总记录数
     */

    int queryStoreCrowdFundingMarketingCount(Map<String, Object> params);

    /**
     * 新增促销
     *
     * @param marketing 促销信息
     * @return 成功返回 1 失败返回0
     */

    int addMarketing(Marketing marketing);

    /**
     * 查询促销信息
     *
     * @param params 查询参数
     * @return 返回促销信息
     */

    Marketing queryMarketingById(Map<String, Object> params);

    /**
     * 更新促销信息
     *
     * @param marketing 促销信息
     * @return 成功返回1 失败返回0
     */

    int updateMarketing(Marketing marketing);

    /**
     * 删除促销
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int deleteMarketing(Map<String, Object> params);

    /**
     * 批量删除促销
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int deleteMarketings(Map<String, Object> params);

    /**
     * 查询加入购物车的时候 单品的默认促销(主要是满减和满折和满赠促销 随机查出一个)
     *
     * @param skuId 单品id
     * @return 返回满减满折满赠促销
     */

    Marketing queryMarketingForShoppingCart(String skuId);

    /**
     * 根据单品id查询单品的促销
     *
     * @param params 参数
     * @return 返回粗细信息
     */

    List<Marketing> queryMarketingsBySkuId(Map<String, Object> params);


    /**
     * *************************************************************** 分隔符 *********************************************************************************
     *
     * 前端用，（抢购，预售，拼团，试用）
     */

    /**
     * 根据单品id查询单品的促销
     *
     * @param skuId 单品id
     * @return 抢购促销
     */

    Marketing queryPanicBuyMaketingBySkuId(String skuId);

    /**
     * 查询促销总纪录数(带有单品信息，前端用)，（预售和拼团）
     *
     * @param params 查询参数
     * @return 返回促销总纪录数
     */

    int queryMarketingsForSiteCount(Map<String, Object> params);

    /**
     * 查询促销信息(带有单品信息，前端用)，（预售和拼团）
     *
     * @param params 查询参数
     * @return 返回促销信息
     */

    List<Marketing> queryMarketingsForSite(Map<String, Object> params);

    /**
     * 查询众筹促销总纪录数(前端用)
     *
     * @param params 查询参数
     * @return 返回众筹促销总纪录数
     */

    int queryCrowdFundingMarketingsForSiteCount(Map<String, Object> params);

    /**
     * 查询众筹促销信息(前端用)
     *
     * @param params 查询参数
     * @return 返回众筹促销信息
     */

    List<Marketing> queryCrowdFundingMarketingsForSite(Map<String, Object> params);

    /**
     * 查询试用促销总纪录数
     *
     * @param params 查询参数
     * @return 返回试用促销总纪录数
     */

    int queryTryMarketingsForSiteCount(Map<String, Object> params);

    /**
     * 查询试用促销信息
     *
     * @param params 查询参数
     * @return 返回试用促销信息
     */

    List<Marketing> queryTryMarketingsForSite(Map<String, Object> params);

    /**
     * 查询已过期且没有审核的试用促销
     */

    List<Marketing> queryTimeOutAndUnAuditTryMarketings();

    /**
     * 分页查询促销信息，（前端抢购用到）
     *
     * @param params 参数
     * @return 返回促销信息
     */

    List<Marketing> queryMarketings(Map<String, Object> params);

    /**
     * 查询交叉时间内含有相同单品的互斥促销数量
     *
     * @param params 参数
     * @return 返回交叉时间内含有相同单品的互斥促销数量
     */

    int queryExclusionMarketingCount(Map<String, Object> params);

    /**
     * 查询交叉时间内含有相同商品的众筹促销数量
     *
     * @param params 参数
     * @return 返回交叉时间内含有相同单品的互斥促销数量
     */

    int querySpuExclusionMarketingCount(Map<String, Object> params);

}
