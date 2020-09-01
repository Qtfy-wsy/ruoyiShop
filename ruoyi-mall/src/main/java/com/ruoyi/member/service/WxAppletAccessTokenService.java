package com.ruoyi.member.service;


import com.ruoyi.common.utils.bean.WechatSetting;

/**
 * 微信小程序access_token服务接口
 *
 * @author 魔金商城 created on 2020/4/28
 */
public interface WxAppletAccessTokenService {

    /**
     * 获取微信小程序accesstoken
     *
     * @param wechatSetting 微信设置实体
     * @return 微信小程序accesstoken
     */
    String getWxAppletAccessToken(WechatSetting wechatSetting);

}
