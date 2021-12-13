package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.store.domain.TStoreOrderOperationLog;
import com.ruoyi.store.mapper.TStoreOrderOperationLogMapper;
import com.ruoyi.store.service.ITStoreOrderOperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店订单操作日志Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Service
public class TStoreOrderOperationLogServiceImpl implements ITStoreOrderOperationLogService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreOrderOperationLogServiceImpl.class);
    @Autowired
    private TStoreOrderOperationLogMapper tStoreOrderOperationLogMapper;

    /**
     * 查询门店订单操作日志
     *
     * @param id 门店订单操作日志ID
     * @return 门店订单操作日志
     */
    @Override
    public TStoreOrderOperationLog selectTStoreOrderOperationLogById(Long id) {
        return tStoreOrderOperationLogMapper.selectTStoreOrderOperationLogById(id);
    }

    @Override
    public List<TStoreOrderOperationLog> queryStoreOrderOperationLogByOrderId(long orderId) {
        return tStoreOrderOperationLogMapper.queryStoreOrderOperationLogByOrderId(orderId);
    }

    /**
     * 查询门店订单操作日志列表
     *
     * @param tStoreOrderOperationLog 门店订单操作日志
     * @return 门店订单操作日志
     */
    @Override
    public List<TStoreOrderOperationLog> selectTStoreOrderOperationLogList(TStoreOrderOperationLog tStoreOrderOperationLog) {
        return tStoreOrderOperationLogMapper.selectTStoreOrderOperationLogList(tStoreOrderOperationLog);
    }

    /**
     * 新增门店订单操作日志
     *
     * @param tStoreOrderOperationLog 门店订单操作日志
     * @return 结果
     */
    @Override
    public int insertTStoreOrderOperationLog(TStoreOrderOperationLog tStoreOrderOperationLog) {
        tStoreOrderOperationLog.setCreateTime(DateUtils.getNowDate());
        return tStoreOrderOperationLogMapper.insertTStoreOrderOperationLog(tStoreOrderOperationLog);
    }

    /**
     * 修改门店订单操作日志
     *
     * @param tStoreOrderOperationLog 门店订单操作日志
     * @return 结果
     */
    @Override
    public int updateTStoreOrderOperationLog(TStoreOrderOperationLog tStoreOrderOperationLog) {
        return tStoreOrderOperationLogMapper.updateTStoreOrderOperationLog(tStoreOrderOperationLog);
    }

    /**
     * 批量删除门店订单操作日志
     *
     * @param ids 需要删除的门店订单操作日志ID
     * @return 结果
     */
    @Override
    public int deleteTStoreOrderOperationLogByIds(Long[] ids) {
        return tStoreOrderOperationLogMapper.deleteTStoreOrderOperationLogByIds(ids);
    }

    /**
     * 删除门店订单操作日志信息
     *
     * @param id 门店订单操作日志ID
     * @return 结果
     */
    @Override
    public int deleteTStoreOrderOperationLogById(Long id) {
        return tStoreOrderOperationLogMapper.deleteTStoreOrderOperationLogById(id);
    }
}
