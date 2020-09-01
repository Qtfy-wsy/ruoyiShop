package com.ruoyi.member.service.impl;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.AliPayUtils;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.WechatUtils;
import com.ruoyi.common.utils.bean.*;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.domain.UmsPreDepositRecord;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.member.service.RechargeService;
import com.ruoyi.setting.bean.AliPaySet;
import com.ruoyi.setting.bean.BaseInfoSet;
import com.ruoyi.setting.bean.WechatPaySet;
import com.ruoyi.setting.service.BaseInfoSetService;
import com.ruoyi.setting.service.ILsPaySettingService;
import com.ruoyi.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Objects;

/**
 * 充值服务
 */
@Service
public class RechargeServiceImpl implements RechargeService {


    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(RechargeServiceImpl.class);

    /**
     * 注入支付设置服务
     */
    @Autowired
    private ILsPaySettingService paySetService;

    /**
     * 注入网站基础信息服务
     */
    @Autowired
    private BaseInfoSetService baseInfoSetService;

    /**
     * 注入预存款服务
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;

    /**
     * 注入序号生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /**
     * 用户服务接口
     */
    @Autowired
    private IUmsMemberService customerService;


    @Override
    public AjaxResult aliPagePay(String transCode, BigDecimal money, int payType, long customerId, int type) {
        logger.debug("aliPagePay and transCode:{}\r\n money:{}\r\n customerId:{}", transCode, money, customerId);
        //支付宝支付设置(数据库)
        AliPaySet aliPaySet = paySetService.queryPaySet().getAliPaySet();
        // 查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);
        if (Objects.isNull(customer)) {
            logger.error("aliPagePay fail due to customer is not ");
            return AjaxResult.error("aliPagePay fail due to customer is not");
        }
        if (!aliPaySet.checkIsUse()) {
            logger.error("aliPagePay fail due to not used....");
            return AjaxResult.error("aliPagePay fail due to not used....");
        }

        //获得交易号
        transCode = getTransCode(transCode, money, customerId, CommonConstant.ALI_PAY);
        if (StringUtils.isEmpty(transCode)) {
            logger.error("aliPagePay fail due to getTransCode fail ");
            return AjaxResult.error("aliPagePay fail due to getTransCode fail..");
        }
        //查询网站基本信息
        BaseInfoSet baseInfoSet = baseInfoSetService.queryBaseInfoSet();
        if (ObjectUtils.isEmpty(baseInfoSet.getSiteUrlWithBias())) {
            logger.error("aliPagePay fail due to no siteurl....");
            return AjaxResult.error("aliPagePay fail due to no siteurl....");
        }
        String siteUrl = baseInfoSet.getSiteUrlWithBias();
        if (StringUtils.isEmpty(aliPaySet.getAppId())) {
            logger.error("aliPagePay fail due to no appId....");
            return AjaxResult.error("aliPagePay fail due to no appId.....");
        }
        //支付宝支付设置
        AliPaySetting aliPaySetting = new AliPaySetting();
        BeanUtils.copyProperties(aliPaySet, aliPaySetting);
        //前台同步回调地址
        aliPaySetting.setBeforeCallbackUrl(siteUrl + "#/rechargesuccess?transCode=" + transCode + "&money=" + money);
        //异步回调地址
        aliPaySetting.setBackCallbackUrl(siteUrl + "api/pc/alipaynotify");

        if (!aliPaySetting.checkPayParams()) {
            logger.error("aliPagePay fail due to no checkPayParams fail....");
            return AjaxResult.error("aliPagePay fail due to no checkPayParams fail....");
        }

        //待付订单信息
        OrderInfoForPay orderInfoForPay = buildOrderInfoForPay(transCode, money, type);

        //返回的html
        String form = AliPayUtils.pagePay(aliPaySetting, orderInfoForPay);
        //生成表单错误
        if (StringUtils.isEmpty(form)) {
            logger.error("aliPagePay fail due to form error....");
            return AjaxResult.error("aliPagePay fail due to form error....");
        }
        logger.debug("aliPagePay pay success......");
        return AjaxResult.success(form);
    }

    /**
     *
     * @param transCode
     * @param money      充值金额
     * @param payType  1 公众号 2 h5 3 小程序 4 app 5 pc 支付
     * @param customerId 用户id
     * @param ip         请求真实ip
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return
     */
    @Override
    public AjaxResult wechatQRPay(String transCode, BigDecimal money, int payType, long customerId, String ip, int type) {
        logger.debug("wechatQRPay and transCode:{}\r\n money:{} \r\n customerId:{}", transCode, money, customerId);
        //微信支付设置(数据库)
        WechatPaySet wechatPaySet = null;
        // 查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);
        //微信支付设置
        WechatSetting wechatSetting = null;
        if (Objects.isNull(customer)) {
            logger.error("wechatQRPay fail due to customer is not ");
            return AjaxResult.error("wechatQRPay fail due to customer is not");
        }
        if (payType==4){
            wechatPaySet = paySetService.queryPaySet().getWechatAppPaySet();
            wechatSetting = WechatSetting.build(ip, WechatUtils.APP_PAY, "wechatnotify");
        }else if (payType==3){
            wechatPaySet = paySetService.queryPaySet().getWechatAppletPaySet();
            if (customer!=null && ObjectUtils.isEmpty(customer.getAppletOpenId())){
                logger.error("wechatAppletPay fail due to wechat error not auth...openid is not exist.");
                return AjaxResult.error("wechatAppletPay fail due to wechat error not auth..openid is not exist..");
            }
            wechatSetting = WechatSetting.build(ip, WechatUtils.APPLET_PAY, "wechatnotify");
        }else if (payType==1) {
            wechatPaySet = paySetService.queryPaySet().getWechatPaySet();
            wechatSetting = WechatSetting.build(ip, WechatUtils.OFFICIAL_ACCOUNT_PAY, "wechatnotify");
            if (customer!=null && ObjectUtils.isEmpty(customer.getH5OpenId())){
                logger.error("wechatPay fail due to wechat error not auth....");
                return AjaxResult.error("wechatPay fail due to wechat error not auth openid is not exist...openid is not exist.");
            }
        } else if (payType==2) {
            wechatPaySet = paySetService.queryPaySet().getWechatPaySet();
            wechatSetting = WechatSetting.build(ip, WechatUtils.H5_PAY, "wechatnotify");
        }else if (payType==5) {
            wechatPaySet = paySetService.queryPaySet().getWechatPaySet();
            wechatSetting = WechatSetting.build(ip, WechatUtils.QR_PAY, "wechatnotify");
        }
        if (!wechatPaySet.checkIsUse()) {
            logger.error("wechatQRPay fail due to not used....");
            return AjaxResult.error("wechatQRPay fail due to not used....");
        }

        //获得交易号
        transCode = getTransCode(transCode, money, customerId, CommonConstant.WECHAT_PAY);
        if (StringUtils.isEmpty(transCode)) {
            logger.error("wechatQRPay fail due to getTransCode fail ");
            return AjaxResult.error("wechatQRPay fail due to not used....");
        }
        //查询网站基本信息
        BaseInfoSet baseInfoSet = baseInfoSetService.queryBaseInfoSet();
        if (ObjectUtils.isEmpty(baseInfoSet.getSiteUrlWithBias())) {
            logger.error("wechatQRPay fail due to no siteurl....");
            return AjaxResult.error("wechatQRPay fail due to not used....");
        }
        String siteUrl = baseInfoSet.getSiteUrlWithBias();
        if (ObjectUtils.isEmpty(wechatPaySet)) {
            logger.error("wechatQRPay fail due to no wechatPaySet is empty....");
            return AjaxResult.error("wechatQRPay fail due to not used....");
        }

        BeanUtils.copyProperties(wechatPaySet, wechatSetting);

        //异步回调地址
        wechatSetting.setPayCallback(siteUrl + wechatSetting.getPayCallback());
        if (!wechatSetting.checkPayParams()) {
            logger.error("wechatQRPay fail due to no checkPayParams fail....");
            return AjaxResult.error("wechatQRPay fail due to not used....");
        }
        //待付订单信息
        OrderInfoForPay orderInfoForPay = buildOrderInfoForPay(transCode, money, type);
        //获取预支付信息
        PrepayResult prepayResult = WechatUtils.getPrepay(wechatSetting, orderInfoForPay);


        if (payType==4){
            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatAppPay fail due to wechat error....");
                return AjaxResult.error("wechatAppPay fail due to wechat error....");
            }
            logger.debug("wechatAppPay getPayUrl success......");
            return AjaxResult.success(prepayResult);
        }else if (payType==3){

            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatAppletPay fail due to wechat error....");
                return AjaxResult.error("wechatAppletPay fail due to wechat error....");
            }
            return AjaxResult.success(prepayResult);
        }else if (payType==1) {
            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatOfficialAccountPay fail due to wechat error....");
                return AjaxResult.error("wechatOfficialAccountPay fail due to wechat error....");
            }
            logger.debug("wechatOfficialAccountPay getPayUrl success......");
            return AjaxResult.success(prepayResult);
        } else if (payType==2) {

            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatH5Pay fail due to wechat error....");
                return AjaxResult.error("wechatH5Pay fail due to wechat error....");
            }

            logger.debug("wechatH5Pay getPayUrl success......");
            WechatPayResponse wechatPayResponse = new WechatPayResponse();


            // 同步回调地址
            String callBackUrl = "paysuccess?type=" + type;

            BaseInfoSet infoSet = getSite();
            wechatPayResponse.setMwebUrl(prepayResult.getMweb_url() + "&redirect_url=" + URLEncoder.encode(infoSet.getH5CallBackDomain() + callBackUrl));

            //订单code
            wechatPayResponse.setOrderCode(transCode);
            //订单金额
            wechatPayResponse.setOrderMoney(money);
            return AjaxResult.success(wechatPayResponse);
        }else if (payType==5) {
            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatQRPay fail due to wechat error....");
                return AjaxResult.error("wechatQRPay fail due to wechat error....");
            }
            logger.debug("wechatQRPay gePayUrl success......");
            WechatPayResponseForRecharge wechatQRPayResponseForRecharge = new WechatPayResponseForRecharge();
            //扫码支付url
            wechatQRPayResponseForRecharge.setCodeUrl(prepayResult.getCode_url());
            //订单code
            wechatQRPayResponseForRecharge.setTransCode(transCode);
            //订单金额
            wechatQRPayResponseForRecharge.setMoney(money);

            return AjaxResult.success(wechatQRPayResponseForRecharge);
        }

        return AjaxResult.success();
    }



    /**
     * 获取网站地址
     */
    private BaseInfoSet getSite() {
        return baseInfoSetService.queryBaseInfoSet();
    }
    /**
     * 获取交易号
     *
     * @param transCode  交易号
     * @param money      充值金额
     * @param customerId 用户ID
     * @param channel    充值渠道
     * @return
     */
    private String getTransCode(String transCode, BigDecimal money, long customerId, String channel) {
        boolean flag = true;
        if (!StringUtils.isEmpty(transCode)) {
            UmsPreDepositRecord predepositRecord = predepositRecordService.queryPredepositRecordByTransCode(transCode, customerId);
            if (!ObjectUtils.isEmpty(predepositRecord) && !predepositRecord.isPaid() && predepositRecord.getMoney().compareTo(money) == 0) {
                flag = false;
            }
        }
        if (flag) {
            //创建充值记录
            UmsPreDepositRecord predepositRecord = UmsPreDepositRecord.buildRecharge(money, customerId, channel, snowflakeIdWorker.nextId());
            int addNum = predepositRecordService.addPredepositRecord(predepositRecord);
            if (addNum < 1) {
                return null;
            }
            transCode = predepositRecord.getTranscode();
        }
        return transCode;
    }

    /**
     * 构建预支付订单信息
     *
     * @param transCode 交易号
     * @param money     订单金额
     * @param type      类型 1:订单支付 2:预存款充值
     */
    private OrderInfoForPay buildOrderInfoForPay(String transCode, BigDecimal money, int type) {
        OrderInfoForPay orderInfoForPay = new OrderInfoForPay();
        //商品名
        orderInfoForPay.setGoodsName("预存款充值");
        //订单号
        orderInfoForPay.setOrderCode(transCode);
        //订单金额
        orderInfoForPay.setPrice(money);
        //支付类型
        orderInfoForPay.setType(type);
        return orderInfoForPay;
    }
}
