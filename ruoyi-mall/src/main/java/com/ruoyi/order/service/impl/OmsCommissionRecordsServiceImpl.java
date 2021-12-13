package com.ruoyi.order.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.order.domain.OmsCommissionRecords;
import com.ruoyi.order.mapper.OmsCommissionRecordsMapper;
import com.ruoyi.order.service.IOmsCommissionRecordsService;
import com.ruoyi.util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 佣金记录Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class OmsCommissionRecordsServiceImpl implements IOmsCommissionRecordsService {
    @Autowired
    private OmsCommissionRecordsMapper omsCommissionRecordsMapper;

    /**
     * 查询佣金记录
     *
     * @param id 佣金记录ID
     * @return 佣金记录
     */
    @Override
    public OmsCommissionRecords selectOmsCommissionRecordsById(Long id) {
        return omsCommissionRecordsMapper.selectOmsCommissionRecordsById(id);
    }

    @Override
    public PageHelper<OmsCommissionRecords> queryCommissionRecords(PageHelper<OmsCommissionRecords> pageHelper, OmsCommissionRecords.QueryCriteria queryCriteria) {
        return pageHelper.setListDates(omsCommissionRecordsMapper.queryCommissionRecords(pageHelper.getQueryParams(queryCriteria.getQueryMap(), omsCommissionRecordsMapper.queryCommissionRecordsCount(queryCriteria.getQueryMap()))));
    }
    @Override
    public String queryCommissionMoney(Map<String, Object> params){
        return omsCommissionRecordsMapper.queryCommissionMoney(params);
    }
    /**
     * 查询佣金记录列表
     *
     * @param omsCommissionRecords 佣金记录
     * @return 佣金记录
     */
    @Override
    public List<OmsCommissionRecords> selectOmsCommissionRecordsList(OmsCommissionRecords omsCommissionRecords) {
        return omsCommissionRecordsMapper.selectOmsCommissionRecordsList(omsCommissionRecords);
    }

    /**
     * 新增佣金记录
     *
     * @param omsCommissionRecords 佣金记录
     * @return 结果
     */
    @Override
    public int insertOmsCommissionRecords(OmsCommissionRecords omsCommissionRecords) {
        omsCommissionRecords.setCreateTime(DateUtils.getNowDate());
        return omsCommissionRecordsMapper.insertOmsCommissionRecords(omsCommissionRecords);
    }

    /**
     * 修改佣金记录
     *
     * @param omsCommissionRecords 佣金记录
     * @return 结果
     */
    @Override
    public int updateOmsCommissionRecords(OmsCommissionRecords omsCommissionRecords) {
        return omsCommissionRecordsMapper.updateOmsCommissionRecords(omsCommissionRecords);
    }

    /**
     * 批量删除佣金记录
     *
     * @param ids 需要删除的佣金记录ID
     * @return 结果
     */
    @Override
    public int deleteOmsCommissionRecordsByIds(Long[] ids) {
        return omsCommissionRecordsMapper.deleteOmsCommissionRecordsByIds(ids);
    }

    /**
     * 删除佣金记录信息
     *
     * @param id 佣金记录ID
     * @return 结果
     */
    @Override
    public int deleteOmsCommissionRecordsById(Long id) {
        return omsCommissionRecordsMapper.deleteOmsCommissionRecordsById(id);
    }
}
