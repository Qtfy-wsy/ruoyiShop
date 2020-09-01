package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.TryReport;
import com.ruoyi.util.PageHelper;

/**
 * 试用报告服务接口
 */
public interface TryReportService {


    /**
     * 新增试用报告
     *
     * @param tryReport 试用报告
     * @return 1：成功 0：失败
     */
    int addTryReport(TryReport tryReport);

    /**
     * 查找试用报告
     *
     * @param tryApplyId 试用申请id
     * @param customerId 用户id
     * @return 试用报告
     */
    TryReport queryTryReportByTryApplyId(long tryApplyId, long customerId);

    /**
     * 分页查询试用报告
     *
     * @param pageHelper 分页工具类
     * @param tryId      试用促销id
     * @return 试用报告集合
     */
    PageHelper<TryReport> queryTryReportList(PageHelper<TryReport> pageHelper, long tryId);
}
