package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.Fall;

/**
 * Created by 伊甸园商城 on 17/6/8.
 * 直降数据库接口
 */
public interface FallMapper {

    /**
     * 新增直降促销
     *
     * @param fall 直降促销
     * @return 成功返回1 失败返回0
     */

    int addFallMarketing(Fall fall);

    /**
     * 根据促销id查询直降信息
     *
     * @param marketingId 促销id
     * @return 返回直降信息
     */

    Fall queryFallByMarketingId(long marketingId);

    /**
     * 修改直降促销
     *
     * @param fall 直降促销
     * @return 成功返回 1 失败返回0
     */

    int updateFallMarketing(Fall fall);
}
