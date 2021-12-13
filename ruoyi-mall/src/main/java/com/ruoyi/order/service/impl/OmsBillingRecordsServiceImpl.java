package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OmsBillingRecords;
import com.ruoyi.order.mapper.OmsBillingRecordsMapper;
import com.ruoyi.order.service.IOmsBillingRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账单记录Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class OmsBillingRecordsServiceImpl implements IOmsBillingRecordsService {
    @Autowired
    private OmsBillingRecordsMapper omsBillingRecordsMapper;

    /**
     * 查询账单记录
     *
     * @param id 账单记录ID
     * @return 账单记录
     */
    @Override
    public OmsBillingRecords selectOmsBillingRecordsById(Long id) {
        return omsBillingRecordsMapper.selectOmsBillingRecordsById(id);
    }

    /**
     * 查询账单记录列表
     *
     * @param omsBillingRecords 账单记录
     * @return 账单记录
     */
    @Override
    public List<OmsBillingRecords> selectOmsBillingRecordsList(OmsBillingRecords omsBillingRecords) {
        return omsBillingRecordsMapper.selectOmsBillingRecordsList(omsBillingRecords);
    }

    /**
     * 新增账单记录
     *
     * @param omsBillingRecords 账单记录
     * @return 结果
     */
    @Override
    public int insertOmsBillingRecords(OmsBillingRecords omsBillingRecords) {
        omsBillingRecords.setCreateTime(DateUtils.getNowDate());
        return omsBillingRecordsMapper.insertOmsBillingRecords(omsBillingRecords);
    }

    /**
     * 修改账单记录
     *
     * @param omsBillingRecords 账单记录
     * @return 结果
     */
    @Override
    public int updateOmsBillingRecords(OmsBillingRecords omsBillingRecords) {
        return omsBillingRecordsMapper.updateOmsBillingRecords(omsBillingRecords);
    }

    /**
     * 批量删除账单记录
     *
     * @param ids 需要删除的账单记录ID
     * @return 结果
     */
    @Override
    public int deleteOmsBillingRecordsByIds(Long[] ids) {
        return omsBillingRecordsMapper.deleteOmsBillingRecordsByIds(ids);
    }

    /**
     * 删除账单记录信息
     *
     * @param id 账单记录ID
     * @return 结果
     */
    @Override
    public int deleteOmsBillingRecordsById(Long id) {
        return omsBillingRecordsMapper.deleteOmsBillingRecordsById(id);
    }
}
