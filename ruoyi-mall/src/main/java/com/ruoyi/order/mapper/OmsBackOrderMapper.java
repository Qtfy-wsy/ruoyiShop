package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.OmsBackOrder;

import java.util.List;
import java.util.Map;

/**
 * 退单退款Mapper接口
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
public interface OmsBackOrderMapper {
    /**
     * 查询退单列表总数
     *
     * @param params 查询参数
     * @return 返回退单列表总数
     */

    int queryBackOrderCount(Map<String, Object> params);

    /**
     * 查询退单列表
     *
     * @param params 查询参数
     * @return 返回退单列表
     */

    List<OmsBackOrder> queryBackOrders(Map<String, Object> params);

    /**
     * 根据退单id查询退单信息
     *
     * @param params 查询参数
     * @return 返回退单信息
     */

    OmsBackOrder queryBackOrderById(Map<String, Object> params);

    /**
     * 同意退款
     *
     * @param params 参数
     * @return 成功返回1  失败返回0
     */

    int agreeToRefund(Map<String, Object> params);

    /**
     * 拒绝退款
     *
     * @param params 参数
     * @return 成功返回 1 失败返回0
     */

    int refuseRefund(Map<String, Object> params);

    /**
     * 同意退货
     *
     * @param params 参数
     * @return 成功返回1  失败返回0
     */

    int agreeToReturn(Map<String, Object> params);

    /**
     * 拒绝退货
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int refuseRrturn(Map<String, Object> params);

    /**
     * 同意退货确认收货
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int confirmReturn(Map<String, Object> params);

    /**
     * 拒绝收货
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int refuseToReceive(Map<String, Object> params);

    /**
     * 查询店铺退单总数
     *
     * @param params 查询参数
     * @return 返回店铺退单总数
     */

    int queryStoreBackOrdersCount(Map<String, Object> params);

    /**
     * 查询店铺退单
     *
     * @param params 查询参数
     * @return 返回店铺退单列表
     */

    List<OmsBackOrder> queryStoreBackOrders(Map<String, Object> params);

    /**
     * 新增退单信息
     *
     * @param backOrder 退单信息
     * @return 成功>0 失败=0
     */

    int addBackOrder(OmsBackOrder backOrder);

    /**
     * 根据订单id查询 退单信息
     *
     * @param params 参数
     * @return 返回退单信息
     */

    List<OmsBackOrder> queryByOrderId(Map<String, Object> params);


    /**
     * 查询退单列表总数(前端)
     *
     * @param params 查询参数
     * @return 返回退单列表总数
     */

    int queryBackOrderForSiteCount(Map<String, Object> params);

    /**
     * 查询退单列表(前端)
     *
     * @param params 查询参数
     * @return 返回退单列表
     */

    List<OmsBackOrder> queryBackForSiteOrders(Map<String, Object> params);

    /**
     * 填写物流信息
     *
     * @param params 参数
     * @return 成功>0  失败= 0
     */

    int fillTheLogistics(Map<String, Object> params);

    /**
     * 查询正在进行中的退款/退货数量
     *
     * @param customerId 会员id
     * @return 返回正在进行中的退款/退货数量
     */

    int queryInProcessBackOrder(long customerId);

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
     * 删除退单退款
     *
     * @param id 退单退款ID
     * @return 结果
     */
    public int deleteOmsBackOrderById(Long id);

    /**
     * 批量删除退单退款
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteOmsBackOrderByIds(Long[] ids);

    /**
     *  查询退单列表
     * @param omsBackOrder 退单
     * @return 退单集
     */
    List<OmsBackOrder> queryStoreBackOrdersList(OmsBackOrder omsBackOrder);
}
