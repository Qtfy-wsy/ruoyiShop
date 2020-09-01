package com.ruoyi.setting.service.impl;

import com.ruoyi.setting.domain.LsSmsSetting;
import com.ruoyi.setting.mapper.LsSmsSettingMapper;
import com.ruoyi.setting.service.ILsSmsSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短信接口设置Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class LsSmsSettingServiceImpl implements ILsSmsSettingService {
    private static final Logger logger = LoggerFactory.getLogger(LsSmsSettingServiceImpl.class);
    @Autowired
    private LsSmsSettingMapper lsSmsSettingMapper;

    /**
     * 查询短信接口设置
     *
     * @param id 短信接口设置ID
     * @return 短信接口设置
     */
    @Override
    public LsSmsSetting selectLsSmsSettingById(Long id) {
        return lsSmsSettingMapper.selectLsSmsSettingById(id);
    }

    /**
     * 查询短信接口设置列表
     *
     * @param lsSmsSetting 短信接口设置
     * @return 短信接口设置
     */
    @Override
    public List<LsSmsSetting> selectLsSmsSettingList(LsSmsSetting lsSmsSetting) {
        return lsSmsSettingMapper.selectLsSmsSettingList(lsSmsSetting);
    }

    /**
     * 新增短信接口设置
     *
     * @param lsSmsSetting 短信接口设置
     * @return 结果
     */
    @Override
    public int insertLsSmsSetting(LsSmsSetting lsSmsSetting) {
        return lsSmsSettingMapper.insertLsSmsSetting(lsSmsSetting);
    }

    /**
     * 修改短信接口设置
     *
     * @param lsSmsSetting 短信接口设置
     * @return 结果
     */
    @Override
    public int updateLsSmsSetting(LsSmsSetting lsSmsSetting) {
        return lsSmsSettingMapper.updateLsSmsSetting(lsSmsSetting);
    }

    /**
     * 批量删除短信接口设置
     *
     * @param ids 需要删除的短信接口设置ID
     * @return 结果
     */
    @Override
    public int deleteLsSmsSettingByIds(Long[] ids) {
        return lsSmsSettingMapper.deleteLsSmsSettingByIds(ids);
    }

    /**
     * 删除短信接口设置信息
     *
     * @param id 短信接口设置ID
     * @return 结果
     */
    @Override
    public int deleteLsSmsSettingById(Long id) {
        return lsSmsSettingMapper.deleteLsSmsSettingById(id);
    }
}
