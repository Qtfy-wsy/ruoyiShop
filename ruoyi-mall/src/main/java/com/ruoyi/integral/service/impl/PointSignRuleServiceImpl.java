package com.ruoyi.integral.service.impl;

import com.ruoyi.integral.domain.PointSignRule;
import com.ruoyi.integral.mapper.PointSignRuleMapper;
import com.ruoyi.integral.service.PointSignRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 签到积分规则服务实现类
 */
@Service
public class PointSignRuleServiceImpl implements PointSignRuleService {

    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(PointSignRuleServiceImpl.class);
    /**
     * 注入签到积分规则数据库服务
     */
    @Autowired
    private PointSignRuleMapper pointSignRuleMapper;

    @Override
    public PointSignRule queryPointSignRule() {
        logger.debug("queryPointSignRule...");
        PointSignRule pointSignRule = pointSignRuleMapper.queryPointSignRule();
        return ObjectUtils.isEmpty(pointSignRule) ? pointSignRule : pointSignRule.convertJsonToObject();
    }

    @Override
    public int updatePointSignRule(PointSignRule pointSignRule) {
        logger.debug("updatePointSignRule and pointSignRule:{}", pointSignRule);
        if (!pointSignRule.hasRule()) {
            logger.error("updatePointSignRule fail : do not have rule");
            return -1;
        }
        //校验规则
        int checkStatus = pointSignRule.checkRuleInfos();
        if (checkStatus == 1) {
            if (0 != pointSignRule.getId()) {
                return pointSignRuleMapper.updatePointSignRule(pointSignRule.distinctRuleInfos());
            } else {
                return pointSignRuleMapper.addPointSignRule(pointSignRule.distinctRuleInfos());
            }
        } else {
            return checkStatus;
        }
    }

}
