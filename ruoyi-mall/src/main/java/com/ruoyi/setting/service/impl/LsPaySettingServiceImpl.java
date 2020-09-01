package com.ruoyi.setting.service.impl;

import com.ruoyi.setting.bean.PaySet;
import com.ruoyi.setting.bean.PaySetCommon;
import com.ruoyi.setting.domain.LsPaySetting;
import com.ruoyi.setting.mapper.LsPaySettingMapper;
import com.ruoyi.setting.service.ILsPaySettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 支付设置Service业务层处理
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@Service
public class LsPaySettingServiceImpl implements ILsPaySettingService {
    private static final Logger logger = LoggerFactory.getLogger(LsPaySettingServiceImpl.class);
    /**
     * 支付类型列表
     */
    private final static List<String> codeTypeList = Arrays.asList("1", "2", "3", "4", "5", "6");
    @Autowired
    private LsPaySettingMapper lsPaySettingMapper;

    /**
     * 查询支付设置
     *
     * @param id 支付设置ID
     * @return 支付设置
     */
    @Override
    public LsPaySetting selectLsPaySettingById(Long id) {
        return lsPaySettingMapper.selectLsPaySettingById(id);
    }

    /**
     * 查询支付接口设置
     *
     * @return 返回PaySetCommon
     */
    @Override
    public PaySetCommon queryPaySet() {
        logger.debug("queryPaySet...");
        return PaySetCommon.getPaySetCommon(new PaySetCommon(), lsPaySettingMapper.queryPaySet());
    }

    /**
     * 编辑支付接口设置
     *
     * @param paySetCommon 实体类参数
     * @param codeType     支付设置类型 1 支付宝 2 微信(扫码，公众号，H5) 3 银联 4预存款 5 微信支app付 6微信小程序支付
     * @return -1编辑出错 >=1成功
     */
    @Override
    @Transactional
    public int editPaySet(PaySetCommon paySetCommon, String codeType) {
        List<PaySet> list = new ArrayList<>();
        if (codeTypeList.indexOf(codeType) < 0) {
            logger.error("editPaySet error codeType is illegal");
            return -1;
        }
        //先删后增
        lsPaySettingMapper.deletePaySet(codeType);
        if ("1".equals(codeType)) {
            logger.debug("editPaySet aliPay...");
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getAliPaySet().getAppId(), "appId"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getAliPaySet().getAlipayPublicKey(), "alipayPublicKey"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getAliPaySet().getAppPrivateKey(), "appPrivateKey"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getAliPaySet().getIsUse(), "isUse"));
        }
        if ("2".equals(codeType)) {
            logger.debug("editPaySet wechatPay...");
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatPaySet().getAppId(), "appId"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatPaySet().getAppSecret(), "appSecret"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatPaySet().getMerchantNum(), "merchantNum"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatPaySet().getApiKey(), "apiKey"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatPaySet().getLoginNotice(), "loginNotice"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatPaySet().getIsUse(), "isUse"));
        }
        if ("3".equals(codeType)) {
            logger.debug("editPaySet unionPay...");
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getUnionPaySet().getMerchantNum(), "merchantNum"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getUnionPaySet().getApiKey(), "apiKey"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getUnionPaySet().getIsUse(), "isUse"));
        }
        if ("4".equals(codeType)) {
            logger.debug("editPaySet prePay...");
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getPrePaySet().getIsUse(), "isUse"));
        }
        if ("5".equals(codeType)) {
            logger.debug("editPaySet wechatAppPay...");
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppPaySet().getAppId(), "appId"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppPaySet().getAppSecret(), "appSecret"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppPaySet().getMerchantNum(), "merchantNum"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppPaySet().getApiKey(), "apiKey"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppPaySet().getIsUse(), "isUse"));
        }
        if ("6".equals(codeType)) {
            logger.debug("editPaySet wechatAppletPay...");
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppletPaySet().getAppId(), "appId"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppletPaySet().getAppSecret(), "appSecret"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppletPaySet().getMerchantNum(), "merchantNum"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppletPaySet().getApiKey(), "apiKey"));
            list.add(PaySet.getPaySet(new PaySet(), codeType, paySetCommon.getWechatAppletPaySet().getIsUse(), "isUse"));
        }
        return lsPaySettingMapper.addPaySet(list);
    }

    /**
     * 查询支付设置列表
     *
     * @param lsPaySetting 支付设置
     * @return 支付设置
     */
    @Override
    public List<LsPaySetting> selectLsPaySettingList(LsPaySetting lsPaySetting) {
        return lsPaySettingMapper.selectLsPaySettingList(lsPaySetting);
    }

    /**
     * 新增支付设置
     *
     * @param lsPaySetting 支付设置
     * @return 结果
     */
    @Override
    public int insertLsPaySetting(LsPaySetting lsPaySetting) {
        return lsPaySettingMapper.insertLsPaySetting(lsPaySetting);
    }

    /**
     * 修改支付设置
     *
     * @param lsPaySetting 支付设置
     * @return 结果
     */
    @Override
    public int updateLsPaySetting(LsPaySetting lsPaySetting) {
        return lsPaySettingMapper.updateLsPaySetting(lsPaySetting);
    }

    /**
     * 批量删除支付设置
     *
     * @param ids 需要删除的支付设置ID
     * @return 结果
     */
    @Override
    public int deleteLsPaySettingByIds(Long[] ids) {
        return lsPaySettingMapper.deleteLsPaySettingByIds(ids);
    }

    /**
     * 删除支付设置信息
     *
     * @param id 支付设置ID
     * @return 结果
     */
    @Override
    public int deleteLsPaySettingById(Long id) {
        return lsPaySettingMapper.deleteLsPaySettingById(id);
    }
}
