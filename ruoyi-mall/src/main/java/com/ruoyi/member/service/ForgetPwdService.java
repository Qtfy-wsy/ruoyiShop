package com.ruoyi.member.service;


import com.ruoyi.member.vo.ForgetPwdRequest;

import java.util.function.Consumer;

/**
 * Created by 魔金商城 on 17/11/22.
 * 忘记密码服务接口
 */
public interface ForgetPwdService {

    /**
     * 校验验证码
     *
     * @param code       用户输入的验证码
     * @param originCode 原始的验证码
     * @param mobile     手机号码
     * @return 0 成功  -1 手机号码不存在  -2 验证码错误
     */
    int validateKaptcha(String code, String originCode, String mobile);

    /**
     * 发送手机验证码
     *
     * @param mobile   手机号码
     * @param consumer 回调
     * @return -1 发送失败 0 成功
     */
    int sendCode(String mobile, Consumer<String> consumer);

    /**
     * 发送忘记密码短信验证码（PC端用，带有图片验证码）
     *
     * @param mobile     手机号码
     * @param kaptcha    用户输入的图片验证码
     * @param oldKaptcha redis中的图片验证码
     * @param consumer   回调接口
     * @return 成功返回0 失败返回1 -1 图片验证码失效或不存在 -2 图片验证码为空 -3 图片证码不正确 -4 手机号码不存在
     */
    int sendCodeForPc(String mobile, String kaptcha, String oldKaptcha, Consumer<String> consumer);

    /**
     * 修改密码
     *
     * @param forgetPwdRequest 修改密码请求
     * @return -1 参数错误  -2 没有凭证 -3 手机号码不存在 -4 图片证码错误  0 失败 1 成功
     */
    int updatePassword(ForgetPwdRequest forgetPwdRequest);

    /**
     * 校验验证码
     *
     * @param kaptcha          验证码
     * @param kaptchaInSession session中的验证码
     * @return -1 验证码失效或不存在 -2 验证码为空 -3验证码不正确  1 成功
     */
    int checkKaptcha(String kaptcha, String kaptchaInSession);
}
