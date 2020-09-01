package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreSignedCategory;

import java.util.List;
import java.util.Map;

/**
 * 店铺的签约分类Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface TStoreSignedCategoryMapper {
    /**
     * 查询店铺的签约分类
     *
     * @param id 店铺的签约分类ID
     * @return 店铺的签约分类
     */
    public TStoreSignedCategory selectTStoreSignedCategoryById(Long id);

    /**
     * 查询店铺签约分类
     *
     * @param storeId 店铺id
     * @return 返回店铺签约分类
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
     * @param map 店铺id 分类id
     * @return 删除返回码
     */

    int deleteSingedCategoryById(Map<String, Object> map);

    /**
     * 更新签约分类
     * 管理端和商户管理端使用
     *
     * @param map 店铺id 分类id
     * @return 删除返回码
     */

    int update(Map<String, Object> map);

    /**
     * 查询店铺签约分类
     *
     * @param storeId 店铺id
     * @return 返回店铺签约分类
     */

    List<TStoreSignedCategory> querySignedCategorysPageList(long storeId);

    /**
     * 查询店铺的签约分类列表
     *
     * @param tStoreSignedCategory 店铺的签约分类
     * @return 店铺的签约分类集合
     */
    public List<TStoreSignedCategory> selectTStoreSignedCategoryList(TStoreSignedCategory tStoreSignedCategory);

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
     * 删除店铺的签约分类
     *
     * @param id 店铺的签约分类ID
     * @return 结果
     */
    public int deleteTStoreSignedCategoryById(Long id);

    /**
     * 批量删除店铺的签约分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreSignedCategoryByIds(Long[] ids);
}
