package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsCommissionRecords;

import java.util.List;
import java.util.Map;

/**
 * 佣金记录Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface OmsCommissionRecordsMapper {
    /**
     * 查询佣金记录
     *
     * @param id 佣金记录ID
     * @return 佣金记录
     */
    public OmsCommissionRecords selectOmsCommissionRecordsById(Long id);

    /**
     * 查询佣金记录列表
     *
     * @param omsCommissionRecords 佣金记录
     * @return 佣金记录集合
     */
    public List<OmsCommissionRecords> selectOmsCommissionRecordsList(OmsCommissionRecords omsCommissionRecords);

    public String queryCommissionMoney(Map<String, Object> params);

    List<OmsCommissionRecords> queryCommissionRecords(Map<String, Object> params);

    /**
     * 查询佣金记录数量
     *
     * @param params 参数
     * @return 数量
     */
    int queryCommissionRecordsCount(Map<String, Object> params);

    /**
     * 新增佣金记录
     *
     * @param omsCommissionRecords 佣金记录
     * @return 结果
     */
    public int insertOmsCommissionRecords(OmsCommissionRecords omsCommissionRecords);

    /**
     * 修改佣金记录
     *
     * @param omsCommissionRecords 佣金记录
     * @return 结果
     */
    public int updateOmsCommissionRecords(OmsCommissionRecords omsCommissionRecords);

    /**
     * 删除佣金记录
     *
     * @param id 佣金记录ID
     * @return 结果
     */
    public int deleteOmsCommissionRecordsById(Long id);

    /**
     * 批量删除佣金记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsCommissionRecordsByIds(Long[] ids);
}
