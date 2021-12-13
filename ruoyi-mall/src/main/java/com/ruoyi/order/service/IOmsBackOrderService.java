package com.ruoyi.order.service;

import com.ruoyi.order.domain.OmsBackOrder;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.vo.BackOrderItem;
import com.ruoyi.order.vo.QueryCriteria;
import com.ruoyi.order.vo.QueryOrderCriteria;
import com.ruoyi.util.PageHelper;

import java.math.BigDecimal;
import java.util.List;

/**
 * 退单退款Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface IOmsBackOrderService {
    /**
     * 分页查询退单列表 (后端使用)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回退单列表
     */
    PageHelper<OmsBackOrder> queryBackOrders(PageHelper<OmsBackOrder> pageHelper, QueryCriteria queryCriteria);

    /**
     * 查询店铺退单列表 (后端使用，平台查询店铺退单)
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回店铺退单列表
     */
    PageHelper<OmsBackOrder> queryStoreBackOrders(PageHelper<OmsBackOrder> pageHelper, QueryCriteria queryCriteria);

    /**
     * 根据退单id查询退单信息
     *
     * @param backOrderId    退单id
     * @param storeId        店铺id
     * @param customerId     会员id
     * @param backOrderItems 查询的枚举
     * @return 返回退单信息
     */
    OmsBackOrder queryBackOrderById(long backOrderId, long storeId, long customerId, BackOrderItem... backOrderItems);

    /**
     * 同意退款
     *
     * @param backOrderId 退单id
     * @param storeId     店铺id
     * @param message     留言
     * @return 成功返回1  失败返回0  当前退单状态不对 返回-1
     */
    int agreeToRefund(long backOrderId, long storeId, String message);

    /**
     * 拒绝退款
     *
     * @param backOrderId 退单id
     * @param storeId     店铺id
     * @param message     留言
     * @return 成功返回1 失败返回0 当前退单状态不对 返回-1
     */
    int refuseRefund(long backOrderId, long storeId, String message);

    /**
     * 同意退货
     *
     * @param backOrderId 退单id
     * @param storeId     店铺id
     * @param message     留言
     * @return 成功返回1 失败返回0 当前退单状态不对 返回-1
     */
    int agreeToReturn(long backOrderId, long storeId, String message);

    /**
     * 拒绝退货
     *
     * @param backOrderId 退单id
     * @param storeId     店铺id
     * @param message     留言
     * @return 成功返回1 失败返回0 当前退单状态不对 返回-1
     */
    int refuseReturn(long backOrderId, long storeId, String message);

    /**
     * 同意确认退货
     *
     * @param backOrderId 退单id
     * @param storeId     店铺id
     * @param message     留言
     * @param money       实退金额
     * @return 成功返回1 失败返回0 当前退单状态不对 返回-1
     */
    int confirmReturn(long backOrderId, long storeId, String message, BigDecimal money);

    /**
     * 拒绝收货
     *
     * @param backOrderId 退单id
     * @param storeId     店铺id
     * @param message     留言
     * @return 成功返回1 失败返回0 当前退单状态不对 返回-1
     */
    int refuseToReceive(long backOrderId, long storeId, String message);

    /**
     * 新增退款/退货纪录
     *
     * @param backOrder 退款/退货实体
     * @return 成功返回>0  失败返回0
     */
    int addBackOrder(OmsBackOrder backOrder);

    /**
     * 判断是否可以申请退款
     *
     * @param orderId    订单id
     * @param customerId 用户id
     * @param orderType  订单类型
     * @return 可以返回true  不可以返回false
     */
    boolean isCanApplyRefund(long orderId, long customerId, String orderType);

    /**
     * 根据订单id查询退单信息
     *
     * @param orderId    订单id
     * @param customerId 会员id
     * @return 返回退单信息
     */
    List<OmsBackOrder> queryByOrderId(long orderId, long customerId);

    /**
     * 获得当前用户订单下可以退货的单品信息
     *
     * @param order 订单信息
     * @return 返回可以退货的单品信息
     */
    List<OmsOrderSku> getCanRetrunOrderSkus(OmsOrder order);


    /**
     * 分页查询退单信息
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回退单信息
     */
    PageHelper<OmsBackOrder> queryBackOrderForSite(PageHelper<OmsBackOrder> pageHelper, QueryOrderCriteria queryCriteria);

    /**
     * 填写物流信息
     *
     * @param customerId       用户id
     * @param backOrderId      退单id
     * @param logisCompanyName 公司名称
     * @param waybillCode      物流单号
     * @return 成功>0 其他失败
     */
    int fillTheLogistics(long customerId, long backOrderId, String logisCompanyName, String waybillCode);

    /**
     * 设置退单下的单品信息
     *
     * @param backOrder 退单信息
     */
    void setBackOrderSkus(OmsBackOrder backOrder);

    /**
     * 查询正在进行中的退款/退货数量(c端用户查询)
     *
     * @param customerId 会员id
     * @return 返回正在进行中的退款/退货数量
     */
    int queryInProcessBackOrder(long customerId);

    /**
     * 查询正在进行中的退款/退货数量（商家app端使用）
     *
     * @return 返回正在进行中的退款/退货数量
     */
    int queryInProcessBackOrderForStoreApp(long storeId);

    /**
     * 查询退单退款
     *
     * @param id 退单退款ID
     * @return 退单退款
     */
    public OmsBackOrder selectOmsBackOrderById(Long id);

    /**
     * 查询退单退款列表
     *
     * @param omsBackOrder 退单退款
     * @return 退单退款集合
     */
    public List<OmsBackOrder> selectOmsBackOrderList(OmsBackOrder omsBackOrder);

    /**
     * 新增退单退款
     *
     * @param omsBackOrder 退单退款
     * @return 结果
     */
    public int insertOmsBackOrder(OmsBackOrder omsBackOrder);

    /**
     * 修改退单退款
     *
     * @param omsBackOrder 退单退款
     * @return 结果
     */
    public int updateOmsBackOrder(OmsBackOrder omsBackOrder);

    /**
     * 批量删除退单退款
     *
     * @param ids 需要删除的退单退款ID
     * @return 结果
     */
    public int deleteOmsBackOrderByIds(Long[] ids);

    /**
     * 删除退单退款信息
     *
     * @param id 退单退款ID
     * @return 结果
     */
    public int deleteOmsBackOrderById(Long id);

    /**
     * 查询退单列表
     * @param omsBackOrder 退单
     * @return 退单集
     */
    List<OmsBackOrder> queryStoreBackOrdersList(OmsBackOrder omsBackOrder);
}
