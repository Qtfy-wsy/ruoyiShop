package com.ruoyi.member.service.impl;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.WechatUtils;
import com.ruoyi.common.utils.bean.AccessTokenResult;
import com.ruoyi.common.utils.bean.WechatSetting;
import com.ruoyi.member.domain.WeChatCustomerLink;
import com.ruoyi.member.service.WeChatCustomerLinkService;
import com.ruoyi.member.service.WechatLoginService;
import com.ruoyi.setting.bean.WechatPaySet;
import com.ruoyi.setting.service.ILsPaySettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 微信登录服务实现类
 */
@Service
public class WechatLoginServiceImpl implements WechatLoginService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(WechatLoginServiceImpl.class);

    /**
     * 注入微信用户数据库服务
     */
    @Autowired
    private WeChatCustomerLinkService weChatCustomerLinkService;

    /**
     * 注入登录设置服务
     */
    /**
     * 注入支付设置服务
     */
    @Autowired
    private ILsPaySettingService paySetService;


    @Override
    public int wechatLogin(String code, String state, Consumer<WeChatCustomerLink> consumer) {
        logger.debug("wechatLogin and code:{} \r\n state:{} \r\n ", code, state);
        //获取微信登录设置
        WechatPaySet wechatLogin = paySetService.queryPaySet().getWechatPaySet();
        WechatSetting wechatSetting = new WechatSetting();
        wechatSetting.setUrl(wechatLogin.getLoginNotice());
        wechatSetting.setAppId(wechatLogin.getAppId());
        wechatSetting.setAppSecret(wechatLogin.getAppSecret());
        //获取accesstoken
        AccessTokenResult accessTokenResult = WechatUtils.getAccessToken(code, state, wechatSetting);
        if (ObjectUtils.isEmpty(accessTokenResult) || accessTokenResult.isError()) {
            logger.error("wechatLogin error : getAccessToken fail");
            return -1;
        }
        //查找微信关联用户
        WeChatCustomerLink weChatCustomerLink = null;
        if (!StringUtils.isEmpty(accessTokenResult.getUnionid())) {
            weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByUnionId(accessTokenResult.getUnionid());
        }
        if (!Objects.isNull(weChatCustomerLink)) {
            //如果根据unionId查到了关联信息，就判断openId是否存在
            if (!weChatCustomerLink.hasOpenId()) {
                //openId不存在，更新关联信息
                weChatCustomerLink.setOpenId(accessTokenResult.getOpenid());
                weChatCustomerLinkService.updateWeChatCustomerLink(weChatCustomerLink);
            }
        } else {
            weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByOpenId(accessTokenResult.getOpenid());
        }
        //如果没有关联用户，则将获取到的openId和unionId放入session
        if (ObjectUtils.isEmpty(weChatCustomerLink)) {
            consumer.accept(new WeChatCustomerLink(accessTokenResult.getOpenid(), accessTokenResult.getUnionid()));
            logger.error("wechatLogin error: query null");
            return -2;
        }
        //如果有关联用户，则将customerId放入session,自动登录
        consumer.accept(weChatCustomerLink);
        return 1;
    }

    @Override
    public int bindAccount(String openId, String unionId, long customerId) {
        logger.debug("bindAccount and openId:{} \r\n unionId:{} \r\n customerId:{}", openId, unionId, customerId);
        WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(weChatCustomerLink) && !weChatCustomerLink.getUnionId().equals(unionId)) {
            logger.error("checkCustomerBind fail : bind already");
            throw new ServiceException("R-00004");
        }
        if (Objects.isNull(weChatCustomerLink)) {
            weChatCustomerLink = new WeChatCustomerLink();
        }
        weChatCustomerLink.setUnionId(unionId);
        weChatCustomerLink.setOpenId(openId);
        weChatCustomerLink.setCustomerId(customerId);
        //如果数据库中有记录就更新记录
        if (-1 != weChatCustomerLink.getId()) {
            logger.info("bindAccount:updateWeChatCustomerLink");
            return weChatCustomerLinkService.updateWeChatCustomerLink(weChatCustomerLink);
        }
        logger.info("bindAccount:addWeChatCustomerLink");
        //否则新增
        return weChatCustomerLinkService.addWeChatCustomerLink(weChatCustomerLink);
    }

    @Override
    public int unbindAccount(long customerId, String unionId, Consumer<String> consumer) {
        logger.debug("unbindAccount and customerId:{} \r\n unionId:{}", customerId, unionId);
        WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByCustomerId(customerId);
        if (Objects.nonNull(weChatCustomerLink)) {
            logger.error("unbindAccount error: no weChatCustomerLink");
            consumer.accept(weChatCustomerLink.getUnionId());
        }
        return weChatCustomerLinkService.unbindWeChatCustomerLink(customerId, unionId);
    }
}
