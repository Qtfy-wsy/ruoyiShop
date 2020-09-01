package com.ruoyi.statistics.service;


import com.ruoyi.store.vo.StoreInfoAreaStatistics;

import java.util.List;

/**
 * 店铺地区统计服务接口
 *
 * @author 魔金商城 created on 2019/4/11
 */
public interface StoreInfoAreaStatisticsService {

    /**
     * 统计店铺地区数量（按省级地区分组）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回店铺地区数量统计
     */
    List<StoreInfoAreaStatistics> queryStoreInfoAreaStatistics(String startTime, String endTime);

}
