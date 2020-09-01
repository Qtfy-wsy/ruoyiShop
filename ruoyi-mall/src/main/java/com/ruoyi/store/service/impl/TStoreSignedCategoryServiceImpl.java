package com.ruoyi.store.service.impl;

import com.ruoyi.store.domain.TStoreSignedCategory;
import com.ruoyi.store.mapper.TStoreSignedCategoryMapper;
import com.ruoyi.store.service.ITStoreSignedCategoryService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 店铺的签约分类Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class TStoreSignedCategoryServiceImpl implements ITStoreSignedCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(TStoreSignedCategoryServiceImpl.class);
    @Autowired
    private TStoreSignedCategoryMapper tStoreSignedCategoryMapper;


    /**
     * 注入店铺签约分类
     */
    @Autowired
    private TStoreSignedCategoryMapper storeSignedCategoryMapper;

    @Override
    public List<TStoreSignedCategory> queryAllSignedCategorys(long storeId) {
        logger.debug("queryAllSignedCategorys and storeId:{}", storeId);
        return storeSignedCategoryMapper.queryAllSignedCategorys(storeId);
    }

    /**
     * 批量添加签约分类
     *
     * @param list 店铺签约分类集合
     * @return 添加返回码
     */
    @Override
    public int addSignedCategory(List<TStoreSignedCategory> list) {
        logger.debug("addSignedCategory and list:{}", list);
        return storeSignedCategoryMapper.addSignedCategory(list);
    }

    /**
     * 删除店铺签约分类
     *
     * @param storeId 店铺id
     * @return 删除返回码
     */
    @Override
    public int deleteSignedCategory(long storeId) {
        logger.debug("deleteSignedCategory and storeId:{}", storeId);
        return storeSignedCategoryMapper.deleteSignedCategory(storeId);
    }

    /**
     * 根据店铺id和分类id删除签约分类
     *
     * @param storeId 店铺id
     * @param cateId  分类id
     * @return 删除返回码
     */
    @Override
    public int deleteSingedCategoryById(long storeId, long cateId) {
        logger.debug("deleteSingedCategoryById and storeId:{}\r\n cateId:{}", storeId, cateId);
        Map<String, Object> map = new HashMap<>();
        map.put("storeId", storeId);
        map.put("cateId", cateId);
        return storeSignedCategoryMapper.deleteSingedCategoryById(map);
    }

    /**
     * 添加签约分类 admin和store端用
     *
     * @param state       是否直接生效， 否则需要审核
     * @param categoryIds 分类id数组
     * @param storeId     店铺id
     * @return 返回添加码
     */
    @Override
    public int addSignedCategoryAdmin(long[] categoryIds, long storeId, boolean state) {
        logger.debug("addSignedCategoryAdmin and storeId:{}\r\n categoryIds:{}", storeId, categoryIds);
        if (ArrayUtils.isEmpty(categoryIds)) {
            logger.error("addSignedCategoryAdmin error due to categoryIds is empty");
            return -1;
        }
        List<Long> categoryIdsList = Arrays.asList(ArrayUtils.toObject(categoryIds));
        List<Long> filterList = new ArrayList<>();
        queryAllSignedCategorys(storeId).forEach(storeSignedCategory -> {
            if (categoryIdsList.contains(storeSignedCategory.getCateId())) {
                filterList.add(storeSignedCategory.getCateId());
            }
        });
        List<Long> toRemoveList = new ArrayList<>();
        toRemoveList.addAll(categoryIdsList);
        toRemoveList.removeAll(filterList);
        if (CollectionUtils.isEmpty(toRemoveList)) {
            logger.error("addSignedCategoryAdmin error due to already add");
            return -2;
        }
        //添加签约分类
        List<TStoreSignedCategory> storeSignedCategories = new ArrayList<>();
        toRemoveList.forEach(categoryId -> storeSignedCategories.add(new TStoreSignedCategory().getStoreSignedCategory(storeId, (long) categoryId, state)));
        return addSignedCategory(storeSignedCategories);
    }

    /**
     * 更新签约分类 admin
     *
     * @param state 是否直接生效，否 则需要审核
     * @param ids   签约id
     * @return 返回添加码
     */
    @Override
    public int updateState(List<Long> ids, boolean state) {
        logger.debug("update ids:{}\r\n state:{}", ids, state);
        if (ids.isEmpty()) {
            logger.error("update error due to ids is empty");
            return -1;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("state", state);
        param.put("ids", ids);
        return storeSignedCategoryMapper.update(param);
    }

    /**
     * 查询店铺的签约分类
     *
     * @param id 店铺的签约分类ID
     * @return 店铺的签约分类
     */
    @Override
    public TStoreSignedCategory selectTStoreSignedCategoryById(Long id) {
        return tStoreSignedCategoryMapper.selectTStoreSignedCategoryById(id);
    }

    /**
     * 查询店铺的签约分类列表
     *
     * @param tStoreSignedCategory 店铺的签约分类
     * @return 店铺的签约分类
     */
    @Override
    public List<TStoreSignedCategory> selectTStoreSignedCategoryList(TStoreSignedCategory tStoreSignedCategory) {
        return tStoreSignedCategoryMapper.selectTStoreSignedCategoryList(tStoreSignedCategory);
    }

    /**
     * 新增店铺的签约分类
     *
     * @param tStoreSignedCategory 店铺的签约分类
     * @return 结果
     */
    @Override
    public int insertTStoreSignedCategory(TStoreSignedCategory tStoreSignedCategory) {
        return tStoreSignedCategoryMapper.insertTStoreSignedCategory(tStoreSignedCategory);
    }

    /**
     * 修改店铺的签约分类
     *
     * @param tStoreSignedCategory 店铺的签约分类
     * @return 结果
     */
    @Override
    public int updateTStoreSignedCategory(TStoreSignedCategory tStoreSignedCategory) {
        return tStoreSignedCategoryMapper.updateTStoreSignedCategory(tStoreSignedCategory);
    }

    /**
     * 批量删除店铺的签约分类
     *
     * @param ids 需要删除的店铺的签约分类ID
     * @return 结果
     */
    @Override
    public int deleteTStoreSignedCategoryByIds(Long[] ids) {
        return tStoreSignedCategoryMapper.deleteTStoreSignedCategoryByIds(ids);
    }

    /**
     * 删除店铺的签约分类信息
     *
     * @param id 店铺的签约分类ID
     * @return 结果
     */
    @Override
    public int deleteTStoreSignedCategoryById(Long id) {
        return tStoreSignedCategoryMapper.deleteTStoreSignedCategoryById(id);
    }
}
