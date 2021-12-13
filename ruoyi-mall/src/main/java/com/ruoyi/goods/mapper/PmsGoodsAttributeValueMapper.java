package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsGoodsAttributeValue;

import java.util.List;

/**
 * 商品下面的属性值Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface PmsGoodsAttributeValueMapper {
    /**
     * 查询商品下面的属性值
     *
     * @param id 商品下面的属性值ID
     * @return 商品下面的属性值
     */
    public PmsGoodsAttributeValue selectPmsGoodsAttributeValueById(Long id);

    /**
     * 查询商品下面的属性值列表
     *
     * @param pmsGoodsAttributeValue 商品下面的属性值
     * @return 商品下面的属性值集合
     */
    public List<PmsGoodsAttributeValue> selectPmsGoodsAttributeValueList(PmsGoodsAttributeValue pmsGoodsAttributeValue);

    /**
     * 新增商品下面的属性值
     *
     * @param pmsGoodsAttributeValue 商品下面的属性值
     * @return 结果
     */
    public int insertPmsGoodsAttributeValue(PmsGoodsAttributeValue pmsGoodsAttributeValue);

    /**
     * 修改商品下面的属性值
     *
     * @param pmsGoodsAttributeValue 商品下面的属性值
     * @return 结果
     */
    public int updatePmsGoodsAttributeValue(PmsGoodsAttributeValue pmsGoodsAttributeValue);

    /**
     * 删除商品下面的属性值
     *
     * @param id 商品下面的属性值ID
     * @return 结果
     */
    public int deletePmsGoodsAttributeValueById(Long id);

    /**
     * 批量删除商品下面的属性值
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsGoodsAttributeValueByIds(Long[] ids);

    /**
     * 添加商品属性值
     *
     * @param spuAttributeValues 商品属性值集合
     */

    void addSpuAttributeValues(List<PmsGoodsAttributeValue> spuAttributeValues);

    /**
     * 根据商品id删除商品属性值
     *
     * @param spuId 商品id
     */

    void deleteBySpuId(long spuId);


    /**
     * 根据商品id删除商品属性值(物理)
     *
     * @param spuId 商品id
     */

    void deleteBySpuIdPhysical(long spuId);

    /**
     * 根据商品id查询商品属性值
     *
     * @param spuId 商品id
     * @return 返回商品属性值
     */

    List<PmsGoodsAttributeValue> queryBySpuId(long spuId);
}
