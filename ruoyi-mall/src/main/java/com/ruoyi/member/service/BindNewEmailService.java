package com.ruoyi.member.service;


import com.ruoyi.member.vo.BindNewEmailRequest;

import java.util.function.BiConsumer;

/**
 * 绑定新邮箱服务接口
 */
public interface BindNewEmailService {

    /**
     * 给手机发送短信验证码
     *
     * @param customerId 用户id
     * @param kaptcha    用户输入的图片验证码
     * @param oldKaptcha redis中的图片验证码
     * @return 0 成功 1 失败
     */
    int sendValidateToMobile(long customerId, String kaptcha, String oldKaptcha, BiConsumer<String, String> consumer);

    /**
     * 校验用户输入的验证码是否正确 (重新绑定手机号码第一步的校验验证码)
     *
     * @param code       用户输入的验证码
     * @param originCode redis中的验证码
     * @param mobile     手机号码
     * @return 0 成功 -1 手机号码不存在 -2 短信验证码错误
     */
    int validateCode(String code, String originCode, String mobile);

    /**
     * 校验验证码
     *
     * @param kaptcha          验证码
     * @param kaptchaInSession session中的验证码
     * @return -1 验证码失效或不存在 -2 验证码为空 -3验证码不正确  1 成功
     */
    int checkKaptcha(String kaptcha, String kaptchaInSession);

    /**
     * 发送邮件
     *
     * @param bindNewEmailRequest 绑定新邮箱实体
     * @return 1:成功 -1缺少参数 -2没有凭证 -3邮箱已存在 -4发送邮件失败 -5图片验证码错误
     */
    int sendEmail(BindNewEmailRequest bindNewEmailRequest);

    /**
     * 重新发送邮件
     *
     * @param customerId 用户id
     * @return 1:成功 -1用户不存在 -2没有更改邮箱的信息 -3发送邮件失败
     */
    int reSendEmail(long customerId);

    /**
     * 更新邮箱
     *
     * @param checkCodeS        校验码(加密后)
     * @param customerIdS       用户id(加密后)
     * @param finalDateS        验证截止时间（加密后）
     * @param key               加密key
     * @param currentCustomerId session中的customerId
     * @return 1:成功 -1用户id不一致 -2:没有待验证邮箱 -3验证时间已过 -4邮箱已存在 -5异常
     */
    int modifiedEmail(String checkCodeS, String customerIdS, String finalDateS, String key, long currentCustomerId);
}
