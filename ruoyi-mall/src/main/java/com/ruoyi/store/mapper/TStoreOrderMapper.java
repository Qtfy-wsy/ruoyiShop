package com.ruoyi.store.mapper;

import com.ruoyi.order.vo.StoreSaleAmount;
import com.ruoyi.store.domain.TStoreOrder;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 门店订单Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface TStoreOrderMapper {
    /**
     * 查询门店订单
     *
     * @param id 门店订单ID
     * @return 门店订单
     */
    public TStoreOrder selectTStoreOrderById(Long id);

    /**
     * 根据参数查询门店订单集合
     *
     * @param params 查询参数
     * @return 门店订单集合
     */

    List<TStoreOrder> queryStoreOrderList(Map<String, Object> params);

    /**
     * 根据参数查询门店订单数量
     *
     * @param params 查询参数
     * @return 门店订单数量
     */

    int queryStoreOrderListCount(Map<String, Object> params);

    /**
     * 根据参数查询门店订单集合(前台使用)
     *
     * @param params 查询参数
     * @return 门店订单集合
     */

    List<TStoreOrder> queryStoreOrderListForSite(Map<String, Object> params);

    /**
     * 根据参数查询门店订单数量(前台使用)
     *
     * @param params 查询参数
     * @return 门店订单数量
     */

    int queryStoreOrderListCountForSite(Map<String, Object> params);

    /**
     * 根据订单id和核销码查询门店订单实体
     *
     * @param orderId      订单id
     * @param writeOffCode 核销码
     * @param storeId      店铺id
     * @return 门店订单实体
     */

    TStoreOrder queryStoreOrderByOrderIdAndWriteOffCode(@Param("orderId") long orderId, @Param("writeOffCode") String writeOffCode, @Param("storeId") long storeId);

    /**
     * 提货
     *
     * @param orderId 订单id
     * @param storeId 门店id
     * @return 1成功 否则失败
     */

    int pickUpGoods(@Param("orderId") long orderId, @Param("storeId") long storeId);

    /**
     * 取消订单
     *
     * @param orderId      订单id
     * @param storeId      门店id
     * @param cancelReason 取消原因
     * @param customerId   用户id
     * @return 1成功 否则失败
     */

    int cancelStoreOrder(@Param("orderId") long orderId, @Param("storeId") long storeId, @Param("cancelReason") String cancelReason, @Param("customerId") long customerId);

    /**
     * 保存门店订单
     *
     * @param storeOrder 门店订单
     * @return 成功返回1 失败返回0
     */

    int saveStoreOrder(TStoreOrder storeOrder);

    /**
     * 根据订单id查询门店订单详情
     *
     * @param orderId    订单id
     * @param storeId    门店id
     * @param customerId 用户id
     * @return 订单实体
     */

    TStoreOrder queryByOrderId(@Param("orderId") long orderId, @Param("storeId") long storeId, @Param("customerId") long customerId);

    /**
     * 查询待支付的门店订单
     *
     * @return 返回待支付的门店订单
     */

    List<TStoreOrder> queryToPayStoreOrder(Map<String, Object> params);

    /**
     * 修改订单为已支付状态
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int confirmOrderPayed(Map<String, Object> params);

    /**
     * 根据订单code查询订单信息
     *
     * @param params 参数
     * @return 返回订单信息
     */

    List<TStoreOrder> queryOrderByOrderCode(Map<String, Object> params);

    /**
     * 查询用户待支付的门店订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待支付的门店订单总数
     */

    int toPayOrderCount(@Param("customerId") long customerId);

    /**
     * 查询用户待取货的门店订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待取货的门店订单总数
     */

    int toPickUpOrderCount(@Param("customerId") long customerId);


    /**
     * 查询用户待评论的门店订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待评论的门店订单总数
     */

    int toEvaluateOrderCount(@Param("customerId") long customerId);

    /**
     * 查询今日门店销售额
     *
     * @param storeId 店铺id
     * @return 返回今日门店销售额
     */

    BigDecimal queryStoreSaleAmountToday(long storeId);

    /**
     * 查询今日门店销售量
     *
     * @param storeId 店铺id
     * @return 返回今日门店销售量
     */

    int queryStoreSaleCountToday(long storeId);

    /**
     * 查询本周门店销售额
     *
     * @param storeId 店铺id
     * @return 返回本周门店销售额
     */

    BigDecimal queryStoreSaleAmountThisWeek(long storeId);

    /**
     * 查询本周门店销售额（按日期分组）
     *
     * @param storeId 店铺id
     * @return 返回本周门店销售额
     */

    List<StoreSaleAmount> queryStoreSaleAmountThisWeekGroupByDay(long storeId);

    /**
     * 查询门店订单列表
     *
     * @param tStoreOrder 门店订单
     * @return 门店订单集合
     */
    public List<TStoreOrder> selectTStoreOrderList(TStoreOrder tStoreOrder);

    /**
     * 新增门店订单
     *
     * @param tStoreOrder 门店订单
     * @return 结果
     */
    public int insertTStoreOrder(TStoreOrder tStoreOrder);

    /**
     * 修改门店订单
     *
     * @param tStoreOrder 门店订单
     * @return 结果
     */
    public int updateTStoreOrder(TStoreOrder tStoreOrder);

    /**
     * 删除门店订单
     *
     * @param id 门店订单ID
     * @return 结果
     */
    public int deleteTStoreOrderById(Long id);

    /**
     * 批量删除门店订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTStoreOrderByIds(Long[] ids);
}
