package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OmsTransRecords;
import com.ruoyi.order.mapper.OmsTransRecordsMapper;
import com.ruoyi.order.service.IOmsTransRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 支付流水Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class OmsTransRecordsServiceImpl implements IOmsTransRecordsService {
    @Autowired
    private OmsTransRecordsMapper omsTransRecordsMapper;

    /**
     * 查询支付流水
     *
     * @param id 支付流水ID
     * @return 支付流水
     */
    @Override
    public OmsTransRecords selectOmsTransRecordsById(Long id) {
        return omsTransRecordsMapper.selectOmsTransRecordsById(id);
    }

    /**
     * 查询支付流水列表
     *
     * @param omsTransRecords 支付流水
     * @return 支付流水
     */
    @Override
    public List<OmsTransRecords> selectOmsTransRecordsList(OmsTransRecords omsTransRecords) {
        return omsTransRecordsMapper.selectOmsTransRecordsList(omsTransRecords);
    }

    /**
     * 新增支付流水
     *
     * @param omsTransRecords 支付流水
     * @return 结果
     */
    @Override
    public int insertOmsTransRecords(OmsTransRecords omsTransRecords) {
        omsTransRecords.setCreateTime(DateUtils.getNowDate());
        return omsTransRecordsMapper.insertOmsTransRecords(omsTransRecords);
    }

    /**
     * 修改支付流水
     *
     * @param omsTransRecords 支付流水
     * @return 结果
     */
    @Override
    public int updateOmsTransRecords(OmsTransRecords omsTransRecords) {
        return omsTransRecordsMapper.updateOmsTransRecords(omsTransRecords);
    }

    /**
     * 批量删除支付流水
     *
     * @param ids 需要删除的支付流水ID
     * @return 结果
     */
    @Override
    public int deleteOmsTransRecordsByIds(Long[] ids) {
        return omsTransRecordsMapper.deleteOmsTransRecordsByIds(ids);
    }

    /**
     * 删除支付流水信息
     *
     * @param id 支付流水ID
     * @return 结果
     */
    @Override
    public int deleteOmsTransRecordsById(Long id) {
        return omsTransRecordsMapper.deleteOmsTransRecordsById(id);
    }
}
