package com.ruoyi.member.service;


import com.ruoyi.common.core.domain.AjaxResult;

import java.math.BigDecimal;

/**
 * 充值服务
 */
public interface RechargeService {

    /**
     * 支付宝pc支付
     *
     * @param money      充值金额
     * @param customerId 用户id
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -2:生成充值记录出错 -5:支付宝生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用  1 成功
     */
    AjaxResult aliPagePay(String transCode, BigDecimal money, int payType, long customerId, int type);

    /**
     * 微信扫码支付
     *
     * @param money      充值金额
     * @param customerId 用户id
     * @param ip         请求真实ip
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -2:生成充值记录出错 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功
     */
    AjaxResult wechatQRPay(String transCode, BigDecimal money, int payType, long customerId, String ip, int type);



}
