package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsCombinationSku;

import java.util.List;

/**
 * 商品组合下的单品Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsCombinationSkuService {
    /**
     * 查询商品组合下的单品
     *
     * @param id 商品组合下的单品ID
     * @return 商品组合下的单品
     */
    public PmsCombinationSku selectPmsCombinationSkuById(Long id);

    /**
     * 查询商品组合下的单品列表
     *
     * @param pmsCombinationSku 商品组合下的单品
     * @return 商品组合下的单品集合
     */
    public List<PmsCombinationSku> selectPmsCombinationSkuList(PmsCombinationSku pmsCombinationSku);

    /**
     * 新增商品组合下的单品
     *
     * @param pmsCombinationSku 商品组合下的单品
     * @return 结果
     */
    public int insertPmsCombinationSku(PmsCombinationSku pmsCombinationSku);

    /**
     * 修改商品组合下的单品
     *
     * @param pmsCombinationSku 商品组合下的单品
     * @return 结果
     */
    public int updatePmsCombinationSku(PmsCombinationSku pmsCombinationSku);

    /**
     * 批量删除商品组合下的单品
     *
     * @param ids 需要删除的商品组合下的单品ID
     * @return 结果
     */
    public int deletePmsCombinationSkuByIds(Long[] ids);

    /**
     * 删除商品组合下的单品信息
     *
     * @param id 商品组合下的单品ID
     * @return 结果
     */
    public int deletePmsCombinationSkuById(Long id);
}
