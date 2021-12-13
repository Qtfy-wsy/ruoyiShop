package com.ruoyi.store.service.impl;


import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.SmsService;
import com.ruoyi.store.service.StoreShoppingServiceApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by 伊甸园商城 on 18/4/11
 * 代客下单服务接口
 */
@Slf4j
@Service
public class StoreShoppingServiceApiImpl implements StoreShoppingServiceApi {

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
    public int sendSmsCode(String mobile, Consumer<String> consumer) {

        log.debug("sendSmsCode and mobile:{}", mobile);

        // 生成的6位数数字
        String code = String.format("%06d", (int) (Math.random() * 1000000));

        // 短信验证码发送失败
        if (smsService.sendSms(mobile, code) == 1) {
            log.error("send sms fail....");
            return 1;
        }

        //  发送成功后回调
        if (Objects.nonNull(consumer)) {
            consumer.accept(code);
        }

        if (customerService.isMobileExist(mobile) == 0) {
            return -1;
        }

        return 0;
    }

    @Override
    public int validateCode(String code, String originCode, String mobile, long beloneStoreId) {

        log.debug("validateCode and code:{} \r\n originCode:{}", code, originCode);

        if (StringUtils.isEmpty(code)) {
            log.error("validateCode fail due to code is empty...");
            return -1;
        }

        if (code.equals(originCode)) {
            // 手机号码未注册，自动注册
            if (customerService.isMobileExist(mobile) == 0) {
                int res = customerService.addCustomer(UmsMember.buildStoreShoppingRegisterCustomer(mobile, beloneStoreId));
                if (res == 1) {
                    return 0;
                } else {
                    log.error("validateCode fail due to addCustomer fail");
                    return -2;
                }
            }
            return 0;
        } else {
            return -1;
        }
    }

}
