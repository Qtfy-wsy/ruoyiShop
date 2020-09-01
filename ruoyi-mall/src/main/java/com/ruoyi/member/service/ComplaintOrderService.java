package com.ruoyi.member.service;

import com.ruoyi.member.domain.ComplaintOrder;
import com.ruoyi.util.PageHelper;


/**
 * 订单投诉服务接口
 */
public interface ComplaintOrderService {

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
     * 分页查找订单投诉
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询参数实体
     * @return 订单投诉列表
     */
    PageHelper<ComplaintOrder> queryComplaintOrder(PageHelper<ComplaintOrder> pageHelper, ComplaintOrder.QueryCriteria queryCriteria);
}
