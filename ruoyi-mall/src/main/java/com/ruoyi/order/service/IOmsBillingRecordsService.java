package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsBillingRecords;

import java.util.List;

/**
 * 账单记录Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IOmsBillingRecordsService {
    /**
     * 查询账单记录
     *
     * @param id 账单记录ID
     * @return 账单记录
     */
    public OmsBillingRecords selectOmsBillingRecordsById(Long id);

    /**
     * 查询账单记录列表
     *
     * @param omsBillingRecords 账单记录
     * @return 账单记录集合
     */
    public List<OmsBillingRecords> selectOmsBillingRecordsList(OmsBillingRecords omsBillingRecords);

    /**
     * 新增账单记录
     *
     * @param omsBillingRecords 账单记录
     * @return 结果
     */
    public int insertOmsBillingRecords(OmsBillingRecords omsBillingRecords);

    /**
     * 修改账单记录
     *
     * @param omsBillingRecords 账单记录
     * @return 结果
     */
    public int updateOmsBillingRecords(OmsBillingRecords omsBillingRecords);

    /**
     * 批量删除账单记录
     *
     * @param ids 需要删除的账单记录ID
     * @return 结果
     */
    public int deleteOmsBillingRecordsByIds(Long[] ids);

    /**
     * 删除账单记录信息
     *
     * @param id 账单记录ID
     * @return 结果
     */
    public int deleteOmsBillingRecordsById(Long id);
}
