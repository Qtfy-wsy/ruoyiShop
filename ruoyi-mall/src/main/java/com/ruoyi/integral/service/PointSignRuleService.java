package com.ruoyi.integral.service;

import com.ruoyi.integral.domain.PointSignRule;

/**
 * 签到积分规则服务接口
 */
public interface PointSignRuleService {

    /**
     * 查找积分规则
     */
    PointSignRule queryPointSignRule();

    /**
     * 更新积分规则
     *
     * @param pointSignRule 积分规则实体
     * @return 1:成功，否则失败
     */
    int updatePointSignRule(PointSignRule pointSignRule);

}
