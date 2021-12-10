package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreOrderOperationLog;

import java.util.List;

/**
 * 门店订单操作日志Mapper接口
 *
 * @author 商城
 */
public interface TStoreOrderOperationLogMapper {
    /**
     * 查询门店订单操作日志
     *
     * @param id 门店订单操作日志ID
     * @return 门店订单操作日志
     */
    public TStoreOrderOperationLog selectTStoreOrderOperationLogById(Long id);

    /**
     * 查询门店订单操作日志列表
     *
     * @param tStoreOrderOperationLog 门店订单操作日志
     * @return 门店订单操作日志集合
     */
    public List<TStoreOrderOperationLog> selectTStoreOrderOperationLogList(TStoreOrderOperationLog tStoreOrderOperationLog);

    List<TStoreOrderOperationLog> queryStoreOrderOperationLogByOrderId(long orderId);

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
     * 删除门店订单操作日志
     *
     * @param id 门店订单操作日志ID
     * @return 结果
     */
    public int deleteTStoreOrderOperationLogById(Long id);

    /**
     * 批量删除门店订单操作日志
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreOrderOperationLogByIds(Long[] ids);
}
