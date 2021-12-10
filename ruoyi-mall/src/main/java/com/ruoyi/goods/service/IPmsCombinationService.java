package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsCombination;

import java.util.List;

/**
 * 商品组合Service接口
 *
 * @author 商城
 */
public interface IPmsCombinationService {
    /**
     * 查询商品组合
     *
     * @param id 商品组合ID
     * @return 商品组合
     */
    public PmsCombination selectPmsCombinationById(Long id);

    /**
     * 查询商品组合列表
     *
     * @param pmsCombination 商品组合
     * @return 商品组合集合
     */
    public List<PmsCombination> selectPmsCombinationList(PmsCombination pmsCombination);

    /**
     * 新增商品组合
     *
     * @param pmsCombination 商品组合
     * @return 结果
     */
    public int insertPmsCombination(PmsCombination pmsCombination);

    /**
     * 修改商品组合
     *
     * @param pmsCombination 商品组合
     * @return 结果
     */
    public int updatePmsCombination(PmsCombination pmsCombination);

    /**
     * 批量删除商品组合
     *
     * @param ids 需要删除的商品组合ID
     * @return 结果
     */
    public int deletePmsCombinationByIds(Long[] ids);

    /**
     * 删除商品组合信息
     *
     * @param id 商品组合ID
     * @return 结果
     */
    public int deletePmsCombinationById(Long id);
}
