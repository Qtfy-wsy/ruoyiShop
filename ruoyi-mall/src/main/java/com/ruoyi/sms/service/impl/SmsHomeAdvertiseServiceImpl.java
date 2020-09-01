package com.ruoyi.sms.service.impl;

import com.ruoyi.sms.domain.SmsHomeAdvertise;
import com.ruoyi.sms.mapper.SmsHomeAdvertiseMapper;
import com.ruoyi.sms.service.ISmsHomeAdvertiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页轮播广告Service业务层处理
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@Slf4j
@Service
public class SmsHomeAdvertiseServiceImpl implements ISmsHomeAdvertiseService {

    @Autowired
    private SmsHomeAdvertiseMapper smsHomeAdvertiseMapper;

    /**
     * 查询首页轮播广告
     *
     * @param id 首页轮播广告ID
     * @return 首页轮播广告
     */
    @Override
    public SmsHomeAdvertise selectSmsHomeAdvertiseById(Long id) {
        return smsHomeAdvertiseMapper.selectSmsHomeAdvertiseById(id);
    }

    /**
     * 查询首页轮播广告列表
     *
     * @param smsHomeAdvertise 首页轮播广告
     * @return 首页轮播广告
     */
    @Override
    public List<SmsHomeAdvertise> selectSmsHomeAdvertiseList(SmsHomeAdvertise smsHomeAdvertise) {
        return smsHomeAdvertiseMapper.selectSmsHomeAdvertiseList(smsHomeAdvertise);
    }

    /**
     * 新增首页轮播广告
     *
     * @param smsHomeAdvertise 首页轮播广告
     * @return 结果
     */
    @Override
    public int insertSmsHomeAdvertise(SmsHomeAdvertise smsHomeAdvertise) {
        return smsHomeAdvertiseMapper.insertSmsHomeAdvertise(smsHomeAdvertise);
    }

    /**
     * 修改首页轮播广告
     *
     * @param smsHomeAdvertise 首页轮播广告
     * @return 结果
     */
    @Override
    public int updateSmsHomeAdvertise(SmsHomeAdvertise smsHomeAdvertise) {
        return smsHomeAdvertiseMapper.updateSmsHomeAdvertise(smsHomeAdvertise);
    }

    /**
     * 批量删除首页轮播广告
     *
     * @param ids 需要删除的首页轮播广告ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeAdvertiseByIds(Long[] ids) {
        return smsHomeAdvertiseMapper.deleteSmsHomeAdvertiseByIds(ids);
    }

    /**
     * 删除首页轮播广告信息
     *
     * @param id 首页轮播广告ID
     * @return 结果
     */
    @Override
    public int deleteSmsHomeAdvertiseById(Long id) {
        return smsHomeAdvertiseMapper.deleteSmsHomeAdvertiseById(id);
    }
}
