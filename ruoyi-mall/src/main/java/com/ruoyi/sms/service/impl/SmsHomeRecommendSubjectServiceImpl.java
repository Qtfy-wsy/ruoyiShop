package com.ruoyi.sms.service.impl;

import com.ruoyi.sms.domain.SmsHomeRecommendSubject;
import com.ruoyi.sms.mapper.SmsHomeRecommendSubjectMapper;
import com.ruoyi.sms.service.ISmsHomeRecommendSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页推荐专题Service业务层处理
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@Slf4j
@Service
public class SmsHomeRecommendSubjectServiceImpl implements ISmsHomeRecommendSubjectService {

    @Autowired
    private SmsHomeRecommendSubjectMapper smsHomeRecommendSubjectMapper;

    /**
     * 查询首页推荐专题
     *
     * @param id 首页推荐专题ID
     * @return 首页推荐专题
     */
    @Override
    public SmsHomeRecommendSubject selectSmsHomeRecommendSubjectById(Long id) {
        return smsHomeRecommendSubjectMapper.selectSmsHomeRecommendSubjectById(id);
    }
    @Override
    public int updateSort(Long id, Integer sort) {
        SmsHomeRecommendSubject recommendProduct = new SmsHomeRecommendSubject();
        recommendProduct.setId(id);
        recommendProduct.setSort(sort);
        return smsHomeRecommendSubjectMapper.updateSmsHomeRecommendSubject(recommendProduct);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {

        SmsHomeRecommendSubject record = new SmsHomeRecommendSubject();
        record.setRecommendStatus(recommendStatus);
        record.setId(ids.get(0));
        return smsHomeRecommendSubjectMapper.updateSmsHomeRecommendSubject(record);
    }

    /**
     * 查询首页推荐专题列表
     *
     * @param smsHomeRecommendSubject 首页推荐专题
     * @return 首页推荐专题
     */
    @Override
    public List<SmsHomeRecommendSubject> selectSmsHomeRecommendSubjectList(SmsHomeRecommendSubject smsHomeRecommendSubject) {
        return smsHomeRecommendSubjectMapper.selectSmsHomeRecommendSubjectList(smsHomeRecommendSubject);
    }

    /**
     * 新增首页推荐专题
     *
     * @param smsHomeRecommendSubject 首页推荐专题
     * @return 结果
     */
    @Override
    public int insertSmsHomeRecommendSubject(SmsHomeRecommendSubject smsHomeRecommendSubject) {
        return smsHomeRecommendSubjectMapper.insertSmsHomeRecommendSubject(smsHomeRecommendSubject);
    }

    /**
     * 修改首页推荐专题
     *
     * @param smsHomeRecommendSubject 首页推荐专题
     * @return 结果
     */
    @Override
    public int updateSmsHomeRecommendSubject(SmsHomeRecommendSubject smsHomeRecommendSubject) {
        return smsHomeRecommendSubjectMapper.updateSmsHomeRecommendSubject(smsHomeRecommendSubject);
    }

    /**
     * 批量删除首页推荐专题
     *
     * @param ids 需要删除的首页推荐专题ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeRecommendSubjectByIds(Long[] ids) {
        return smsHomeRecommendSubjectMapper.deleteSmsHomeRecommendSubjectByIds(ids);
    }

    /**
     * 删除首页推荐专题信息
     *
     * @param id 首页推荐专题ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeRecommendSubjectById(Long id) {
        return smsHomeRecommendSubjectMapper.deleteSmsHomeRecommendSubjectById(id);
    }
}
