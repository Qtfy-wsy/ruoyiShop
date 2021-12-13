package com.ruoyi.setting.service;

import com.ruoyi.setting.domain.SCommunityBuySetting;

import java.util.List;

/**
 * 社区团购设置Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ISCommunityBuySettingService {
    /**
     * 查询社区团购设置
     *
     * @param id 社区团购设置ID
     * @return 社区团购设置
     */
    public SCommunityBuySetting selectSCommunityBuySettingById(Long id);

    /**
     * 查询社区团购设置列表
     *
     * @param sCommunityBuySetting 社区团购设置
     * @return 社区团购设置集合
     */
    public List<SCommunityBuySetting> selectSCommunityBuySettingList(SCommunityBuySetting sCommunityBuySetting);

    /**
     * 新增社区团购设置
     *
     * @param sCommunityBuySetting 社区团购设置
     * @return 结果
     */
    public int insertSCommunityBuySetting(SCommunityBuySetting sCommunityBuySetting);

    /**
     * 修改社区团购设置
     *
     * @param sCommunityBuySetting 社区团购设置
     * @return 结果
     */
    public int updateSCommunityBuySetting(SCommunityBuySetting sCommunityBuySetting);

    /**
     * 批量删除社区团购设置
     *
     * @param ids 需要删除的社区团购设置ID
     * @return 结果
     */
    public int deleteSCommunityBuySettingByIds(Long[] ids);

    /**
     * 删除社区团购设置信息
     *
     * @param id 社区团购设置ID
     * @return 结果
     */
    public int deleteSCommunityBuySettingById(Long id);
}
