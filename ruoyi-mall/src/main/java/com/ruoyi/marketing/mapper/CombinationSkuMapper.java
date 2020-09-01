package com.ruoyi.marketing.mapper;


import com.ruoyi.marketing.domain.CombinationSku;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 商品组合单品关联数据库接口
 * <p>
 * Created by 魔金商城 on 2017/6/13.
 */
@Repository
public interface CombinationSkuMapper {

    /**
     * 分页查询商品组合下的商品
     *
     * @param params 查询参数
     * @return 商品组合下的单品
     */

    List<CombinationSku> querySkuOfGoodCom(Map<String, Object> params);

    /**
     * 查询商品组合下的单品数目
     *
     * @param params 查询参数
     * @return 商品组合下的单品数目
     */

    int querySkuCount(Map<String, Object> params);

    /**
     * 查询指定商品组合下的单品id集合（防止商品组合添加重复单品）
     *
     * @param combinationSku 商品组合与单品的关联类
     * @return 单品id集合
     */

    List<String> querySkuIdWithAdd(CombinationSku combinationSku);

    /**
     * 为商品组合添加单品
     *
     * @param combinationSku 单品
     * @return 成功返回1，失败返回0
     */

    int addCombinationSku(CombinationSku combinationSku);

    /**
     * 批量删除单品
     *
     * @param params 单品id数组
     * @return 成功返回>=1，失败返回0
     */

    int batchDeleteCombiantionSKu(Map<String, Object> params);

    /**
     * 根据组合id查询组合下的单品信息
     *
     * @param id 组合id
     * @return 返回组合下的单品信息
     */

    List<CombinationSku> queryByCombiantionId(long id);
}
