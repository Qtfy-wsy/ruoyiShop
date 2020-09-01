package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsOrderAttr;

import java.util.List;

/**
 * 订单属性Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface OmsOrderAttrMapper {
    /**
     * 查询订单属性
     *
     * @param id 订单属性ID
     * @return 订单属性
     */
    public OmsOrderAttr selectOmsOrderAttrById(Long id);

    OmsOrderAttr queryByOrderId(long orderId);

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
     * 删除订单属性
     *
     * @param id 订单属性ID
     * @return 结果
     */
    public int deleteOmsOrderAttrById(Long id);

    /**
     * 批量删除订单属性
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsOrderAttrByIds(Long[] ids);
}
