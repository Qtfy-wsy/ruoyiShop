package com.ruoyi.store.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.store.domain.TStoreCategory;
import com.ruoyi.store.mapper.TStoreCategoryMapper;
import com.ruoyi.store.service.ITStoreCategoryService;
import com.ruoyi.util.ThreadTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 店铺分类Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class TStoreCategoryServiceImpl implements ITStoreCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreCategoryServiceImpl.class);
    @Autowired
    private TStoreCategoryMapper tStoreCategoryMapper;

    /**
     * 自动注入商品分类mapper
     */
    @Autowired
    private TStoreCategoryMapper spuCategoryMapper;

    /**
     * 注入商品服务接口
     */
    @Autowired
    private IPmsGoodsService spuService;

    /**
     * 根据父级商品分类id查询商品分类
     *
     * @param parentId 父级商品分类id
     * @param storeId  店铺id
     * @return 商品分类
     */
    public List<TStoreCategory> querySpuCategoryByParentId(long parentId, long storeId) {
        logger.debug("querySpuCategoryByParentId and parentId :{} \r\n and storeId :{}", parentId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("parentId", parentId);
        params.put("storeId", storeId);
        return spuCategoryMapper.querySpuCategoryByParentId(params);
    }

    /**
     * 根据店铺id查询所有商品分类
     *
     * @param storeId 店铺id
     * @return 商品分类
     */
    @Override
    public List<TStoreCategory> queryAllSpuCategory(long storeId) {
        logger.debug("queryAllSpuCategory and storeId :{}", storeId);
        return spuCategoryMapper.queryAllSpuCategory(storeId);
    }

    /**
     * 删除商品分类
     *
     * @param id      商品分类id
     * @param storeId 店铺id
     * @param grade   商品分类级别
     * @return -1 有子分类不能删除，  1 成功， 0 失败
     */
    @Override
    @Transactional
    public int deleteSpuCategory(long id, long storeId, int grade, Consumer<Long[]> consumer) {
        logger.debug("deleteSpuCategory and id :{} \r\n and storeId :{} \r\n and grade :{}", id, storeId, grade);
        // 判断该商品分类是否有子分类 如果有则不能删除
        if (hasChildren(id, storeId)) {
            logger.error("deleteSpuCategory fail due to SpuCategory has children...and SpuCategoryId : {}", id);
            return -1;
        }
        // 取消该三级分类下面关联的商品
        if (grade == 3) {
            spuService.cancelSpuWithStoreCategoryByStoreTcateId(id, storeId);
            if (Objects.nonNull(consumer)) {
                ThreadTask.getInstance().addTask(() -> {
                    consumer.accept(spuService.queryAllSpuIdByStoreCategory(storeId, id));
                });
            }
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        return spuCategoryMapper.deleteSpuCategory(params);

    }

    /**
     * 判断该商品分类下是否有子分类
     *
     * @param id 商品分类id
     * @return 有返回true，没有返回false
     */
    private boolean hasChildren(long id, long storeId) {
        logger.debug("hasChildren and id :{} \r\n and storeId :{}", id, storeId);
        return this.querySpuCategoryByParentId(id, storeId).size() > 0;
    }

    /**
     * 根据商品分类id及店铺id查询商品分类
     *
     * @param id      商品分类id
     * @param storeId 店铺id
     * @return 商品分类
     */
    @Override
    public TStoreCategory querySpuCategoryById(Long id, long storeId) {
        logger.debug("querySpuCategoryById and id :{} \r\n and storeId :{}", id, storeId);
        if (Objects.isNull(id)) {
            logger.error("querySpuCategoryById fail: id is null");
            return null;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("storeId", storeId);
        return spuCategoryMapper.querySpuCategoryById(params);
    }

    /**
     * 更新商品分类
     *
     * @param spuCategory 商品分类
     * @return 成功返回1，失败返回0
     */
    @Override
    public int updateSpuCategory(TStoreCategory spuCategory) {
        logger.debug("updateSpuCategory and spuCategory :{}", spuCategory);
        return spuCategoryMapper.updateSpuCategory(spuCategory);
    }

    /**
     * 添加商品分类
     *
     * @param spuCategory 商品分类
     * @return 成功返回1，失败返回0
     */
    @Override
    public int addSpuCategory(TStoreCategory spuCategory) {
        logger.debug("addSpuCategory and spuCategory :{}", spuCategory);
        return spuCategoryMapper.addSpuCategory(spuCategory);
    }

    /**
     * 根据ID查询所有父集
     *
     * @param id      分类ID
     * @param storeId 店铺ID
     * @return 包含自身的所有父集
     */
    @Override
    public List<TStoreCategory> queryAllParentSpuCategoryById(Long id, Long storeId) {
        logger.debug("queryAllParentSpuCategoryById and id :{}", id);
        TStoreCategory category = querySpuCategoryById(id, storeId);
        List<TStoreCategory> categoryList = new ArrayList<>();
        categoryList.add(category);
        if (StringUtils.isEmpty(category)) {
            return Collections.emptyList();
        } else {
            return getParentCategory(categoryList);
        }
    }

    @Override
    public List<TStoreCategory> querySortedAllSpuCategory(long storeId) {
        logger.debug("querySortedAllSpuCategory and storeId:{}", storeId);
        List<TStoreCategory> spuCategoryList = queryAllSpuCategory(storeId);
        return sortSpuCategoryByParentId(spuCategoryList);
    }

    /**
     * 根据父类id排序
     *
     * @param spuCategoryList 分类集合
     * @return 分类集合
     */
    private List<TStoreCategory> sortSpuCategoryByParentId(List<TStoreCategory> spuCategoryList) {
        if (CollectionUtils.isEmpty(spuCategoryList)) {
            return spuCategoryList;
        }
        List<TStoreCategory> firstSpuCategory = spuCategoryList.stream().filter(TStoreCategory::isFirst).collect(Collectors.toList());
        firstSpuCategory.forEach(spuCategory -> buildChildCategory(spuCategory, spuCategoryList));
        return firstSpuCategory;
    }

    /**
     * 构建子集分类
     *
     * @param spuCategory     分类实体
     * @param spuCategoryList 分类集合
     * @return 分类实体
     */
    private TStoreCategory buildChildCategory(TStoreCategory spuCategory, List<TStoreCategory> spuCategoryList) {
        spuCategory.setChildCateGory(spuCategoryList.stream().filter(temp -> temp.getParentId() == spuCategory.getId()).collect(Collectors.toList()));
        if (spuCategory.getGrade() != 3) {
            spuCategory.getChildCateGory().forEach(temp -> buildChildCategory(temp, spuCategoryList));
        }
        return spuCategory;
    }

    /**
     * 递归查询所有父集
     *
     * @param categoryList 类别集合
     * @return 包含自身的所有父集集合
     */
    private List<TStoreCategory> getParentCategory(List<TStoreCategory> categoryList) {
        logger.debug("getParentCategory and categoryList :{}", categoryList);
        TStoreCategory category = categoryList.get(categoryList.size() - 1);
        if (!StringUtils.isEmpty(category.getParentId()) && category.getParentId() != 0L) {
            categoryList.add(querySpuCategoryById(category.getParentId(), category.getStoreId()));
        } else {
            return categoryList;
        }
        return getParentCategory(categoryList);
    }

    /**
     * 查询店铺分类
     *
     * @param id 店铺分类ID
     * @return 店铺分类
     */
    @Override
    public TStoreCategory selectTStoreCategoryById(Long id) {
        return tStoreCategoryMapper.selectTStoreCategoryById(id);
    }

    /**
     * 查询店铺分类列表
     *
     * @param tStoreCategory 店铺分类
     * @return 店铺分类
     */
    @Override
    public List<TStoreCategory> selectTStoreCategoryList(TStoreCategory tStoreCategory) {
        return tStoreCategoryMapper.selectTStoreCategoryList(tStoreCategory);
    }

    /**
     * 新增店铺分类
     *
     * @param tStoreCategory 店铺分类
     * @return 结果
     */
    @Override
    public int insertTStoreCategory(TStoreCategory tStoreCategory) {
        tStoreCategory.setCreateTime(DateUtils.getNowDate());
        return tStoreCategoryMapper.insertTStoreCategory(tStoreCategory);
    }

    /**
     * 修改店铺分类
     *
     * @param tStoreCategory 店铺分类
     * @return 结果
     */
    @Override
    public int updateTStoreCategory(TStoreCategory tStoreCategory) {
        return tStoreCategoryMapper.updateTStoreCategory(tStoreCategory);
    }

    /**
     * 批量删除店铺分类
     *
     * @param ids 需要删除的店铺分类ID
     * @return 结果
     */
    @Override
    public int deleteTStoreCategoryByIds(Long[] ids) {
        return tStoreCategoryMapper.deleteTStoreCategoryByIds(ids);
    }

    /**
     * 删除店铺分类信息
     *
     * @param id 店铺分类ID
     * @return 结果
     */
    @Override
    public int deleteTStoreCategoryById(Long id) {
        return tStoreCategoryMapper.deleteTStoreCategoryById(id);
    }
}
