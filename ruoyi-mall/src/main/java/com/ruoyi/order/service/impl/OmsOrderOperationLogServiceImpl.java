package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OmsOrderOperationLog;
import com.ruoyi.order.mapper.OmsOrderOperationLogMapper;
import com.ruoyi.order.service.IOmsOrderOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单操作日志Service业务层处理
 *
 * @author 商城
 */
@Service
public class OmsOrderOperationLogServiceImpl implements IOmsOrderOperationLogService {
    @Autowired
    private OmsOrderOperationLogMapper omsOrderOperationLogMapper;

    /**
     * 查询订单操作日志
     *
     * @param id 订单操作日志ID
     * @return 订单操作日志
     */
    @Override
    public OmsOrderOperationLog selectOmsOrderOperationLogById(Long id) {
        return omsOrderOperationLogMapper.selectOmsOrderOperationLogById(id);
    }

    @Override
    public List<OmsOrderOperationLog> queryOrderOperatonLogByOrderId(long orderId) {
        return omsOrderOperationLogMapper.queryOrderOperatonLogByOrderId(orderId);
    }

    /**
     * 查询订单操作日志列表
     *
     * @param omsOrderOperationLog 订单操作日志
     * @return 订单操作日志
     */
    @Override
    public List<OmsOrderOperationLog> selectOmsOrderOperationLogList(OmsOrderOperationLog omsOrderOperationLog) {
        return omsOrderOperationLogMapper.selectOmsOrderOperationLogList(omsOrderOperationLog);
    }

    /**
     * 新增订单操作日志
     *
     * @param omsOrderOperationLog 订单操作日志
     * @return 结果
     */
    @Override
    public int insertOmsOrderOperationLog(OmsOrderOperationLog omsOrderOperationLog) {
        omsOrderOperationLog.setCreateTime(DateUtils.getNowDate());
        return omsOrderOperationLogMapper.insertOmsOrderOperationLog(omsOrderOperationLog);
    }

    /**
     * 修改订单操作日志
     *
     * @param omsOrderOperationLog 订单操作日志
     * @return 结果
     */
    @Override
    public int updateOmsOrderOperationLog(OmsOrderOperationLog omsOrderOperationLog) {
        return omsOrderOperationLogMapper.updateOmsOrderOperationLog(omsOrderOperationLog);
    }

    /**
     * 批量删除订单操作日志
     *
     * @param ids 需要删除的订单操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderOperationLogByIds(Long[] ids) {
        return omsOrderOperationLogMapper.deleteOmsOrderOperationLogByIds(ids);
    }

    /**
     * 删除订单操作日志信息
     *
     * @param id 订单操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderOperationLogById(Long id) {
        return omsOrderOperationLogMapper.deleteOmsOrderOperationLogById(id);
    }
}
