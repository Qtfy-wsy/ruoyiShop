package com.ruoyi.member.service;

import com.ruoyi.member.domain.Complaints;
import com.ruoyi.util.PageHelper;


/**
 * Created By Caizize on 2018.3.21
 *
 * 平台投诉服务接口
 */
public interface ComplaintsService {

    /**
     * 新增平台投诉
     *
     * @param complaints 平台投诉实体
     * @return 成功1 ，失败0
     */
    int addComplaints(Complaints complaints);

    /**
     * 平台回复投诉
     *
     * @param complaints 平台投诉实体
     * @return 成功1，失败0
     */
    int updateComplaintsReplay(Complaints complaints);

    /**
     * 分页查询平台投诉
     *
     * @param pageHelper 分页帮助类
     * @param customerId 会员id
     * @param status     状态
     * @return 平台投诉集合
     */
    PageHelper<Complaints> queryComplaints(PageHelper<Complaints> pageHelper, long customerId, String status);

    Complaints queryComplaintsById(long id);

}
