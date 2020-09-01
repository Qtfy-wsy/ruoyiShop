package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreCategory;

import java.util.List;
import java.util.function.Consumer;

/**
 * 店铺分类Service接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface ITStoreCategoryService {
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
     * @param parentId 父级商品分类id
     * @param storeId  店铺id
     * @return 商品分类
     */
    List<TStoreCategory> querySpuCategoryByParentId(long parentId, long storeId);

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
     * @param id      商品分类id
     * @param storeId 店铺id
     * @return 商品分类
     */
    TStoreCategory querySpuCategoryById(Long id, long storeId);

    /**
     * 更新商品分类
     *
     * @param spuCategory 商品分类
     * @return 成功返回1，失败返回0
     */
    int updateSpuCategory(TStoreCategory spuCategory);

    /**
     * 根据店铺id查询所有商品分类
     *
     * @param storeId 店铺id
     * @return 商品分类
     */
    List<TStoreCategory> queryAllSpuCategory(long storeId);

    /**
     * 删除商品分类
     *
     * @param id      商品分类id
     * @param storeId 店铺id
     * @param grade   商品分类jibie
     * @return -1 有子分类不能删除，  1 成功， 0 失败
     */
    int deleteSpuCategory(long id, long storeId, int grade, Consumer<Long[]> consumer);

    /**
     * 根据ID查询所有父集
     *
     * @param id      分类ID
     * @param storeId 店铺ID
     * @return 包含自身的所有父集
     */
    List<TStoreCategory> queryAllParentSpuCategoryById(Long id, Long storeId);

    /**
     * 根据店铺id查询所有商品分类(根据parentId排好序的)
     *
     * @param storeId 店铺id
     * @return 商品分类
     */
    List<TStoreCategory> querySortedAllSpuCategory(long storeId);

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
     * 批量删除店铺分类
     *
     * @param ids 需要删除的店铺分类ID
     * @return 结果
     */
    public int deleteTStoreCategoryByIds(Long[] ids);

    /**
     * 删除店铺分类信息
     *
     * @param id 店铺分类ID
     * @return 结果
     */
    public int deleteTStoreCategoryById(Long id);
}
