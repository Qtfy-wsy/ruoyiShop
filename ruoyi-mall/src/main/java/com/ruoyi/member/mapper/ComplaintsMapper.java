package com.ruoyi.member.mapper;

import com.ruoyi.member.domain.Complaints;

import java.util.List;
import java.util.Map;

/**
 * Created By Caizize on 2018.3.21
 *
 * 平台投诉数据库接口
 */
public interface ComplaintsMapper {

    /**
     * 会员新增平台投诉
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
     * @param params 查询参数
     * @return 平台投诉集合
     */

    List<Complaints> queryComplaints(Map<String, Object> params);

    /**
     * 查询平台投诉数量
     *
     * @param params 查询参数
     * @return 平台投诉数量
     */

    int queryComplaintsCount(Map<String, Object> params);

    Complaints queryComplaintsById(long id);
}
