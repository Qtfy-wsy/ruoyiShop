package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsLogisticsCompanyUse;

import java.util.List;
import java.util.Map;

/**
 * 店铺使用的物流公司Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface OmsLogisticsCompanyUseMapper {
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
     * 删除店铺使用的物流公司
     *
     * @param id 店铺使用的物流公司ID
     * @return 结果
     */
    public int deleteOmsLogisticsCompanyUseById(Long id);

    /**
     * 批量删除店铺使用的物流公司
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsLogisticsCompanyUseByIds(Long[] ids);

    int addLogisticsCompanyUse(Map<String, Object> params);

    /**
     * 删除店铺使用的物流公司
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */
    int deleteLogisticsCompanyUse(Map<String, Object> params);

    /**
     * 查询店铺使用物流公司的总数
     *
     * @param storeId 店铺id
     * @return 返回店铺使用物流公司的总数
     */
    int queryLogisticsCompanyUseCount(long storeId);

    List<OmsLogisticsCompanyUse> queryLogisticsCompanyUses(long storeId);

}
