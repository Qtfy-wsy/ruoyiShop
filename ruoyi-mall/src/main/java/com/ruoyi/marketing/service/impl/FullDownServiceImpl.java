package com.ruoyi.marketing.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.marketing.domain.FullDown;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.mapper.FullDownMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 伊甸园商城 on 17/6/8.
 * 满减服务接口
 */
@Service("fullDownService")
public class FullDownServiceImpl extends MarketingTemplate {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(FullDownServiceImpl.class);

    /**
     * 注入满减促销数据库接口
     */
    @Autowired
    private FullDownMapper fullDownMapper;

    @Override
    public int addMarketingDetail(Marketing marketing) {

        logger.debug("addMarketingDetail and marketing");

        if (!validateParams(marketing)) {
            logger.error("addMarketingDetail fail due to params is error....");
            return 0;
        }
        if (checkPrice(marketing.getFullDowns())) {
            logger.error("addMarketingDetail fail:fullPrice<=price");
            throw new ServiceException("R-000013");
        }

        fullDownMapper.addFullDowns(marketing.getFullDowns());

        return 1;
    }

    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {
        logger.error("updateMarketingDetail and marketing:{}", marketing);
        if (!validateParams(marketing)) {
            logger.error("updateMarketingDetail fail due to params is error...");
            return 0;
        }
        if (checkPrice(marketing.getFullDowns())) {
            logger.error("updateMarketingDetail fail:fullPrice<=price");
            throw new ServiceException("R-000013");
        }
        // 删除满减促销
        fullDownMapper.deleteByMarketingId(marketing.getId());

        // 新增满减促销
        addMarketingDetail(marketing);

        return 1;
    }

    @Override
    public void setMarketingDetail(Marketing marketing) {


        if (Objects.isNull(marketing) || !marketing.isFullDownMarketing()) {
            logger.error("setMarketingDetail fail due to params is error...");
            return;
        }

        marketing.setFullDowns(fullDownMapper.queryByMarketingId(marketing.getId()));
    }

    /**
     * 校验当前促销是否正确
     *
     * @param marketing 促销信息
     * @return 正确返回 1 失败返回0
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && !CollectionUtils.isEmpty(marketing.getFullDowns()) && marketing.isFullDownMarketing();
    }

    /**
     * 校验金额,存在满的金额小于等于减的金额的返回true
     *
     * @param fullDownList 满减集合
     */
    private boolean checkPrice(List<FullDown> fullDownList) {
        return fullDownList.stream().filter(FullDown::checkPrice).collect(Collectors.toList()).size() > 1;
    }
}
