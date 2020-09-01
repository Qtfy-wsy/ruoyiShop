package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsAttribute;

import java.util.List;

/**
 * 商品属性Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsAttributeMapper {
    /**
     * 查询商品属性
     *
     * @param id 商品属性ID
     * @return 商品属性
     */
    public PmsAttribute selectPmsAttributeById(String id);

    /**
     * 查询商品属性列表
     *
     * @param pmsAttribute 商品属性
     * @return 商品属性集合
     */
    public List<PmsAttribute> selectPmsAttributeList(PmsAttribute pmsAttribute);

    /**
     * 新增商品属性
     *
     * @param pmsAttribute 商品属性
     * @return 结果
     */
    public int insertPmsAttribute(PmsAttribute pmsAttribute);

    /**
     * 修改商品属性
     *
     * @param pmsAttribute 商品属性
     * @return 结果
     */
    public int updatePmsAttribute(PmsAttribute pmsAttribute);

    /**
     * 删除商品属性
     *
     * @param id 商品属性ID
     * @return 结果
     */
    public int deletePmsAttributeById(String id);

    /**
     * 批量删除商品属性
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsAttributeByIds(String[] ids);
}
