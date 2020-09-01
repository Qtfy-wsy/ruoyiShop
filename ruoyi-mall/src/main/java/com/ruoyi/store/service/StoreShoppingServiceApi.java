package com.ruoyi.store.service;

import java.util.function.Consumer;

/**
 * Created by 魔金商城 on 18/4/11
 * 代客下单服务接口
 */
public interface StoreShoppingServiceApi {

    /**
     * 发送短信验证码
     *
     * @param mobile   手机号码
     * @param consumer 回调接口
     * @return 0 成功 1 失败 -1 成功，手机号码未注册
     */
    int sendSmsCode(String mobile, Consumer<String> consumer);

    /**
     * 校验用户输入的短信验证码是否正确
     *
     * @param code          用户输入的短信验证码
     * @param originCode    redis中的验证码
     * @param mobile        手机号码
     * @param beloneStoreId 店铺id
     * @return 0 正确 -1 错误 -2 自动注册会员失败
     */
    int validateCode(String code, String originCode, String mobile, long beloneStoreId);

}
