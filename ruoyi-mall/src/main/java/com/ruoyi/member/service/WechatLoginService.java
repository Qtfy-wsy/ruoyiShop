package com.ruoyi.member.service;


import com.ruoyi.member.domain.WeChatCustomerLink;

import java.util.function.Consumer;

/**
 * 微信登录服务接口
 */
public interface WechatLoginService {

    /**
     * 微信登录
     *
     * @param code     code说明 ： code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
     * @param state    微信重定向附带参数
     * @param consumer 回调函数
     * @return -1获取accessToken出错 -2 没有绑定用户 1:有绑定用户
     */
    int wechatLogin(String code, String state, Consumer<WeChatCustomerLink> consumer);

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
     * @param consumer   回调
     * @return 1:成功
     */
    int unbindAccount(long customerId, String unionId, Consumer<String> consumer);

}
