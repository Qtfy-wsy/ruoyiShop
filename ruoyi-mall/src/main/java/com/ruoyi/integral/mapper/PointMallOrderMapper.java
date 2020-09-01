package com.ruoyi.integral.mapper;


import com.ruoyi.integral.domain.PointMallOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 积分商城订单数据库接口
 */
@Repository
public interface PointMallOrderMapper {


    /**
     * 分页查询积分商城订单
     *
     * @param params 查询参数
     * @return 积分商城订单集合
     */

    List<PointMallOrder> queryPointMallOrders(Map<String, Object> params);

    /**
     * 积分商城订单数量
     *
     * @param params 查询参数
     * @return 积分商城订单数量
     */

    int queryPointMallOrdersCount(Map<String, Object> params);

    /**
     * 发货
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int deliverPointMallOrder(Map<String, Object> params);

    /**
     * 根据订单id查询订单信息
     *
     * @param params 查询参数
     * @return 返回订单信息
     */

    PointMallOrder queryPointMallOrderById(Map<String, Object> params);

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
     * @param params 请求参数
     * @return 成返回>0 失败返回0
     */

    int confirmReceipt(Map<String, Object> params);

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
