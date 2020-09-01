package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.GroupMarketingShow;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 拼团活动服务接口
 *
 * @author 魔金商城 created on 2020/5/28
 */
public interface GroupMarketingShowService {

    /**
     * 新增拼团活动
     *
     * @param groupMarketingShowList 拼团活动列表
     * @param storeId                店铺id
     * @return 成功>0 否则失败 -1 存在重复添加
     */
    int addGroupMarketingShows(List<GroupMarketingShow> groupMarketingShowList, long storeId);

    /**
     * 删除拼团活动
     *
     * @param ids     拼团活动id数组
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */

    int deleteGroupMarketingShows(Long[] ids, long storeId);

    /**
     * 修改拼团活动
     *
     * @param id      拼团活动id
     * @param cateId  促销分类id
     * @param sort    排序
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */

    int updateGroupMarketingShow(long id, Long cateId, int sort, long storeId);

    /**
     * 分页查询拼团活动列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回拼团活动列表
     */
    PageHelper<GroupMarketingShow> queryGroupMarketingShowList(PageHelper<GroupMarketingShow> pageHelper, String name, String skuNo, long storeId);

    /**
     * 分页查询店铺参与平台拼团活动列表
     *
     * @param pageHelper 分页帮助类
     * @param name       单品名称
     * @param skuNo      单品编号
     * @param storeId    店铺id
     * @return 返回店铺参与平台拼团活动列表
     */
    PageHelper<GroupMarketingShow> queryGroupMarketingShowListForStore(PageHelper<GroupMarketingShow> pageHelper, String name, String skuNo, long storeId);

    /**
     * 删除拼团活动促销分类id（删除促销分类时用）
     *
     * @param cateId  分类id
     * @param storeId 店铺id
     * @return 成功>0 否则失败
     */
    int deleteGroupCate(long cateId, long storeId);

    /**
     * 分页查询拼团活动列表（前端用）
     *
     * @param pageHelper 分页帮助类
     * @param cateId     分类id
     * @param storeId    店铺id
     * @return 返回拼团活动列表
     */
    PageHelper<GroupMarketingShow> queryGroupsForSite(PageHelper<GroupMarketingShow> pageHelper, long cateId, long storeId);

    /**
     * 根据促销id删除拼团活动
     *
     * @param marketingIds 促销id集合
     */
    void deleteGroupsByMarketingIds(List<Long> marketingIds);

    /**
     * 自动删除结束的拼团活动（定时任务）
     */
    void autoDeleteEndGroups();

}
