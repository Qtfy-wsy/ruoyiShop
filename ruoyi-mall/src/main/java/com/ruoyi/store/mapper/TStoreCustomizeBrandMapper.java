package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreCustomizeBrand;

import java.util.List;

/**
 * 店铺自定义品牌列Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface TStoreCustomizeBrandMapper {
    /**
     * 查询店铺自定义品牌列
     *
     * @param id 店铺自定义品牌列ID
     * @return 店铺自定义品牌列
     */
    public TStoreCustomizeBrand selectTStoreCustomizeBrandById(Long id);

    /**
     * 查询店铺自定义品牌列列表
     *
     * @param tStoreCustomizeBrand 店铺自定义品牌列
     * @return 店铺自定义品牌列集合
     */
    public List<TStoreCustomizeBrand> selectTStoreCustomizeBrandList(TStoreCustomizeBrand tStoreCustomizeBrand);

    /**
     * 新增店铺自定义品牌列
     *
     * @param tStoreCustomizeBrand 店铺自定义品牌列
     * @return 结果
     */
    public int insertTStoreCustomizeBrand(TStoreCustomizeBrand tStoreCustomizeBrand);

    /**
     * 修改店铺自定义品牌列
     *
     * @param tStoreCustomizeBrand 店铺自定义品牌列
     * @return 结果
     */
    public int updateTStoreCustomizeBrand(TStoreCustomizeBrand tStoreCustomizeBrand);

    /**
     * 删除店铺自定义品牌列
     *
     * @param id 店铺自定义品牌列ID
     * @return 结果
     */
    public int deleteTStoreCustomizeBrandById(Long id);

    /**
     * 批量删除店铺自定义品牌列
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreCustomizeBrandByIds(Long[] ids);
}
