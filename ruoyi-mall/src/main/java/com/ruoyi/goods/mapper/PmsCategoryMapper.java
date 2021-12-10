package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsCategory;
import com.ruoyi.goods.domain.PmsCategorySpec;

import java.util.List;
import java.util.Map;

/**
 * 商品分类Mapper接口
 *
 * @author 商城
 */
public interface PmsCategoryMapper {
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
     * 新增商品分类
     *
     * @param pmsCategory 商品分类
     * @return 结果
     */
    public int insertPmsCategory(PmsCategory pmsCategory);

    List<PmsCategory> querySpuCategoryByParentId(Map<String, Object> params);

    /**
     * 修改商品分类
     *
     * @param pmsCategory 商品分类
     * @return 结果
     */
    public int updatePmsCategory(PmsCategory pmsCategory);

    /**
     * 删除商品分类
     *
     * @param id 商品分类ID
     * @return 结果
     */
    public int deletePmsCategoryById(Long id);

    /**
     * 批量删除商品分类
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsCategoryByIds(Long[] ids);

    /**
     * 新增分类
     *
     * @param category 分类信息
     * @return 成功返回1  失败返回0
     */

    int addCategory(PmsCategory category);

    /**
     * 查询分类信息
     *
     * @param id 分类id
     * @return 返回分类信息
     */

    PmsCategory queryCategoryById(long id);

    /**
     * 根据父级id查询分类信息
     *
     * @param parentId 父级id
     * @return 返回该父级分类下的所有分类信息
     */

    List<PmsCategory> queryCategoryByParentId(Long parentId);

    /**
     * 删除分类
     *
     * @param category 分类
     * @return 成功返回 1 失败返回0
     */

    int deleteCategory(PmsCategory category);

    /**
     * 更新分类信息
     *
     * @param category 分类
     * @return 成功返回1 失败返回0
     */

    int updateCategory(PmsCategory category);

    /**
     * 更新除了商品类型之外的其他分类信息
     *
     * @param category 分类
     * @return 成功返回1 失败返回0
     */

    int updateCategoryExceptTypeId(PmsCategory category);

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
     * 根据类型typeid查询是否存在关联三级分类
     *
     * @return 返回不为null即表示该类型关联三级类
     */

    List<PmsCategory> queryThirdCategoryByTypeId(long TypeId);

    /**
     * 根据分类id集合查询分类集合
     *
     * @param categoryIds 分类id集合
     * @return 分类集合
     */

    List<PmsCategory> queryCategoriesByIds(List<Long> categoryIds);


    /**
     * 新增分类和规格的关联关系
     *
     * @param cateSpecs 分类规格集合
     */

    void addCateAndSpec(List<PmsCategorySpec> cateSpecs);

    /**
     * 根据分类id查询分类关联的规格信息
     *
     * @param cateId 分类id
     * @return 返回类型关联的规格
     */

    List<PmsCategorySpec> queryCateSpecByCateId(long cateId);


    /**
     * 根据分类id 删除分类规格 (物理删除)
     *
     * @param cateId 分类id
     */

    void deleteCateSpecsByCateIdPhysical(long cateId);
}
