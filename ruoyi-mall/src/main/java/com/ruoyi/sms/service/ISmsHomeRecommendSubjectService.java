package com.ruoyi.sms.service;

import com.ruoyi.sms.domain.SmsHomeRecommendSubject;

import java.util.List;

/**
 * 首页推荐专题Service接口
 *
 * @author é­éåå
 * @date 2020-08-06
 */
public interface ISmsHomeRecommendSubjectService {
    /**
     * 查询首页推荐专题
     *
     * @param id 首页推荐专题ID
     * @return 首页推荐专题
     */
    public SmsHomeRecommendSubject selectSmsHomeRecommendSubjectById(Long id);

    /**
     * 查询首页推荐专题列表
     *
     * @param smsHomeRecommendSubject 首页推荐专题
     * @return 首页推荐专题集合
     */
    public List<SmsHomeRecommendSubject> selectSmsHomeRecommendSubjectList(SmsHomeRecommendSubject smsHomeRecommendSubject);

    /**
     * 新增首页推荐专题
     *
     * @param smsHomeRecommendSubject 首页推荐专题
     * @return 结果
     */
    public int insertSmsHomeRecommendSubject(SmsHomeRecommendSubject smsHomeRecommendSubject);

    /**
     * 修改首页推荐专题
     *
     * @param smsHomeRecommendSubject 首页推荐专题
     * @return 结果
     */
    public int updateSmsHomeRecommendSubject(SmsHomeRecommendSubject smsHomeRecommendSubject);

    /**
     * 批量删除首页推荐专题
     *
     * @param ids 需要删除的首页推荐专题ID
     * @return 结果
     */
    public int deleteSmsHomeRecommendSubjectByIds(Long[] ids);

    /**
     * 删除首页推荐专题信息
     *
     * @param id 首页推荐专题ID
     * @return 结果
     */
    public int deleteSmsHomeRecommendSubjectById(Long id);


    /**
     * 修改推荐排序
     */
    int updateSort(Long id, Integer sort);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);
}
