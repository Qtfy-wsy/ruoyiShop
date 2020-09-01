package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsOrderOperationLog;

import java.util.List;

/**
 * 订单操作日志Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IOmsOrderOperationLogService {
    /**
     * 根据订单id查询订单操作日志
     *
     * @param orderId 订单id
     * @return 返回订单的操作日志
     */
    List<OmsOrderOperationLog> queryOrderOperatonLogByOrderId(long orderId);

    /**
     * 查询订单操作日志
     *
     * @param id 订单操作日志ID
     * @return 订单操作日志
     */
    public OmsOrderOperationLog selectOmsOrderOperationLogById(Long id);

    /**
     * 查询订单操作日志列表
     *
     * @param omsOrderOperationLog 订单操作日志
     * @return 订单操作日志集合
     */
    public List<OmsOrderOperationLog> selectOmsOrderOperationLogList(OmsOrderOperationLog omsOrderOperationLog);

    /**
     * 新增订单操作日志
     *
     * @param omsOrderOperationLog 订单操作日志
     * @return 结果
     */
    public int insertOmsOrderOperationLog(OmsOrderOperationLog omsOrderOperationLog);

    /**
     * 修改订单操作日志
     *
     * @param omsOrderOperationLog 订单操作日志
     * @return 结果
     */
    public int updateOmsOrderOperationLog(OmsOrderOperationLog omsOrderOperationLog);

    /**
     * 批量删除订单操作日志
     *
     * @param ids 需要删除的订单操作日志ID
     * @return 结果
     */
    public int deleteOmsOrderOperationLogByIds(Long[] ids);

    /**
     * 删除订单操作日志信息
     *
     * @param id 订单操作日志ID
     * @return 结果
     */
    public int deleteOmsOrderOperationLogById(Long id);
}
