package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsLogisticsCompanyUse;

import java.util.List;

/**
 * 店铺使用的物流公司Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IOmsLogisticsCompanyUseService {
    /**
     * 查询店铺使用的物流公司
     *
     * @param id 店铺使用的物流公司ID
     * @return 店铺使用的物流公司
     */
    public OmsLogisticsCompanyUse selectOmsLogisticsCompanyUseById(Long id);

    /**
     * 查询店铺使用的物流公司列表
     *
     * @param omsLogisticsCompanyUse 店铺使用的物流公司
     * @return 店铺使用的物流公司集合
     */
    public List<OmsLogisticsCompanyUse> selectOmsLogisticsCompanyUseList(OmsLogisticsCompanyUse omsLogisticsCompanyUse);

    /**
     * 新增店铺使用的物流公司
     *
     * @param omsLogisticsCompanyUse 店铺使用的物流公司
     * @return 结果
     */
    public int insertOmsLogisticsCompanyUse(OmsLogisticsCompanyUse omsLogisticsCompanyUse);

    /**
     * 修改店铺使用的物流公司
     *
     * @param omsLogisticsCompanyUse 店铺使用的物流公司
     * @return 结果
     */
    public int updateOmsLogisticsCompanyUse(OmsLogisticsCompanyUse omsLogisticsCompanyUse);

    /**
     * 批量删除店铺使用的物流公司
     *
     * @param ids 需要删除的店铺使用的物流公司ID
     * @return 结果
     */
    public int deleteOmsLogisticsCompanyUseByIds(Long[] ids);

    /**
     * 删除店铺使用的物流公司信息
     *
     * @param id 店铺使用的物流公司ID
     * @return 结果
     */
    public int deleteOmsLogisticsCompanyUseById(Long id);


}
