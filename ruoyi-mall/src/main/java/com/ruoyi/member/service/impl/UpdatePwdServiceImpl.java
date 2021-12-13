package com.ruoyi.member.service.impl;


import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.SmsService;
import com.ruoyi.member.service.UpdatePwdService;
import com.ruoyi.member.vo.UpdatePwdBean;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * Created by 伊甸园商城 on 17/11/20.
 * 修改密码服务接口实现
 */
@Service
public class UpdatePwdServiceImpl implements UpdatePwdService {

    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(UpdatePwdServiceImpl.class);

    /**
     * 注入短信服务接口
     */
    @Autowired
    private SmsService smsService;

    /**
     * 注入用户服务接口
     */
    @Autowired
    private IUmsMemberService customerService;

    @Override
    public int sendUpdatePwdSmsCode(long customerId, BiConsumer<String, String> consumer) {
        logger.debug("sendUpdatePwdSmsCode and customerId:{}", customerId);

        // 根据用户id查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);

        if (Objects.isNull(customer)) {
            logger.error("sendUpdatePwdSmsCode fail due to member is not exist");
            return 1;
        }


        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 短信发送失败
        if (smsService.sendSms(customer.getMobile(), code) == 1) {
            logger.error("send sms error..");
            return 1;
        }

        // 短信发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(customer.getMobile(), code);
        }

        return 0;
    }

    @Override
    public int sendUpdatePwdSmsCodeForPc(long customerId, String kaptcha, String oldKaptcha, BiConsumer<String, String> consumer) {
        logger.debug("sendUpdatePwdSmsCodeForPc and customerId:{}", customerId);

        // 根据用户id查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);

        if (Objects.isNull(customer)) {
            logger.error("sendUpdatePwdSmsCode fail due to member is not exist");
            return 1;
        }

        int checkKaptchaRes = checkKaptcha(kaptcha, oldKaptcha);
        if (checkKaptchaRes != 1) {
            logger.error("sendUpdatePwdSmsCode fail due to kaptcha is error...");
            return checkKaptchaRes;
        }

        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 短信发送失败
        if (smsService.sendSms(customer.getMobile(), code) == 1) {
            logger.error("send sms error..");
            return 1;
        }

        // 短信发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(customer.getMobile(), code);
        }

        return 0;
    }


    @Override
    public int validateCode(String code, String originCode, String mobile) {
        logger.debug("validatCode and code:{} \r\n originCode:{} \r\n :{}", code, originCode, mobile);

        // 手机号码不存在直接返回
        if (customerService.isMobileExist(mobile) == 0) {
            logger.error("validatCode fail due to mobile is not exist....");
            return -1;
        }

        if (StringUtils.isEmpty(code)) {
            logger.error("validatCode fail due to code is empty...");
            return -2;
        }
        return code.equals(originCode) ? 0 : -2;
    }


    @Override
    public int updatePassword(UpdatePwdBean updatePwdBean) {

        logger.debug("updatePassword and updatePwdBean:{}", updatePwdBean);

        if (Objects.isNull(updatePwdBean)) {
            logger.error("updatePassword fail due to updatePwdBean is empty...");
            return -1;
        }

        UmsMember customer = customerService.queryCustomerWithNoPasswordById(updatePwdBean.getCustomerId());

        if (Objects.isNull(customer)) {
            logger.error("updatePassword fail due to member is not exist");
            return -3;
        }

        // 用户不匹配
        if (!customer.getMobile().equals(updatePwdBean.getMobile())) {
            logger.error("updatePassword due to mobile error...");
            return -3;
        }


        //判断是不是pc请求
        if (CommonConstant.FROM_PC.equals(updatePwdBean.getRequestFrom())) {

            if (StringUtils.isEmpty(updatePwdBean.getPassword())) {
                logger.error("updatePassword fail due to params is error....");
                return -1;
            }

            // 校验是否有凭证
            if (!updatePwdBean.hasCertificate()) {
                logger.error("updatePassword fail due to no certificate ...");
                return -2;
            }

            int checkKaptchaRes = checkKaptcha(updatePwdBean.getKaptcha(), updatePwdBean.getOldKaptcha());
            if (checkKaptchaRes != 1) {
                logger.error("updatePassword fail due to kaptcha is error...");
                return -4;
            }

        } else {
            if (StringUtils.isEmpty(updatePwdBean.getCode()) || StringUtils.isEmpty(updatePwdBean.getPassword())) {
                logger.error("updatePassword fail due to params is error....");
                return -1;
            }
            // 校验用户输入的验证码是否正确
            if (!updatePwdBean.validateCode()) {
                logger.error("updatePassword fail due to code is error...");
                return -2;
            }
        }


        return customerService.updatePassword(updatePwdBean.getCustomerId(), updatePwdBean.getPassword());
    }

    @Override
    public int checkKaptcha(String kaptcha, String kaptchaInSession) {
        logger.debug("updatepwd checkKaptcha and kaptcha:{}\r\n kaptchaInSession:{}", kaptcha, kaptchaInSession);
        if (StringUtils.isEmpty(kaptchaInSession)) {
            logger.error("updatepwd checkKaptcha fail:kaptchaInSession is not exist ");
            return -1;
        }
        if (StringUtils.isEmpty(kaptcha)) {
            logger.error("updatepwd checkKaptcha fail:kaptcha is not exist ");
            return -2;
        }
        if (!kaptcha.equals(kaptchaInSession)) {
            logger.error("updatepwd checkKaptcha fail:kaptchaInSession is not equal to kaptcha ");
            return -3;
        }
        return 1;
    }
}
