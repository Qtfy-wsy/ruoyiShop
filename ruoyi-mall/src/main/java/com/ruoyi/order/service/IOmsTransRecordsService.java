package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsTransRecords;

import java.util.List;

/**
 * 支付流水Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IOmsTransRecordsService {
    /**
     * 查询支付流水
     *
     * @param id 支付流水ID
     * @return 支付流水
     */
    public OmsTransRecords selectOmsTransRecordsById(Long id);

    /**
     * 查询支付流水列表
     *
     * @param omsTransRecords 支付流水
     * @return 支付流水集合
     */
    public List<OmsTransRecords> selectOmsTransRecordsList(OmsTransRecords omsTransRecords);

    /**
     * 新增支付流水
     *
     * @param omsTransRecords 支付流水
     * @return 结果
     */
    public int insertOmsTransRecords(OmsTransRecords omsTransRecords);

    /**
     * 修改支付流水
     *
     * @param omsTransRecords 支付流水
     * @return 结果
     */
    public int updateOmsTransRecords(OmsTransRecords omsTransRecords);

    /**
     * 批量删除支付流水
     *
     * @param ids 需要删除的支付流水ID
     * @return 结果
     */
    public int deleteOmsTransRecordsByIds(Long[] ids);

    /**
     * 删除支付流水信息
     *
     * @param id 支付流水ID
     * @return 结果
     */
    public int deleteOmsTransRecordsById(Long id);
}
