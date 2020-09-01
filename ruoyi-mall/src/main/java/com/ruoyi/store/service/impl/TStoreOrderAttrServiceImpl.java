package com.ruoyi.store.service.impl;

import com.ruoyi.store.domain.TStoreOrderAttr;
import com.ruoyi.store.mapper.TStoreOrderAttrMapper;
import com.ruoyi.store.service.ITStoreOrderAttrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 门店订单附属信息Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class TStoreOrderAttrServiceImpl implements ITStoreOrderAttrService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreOrderAttrServiceImpl.class);
    @Autowired
    private TStoreOrderAttrMapper tStoreOrderAttrMapper;

    /**
     * 查询门店订单附属信息
     *
     * @param id 门店订单附属信息ID
     * @return 门店订单附属信息
     */
    @Override
    public TStoreOrderAttr selectTStoreOrderAttrById(Long id) {
        return tStoreOrderAttrMapper.selectTStoreOrderAttrById(id);
    }

    /**
     * 查询门店订单附属信息列表
     *
     * @param tStoreOrderAttr 门店订单附属信息
     * @return 门店订单附属信息
     */
    @Override
    public List<TStoreOrderAttr> selectTStoreOrderAttrList(TStoreOrderAttr tStoreOrderAttr) {
        return tStoreOrderAttrMapper.selectTStoreOrderAttrList(tStoreOrderAttr);
    }

    /**
     * 新增门店订单附属信息
     *
     * @param tStoreOrderAttr 门店订单附属信息
     * @return 结果
     */
    @Override
    public int insertTStoreOrderAttr(TStoreOrderAttr tStoreOrderAttr) {
        return tStoreOrderAttrMapper.insertTStoreOrderAttr(tStoreOrderAttr);
    }

    /**
     * 修改门店订单附属信息
     *
     * @param tStoreOrderAttr 门店订单附属信息
     * @return 结果
     */
    @Override
    public int updateTStoreOrderAttr(TStoreOrderAttr tStoreOrderAttr) {
        return tStoreOrderAttrMapper.updateTStoreOrderAttr(tStoreOrderAttr);
    }

    @Override
    public TStoreOrderAttr queryByStoreOrderId(long orderId) {
        return tStoreOrderAttrMapper.queryByStoreOrderId(orderId);
    }

    /**
     * 批量删除门店订单附属信息
     *
     * @param ids 需要删除的门店订单附属信息ID
     * @return 结果
     */
    @Override
    public int deleteTStoreOrderAttrByIds(Long[] ids) {
        return tStoreOrderAttrMapper.deleteTStoreOrderAttrByIds(ids);
    }

    /**
     * 删除门店订单附属信息信息
     *
     * @param id 门店订单附属信息ID
     * @return 结果
     */
    @Override
    public int deleteTStoreOrderAttrById(Long id) {
        return tStoreOrderAttrMapper.deleteTStoreOrderAttrById(id);
    }
}
