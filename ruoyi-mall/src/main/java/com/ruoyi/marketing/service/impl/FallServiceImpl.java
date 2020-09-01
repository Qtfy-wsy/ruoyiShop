package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.mapper.FallMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by 魔金商城 on 17/6/8.
 * 直降促销接口
 */
@Service("fallService")
public class FallServiceImpl extends MarketingTemplate {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(FallServiceImpl.class);

    /**
     * 注入直降数据库接口
     */
    @Autowired
    private FallMapper fallMapper;

    /**
     * 新增直降信息
     *
     * @param marketing 促销信息
     * @return 成功返回1 失败返回0
     */
    @Override
    public int addMarketingDetail(Marketing marketing) {

        logger.debug("addMarketingDetail and marketing:{}", marketing);

        if (!validateParams(marketing)) {
            logger.error("addMarketingDetail fail due to params is error..");
            return 0;
        }
        return fallMapper.addFallMarketing(marketing.getFall());
    }


    /**
     * 更新直降信息
     *
     * @param marketing 促销信息
     * @return 成功返回1 失败返回0
     */
    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {

        logger.debug("updateMarketingDetail and marketing:{}", marketing);

        if (!validateParams(marketing)) {
            logger.error("updateMarketingDetail fail due to params is error..");
            return 0;
        }
        return fallMapper.updateFallMarketing(marketing.getFall());
    }

    @Override
    public void setMarketingDetail(Marketing marketing) {
       //

        if (Objects.isNull(marketing) || !marketing.isFallMarketing()) {
            logger.error("setMarketingDetail fail due to params is error....");
            return;
        }

        marketing.setFall(fallMapper.queryFallByMarketingId(marketing.getId()));
    }


    /**
     * 判断当前促销是否正确
     *
     * @param marketing 促销
     * @return 正确返回true  不正确返回false
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && Objects.nonNull(marketing.getFall()) && marketing.isFallMarketing();
    }

}
