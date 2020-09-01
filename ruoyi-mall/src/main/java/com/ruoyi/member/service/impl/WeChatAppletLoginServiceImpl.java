package com.ruoyi.member.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.WeChatAppletUtils;
import com.ruoyi.common.utils.bean.WeChatAppletLoginResponse;
import com.ruoyi.common.utils.bean.WeChatAppletUserInfo;
import com.ruoyi.common.utils.bean.WechatSetting;
import com.ruoyi.member.domain.WeChatCustomerLink;
import com.ruoyi.member.service.WeChatAppletLoginService;
import com.ruoyi.member.service.WeChatCustomerLinkService;
import com.ruoyi.member.vo.AppletLoginInfo;
import com.ruoyi.member.vo.AppletLoginRedisParamResponse;
import com.ruoyi.setting.bean.WechatPaySet;
import com.ruoyi.setting.service.ILsPaySettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 微信小程序登录服务实现类
 *
 * @author SK
 * @since 2018/6/13
 */
@Service
@Slf4j
public class WeChatAppletLoginServiceImpl implements WeChatAppletLoginService {

    /**
     * 注入微信用户关联服务
     */
    @Autowired
    private WeChatCustomerLinkService weChatCustomerLinkService;

    /**
     * 注入随机数生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 注入支付设置服务
     */
    @Autowired
    private ILsPaySettingService paySetService;


    @Override
    public AppletLoginInfo getLoginInfo(String code, String unionId, Consumer<AppletLoginRedisParamResponse> claimsConsumer) {
        log.debug("getLoginInfo and code:{} \r\n unionId:{}", code, unionId);
        AppletLoginRedisParamResponse appletLoginRedisParamResponse = new AppletLoginRedisParamResponse();
        String token = snowflakeIdWorker.nextId() + "";
        appletLoginRedisParamResponse.setToken(token);
        WechatPaySet wechatAppletPaySet = paySetService.queryPaySet().getWechatAppletPaySet();
        WechatSetting wechatSetting = new WechatSetting();
        wechatSetting.setAppId(wechatAppletPaySet.getAppId());
        wechatSetting.setAppSecret(wechatAppletPaySet.getAppSecret());
        if (!wechatSetting.checkAppletOAuthParams()) {
            log.error("getLoginInfo fail:checkAppletOAuthParams fail");
            return buildNotLinked(claimsConsumer, appletLoginRedisParamResponse, token, null);
        }
        WeChatAppletLoginResponse weChatAppletLoginResponse = WeChatAppletUtils.getLoginInfo(code, wechatSetting);
        if (Objects.isNull(weChatAppletLoginResponse)) {
            log.error("getLoginInfo fail: getLoginInfo fail");
            return buildNotLinked(claimsConsumer, appletLoginRedisParamResponse, token, null);
        }
        appletLoginRedisParamResponse.setSessionKey(weChatAppletLoginResponse.getSession_key());
        appletLoginRedisParamResponse.setOpenId(weChatAppletLoginResponse.getOpenid());
        if (!weChatAppletLoginResponse.hasUnionId()) {
            weChatAppletLoginResponse.setUnionid(unionId);
        }
        if (!weChatAppletLoginResponse.hasUnionId()) {
            log.info("getLoginInfo: no unionId");
            return buildNotLinked(claimsConsumer, appletLoginRedisParamResponse, token, null);
        }
        appletLoginRedisParamResponse.setUnionId(weChatAppletLoginResponse.getUnionid());
        WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByUnionId(weChatAppletLoginResponse.getUnionid());
        if (Objects.isNull(weChatCustomerLink)) {
            log.info("getLoginInfo: not linked");
            return buildNotLinked(claimsConsumer, appletLoginRedisParamResponse, token, weChatAppletLoginResponse.getUnionid());
        }
        log.info("getLoginInfo:  linked");
        //如果没有小程序的openId,则更新关联信息
        if (!weChatCustomerLink.hasAppletOpenId()) {
            weChatCustomerLink.setAppletOpenId(weChatAppletLoginResponse.getOpenid());
            weChatCustomerLinkService.updateWeChatCustomerLink(weChatCustomerLink);
        }
        appletLoginRedisParamResponse.setCustomerId(weChatCustomerLink.getCustomerId());
        claimsConsumer.accept(appletLoginRedisParamResponse);
        return AppletLoginInfo.buildLinked(token);
    }


    @Override
    public AppletLoginInfo dealUserInfo(WeChatAppletUserInfo weChatAppletUserInfo, String openId, String sessionKey, Consumer<AppletLoginRedisParamResponse> claimsConsumer) {
        log.debug("dealUserInfo and weChatAppletUserInfo:{} \r\n sessionKey:{} \r\n openId:{}", weChatAppletUserInfo, sessionKey, openId);
        String unionId = WeChatAppletUtils.getUnionIdFromUserInfo(sessionKey, weChatAppletUserInfo);
        if (StringUtils.isEmpty(unionId)) {
            log.error("dealUserInfo fail : getUnionIdFromUserInfo fail");
            claimsConsumer.accept(null);
            return AppletLoginInfo.buildNotLinked();
        }
        AppletLoginRedisParamResponse appletLoginRedisParamResponse = new AppletLoginRedisParamResponse();
        appletLoginRedisParamResponse.setUnionId(unionId);
        WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByUnionId(unionId);
        if (Objects.isNull(weChatCustomerLink)) {
            log.info("dealUserInfo: not linked");
            claimsConsumer.accept(appletLoginRedisParamResponse);
            return AppletLoginInfo.buildNotLinked().addUnionId(unionId);
        }
        log.info("dealUserInfo:  linked");
        //如果没有小程序的openId,则更新关联信息
        if (!weChatCustomerLink.hasAppletOpenId()) {
            weChatCustomerLink.setAppletOpenId(openId);
            weChatCustomerLinkService.updateWeChatCustomerLink(weChatCustomerLink);
        }
        appletLoginRedisParamResponse.setCustomerId(weChatCustomerLink.getCustomerId());
        claimsConsumer.accept(appletLoginRedisParamResponse);
        return AppletLoginInfo.buildLinked();
    }

    @Override
    public int checkCustomerBind(String userName) {
        log.debug("checkCustomerBind and userName:{} ", userName);
        WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByUserName(userName);
        if (!ObjectUtils.isEmpty(weChatCustomerLink) && !StringUtils.isEmpty(weChatCustomerLink.getAppletOpenId())) {
            log.error("checkCustomerBind fail : bind already");
            return -9;
        }
        return 1;
    }

    @Override
    public int bindAccount(String openId, String unionId, long customerId) {
        log.debug("bindAccount and openId:{} \r\n unionId:{} \r\n customerId:{}", openId, unionId, customerId);
        WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByCustomerId(customerId);
        if (!ObjectUtils.isEmpty(weChatCustomerLink) && !weChatCustomerLink.getUnionId().equals(unionId)) {
            log.error("checkCustomerBind fail : bind already");
            throw new ServiceException("R-00004");
        }
        if (Objects.isNull(weChatCustomerLink)) {
            weChatCustomerLink = new WeChatCustomerLink();
        }
        weChatCustomerLink.setUnionId(unionId);
        weChatCustomerLink.setAppletOpenId(openId);
        weChatCustomerLink.setCustomerId(customerId);
        //如果数据库中有记录就更新记录
        if (-1 != weChatCustomerLink.getId()) {
            return weChatCustomerLinkService.updateWeChatCustomerLink(weChatCustomerLink);
        }
        //否则新增
        return weChatCustomerLinkService.addWeChatCustomerLink(weChatCustomerLink);
    }

    @Override
    public int unbindAccount(long customerId, String unionId) {
        log.debug("unbindAccount and customerId:{} \r\n unionId:{}", customerId, unionId);
        return weChatCustomerLinkService.unbindWeChatCustomerLink(customerId, unionId);
    }

    /**
     * 构建未关联返回
     *
     * @param claimsConsumer                回调
     * @param appletLoginRedisParamResponse 小程序redis参数返回实体类
     * @param token                         token
     * @return 小程序登录信息
     */
    private AppletLoginInfo buildNotLinked(Consumer<AppletLoginRedisParamResponse> claimsConsumer, AppletLoginRedisParamResponse appletLoginRedisParamResponse, String token, String unionId) {
        claimsConsumer.accept(appletLoginRedisParamResponse);
        return AppletLoginInfo.buildNotLinked(token).addUnionId(unionId);
    }
}
