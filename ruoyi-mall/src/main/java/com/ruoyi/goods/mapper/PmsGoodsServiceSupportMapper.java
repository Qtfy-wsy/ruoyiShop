package com.ruoyi.goods.mapper;

import com.ruoyi.goods.domain.PmsGoodsServiceSupport;

import java.util.List;

/**
 * 商品和服务支持的关联Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface PmsGoodsServiceSupportMapper {
    /**
     * 查询商品和服务支持的关联
     *
     * @param id 商品和服务支持的关联ID
     * @return 商品和服务支持的关联
     */
    public PmsGoodsServiceSupport selectPmsGoodsServiceSupportById(Long id);

    /**
     * 查询商品和服务支持的关联列表
     *
     * @param pmsGoodsServiceSupport 商品和服务支持的关联
     * @return 商品和服务支持的关联集合
     */
    public List<PmsGoodsServiceSupport> selectPmsGoodsServiceSupportList(PmsGoodsServiceSupport pmsGoodsServiceSupport);

    /**
     * 新增商品和服务支持的关联
     *
     * @param pmsGoodsServiceSupport 商品和服务支持的关联
     * @return 结果
     */
    public int insertPmsGoodsServiceSupport(PmsGoodsServiceSupport pmsGoodsServiceSupport);

    /**
     * 修改商品和服务支持的关联
     *
     * @param pmsGoodsServiceSupport 商品和服务支持的关联
     * @return 结果
     */
    public int updatePmsGoodsServiceSupport(PmsGoodsServiceSupport pmsGoodsServiceSupport);

    /**
     * 删除商品和服务支持的关联
     *
     * @param id 商品和服务支持的关联ID
     * @return 结果
     */
    public int deletePmsGoodsServiceSupportById(Long id);

    /**
     * 批量删除商品和服务支持的关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deletePmsGoodsServiceSupportByIds(Long[] ids);

    /**
     * 新增商品服务支持
     *
     * @param spuServiceSupports 商品服务支持
     */

    void addSpuServicceSupportServices(List<PmsGoodsServiceSupport> spuServiceSupports);

    /**
     * 根据商品id查询商品服务支持
     *
     * @param spuId 商品id
     * @return 返回商品服务支持
     */

    List<PmsGoodsServiceSupport> queryBySpuId(long spuId);

    /**
     * 根据商品id删除商品服务支持
     *
     * @param spuId 商品id
     */

    void deleteBySpuId(long spuId);

    /**
     * 根据商品id删除商品服务支持(物理)
     *
     * @param spuId 商品id
     */

    void deleteBySpuIdPhysical(long spuId);

}
