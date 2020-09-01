package com.ruoyi.order.service.impl;

import com.ruoyi.order.domain.OmsOrderAttr;
import com.ruoyi.order.mapper.OmsOrderAttrMapper;
import com.ruoyi.order.service.IOmsOrderAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 订单属性Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class OmsOrderAttrServiceImpl implements IOmsOrderAttrService {
    @Autowired
    private OmsOrderAttrMapper omsOrderAttrMapper;

    /**
     * 查询订单属性
     *
     * @param id 订单属性ID
     * @return 订单属性
     */
    @Override
    public OmsOrderAttr selectOmsOrderAttrById(Long id) {
        return omsOrderAttrMapper.selectOmsOrderAttrById(id);
    }


    @Override
    public OmsOrderAttr queryByOrderId(long orderId) {
        return omsOrderAttrMapper.queryByOrderId(orderId);
    }

    @Override
    public int saveOrderAttr(OmsOrderAttr orderAttr) {
        return omsOrderAttrMapper.insertOmsOrderAttr(orderAttr);
    }

    /**
     * 查询订单属性列表
     *
     * @param omsOrderAttr 订单属性
     * @return 订单属性
     */
    @Override
    public List<OmsOrderAttr> selectOmsOrderAttrList(OmsOrderAttr omsOrderAttr) {
        return omsOrderAttrMapper.selectOmsOrderAttrList(omsOrderAttr);
    }

    /**
     * 新增订单属性
     *
     * @param omsOrderAttr 订单属性
     * @return 结果
     */
    @Override
    public int insertOmsOrderAttr(OmsOrderAttr omsOrderAttr) {
        return omsOrderAttrMapper.insertOmsOrderAttr(omsOrderAttr);
    }

    /**
     * 修改订单属性
     *
     * @param omsOrderAttr 订单属性
     * @return 结果
     */
    @Override
    public int updateOmsOrderAttr(OmsOrderAttr omsOrderAttr) {
        return omsOrderAttrMapper.updateOmsOrderAttr(omsOrderAttr);
    }

    /**
     * 批量删除订单属性
     *
     * @param ids 需要删除的订单属性ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderAttrByIds(Long[] ids) {
        return omsOrderAttrMapper.deleteOmsOrderAttrByIds(ids);
    }

    /**
     * 删除订单属性信息
     *
     * @param id 订单属性ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderAttrById(Long id) {
        return omsOrderAttrMapper.deleteOmsOrderAttrById(id);
    }
}
