package com.ruoyi.member.service.impl;


import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.BindNewMobileService;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.SmsService;
import com.ruoyi.member.vo.BindNewMobileRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created by 魔金商城 on 17/11/20.
 * 重新绑定手机号码服务
 */
@Service
public class BindNewMobileServiceImpl implements BindNewMobileService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(BindNewMobileServiceImpl.class);

    /**
     * 注入用户服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入短信服务接口
     */
    @Autowired
    private SmsService smsService;

    @Override
    public int sendValidateToOldMobile(long customerId, BiConsumer<String, String> consumer) {
        logger.debug("sendValidateToOldMobile and customerId:{}", customerId);


        // 查询用户手机号码
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);

        if (Objects.isNull(customer)) {
            logger.error("sendValidateToOldMobile fail due to member is not exist...customerId:{}", customerId);
            return 1;
        }


        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 发送短信失败
        if (smsService.sendSms(customer.getMobile(), code) == 1) {
            logger.error("send sms fail...");
            return 1;
        }

        // 短信发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(customer.getMobile(), code);
        }

        return 0;
    }

    @Override
    public int sendValidateToOldMobileForPc(long customerId, String kaptcha, String oldKaptcha, BiConsumer<String, String> consumer) {
        logger.debug("sendValidateToOldMobileForPc and customerId:{} \r\n kaptcha:{} \r\n oldKaptcha:{}", customerId, kaptcha, oldKaptcha);


        // 查询用户手机号码
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);

        if (Objects.isNull(customer)) {
            logger.error("sendValidateToOldMobile fail due to member is not exist...customerId:{}", customerId);
            return 1;
        }

        int checkKaptchaRes = checkKaptcha(kaptcha, oldKaptcha);
        if (checkKaptchaRes != 1) {
            logger.error("sendValidateToOldMobile fail due to kaptcha is error...");
            return checkKaptchaRes;
        }

        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 发送短信失败
        if (smsService.sendSms(customer.getMobile(), code) == 1) {
            logger.error("send sms fail...");
            return 1;
        }

        // 短信发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(customer.getMobile(), code);
        }

        return 0;
    }

    @Override
    public int validateCode(String code, String originCode) {

        logger.debug("validateCode and code:{} \r\n originCode:{}", code, originCode);

        if (StringUtils.isEmpty(code)) {
            logger.error("validateCode fail due to code is empty...");
            return -1;
        }

        return code.equals(originCode) ? 0 : -1;
    }

    @Override
    public int sendNewMobileCode(String mobile, Consumer<String> consumer) {
        logger.debug("sendNewMobileCode and mobile:{}", mobile);


        // 如果要注册的手机号码存在 则直接返回
        if (customerService.isMobileExist(mobile) != 0) {
            logger.error("sendNewMobileCode fail due to mobile :{} is exist...", mobile);
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
    public int sendNewMobileCodeForPc(String mobile, String kaptcha, String oldKaptcha, Consumer<String> consumer) {
        logger.debug("sendNewMobileCodeForPc and mobile:{} \r\n kaptcha:{} \r\n oldKaptcha:{}", mobile, kaptcha, oldKaptcha);


        // 如果要注册的手机号码存在 则直接返回
        if (customerService.isMobileExist(mobile) != 0) {
            logger.error("sendNewMobileCode fail due to mobile :{} is exist...", mobile);
            return -4;
        }

        int checkKaptchaRes = checkKaptcha(kaptcha, oldKaptcha);
        if (checkKaptchaRes != 1) {
            logger.error("sendNewMobileCode fail due to kaptcha is error...");
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
    public int bindNewMobile(BindNewMobileRequest bindNewMobileRequest) {
        logger.debug("bindNewMobile and bindNewMobileRequest :{}", bindNewMobileRequest);

        if (Objects.isNull(bindNewMobileRequest)) {
            logger.error("bindNewMobile fail due to bindNewMobileRequest is empty...");
            return -1;
        }

        // 判断是否有凭证
        if (!bindNewMobileRequest.hasOwnCert()) {
            logger.error("bindNewMobile fail due to has no cert...");
            return -2;
        }

        // 校验短信验证码
        if (!bindNewMobileRequest.validateCode()) {
            logger.error("bindNewMobile fail due to code is error...");
            return -3;
        }


        // 判断手机号码是否存在
        if (customerService.isMobileExist(bindNewMobileRequest.getMobile()) != 0) {
            logger.error("bindNewMobile fail due to mobile is exist");
            return -4;
        }

        // 修改用户手机号码
        return customerService.bindNewMobile(bindNewMobileRequest.getCustomerId(), bindNewMobileRequest.getMobile());
    }

    @Override
    public int checkKaptcha(String kaptcha, String kaptchaInSession) {
        logger.debug("bindnewmobile checkKaptcha and kaptcha:{}\r\n kaptchaInSession:{}", kaptcha, kaptchaInSession);
        if (StringUtils.isEmpty(kaptchaInSession)) {
            logger.error("bindnewmobile checkKaptcha fail:kaptchaInSession is not exist ");
            return -1;
        }
        if (StringUtils.isEmpty(kaptcha)) {
            logger.error("bindnewmobile checkKaptcha fail:kaptcha is not exist ");
            return -2;
        }
        if (!kaptcha.equals(kaptchaInSession)) {
            logger.error("bindnewmobile checkKaptcha fail:kaptchaInSession is not equal to kaptcha ");
            return -3;
        }
        return 1;
    }
}
