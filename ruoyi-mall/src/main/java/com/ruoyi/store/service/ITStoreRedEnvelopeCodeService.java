package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreRedEnvelopeCode;

import java.util.List;

/**
 * 门店红包卷吗Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStoreRedEnvelopeCodeService {
    /**
     * 查询门店红包卷吗
     *
     * @param id 门店红包卷吗ID
     * @return 门店红包卷吗
     */
    public TStoreRedEnvelopeCode selectTStoreRedEnvelopeCodeById(Long id);

    /**
     * 查询门店红包卷吗列表
     *
     * @param tStoreRedEnvelopeCode 门店红包卷吗
     * @return 门店红包卷吗集合
     */
    public List<TStoreRedEnvelopeCode> selectTStoreRedEnvelopeCodeList(TStoreRedEnvelopeCode tStoreRedEnvelopeCode);

    /**
     * 新增门店红包卷吗
     *
     * @param tStoreRedEnvelopeCode 门店红包卷吗
     * @return 结果
     */
    public int insertTStoreRedEnvelopeCode(TStoreRedEnvelopeCode tStoreRedEnvelopeCode);

    /**
     * 修改门店红包卷吗
     *
     * @param tStoreRedEnvelopeCode 门店红包卷吗
     * @return 结果
     */
    public int updateTStoreRedEnvelopeCode(TStoreRedEnvelopeCode tStoreRedEnvelopeCode);

    /**
     * 批量删除门店红包卷吗
     *
     * @param ids 需要删除的门店红包卷吗ID
     * @return 结果
     */
    public int deleteTStoreRedEnvelopeCodeByIds(Long[] ids);

    /**
     * 删除门店红包卷吗信息
     *
     * @param id 门店红包卷吗ID
     * @return 结果
     */
    public int deleteTStoreRedEnvelopeCodeById(Long id);
}
