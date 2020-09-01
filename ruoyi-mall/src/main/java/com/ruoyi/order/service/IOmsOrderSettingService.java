package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsOrderSetting;

import java.util.List;

/**
 * 订单设置Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IOmsOrderSettingService {
    /**
     * 查询订单设置
     *
     * @param id 订单设置ID
     * @return 订单设置
     */
    public OmsOrderSetting selectOmsOrderSettingById(Long id);

    /**
     * 是否可以退单
     *
     * @return 可以返回true  不可以返回false
     */
    boolean isCanBackOrder();

    /**
     * 查询订单设置列表
     *
     * @param omsOrderSetting 订单设置
     * @return 订单设置集合
     */
    public List<OmsOrderSetting> selectOmsOrderSettingList(OmsOrderSetting omsOrderSetting);

    /**
     * 新增订单设置
     *
     * @param omsOrderSetting 订单设置
     * @return 结果
     */
    public int insertOmsOrderSetting(OmsOrderSetting omsOrderSetting);

    /**
     * 修改订单设置
     *
     * @param omsOrderSetting 订单设置
     * @return 结果
     */
    public int updateOmsOrderSetting(OmsOrderSetting omsOrderSetting);

    /**
     * 批量删除订单设置
     *
     * @param ids 需要删除的订单设置ID
     * @return 结果
     */
    public int deleteOmsOrderSettingByIds(Long[] ids);

    /**
     * 删除订单设置信息
     *
     * @param id 订单设置ID
     * @return 结果
     */
    public int deleteOmsOrderSettingById(Long id);
}
