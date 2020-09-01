package com.ruoyi.marketing.service;

import com.ruoyi.marketing.domain.GoodsCombination;
import com.ruoyi.util.PageHelper;

import java.util.List;

/**
 * 商品组合service
 * <p>
 * Created by 魔金商城 on 2017/6/12.
 */
public interface GoodsCombinationService {

    /**
     * 分页查询商品组合
     *
     * @param pageHelper 查询参数
     * @param name       商品组合名称
     * @return 返回商品组合数据
     */
    PageHelper<GoodsCombination> queryGoodsCombination(PageHelper<GoodsCombination> pageHelper, String name, long storeId);

    /**
     * 添加商品组合
     *
     * @param goodsCombination 商品组合
     * @return 成功返回1，失败返回0
     */
    int addGoodsCombination(GoodsCombination goodsCombination);

    /**
     * 批量删除商品组合
     *
     * @param ids     商品组合id数组
     * @param storeId 店铺id
     * @return 成功返回>=1，失败返回0
     */
    int batchDeleteGoodsCombination(long[] ids, long storeId);

    /**
     * 通过id查找商品组合
     *
     * @param id      商品组合id
     * @param storeId 店铺id
     * @return 商品组合
     */
    GoodsCombination queryGoodsCombinationById(long id, long storeId);

    /**
     * 修改商品组合
     *
     * @param goodsCombination 商品组合
     * @return 成功返回1，失败返回0
     */
    int updateGoodsCombination(GoodsCombination goodsCombination);


    /**
     * 根据单品id查询和这个单品属于同一个组合的单品信息
     *
     * @param skuId 单品id
     * @return 返回单品的组合信息
     */
    GoodsCombination querySkusBySku(String skuId);

    /**
     * 根据单品id集合查询组合数量
     *
     * @param skuIds 单品id集合
     * @return 返回单品的组合信息
     */
    int queryGoodsCombinationCountBySkuIds(List<String> skuIds);
}
