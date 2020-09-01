package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.FullDown;

import java.util.List;

/**
 * Created by 魔金商城 on 17/6/8.
 * 满减数据库接口
 */
public interface FullDownMapper {

    /**
     * 新增满减促销
     *
     * @param fullDowns 满减促销
     */

    void addFullDowns(List<FullDown> fullDowns);


    /**
     * 根据促销id删除满减促销
     *
     * @param marketingId 促销id
     */

    void deleteByMarketingId(long marketingId);

    /**
     * 根据促销id查询满减促销
     *
     * @param marketingId 促销id
     * @return 返回满减促销
     */

    List<FullDown> queryByMarketingId(long marketingId);
}
