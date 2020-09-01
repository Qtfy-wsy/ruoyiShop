package com.ruoyi.integral.service;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.integral.domain.PointMallOrder;

/**
 * 积分商城订单聚合服务接口
 */
public interface PointMallOrderServiceApi {

    /**
     * 根据订单id查询订单信息
     *
     * @param id         积分商城订单id
     * @param customerId 用户id
     * @return 返回订单信息, 包含用户名和快递公司名
     */
    PointMallOrder queryPointMallOrderDetailById(long id, Long customerId);

    /**
     * 新增积分商城订单
     *
     * @param pointMallOrder 积分商城订单实体
     * @return 1成功 0失败 -1缺少参数 -2库存不足 -3积分商品不存在 ServiceException 积分不足 -5未发布
     */
    AjaxResult addPointMallOrder(PointMallOrder pointMallOrder);


    /**
     * 发货
     *
     * @param id            积分商城订单id
     * @param logisticsId   物流公司id
     * @param logisticsCode 运单号
     * @return 成功返回1 失败返回0 -1:没有运单号 -2:运单号中包含中文
     */
    int deliverPointMallOrder(long id, long logisticsId, String logisticsCode);
}
