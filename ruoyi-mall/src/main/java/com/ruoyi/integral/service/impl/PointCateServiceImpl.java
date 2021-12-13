package com.ruoyi.integral.service.impl;

import com.ruoyi.integral.domain.PointCate;
import com.ruoyi.integral.mapper.PointCateMapper;
import com.ruoyi.integral.service.PointCateService;
import com.ruoyi.util.PageHelper;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 伊甸园商城 on 18/1/11.
 * 积分商城分类服务实现接口
 */
@Service
public class PointCateServiceImpl implements PointCateService {

    /**
     * 注入积分商城分类数据库接口
     */
    @Autowired
    private PointCateMapper pointCateMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PointCateServiceImpl.class);

    @Override
    public PageHelper<PointCate> queryPointCates(PageHelper<PointCate> pageHelper, String name) {
        logger.debug("queryPointCates and pageHelper :{} \r\n name : {}", pageHelper, name);
        Map<String, Object> params = new HashMap<>();
        params.put("name", name);
        return pageHelper.setListDates(pointCateMapper.queryPointCates(pageHelper.getQueryParams(params, pointCateMapper.queryPointCateCount(params))));
    }

    @Override
    public PointCate queryPointCateById(long id) {
        logger.debug("queryPointCateById and id {}", id);
        return pointCateMapper.queryPointCateById(id);
    }

    @Override
    public List<PointCate> queryAllPointCates() {
        logger.debug("queryAllPointCates ......");
        return pointCateMapper.queryAllPointCates();
    }

    @Override
    public int addPointCate(PointCate pointCate) {

        if (Objects.isNull(pointCate)) {
            logger.error("addPointCate fail due to pointCate is null...");
            return 0;
        }

        logger.debug("addPointCate and pointCate {}", pointCate);

        return pointCateMapper.addPointCate(pointCate);
    }

    @Override
    public int updatePointCate(PointCate pointCate) {
        logger.debug("updatePointCate and pointCate {}", pointCate);
        return pointCateMapper.updatePointCate(pointCate);
    }

    @Override
    public int deletePointCate(long id) {
        logger.debug("deletePointCate and id {}", id);
        return pointCateMapper.deletePointCate(id);
    }

    @Transactional
    @Override
    public int deletePointCates(long[] ids) {
        logger.debug("deletePointCates and ids : {}", ids);
        if (ArrayUtils.isEmpty(ids)) {
            logger.error("deletePointCates due to ids is empty....");
            return 0;
        }
        Arrays.stream(ids).forEach(id -> deletePointCate(id));
        return 1;
    }

}