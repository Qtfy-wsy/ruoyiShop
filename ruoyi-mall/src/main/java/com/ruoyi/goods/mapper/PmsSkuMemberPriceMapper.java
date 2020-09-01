package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsSkuMemberPrice;

import java.util.List;

/**
 * 单品的会员价Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsSkuMemberPriceMapper {
    /**
     * 查询单品的会员价
     *
     * @param id 单品的会员价ID
     * @return 单品的会员价
     */
    public PmsSkuMemberPrice selectPmsSkuMemberPriceById(Long id);

    /**
     * 查询单品的会员价列表
     *
     * @param pmsSkuMemberPrice 单品的会员价
     * @return 单品的会员价集合
     */
    public List<PmsSkuMemberPrice> selectPmsSkuMemberPriceList(PmsSkuMemberPrice pmsSkuMemberPrice);

    /**
     * 新增单品的会员价
     *
     * @param pmsSkuMemberPrice 单品的会员价
     * @return 结果
     */
    public int insertPmsSkuMemberPrice(PmsSkuMemberPrice pmsSkuMemberPrice);

    /**
     * 修改单品的会员价
     *
     * @param pmsSkuMemberPrice 单品的会员价
     * @return 结果
     */
    public int updatePmsSkuMemberPrice(PmsSkuMemberPrice pmsSkuMemberPrice);

    /**
     * 删除单品的会员价
     *
     * @param id 单品的会员价ID
     * @return 结果
     */
    public int deletePmsSkuMemberPriceById(Long id);

    /**
     * 批量删除单品的会员价
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsSkuMemberPriceByIds(Long[] ids);

    /**
     * 新增单品会员价
     *
     * @param skuMemberPrices 单品会员价
     */
    void addSkuMemberPrices(List<PmsSkuMemberPrice> skuMemberPrices);

    /**
     * 根据单品id查询单品会员价格
     *
     * @param skuId 单品id
     * @return 返回单品会员价格
     */
    List<PmsSkuMemberPrice> queryBySkuId(String skuId);

    /**
     * 根据商品id删除单品会员价
     *
     * @param spuId 商品id
     */
    void deleteBySpuId(long spuId);

    /**
     * 根据商品id删除单品会员价(物理)
     *
     * @param spuId 商品id
     */
    void deleteBySpuIdPhysical(long spuId);

    /**
     * 根据会员等级id删除会员价格
     *
     * @param customerLevelId 会员等级id
     */
    void deleteByLevelId(long customerLevelId);


    /**
     * 根据商品id集合查询有会员价的商品数量
     *
     * @param skuIds 商品id集合
     * @return 有会员价的商品数量
     */
    int querySkuMemberPriceCountBySkuIds(List<String> skuIds);
}
