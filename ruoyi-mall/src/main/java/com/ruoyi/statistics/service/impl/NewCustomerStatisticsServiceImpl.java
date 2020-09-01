package com.ruoyi.statistics.service.impl;


import com.ruoyi.member.mapper.UmsMemberMapper;
import com.ruoyi.statistics.service.NewCustomerStatisticsService;
import com.ruoyi.store.vo.NewCustomerStatistics;
import com.ruoyi.util.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 魔金商城 on 18/2/1
 * 新增会员统计服务接口实现
 */
@Service
public class NewCustomerStatisticsServiceImpl implements NewCustomerStatisticsService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(NewCustomerStatisticsServiceImpl.class);

    /**
     * 注入会员数据库接口
     */
    @Autowired
    private UmsMemberMapper customerMapper;

    @Override
    public List<NewCustomerStatistics> queryNewCustomerStatistics(String startTime, String endTime) {
        logger.debug("queryNewCustomerStatistics and startTime:{} \r\n endTime:{}", startTime, endTime);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return customerMapper.queryNewCustomerStatistics(params);
    }

    @Override
    public PageHelper<NewCustomerStatistics> queryNewCustomerStatisticsWithPage(PageHelper<NewCustomerStatistics> pageHelper, String startTime, String endTime) {
        logger.debug("queryNewCustomerStatisticsWithPage and pageHelper :{} \r\n and startTime:{} \r\n endTime:{}", pageHelper, startTime, endTime);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return pageHelper.setListDates(customerMapper.queryNewCustomerStatisticsWithPage(pageHelper.getQueryParams(params, customerMapper.queryNewCustomerStatisticsCount(params))));
    }

}
