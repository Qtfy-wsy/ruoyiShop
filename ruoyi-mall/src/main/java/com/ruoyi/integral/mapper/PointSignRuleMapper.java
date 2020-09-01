package com.ruoyi.integral.mapper;

import com.ruoyi.integral.domain.PointSignRule;
import org.springframework.stereotype.Repository;

/**
 * 签到积分规则数据库接口
 */
@Repository
public interface PointSignRuleMapper {

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

    /**
     * 新增积分规则
     *
     * @param pointSignRule 积分规则实体
     * @return 1:成功，否则失败
     */

    int addPointSignRule(PointSignRule pointSignRule);
}
