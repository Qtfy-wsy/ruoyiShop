package com.ruoyi.store.service;

import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.order.vo.StoreSaleAmount;
import com.ruoyi.store.domain.TStoreOrder;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 门店订单Service接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface ITStoreOrderService {
    /**
     * 查询门店订单
     *
     * @param id 门店订单ID
     * @return 门店订单
     */
    public TStoreOrder selectTStoreOrderById(Long id);

    /**
     * 分页查询门店订单
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询参数
     * @return 门店订单集合
     */
    PageHelper<TStoreOrder> queryStoreOrderList(PageHelper<TStoreOrder> pageHelper, TStoreOrder.QueryCriteria queryCriteria);

    /**
     * 分页查询门店订单(前台使用)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询参数
     * @return 门店订单集合
     */
    PageHelper<TStoreOrder> queryStoreOrderListForSite(PageHelper<TStoreOrder> pageHelper, TStoreOrder.QueryCriteria queryCriteria);

    /**
     * 提货
     *
     * @param orderId 订单id
     * @param storeId 门店id
     * @return 1成功 0失败
     */
    int pickUpGoods(long orderId, long storeId);

    /**
     * 根据订单id和核销id查询门店订单
     *
     * @param orderId      订单id
     * @param writeOffCode 核销码
     * @param storeId      店铺id
     * @return 返回门店订单
     */
    TStoreOrder queryStoreOrderByOrderIdAndWriteOffCode(long orderId, String writeOffCode, long storeId);

    /**
     * 取消订单
     *
     * @param orderId      订单id
     * @param storeId      门店id
     * @param cancelReason 取消原因
     * @param customerId   用户id
     * @return 1成功 否则失败
     */
    int cancelStoreOrder(long orderId, long storeId, String cancelReason, long customerId);


    /**
     * 保存门店订单
     *
     * @param storeOrder 门店订单
     * @return 成功返回1 失败返回0
     */
    int saveOrder(TStoreOrder storeOrder);

    /**
     * 根据订单id查询门店订单详情
     *
     * @param orderId    订单id
     * @param storeId    门店id
     * @param customerId 用户id
     * @param orderItems
     * @return 订单实体
     */
    TStoreOrder queryByOrderId(long orderId, long storeId, long customerId, OrderItem... orderItems);


    /**
     * 查询待付款的门店订单信息
     *
     * @param orderCode  订单code
     * @param customerId 会员id
     * @param orderItems 查询范围
     * @return 返回待付款的订单信息
     */
    List<TStoreOrder> queryToPayStoreOrder(String orderCode, long customerId, OrderItem... orderItems);

    /**
     * 修改订单为已支付状态
     *
     * @param customerId      会员id
     * @param orderCodes      订单code
     * @param isPredepositPay 是否为预存款支付 0 否 1 是
     * @param transCode       交易流水号
     * @param channel         交易渠道
     * @return 成功返回>=1 失败返回0
     */
    int confirmOrderPayed(long customerId, List<String> orderCodes, String isPredepositPay, String transCode, String channel);

    /**
     * 根据门店订单code查询订单信息
     *
     * @param orderCode  订单code
     * @param customerId 会员id
     * @param orderItems 查询条件
     * @return 返回门店订单信息
     */
    List<TStoreOrder> queryOrderByOrderCode(String orderCode, long customerId, OrderItem... orderItems);


    /**
     * 查询用户待支付的门店订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待支付的门店订单总数
     */
    int toPayOrderCount(long customerId);

    /**
     * 查询用户待取货的门店订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待取货的门店订单总数
     */
    int toPickUpOrderCount(long customerId);


    /**
     * 查询用户待评论的门店订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待评论的门店订单总数
     */
    int toEvaluateOrderCount(long customerId);

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
     * 批量删除门店订单
     *
     * @param ids 需要删除的门店订单ID
     * @return 结果
     */
    public int deleteTStoreOrderByIds(Long[] ids);

    /**
     * 删除门店订单信息
     *
     * @param id 门店订单ID
     * @return 结果
     */
    public int deleteTStoreOrderById(Long id);
}
