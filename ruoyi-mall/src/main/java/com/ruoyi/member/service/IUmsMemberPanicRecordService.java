package com.ruoyi.member.service;

import com.ruoyi.member.domain.UmsMemberPanicRecord;

import java.util.List;

/**
 * 用户抢购记录Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
public interface IUmsMemberPanicRecordService {
    /**
     * 查询用户抢购记录
     *
     * @param id 用户抢购记录ID
     * @return 用户抢购记录
     */
    public UmsMemberPanicRecord selectUmsMemberPanicRecordById(Long id);

    /**
     * 查询用户抢购记录列表
     *
     * @param umsMemberPanicRecord 用户抢购记录
     * @return 用户抢购记录集合
     */
    public List<UmsMemberPanicRecord> selectUmsMemberPanicRecordList(UmsMemberPanicRecord umsMemberPanicRecord);

    /**
     * 新增用户抢购记录
     *
     * @param umsMemberPanicRecord 用户抢购记录
     * @return 结果
     */
    public int insertUmsMemberPanicRecord(UmsMemberPanicRecord umsMemberPanicRecord);

    /**
     * 修改用户抢购记录
     *
     * @param umsMemberPanicRecord 用户抢购记录
     * @return 结果
     */
    public int updateUmsMemberPanicRecord(UmsMemberPanicRecord umsMemberPanicRecord);

    /**
     * 批量删除用户抢购记录
     *
     * @param ids 需要删除的用户抢购记录ID
     * @return 结果
     */
    public int deleteUmsMemberPanicRecordByIds(Long[] ids);

    /**
     * 删除用户抢购记录信息
     *
     * @param id 用户抢购记录ID
     * @return 结果
     */
    public int deleteUmsMemberPanicRecordById(Long id);
}
