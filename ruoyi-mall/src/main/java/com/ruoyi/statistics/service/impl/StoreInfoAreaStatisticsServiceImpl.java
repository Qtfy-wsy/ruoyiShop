package com.ruoyi.statistics.service.impl;


import com.ruoyi.statistics.service.StoreInfoAreaStatisticsService;
import com.ruoyi.store.mapper.TStoreInfoMapper;
import com.ruoyi.store.vo.StoreInfoAreaStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 店铺地区统计服务接口实现类
 *
 * @author 魔金商城 created on 2019/4/11
 */
@Service
@Slf4j
public class StoreInfoAreaStatisticsServiceImpl implements StoreInfoAreaStatisticsService {

    /**
     * 注入店铺数据库接口
     */
    @Autowired
    private TStoreInfoMapper storeInfoMapper;

    @Override
    public List<StoreInfoAreaStatistics> queryStoreInfoAreaStatistics(String startTime, String endTime) {
        log.debug("queryStoreInfoAreaStatistics and startTime :{} \r\n endTime :{}", startTime, endTime);
        Map<String, Object> params = new HashMap<>();
        params.put("startTime", startTime);
        params.put("endTime", endTime);
        return storeInfoMapper.queryStoreInfoAreaStatistics(params);
    }

}
