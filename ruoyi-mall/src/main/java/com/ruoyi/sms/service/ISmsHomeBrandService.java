package com.ruoyi.sms.service;

import com.ruoyi.sms.domain.SmsHomeBrand;

import java.util.List;

/**
 * 首页推荐品牌Service接口
 *
 * @author é­éåå
 * @date 2020-08-06
 */
public interface ISmsHomeBrandService {
    /**
     * 查询首页推荐品牌
     *
     * @param id 首页推荐品牌ID
     * @return 首页推荐品牌
     */
    public SmsHomeBrand selectSmsHomeBrandById(Long id);

    /**
     * 查询首页推荐品牌列表
     *
     * @param smsHomeBrand 首页推荐品牌
     * @return 首页推荐品牌集合
     */
    public List<SmsHomeBrand> selectSmsHomeBrandList(SmsHomeBrand smsHomeBrand);

    /**
     * 新增首页推荐品牌
     *
     * @param smsHomeBrand 首页推荐品牌
     * @return 结果
     */
    public int insertSmsHomeBrand(SmsHomeBrand smsHomeBrand);

    /**
     * 修改首页推荐品牌
     *
     * @param smsHomeBrand 首页推荐品牌
     * @return 结果
     */
    public int updateSmsHomeBrand(SmsHomeBrand smsHomeBrand);

    /**
     * 批量删除首页推荐品牌
     *
     * @param ids 需要删除的首页推荐品牌ID
     * @return 结果
     */
    public int deleteSmsHomeBrandByIds(Long[] ids);

    /**
     * 删除首页推荐品牌信息
     *
     * @param id 首页推荐品牌ID
     * @return 结果
     */
    public int deleteSmsHomeBrandById(Long id);

    int create(List<SmsHomeBrand> homeBrandList);

    /**
     * 更新推荐状态
     */
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    /**
     * 修改品牌推荐排序
     */
    int updateSort(Long id, Integer sort);
}
