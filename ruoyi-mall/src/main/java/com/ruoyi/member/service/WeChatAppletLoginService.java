package com.ruoyi.member.service;


import com.ruoyi.common.utils.bean.WeChatAppletUserInfo;
import com.ruoyi.member.vo.AppletLoginInfo;
import com.ruoyi.member.vo.AppletLoginRedisParamResponse;

import java.util.function.Consumer;

/**
 * 微信小程序登录服务
 *
 * @author SK
 * @since 2018/6/13
 */
public interface WeChatAppletLoginService {

    /**
     * 小程序获取登录信息
     *
     * @param code           用户登录凭证（有效期五分钟）。开发者需要在开发者服务器后台调用 api，使用 code 换取 openid 和 session_key 等信息
     * @param unionId        联合登录id
     * @param claimsConsumer 回调
     * @return 登录信息
     */
    AppletLoginInfo getLoginInfo(String code, String unionId, Consumer<AppletLoginRedisParamResponse> claimsConsumer);

    /**
     * 处理小程序用户信息
     *
     * @param weChatAppletUserInfo 小程序用户信息实体
     * @param openId               微信用户标识
     * @param sessionKey           小程序sessionKey
     * @param claimsConsumer       回调
     * @return 登录信息
     */
    AppletLoginInfo dealUserInfo(WeChatAppletUserInfo weChatAppletUserInfo, String openId, String sessionKey, Consumer<AppletLoginRedisParamResponse> claimsConsumer);


    /**
     * 检验用户是否已绑定
     *
     * @param userName 用户名
     * @return -9已经绑定过 1:没有绑定
     */
    int checkCustomerBind(String userName);

    /**
     * 绑定账号
     *
     * @param openId     微信用户唯一标识
     * @param unionId    微信联合登录id
     * @param customerId 用户id
     * @return 1:成功
     */
    int bindAccount(String openId, String unionId, long customerId);

    /**
     * 解绑账号
     *
     * @param customerId 用户id
     * @param unionId    联合登录id,用于redis缓存
     * @return 1:成功
     */
    int unbindAccount(long customerId, String unionId);
}
