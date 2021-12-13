package com.ruoyi.integral.service;

import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.util.PageHelper;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by 伊甸园商城 on 17/5/25.
 * 会员积分服务接口
 */
public interface CustomerPointService {

    /**
     * 分页查询积分记录
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @param sourceType 积分来源
     * @return 返回积分记录
     */
    PageHelper<CustomerPoint> queryCustomerPoints(PageHelper<CustomerPoint> pageHelper, long customerId, String sourceType,String type);

    /**
     * 查询用户积分总额
     *
     * @param customerId 用户id
     * @return 返回用户积分总额
     */
    int queryCustomerPointCount(long customerId);

    List<CustomerPoint> queryCustomerPointGroupByType(long customerId);
    /**
     * 新增会员积分纪录
     *
     * @param customerPoint 会员积分
     * @return 成功返回1 失败返回0
     */
    int addCustomerPoint(CustomerPoint customerPoint);

    /**
     * 今天是否签到
     *
     * @param customerId 用户id
     * @return 1:未签到 -1:已签到
     */
    int isTodaySign(long customerId);

    /**
     * 添加签到记录
     *
     * @param customerId 用户id
     * @return 1：成功 -1：没有开启签到活动 -2：今天已签到
     */
    int addSignRecord(long customerId, Consumer<Integer> consumer);
}
