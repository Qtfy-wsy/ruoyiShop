package com.ruoyi.member.service.impl;

import com.ruoyi.member.domain.UmsMemberPanicRecord;
import com.ruoyi.member.mapper.UmsMemberPanicRecordMapper;
import com.ruoyi.member.service.IUmsMemberPanicRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户抢购记录Service业务层处理
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
@Service
public class UmsMemberPanicRecordServiceImpl implements IUmsMemberPanicRecordService {
    @Autowired
    private UmsMemberPanicRecordMapper umsMemberPanicRecordMapper;

    /**
     * 查询用户抢购记录
     *
     * @param id 用户抢购记录ID
     * @return 用户抢购记录
     */
    @Override
    public UmsMemberPanicRecord selectUmsMemberPanicRecordById(Long id) {
        return umsMemberPanicRecordMapper.selectUmsMemberPanicRecordById(id);
    }

    /**
     * 查询用户抢购记录列表
     *
     * @param umsMemberPanicRecord 用户抢购记录
     * @return 用户抢购记录
     */
    @Override
    public List<UmsMemberPanicRecord> selectUmsMemberPanicRecordList(UmsMemberPanicRecord umsMemberPanicRecord) {
        return umsMemberPanicRecordMapper.selectUmsMemberPanicRecordList(umsMemberPanicRecord);
    }

    /**
     * 新增用户抢购记录
     *
     * @param umsMemberPanicRecord 用户抢购记录
     * @return 结果
     */
    @Override
    public int insertUmsMemberPanicRecord(UmsMemberPanicRecord umsMemberPanicRecord) {
        return umsMemberPanicRecordMapper.insertUmsMemberPanicRecord(umsMemberPanicRecord);
    }

    /**
     * 修改用户抢购记录
     *
     * @param umsMemberPanicRecord 用户抢购记录
     * @return 结果
     */
    @Override
    public int updateUmsMemberPanicRecord(UmsMemberPanicRecord umsMemberPanicRecord) {
        return umsMemberPanicRecordMapper.updateUmsMemberPanicRecord(umsMemberPanicRecord);
    }

    /**
     * 批量删除用户抢购记录
     *
     * @param ids 需要删除的用户抢购记录ID
     * @return 结果
     */
    @Override
    public int deleteUmsMemberPanicRecordByIds(Long[] ids) {
        return umsMemberPanicRecordMapper.deleteUmsMemberPanicRecordByIds(ids);
    }

    /**
     * 删除用户抢购记录信息
     *
     * @param id 用户抢购记录ID
     * @return 结果
     */
    @Override
    public int deleteUmsMemberPanicRecordById(Long id) {
        return umsMemberPanicRecordMapper.deleteUmsMemberPanicRecordById(id);
    }
}
