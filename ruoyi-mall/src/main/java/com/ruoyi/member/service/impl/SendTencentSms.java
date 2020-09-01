package com.ruoyi.member.service.impl;

import com.github.qcloudsms.SmsMultiSender;
import com.github.qcloudsms.SmsResultBase;
import com.github.qcloudsms.SmsSingleSender;
import com.ruoyi.setting.domain.LsSmsSetting;
import lombok.extern.slf4j.Slf4j;

/**
 * Tencent Cloud Sms Sendsms
 * https://cloud.tencent.com/document/product/382/38778
 */
@Slf4j
public class SendTencentSms {
    /**
     * 按模板发送短信 支持单发和群发
     *
     * @param smsSet 短信配置
     */
    public static int sendMessage(LsSmsSetting smsSet, String[] params, String phones) {

        String regex = ";";
        String[] phoneNumbers = phones.split(regex);
        SmsResultBase result = null;

        try {
            log.debug("url:{},phones:{},templateId:{},params:{}", smsSet.getUrl(), phones, smsSet.getTemplateId(), params);
            // 是否单发
            if (phoneNumbers.length == 1) {
                //APPID getInterfaceUrl
                SmsSingleSender ssender = new SmsSingleSender(Integer.parseInt(smsSet.getUrl()), smsSet.getKey());
                result = ssender.sendWithParam("86", phoneNumbers[0], Integer.parseInt(smsSet.getTemplateId()), params, smsSet.getSign(), "", "");
            } else {
                SmsMultiSender msender = new SmsMultiSender(Integer.parseInt(smsSet.getUrl()), smsSet.getKey());
                result = msender.sendWithParam("86", phoneNumbers, Integer.parseInt(smsSet.getTemplateId()), params, smsSet.getSign(), "", "");
            }
            return 0;
        } catch (Exception e) {
            log.error("腾讯云短信发送失败,phoneNumbers={},smsSet={}", phoneNumbers, smsSet, e);
            return 1;
        }
    }

}