package com.ruoyi.store.service.impl;

import com.ruoyi.store.domain.TStoreRedEnvelope;
import com.ruoyi.store.mapper.TStoreRedEnvelopeMapper;
import com.ruoyi.store.service.ITStoreRedEnvelopeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店红包Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Service
public class TStoreRedEnvelopeServiceImpl implements ITStoreRedEnvelopeService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreRedEnvelopeServiceImpl.class);
    @Autowired
    private TStoreRedEnvelopeMapper tStoreRedEnvelopeMapper;

    /**
     * 查询门店红包
     *
     * @param id 门店红包ID
     * @return 门店红包
     */
    @Override
    public TStoreRedEnvelope selectTStoreRedEnvelopeById(Long id) {
        return tStoreRedEnvelopeMapper.selectTStoreRedEnvelopeById(id);
    }

    /**
     * 查询门店红包列表
     *
     * @param tStoreRedEnvelope 门店红包
     * @return 门店红包
     */
    @Override
    public List<TStoreRedEnvelope> selectTStoreRedEnvelopeList(TStoreRedEnvelope tStoreRedEnvelope) {
        return tStoreRedEnvelopeMapper.selectTStoreRedEnvelopeList(tStoreRedEnvelope);
    }

    /**
     * 新增门店红包
     *
     * @param tStoreRedEnvelope 门店红包
     * @return 结果
     */
    @Override
    public int insertTStoreRedEnvelope(TStoreRedEnvelope tStoreRedEnvelope) {
        return tStoreRedEnvelopeMapper.insertTStoreRedEnvelope(tStoreRedEnvelope);
    }

    /**
     * 修改门店红包
     *
     * @param tStoreRedEnvelope 门店红包
     * @return 结果
     */
    @Override
    public int updateTStoreRedEnvelope(TStoreRedEnvelope tStoreRedEnvelope) {
        return tStoreRedEnvelopeMapper.updateTStoreRedEnvelope(tStoreRedEnvelope);
    }

    /**
     * 批量删除门店红包
     *
     * @param ids 需要删除的门店红包ID
     * @return 结果
     */
    @Override
    public int deleteTStoreRedEnvelopeByIds(Long[] ids) {
        return tStoreRedEnvelopeMapper.deleteTStoreRedEnvelopeByIds(ids);
    }

    /**
     * 删除门店红包信息
     *
     * @param id 门店红包ID
     * @return 结果
     */
    @Override
    public int deleteTStoreRedEnvelopeById(Long id) {
        return tStoreRedEnvelopeMapper.deleteTStoreRedEnvelopeById(id);
    }
}
