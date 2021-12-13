package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.GroupMarketingShow;

import java.util.List;
import java.util.Map;

/**
 * 拼团活动数据库接口
 *
 * @author 伊甸园商城 created on 2020/5/28
 */
public interface GroupMarketingShowMapper {

    /**
     * 新增拼团活动
     *
     * @param groupMarketingShowList 拼团活动列表
     * @return 成功>0 否则失败
     */

    int addGroupMarketingShows(List<GroupMarketingShow> groupMarketingShowList);

    /**
     * 删除拼团活动
     *
     * @param params 删除参数
     * @return 成功>0 否则失败
     */

    int deleteGroupMarketingShows(Map<String, Object> params);

    /**
     * 修改拼团活动
     *
     * @param params 修改参数
     * @return 成功>0 否则失败
     */

    int updateGroupMarketingShow(Map<String, Object> params);

    /**
     * 根据团购id查询拼团活动数量
     *
     * @param params 查询参数
     * @return 拼团活动数量
     */

    int queryGroupMarketingShowCountByGroupId(Map<String, Object> params);

    /**
     * 分页查询拼团活动集合
     *
     * @param parsms 查询参数
     * @return 拼团活动集合
     */

    List<GroupMarketingShow> queryGroupMarketingShowList(Map<String, Object> parsms);

    /**
     * 查询拼团活动总记录数
     *
     * @param parsms 查询参数
     * @return 拼团活动总记录数
     */

    int queryGroupMarketingShowListCount(Map<String, Object> parsms);

    /**
     * 分页查询店铺参与平台拼团活动集合
     *
     * @param parsms 查询参数
     * @return 店铺参与平台拼团活动集合
     */

    List<GroupMarketingShow> queryGroupMarketingShowListForStore(Map<String, Object> parsms);

    /**
     * 查询店铺参与平台拼团活动总记录数
     *
     * @param parsms 查询参数
     * @return 店铺参与平台拼团活动总记录数
     */

    int queryGroupMarketingShowListCountForStore(Map<String, Object> parsms);

    /**
     * 删除拼团活动促销分类id（删除促销分类时用）
     *
     * @param parsms 参数
     * @return 成功>0 否则失败
     */

    int deleteGroupCate(Map<String, Object> parsms);

    /**
     * 分页查询拼团活动列表（前端用）
     *
     * @param parsms 查询参数
     * @return 拼团活动列表
     */

    List<GroupMarketingShow> queryGroupsForSite(Map<String, Object> parsms);

    /**
     * 分页查询拼团活动总记录数（前端用）
     *
     * @param parsms 查询参数
     * @return 拼团活动总记录数
     */

    int queryGroupsCountForSite(Map<String, Object> parsms);

    /**
     * 根据促销id删除拼团活动
     *
     * @param params 删除参数
     */

    void deleteGroupsByMarketingIds(Map<String, Object> params);

    /**
     * 删除结束的拼团活动（定时任务）
     */

    void deleteEndGroups();

}
