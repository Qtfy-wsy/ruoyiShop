package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.store.domain.TStoreBillingRecords;
import com.ruoyi.store.mapper.TStoreBillingRecordsMapper;
import com.ruoyi.store.service.ITStoreBillingRecordsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店账单收入支出Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Service
public class TStoreBillingRecordsServiceImpl implements ITStoreBillingRecordsService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreBillingRecordsServiceImpl.class);
    @Autowired
    private TStoreBillingRecordsMapper tStoreBillingRecordsMapper;

    /**
     * 查询门店账单收入支出
     *
     * @param id 门店账单收入支出ID
     * @return 门店账单收入支出
     */
    @Override
    public TStoreBillingRecords selectTStoreBillingRecordsById(Long id) {
        return tStoreBillingRecordsMapper.selectTStoreBillingRecordsById(id);
    }

    /**
     * 查询门店账单收入支出列表
     *
     * @param tStoreBillingRecords 门店账单收入支出
     * @return 门店账单收入支出
     */
    @Override
    public List<TStoreBillingRecords> selectTStoreBillingRecordsList(TStoreBillingRecords tStoreBillingRecords) {
        return tStoreBillingRecordsMapper.selectTStoreBillingRecordsList(tStoreBillingRecords);
    }

    /**
     * 新增门店账单收入支出
     *
     * @param tStoreBillingRecords 门店账单收入支出
     * @return 结果
     */
    @Override
    public int insertTStoreBillingRecords(TStoreBillingRecords tStoreBillingRecords) {
        tStoreBillingRecords.setCreateTime(DateUtils.getNowDate());
        return tStoreBillingRecordsMapper.insertTStoreBillingRecords(tStoreBillingRecords);
    }

    /**
     * 修改门店账单收入支出
     *
     * @param tStoreBillingRecords 门店账单收入支出
     * @return 结果
     */
    @Override
    public int updateTStoreBillingRecords(TStoreBillingRecords tStoreBillingRecords) {
        return tStoreBillingRecordsMapper.updateTStoreBillingRecords(tStoreBillingRecords);
    }

    /**
     * 批量删除门店账单收入支出
     *
     * @param ids 需要删除的门店账单收入支出ID
     * @return 结果
     */
    @Override
    public int deleteTStoreBillingRecordsByIds(Long[] ids) {
        return tStoreBillingRecordsMapper.deleteTStoreBillingRecordsByIds(ids);
    }

    /**
     * 删除门店账单收入支出信息
     *
     * @param id 门店账单收入支出ID
     * @return 结果
     */
    @Override
    public int deleteTStoreBillingRecordsById(Long id) {
        return tStoreBillingRecordsMapper.deleteTStoreBillingRecordsById(id);
    }
}
