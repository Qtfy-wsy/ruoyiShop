package com.ruoyi.store.service.impl;

import com.ruoyi.goods.domain.PmsGoods;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.domain.PmsSkuItem;
import com.ruoyi.goods.domain.StoreSku;
import com.ruoyi.goods.service.IPmsCategoryService;
import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.goods.service.IPmsSkuService;
import com.ruoyi.goods.service.IPmsSpecService;
import com.ruoyi.store.domain.TStoreSku;
import com.ruoyi.store.mapper.TStoreSkuMapper;
import com.ruoyi.store.service.ITStoreSkuService;
import com.ruoyi.store.vo.StoreSkuDetail;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 门店单品Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class TStoreSkuServiceImpl implements ITStoreSkuService {
    private static final Logger log = LoggerFactory.getLogger(TStoreSkuServiceImpl.class);
    @Autowired
    private TStoreSkuMapper tStoreSkuMapper;
    /**
     * 注入门店单品关联数据库接口
     */
    @Autowired
    private TStoreSkuMapper storeSkuMapper;

    /**
     * 注入单品服务
     */
    @Autowired
    private IPmsSkuService skuService;

    /**
     * 注入商品服务
     */
    @Autowired
    private IPmsGoodsService spuService;

    /**
     * 注入规格服务
     */
    @Autowired
    private IPmsSpecService specService;

    /**
     * 注入分类服务
     */
    @Autowired
    private IPmsCategoryService categoryService;

    @Override
    public List<StoreSku> queryStoreSkuListBySpuId(long spuId, long storeId) {
        log.debug("queryStoreSkuListBySpuId and spuId:{} \r\n storeId:{}", spuId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("spuId", spuId);
        params.put("storeId", storeId);
        return storeSkuMapper.queryStoreSkuList(params);
    }

    @Override
    public List<StoreSku> queryStoreSkuListBySkuId(String skuId, long storeId) {
        log.debug("queryStoreSkuListBySkuId and skuId:{} \r\n storeId:{}", skuId, storeId);
        Map<String, Object> params = new HashMap<>();
        params.put("skuId", skuId);
        params.put("storeId", storeId);
        return storeSkuMapper.queryStoreSkuList(params);
    }

    @Override
    @Transactional
    public int addStoreSkuList(List<StoreSku> storeSkuList, long spuId, long storeId) {
        log.debug("addStoreSkuList and storeSkuList:{} \r\n spuId:{} \r\n storeId:{}", storeSkuList, spuId, storeId);
        storeSkuMapper.deleteStoreSkuBySpuId(spuId, storeId);
        if (CollectionUtils.isEmpty(storeSkuList)) {
            log.info("addStoreSkuList : storeSkuList is empty");
            return 1;
        }
        return storeSkuMapper.addStoreSkuList(storeSkuList.stream().map(storeSku -> storeSku.buildStoreId(storeId)).collect(Collectors.toList()));
    }

    @Override
    public int reduceStoreSkusStock(Long storeId, String skuId, int num) {
        log.debug("reduceStoreSkusStock and storeId:{} \r\n skuId:{} \r\n num:{}", storeId, skuId, num);
        Map<String, Object> params = new HashMap<>();
        params.put("storeId", storeId);
        params.put("skuId", skuId);
        params.put("stock", num);
        return storeSkuMapper.reduceStoreSkusStock(params);
    }

    /**
     * 删除单品关联
     *
     * @param spuId spuId
     * @return 1 成功 0 失败
     */
    @Override
    public int deleteStoreSkuBySpuId(long spuId) {
        log.debug("deleteStoreSkuBySpuId and spuId:{}", spuId);
        return storeSkuMapper.deleteStoreSkuBySpuId(spuId, null);
    }


    @Override
    public StoreSkuDetail queryStoreSkuDetail(String skuId, long storeId) {
        log.debug("queryStoreSkuDetail and skuId:{} \r\n storeId:{}", skuId, storeId);
        //查询门店是否关联了单品
        List<StoreSku> storeSkuList = queryStoreSkuListBySkuId(skuId, storeId);
        if (CollectionUtils.isEmpty(storeSkuList)) {
            log.error("queryStoreSkuDetail fail : no storeSku");
            return null;
        }
        //查询单品信息
        PmsSku sku = skuService.querySkuById(skuId);
        if (Objects.isNull(sku)) {
            log.error("queryStoreSkuDetail fail : no sku");
            return null;
        }
        skuService.setSkuDetail(sku, PmsSkuItem.IMAGE, PmsSkuItem.SPEC);
        PmsGoods spu = spuService.querySimpleSpu(sku.getSpuId(), CommonConstant.QUERY_WITH_NO_STORE);
        return StoreSkuDetail.build(storeSkuList.get(0), sku)
                .buildCategories(categoryService.queryAllParentCategoryById(spu.getThirdCateId()))
                .buildSpecs(specService.querySpuSpecs(sku.getSpuId()));
    }

    /**
     * 查询门店单品
     *
     * @param id 门店单品ID
     * @return 门店单品
     */
    @Override
    public TStoreSku selectTStoreSkuById(Long id) {
        return tStoreSkuMapper.selectTStoreSkuById(id);
    }

    /**
     * 查询门店单品列表
     *
     * @param tStoreSku 门店单品
     * @return 门店单品
     */
    @Override
    public List<TStoreSku> selectTStoreSkuList(TStoreSku tStoreSku) {
        return tStoreSkuMapper.selectTStoreSkuList(tStoreSku);
    }

    /**
     * 新增门店单品
     *
     * @param tStoreSku 门店单品
     * @return 结果
     */
    @Override
    public int insertTStoreSku(TStoreSku tStoreSku) {
        return tStoreSkuMapper.insertTStoreSku(tStoreSku);
    }

    /**
     * 修改门店单品
     *
     * @param tStoreSku 门店单品
     * @return 结果
     */
    @Override
    public int updateTStoreSku(TStoreSku tStoreSku) {
        return tStoreSkuMapper.updateTStoreSku(tStoreSku);
    }

    /**
     * 批量删除门店单品
     *
     * @param ids 需要删除的门店单品ID
     * @return 结果
     */
    @Override
    public int deleteTStoreSkuByIds(Long[] ids) {
        return tStoreSkuMapper.deleteTStoreSkuByIds(ids);
    }

    /**
     * 删除门店单品信息
     *
     * @param id 门店单品ID
     * @return 结果
     */
    @Override
    public int deleteTStoreSkuById(Long id) {
        return tStoreSkuMapper.deleteTStoreSkuById(id);
    }
}
