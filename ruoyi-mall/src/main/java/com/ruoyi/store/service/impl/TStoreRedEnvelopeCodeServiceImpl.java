package com.ruoyi.store.service.impl;

import com.ruoyi.store.domain.TStoreRedEnvelopeCode;
import com.ruoyi.store.mapper.TStoreRedEnvelopeCodeMapper;
import com.ruoyi.store.service.ITStoreRedEnvelopeCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店红包卷吗Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@Service
public class TStoreRedEnvelopeCodeServiceImpl implements ITStoreRedEnvelopeCodeService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreRedEnvelopeCodeServiceImpl.class);
    @Autowired
    private TStoreRedEnvelopeCodeMapper tStoreRedEnvelopeCodeMapper;

    /**
     * 查询门店红包卷吗
     *
     * @param id 门店红包卷吗ID
     * @return 门店红包卷吗
     */
    @Override
    public TStoreRedEnvelopeCode selectTStoreRedEnvelopeCodeById(Long id) {
        return tStoreRedEnvelopeCodeMapper.selectTStoreRedEnvelopeCodeById(id);
    }

    /**
     * 查询门店红包卷吗列表
     *
     * @param tStoreRedEnvelopeCode 门店红包卷吗
     * @return 门店红包卷吗
     */
    @Override
    public List<TStoreRedEnvelopeCode> selectTStoreRedEnvelopeCodeList(TStoreRedEnvelopeCode tStoreRedEnvelopeCode) {
        return tStoreRedEnvelopeCodeMapper.selectTStoreRedEnvelopeCodeList(tStoreRedEnvelopeCode);
    }

    /**
     * 新增门店红包卷吗
     *
     * @param tStoreRedEnvelopeCode 门店红包卷吗
     * @return 结果
     */
    @Override
    public int insertTStoreRedEnvelopeCode(TStoreRedEnvelopeCode tStoreRedEnvelopeCode) {
        return tStoreRedEnvelopeCodeMapper.insertTStoreRedEnvelopeCode(tStoreRedEnvelopeCode);
    }

    /**
     * 修改门店红包卷吗
     *
     * @param tStoreRedEnvelopeCode 门店红包卷吗
     * @return 结果
     */
    @Override
    public int updateTStoreRedEnvelopeCode(TStoreRedEnvelopeCode tStoreRedEnvelopeCode) {
        return tStoreRedEnvelopeCodeMapper.updateTStoreRedEnvelopeCode(tStoreRedEnvelopeCode);
    }

    /**
     * 批量删除门店红包卷吗
     *
     * @param ids 需要删除的门店红包卷吗ID
     * @return 结果
     */
    @Override
    public int deleteTStoreRedEnvelopeCodeByIds(Long[] ids) {
        return tStoreRedEnvelopeCodeMapper.deleteTStoreRedEnvelopeCodeByIds(ids);
    }

    /**
     * 删除门店红包卷吗信息
     *
     * @param id 门店红包卷吗ID
     * @return 结果
     */
    @Override
    public int deleteTStoreRedEnvelopeCodeById(Long id) {
        return tStoreRedEnvelopeCodeMapper.deleteTStoreRedEnvelopeCodeById(id);
    }
}
