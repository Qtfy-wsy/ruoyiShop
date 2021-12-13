package com.ruoyi.statistics.service;


import com.ruoyi.store.vo.NewCustomerStatistics;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * Created by 伊甸园商城 on 18/2/1
 * 新增会员统计服务接口
 */
public interface NewCustomerStatisticsService {

    /**
     * 统计新增用户数量（按日期分组）
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 返回按日期分组的新增用户数量
     */
    List<NewCustomerStatistics> queryNewCustomerStatistics(String startTime, String endTime);

    /**
     * 分页统计新增用户数量（按日期分组）
     *
     * @param pageHelper 分页帮助类
     * @param startTime  开始时间
     * @param endTime    结束时间
     * @return 返回分页按日期分组的新增用户数量
     */
    PageHelper<NewCustomerStatistics> queryNewCustomerStatisticsWithPage(PageHelper<NewCustomerStatistics> pageHelper, String startTime, String endTime);

}
