package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.vo.CustomerConsumption;
import com.ruoyi.order.vo.CustomerOrderAmount;
import com.ruoyi.order.vo.MarketingOrderStatistics;
import com.ruoyi.order.vo.StoreSaleAmount;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface OmsOrderMapper {
    /**
     * 查询订单
     *
     * @param id 订单ID
     * @return 订单
     */
    public OmsOrder selectOmsOrderById(Long id);

    /**
     * 查询订单列表
     *
     * @param omsOrder 订单
     * @return 订单集合
     */
    public List<OmsOrder> selectOmsOrderList(OmsOrder omsOrder);

    /**
     * 新增订单
     *
     * @param omsOrder 订单
     * @return 结果
     */
    public int insertOmsOrder(OmsOrder omsOrder);

    /**
     * 修改订单
     *
     * @param omsOrder 订单
     * @return 结果
     */
    public int updateOmsOrder(OmsOrder omsOrder);

    /**
     * 删除订单
     *
     * @param id 订单ID
     * @return 结果
     */
    public int deleteOmsOrderById(Long id);

    /**
     * 查询订单总数
     *
     * @param params 查询参数
     * @return 返回订单总数
     */

    int queryOrderCount(Map<String, Object> params);

    /**
     * 分页查询订单
     *
     * @param params 查询参数
     * @return 返回订单
     */

    List<OmsOrder> queryOrders(Map<String, Object> params);

    /**
     * 确认付款
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int confirmOrder(Map<String, Object> params);

    /**
     * 修改定金预售的第一阶段的订单状态
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int confirmPreSaleOrder(Map<String, Object> params);

    /**
     * 取消订单
     *
     * @param params 参数
     * @return 成功返回 1 失败返回0
     */

    int cancelOrder(Map<String, Object> params);

    /**
     * 修改价格
     *
     * @param params 参数
     * @return 成功返回1  失败返回0
     */

    int modifyPrice(Map<String, Object> params);

    /**
     * 查询订单的运费模版id
     *
     * @param params 查询参数
     * @return 返回订单的运费模版id
     */

    long queryTemplateId(Map<String, Object> params);

    /**
     * 发货
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int deliverOrder(Map<String, Object> params);

    /**
     * 修改物流单号
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int changeExpressNo(Map<String, Object> params);

    /**
     * 根据订单id查询订单信息
     *
     * @param params 查询参数
     * @return 返回订单信息
     */

    OmsOrder queryOrderById(Map<String, Object> params);

    /**
     * 查询所有店铺订单总数
     *
     * @param params 查询参数
     * @return 返回所有店铺订单总数
     */

    int queryStoreOrdersCount(Map<String, Object> params);

    /**
     * 查询所有店铺订单
     *
     * @param params 查询参数
     * @return 返回所有店铺订单
     */

    List<OmsOrder> queryStoreOrders(Map<String, Object> params);

    /**
     * 保存订单
     *
     * @param order 订单信息
     * @return 成功返回>0 失败返回0
     */

    int saveOrder(OmsOrder order);

    /**
     * 更新订单为已支付状态
     *
     * @param params 参数
     * @return 成功>0 失败返回0
     */

    int updateOrderPayed(Map<String, Object> params);

    /**
     * 更新定金预售订单第一阶段为已支付状态
     *
     * @param params 参数
     * @return 成功>0 失败返回0
     */

    int updatePreSaleOrderPayed(Map<String, Object> params);

    /**
     * 查询待付款的订单
     *
     * @param params 查询参数
     * @return 返回待付款的订单
     */

    List<OmsOrder> queryToPayOrder(Map<String, Object> params);


    /**
     * 查询订单
     *
     * @param params 查询参数
     * @return 返回订单
     */

    List<OmsOrder> queryOrderByOrderCode(Map<String, Object> params);


    /**
     * 查询订单总数(前端查询订单)
     *
     * @param params 查询参数
     * @return 返回订单总数
     */

    int queryOrderCountForSite(Map<String, Object> params);

    /**
     * 分页查询订单(前端查询订单)
     *
     * @param params 查询参数
     * @return 返回订单
     */

    List<OmsOrder> queryOrdersForSite(Map<String, Object> params);

    /**
     * 确认收货
     *
     * @param params 请求参数
     * @return 成返回>0 失败返回0
     */

    int confirmReceipt(Map<String, Object> params);

    /**
     * 查询用户待支付的订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待支付的订单总数
     */

    int toPayOrderCount(long customerId);

    /**
     * 查询用户待发货的订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待发货的订单总数
     */

    int toDeliverOrderCount(long customerId);

    /**
     * 查询用户待确认收货的订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待确认收货的订单总数
     */

    int toReceiptOrderCount(long customerId);

    /**
     * 查询用户待评论的订单总数
     *
     * @param customerId 用户id
     * @return 返回用户待评论的订单总数
     */

    int toEvaluateOrderCount(long customerId);

    /**
     * 修改订单退款退货状态
     *
     * @param params 参数
     * @return 成功>1 其他失败
     */

    int updateOrderBack(Map<String, Object> params);

    /**
     * 查询店铺总的单品销量
     *
     * @param storeId 店铺id
     * @return 返回店铺总的单品销量
     */

    int saleNum(long storeId);

    /**
     * 修改订单为已评论状态
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updateOrderEvluation(Map<String, Object> params);

    /**
     * 查找需要自动确认收货的订单
     *
     * @param day 天数
     * @return 需自动确认收货的订单
     */

    List<OmsOrder> queryOrdersForConfirmReceipt(@Param("day") int day);

    /**
     * 查找需要自动取消的订单
     *
     * @return 需自动取消的订单
     */

    List<OmsOrder> queryOrdersForCancel();

    /**
     * 查找需要自动取消的订单
     *
     * @return 需自动取消的订单
     */

    List<OmsOrder> queryDepositPreSaleOrdersForCancel();

    /**
     * 查询用户下单量总记录数
     *
     * @param params 查询参数
     * @return 返回用户下单量总记录数
     */

    int queryCustomerOrderAmountsCount(Map<String, Object> params);

    /**
     * 查询用户下单量
     *
     * @param params 查询参数
     * @return 返回用户下单量数据
     */

    List<CustomerOrderAmount> queryCustomerOrderAmounts(Map<String, Object> params);

    /**
     * 查询用户消费额总数
     *
     * @param params 查询参数
     * @return 返回用户消费额总记录数
     */

    int queryCustomerConsumptionCount(Map<String, Object> params);

    /**
     * 查询用户消费额
     *
     * @param params 查询参数
     * @return 返回用户消费额
     */

    List<CustomerConsumption> queryCustomerConsumption(Map<String, Object> params);

    /**
     * 分页查询店铺销量排行
     *
     * @param params 查询参数
     * @return 店铺销量排行
     */

    List<StoreSaleAmount> queryStoreSaleVolume(Map<String, Object> params);

    /**
     * 查询期间内有多少店铺有订单
     *
     * @param params 查询参数
     * @return 店铺数量
     */

    int queryStoreSaleCountByTime(Map<String, Object> params);

    /**
     * 分页查询店铺销售额排行
     *
     * @param params 查询参数
     * @return 店铺销售额排行
     */

    List<StoreSaleAmount> queryStoreSaleAmount(Map<String, Object> params);

    /**
     * 分页查询分销订单
     *
     * @param params 参数
     * @return 分销订单
     */

    List<OmsOrder> querySpreadOrders(Map<String, Object> params);

    /**
     * 查询分销订单数量
     *
     * @param params 参数
     * @return 分销订单数量
     */

    int querySpreadOrdersCount(Map<String, Object> params);

    /**
     * 查找订单信息(导出用)
     *
     * @param params 查询参数
     * @return 订单信息集合
     */

    List<OmsOrder> queryOrdersByIds(Map<String, Object> params);

    /**
     * 查找所有订单信息(导出用)
     *
     * @param params 查询参数
     * @return 订单信息
     */

    List<OmsOrder> queryAllOrder(Map<String, Object> params);

    /**
     * 查找未付款拼团订单信息(导出用)
     *
     * @param params 查询参数
     * @return 订单信息集合
     */

    List<OmsOrder> queryNotPayGroupOrdersByIds(Map<String, Object> params);

    /**
     * 查找所有未付款拼团订单信息(导出用)
     *
     * @param params 查询参数
     * @return 所有订单信息
     */

    List<OmsOrder> queryAllNotPayGroupOrder(Map<String, Object> params);

    /**
     * 查询今日店铺销售额
     *
     * @param storeId 店铺id
     * @return 返回今日店铺销售额
     */

    BigDecimal querySaleAmountToday(long storeId);

    /**
     * 查询今日店铺销售量
     *
     * @param storeId 店铺id
     * @return 返回今日店铺销售量
     */

    int querySaleCountToday(long storeId);

    /**
     * 查询本周店铺销售额
     *
     * @param storeId 店铺id
     * @return 返回本周店铺销售额
     */

    BigDecimal querySaleAmountThisWeek(long storeId);

    /**
     * 查询本周店铺销售量
     *
     * @param storeId 店铺id
     * @return 返回本周店铺销售量
     */

    int querySaleCountThisWeek(long storeId);

    /**
     * 查询本周店铺销售额（按日期分组）
     *
     * @param storeId 店铺id
     * @return 返回本周店铺销售额
     */

    List<StoreSaleAmount> querySaleAmountThisWeekGroupByDay(long storeId);

    /**
     * 查询本周店铺销售量（按日期分组）
     *
     * @param storeId 店铺id
     * @return 返回本周店铺销售量
     */

    List<StoreSaleAmount> querySaleCountThisWeekGroupByDay(long storeId);

    /**
     * 查询众筹促销的订单数
     *
     * @param marketingId 众筹促销id
     * @param storeId     店铺id
     * @return 订单数量
     */

    int queryCrowdFundingOrderCount(@Param("marketingId") long marketingId, @Param("storeId") long storeId);

    /**
     * 设置订单的状态为已经完成
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updateOrderFinished(Map<String, Object> params);

    /**
     * 设置订单为已完成状态
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int confirmOrderFinished(Map<String, Object> params);

    /**
     * 查询众筹促销的支持人数
     *
     * @param params 查询参数
     * @return 支持人数
     */

    int queryCrowFundingCustomerCount(Map<String, Object> params);

    /**
     * 根据众筹id查询所有众筹订单
     *
     * @param params 查询参数
     * @return 众筹订单集合
     */

    List<OmsOrder> queryAllCrowdFundingOrderList(Map<String, Object> params);

    /**
     * 更新众筹订单抽奖状态为抽中
     *
     * @param orderId 订单id
     * @return 1成功 否则失败
     */

    int updateCrowdFundingOrderLotteryStatus(@Param("orderId") long orderId);


    /**
     * 查询订单总数
     *
     * @param params 查询参数
     * @return 返回订单总数
     */

    int queryCrowdFundingOrdersCount(Map<String, Object> params);

    /**
     * 分页查询订单
     *
     * @param params 查询参数
     * @return 返回订单
     */

    List<OmsOrder> queryCrowdFundingOrders(Map<String, Object> params);

    /**
     * 查询今日付款订单数
     *
     * @param storeId 店铺id
     * @return 返回今日付款订单数
     */

    int queryTodayPayedOrderNum(long storeId);

    /**
     * 查询今日待发货订单总数
     *
     * @param storeId 店铺id
     * @return 返回今日待发货订单总数
     */

    int queryToDeliveryOrderNum(long storeId);

    /**
     * 查询促销订单统计
     *
     * @param params 查询参数
     * @return 促销订单统计
     */

    MarketingOrderStatistics queryMarketingOrderStatistics(Map<String, Object> params);

    /**
     * 查询用户社区团购订单的总纪录数
     *
     * @param params 参数
     * @return 返回用户社区团购订单的总记录数
     */

    int queryCustomerCommunityOrdersNum(Map<String, Object> params);

    /**
     * 查询用户社区团购额订单
     *
     * @param params 查询参数
     * @return 返回用户社区团购的订单
     */

    List<OmsOrder> queryCustomerCommunityOrders(Map<String, Object> params);


    /**
     * 查找需要自动取消的社区团购订单（30分钟前还没付款的订单）
     *
     * @return 需自动取消的订单
     */

    List<OmsOrder> queryCommunityOrdersForCancel();


    /**
     * 查询团长下面的社区团购订单的总纪录数
     *
     * @param params 参数
     * @return 返回团长下面的社区团购订单的总记录数
     */

    int queryHeadCommunityOrdersCount(Map<String, Object> params);

    /**
     * 查询团长下面的社区团购订单
     *
     * @param params 查询参数
     * @return 返回团长下面的社区团购的订单
     */

    List<OmsOrder> queryHeadCommunityOrders(Map<String, Object> params);

    /**
     * 查询社区团购已参团订单（按会员分组）
     *
     * @param params 查询参数
     * @return 社区团购已参团订单
     */

    List<OmsOrder> queryJoinCommunityOrders(Map<String, Object> params);

    /**
     * 查询社区团购已下单会员人数
     *
     * @param params 查询参数
     * @return 社区团购已下单会员人数
     */

    int queryJoinCommunityOrdersCount(Map<String, Object> params);


    /**
     * 查询社区团购订单(用于 PC 端计算销量)
     *
     * @param communityBuyId 社区团购 id
     * @return 社区团购订单id 集
     */

    List<Long> queryOrderIdsByCommunityBuyId(Long communityBuyId);

    /**
     * 查询社区团购的订单
     *
     * @param params 参数
     * @return 返回社区团购的订单
     */

    List<OmsOrder> queryOrdersByCommunityBuyId(Map<String, Object> params);

    /**
     * 分页查询社区团购订单（后端）
     *
     * @param params 查询参数
     * @return 社区团购订单
     */

    List<OmsOrder> queryCommunityOrders(Map<String, Object> params);

    /**
     * 分页查询社区团购订单总记录数（后端）
     *
     * @param params 查询参数
     * @return 社区团购订单
     */

    int queryCommunityOrderCount(Map<String, Object> params);


    /**
     * 社区团购订单确认收货
     *
     * @param params 请求参数
     * @return 成返回>0 失败返回0
     */

    int confirmCommunityOrderReceipt(Map<String, Object> params);

    /**
     * 根据订单id集合查询社区团购订单集合
     *
     * @param params 查询参数
     * @return 社区团购订单集合
     */
    List<OmsOrder> queryCommunityOrdersByIds(Map<String, Object> params);

    /**
     * 查询所有社区团购订单集合
     *
     * @param params 查询参数
     * @return 所有社区团购订单集合
     */
    List<OmsOrder> queryAllCommunityOrders(Map<String, Object> params);


    /**
     * 根据团长的会员ID 和 团购活动ID 查询该团长在该活动中所有的销售订单 字段过多 仅查询必要字段
     *
     * @param param 参数集
     * @return 订单集
     */

    List<OmsOrder> queryCommunityBuyOrders(Map<String, Object> param);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsOrderByIds(Long[] ids);

    /**
     * 分页查询店铺订单
     * @param omsOrder 订单
     * @return 订单集
     */
    List<OmsOrder> queryStoreOrderList(OmsOrder omsOrder);
}
