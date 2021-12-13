package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsCategory;

import java.util.List;

/**
 * 商品分类Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IPmsCategoryService {
    /**
     * 查询商品分类
     *
     * @param id 商品分类ID
     * @return 商品分类
     */
    public PmsCategory selectPmsCategoryById(Long id);

    /**
     * 查询商品分类列表
     *
     * @param pmsCategory 商品分类
     * @return 商品分类集合
     */
    public List<PmsCategory> selectPmsCategoryList(PmsCategory pmsCategory);

    /**
     * 根据父级商品分类id查询商品分类
     *
     * @param parentId 父级商品分类id
     * @param storeId  店铺id
     * @return 商品分类
     */
    List<PmsCategory> querySpuCategoryByParentId(long parentId, long storeId);
    /**
     * 新增商品分类
     *
     * @param pmsCategory 商品分类
     * @return 结果
     */
    public int insertPmsCategory(PmsCategory pmsCategory);

    /**
     * 修改商品分类
     *
     * @param pmsCategory 商品分类
     * @return 结果
     */
    public int updatePmsCategory(PmsCategory pmsCategory);

    /**
     * 批量删除商品分类
     *
     * @param ids 需要删除的商品分类ID
     * @return 结果
     */
    public int deletePmsCategoryByIds(Long[] ids);

    /**
     * 删除商品分类信息
     *
     * @param id 商品分类ID
     * @return 结果
     */
    public int deletePmsCategoryById(Long id);

    /**
     * 添加分类
     *
     * @param category 分类信息
     * @return 成功返回1  失败返回0
     */
    int addCategory(PmsCategory category);

    /**
     * 根据id查询分类信息
     *
     * @param id 分类id
     * @return 返回分类信息
     */
    PmsCategory queryCategoryById(long id);

    /**
     * 根据父级id 查询分类信息
     *
     * @param parentId 父级id
     * @return 返回指定父级下的所有分类
     */
    List<PmsCategory> queryCategoryByParentId(Long parentId);


    /**
     * 删除分类
     *
     * @param category 分类
     * @return -1 有子分类不能删除  1 成功 0 失败
     */
    int deleteCategory(PmsCategory category);

    /**
     * 更新分类信息
     *
     * @param category 分类
     * @return -1 该三级类关联商品，商品类型不能修改 1 成功 0 失败
     */
    int updateCategory(PmsCategory category);

    /**
     * 查询所有一级分类和二级分类
     *
     * @return 分类集合
     */
    List<PmsCategory> queryAllFirstAndSecondCategory();


    /**
     * 根据店铺id查询签约三级分类
     *
     * @param storeId 店铺id
     * @return 签约分类集合
     */
    List<PmsCategory> queryThreeCategoryByStoreId(long storeId);

    /**
     * 根据店铺id查询签约的二级分类
     *
     * @param storeId 店铺id
     * @return 返回签约二级分类
     */
    List<PmsCategory> queryTwoCategoryByStoreId(long storeId);

    /**
     * 查询所有boss分类
     *
     * @return 所有boss分类
     */
    List<PmsCategory> queryAllCategory();

    /**
     * 查询所有三级分类
     *
     * @return 所有三级分类
     */
    List<PmsCategory> queryThirdCategory();

    /**
     * 根据ID查询所有父类
     *
     * @param id 分类id
     * @return 包含自身的所有父集集合
     */
    List<PmsCategory> queryAllParentCategoryById(Long id);

    /**
     * 根据类型typeid查询是否存在关联三级分类
     *
     * @return 返回不为null即表示该类型关联三级类
     */
    List<PmsCategory> queryThirdCategoryByTypeId(long TypeId);

    /**
     * 根据分类id查询三级分类是否关联商品
     *
     * @return -1 该三级分类关联商品，商品类型不能修改 0 不关联
     */
    int queryThirdCategoryHasSpuById(long id);

    /**
     * 根据店铺id查询签约分类（包含签约分类的父级）
     *
     * @param storeId 店铺id
     * @return 店铺签约分类（包含父级）
     */
    List<PmsCategory> querySignCategoryByStoreIdWithAllParent(long storeId);
}
