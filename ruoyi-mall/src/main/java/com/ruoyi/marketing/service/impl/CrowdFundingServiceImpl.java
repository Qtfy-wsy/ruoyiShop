package com.ruoyi.marketing.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.marketing.domain.Marketing;
import com.ruoyi.marketing.mapper.CrowdFundingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 众筹促销服务实现
 *
 * @author SK
 * @since 2018/4/23
 */
@Service("crowdFundingService")
@Slf4j
public class CrowdFundingServiceImpl extends MarketingTemplate {


    /**
     * 注入试用促销数据库接口
     */
    @Autowired
    private CrowdFundingMapper crowdFundingMapper;

    @Override
    public int addMarketingDetail(Marketing marketing) {

        log.debug("addMarketingDetail and marketing:{}", marketing);

        // 校验参数
        if (!validateParams(marketing)) {
            log.error("addCrowdFundingMarketing fail deu to validate params fail....");
            return 0;
        }
        return crowdFundingMapper.addCrowdFunding(marketing.getCrowdFunding());
    }

    @Override
    public int updateMarketingDetail(Marketing marketing, Marketing oldMarketing) {
        log.debug("updateMarketingDetail and marketing:{}", marketing);

        // 校验参数是否合法
        if (!validateParams(marketing)) {
            log.error("updateCrowdFundingMarketingDetail fail due to marketing is error...");
            return 0;
        }
        //只能修改还未开始的众筹促销
        if (!LocalDateTime.now().isBefore(marketing.getStartTime())) {
            log.error("updateCrowdFundingMarketingDetail fail due to marketing is already start....");
            throw new ServiceException();
        }
        return crowdFundingMapper.updateCrowdFunding(marketing.getCrowdFunding());
    }


    @Override
    public void setMarketingDetail(Marketing marketing) {
      //  log.debug("setMarketingDetail and marketing:{}", marketing);

        if (Objects.isNull(marketing) || !marketing.isCrowdFundingMarketingType()) {
            log.error("setCrowdFundingMarketingDetail fail ...due to marketing is error....");
            return;
        }
        marketing.setCrowdFunding(crowdFundingMapper.queryCrowdFunding(marketing.getId()));
    }

    @Override
    public int addCrowdFundingAlerdyMoney(BigDecimal money, long marketingId) {
        log.debug("addCrowdFundingAlerdyMoney and money:{} \r\n marketingId:{} ", money, marketingId);
        Map<String, Object> params = new HashMap<>();
        params.put("money", money);
        params.put("marketingId", marketingId);
        return crowdFundingMapper.addCrowdFundingAlerdyMoney(params);
    }

    @Override
    public int updateCrowdFundingAutoHandleStatus(long marketingId) {
        log.debug("updateCrowdFundingAutoHandleStatus and marketingId:{}", marketingId);
        return crowdFundingMapper.updateAutoHandleStatus(marketingId);
    }

    /**
     * 校验当前促销是否正确
     *
     * @param marketing 促销信息
     * @return 正确返回 true 失败返回false
     */
    private boolean validateParams(Marketing marketing) {
        return Objects.nonNull(marketing) && Objects.nonNull(marketing.getCrowdFunding()) && marketing.isCrowdFundingMarketingType();
    }

}
