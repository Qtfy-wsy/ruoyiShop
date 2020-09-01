package com.ruoyi.setting.service.impl;

import com.ruoyi.setting.domain.LsEmailSetting;
import com.ruoyi.setting.mapper.LsEmailSettingMapper;
import com.ruoyi.setting.service.ILsEmailSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 邮箱设置Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class LsEmailSettingServiceImpl implements ILsEmailSettingService {
    private static final Logger logger = LoggerFactory.getLogger(LsEmailSettingServiceImpl.class);
    @Autowired
    private LsEmailSettingMapper lsEmailSettingMapper;

    /**
     * 查询邮箱设置
     *
     * @param id 邮箱设置ID
     * @return 邮箱设置
     */
    @Override
    public LsEmailSetting selectLsEmailSettingById(Long id) {
        return lsEmailSettingMapper.selectLsEmailSettingById(id);
    }

    /**
     * 查询邮箱设置列表
     *
     * @param lsEmailSetting 邮箱设置
     * @return 邮箱设置
     */
    @Override
    public List<LsEmailSetting> selectLsEmailSettingList(LsEmailSetting lsEmailSetting) {
        return lsEmailSettingMapper.selectLsEmailSettingList(lsEmailSetting);
    }

    /**
     * 新增邮箱设置
     *
     * @param lsEmailSetting 邮箱设置
     * @return 结果
     */
    @Override
    public int insertLsEmailSetting(LsEmailSetting lsEmailSetting) {
        return lsEmailSettingMapper.insertLsEmailSetting(lsEmailSetting);
    }

    /**
     * 修改邮箱设置
     *
     * @param lsEmailSetting 邮箱设置
     * @return 结果
     */
    @Override
    public int updateLsEmailSetting(LsEmailSetting lsEmailSetting) {
        return lsEmailSettingMapper.updateLsEmailSetting(lsEmailSetting);
    }

    /**
     * 批量删除邮箱设置
     *
     * @param ids 需要删除的邮箱设置ID
     * @return 结果
     */
    @Override
    public int deleteLsEmailSettingByIds(Long[] ids) {
        return lsEmailSettingMapper.deleteLsEmailSettingByIds(ids);
    }

    /**
     * 删除邮箱设置信息
     *
     * @param id 邮箱设置ID
     * @return 结果
     */
    @Override
    public int deleteLsEmailSettingById(Long id) {
        return lsEmailSettingMapper.deleteLsEmailSettingById(id);
    }
}
