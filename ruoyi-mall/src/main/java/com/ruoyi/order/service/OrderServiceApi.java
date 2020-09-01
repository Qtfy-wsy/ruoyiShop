package com.ruoyi.order.service;


import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.vo.*;
import com.ruoyi.util.PageHelper;

import java.io.OutputStream;
import java.util.List;

/**
 * Created by 魔金商城 on 17/11/7.
 * 订单服务接口
 */
public interface OrderServiceApi {

    /**
     * 提交订单
     *
     * @param submitOrderParams 用户提交的参数
     * @return 返回订单响应实体
     */
    SubmitOrderResponse submitOrder(SubmitOrderParams submitOrderParams);

    /**
     * 取消订单
     *
     * @param cancelOrderParams 取消订单参数
     * @return 1 成功 -1 订单不存在 -2 用户不匹配 0 失败
     */
    int cancleOrder(CancelOrderParams cancelOrderParams);

    /**
     * 取消定金预售订单
     *
     * @param cancelOrderParams 取消订单参数
     * @return 1 成功 -1 订单不存在 -2 用户不匹配 0 失败
     */
    int cancelDepositPreSaleOrder(CancelOrderParams cancelOrderParams);

    /**
     * 确认付款
     *
     * @param confirmOrderParams 确认订单支付请求
     * @return 0 返回失败 1 成功
     */
    int confirmOrderPayed(ConfirmOrderParams confirmOrderParams);

    /**
     * 新增订单评论
     *
     * @param evaluationParams 评论参数
     * @return 成功返回1 失败返回0 -1 订单不存在  -2 订单状态错误
     */
    int addOrderEvaluation(Evaluation evaluationParams);

    /**
     * 查询订单的评价
     *
     * @param orderId    订单id
     * @param customerId 用户id
     * @return 返回订单评价
     */
    Evaluation queryOrderEvaluation(long orderId, long customerId);


    /**
     * 再次购买
     *
     * @param orderId    订单id
     * @param customerId 会员id
     * @return -2 订单不存在 1 成功
     */
    int buyAgain(long orderId, long customerId);

    /**
     * 确认收货
     *
     * @param orderId    订单id
     * @param customerId 会员id
     * @return 成功返回1 失败返回0
     */
    int confirmReceipt(long orderId, long customerId);

    /**
     * 查询订单详情
     *
     * @param id         订单id
     * @param customerId 会员id
     * @param storeId    店铺id
     * @param orderItems 查询条件
     * @return 返回订单详情
     */
    OmsOrder queryOrderDetailById(long id, long customerId, long storeId, OrderItem... orderItems);


    /**
     * 导出选中的订单信息
     *
     * @param outputStream 输出流
     * @param status       订单状态
     * @param ids          订单ID数组
     * @param storeId      店铺id
     */
    Void exportCheckedOrder(OutputStream outputStream, String status, Long[] ids, long storeId);


    /**
     * 导出全部订单信息
     *
     * @param outputStream 输出流
     * @param status       订单状态
     * @param storeId      店铺id
     * @param marketingId  促销id
     */
    Void exportAllOrder(OutputStream outputStream, String status, long storeId, Long marketingId);

    /**
     * 导出选中的拼团订单信息
     *
     * @param outputStream 输出流
     * @param status       订单状态
     * @param ids          订单ID数组
     * @param storeId      店铺id
     */
    Void exportCheckedGroupOrder(OutputStream outputStream, String status, Long[] ids, long storeId);


    /**
     * 导出全部拼团订单信息
     *
     * @param outputStream 输出流
     * @param status       订单状态
     * @param storeId      店铺id
     */
    Void exportAllGroupOrder(OutputStream outputStream, String status, long storeId);

    /**
     * 分页查询分销订单(管理端用)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询参数
     * @return 返回分销订单数据
     */
    PageHelper<OmsOrder> querySpreadOrdersForBack(PageHelper<OmsOrder> pageHelper, QueryOrderCriteria queryCriteria);

    /**
     * 订单发货
     *
     * @param id            订单id
     * @param storeId       店铺id
     * @param waybillCode   运单号
     * @param operationName 操作人
     * @param companyId     物流公司Id
     * @return 成功返回1 失败返回0 -2 订单正在申请退款 -1 订单号含有中文 -3 拼团未成功  -4 众筹还未成功 -5 订单不存在 -6 物流公司不存在
     */
    int deliverOrder(long id, long storeId, String waybillCode, String operationName, long companyId);

    /**
     * 核销虚拟订单
     *
     * @param id            订单id
     * @param storeId       店铺id
     * @param writeOffCode  核销码
     * @param operationName 操作人
     * @return 0 失败   1 成功 -1 核销吗错误
     */
    int writeOffVirtualOrder(long id, long storeId, String writeOffCode, String operationName);

    /**
     * 查询打印订单的详情
     *
     * @param ids 订单id
     * @return 返回打印订单的详情
     */
    List<OmsOrder> queryPrintOrderDetails(List<Long> ids);

    /**
     * 查询打印订单的详情
     *
     * @param ids     订单id
     * @param storeId 店铺id
     * @return 返回打印订单的详情
     */
    List<OmsOrder> queryPrintOrderDetailsForStore(List<Long> ids, long storeId);

    /**
     * 设置订单的推荐人信息
     *
     * @param order 订单信息
     */
    void setOrderRecommoned(OmsOrder order);

    /**
     * 保存订单信息
     *
     * @param orders                      订单信息
     * @param needCheckShoppingCartDelNum 是否需要删除购物车
     */
    void saveOrders(List<OmsOrder> orders, boolean needCheckShoppingCartDelNum);


    /**
     * 自动取消拼团订单（定时任务，勿删）
     */
    int autoCancelGroupOrder();


    /**
     * 自动取消定金预售订单（定时任务，勿删）
     */
    int autoCancelDepositPreSaleOrder();

    /**
     * 自动确认收货（定时任务，勿删）
     */
    int autoConfirmReceipt();

    /**
     * 自动取消订单（定时任务，勿删）
     */
    int autoCancelOrder();

    /**
     * 自动处理已结束的众筹项目订单（定时任务，勿删）
     */
    int autoHandleCrowdFundingOrder();
}
