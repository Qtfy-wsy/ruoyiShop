package com.ruoyi.member.service.impl;


import com.ruoyi.common.utils.bean.RequestParam;
import com.ruoyi.common.utils.sms.SmsUtil;
import com.ruoyi.member.service.SmsService;
import com.ruoyi.setting.domain.LsSmsSetting;
import com.ruoyi.setting.service.ILsSmsSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by 伊甸园商城 on 17/11/18.
 * 发送短信接口实现
 */
@Service
public class SmsServiceImpl implements SmsService {

    /**
     * 团长审批状态
     */
    private static final String STATUS = "通过";
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);
    /**
     * 注入短信设置服务接口
     */
    @Autowired
    private ILsSmsSettingService smsSetService;

    @Override
    public int sendSms(String phone, String content) {
        int result = 1;
        logger.debug("sendSms and phone:{} \r\n content:{}", phone, content);

        // SmsSet smsSet = smsSetService.querySmsSet().get(0);
        List<LsSmsSetting> smsSetList = smsSetService.selectLsSmsSettingList(null);
        if (Objects.isNull(smsSetList)) {
            logger.error("sendSms fail due to smsSet is null...");
            return result;
        }
        for (LsSmsSetting smsSet : smsSetList) {
            // 阿里云短信
            if (smsSet.getId() == 1 && smsSet.getStatus()) {
                RequestParam requestParam = convert(phone, smsSet);
                requestParam.setTemplateCode(smsSet.getTemplateId());
                requestParam.setTemplateParam("{\"code\":\"" + content + "\"}");
                result = SmsUtil.newSendSms(requestParam);
            } else if (smsSet.getId() == 2 && smsSet.getStatus()) { // 腾讯云短信
                String[] params = new String[1];
                params[0] = content;
                result = SendTencentSms.sendMessage(smsSet, params, phone);
            }
        }
        return result;
    }

    @Override
    public int sendWiteOffCodeSms(String phone, String code, String writeOffCode, String storeName) {
        logger.debug("sendWiteOffCodeSms and phone:{} \r\n code:{} \r\n writeOffCode:{} \r\n storeName:{}", phone, code, writeOffCode, storeName);

        List<LsSmsSetting> smsSetList = smsSetService.selectLsSmsSettingList(null);

        if (Objects.isNull(smsSetList)) {
            logger.error("sendWiteOffCodeSms fail due to smsSet is null...");
            return 0;
        }
        for (LsSmsSetting smsSet : smsSetList) {
            if (smsSet.getId() == 1 && smsSet.getStatus()) {
                RequestParam requestParam = convert(phone, smsSet);
                requestParam.setTemplateCode(smsSet.getWriteoffTemplateId());
                requestParam.setSmsParamString("{\"code\":\"" + code + "\",\"product\":\"\",\"storename\":\"" + storeName + "\",\"writeoffcode\":\"" + writeOffCode + "\"}");
                SmsUtil.newSendSms(requestParam);
            } else if (smsSet.getId() == 2 && smsSet.getStatus()) { // 腾讯云短信
                String[] params = new String[3];
                params[0] = storeName;
                params[1] = code;
                params[2] = writeOffCode;
                SendTencentSms.sendMessage(smsSet, params, phone);
            }
        }

        return 0;
    }

    @Override
    public int sendVirtualOrderWiteOffCodeSms(String phone, String code, String writeOffCode, String storeName) {
        logger.debug("sendVirtualOrderWiteOffCodeSms  and phone :{} \r\n code:{} \r\n writeOffCode:{} \r\n storeName:{}", phone, code, writeOffCode, storeName);

        List<LsSmsSetting> smsSetList = smsSetService.selectLsSmsSettingList(null);

        if (Objects.isNull(smsSetList)) {
            logger.error("sendVirtualOrderWiteOffCodeSms fail due to smsSet is null...");
            return 0;
        }
        for (LsSmsSetting smsSet : smsSetList) {
            //阿里云短信
            if (smsSet.getId() == 1 && smsSet.getStatus()) {
                RequestParam requestParam = convert(phone, smsSet);
                requestParam.setTemplateCode(smsSet.getVirtualOrderTemplateId());
                requestParam.setSmsParamString("{\"code\":\"" + code + "\",\"product\":\"\",\"storename\":\"" + storeName + "\",\"writeoffcode\":\"" + writeOffCode + "\"}");
                SmsUtil.newSendSmsCommon(requestParam);
            } else if (smsSet.getId() == 2 && smsSet.getStatus()) { // 腾讯云短信
                String[] params = new String[3];
                params[0] = storeName;
                params[1] = code;
                params[2] = writeOffCode;
                SendTencentSms.sendMessage(smsSet, params, phone);
            }
        }
        return 0;
    }

    /**
     * 发送社区团购审核结果通知短信
     *
     * @param phone      手机号码
     * @param submittime 通过时间
     * @return 成功返回0 失败返回1
     */
    @Override
    public int sendAuditTemplateOffCodeSms(String phone, String submittime) {
        logger.debug("sendAuditTemplateOffCodeSms  and phone :{} \r\n submittime:{}", phone, submittime);
        List<LsSmsSetting> smsSetList = smsSetService.selectLsSmsSettingList(null);
        if (Objects.isNull(smsSetList)) {
            logger.error("sendAuditTemplateOffCodeSms fail due to smsSet is null...");
            return 0;
        }
        for (LsSmsSetting smsSet : smsSetList) {
            //阿里云短信
            if (smsSet.getId() == 1 && smsSet.getStatus()) {
                RequestParam requestParam = convert(phone, smsSet);
                requestParam.setTemplateCode(smsSet.getAuditTemplateId());
                requestParam.setSmsParamString("{\"submittime\":\"" + submittime + "\",\"product\":\"\",\"status\":\"" + STATUS + "\"}");
                SmsUtil.newSendSmsCommon(requestParam);
            } else if (smsSet.getId() == 2 && smsSet.getStatus()) { // 腾讯云短信
                String[] params = new String[1];
                params[0] = submittime;
                SendTencentSms.sendMessage(smsSet, params, phone);
            }
        }
        return 0;
    }

    @Override
    public int sendCommunityBuySettlementSms(String phone, String communityName, BigDecimal price) {

        logger.debug("sendCommunityBuySettlementSms  and communityName :{} \r\n price:{} \r\n phone:{}", communityName, price, phone);

        List<LsSmsSetting> smsSetList = smsSetService.selectLsSmsSettingList(null);

        if (Objects.isNull(smsSetList)) {
            logger.error("sendCommunityBuySettlementSms fail due to smsSet is null...");
            return 0;
        }
        for (LsSmsSetting smsSet : smsSetList) {
            //阿里云短信
            if (smsSet.getId() == 1 && smsSet.getStatus()) {
                RequestParam requestParam = convert(phone, smsSet);
                requestParam.setTemplateCode(smsSet.getSettlementTemplateId());
                requestParam.setSmsParamString("{\"communityname\":\"" + communityName + "\",\"product\":\"\",\"price\":\"" + price + "\"}");
                SmsUtil.newSendSmsCommon(requestParam);
            } else if (smsSet.getId() == 2 && smsSet.getStatus()) { // 腾讯云短信
                String[] params = new String[2];
                params[0] = communityName;
                params[1] = String.valueOf(price);
                SendTencentSms.sendMessage(smsSet, params, phone);
            }
        }
        return 0;
    }

    @Override
    public int sendCommunityBuyWitudrawSms(String phone, String submittime, BigDecimal price) {


        logger.debug("sendCommunityBuyWitudrawSms  and phone :{} \r\n submittime:{} \r\n price:{}", phone, submittime, price);

        List<LsSmsSetting> smsSetList = smsSetService.selectLsSmsSettingList(null);

        if (Objects.isNull(smsSetList)) {
            logger.error("sendCommunityBuyWitudrawSms fail due to smsSet is null...");
            return 0;
        }
        for (LsSmsSetting smsSet : smsSetList) {
            //阿里云短信
            if (smsSet.getId() == 1 && smsSet.getStatus()) {
                RequestParam requestParam = convert(phone, smsSet);
                requestParam.setTemplateCode(smsSet.getWithdrawTemplateId());
                requestParam.setSmsParamString("{\"submittime\":\"" + submittime + "\",\"product\":\"\",\"price\":\"" + price + "\"}");
                SmsUtil.newSendSmsCommon(requestParam);
            } else if (smsSet.getId() == 2 && smsSet.getStatus()) { // 腾讯云短信
                String[] params = new String[2];
                params[0] = submittime;
                params[1] = price.toString();
                SendTencentSms.sendMessage(smsSet, params, phone);
            }
        }
        return 0;
    }

    private RequestParam convert(String phone, LsSmsSetting smsSet) {
        RequestParam requestParam = new RequestParam();
        requestParam.setPhoneNumbers(phone);
        requestParam.setSignName(smsSet.getSign());
        requestParam.setInterfaceUrl(smsSet.getUrl());
        requestParam.setAccessKeyId(smsSet.getKey());
        requestParam.setAccessKeySecret(smsSet.getSecret());
        return requestParam;
    }

}
