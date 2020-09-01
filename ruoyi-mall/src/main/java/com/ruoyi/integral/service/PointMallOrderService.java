package com.ruoyi.integral.service;

import com.ruoyi.integral.domain.PointMallOrder;
import com.ruoyi.integral.domain.QueryCriteria;
import com.ruoyi.util.PageHelper;

import java.util.List;


/**
 * 积分商城订单服务接口
 */
public interface PointMallOrderService {


    /**
     * 分页查询积分商城订单
     *
     * @param pointMallOrderPageHelper 分页查询帮助类
     * @param queryCriteria            查询条件
     * @return 积分商城订单集合
     */
    PageHelper<PointMallOrder> queryPointMallOrders(PageHelper<PointMallOrder> pointMallOrderPageHelper, QueryCriteria queryCriteria);


    /**
     * 分页查询积分商城订单(pc端使用)
     *
     * @param pointMallOrderPageHelper 分页查询帮助类
     * @param queryCriteria            查询条件
     * @return 积分商城订单集合
     */
    PageHelper<PointMallOrder> queryPointMallOrdersForSite(PageHelper<PointMallOrder> pointMallOrderPageHelper, QueryCriteria queryCriteria);

    /**
     * 发货
     *
     * @param id            积分商城订单id
     * @param companyName   物流公司名字
     * @param logisticsCode 运单号
     * @return 成功返回1 失败返回0 -1:没有运单号 -2:运单号中包含中文
     */
    int deliverPointMallOrder(long id, String companyName, String logisticsCode);

    /**
     * 根据订单id查询订单信息
     *
     * @param id         积分商城订单id
     * @param customerId 用户id
     * @return 返回订单信息
     */
    PointMallOrder queryPointMallOrderById(long id, Long customerId);

    /**
     * 保存订单
     *
     * @param pointMallOrder 订单信息
     * @return 成功返回>0 失败返回0
     */
    int savePointMallOrder(PointMallOrder pointMallOrder);

    /**
     * 确认收货
     *
     * @param id         积分商城订单id
     * @param customerId 用户id
     * @return 成返回>0 失败返回0
     */
    int confirmReceipt(long id, long customerId);

    /**
     * 查找热销积分商品id
     */
    List<PointMallOrder> queryHotPointSkusId();

    /**
     * 查询用户待发货积分订单数量
     *
     * @param customerId 会员id
     * @return 返回用户待发货积分订单数量
     */
    int toDeliverPointOrderCount(long customerId);

    /**
     * 查询用户待收货积分订单数量
     *
     * @param customerId 会员id
     * @return 返回用户待收货积分订单数量
     */
    int toReceiptPointOrderCount(long customerId);

}
