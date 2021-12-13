package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreOrderOperationLog;

import java.util.List;

/**
 * 门店订单操作日志Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStoreOrderOperationLogService {
    /**
     * 查询门店订单操作日志
     *
     * @param id 门店订单操作日志ID
     * @return 门店订单操作日志
     */
    public TStoreOrderOperationLog selectTStoreOrderOperationLogById(Long id);

    /**
     * 根据门店订单id查询门店订单操作日志
     *
     * @param orderId 门店订单id
     * @return 返回门店订单的操作日志
     */
    List<TStoreOrderOperationLog> queryStoreOrderOperationLogByOrderId(long orderId);

    /**
     * 查询门店订单操作日志列表
     *
     * @param tStoreOrderOperationLog 门店订单操作日志
     * @return 门店订单操作日志集合
     */
    public List<TStoreOrderOperationLog> selectTStoreOrderOperationLogList(TStoreOrderOperationLog tStoreOrderOperationLog);

    /**
     * 新增门店订单操作日志
     *
     * @param tStoreOrderOperationLog 门店订单操作日志
     * @return 结果
     */
    public int insertTStoreOrderOperationLog(TStoreOrderOperationLog tStoreOrderOperationLog);

    /**
     * 修改门店订单操作日志
     *
     * @param tStoreOrderOperationLog 门店订单操作日志
     * @return 结果
     */
    public int updateTStoreOrderOperationLog(TStoreOrderOperationLog tStoreOrderOperationLog);

    /**
     * 批量删除门店订单操作日志
     *
     * @param ids 需要删除的门店订单操作日志ID
     * @return 结果
     */
    public int deleteTStoreOrderOperationLogByIds(Long[] ids);

    /**
     * 删除门店订单操作日志信息
     *
     * @param id 门店订单操作日志ID
     * @return 结果
     */
    public int deleteTStoreOrderOperationLogById(Long id);
}
