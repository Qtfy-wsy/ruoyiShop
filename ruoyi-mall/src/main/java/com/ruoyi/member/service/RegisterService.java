package com.ruoyi.member.service;

import java.util.function.Consumer;

/**
 * Created by 伊甸园商城 on 17/11/18.
 * 注册服务接口
 */
public interface RegisterService {

    /**
     * 发送注册短信验证码
     *
     * @param mobile   手机号码
     * @param consumer 回调接口
     * @return 0 成功 1 失败 -1 手机号码已经存在
     */
    int sendRegisterSmsCode(String mobile, Consumer<String> consumer);

    /**
     * 发送注册短信验证码（PC端用，带有图片验证码）
     *
     * @param mobile     手机号码
     * @param kaptcha    用户输入的图片验证码
     * @param oldKaptcha redis中的图片验证码
     * @param consumer   回调接口
     * @return 0 成功 1 失败 -4 手机号码已经存在 -1 图片验证码失效或不存在 -2 图片验证码为空 -3 图片证码不正确
     */
    int sendRegisterSmsCodeForPc(String mobile, String kaptcha, String oldKaptcha, Consumer<String> consumer);

    /**
     * 用户注册
     *
     * @param mobile        手机号码
     * @param password      密码
     * @param code          用户输入的手机验证码
     * @param originCode    生成的手机验证码
     * @param recommondCode 推荐吗
     * @return -1 手机验证码错误 -2 参数错误 0 失败  成功>0 -3 手机号码已存在 -10  推荐人不存在
     */
    int registerCustomer(String mobile, String password, String code, String originCode, String recommondCode);

    /**
     * 免密用户自动注册
     *
     * @param phoneNo 手机号码
     * @return -1 手机验证码错误 -2 参数错误 0 失败  成功>0 -3 手机号码已存在 -10  推荐人不存在
     */
    Long unAuthRegister(String phoneNo, String channelType);

    /**
     * 校验验证码
     *
     * @param kaptcha          验证码
     * @param kaptchaInSession session中的验证码
     * @return -1 验证码失效或不存在 -2 验证码为空 -3验证码不正确  1 成功
     */
    int checkKaptcha(String kaptcha, String kaptchaInSession);

}
