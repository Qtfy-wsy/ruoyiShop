package com.ruoyi.setting.service;

import com.ruoyi.setting.domain.LsEmailSetting;

import java.util.List;

/**
 * 邮箱设置Service接口
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
public interface ILsEmailSettingService {
    /**
     * 查询邮箱设置
     *
     * @param id 邮箱设置ID
     * @return 邮箱设置
     */
    public LsEmailSetting selectLsEmailSettingById(Long id);

    /**
     * 查询邮箱设置列表
     *
     * @param lsEmailSetting 邮箱设置
     * @return 邮箱设置集合
     */
    public List<LsEmailSetting> selectLsEmailSettingList(LsEmailSetting lsEmailSetting);

    /**
     * 新增邮箱设置
     *
     * @param lsEmailSetting 邮箱设置
     * @return 结果
     */
    public int insertLsEmailSetting(LsEmailSetting lsEmailSetting);

    /**
     * 修改邮箱设置
     *
     * @param lsEmailSetting 邮箱设置
     * @return 结果
     */
    public int updateLsEmailSetting(LsEmailSetting lsEmailSetting);

    /**
     * 批量删除邮箱设置
     *
     * @param ids 需要删除的邮箱设置ID
     * @return 结果
     */
    public int deleteLsEmailSettingByIds(Long[] ids);

    /**
     * 删除邮箱设置信息
     *
     * @param id 邮箱设置ID
     * @return 结果
     */
    public int deleteLsEmailSettingById(Long id);
}
