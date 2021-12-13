package com.ruoyi.member.service;

import com.ruoyi.member.domain.UmsMemberPoint;

import java.util.List;

/**
 * 会员积分详情Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
public interface IUmsMemberPointService {
    /**
     * 查询会员积分详情
     *
     * @param id 会员积分详情ID
     * @return 会员积分详情
     */
    public UmsMemberPoint selectUmsMemberPointById(Long id);

    /**
     * 查询会员积分详情列表
     *
     * @param umsMemberPoint 会员积分详情
     * @return 会员积分详情集合
     */
    public List<UmsMemberPoint> selectUmsMemberPointList(UmsMemberPoint umsMemberPoint);

    /**
     * 新增会员积分详情
     *
     * @param umsMemberPoint 会员积分详情
     * @return 结果
     */
    public int insertUmsMemberPoint(UmsMemberPoint umsMemberPoint);

    /**
     * 修改会员积分详情
     *
     * @param umsMemberPoint 会员积分详情
     * @return 结果
     */
    public int updateUmsMemberPoint(UmsMemberPoint umsMemberPoint);

    /**
     * 批量删除会员积分详情
     *
     * @param ids 需要删除的会员积分详情ID
     * @return 结果
     */
    public int deleteUmsMemberPointByIds(Long[] ids);

    /**
     * 删除会员积分详情信息
     *
     * @param id 会员积分详情ID
     * @return 结果
     */
    public int deleteUmsMemberPointById(Long id);
}
