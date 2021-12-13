package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsCombination;

import java.util.List;

/**
 * 商品组合Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface PmsCombinationMapper {
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
     * 删除商品组合
     *
     * @param id 商品组合ID
     * @return 结果
     */
    public int deletePmsCombinationById(Long id);

    /**
     * 批量删除商品组合
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsCombinationByIds(Long[] ids);
}
