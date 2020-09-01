package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.vo.GroupOrder;
import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.order.vo.QueryOrderCriteria;
import com.ruoyi.order.vo.StoreSaleAmount;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Consumer;

/**
 * 订单Service接口
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
public interface IOmsOrderService {
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
     * 批量删除订单
     *
     * @param ids 需要删除的订单ID
     * @return 结果
     */
    public int deleteOmsOrderByIds(Long[] ids);

    /**
     * 删除订单信息
     *
     * @param id 订单ID
     * @return 结果
     */
    public int deleteOmsOrderById(Long id);

    /**
     * 分页查询订单(后端使用)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回订单数据
     */
    PageHelper<OmsOrder> queryOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria, OrderItem... orderItems);

    /**
     * 查询所有店铺的订单(后端使用)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回订单数据
     */
    PageHelper<OmsOrder> queryStoreOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria);

    /**
     * 确认付款
     *
     * @param id                   订单id
     * @param storeId              店铺id
     * @param orderConsumer        拼团订单回调
     * @param crowdfundingConsumer 众筹订单回调
     * @return 成功返回1  失败返回0
     */
    int confirmOrder(long id, long storeId, Consumer<OmsOrder> orderConsumer, Consumer<OmsOrder> crowdfundingConsumer);

    /**
     * 取消订单
     *
     * @param id      订单id
     * @param storeId 店铺id
     * @param reason  取消原因
     * @return 成功返回1 失败返回0
     */
    int cancelOrder(long id, long storeId, String reason);

    /**
     * 修改价格
     *
     * @param id            订单id
     * @param price         修改的价格(不是最终价格)
     * @param reason        修改原因
     * @param storeId       店铺id
     * @param operationName 操作人
     * @return 成功返回 1 失败返回0 -1 订单最低支付1毛 -2 订单不能修改价格
     */
    int modifyPrice(long id, BigDecimal price, String reason, long storeId, String operationName);

    /**
     * 发货
     *
     * @param id          订单id
     * @param storeId     店铺id
     * @param waybillCode 运单号
     * @param companyName 物流公司名称
     * @param companyCode 物流公司code
     * @return 成功返回1 失败返回0
     */
    int deliverOrder(long id, long storeId, String waybillCode, String companyName, String companyCode);

    /**
     * 修改物流单号
     *
     * @param id            订单id
     * @param storeId       店铺id
     * @param waybillCode   运单号
     * @param operationName 操作人
     * @param companyId     物流公司id
     * @return 成功返回1 失败返回0 -2 订单正在申请退款 -1 订单号含有中文
     */
    int changeExpressNo(long id, long storeId, String waybillCode, String operationName, long companyId);

    /**
     * 根据订单id查询订单信息
     *
     * @param id         订单id
     * @param customerId 会员id 会员id 为-1  则不过滤会员id
     * @param storeId    店铺id 店铺id 为-1  则不必过滤店铺id
     * @param orderItems 设置订单的属性
     * @return 返回订单信息
     */
    OmsOrder queryOrderDetailById(long id, long customerId, long storeId, OrderItem... orderItems);


    /**
     * 保存订单信息
     *
     * @param order 订单
     */
    void saveOrder(OmsOrder order);

    /**
     * 修改订单状态为已支付状态
     *
     * @param orderCode            订单code
     * @param customerId           用户id
     * @param isPredepositPay      是否预存款支付 0 否 1 是
     * @param transCode            交易流水号
     * @param channel              支付渠道
     * @param orderConsumer        拼团订单回调
     * @param crowdfundingConsumer 众筹订单回调
     * @return 成功返回>0 失败返回0
     */
    int updateOrderPayed(List<String> orderCode, long customerId, int isPredepositPay, String transCode, String channel, Consumer<OmsOrder> orderConsumer, Consumer<OmsOrder> crowdfundingConsumer);

    /**
     * 查询待付款的订单信息
     *
     * @param orderCode  订单code
     * @param customerId 会员id
     * @param orderItems 查询范围
     * @return 返回待付款的订单信息
     */
    List<OmsOrder> queryToPayOrder(String orderCode, long customerId, OrderItem... orderItems);

    /**
     * 分页查询订单信息(前端)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询参数
     * @param orderItems    查询条件
     * @return 返回订单信息
     */
    PageHelper<OmsOrder> queryOrdersForSite(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria, OrderItem... orderItems);

    /**
     * 确认收货
     *
     * @param orderId    订单id
     * @param customerId 会员id
     * @return 成功返回1 失败返回0
     */
    int confirmReceipt(long orderId, long customerId);


    /**
     * 查找需要自动取消的订单
     *
     * @return 需自动取消的订单
     */
    List<OmsOrder> queryOrdersForCancel();

    /**
     * 查找需要自动取消定金预售的订单
     *
     * @return 需自动取消的定金预售订单
     */
    List<OmsOrder> queryDepositPreSaleOrdersForCancel();

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
     * 修改订单的退款退货状态
     *
     * @param orderId 订单id
     * @param status  状态
     * @return 成功>1 其他失败
     */
    int updateOrderBack(long orderId, String status);

    /**
     * 查询店铺总的单品销量
     *
     * @param storeId 店铺id
     * @return 返回店铺总的单品销量
     */
    int saleNum(long storeId);

    /**
     * 根据订单code查询订单集合
     *
     * @param orderCode  订单code
     * @param customerId 会员id
     * @return 返回订单信息
     */
    List<OmsOrder> queryOrderByOrderCode(String orderCode, long customerId, OrderItem... orderItems);

    /**
     * 修改订单状态为已经评论
     *
     * @param orderId    订单id
     * @param customerId 会员id
     * @return 成功>0 失败0
     */
    int updateOrderEvluation(long orderId, long customerId);

    /**
     * 查看物流
     *
     * @param orderId     订单id
     * @param customerId  会员id
     * @param callbackUrl 回调地址
     * @return 查看物流url
     */
    String getCheckLogisticsUrl(long orderId, long customerId, String callbackUrl);

    /**
     * 分页查询拼团订单
     *
     * @param queryCriteria     查询参数实体
     * @param pageHelper        分页帮助类
     * @param isNeedMemberOrder 是否查询团员订单，true 查询团员订单 false 不查询
     * @param isNeedOrderSku    是否需要订单商品
     * @return 拼团订单集合
     */
    PageHelper<GroupOrder> queryGroupOrders(GroupOrder.QueryCriteria queryCriteria, PageHelper<GroupOrder> pageHelper, boolean isNeedMemberOrder, boolean isNeedOrderSku);

    /**
     * 分页查询未支付的拼团订单
     *
     * @param queryCriteria 查询参数实体
     * @param pageHelper    分页帮助类
     * @return 拼团订单集合
     */
    PageHelper<GroupOrder> queryNotPayGroupOrders(GroupOrder.QueryCriteria queryCriteria, PageHelper<GroupOrder> pageHelper, boolean isNeedOrderSku);

    /**
     * 分页查询店铺拼团订单（admin端使用）
     *
     * @param queryCriteria 查询参数实体
     * @param pageHelper    分页帮助类
     * @return 拼团订单集合
     */
    PageHelper<GroupOrder> queryStoreGroupOrders(GroupOrder.QueryCriteria queryCriteria, PageHelper<GroupOrder> pageHelper);

    /**
     * 分页查询店铺未支付的拼团订单 （admin端使用）
     *
     * @param queryCriteria 查询参数实体
     * @param pageHelper    分页帮助类
     * @return 拼团订单集合
     */
    PageHelper<GroupOrder> queryStoreNotPayGroupOrders(GroupOrder.QueryCriteria queryCriteria, PageHelper<GroupOrder> pageHelper);

    /**
     * 查询拼团团员订单
     *
     * @param groupId         拼团id
     * @param isNeedFilterPay 是否过滤支付，true表示只查支付过的订单
     * @return 团员订单集合
     */
    List<GroupOrder> queryGroupMemberOrders(String groupId, boolean isNeedFilterPay);


    /**
     * 根据成团id查询团长信息
     *
     * @param groupId 成团id
     * @return 返回团长信息
     */
    GroupOrder queryGroupHeadByGroupId(String groupId);

    /**
     * 根据会员id和团id查询团购信息
     *
     * @param groupId    团id
     * @param customerId 会员id
     * @return 返回成团的订单信息
     */
    GroupOrder queryByGroupIdAndCustomerId(String groupId, long customerId);

    /**
     * 根据团id和会员id修改拼团订单状态
     *
     * @param orderId 订单id
     * @param status  状态
     * @return 成功返回1 失败返回0
     */
    int updateGroupStatus(long orderId, String status, int isUpdateTime);

    /**
     * 更新拼团订单定时任务处理状态
     *
     * @param orderId 订单id
     * @return 成功返回1 失败返回0
     */
    int updateAutoHandleStatus(long orderId);

    /**
     * 查询该团的团员数量
     *
     * @param groupId 团id
     * @return 返回团员数量
     */
    int queryGroupNum(String groupId);

    /**
     * 修改拼团成功
     *
     * @param groupId 团id
     * @return 成功>0  失败=0
     */
    int updateGroupSuccess(String groupId);

    /**
     * 根据团id和会员id取消订单
     *
     * @param groupId    团id
     * @param customerId 会员id
     * @return 成功>0 失败= 0
     */
    int calcOrderByGroupIdAndCustomerId(String groupId, long customerId);

    /**
     * 查询已经开团24小时以上的（未成团或未支付）的未处理拼团订单
     */
    List<GroupOrder> queryOpenGroupOrderForCancel();


    /**
     * 查询创建时间24小时以上的未支付的未处理拼团订单
     */
    List<GroupOrder> queryNotOpenGroupOrderForCancel();

    /**
     * 查找需要自动确认收货的订单
     *
     * @param day 天数
     * @return 需自动确认收货的订单
     */
    List<OmsOrder> queryOrdersForConfirmReceipt(int day);

    /**
     * 分页查询分销订单
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询参数
     * @return 返回分销订单数据
     */
    PageHelper<OmsOrder> querySpreadOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria);


    /**
     * 查找订单信息(导出用)
     *
     * @param ids     订单id集合
     * @param status  订单状态
     * @param storeId 店铺id
     * @return 订单信息集合
     */
    List<OmsOrder> queryOrdersByIdsWithOrderSku(List<Long> ids, String status, long storeId);

    /**
     * 查找所有订单信息(导出用)
     *
     * @param status      订单状态
     * @param storeId     店铺id
     * @param marketingId 促销id
     * @return 所有订单信息
     */
    List<OmsOrder> queryAllOrderWithOrderSku(String status, long storeId, Long marketingId);


    /**
     * 查找未付款拼团订单信息(导出用)
     *
     * @param ids     订单id集合
     * @param storeId 店铺id
     * @return 订单信息集合
     */
    List<OmsOrder> queryNotPayGroupOrdersByIdsWithOrderSku(List<Long> ids, long storeId);

    /**
     * 查找所有未付款拼团订单信息(导出用)
     *
     * @param storeId 店铺id
     * @return 所有订单信息
     */
    List<OmsOrder> queryAllNotPayGroupOrderWithOrderSku(long storeId);

    /**
     * 查找拼团订单信息(导出用)
     *
     * @param ids     订单id集合
     * @param status  订单状态
     * @param storeId 店铺id
     * @return 订单信息集合
     */
    List<GroupOrder> queryGroupOrdersByIdsWithOrderSku(List<Long> ids, String status, long storeId);

    /**
     * 查找所有拼团订单信息(导出用)
     *
     * @param status  订单状态
     * @param storeId 店铺id
     * @return 所有订单信息
     */
    List<GroupOrder> queryAllGroupOrderWithOrderSku(String status, long storeId);

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
     * @param storeId 店铺id量额
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
    int queryCrowdFundingOrderCount(long marketingId, long storeId);

    /**
     * 查询众筹促销的支持人数
     *
     * @param marketingId 众筹促销id
     * @param storeId     店铺id
     * @return 支持人数
     */
    int queryCrowFundingCustomerCount(long marketingId, long storeId);


    /**
     * 根据众筹id查询所有众筹订单
     *
     * @param marketingId 众筹促销id
     * @param storeId     店铺id
     * @return 众筹订单集合
     */
    List<OmsOrder> queryAllCrowdFundingOrderList(long marketingId, long storeId);

    /**
     * 更新众筹订单抽奖状态为抽中
     *
     * @param orderId 订单id
     * @return 1成功 否则失败
     */
    int updateCrowdFundingOrderLotteryStatus(long orderId);

    /**
     * 分页查询众筹订单(后端使用)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回众筹订单数据
     */
    PageHelper<OmsOrder> queryCrowdFundingOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria);

    /**
     * 查询今日付款订单数
     *
     * @param storeId 店铺id
     * @return 返回金额付款订单数
     */
    int queryTodayPayedOrderNum(long storeId);

    /**
     * 查询店铺待发货订单总数
     *
     * @param storeId 店铺id
     * @return 返回待发货订单总数
     */
    int queryToDeliveryOrderNum(long storeId);

    /**
     * 查询店铺待付款订单总数
     *
     * @param storeId 店铺id
     * @return 返回店铺待付款订单总数
     */
    int queryToPayedOrderNum(long storeId);

    /**
     * 查询用户的社区团购订单
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回用户的社区团购订单
     */
    PageHelper<OmsOrder> queryCustomerCommunityOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria);

    /**
     * 查找需要自动取消的社区团购订单（30分钟前还没付款的订单）
     *
     * @return 需自动取消的订单
     */
    List<OmsOrder> queryCommunityOrdersForCancel();

    /**
     * 查询团长下面的社区团购订单
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回团长下面的社区团购订单
     */
    PageHelper<OmsOrder> queryHeadCommunityOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria);

    /**
     * 查询社区团购还未支付的订单
     *
     * @param communityBuyId 社区团购id
     * @return 返回社区团购还没支付的订单
     */
    List<OmsOrder> queryNoPayOrdersByCommunityBuyId(long communityBuyId);

    /**
     * 查询已经支付的订单
     *
     * @param communityBuyId 社区团购id
     * @return 返回社区团购已经支付的订单
     */
    List<OmsOrder> queryPayedOrdersByCommunityBuyId(long communityBuyId);

    /**
     * 查询社区团购已参团订单（按会员分组）
     *
     * @param communityBuyCustomerId 社区团购的团长会员id
     * @param communityBuyId         社区团购的团购id
     * @return 社区团购已参团订单
     */
    List<OmsOrder> queryJoinCommunityOrders(long communityBuyCustomerId, long communityBuyId);

    /**
     * 查询社区团购已参团人数
     *
     * @param communityBuyCustomerId 社区团购的团长会员id
     * @param communityBuyId         社区团购的团购id
     * @return 社区团购已参团人数
     */
    int queryJoinCommunityOrdersCount(long communityBuyCustomerId, long communityBuyId);


    /**
     * 根据社区团购 id 查询订单 id 集(PC 端计算销量用, 因字段过多 ,  只查询必要信息)
     *
     * @param communityBuyId 社区团购 id
     * @return id 结果集
     */
    List<Long> queryOrderIdsByCommunityBuyId(Long communityBuyId);

    /**
     * 分页查询社区团购订单（后端）
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 社区团购订单
     */
    PageHelper<OmsOrder> queryCommunityOrders(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria);


    /**
     * 社区团购订单确认收货
     *
     * @param orderId    订单id
     * @param customerId 会员id
     * @return 成功返回1 失败返回0
     */
    int confirmCommunityOrderReceipt(long orderId, long customerId);

    /**
     * 根据订单id集合查询社区团购订单集合
     *
     * @param ids 订单id集合
     * @return 社区团购订单集合
     */
    List<OmsOrder> queryCommunityOrdersByIds(List<Long> ids);

    /**
     * 查询所有社区团购订单集合
     *
     * @param mobile 团长手机号
     * @return 所有社区团购订单集合
     */
    List<OmsOrder> queryAllCommunityOrders(String mobile);


    /**
     * 根据团长的会员ID 和 团购活动ID 查询该团长在该活动中所有的销售订单 字段过多 仅查询必要字段s
     *
     * @param customerId     团长的会员ID
     * @param communityBuyId 活动ID
     * @param status         订单状态
     * @return 订单集
     */
    List<OmsOrder> queryCommunityOrders(Long customerId, Long communityBuyId, String status);
}
