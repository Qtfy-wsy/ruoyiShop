package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreBillingRecords;

import java.util.List;

/**
 * 门店账单收入支出Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface TStoreBillingRecordsMapper {
    /**
     * 查询门店账单收入支出
     *
     * @param id 门店账单收入支出ID
     * @return 门店账单收入支出
     */
    public TStoreBillingRecords selectTStoreBillingRecordsById(Long id);

    /**
     * 查询门店账单收入支出列表
     *
     * @param tStoreBillingRecords 门店账单收入支出
     * @return 门店账单收入支出集合
     */
    public List<TStoreBillingRecords> selectTStoreBillingRecordsList(TStoreBillingRecords tStoreBillingRecords);

    /**
     * 新增门店账单收入支出
     *
     * @param tStoreBillingRecords 门店账单收入支出
     * @return 结果
     */
    public int insertTStoreBillingRecords(TStoreBillingRecords tStoreBillingRecords);

    /**
     * 修改门店账单收入支出
     *
     * @param tStoreBillingRecords 门店账单收入支出
     * @return 结果
     */
    public int updateTStoreBillingRecords(TStoreBillingRecords tStoreBillingRecords);

    /**
     * 删除门店账单收入支出
     *
     * @param id 门店账单收入支出ID
     * @return 结果
     */
    public int deleteTStoreBillingRecordsById(Long id);

    /**
     * 批量删除门店账单收入支出
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreBillingRecordsByIds(Long[] ids);
}
