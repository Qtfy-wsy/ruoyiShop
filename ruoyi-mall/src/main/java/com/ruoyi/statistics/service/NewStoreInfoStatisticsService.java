package com.ruoyi.statistics.service;


import com.ruoyi.store.vo.NewStoreInfoStatistics;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 伊甸园商城 on 18/2/5
 * 新增店铺统计服务接口
 */
public interface NewStoreInfoStatisticsService {

    /**
     * 统计新增店铺数量（按日期分组）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回新增店铺数量
     */
    List<NewStoreInfoStatistics> queryNewStoreInfoStatistics(String startTime, String endTime);

    /**
     * 分页统计新增店铺数量（按日期分组）
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 返回新增店铺数量（带分页）
     */
    PageHelper<NewStoreInfoStatistics> queryNewStoreInfoStatisticsWithPage(PageHelper<NewStoreInfoStatistics> pageHelper, String startTime, String endTime);

}
