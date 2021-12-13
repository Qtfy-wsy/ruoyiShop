package com.ruoyi.store.service;

import com.ruoyi.store.domain.TStoreSignedCategory;

import java.util.List;

/**
 * 店铺的签约分类Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ITStoreSignedCategoryService {
    /**
     * 查询店铺的签约分类
     *
     * @param id 店铺的签约分类ID
     * @return 店铺的签约分类
     */
    public TStoreSignedCategory selectTStoreSignedCategoryById(Long id);

    /**
     * 查询店铺的签约分类列表
     *
     * @param tStoreSignedCategory 店铺的签约分类
     * @return 店铺的签约分类集合
     */
    public List<TStoreSignedCategory> selectTStoreSignedCategoryList(TStoreSignedCategory tStoreSignedCategory);

    /**
     * 查询店铺的所有签约分类
     *
     * @param storeId 店铺id
     * @return 返回店铺所有分类
     */
    List<TStoreSignedCategory> queryAllSignedCategorys(long storeId);

    /**
     * 批量插入店铺签约分类
     *
     * @param list 店铺签约分类集合
     * @return 添加返回码
     */
    int addSignedCategory(List<TStoreSignedCategory> list);

    /**
     * 删除店铺签约分类
     *
     * @param storeId 店铺id
     * @return 删除返回码
     */
    int deleteSignedCategory(long storeId);

    /**
     * 根据店铺id和分类id删除签约分类
     *
     * @param storeId 店铺id
     * @param cateId  分类id
     * @return 删除返回码
     */
    int deleteSingedCategoryById(long storeId, long cateId);

    /**
     * 添加签约分类admin端用
     *
     * @param state       是否直接生效
     * @param categoryIds 分类id数组
     * @param storeId     店铺id
     * @return 返回添加码
     */
    int addSignedCategoryAdmin(long[] categoryIds, long storeId, boolean state);

    /**
     * 更新签约分类
     *
     * @return
     */
    int updateState(List<Long> ids, boolean state);

    /**
     * 新增店铺的签约分类
     *
     * @param tStoreSignedCategory 店铺的签约分类
     * @return 结果
     */
    public int insertTStoreSignedCategory(TStoreSignedCategory tStoreSignedCategory);

    /**
     * 修改店铺的签约分类
     *
     * @param tStoreSignedCategory 店铺的签约分类
     * @return 结果
     */
    public int updateTStoreSignedCategory(TStoreSignedCategory tStoreSignedCategory);

    /**
     * 批量删除店铺的签约分类
     *
     * @param ids 需要删除的店铺的签约分类ID
     * @return 结果
     */
    public int deleteTStoreSignedCategoryByIds(Long[] ids);

    /**
     * 删除店铺的签约分类信息
     *
     * @param id 店铺的签约分类ID
     * @return 结果
     */
    public int deleteTStoreSignedCategoryById(Long id);
}
