package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsShippingMethodFreeshipArea;

import java.util.List;

/**
 * 运费方式包邮关联的区域Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsShippingMethodFreeshipAreaService {
    /**
     * 查询运费方式包邮关联的区域
     *
     * @param id 运费方式包邮关联的区域ID
     * @return 运费方式包邮关联的区域
     */
    public PmsShippingMethodFreeshipArea selectPmsShippingMethodFreeshipAreaById(Long id);

    /**
     * 查询运费方式包邮关联的区域列表
     *
     * @param pmsShippingMethodFreeshipArea 运费方式包邮关联的区域
     * @return 运费方式包邮关联的区域集合
     */
    public List<PmsShippingMethodFreeshipArea> selectPmsShippingMethodFreeshipAreaList(PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea);

    /**
     * 新增运费方式包邮关联的区域
     *
     * @param pmsShippingMethodFreeshipArea 运费方式包邮关联的区域
     * @return 结果
     */
    public int insertPmsShippingMethodFreeshipArea(PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea);

    /**
     * 修改运费方式包邮关联的区域
     *
     * @param pmsShippingMethodFreeshipArea 运费方式包邮关联的区域
     * @return 结果
     */
    public int updatePmsShippingMethodFreeshipArea(PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea);

    /**
     * 批量删除运费方式包邮关联的区域
     *
     * @param ids 需要删除的运费方式包邮关联的区域ID
     * @return 结果
     */
    public int deletePmsShippingMethodFreeshipAreaByIds(Long[] ids);

    /**
     * 删除运费方式包邮关联的区域信息
     *
     * @param id 运费方式包邮关联的区域ID
     * @return 结果
     */
    public int deletePmsShippingMethodFreeshipAreaById(Long id);

    /**
     * 添加包邮运费关联的区域
     *
     * @param shippingMethodFreeShipAreas 包邮运费关联的区域
     */
    void addShippingMethodFreeShipAreas(List<PmsShippingMethodFreeshipArea> shippingMethodFreeShipAreas);

    /**
     * 根据包邮运费模版id查询区域
     *
     * @param methodId 包邮运费模版id
     * @return 返回包邮运费模版的区域
     */
    List<PmsShippingMethodFreeshipArea> queryByMethodId(long methodId);

    /**
     * 根据模版id删除包邮运费模版地区
     *
     * @param templateId 模版id
     */
    void deleteByTemplateId(long templateId);

}
