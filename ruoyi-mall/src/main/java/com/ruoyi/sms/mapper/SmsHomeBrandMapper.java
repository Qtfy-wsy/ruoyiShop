package com.ruoyi.sms.mapper;

import com.ruoyi.sms.domain.SmsHomeBrand;

import java.util.List;

/**
 * 首页推荐品牌Mapper接口
 *
 * @author é­éåå
 * @date 2020-08-06
 */
public interface SmsHomeBrandMapper {
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
     * 删除首页推荐品牌
     *
     * @param id 首页推荐品牌ID
     * @return 结果
     */
    public int deleteSmsHomeBrandById(Long id);

    /**
     * 批量删除首页推荐品牌
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSmsHomeBrandByIds(Long[] ids);
}
