package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsOrderAttr;

import java.util.List;

/**
 * 订单属性Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IOmsOrderAttrService {
    /**
     * 查询订单属性
     *
     * @param id 订单属性ID
     * @return 订单属性
     */
    public OmsOrderAttr selectOmsOrderAttrById(Long id);

    /**
     * 根据订单id查询订单的附属信息
     *
     * @param orderId 订单id
     * @return 返回订单附属信息
     */
    OmsOrderAttr queryByOrderId(long orderId);

    /**
     * 保存订单附属信息
     *
     * @param orderAttr 订单附属信息
     * @return 成功返回>0 失败返回0
     */
    int saveOrderAttr(OmsOrderAttr orderAttr);

    /**
     * 查询订单属性列表
     *
     * @param omsOrderAttr 订单属性
     * @return 订单属性集合
     */
    public List<OmsOrderAttr> selectOmsOrderAttrList(OmsOrderAttr omsOrderAttr);

    /**
     * 新增订单属性
     *
     * @param omsOrderAttr 订单属性
     * @return 结果
     */
    public int insertOmsOrderAttr(OmsOrderAttr omsOrderAttr);

    /**
     * 修改订单属性
     *
     * @param omsOrderAttr 订单属性
     * @return 结果
     */
    public int updateOmsOrderAttr(OmsOrderAttr omsOrderAttr);

    /**
     * 批量删除订单属性
     *
     * @param ids 需要删除的订单属性ID
     * @return 结果
     */
    public int deleteOmsOrderAttrByIds(Long[] ids);

    /**
     * 删除订单属性信息
     *
     * @param id 订单属性ID
     * @return 结果
     */
    public int deleteOmsOrderAttrById(Long id);
}
