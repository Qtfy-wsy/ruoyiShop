package com.ruoyi.setting.service.impl;

import com.ruoyi.setting.domain.SCommunityBuySetting;
import com.ruoyi.setting.mapper.SCommunityBuySettingMapper;
import com.ruoyi.setting.service.ISCommunityBuySettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 社区团购设置Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class SCommunityBuySettingServiceImpl implements ISCommunityBuySettingService {
    private static final Logger logger = LoggerFactory.getLogger(SCommunityBuySettingServiceImpl.class);
    @Autowired
    private SCommunityBuySettingMapper sCommunityBuySettingMapper;

    /**
     * 查询社区团购设置
     *
     * @param id 社区团购设置ID
     * @return 社区团购设置
     */
    @Override
    public SCommunityBuySetting selectSCommunityBuySettingById(Long id) {
        return sCommunityBuySettingMapper.selectSCommunityBuySettingById(id);
    }

    /**
     * 查询社区团购设置列表
     *
     * @param sCommunityBuySetting 社区团购设置
     * @return 社区团购设置
     */
    @Override
    public List<SCommunityBuySetting> selectSCommunityBuySettingList(SCommunityBuySetting sCommunityBuySetting) {
        return sCommunityBuySettingMapper.selectSCommunityBuySettingList(sCommunityBuySetting);
    }

    /**
     * 新增社区团购设置
     *
     * @param sCommunityBuySetting 社区团购设置
     * @return 结果
     */
    @Override
    public int insertSCommunityBuySetting(SCommunityBuySetting sCommunityBuySetting) {
        return sCommunityBuySettingMapper.insertSCommunityBuySetting(sCommunityBuySetting);
    }

    /**
     * 修改社区团购设置
     *
     * @param sCommunityBuySetting 社区团购设置
     * @return 结果
     */
    @Override
    public int updateSCommunityBuySetting(SCommunityBuySetting sCommunityBuySetting) {
        return sCommunityBuySettingMapper.updateSCommunityBuySetting(sCommunityBuySetting);
    }

    /**
     * 批量删除社区团购设置
     *
     * @param ids 需要删除的社区团购设置ID
     * @return 结果
     */
    @Override
    public int deleteSCommunityBuySettingByIds(Long[] ids) {
        return sCommunityBuySettingMapper.deleteSCommunityBuySettingByIds(ids);
    }

    /**
     * 删除社区团购设置信息
     *
     * @param id 社区团购设置ID
     * @return 结果
     */
    @Override
    public int deleteSCommunityBuySettingById(Long id) {
        return sCommunityBuySettingMapper.deleteSCommunityBuySettingById(id);
    }
}
