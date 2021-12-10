package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreCategory;

import java.util.List;
import java.util.Map;

/**
 * 店铺分类Mapper接口
 *
 * @author 商城
 */
public interface TStoreCategoryMapper {
    /**
     * 查询店铺分类
     *
     * @param id 店铺分类ID
     * @return 店铺分类
     */
    public TStoreCategory selectTStoreCategoryById(Long id);

    /**
     * 根据父级商品分类id查询商品分类
     *
     * @param params 父级商品分类id及店铺id
     * @return 商品分类
     */

    List<TStoreCategory> querySpuCategoryByParentId(Map<String, Object> params);

    /**
     * 根据店铺id查询所有商品分类
     *
     * @param storeId 店铺id
     * @return 商品分类
     */

    List<TStoreCategory> queryAllSpuCategory(Long storeId);

    /**
     * 添加商品分类
     *
     * @param spuCategory 商品分类
     * @return 成功返回1，失败返回0
     */

    int addSpuCategory(TStoreCategory spuCategory);

    /**
     * 根据商品分类id及店铺id查询商品分类
     *
     * @param params 商品分类id及店铺id
     * @return 商品分类
     */

    TStoreCategory querySpuCategoryById(Map<String, Object> params);

    /**
     * 更新商品分类
     *
     * @param spuCategory 商品分类
     * @return 成功返回1，失败返回0
     */

    int updateSpuCategory(TStoreCategory spuCategory);

    /**
     * 删除商品分类
     *
     * @param params 商品分类id及店铺id
     * @return 成功返回1，失败返回0
     */

    int deleteSpuCategory(Map<String, Object> params);

    /**
     * 查询店铺分类列表
     *
     * @param tStoreCategory 店铺分类
     * @return 店铺分类集合
     */
    public List<TStoreCategory> selectTStoreCategoryList(TStoreCategory tStoreCategory);

    /**
     * 新增店铺分类
     *
     * @param tStoreCategory 店铺分类
     * @return 结果
     */
    public int insertTStoreCategory(TStoreCategory tStoreCategory);

    /**
     * 修改店铺分类
     *
     * @param tStoreCategory 店铺分类
     * @return 结果
     */
    public int updateTStoreCategory(TStoreCategory tStoreCategory);

    /**
     * 删除店铺分类
     *
     * @param id 店铺分类ID
     * @return 结果
     */
    public int deleteTStoreCategoryById(Long id);

    /**
     * 批量删除店铺分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreCategoryByIds(Long[] ids);
}
