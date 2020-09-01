package com.ruoyi.marketing.service;


import com.ruoyi.marketing.domain.TryReport;

/**
 * 试用报告聚合服务
 */
public interface TryReportServiceApi {

    /**
     * 添加试用报告
     *
     * @param tryReport  试用报告实体
     * @param customerId 用户id
     * @return 1:成功 0失败 -1:用户没有资格 -2:没有订单 -3没有确认收货
     */
    int addTryReport(TryReport tryReport, long customerId);

    /**
     * 判断有没有提交报告的资格
     *
     * @param tryApplyId 试用申请id
     * @param customerId 用户id
     * @return 1:有资格 -1:用户没有资格 -2:没有订单 -3没有确认收货
     */
    int hasAuthToAddTryReport(long tryApplyId, long customerId);
}
