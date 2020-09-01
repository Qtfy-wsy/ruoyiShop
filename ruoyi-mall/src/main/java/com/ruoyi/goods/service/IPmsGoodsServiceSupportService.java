package com.ruoyi.goods.service;

import com.ruoyi.goods.domain.PmsGoodsServiceSupport;
import com.ruoyi.goods.domain.SpuServiceSupportItem;

import java.util.List;

/**
 * 商品和服务支持的关联Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IPmsGoodsServiceSupportService {
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
     * 批量删除商品和服务支持的关联
     *
     * @param ids 需要删除的商品和服务支持的关联ID
     * @return 结果
     */
    public int deletePmsGoodsServiceSupportByIds(Long[] ids);

    /**
     * 删除商品和服务支持的关联信息
     *
     * @param id 商品和服务支持的关联ID
     * @return 结果
     */
    public int deletePmsGoodsServiceSupportById(Long id);

    /**
     * 新增商品服务支持
     *
     * @param spuServiceSupports 商品服务支持
     */
    void addSpuServicceSupport(List<PmsGoodsServiceSupport> spuServiceSupports);

    /**
     * 根据商品id 查询商品的服务支持
     *
     * @param spuId                  商品id
     * @param spuServiceSupportItems 查询条件
     * @return 返回商品的服务支持
     */
    List<PmsGoodsServiceSupport> queryBySpuId(long spuId, SpuServiceSupportItem... spuServiceSupportItems);

    /**
     * 根据商品id删除商品服务支持
     *
     * @param spuId 商品id
     */
    void deleteBySpuId(long spuId);

    /**
     * 更新商品服务支持
     *
     * @param spuServiceSupports 商品服务支持
     * @param spuId              商品id
     */
    void updateSpuServiceSupport(List<PmsGoodsServiceSupport> spuServiceSupports, long spuId);
}
