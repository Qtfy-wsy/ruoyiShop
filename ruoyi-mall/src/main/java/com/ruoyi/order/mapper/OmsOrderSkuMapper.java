package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.vo.SkuSaleAmount;

import java.util.List;
import java.util.Map;

/**
 * 订单单品Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface OmsOrderSkuMapper {
    /**
     * 查询订单单品
     *
     * @param id 订单单品ID
     * @return 订单单品
     */
    public OmsOrderSku selectOmsOrderSkuById(Long id);

    /**
     * 根据订单id查询订单单品
     *
     * @param orderId 订单id
     * @return 返回订单单品信息
     */

    List<OmsOrderSku> queryByOrderId(long orderId);

    /**
     * 根据订单id和单品id查询订单商品
     *
     * @param params 查询参数
     * @return 返回订单商品
     */

    OmsOrderSku queryByOrderIdAndSkuId(Map<String, Object> params);

    /**
     * 保存订单的单品信息
     *
     * @param orderSkus 订单单品
     * @return 成功>0 失败=0
     */

    int saveOrderSkus(List<OmsOrderSku> orderSkus);

    /**
     * 查询推荐商品
     *
     * @param pageSize 查询数量
     */

    List<OmsOrderSku> queryRecommentSkus(int pageSize);


    /**
     * 查询单品销量
     *
     * @param skuId 单品id
     */

    Integer querySkuSaleCount(String skuId);

    /**
     * 修改订单单品金额
     *
     * @param orderSku 订单单品
     */

    void updateOrderSkuPrice(OmsOrderSku orderSku);

    /**
     * 分页查询单品销量排行
     *
     * @param params 查询参数
     * @return 单品销量排行
     */

    List<SkuSaleAmount> querySkuSaleVolume(Map<String, Object> params);

    /**
     * 查询单品销售种数
     *
     * @param params 查询参数
     * @return 单品销售种数
     */

    int querySkuSaleCountByTime(Map<String, Object> params);

    /**
     * 分页查询单品销售额排行
     *
     * @param params 查询参数
     * @return 单品销售额排行
     */

    List<SkuSaleAmount> querySkuSaleAmount(Map<String, Object> params);

    /**
     * 查询店铺30天内热销商品
     *
     * @param storeId 店铺id
     * @return 热销商品
     */

    List<OmsOrderSku> queryRecommentSkusThirtyDays(long storeId);


    /**
     * 根据订单订单id,查询该订单下的sku销量信息( 因字段过多, 仅查询 skuid, num 等必要信息)
     *
     * @param orderId 订单 id
     * @return 结果集
     */
    List<OmsOrderSku> queryByOrderIdForCommunityBuy(Long orderId);

    /**
     * 查询订单单品列表
     *
     * @param omsOrderSku 订单单品
     * @return 订单单品集合
     */
    public List<OmsOrderSku> selectOmsOrderSkuList(OmsOrderSku omsOrderSku);

    /**
     * 新增订单单品
     *
     * @param omsOrderSku 订单单品
     * @return 结果
     */
    public int insertOmsOrderSku(OmsOrderSku omsOrderSku);

    /**
     * 修改订单单品
     *
     * @param omsOrderSku 订单单品
     * @return 结果
     */
    public int updateOmsOrderSku(OmsOrderSku omsOrderSku);

    /**
     * 删除订单单品
     *
     * @param id 订单单品ID
     * @return 结果
     */
    public int deleteOmsOrderSkuById(Long id);

    /**
     * 批量删除订单单品
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsOrderSkuByIds(Long[] ids);
}
