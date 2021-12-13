package com.ruoyi.marketing.service.impl;

import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.mapper.FullDiscountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Objects;

/**
 * Created by 伊甸园商城 on 17/6/9.
 * 满折促销服务接口
 */
@Service("fullDiscountService")
public class FullDiscountServiceImpl extends MarketingTemplate {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(FullDiscountServiceImpl.class);

    /**
     * 注入满折促销数据库接口
     */
    @Autowired
    private FullDiscountMapper fullDiscountMapper;

    @Override
    public int addMarketingDetail(Marketing marketing) {

        if (!validateParams(marketing)) {
            logger.error("addMarketingDetail fail due to params is error...");
            return 0;
        }

        fullDiscountMapper.addFullDiscounts(marketing.getFullDiscounts());

        return 1;
    }

    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {

        if (!validateParams(marketing)) {
            logger.error("updateMarketingDetail fail due to params is error...");
            return 0;
        }

        // 删除满折促销
        fullDiscountMapper.deleteByMarketingId(marketing.getId());

        // 新增满折促销
        addMarketingDetail(marketing);

        return 1;
    }

    @Override
    public void setMarketingDetail(Marketing marketing) {
        if (Objects.isNull(marketing) || !marketing.isFullDiscountMarketing()) {
            logger.error("setMarketingDetail fail due to params is error....");
            return;
        }

        marketing.setFullDiscounts(fullDiscountMapper.queryByMarketingId(marketing.getId()));
    }


    /**
     * 校验促销参数是否正确
     *
     * @param marketing 促销
     * @return 成功返回true 失败返回false
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && !CollectionUtils.isEmpty(marketing.getFullDiscounts()) && marketing.isFullDiscountMarketing();
    }
}
