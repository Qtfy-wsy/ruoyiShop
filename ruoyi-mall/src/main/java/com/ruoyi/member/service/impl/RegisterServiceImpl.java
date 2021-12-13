package com.ruoyi.member.service.impl;


import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.RegisterService;
import com.ruoyi.member.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by 伊甸园商城 on 17/11/18.
 * 注册接口
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(RegisterServiceImpl.class);


    /**
     * 注入短信接口
     */
    @Autowired
    private SmsService smsService;

    /**
     * 注入会员服务接口
     */
    @Autowired
    private IUmsMemberService customerService;


    @Override
    public int sendRegisterSmsCode(String mobile, Consumer<String> consumer) {

        logger.debug("sendRegisterSmsCode and mobile:{}");


        // 如果要注册的手机号码存在 则直接返回
        if (customerService.isMobileExist(mobile) != 0) {
            logger.error("sendRegisterSmsCode fail due to mobile :{} is exist...", mobile);
            return -1;
        }

        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 短信验证码发送失败
        if (smsService.sendSms(mobile, code) == 1) {
            logger.error("send sms fail....");
            return 1;
        }

        //  发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(code);
        }

        return 0;
    }

    @Override
    public int sendRegisterSmsCodeForPc(String mobile, String kaptcha, String oldKaptcha, Consumer<String> consumer) {

        logger.debug("sendRegisterSmsCode and mobile:{}");


        // 如果要注册的手机号码存在 则直接返回
        if (customerService.isMobileExist(mobile) != 0) {
            logger.error("sendRegisterSmsCode fail due to mobile :{} is exist...", mobile);
            return -4;
        }

        int checkKaptchaRes = checkKaptcha(kaptcha, oldKaptcha);
        if (checkKaptchaRes != 1) {
            logger.error("sendRegisterSmsCode fail due to kaptcha is error...");
            return checkKaptchaRes;
        }

        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 短信验证码发送失败
        if (smsService.sendSms(mobile, code) == 1) {
            logger.error("send sms fail....");
            return 1;
        }

        //  发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(code);
        }

        return 0;
    }

    @Override
    public int registerCustomer(String mobile, String password, String code, String originCode, String recommondCode) {
        logger.debug("registerCustomer and mobile :{} \r\n code:{} \r\n: originCode{} \r\n recommondCode:{}", mobile, code, originCode, recommondCode);

        if (StringUtils.isEmpty(code)) {
            logger.error("registerCustomer fail due to code is error...");
            return -1;
        }

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            logger.error("registerCustomer fail due to mobile or password is empty...");
            return -2;
        }

        // 判断手机号码是否存在
        if (customerService.isMobileExist(mobile) != 0) {
            logger.error("registerCustomer fail due to mobile is exist");
            return -3;
        }

        // 校验验证码是否正确TODO
        if (!code.equals(originCode)) {
            logger.error("registerCustomer fail due to code is error...");
            return -1;
        }

        // 进行会员注册
        return customerService.addCustomer(UmsMember.buildAppRegisterCustomer(mobile, password, recommondCode));
    }

    /**
     * 免密用户自动注册
     *
     * @param phoneNo     手机号码
     * @param channelType 渠道类型
     * @return -1 手机验证码错误 -2 参数错误 0 失败  成功>0 -3 手机号码已存在 -10  推荐人不存在
     */
    @Override
    public Long unAuthRegister(String phoneNo, String channelType) {
        logger.debug("registerCustomer and mobile :{}", phoneNo);

        // 判断手机号码是否存在
        if (customerService.isMobileExist(phoneNo) != 0) {
            logger.error("registerCustomer fail due to mobile is exist");
            return -3L;
        }
        UmsMember customer = UmsMember.buildH5RegisterCustomer(phoneNo, null);
        customer.setChannelType(channelType);
        // 进行会员注册
        return customerService.autoAddCustomer(customer);
    }

    @Override
    public int checkKaptcha(String kaptcha, String kaptchaInSession) {
        logger.debug("register checkKaptcha and kaptcha:{}\r\n kaptchaInSession:{}", kaptcha, kaptchaInSession);
        if (StringUtils.isEmpty(kaptchaInSession)) {
            logger.error("register checkKaptcha fail:kaptchaInSession is not exist ");
            return -1;
        }
        if (StringUtils.isEmpty(kaptcha)) {
            logger.error("register checkKaptcha fail:kaptcha is not exist ");
            return -2;
        }
        if (!kaptcha.equals(kaptchaInSession)) {
            logger.error("register checkKaptcha fail:kaptchaInSession is not equal to kaptcha ");
            return -3;
        }
        return 1;
    }
}
