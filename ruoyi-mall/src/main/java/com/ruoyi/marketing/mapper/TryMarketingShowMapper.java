package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.MarketingsSku;
import com.ruoyi.marketing.domain.TryMarketingShow;

import java.util.List;
import java.util.Map;

/**
 * 试用活动数据库接口
 *
 * @author 伊甸园商城 created on 2020/6/9
 */
public interface TryMarketingShowMapper {

    /**
     * 新增试用活动
     *
     * @param tryMarketingShowList 试用活动列表
     * @return 成功>0 否则失败
     */

    int addTryMarketingShows(List<TryMarketingShow> tryMarketingShowList);

    /**
     * 删除试用活动
     *
     * @param params 删除参数
     * @return 成功>0 否则失败
     */

    int deleteTryMarketingShows(Map<String, Object> params);

    /**
     * 修改试用活动
     *
     * @param params 修改参数
     * @return 成功>0 否则失败
     */

    int updateTryMarketingShow(Map<String, Object> params);

    /**
     * 根据试用id查询试用活动数量
     *
     * @param params 查询参数
     * @return 试用活动数量
     */

    int queryTryMarketingShowCountByTryId(Map<String, Object> params);

    /**
     * 分页查询试用活动集合
     *
     * @param parsms 查询参数
     * @return 试用活动集合
     */

    List<TryMarketingShow> queryTryMarketingShowList(Map<String, Object> parsms);

    /**
     * 查询试用活动总记录数
     *
     * @param parsms 查询参数
     * @return 试用活动总记录数
     */

    int queryTryMarketingShowListCount(Map<String, Object> parsms);

    /**
     * 分页查询店铺参与平台试用活动集合
     *
     * @param parsms 查询参数
     * @return 店铺参与平台试用活动集合
     */

    List<TryMarketingShow> queryTryMarketingShowListForStore(Map<String, Object> parsms);

    /**
     * 查询店铺参与平台试用活动总记录数
     *
     * @param parsms 查询参数
     * @return 店铺参与平台试用活动总记录数
     */

    int queryTryMarketingShowListCountForStore(Map<String, Object> parsms);

    /**
     * 删除试用活动促销分类id（删除促销分类时用）
     *
     * @param parsms 参数
     * @return 成功>0 否则失败
     */

    int deleteTryCate(Map<String, Object> parsms);

    /**
     * 分页查询试用活动列表（前端用）
     *
     * @param parsms 查询参数
     * @return 试用活动列表
     */

    List<MarketingsSku> queryTrysForSite(Map<String, Object> parsms);

    /**
     * 分页查询试用活动总记录数（前端用）
     *
     * @param parsms 查询参数
     * @return 试用活动总记录数
     */

    int queryTrysCountForSite(Map<String, Object> parsms);

    /**
     * 根据促销id删除试用活动
     *
     * @param params 删除参数
     */

    void deleteTrysByMarketingIds(Map<String, Object> params);

    /**
     * 删除结束的试用活动（定时任务）
     */

    void deleteEndTrys();

}
