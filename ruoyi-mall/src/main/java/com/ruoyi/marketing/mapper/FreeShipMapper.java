package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.FreeShip;
import com.ruoyi.marketing.domain.Marketing;

/**
 * Created by 魔金商城 on 17/6/9.
 * 包邮数据库接口
 */
public interface FreeShipMapper {

    /**
     * 新增包邮促销
     *
     * @param freeShip 包邮促销
     * @return 成功返回1 失败返回0
     */

    int addFreeShip(FreeShip freeShip);

    /**
     * 查询包邮促销
     *
     * @param storeId 店铺id
     * @return 返回包邮促销
     */

    Marketing queryFreeShip(long storeId);

    /**
     * 删除包邮促销
     *
     * @param marketingId 促销id
     * @return 成功返回1 失败返回0
     */

    int deleteFreeShip(long marketingId);

    /**
     * 查询有效期内的包邮促销
     *
     * @param storeId 店铺id
     * @return 返回包邮促销
     */

    Marketing queryEffectiveFreeShip(long storeId);
}
