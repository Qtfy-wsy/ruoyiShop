package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.FullGift;

import java.util.List;

/**
 * Created by 伊甸园商城 on 18/1/2.
 * 满赠数据库接口
 */
public interface FullGiftMapper {

    /**
     * 根据促销id查询满赠促销
     *
     * @param marketingId 促销id
     * @return 返回满赠促销
     */

    List<FullGift> queryByMarketingId(long marketingId);

    /**
     * 添加满赠促销
     *
     * @param fullGifts 满赠实体集合
     * @return >1:成功 否则失败
     */

    int addFullGifts(List<FullGift> fullGifts);

    /**
     * 删除满赠促销（物理删除）
     *
     * @param marketingId 促销id
     */

    int deleteByMarketingId(long marketingId);
}
