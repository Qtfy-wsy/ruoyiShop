package com.ruoyi.member.mapper;


import com.ruoyi.member.domain.ComplaintOrder;

import java.util.List;
import java.util.Map;

/**
 * 订单投诉数据库接口
 */
public interface ComplaintOrderMapper {

    /**
     * 新增订单投诉
     *
     * @param complaintOrder 订单投诉实体
     * @return 1成功 否则失败
     */

    int addComplaintOrder(ComplaintOrder complaintOrder);

    /**
     * 回复订单投诉
     *
     * @param complaintOrder 订单投诉实体
     * @return 1成功 否则失败
     */

    int replyComplaintOrder(ComplaintOrder complaintOrder);

    /**
     * 查找订单投诉
     *
     * @param params 查询参数
     * @return 订单投诉列表
     */

    List<ComplaintOrder> queryComplaintOrder(Map<String, Object> params);

    /**
     * 查找订单投诉数量
     *
     * @param params 查询参数
     * @return 订单投诉数量
     */

    int queryComplaintOrderCount(Map<String, Object> params);
}
