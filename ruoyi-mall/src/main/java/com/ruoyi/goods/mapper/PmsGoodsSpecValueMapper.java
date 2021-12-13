package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsGoodsSpecValue;

import java.util.List;

/**
 * 商品和规格值的关联Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface PmsGoodsSpecValueMapper {
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
     * 删除商品和规格值的关联
     *
     * @param id 商品和规格值的关联ID
     * @return 结果
     */
    public int deletePmsGoodsSpecValueById(Long id);

    /**
     * 批量删除商品和规格值的关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsGoodsSpecValueByIds(Long[] ids);

    /**
     * 新增商品规格值
     *
     * @param spuSpecValues 商品规格值
     */

    void addSpuSpecValues(List<PmsGoodsSpecValue> spuSpecValues);

    /**
     * 根据商品id 查询商品的规格值
     *
     * @param spuId 商品id
     * @return 返回商品的规格值信息
     */

    List<PmsGoodsSpecValue> queryBySpuId(long spuId);

    /**
     * 根据商品id删除商品规格值
     *
     * @param spuId 商品id
     */

    void deleteBySpuId(long spuId);

    /**
     * 根据商品id删除商品规格值(物理)
     *
     * @param spuId 商品id
     */

    void deleteBySpuIdPhysical(long spuId);

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
