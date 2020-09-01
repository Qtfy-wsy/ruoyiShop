package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.TryReport;
import com.ruoyi.marketing.domain.TrySkuApply;
import com.ruoyi.marketing.mapper.TryReportMapper;
import com.ruoyi.marketing.service.TrySkuApplyService;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 试用报告服务实现
 */
@Service
public class TryReportServiceImpl implements TryReportService {


    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(TrySkuApplyServiceImpl.class);

    /**
     * 注入试用报告数据库服务
     */
    @Autowired
    private TryReportMapper tryReportMapper;

    /**
     * 注入试用申请服务
     */
    @Autowired
    private TrySkuApplyService trySkuApplyService;


    @Override
    public int addTryReport(TryReport tryReport) {
        logger.debug("addTryReport and tryReport:{} ", tryReport);
        return tryReportMapper.addTryReport(tryReport);
    }

    @Override
    public TryReport queryTryReportByTryApplyId(long tryApplyId, long customerId) {
        logger.debug("queryTryReportByTryApplyId and tryApplyId:{}", tryApplyId);
        TrySkuApply trySkuApply = trySkuApplyService.queryTrySkuApplyById(tryApplyId, customerId, true);
        if (ObjectUtils.isEmpty(trySkuApply)) {
            logger.error("queryTryReportByTryApplyId fail:trySkuApply is null");
            return null;
        }
        TryReport tryReport = tryReportMapper.queryTryReportByTryApplyId(tryApplyId);
        if (ObjectUtils.isEmpty(tryReport)) {
            logger.error("queryTryReportByTryApplyId fail:no tryReport");
            return null;
        }
        tryReport.setTrySkuApply(trySkuApply);
        return tryReport;
    }

    @Override
    public PageHelper<TryReport> queryTryReportList(PageHelper<TryReport> pageHelper, long tryId) {
        logger.debug("queryTryReportList and pageHelper:{} \r\n tryId:{}", pageHelper, tryId);
        Map<String, Object> params = new HashMap<>();
        params.put("tryId", tryId);
        return pageHelper.setListDates(tryReportMapper.queryTryReportList(pageHelper.getQueryParams(params, tryReportMapper.queryTryReportListCount(params))));
    }
}
