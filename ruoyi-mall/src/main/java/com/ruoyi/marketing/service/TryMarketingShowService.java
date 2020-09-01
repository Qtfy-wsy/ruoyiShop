package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.MarketingsSku;
import com.ruoyi.marketing.domain.TryMarketingShow;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 试用活动服务接口
 *
 * @author 魔金商城 created on 2020/6/9
 */
public interface TryMarketingShowService {

    /**
     * 新增试用活动
     *
     * @param tryMarketingShowList 试用活动列表
     * @param storeId              店铺id
     * @return 成功>0 否则失败 -1 存在重复添加
     */
    int addTryMarketingShows(List<TryMarketingShow> tryMarketingShowList, long storeId);

    /**
     * 删除试用活动
     *
     * @param ids     试用活动id数组
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */

    int deleteTryMarketingShows(Long[] ids, long storeId);

    /**
     * 修改试用活动
     *
     * @param id      试用活动id
     * @param cateId  促销分类id
     * @param sort    排序
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */

    int updateTryMarketingShow(long id, Long cateId, int sort, long storeId);

    /**
     * 分页查询试用活动列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回试用活动列表
     */
    PageHelper<TryMarketingShow> queryTryMarketingShowList(PageHelper<TryMarketingShow> pageHelper, String name, String skuNo, long storeId);

    /**
     * 分页查询店铺参与平台试用活动列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回店铺参与平台试用活动列表
     */
    PageHelper<TryMarketingShow> queryTryMarketingShowListForStore(PageHelper<TryMarketingShow> pageHelper, String name, String skuNo, long storeId);

    /**
     * 删除试用活动促销分类id（删除促销分类时用）
     *
     * @param cateId  分类id
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */
    int deleteTryCate(long cateId, long storeId);

    /**
     * 分页查询试用活动列表（前端用）
     *
     * @param pageHelper 分页帮助类
     * @param cateId     分类id
     * @param storeId    店铺id
     * @return 返回试用活动列表
     */
    PageHelper<MarketingsSku> queryTrysForSite(PageHelper<MarketingsSku> pageHelper, long cateId, long storeId, int type,String name);

    /**
     * 根据促销id删除试用活动
     *
     * @param marketingIds 促销id集合
     */
    void deleteTrysByMarketingIds(List<Long> marketingIds);

    /**
     * 自动删除结束的试用活动（定时任务）
     */
    void autoDeleteEndTrys();

}
