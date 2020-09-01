package com.ruoyi.order.mapper;


import com.ruoyi.order.vo.GroupOrder;

import java.util.List;
import java.util.Map;

/**
 * 拼团订单数据库接口
 */
public interface GroupOrderMapper {

    /**
     * 查询团长订单
     *
     * @param params 查询参数
     * @return 团长订单集合
     */

    List<GroupOrder> queryGroupHeadOrders(Map<String, Object> params);

    /**
     * 查询团长订单数量
     *
     * @param params 查询参数
     * @return 团长订单数量
     */

    int queryGroupHeadOrdersCount(Map<String, Object> params);

    /**
     * 查询店铺团长订单（admin端使用）
     *
     * @param params 查询参数
     * @return 店铺团长订单集合
     */

    List<GroupOrder> queryStoreGroupHeadOrders(Map<String, Object> params);

    /**
     * 查询店铺团长订单数量（admin端使用）
     *
     * @param params 查询参数
     * @return 店铺团长订单数量
     */

    int queryStoreGroupHeadOrdersCount(Map<String, Object> params);

    /**
     * 查询团员订单
     *
     * @param params 查询参数
     * @return 团员订单集合
     */

    List<GroupOrder> queryGroupMemberOrders(Map<String, Object> params);


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
     * @param params 参数
     * @return 返回团购信息
     */

    GroupOrder queryByGroupIdAndCustomerId(Map<String, Object> params);

    /**
     * 修改拼团订单状态
     *
     * @param params 参数
     * @return 成功返回1 失败返回0
     */

    int updateGroupStatus(Map<String, Object> params);

    /**
     * 修改拼团订单定时任务处理状态
     *
     * @param orderId 订单id
     * @return 成功返回1 失败返回0
     */

    int updateAutoHandleStatus(long orderId);

    /**
     * 根据团id查询拼团人数
     *
     * @param groupId 团id
     * @return 返回拼团人数
     */

    int queryGroupNum(String groupId);

    /**
     * 修改拼团为成功
     *
     * @param groupId 团id
     * @return 成功返回>0 失败返回0
     */

    int updateGroupSuccess(String groupId);

    /**
     * 根据团id和会员id取消订单
     *
     * @param params 参数
     * @return 成功返回>0 失败返回0
     */

    int calcOrderByGroupIdAndCustomerId(Map<String, Object> params);

    /**
     * 查询未支付拼团订单
     *
     * @param params 查询参数
     * @return 未支付拼团订单集合
     */

    List<GroupOrder> queryNotPayGroupOrders(Map<String, Object> params);


    /**
     * 查询未支付拼团订单数量
     *
     * @param params 查询参数
     * @return 未支付拼团订单数量
     */

    int queryNotPayGroupOrdersCount(Map<String, Object> params);

    /**
     * 查询店铺未支付拼团订单 （admin端使用）
     *
     * @param params 查询参数
     * @return 店铺未支付拼团订单集合
     */

    List<GroupOrder> queryStoreNotPayGroupOrders(Map<String, Object> params);


    /**
     * 查询店铺未支付拼团订单数量 （admin端使用）
     *
     * @param params 查询参数
     * @return 店铺未支付拼团订单数量
     */

    int queryStoreNotPayGroupOrdersCount(Map<String, Object> params);

    /**
     * 查询已经开团24小时以上的（未成团或未支付）的未处理拼团订单
     */

    List<GroupOrder> queryOpenGroupOrderForCancel();


    /**
     * 查询创建时间24小时以上的未支付的未处理拼团订单
     */

    List<GroupOrder> queryNotOpenGroupOrderForCancel();

    /**
     * 查找拼团订单信息(导出用)
     *
     * @param params 查询参数
     * @return 订单信息集合
     */

    List<GroupOrder> queryGroupHeadOrdersByIds(Map<String, Object> params);

    /**
     * 查找所有拼团订单信息(导出用)
     *
     * @param params 查询参数
     * @return 订单信息
     */

    List<GroupOrder> queryAllGroupHeadOrder(Map<String, Object> params);
}
