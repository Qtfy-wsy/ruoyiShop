package com.ruoyi.member.service;


import com.ruoyi.member.vo.BindNewMobileRequest;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by 魔金商城 on 17/11/20.
 * 重新绑定手机号码服务接口
 */
public interface BindNewMobileService {

    /**
     * 给旧的手机发送短信验证码
     *
     * @param customerId 用户id
     * @return 0 成功 1 失败
     */
    int sendValidateToOldMobile(long customerId, BiConsumer<String, String> consumer);

    /**
     * 给旧的手机发送短信验证码（PC端用，带有图片验证码）
     *
     * @param customerId 用户id
     * @param kaptcha    用户输入的图片验证码
     * @param oldKaptcha redis中的图片验证码
     * @param consumer   回调接口
     * @return 0 成功 1 失败 -1 图片验证码失效或不存在 -2 图片验证码为空 -3 图片证码不正确
     */
    int sendValidateToOldMobileForPc(long customerId, String kaptcha, String oldKaptcha, BiConsumer<String, String> consumer);

    /**
     * 校验用户输入的验证码是否正确 (重新绑定手机号码第一步的校验验证码)
     *
     * @param code       用户输入的验证码
     * @param originCode redis中的验证码
     * @return 0 正确 -1 错误
     */
    int validateCode(String code, String originCode);

    /**
     * 给绑定的新手机发送验证码
     *
     * @param mobile   手机号码
     * @param consumer 回调
     * @return 0 成功 1 失败 -1 手机号码已经存在
     */
    int sendNewMobileCode(String mobile, Consumer<String> consumer);

    /**
     * 给绑定的新手机发送验证码（PC端用，带有图片验证码）
     *
     * @param mobile     手机号码
     * @param kaptcha    用户输入的图片验证码
     * @param oldKaptcha redis中的图片验证码
     * @param consumer   回调接口
     * @return 0 成功 1 失败 -4 手机号码已经存在 -1 图片验证码失效或不存在 -2 图片验证码为空 -3 图片证码不正确
     */
    int sendNewMobileCodeForPc(String mobile, String kaptcha, String oldKaptcha, Consumer<String> consumer);

    /**
     * 绑定新的手机号码
     *
     * @param bindNewMobileRequest 绑定实体
     * @return -1 参数错误 -2 没有凭证 -3 验证码错误 -4 手机号码已经存在 1 成功  0 失败
     */
    int bindNewMobile(BindNewMobileRequest bindNewMobileRequest);

    /**
     * 校验验证码
     *
     * @param kaptcha          验证码
     * @param kaptchaInSession session中的验证码
     * @return -1 验证码失效或不存在 -2 验证码为空 -3验证码不正确  1 成功
     */
    int checkKaptcha(String kaptcha, String kaptchaInSession);
}
