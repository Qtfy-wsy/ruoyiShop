package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.CrowdFunding;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.domain.MarketingItem;
import com.ruoyi.marketing.domain.PanicBuy;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 伊甸园商城 on 17/6/8.
 * 促销查询接口
 */
public interface MarketingQueryService {

    /**
     * 分页查询促销信息
     *
     * @param pageHelper 分页帮助类
     * @param name       促销名称
     * @param type       促销类型
     * @param storeId    店铺id
     * @param status     活动状态
     * @return 返回促销信息
     */
    PageHelper<Marketing> queryMarketings(PageHelper<Marketing> pageHelper, String name, String type, long storeId, String status);

    /**
     * 分页查询众筹促销信息
     *
     * @param pageHelper 分页帮助类
     * @return 返回众筹促销信息
     */
    PageHelper<Marketing> queryCrowdFundingMarketings(PageHelper<Marketing> pageHelper, CrowdFunding.QueryCriteria queryCriteria);

    /**
     * 查询已结束的众筹促销
     */
    List<Marketing> queryEndCrowdFundingMarketingList();

    /**
     * 分页查询店铺众筹促销信息
     *
     * @param pageHelper 分页帮助类
     * @return 返回店铺众筹促销信息
     */
    PageHelper<Marketing> queryStoreCrowdFundingMarketings(PageHelper<Marketing> pageHelper, CrowdFunding.QueryCriteria queryCriteria);

    /**
     * 分页查询抢购促销信息
     *
     * @param pageHelper 分页帮助类
     * @param cateId     分类id
     * @return 返回促销信息
     */
    PageHelper<Marketing> queryPanicBuy(PageHelper<Marketing> pageHelper, int cateId);

    /**
     * 根据id查询促销信息
     *
     * @param id      营销id
     * @param storeId 店铺id
     * @return 返回营销信息(营销的基本信息 和营销的具体信息)
     */
    Marketing queryMarketingById(long id, long storeId);

    /**
     * 根据id查询基本促销信息
     *
     * @param id      营销id
     * @param storeId 店铺id
     * @return 返回基本营销信息
     */
    Marketing querySimpleMarketingById(long id, long storeId);

    /**
     * 查询单品放入购物车中的默认促销id (促销主要是 满减和满折 随机查出一个)
     *
     * @param skuId 单品id
     * @return 返回可用的满减和满折的促销id
     */
    Marketing queryMarketingForShoppingCart(String skuId);

    /**
     * 查询单品的促销
     *
     * @param skuId          单品id
     * @param isNeedDetail   是否需要查询促销详情
     * @param marketingItems 查询条件
     * @return 返回单品的促销信息
     */
    List<Marketing> queryMarketingsBySkuId(String skuId, boolean isNeedDetail, MarketingItem... marketingItems);


    /**
     * 查询已过期且没有审核过的试用促销
     */
    List<Marketing> queryTimeOutAndUnAuditTryMarketings();

    /**
     * 根据单品id查询单品的促销
     *
     * @param skuId 单品id
     * @return 抢购促销
     */
    Marketing queryPanicBuyMaketingBySkuId(String skuId);

    /**
     * 查询促销列表  前端使用带有单品信息
     *
     * @param pageHelper 分页帮助类
     * @param types      促销类型
     * @param cateId     分类id 0 表示查看所有分类
     * @return 返回促销列表
     */
    PageHelper<Marketing> queryMarketingsForSite(PageHelper<Marketing> pageHelper, List<Integer> types, int cateId);

    /**
     * 查询众筹促销列表  前端使用
     *
     * @param pageHelper 分页帮助类
     * @param cateId     分类id 0 表示查看所有分类
     * @return 返回促销列表
     */
    PageHelper<Marketing> queryCrowdFundingMarketingsForSite(PageHelper<Marketing> pageHelper, int cateId);


    /**
     * 查询试用促销列表  前端使用带有单品信息
     *
     * @param pageHelper 分页帮助类
     * @param cateId     分类id 0 表示查看所有分类
     * @return 返回促销列表
     */
    PageHelper<Marketing> queryTryMarketingsForSite(PageHelper<Marketing> pageHelper, int cateId);

    /**
     * 查询交叉时间内含有相同单品的互斥促销数量
     *
     * @param marketing 促销实体
     * @return 交叉时间内含有相同单品的互斥促销数量 -1缺少参数 -2所选商品中存在有商品组合的商品 -3所选商品中存在有会员价的商品 -4众筹互斥 -5所选商品存在含有批发规则的商品
     */
    int queryExclusionMarketingCount(Marketing marketing);

    /**
     * 查询含有相同单品的有效互斥促销数量
     *
     * @param skuIds 单品id集合
     * @param from   1:商品组合 2:商品
     * @return 含有相同单品的有效互斥促销数量
     */
    int queryExclusionMarketingCountBySkuIds(List<String> skuIds, Long from);

    /**
     * 根据单品id和众筹id查询有效的众筹信息
     *
     * @param crowdFundingId 众筹id
     * @param skuId          单品id
     * @return 返回有效的众筹信息
     */
    Marketing queryCrowdFundingByIdAndSkuId(long crowdFundingId, String skuId);

    /**
     * 分页查询店铺抢购促销列表
     *
     * @param pageHelper 分页帮助类
     * @param storeId    店铺id
     * @return 返回店铺抢购促销列表
     */
    PageHelper<PanicBuy> queryStorePanicBuyList(PageHelper<PanicBuy> pageHelper, long storeId);

}
