package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.GoodsCombination;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 商品组合数据库接口
 * <p>
 * Created by 魔金商城 on 2017/6/12.
 */
@Repository
public interface GoodsCombinationMapper {

    /**
     * 分页查询商品组合
     *
     * @param params 查询参数
     * @return 返回商品组合集合
     */

    List<GoodsCombination> queryGoodsCombination(Map<String, Object> params);

    /**
     * 查询商品组合数目
     *
     * @param params 查询参数
     * @return 返回商品组合数目
     */

    int queryGoodsCombinationCount(Map<String, Object> params);

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
     * @param params 商品组合id数组
     * @return 成功返回>=1，失败返回0
     */

    int batchDeleteGoodsCombination(Map<String, Object> params);

    /**
     * 通过id查找商品组合
     *
     * @param params 商品组合id及店铺id
     * @return 商品组合
     */

    GoodsCombination queryGoodsCombinationById(Map<String, Object> params);

    /**
     * 修改商品组合
     *
     * @param goodsCombination 商品组合
     * @return 成功返回1，失败返回0
     */

    int updateGoodsCombination(GoodsCombination goodsCombination);

    /**
     * 根据单品id查询组合信息
     *
     * @param skuId 单品id
     * @return 返回单品的组合信息
     */

    GoodsCombination queryBySkuId(String skuId);

    /**
     * 根据单品id集合查询组合数量
     *
     * @param skuIds 单品id集合
     * @return 返回组合数量
     */

    int queryGoodsCombinationCountBySkuIds(List<String> skuIds);
}
