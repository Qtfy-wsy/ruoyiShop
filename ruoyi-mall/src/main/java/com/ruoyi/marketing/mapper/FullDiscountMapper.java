package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.FullDiscount;

import java.util.List;

/**
 * Created by 魔金商城 on 17/6/9.
 * 满折数据库接口
 */
public interface FullDiscountMapper {

    /**
     * 新增满折促销
     *
     * @param fullDiscounts 满折促销
     */

    void addFullDiscounts(List<FullDiscount> fullDiscounts);

    /**
     * 根据促销id查询满折促销
     *
     * @param marketingId 促销id
     * @return 返回满折促销
     */

    List<FullDiscount> queryByMarketingId(long marketingId);

    /**
     * 根据促销id删除满折促销
     *
     * @param marketingId 促销id
     */

    void deleteByMarketingId(long marketingId);
}
