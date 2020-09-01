package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OmsBackOrderLog;
import com.ruoyi.order.mapper.OmsBackOrderLogMapper;
import com.ruoyi.order.service.IOmsBackOrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 退款退货操作日志Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class OmsBackOrderLogServiceImpl implements IOmsBackOrderLogService {
    @Autowired
    private OmsBackOrderLogMapper omsBackOrderLogMapper;

    /**
     * 查询退款退货操作日志
     *
     * @param id 退款退货操作日志ID
     * @return 退款退货操作日志
     */
    @Override
    public OmsBackOrderLog selectOmsBackOrderLogById(Long id) {
        return omsBackOrderLogMapper.selectOmsBackOrderLogById(id);
    }

    /**
     * 查询退款退货操作日志列表
     *
     * @param omsBackOrderLog 退款退货操作日志
     * @return 退款退货操作日志
     */
    @Override
    public List<OmsBackOrderLog> selectOmsBackOrderLogList(OmsBackOrderLog omsBackOrderLog) {
        return omsBackOrderLogMapper.selectOmsBackOrderLogList(omsBackOrderLog);
    }

    @Override
    public List<OmsBackOrderLog> queryByBackOrderId(long bacKOrderId) {
        return omsBackOrderLogMapper.queryByBackOrderId(bacKOrderId);
    }

    /**
     * 新增退款退货操作日志
     *
     * @param omsBackOrderLog 退款退货操作日志
     * @return 结果
     */
    @Override
    public int insertOmsBackOrderLog(OmsBackOrderLog omsBackOrderLog) {
        omsBackOrderLog.setCreateTime(DateUtils.getNowDate());
        return omsBackOrderLogMapper.insertOmsBackOrderLog(omsBackOrderLog);
    }

    /**
     * 修改退款退货操作日志
     *
     * @param omsBackOrderLog 退款退货操作日志
     * @return 结果
     */
    @Override
    public int updateOmsBackOrderLog(OmsBackOrderLog omsBackOrderLog) {
        return omsBackOrderLogMapper.updateOmsBackOrderLog(omsBackOrderLog);
    }

    /**
     * 批量删除退款退货操作日志
     *
     * @param ids 需要删除的退款退货操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOmsBackOrderLogByIds(Long[] ids) {
        return omsBackOrderLogMapper.deleteOmsBackOrderLogByIds(ids);
    }

    /**
     * 删除退款退货操作日志信息
     *
     * @param id 退款退货操作日志ID
     * @return 结果
     */
    @Override
    public int deleteOmsBackOrderLogById(Long id) {
        return omsBackOrderLogMapper.deleteOmsBackOrderLogById(id);
    }
}
