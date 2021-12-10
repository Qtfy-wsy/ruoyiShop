package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsGoodsSpecValue;

import java.util.List;

/**
 * 商品和规格值的关联Service接口
 *
 * @author 商城
 */
public interface IPmsGoodsSpecValueService {
    /**
     * 查询商品和规格值的关联
     *
     * @param id 商品和规格值的关联ID
     * @return 商品和规格值的关联
     */
    public PmsGoodsSpecValue selectPmsGoodsSpecValueById(Long id);

    /**
     * 查询商品和规格值的关联列表
     *
     * @param pmsGoodsSpecValue 商品和规格值的关联
     * @return 商品和规格值的关联集合
     */
    public List<PmsGoodsSpecValue> selectPmsGoodsSpecValueList(PmsGoodsSpecValue pmsGoodsSpecValue);

    /**
     * 新增商品和规格值的关联
     *
     * @param pmsGoodsSpecValue 商品和规格值的关联
     * @return 结果
     */
    public int insertPmsGoodsSpecValue(PmsGoodsSpecValue pmsGoodsSpecValue);

    /**
     * 修改商品和规格值的关联
     *
     * @param pmsGoodsSpecValue 商品和规格值的关联
     * @return 结果
     */
    public int updatePmsGoodsSpecValue(PmsGoodsSpecValue pmsGoodsSpecValue);

    /**
     * 批量删除商品和规格值的关联
     *
     * @param ids 需要删除的商品和规格值的关联ID
     * @return 结果
     */
    public int deletePmsGoodsSpecValueByIds(Long[] ids);

    /**
     * 删除商品和规格值的关联信息
     *
     * @param id 商品和规格值的关联ID
     * @return 结果
     */
    public int deletePmsGoodsSpecValueById(Long id);

    /**
     * 新增商品规格值
     *
     * @param spuSpecValues 商品规格值
     */
    void addSpuSpecValues(List<PmsGoodsSpecValue> spuSpecValues);

    /**
     * 根据商品id查询商品的规格值信息(附带规格返回)
     *
     * @param spuId 商品id
     * @return 返回商品的规格值信息
     */
    List<PmsGoodsSpecValue> queryBySpuIdWithSpec(long spuId);

    /**
     * 根据商品id删除商品规格值
     *
     * @param spuId 商品id
     */
    void deleteBySpuId(long spuId);

    /**
     * 更新商品规格值信息
     *
     * @param spuSpecValues 商品规格值信息
     * @param spuId         商品id
     */
    void updateSpuSpecValues(List<PmsGoodsSpecValue> spuSpecValues, long spuId);

    /**
     * 根据规格id查询商品规格值记录数
     *
     * @param specId 规格id
     * @return 返回商品规格值记录数
     */
    int queryCountBySpecId(long specId);

    /**
     * 根据规格值id查询商品规格值记录数
     *
     * @param specValueId 规格值id
     * @return 返回商品规格值记录数
     */
    int queryCountBySpecValueId(String specValueId);
}
