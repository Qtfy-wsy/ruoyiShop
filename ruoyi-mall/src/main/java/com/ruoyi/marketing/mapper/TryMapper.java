package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.TryMarketing;

import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 18/1/16.
 * 试用促销数据库接口
 */
public interface TryMapper {

    /**
     * 添加试用促销
     *
     * @param tryMarketing 试用促销
     * @return 添加试用促销
     */

    int addTryMarketing(TryMarketing tryMarketing);


    /**
     * 查询试用促销信息
     *
     * @param params 查询参数
     * @return 返回试用促销信息
     */

    List<TryMarketing> queryTryMarketing(Map<String, Object> params);

    /**
     * 更新试用促销
     *
     * @param tryMarketing 试用促销
     * @return 成功返回1 失败返回0
     */

    int updateTryMarketing(TryMarketing tryMarketing);

    /**
     * 更新已申请人数
     *
     * @param tryId 试用促销id
     * @return 成功返回1 失败返回0
     */

    int updateAlreadyApplyNum(long tryId);

    /**
     * 更新审核状态
     *
     * @param tryId 试用促销id
     * @return 成功返回1 失败返回0
     */

    int updateAuditStatus(long tryId);

    /**
     * 根据试用id集合删除试用促销
     *
     * @param params 参数
     * @return 成功>0 否则失败
     */

    int deleteTryMarketingByIds(Map<String, Object> params);

    /**
     * 分页查询试用促销集合
     *
     * @param parsms 查询参数
     * @return 试用促销集合
     */

    List<TryMarketing> queryTryMarketingList(Map<String, Object> parsms);

    /**
     * 查询试用促销总记录数
     *
     * @param parsms 查询参数
     * @return 试用促销总记录数
     */

    int queryTryMarketingListCount(Map<String, Object> parsms);

    /**
     * 根据id查询试用促销信息
     *
     * @param parsms 查询参数
     * @return 试用促销信息
     */

    TryMarketing queryTryMarketingById(Map<String, Object> parsms);

    /**
     * 查询已过期且没有审核的试用促销列表
     */

    List<TryMarketing> queryTimeOutAndUnAuditTryMarketingList();


}
