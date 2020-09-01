package com.ruoyi.store.mapper;

import com.ruoyi.store.domain.TStoreOrderSku;

import java.util.List;

/**
 * 门店订单单品信息Mapper接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface TStoreOrderSkuMapper {
    /**
     * 查询门店订单单品信息
     *
     * @param id 门店订单单品信息ID
     * @return 门店订单单品信息
     */
    public TStoreOrderSku selectTStoreOrderSkuById(Long id);

    /**
     * 根据订单id查询订单单品
     *
     * @param orderId 订单id
     * @return 返回订单单品信息
     */

    List<TStoreOrderSku> queryByStoreOrderId(long orderId);

    /**
     * 保存订单的单品信息
     *
     * @param orderSkus 订单单品
     * @return 成功>0 失败=0
     */

    int saveStoreOrderSkus(List<TStoreOrderSku> orderSkus);

    /**
     * 查询门店30天内热销商品
     *
     * @param storeId 店铺id
     * @return 热销商品
     */

    List<TStoreOrderSku> queryStoreRecommentSkusThirtyDays(long storeId);

    /**
     * 查询门店订单单品信息列表
     *
     * @param tStoreOrderSku 门店订单单品信息
     * @return 门店订单单品信息集合
     */
    public List<TStoreOrderSku> selectTStoreOrderSkuList(TStoreOrderSku tStoreOrderSku);

    /**
     * 新增门店订单单品信息
     *
     * @param tStoreOrderSku 门店订单单品信息
     * @return 结果
     */
    public int insertTStoreOrderSku(TStoreOrderSku tStoreOrderSku);

    /**
     * 修改门店订单单品信息
     *
     * @param tStoreOrderSku 门店订单单品信息
     * @return 结果
     */
    public int updateTStoreOrderSku(TStoreOrderSku tStoreOrderSku);

    /**
     * 删除门店订单单品信息
     *
     * @param id 门店订单单品信息ID
     * @return 结果
     */
    public int deleteTStoreOrderSkuById(Long id);

    /**
     * 批量删除门店订单单品信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreOrderSkuByIds(Long[] ids);
}
