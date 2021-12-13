package com.ruoyi.member.service;

import java.math.BigDecimal;

/**
 * Created by 伊甸园商城 on 17/11/18.
 * 短信验证码服务接口
 */
public interface SmsService {

    /**
     * 发送短信
     *
     * @param phone   手机号码
     * @param content 发送内容
     * @return 成功返回0 失败返回1
     */
    int sendSms(String phone, String content);

    /**
     * 发送核销码短信
     *
     * @param phone        手机号码
     * @param code         订单号
     * @param writeOffCode 核销码
     * @param storeName    店铺名称
     * @return 成功返回0 失败返回1
     */
    int sendWiteOffCodeSms(String phone, String code, String writeOffCode, String storeName);

    /**
     * 发送虚拟商品订单核销码短信
     *
     * @param phone        手机号码
     * @param code         订单号
     * @param writeOffCode 核销码
     * @param storeName    店铺名称
     * @return 成功返回0 失败返回1
     */
    int sendVirtualOrderWiteOffCodeSms(String phone, String code, String writeOffCode, String storeName);


    /**
     * 发送社区团购审核结果通知短信
     *
     * @param phone      手机号码
     * @param submittime 通过时间
     * @return 成功返回0 失败返回1
     */
    int sendAuditTemplateOffCodeSms(String phone, String submittime);

    /**
     * 发送社区团购结算短信
     *
     * @param communityName 社区团购名称
     * @param price         佣金
     * @param phone         手机号码
     * @return 成功发挥0 失败返回1
     */
    int sendCommunityBuySettlementSms(String phone, String communityName, BigDecimal price);

    /**
     * 社区团购提现申请打款通知
     *
     * @param phone      手机号码
     * @param submittime 打款时间
     * @param price      金额
     * @return 成功返回0 失败返回1
     */
    int sendCommunityBuyWitudrawSms(String phone, String submittime, BigDecimal price);
}
