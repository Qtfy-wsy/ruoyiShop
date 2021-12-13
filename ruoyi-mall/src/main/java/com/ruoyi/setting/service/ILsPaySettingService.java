package com.ruoyi.setting.service;

import com.ruoyi.setting.bean.PaySetCommon;
import com.ruoyi.setting.domain.LsPaySetting;

import java.util.List;

/**
 * 支付设置Service接口
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
public interface ILsPaySettingService {
    /**
     * 查询所有支付接口设置
     *
     * @return 返回PaySetCommon
     */
    PaySetCommon queryPaySet();

    /**
     * 编辑支付接口设置
     *
     * @return -1编辑出错 1成功
     */
    int editPaySet(PaySetCommon paySetCommon, String codeType);

    /**
     * 查询支付设置
     *
     * @param id 支付设置ID
     * @return 支付设置
     */
    public LsPaySetting selectLsPaySettingById(Long id);

    /**
     * 查询支付设置列表
     *
     * @param lsPaySetting 支付设置
     * @return 支付设置集合
     */
    public List<LsPaySetting> selectLsPaySettingList(LsPaySetting lsPaySetting);

    /**
     * 新增支付设置
     *
     * @param lsPaySetting 支付设置
     * @return 结果
     */
    public int insertLsPaySetting(LsPaySetting lsPaySetting);

    /**
     * 修改支付设置
     *
     * @param lsPaySetting 支付设置
     * @return 结果
     */
    public int updateLsPaySetting(LsPaySetting lsPaySetting);

    /**
     * 批量删除支付设置
     *
     * @param ids 需要删除的支付设置ID
     * @return 结果
     */
    public int deleteLsPaySettingByIds(Long[] ids);

    /**
     * 删除支付设置信息
     *
     * @param id 支付设置ID
     * @return 结果
     */
    public int deleteLsPaySettingById(Long id);
}
