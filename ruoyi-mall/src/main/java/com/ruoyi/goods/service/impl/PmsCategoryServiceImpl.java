package com.ruoyi.goods.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.domain.PmsCategory;
import com.ruoyi.goods.domain.PmsCategorySpec;
import com.ruoyi.goods.mapper.PmsCategoryMapper;
import com.ruoyi.goods.service.IPmsCategoryService;
import com.ruoyi.goods.service.IPmsGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品分类Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@Service
public class PmsCategoryServiceImpl implements IPmsCategoryService {
    @Autowired
    private PmsCategoryMapper pmsCategoryMapper;

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(PmsCategoryServiceImpl.class);

    /**
     * 注入分类数据库接口
     */
    @Autowired
    private PmsCategoryMapper categoryMapper;

    /**
     * 注入商品服务接口
     */
    @Autowired
    private IPmsGoodsService spuService;

    /**
     * 查询商品分类
     *
     * @param id 商品分类ID
     * @return 商品分类
     */
    @Override
    public PmsCategory selectPmsCategoryById(Long id) {
        return pmsCategoryMapper.selectPmsCategoryById(id);
    }

    /**
     * 查询商品分类列表
     *
     * @param pmsCategory 商品分类
     * @return 商品分类
     */
    @Override
    public List<PmsCategory> selectPmsCategoryList(PmsCategory pmsCategory) {
        return pmsCategoryMapper.selectPmsCategoryList(pmsCategory);
    }
    /**
     * 根据父级商品分类id查询商品分类
     *
     * @param parentId 父级商品分类id
     * @param storeId  店铺id
     * @return 商品分类
     */
    @Override
    public List<PmsCategory> querySpuCategoryByParentId(long parentId, long storeId) {
        logger.debug("querySpuCategoryByParentId and parentId :{} \r\n and storeId :{}", parentId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        params.put("storeId", storeId);
        return pmsCategoryMapper.querySpuCategoryByParentId(params);
    }
    /**
     * 新增商品分类
     *
     * @param pmsCategory 商品分类
     * @return 结果
     */
    @Override
    public int insertPmsCategory(PmsCategory pmsCategory) {
        pmsCategory.setCreateTime(DateUtils.getNowDate());
        return pmsCategoryMapper.insertPmsCategory(pmsCategory);
    }

    /**
     * 修改商品分类
     *
     * @param pmsCategory 商品分类
     * @return 结果
     */
    @Override
    public int updatePmsCategory(PmsCategory pmsCategory) {
        return pmsCategoryMapper.updatePmsCategory(pmsCategory);
    }

    /**
     * 批量删除商品分类
     *
     * @param ids 需要删除的商品分类ID
     * @return 结果
     */
    @Override
    public int deletePmsCategoryByIds(Long[] ids) {
        return pmsCategoryMapper.deletePmsCategoryByIds(ids);
    }

    /**
     * 删除商品分类信息
     *
     * @param id 商品分类ID
     * @return 结果
     */
    @Override
    public int deletePmsCategoryById(Long id) {
        return pmsCategoryMapper.deletePmsCategoryById(id);
    }

    @Override
    public int addCategory(PmsCategory category) {
        logger.debug("addCategory and category:{}", category);

        if (Objects.isNull(category)) {
            logger.error("addCategory fail due to category is empty....");
            return 0;
        }

        // 新增分类
        categoryMapper.addCategory(category);

        // 如果是三级分类 则添加分类关联的规格信息
        if (category.isThirdCategory()) {
            category.setCateSpecTypeId();
            addCategoryAndSpec(category.getCateSpecs());
        }

        return 1;
    }


    @Override
    public PmsCategory queryCategoryById(long id) {
        logger.debug("queryCategoryById and id :{}", id);

        // 查询分类信息
        PmsCategory category = categoryMapper.queryCategoryById(id);

        if (Objects.isNull(category)) {
            logger.error("queryCategoryById fail... and id :{}", id);
            return category;
        }

        // 如果是三级分类 则设置三级分类关联的规格
        if (category.isThirdCategory()) {
            category.setCateSpecs(categoryMapper.queryCateSpecByCateId(id));
        }

        return category;
    }

    @Override
    public List<PmsCategory> queryCategoryByParentId(Long parentId) {
        logger.debug("queryCategoryByParentId and parentId : {}", parentId);

        return setCateSpecs(categoryMapper.queryCategoryByParentId(parentId));
    }


    @Override
    public int deleteCategory(PmsCategory category) {
        logger.debug("deleteCategory and category :{}", category);

        // 判断该分类是否有子分类 如果有则不能删除
        if (hasChildren(category.getId())) {
            logger.error("deleteCategory fail due to category has children...and category : {}", category);
            return -1;
        }

        // 如果该分类被商品关联了 则不能删除
        if (queryThirdCategoryHasSpuById(category.getId()) == -1) {
            logger.error("deleteCategory fail due to category has goods....");
            return -2;
        }

        return categoryMapper.deleteCategory(category);
    }

    @Override
    @Transactional
    public int updateCategory(PmsCategory category) {
        logger.debug("updateCategory and category : ");
        if (Objects.isNull(category)) {
            logger.error("updateCategory fail due to category is null...");
            return 0;
        }


        // 如果是三级分类 则更新三级分类关联的规格
        if (category.isThirdCategory()) {
            updateCateSpecs(category);
        }

        //如果三级分类关联商品，可以修改除了商品类型之外的其他分类信息
        if (spuService.querySpuByThirdCateId(category.getId()) > 0) {
            logger.debug("updateCategoryExceptTypeId and category : {}", category);
            return categoryMapper.updateCategoryExceptTypeId(category);
        }
        return categoryMapper.updatePmsCategory(category);
    }

    /**
     * 根据分类id查询三级分类是否关联商品
     *
     * @return -1 该三级分类关联商品，商品类型不能修改 0 不关联
     */
    @Override
    public int queryThirdCategoryHasSpuById(long id) {
        logger.debug("queryThirdCategoryHasSpuById and id : {}", id);
        if (spuService.querySpuByThirdCateId(id) > 0) {
            logger.error("updateCategory fail due to category has goods...and id : {}", id);
            return -1;
        }
        return 0;
    }

    @Override
    public List<PmsCategory> querySignCategoryByStoreIdWithAllParent(long storeId) {
        logger.debug("querySignCategoryByStoreIdWithAllParent and storeId:{}", storeId);
        List<PmsCategory> signThirdCategories = queryThreeCategoryByStoreId(storeId);
        if (CollectionUtils.isEmpty(signThirdCategories)) {
            logger.error("querySignCategoryByStoreIdWithAllParent fail: signThirdCategories is empty");
            return signThirdCategories;
        }
        List<PmsCategory> secondCategories = categoryMapper.queryCategoriesByIds(getParentIds(signThirdCategories));
        List<PmsCategory> firstCategories = categoryMapper.queryCategoriesByIds(getParentIds(secondCategories));
        signThirdCategories.addAll(secondCategories);
        signThirdCategories.addAll(firstCategories);

        signThirdCategories.stream().filter(PmsCategory::isThirdCategory).forEach(category -> category.setCateSpecs(categoryMapper.queryCateSpecByCateId(category.getId())));

        return signThirdCategories;
    }

    /**
     * 查询所有一级分类和二级分类
     *
     * @return 分类集合
     */
    @Override
    public List<PmsCategory> queryAllFirstAndSecondCategory() {
        List<PmsCategory> allList = categoryMapper.queryAllFirstAndSecondCategory();
        return allList.stream().filter(PmsCategory::isFirstCategory).map(category -> category.setChildCateGory(allList.stream().filter(childCategory -> category.getId() == childCategory.getParentId()).collect(Collectors.toList()))
        ).collect(Collectors.toList());
    }

    /**
     * 根据店铺id查询签约三级分类
     *
     * @param storeId 店铺id
     * @return 签约分类集合
     */
    @Override
    public List<PmsCategory> queryThreeCategoryByStoreId(long storeId) {
        return categoryMapper.queryThreeCategoryByStoreId(storeId);
    }

    /**
     * 根据店铺id查询签约的二级分类
     *
     * @param storeId 店铺id
     * @return 返回签约二级分类
     */
    @Override
    public List<PmsCategory> queryTwoCategoryByStoreId(long storeId) {
        return categoryMapper.queryTwoCategoryByStoreId(storeId);
    }

    /**
     * 查询所有boss分类
     *
     * @return 所有boss分类
     */
    @Override
    public List<PmsCategory> queryAllCategory() {
        return categoryMapper.queryAllCategory();
    }

    /**
     * 查询所有三级分类
     *
     * @return 所有三级分类
     */
    @Override
    public List<PmsCategory> queryThirdCategory() {
        return categoryMapper.queryThirdCategory();
    }

    /**
     * 根据类型typeid查询是否存在关联三级分类
     *
     * @return 返回不为null即表示该类型关联三级类
     */
    @Override
    public List<PmsCategory> queryThirdCategoryByTypeId(long TypeId) {
        return categoryMapper.queryThirdCategoryByTypeId(TypeId);
    }

    /**
     * 根据ID查询所有父集
     *
     * @param id 分类ID
     * @return 包含自身的所有父集
     */
    @Override
    public List<PmsCategory> queryAllParentCategoryById(Long id) {

        logger.debug("queryAllParentCategoryById and id :{}", id);
        PmsCategory category = categoryMapper.queryCategoryById(id);
        List<PmsCategory> categoryList = new ArrayList<>();
        categoryList.add(category);
        if (StringUtils.isEmpty(category)) {
            return Collections.emptyList();
        } else {
            return getParentCategory(categoryList);
        }
    }

    /**
     * 判断分类下是否有子分类
     *
     * @param id 分类id
     * @return 有返回true  没有返回false
     */
    private boolean hasChildren(long id) {
        logger.debug("hasChildren and id :{}", id);
        return this.queryCategoryByParentId(id).size() > 0;
    }

    /**
     * 递归查询所有父集
     *
     * @param categoryList 类别集合
     * @return 包含自身的所有父集集合
     */
    private List<PmsCategory> getParentCategory(List<PmsCategory> categoryList) {
        logger.debug("getParentCategory and listSize :{}", categoryList.size());
        PmsCategory category = categoryList.get(categoryList.size() - 1);
        if (!StringUtils.isEmpty(category.getParentId()) && category.getParentId() != 0L) {
            categoryList.add(categoryMapper.queryCategoryById(category.getParentId()));
        } else {
            return categoryList;
        }
        return getParentCategory(categoryList);
    }

    /**
     * 获取父级id
     *
     * @param categoryList 分类集合
     * @return 父级id集合
     */
    private List<Long> getParentIds(List<PmsCategory> categoryList) {
        return categoryList.stream().map(PmsCategory::getParentId).collect(Collectors.toList());
    }

    /**
     * 新增类型和规格的关联关系
     *
     * @param cateSpecs 分类规格
     */
    private void addCategoryAndSpec(List<PmsCategorySpec> cateSpecs) {
        logger.debug("addCategoryAndSpec and cateSpecs : {}", cateSpecs);

        if (CollectionUtils.isEmpty(cateSpecs)) {
            logger.error("addCategoryAndSpec fail due to cateSpecs is empty....");
            return;
        }
        categoryMapper.addCateAndSpec(cateSpecs);
    }


    /**
     * 更新分类的规格
     *
     * @param category 分类
     */
    private void updateCateSpecs(PmsCategory category) {

        logger.debug("updateCateSpecs and category :{}", category.getCateSpecs());

        categoryMapper.deleteCateSpecsByCateIdPhysical(category.getId());

        if (CollectionUtils.isEmpty(category.getCateSpecs())) {
            logger.warn("do not need to updateCateSpecs....");
            return;
        }

        //设置分类与规格关联的分类id
        category.setCateSpecTypeId();

        addCategoryAndSpec(category.getCateSpecs());
    }


    /**
     * 设置分类的规格信息
     *
     * @param categories 分类信息
     */
    private List<PmsCategory> setCateSpecs(List<PmsCategory> categories) {

      //  logger.debug("setCateSpecs and categories:{}", categories);

        if (CollectionUtils.isEmpty(categories)) {
            return categories;
        }

        categories.stream().filter(PmsCategory::isThirdCategory).forEach(category -> category.setCateSpecs(categoryMapper.queryCateSpecByCateId(category.getId())));

        return categories;
    }

}
