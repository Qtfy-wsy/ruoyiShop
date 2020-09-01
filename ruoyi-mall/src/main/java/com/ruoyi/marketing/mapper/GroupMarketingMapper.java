package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.GroupMarketing;

import java.util.List;
import java.util.Map;

/**
 * 拼团促销数据库接口
 */
public interface GroupMarketingMapper {

    /**
     * 添加拼团促销
     *
     * @param groupMarketing 拼团促销
     * @return 添加拼团促销
     */

    int addGroupMarketing(GroupMarketing groupMarketing);


    /**
     * 查询拼团促销信息
     *
     * @param params 参数
     * @return 返回拼团促销信息
     */

    List<GroupMarketing> queryGroupMarketing(Map<String, Object> params);

    /**
     * 更新拼团促销
     *
     * @param groupMarketing 拼团促销
     * @return 成功返回1 失败返回0
     */

    int updateGroupMarketing(GroupMarketing groupMarketing);

    /**
     * 根据拼团id集合删除拼团促销
     *
     * @param params 参数
     * @return 成功>0 否则失败
     */

    int deleteGroupMarketingByIds(Map<String, Object> params);

    /**
     * 分页查询拼团促销集合
     *
     * @param parsms 查询参数
     * @return 拼团促销集合
     */

    List<GroupMarketing> queryGroupMarketingList(Map<String, Object> parsms);

    /**
     * 查询拼团促销总记录数
     *
     * @param parsms 查询参数
     * @return 拼团促销总记录数
     */

    int queryGroupMarketingListCount(Map<String, Object> parsms);

    /**
     * 根据id查询拼团促销信息
     *
     * @param parsms 查询参数
     * @return 拼团促销信息
     */

    GroupMarketing queryGroupMarketingById(Map<String, Object> parsms);

}
