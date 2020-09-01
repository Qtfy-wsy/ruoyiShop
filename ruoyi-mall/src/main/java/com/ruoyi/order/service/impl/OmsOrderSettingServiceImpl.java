package com.ruoyi.order.service.impl;

import com.ruoyi.order.domain.OmsOrderSetting;
import com.ruoyi.order.mapper.OmsOrderSettingMapper;
import com.ruoyi.order.service.IOmsOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 订单设置Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@Service
public class OmsOrderSettingServiceImpl implements IOmsOrderSettingService {
    @Autowired
    private OmsOrderSettingMapper omsOrderSettingMapper;

    /**
     * 查询订单设置
     *
     * @param id 订单设置ID
     * @return 订单设置
     */
    @Override
    public OmsOrderSetting selectOmsOrderSettingById(Long id) {
        return omsOrderSettingMapper.selectOmsOrderSettingById(id);
    }

    @Override
    public boolean isCanBackOrder() {


        OmsOrderSetting orderSetting = this.selectOmsOrderSettingById(1L);

        return Objects.nonNull(orderSetting) ? orderSetting.isCanBackOrder() : false;
    }

    /**
     * 查询订单设置列表
     *
     * @param omsOrderSetting 订单设置
     * @return 订单设置
     */
    @Override
    public List<OmsOrderSetting> selectOmsOrderSettingList(OmsOrderSetting omsOrderSetting) {
        return omsOrderSettingMapper.selectOmsOrderSettingList(omsOrderSetting);
    }

    /**
     * 新增订单设置
     *
     * @param omsOrderSetting 订单设置
     * @return 结果
     */
    @Override
    public int insertOmsOrderSetting(OmsOrderSetting omsOrderSetting) {
        return omsOrderSettingMapper.insertOmsOrderSetting(omsOrderSetting);
    }

    /**
     * 修改订单设置
     *
     * @param omsOrderSetting 订单设置
     * @return 结果
     */
    @Override
    public int updateOmsOrderSetting(OmsOrderSetting omsOrderSetting) {
        return omsOrderSettingMapper.updateOmsOrderSetting(omsOrderSetting);
    }

    /**
     * 批量删除订单设置
     *
     * @param ids 需要删除的订单设置ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderSettingByIds(Long[] ids) {
        return omsOrderSettingMapper.deleteOmsOrderSettingByIds(ids);
    }

    /**
     * 删除订单设置信息
     *
     * @param id 订单设置ID
     * @return 结果
     */
    @Override
    public int deleteOmsOrderSettingById(Long id) {
        return omsOrderSettingMapper.deleteOmsOrderSettingById(id);
    }
}
