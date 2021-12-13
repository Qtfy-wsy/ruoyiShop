package com.ruoyi.integral.mapper;


import com.ruoyi.integral.domain.CustomerPoint;

import java.util.List;
import java.util.Map;

/**
 * Created by 伊甸园商城 on 17/5/25.
 * 会员积分数据库接口
 */
public interface CustomerPointMapper {

    /**
     * 查询会员积分总数
     *
     * @param params 查询 参数
     * @return 返回会员积分总数
     */

    int queryCustomerPointCount(Map<String, Object> params);

    /**
     * 查询会员积分
     *
     * @param params 查询参数
     * @return 返回会员积分
     */

    List<CustomerPoint> queryCustomerPoints(Map<String, Object> params);

    /**
     * 查询用户积分总额
     *
     * @param customerId 会员id
     * @return 返回用户积分总额
     */

    Integer queryCustomerPointAllCount(long customerId);

    List<CustomerPoint> queryCustomerPointGroupByType(long customerId);

    /**
     * 新增会员积分
     *
     * @param customerPoint 会员积分
     * @return 成功返回1 失败返回0
     */

    int addCustomerPoint(CustomerPoint customerPoint);

    /**
     * 查询最近一次签到记录
     *
     * @param customerId 用户id
     */

    CustomerPoint queryLastSign(long customerId);
}
