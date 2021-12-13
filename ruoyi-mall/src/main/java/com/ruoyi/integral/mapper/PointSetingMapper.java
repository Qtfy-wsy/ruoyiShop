package com.ruoyi.integral.mapper;


import com.ruoyi.integral.domain.PointSeting;

/**
 * Created by 伊甸园商城 on 17/5/23.
 * 积分设置数据库接口
 */
public interface PointSetingMapper {

    /**
     * 修改积分设置
     *
     * @param pointSeting 积分设置
     * @return 成功返回 1 失败返回0
     */

    int updatePointSeting(PointSeting pointSeting);

    /**
     * 查询积分设置
     *
     * @return 返回积分设置
     */

    PointSeting queryPointSeting();
}
