package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.TryReport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


/**
 * 试用报告数据库接口
 */
@Repository
public interface TryReportMapper {

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
     * @return 试用报告
     */

    TryReport queryTryReportByTryApplyId(long tryApplyId);

    /**
     * 分页查询试用报告
     *
     * @param params 查询参数
     * @return 试用报告集合
     */

    List<TryReport> queryTryReportList(Map<String, Object> params);

    /**
     * 试用报告总数
     *
     * @param params 查询参数
     * @return 试用报告数量
     */

    int queryTryReportListCount(Map<String, Object> params);


}
