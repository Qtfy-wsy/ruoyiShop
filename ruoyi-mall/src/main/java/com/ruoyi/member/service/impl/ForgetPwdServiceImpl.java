package com.ruoyi.member.service.impl;


import com.ruoyi.member.service.ForgetPwdService;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.SmsService;
import com.ruoyi.member.vo.ForgetPwdRequest;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by 伊甸园商城 on 17/11/22.
 * 忘记密码
 */
@Service
public class ForgetPwdServiceImpl implements ForgetPwdService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(ForgetPwdServiceImpl.class);

    /**
     * 注入用户服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入手机服务接口
     */
    @Autowired
    private SmsService smsService;

    @Override
    public int validateKaptcha(String code, String originCode, String mobile) {
        logger.debug("validateKaptcha and code:{} \r\n originCode:{} \r\n :{}", code, originCode, mobile);

        // 手机号码不存在直接返回
        if (customerService.isMobileExist(mobile) == 0) {
            logger.error("validateKaptcha fail due to mobile is not exist....");
            return -1;
        }

        if (StringUtils.isEmpty(code)) {
            logger.error("validateKaptcha fail due to code is empty...");
            return -2;
        }
        return code.equals(originCode) ? 0 : -2;
    }

    @Override
    public int sendCode(String mobile, Consumer<String> consumer) {

        logger.debug("sendCode and mobile:{}", mobile);

        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 发送手机验证码失败直接返回
        if (smsService.sendSms(mobile, code) == 1) {
            return -1;
        }

        if (Objects.nonNull(consumer)) {
            consumer.accept(code);
        }

        return 0;
    }

    @Override
    public int sendCodeForPc(String mobile, String kaptcha, String oldKaptcha, Consumer<String> consumer) {

        logger.debug("sendCodeForPc and mobile:{} \r\n kaptcha:{} \r\n oldKaptcha:{}", mobile, kaptcha, oldKaptcha);

        // 手机号码不存在直接返回
        if (customerService.isMobileExist(mobile) == 0) {
            logger.error("sendCodeForPc fail due to mobile is not exist....");
            return -4;
        }

        int checkKaptchaRes = checkKaptcha(kaptcha, oldKaptcha);
        if (checkKaptchaRes != 1) {
            logger.error("sendCodeForPc fail due to kaptcha is error...");
            return checkKaptchaRes;
        }

        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 发送手机验证码失败直接返回
        if (smsService.sendSms(mobile, code) == 1) {
            logger.error("send sms error..");
            return 1;
        }

        if (Objects.nonNull(consumer)) {
            consumer.accept(code);
        }

        return 0;
    }

    @Override
    public int updatePassword(ForgetPwdRequest forgetPwdRequest) {

        logger.debug("updatePassword and forgetPwdRequest:{}", forgetPwdRequest);

        if (Objects.isNull(forgetPwdRequest)) {
            logger.error("updatePassword fail due to forgetPwdRequest is null...");
            return -1;
        }

        // 手机号码不存在直接返回
        if (customerService.isMobileExist(forgetPwdRequest.getMobile()) == 0) {
            logger.error("updatePassword fail due to mobile is not exist....");
            return -3;
        }

        if (!forgetPwdRequest.hasCertificate()) {
            logger.error("updatePassword fail due to has no certificate");
            return -2;
        }

        if (CommonConstant.FROM_PC.equals(forgetPwdRequest.getRequestFrom())) {

            int checkKaptchaRes = checkKaptcha(forgetPwdRequest.getKaptcha(), forgetPwdRequest.getOldKaptcha());
            if (checkKaptchaRes != 1) {
                logger.error("updatePassword fail due to kaptcha is error...");
                return -4;
            }

        } else {
            if (!forgetPwdRequest.validateCode()) {
                logger.error("updatePassword fail due to code is error...");
                return -2;
            }
        }

        return customerService.updatePasswordByMobile(forgetPwdRequest.getMobile(), forgetPwdRequest.getPassword());
    }

    @Override
    public int checkKaptcha(String kaptcha, String kaptchaInSession) {
        logger.debug("forgetpwd checkKaptcha and kaptcha:{}\r\n kaptchaInSession:{}", kaptcha, kaptchaInSession);
        if (StringUtils.isEmpty(kaptchaInSession)) {
            logger.error("forgetpwd checkKaptcha fail:kaptchaInSession is not exist ");
            return -1;
        }
        if (StringUtils.isEmpty(kaptcha)) {
            logger.error("forgetpwd checkKaptcha fail:kaptcha is not exist ");
            return -2;
        }
        if (!kaptcha.equals(kaptchaInSession)) {
            logger.error("forgetpwd checkKaptcha fail:kaptchaInSession is not equal to kaptcha ");
            return -3;
        }
        return 1;
    }

}
