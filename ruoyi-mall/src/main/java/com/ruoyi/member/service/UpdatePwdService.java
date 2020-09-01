package com.ruoyi.member.service;


import com.ruoyi.member.vo.UpdatePwdBean;

import java.util.function.BiConsumer;

/**
 * Created by 魔金商城 on 17/11/20.
 * 修改密码服务接口
 */
public interface UpdatePwdService {

    /**
     * 发送修改密码短信验证码
     *
     * @param customerId 用户id
     * @param consumer   回调接口
     * @return 成功返回0 失败返回1
     */
    int sendUpdatePwdSmsCode(long customerId, BiConsumer<String, String> consumer);

    /**
     * 发送修改密码短信验证码（PC端用，带有图片验证码）
     *
     * @param customerId 用户id
     * @param kaptcha    用户输入的图片验证码
     * @param oldKaptcha redis中的图片验证码
     * @param consumer   回调接口
     * @return 成功返回0 失败返回1 -1 图片验证码失效或不存在 -2 图片验证码为空 -3 图片证码不正确
     */
    int sendUpdatePwdSmsCodeForPc(long customerId, String kaptcha, String oldKaptcha, BiConsumer<String, String> consumer);

    /**
     * 校验验证码
     *
     * @param code       用户输入的验证码
     * @param originCode 原始的验证码
     * @param mobile     手机号码
     * @return 0 成功  -1 手机号码不存在  -2 验证码错误
     */
    int validateCode(String code, String originCode, String mobile);

    /**
     * 修改用户密码
     *
     * @param updatePwdBean 修改密码实体
     * @return -1 参数错误  -2 验证码错误 -3 用户不匹配 -4 图片验证码错误 0 失败 1 成功
     */
    int updatePassword(UpdatePwdBean updatePwdBean);

    /**
     * 校验验证码
     *
     * @param kaptcha          验证码
     * @param kaptchaInSession session中的验证码
     * @return -1 验证码失效或不存在 -2 验证码为空 -3验证码不正确  1 成功
     */
    int checkKaptcha(String kaptcha, String kaptchaInSession);
}
