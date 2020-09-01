package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreRedEnvelope;

import java.util.List;

/**
 * 门店红包Service接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface ITStoreRedEnvelopeService {
    /**
     * 查询门店红包
     *
     * @param id 门店红包ID
     * @return 门店红包
     */
    public TStoreRedEnvelope selectTStoreRedEnvelopeById(Long id);

    /**
     * 查询门店红包列表
     *
     * @param tStoreRedEnvelope 门店红包
     * @return 门店红包集合
     */
    public List<TStoreRedEnvelope> selectTStoreRedEnvelopeList(TStoreRedEnvelope tStoreRedEnvelope);

    /**
     * 新增门店红包
     *
     * @param tStoreRedEnvelope 门店红包
     * @return 结果
     */
    public int insertTStoreRedEnvelope(TStoreRedEnvelope tStoreRedEnvelope);

    /**
     * 修改门店红包
     *
     * @param tStoreRedEnvelope 门店红包
     * @return 结果
     */
    public int updateTStoreRedEnvelope(TStoreRedEnvelope tStoreRedEnvelope);

    /**
     * 批量删除门店红包
     *
     * @param ids 需要删除的门店红包ID
     * @return 结果
     */
    public int deleteTStoreRedEnvelopeByIds(Long[] ids);

    /**
     * 删除门店红包信息
     *
     * @param id 门店红包ID
     * @return 结果
     */
    public int deleteTStoreRedEnvelopeById(Long id);
}
