package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsOrderOperationLog;

import java.util.List;

/**
 * 订单操作日志Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface OmsOrderOperationLogMapper {
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

    List<OmsOrderOperationLog> queryOrderOperatonLogByOrderId(long orderId);

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
     * 删除订单操作日志
     *
     * @param id 订单操作日志ID
     * @return 结果
     */
    public int deleteOmsOrderOperationLogById(Long id);

    /**
     * 批量删除订单操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsOrderOperationLogByIds(Long[] ids);
}
