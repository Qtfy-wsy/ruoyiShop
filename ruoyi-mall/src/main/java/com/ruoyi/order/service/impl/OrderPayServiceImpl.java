package com.ruoyi.order.service.impl;


import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.AliPayUtils;
import com.ruoyi.common.utils.WechatUtils;
import com.ruoyi.common.utils.bean.*;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.domain.UmsPreDepositRecord;
import com.ruoyi.member.domain.WeChatCustomerLink;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import com.ruoyi.member.service.WeChatCustomerLinkService;
import com.ruoyi.order.OrderPayService;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.order.service.OrderServiceApi;
import com.ruoyi.order.service.PredepositRecordServiceApi;
import com.ruoyi.order.service.StoreOrderServiceApi;
import com.ruoyi.order.vo.ConfirmOrderParams;
import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.setting.bean.*;
import com.ruoyi.setting.service.BaseInfoSetService;
import com.ruoyi.setting.service.ILsPaySettingService;
import com.ruoyi.store.domain.TStoreOrder;
import com.ruoyi.store.service.ITStoreOrderService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by 魔金商城 on 17/11/10.
 * 订单支付接口实现
 */
@Service
public class OrderPayServiceImpl implements OrderPayService {

    // String h5CallBackDomain="https://m-uat.wsfmall.com/";
    //  String pcCallBackDomain="https://uat.wsfmall.com/";
    String appCallBackDomain = "";
    /**
     * 调试日志
     */
    private Logger logger = LoggerFactory.getLogger(OrderPayServiceImpl.class);
    /**
     * 注入订单服务接口
     */
    @Autowired
    private IOmsOrderService orderService;
    /**
     * 注入订单混合服务接口
     */
    @Autowired
    private OrderServiceApi orderServiceApi;
    /**
     * 用户服务接口
     */
    @Autowired
    private IUmsMemberService customerService;
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
     * 预存款纪录服务接口
     */
    @Autowired
    private IUmsPreDepositRecordService predepositRecordService;
    /**
     * 注入预存款记录服务api接口
     */
    @Autowired
    private PredepositRecordServiceApi predepositRecordServiceApi;
    /**
     * 注入微信用户关联服务
     */
    @Autowired
    private WeChatCustomerLinkService weChatCustomerLinkService;
    /**
     * 注入门店订单服务接口
     */
    @Autowired
    private ITStoreOrderService storeOrderService;
    /**
     * 注入门店订单服务接口
     */
    @Autowired
    private StoreOrderServiceApi storeOrderServiceApi;
    /**
     * 密码工具类
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public int predepositPay(String orderCode, String password, long customerId, int type) {
        logger.debug("predepositPay and orderCode:{} \r\n password:{} \r\n customerId:{} \r\n type:{}", orderCode, password, customerId, type);
        //数据库获取银联支付设置
        PrePaySet prePaySet = paySetService.queryPaySet().getPrePaySet();
        if (!prePaySet.checkIsUse()) {
            logger.error("predepositPay fail due to not used....");
            return -9;
        }

        // 查询用户信息
        UmsMember customer = customerService.queryCustomerInfoById(customerId);

        if (Objects.isNull(customer)) {
            logger.error("predepositPay fail due to member is not ");
            return -1;
        }

        if (Objects.isNull(customer.getPaypassword())) {
            logger.error("predepositPay fail due to member payPassword is null ");
            return -6;
        }

        // 校验用户输入的预存款密码是否正确
        if (!passwordEncoder.matches(password, customer.getPaypassword())) {
            logger.error("predepositPay fail due to password is error...");
              return -2;
        }

        // 订单支付
        if (type == CommonConstant.ORDER_PAY) {

            // 根据订单编号和用户id查询待付款的订单信息
            List<OmsOrder> orders = queryToPayOrders(orderCode, customerId);

            // 没有待支付的订单 则直接返回
            if (CollectionUtils.isEmpty(orders)) {
                logger.error("predepositPay fail due to no order....");
                return -3;
            }

            // 待支付订单的总金额
            BigDecimal orderMoney = getOrdersAllMoeny(orders);

            // 查询用户的总预存款
            BigDecimal customerMoney = predepositRecordService.queryCutomerAllPredeposit(customerId);

            // 判断用户的预存款是否充足
            if (customerMoney.compareTo(orderMoney) < 0) {
                logger.error("predepositPay fail due to member money is not enough ...");
                return -4;
            }

            // 支付成功扣除用户预存款
            predepositRecordService.addPredepositRecord(UmsPreDepositRecord.buildPay(orderMoney, customerId));

            // 修改订单状态
            int result = orderServiceApi.confirmOrderPayed(ConfirmOrderParams.buildCustomerSource(customerId, 1, orders.stream().map(OmsOrder::getOrderCode).collect(Collectors.toList()), "", ""));

            // 修改订单失败 状态回滚
            if (result == 0) {
                throw new ServiceException("R-000008");
            }

            logger.debug("haha ..... pay success......");

            return 1;
        } else if (type == CommonConstant.STORE_ORDER_PAY) {
            // 门店订单支付

            // 根据门店订单编号和用户id查询待付款的订单信息
            List<TStoreOrder> storeOrders = queryToPayStoreOrders(orderCode, customerId);

            // 没有待支付的订单 则直接返回
            if (CollectionUtils.isEmpty(storeOrders)) {
                logger.error("predepositPay fail due to no storeOrders....");
                return -3;
            }

            // 待支付订单的总金额
            BigDecimal orderMoney = storeOrders.stream().map(TStoreOrder::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

            // 查询用户的总预存款
            BigDecimal customerMoney = predepositRecordService.queryCutomerAllPredeposit(customerId);

            // 判断用户的预存款是否充足
            if (customerMoney.compareTo(orderMoney) < 0) {
                logger.error("predepositPay fail due to member money is not enough ...");
                return -4;
            }

            // 支付成功扣除用户预存款
            predepositRecordService.addPredepositRecord(UmsPreDepositRecord.buildPay(orderMoney, customerId));

            // 修改订单的状态为已支付状态
            int result = storeOrderServiceApi.confirmOrderPayed(customerId, storeOrders.stream().map(TStoreOrder::getOrderCode).collect(Collectors.toList()), "1", "", "");

            // 修改订单失败 状态回滚
            if (result == 0) {
                throw new ServiceException("R-000008");
            }

            logger.debug("haha ..... pay success......");

            return 1;
        } else {
            logger.error("predepositPay fail due to type unknow,...");
            return -3;
        }
    }

    @Override
    public CommonResponse<String> aliAppPay(String orderCode, long customerId, int type) {
        logger.debug("aliAppPay and orderCode:{} \r\n customerId:{} \r\n type:{}", orderCode, customerId, type);
        // 构造支付信息
        return aliCommonPay(orderCode, customerId, type, AliPaySetting.build("", "app/pay/alipaynotify"), (setting, orderinfo) -> AliPayUtils.appPay(setting, orderinfo));
    }

    @Override
    public CommonResponse<String> aliPagePay(String orderCode, long customerId, int type) {
        logger.debug("aliPagePay and orderCode:{} \r\n customerId:{} \r\n type:{}", orderCode, customerId, type);
        BaseInfoSet infoSet = getSite();
        // 构造支付信息
        return aliCommonPay(orderCode, customerId, type, AliPaySetting.build(infoSet.getPcCallBackDomain() + "paysuccess??type=" + type + "&orderCode=" + orderCode, "api/pc/alipaynotify"), (setting, orderinfo) -> AliPayUtils.pagePay(setting, orderinfo));
    }

    @Override
    public CommonResponse<String> aliWapPay(String orderCode, long customerId, int type, String orderType, String orderId) {
        logger.debug("aliWapPay and orderCode:{} \r\n customerId:{} \r\n type:{} \r\n orderType:{} \r\n orderId", orderCode, customerId, type, orderType, orderId);

        // 同步回调地址
        String callBackUrl = "paysuccess?type=" + type;

        // 如果是社区团购订单 则同步回调地址为订单详情页面
        if ("8".equals(orderType)) {
            callBackUrl = "tocommunityorderdetail.htm?orderId=" + orderId;
        }
        BaseInfoSet infoSet = getSite();
        // 构造支付信息
        return aliCommonPay(orderCode, customerId, type, AliPaySetting.build(infoSet.getH5CallBackDomain() + callBackUrl, "api/mobile/alipaynotify"), (setting, orderinfo) -> AliPayUtils.wapPay(setting, orderinfo));
    }


    @Override
    public CommonResponse<WechatPayResponse> wechatQRPay(String orderCode, long customerId, String ip, int type) {
        logger.debug("wechatQRPay and orderCode:{} \r\n customerId:{}  \r\n ip:{} \r\n:type:{}", orderCode, customerId, ip, type);

        // 返回结果
        return weChatCommonPay(orderCode, customerId, type, WechatSetting.build(ip, WechatUtils.QR_PAY, "wechatnotify"), (setting, orderinfo) -> {
            PrepayResult prepayResult = WechatUtils.getPrepay(setting, orderinfo);
            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatQRPay fail due to wechat error....");
                return CommonResponse.build(null, -5);
            }

            logger.debug("wechatQRPay getPayUrl success......");
            WechatPayResponse wechatQRPayResponse = new WechatPayResponse();
            //扫码支付url
            wechatQRPayResponse.setCodeUrl(prepayResult.getCode_url());
            //订单code
            wechatQRPayResponse.setOrderCode(orderCode);
            //订单金额
            wechatQRPayResponse.setOrderMoney(orderinfo.getPrice());

            return CommonResponse.build(wechatQRPayResponse, 1);
        });
    }

    @Override
    public CommonResponse<WechatPayResponse> wechatH5Pay(String orderCode, long customerId, String ip, int type, String orderType, String orderId) {
        logger.debug("wechatH5Pay and orderCode:{} \r\n customerId:{}  \r\n ip:{} \r\n:type:{} \r\n orderType:{} \r\n orderId:{}", orderCode, customerId, ip, type, orderType, orderId);

        return weChatCommonPay(orderCode, customerId, type, WechatSetting.build(ip, WechatUtils.H5_PAY, "wechatnotify"), (setting, orderinfo) -> {
            //获取预支付信息
            PrepayResult prepayResult = WechatUtils.getPrepay(setting, orderinfo);
            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatH5Pay fail due to wechat error....");
                return CommonResponse.build(null, -5);
            }

            logger.debug("wechatH5Pay getPayUrl success......");
            WechatPayResponse wechatPayResponse = new WechatPayResponse();


            // 同步回调地址
            String callBackUrl = "paysuccess?type=" + type;


            // 如果是社区团购订单 则同步回调地址为订单详情页面
            if ("8".equals(orderType)) {
                callBackUrl = "tocommunityorderdetail.htm?orderId=" + orderId;
            }
            BaseInfoSet infoSet = getSite();
            wechatPayResponse.setMwebUrl(prepayResult.getMweb_url() + "&redirect_url=" + URLEncoder.encode(infoSet.getH5CallBackDomain() + callBackUrl));

            //订单code
            wechatPayResponse.setOrderCode(orderCode);
            //订单金额
            wechatPayResponse.setOrderMoney(orderinfo.getPrice());

            return CommonResponse.build(wechatPayResponse, 1);
        });
    }


    @Override
    public CommonResponse<PrepayResult> wechatAppPay(String orderCode, long customerId, String ip, int type) {
        logger.debug("wechatAppPay and orderCode:{} \r\n customerId:{}  \r\n ip:{} \r\n:type:{}", orderCode, customerId, ip, type);

        return weChatCommonPay(orderCode, customerId, type, WechatSetting.build(ip, WechatUtils.APP_PAY, "wechatnotify"), (setting, orderinfo) -> {
            PrepayResult prepayResult = WechatUtils.getPrepay(setting, orderinfo);
            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatAppPay fail due to wechat error....");
                return CommonResponse.build(null, -5);
            }

            logger.debug("wechatAppPay getPayUrl success......");
            return CommonResponse.build(prepayResult, 1);
        });
    }

    @Override
    public CommonResponse<PrepayResult> wechatOfficialAccountPay(String orderCode, long customerId, String ip, int type) {
        logger.debug("wechatOfficialAccountPay and orderCode:{} \r\n customerId:{} \r\n ip:{} \r\n:type:{}", orderCode, customerId, ip, type);

        WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByCustomerId(customerId);
        if (ObjectUtils.isEmpty(weChatCustomerLink) || StringUtils.isEmpty(weChatCustomerLink.getOpenId())) {
            logger.error("wechatOfficialAccountPay fail due to no weChatCustomerLink or openId is null....");
            return CommonResponse.build(null, -10);
        }

        return weChatCommonPay(orderCode, customerId, type, WechatSetting.build(ip, WechatUtils.OFFICIAL_ACCOUNT_PAY, "wechatnotify").addOpenId(weChatCustomerLink.getOpenId()), (setting, orderInfo) -> {
            //获取预支付信息
            PrepayResult prepayResult = WechatUtils.getPrepay(setting, orderInfo);
            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatOfficialAccountPay fail due to wechat error....");
                return CommonResponse.build(null, -5);
            }

            logger.debug("wechatOfficialAccountPay getPayUrl success......");
            return CommonResponse.build(prepayResult, 1);
        });
    }

    @Override
    public CommonResponse<PrepayResult> wechatAppletPay(String orderCode, long customerId, String ip, int type) {
        logger.debug("wechatAppletPay and orderCode:{} \r\n customerId:{} \r\n ip:{} \r\n:type:{}", orderCode, customerId, ip, type);

     /*   WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByCustomerId(customerId);
        if (ObjectUtils.isEmpty(weChatCustomerLink) || StringUtils.isEmpty(weChatCustomerLink.getAppletOpenId())) {
            logger.error("wechatAppletPay fail due to no weChatCustomerLink or appletOpenId is null....");
            return CommonResponse.build(null, -10);
        }*/
        UmsMember member = customerService.queryCustomerInfoById(customerId);
        if (member!=null && ObjectUtils.isEmpty(member.getAppletOpenId())){
            logger.error("wechatAppletPay fail due to wechat error not auth....");
            return CommonResponse.build(null, -10);
        }
        return weChatCommonPay(orderCode, customerId, type, WechatSetting.build(ip, WechatUtils.APPLET_PAY, "wechatnotify").addOpenId(member.getAppletOpenId()), (setting, orderInfo) -> {
            //获取预支付信息
            PrepayResult prepayResult = WechatUtils.getPrepay(setting, orderInfo);
            //微信生成预支付信息错误
            if (ObjectUtils.isEmpty(prepayResult)) {
                logger.error("wechatAppletPay fail due to wechat error....");
                return CommonResponse.build(null, -5);
            }
            return CommonResponse.build(prepayResult, 1);
        });
    }

    @Override
    public CommonResponse<String> unionPagePay(String orderCode, long customerId, int type) {
        logger.debug("unionPagePay and orderCode:{} \r\n customerId:{} \r\n type:{}", orderCode, customerId, type);
        //数据库获取银联支付设置
        UnionPaySet unionPaySet = paySetService.queryPaySet().getUnionPaySet();
        if (!unionPaySet.checkIsUse()) {
            logger.error("unionPagePay fail due to not used....");
            return CommonResponse.build("", -9);
        }

        // 查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);

        if (Objects.isNull(customer)) {
            logger.error("unionPagePay fail due to member is not ");
            return CommonResponse.build("", -1);
        }

        // 获得支付实体
        OrderInfoForPay orderInfoForPay = getOrderInfoForPay(orderCode, customerId, type);

        // 没有支付实体 直接返回
        if (Objects.isNull(orderInfoForPay)) {
            logger.error("unionPagePay fail due to no order....");
            return CommonResponse.build("", -3);
        }

        String siteUrl = getSiteUrl();
        if (StringUtils.isEmpty(siteUrl)) {
            logger.error("unionPagePay fail due to no siteurl....");
            return CommonResponse.build("", -7);
        }

        if (StringUtils.isEmpty(unionPaySet.getMerchantNum())) {
            logger.error("unionPagePay fail due to MerchantNum is empty....");
            return CommonResponse.build("", -8);
        }
        //银联支付设置
        UnionPaySetting unionPaySetting = new UnionPaySetting();
        //商户号
        unionPaySetting.setMerchantNum(unionPaySet.getMerchantNum());
        //前台同步回调地址
        unionPaySetting.setBeforeCallbackUrl(siteUrl + "topaysuccess.htm");
        //异步回调地址
        unionPaySetting.setBackCallbackUrl(siteUrl + "unionpaynotify.htm");
        //返回的html
        String form = null;
        //UnionPayUtils.pagePay(unionPaySetting, orderInfoForPay);

        //生成表单错误
        if (StringUtils.isEmpty(form)) {
            logger.error("unionPagePay fail due to form error....");
            return CommonResponse.build("", -5);
        }
        logger.debug("unionPagePay pay success......");

        return CommonResponse.build(form, 1);
    }

    @Override
    public int aliPayNotify(Map requestParams) {
        logger.debug("aliPayNotify and requestParams:{} ", requestParams);
        //支付宝支付设置(数据库)
        AliPaySet aliPaySet = paySetService.queryPaySet().getAliPaySet();
        //支付宝支付设置
        AliPaySetting aliPaySetting = new AliPaySetting();
        BeanUtils.copyProperties(aliPaySet, aliPaySetting);
        if (!aliPaySetting.checkPayParams()) {
            logger.error("aliPayNotify fail due to no checkPayParams fail....");
            return -2;
        }
        OrderInfoAfterPay orderInfoAfterPay = AliPayUtils.afterPayInfo(aliPaySetting, requestParams);
        if (!orderInfoAfterPay.isSuccess()) {
            logger.error("aliPayNotify fail due to notify fail....");
            return -3;
        }
        return paySuccess(orderInfoAfterPay.getOrderCode(), CommonConstant.QUERY_WITH_NO_CUSTOMER, orderInfoAfterPay.getType(), CommonConstant.ALI_PAY, orderInfoAfterPay.getTransCode());
    }

    @Override
    public int weChatNotify(InputStream inputStream) {
        logger.debug("weChatNotify...... ");
        return weChatCommonNotify(inputStream, paySetService.queryPaySet().getWechatPaySet());
    }

    @Override
    public int weChatAppNotify(InputStream inputStream) {
        logger.debug("weChatAppNotify...... ");
        return weChatCommonNotify(inputStream, paySetService.queryPaySet().getWechatAppPaySet());
    }

    @Override
    public int weChatAppletNotify(InputStream inputStream) {
        logger.debug("weChatAppletNotify...... ");
        return weChatCommonNotify(inputStream, paySetService.queryPaySet().getWechatAppletPaySet());
    }

    /**
     * 微信支付回调
     *
     * @param inputStream  微信输入流
     * @param wechatPaySet 微信设置
     * @return 返回  0失败，大于0成功
     */
    private int weChatCommonNotify(InputStream inputStream, WechatPaySet wechatPaySet) {
        logger.debug("weChatCommonNotifya and wechatPaySet:{}", wechatPaySet);
        WechatSetting wechatSetting = new WechatSetting();
        BeanUtils.copyProperties(wechatPaySet, wechatSetting);
        if (StringUtils.isEmpty(wechatPaySet.getAppId()) || StringUtils.isEmpty(wechatPaySet.getMerchantNum())) {
            logger.error("weChatNotify fail due to no checkPayParams fail....");
            return -2;
        }
        OrderInfoAfterPay orderInfoAfterPay = WechatUtils.afterPayInfo(inputStream, wechatSetting);
        if (!orderInfoAfterPay.isSuccess()) {
            logger.error("weChatNotify fail due to notify fail....");
            return -3;
        }
        return paySuccess(orderInfoAfterPay.getOrderCode(), CommonConstant.QUERY_WITH_NO_CUSTOMER, orderInfoAfterPay.getType(), CommonConstant.WECHAT_PAY, orderInfoAfterPay.getTransCode());
    }

    @Override
    public int unionPayNotify(Map requestParams) {
        logger.debug("unionPayNotify and requestParams:{} ", requestParams);
        OrderInfoAfterPay orderInfoAfterPay = null;
        //UnionPayUtils.afterPayInfo(requestParams);
        if (!orderInfoAfterPay.isSuccess()) {
            logger.error("unionPayNotify fail due to notify fail....");
            return -3;
        }
        return paySuccess(orderInfoAfterPay.getOrderCode(), CommonConstant.QUERY_WITH_NO_CUSTOMER, orderInfoAfterPay.getType(), CommonConstant.UNION_PAY, orderInfoAfterPay.getTransCode());

    }

    /**
     * 支付成功后的操作
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param type       1:订单支付    2 预存款充值
     * @param channel    支付方式，预存款充值使用
     * @param transCode  交易流水号
     * @return 0失败，大于0成功
     */
    private int paySuccess(String orderCode, long customerId, int type, String channel, String transCode) {
        logger.debug("paySuccess and orderCode:{} \r\n customerId:{} \r\n type:{} \r\n transCode:{}", orderCode, customerId, type, transCode);
        //订单支付
        if (CommonConstant.ORDER_PAY == type) {
            // 根据订单编号和用户id查询待付款的订单信息
            List<OmsOrder> orders = orderService.queryOrderByOrderCode(orderCode, customerId);
            // 没有待支付的订单 则直接返回
            if (CollectionUtils.isEmpty(orders)) {
                logger.error("paySuccess fail due to no order....");
                return -1;
            }
            // 修改订单状态
            return orderServiceApi.confirmOrderPayed(ConfirmOrderParams.buildCustomerSource(customerId, 0, orders.stream().map(OmsOrder::getOrderCode).collect(Collectors.toList()), transCode, channel));
        } else if (CommonConstant.RECHARGE_PAY == type) {
            //预存款充值
            return predepositRecordServiceApi.updateStatusSuccessByTransCode(orderCode, CommonConstant.QUERY_WITH_NO_CUSTOMER, channel, transCode);
        } else {
            // 根据订单编号和用户id查询待付款的门店订单信息
            List<TStoreOrder> storeOrders = storeOrderService.queryOrderByOrderCode(orderCode, customerId);

            if (CollectionUtils.isEmpty(storeOrders)) {
                logger.error("paySuccess fail due to no order....");
                return -1;
            }

            return storeOrderServiceApi.confirmOrderPayed(customerId, storeOrders.stream().map(TStoreOrder::getOrderCode).collect(Collectors.toList()), "0", transCode, channel);
        }

    }


    /**
     * 获得订单的总金额
     *
     * @param orders 待支付的订单
     * @return 返回等待支付订单的总金额
     */
    private BigDecimal getOrdersAllMoeny(List<OmsOrder> orders) {
        // 是定金预售订单 则返回定金的价格
        if (!CollectionUtils.isEmpty(orders.stream().filter(OmsOrder::isDepositPresaleOrder).collect(Collectors.toList()))) {
            return orders.stream().map(OmsOrder::getPresalePrice).reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return orders.stream().map(OmsOrder::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 查询待付款的订单
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @return 返回待付款的订单
     */
    private List<OmsOrder> queryToPayOrders(String orderCode, long customerId) {
        logger.debug("queryToPayOrders and orderCode:{} \r\n customerId:{}", orderCode, customerId);
        return orderService.queryToPayOrder(orderCode, customerId);
    }

    /**
     * 查询待付款的门店订单
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @return 返回待付款的门店订单
     */
    private List<TStoreOrder> queryToPayStoreOrders(String orderCode, long customerId) {
        logger.debug("queryToPayStoreOrders and orderCode:{} \r\n customerId:{}", orderCode, customerId);
        return storeOrderService.queryToPayStoreOrder(orderCode, customerId);
    }

    /**
     * 构建预支付订单信息
     *
     * @param goodsName  商品名
     * @param orderCode  订单code
     * @param orderMoney 订单金额
     * @param type       类型 1:订单支付 2:预存款充值
     */
    private OrderInfoForPay buildOrderInfoForPay(String goodsName, String orderCode, BigDecimal orderMoney, int type) {
        OrderInfoForPay orderInfoForPay = new OrderInfoForPay();
        //商品名（取一个就行）
        orderInfoForPay.setGoodsName(goodsName);
        //订单号
        orderInfoForPay.setOrderCode(orderCode);
        //订单金额
        orderInfoForPay.setPrice(orderMoney);
        //支付类型
        orderInfoForPay.setType(type);
        return orderInfoForPay;
    }


    /**
     * 获取网站地址
     */
    private String getSiteUrl() {
        BaseInfoSet baseInfoSet = baseInfoSetService.queryBaseInfoSet();
        if (ObjectUtils.isEmpty(baseInfoSet)) {
            return null;
        }
        return baseInfoSet.getSiteUrlWithBias();
    }

    /**
     * 获取网站地址
     */
    private BaseInfoSet getSite() {
        return baseInfoSetService.queryBaseInfoSet();
    }

    /**
     * 阿里支付公共方法
     *
     * @param orderCode     订单code
     * @param customerId    用户id
     * @param type          类型 1 订单支付 2 预存充值
     * @param aliPaySetting 支付宝设置实体
     * @param paySupplier   回调获得支付宝支付参数
     * @return -9 支付宝没开启 -1  用户不存在 -3 没有待支付订单 -7 网站地址不存在 -8 支付宝配置的参数错误 1 成功
     */
    private CommonResponse<String> aliCommonPay(String orderCode, long customerId, int type, AliPaySetting aliPaySetting, PaySupplier<AliPaySetting, OrderInfoForPay, String> paySupplier) {

        logger.debug("aliCommonPay and orderCode:{}  \r\n customerId:{} \r\n type:{} \r\n aliPaySetting:{} \r\n", orderCode, customerId, type, aliPaySetting);

        //支付宝支付设置(数据库)
        AliPaySet aliPaySet = paySetService.queryPaySet().getAliPaySet();

        // 没有开启支付宝支付 则直接返回
        if (!aliPaySet.checkIsUse()) {
            logger.error("aliCommonPay fail due to not used....");
            return CommonResponse.build("", -9);

        }

        // 查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);
        if (Objects.isNull(customer)) {
            logger.error("aliCommonPay fail due to member is not ");
            return CommonResponse.build("", -1);
        }

        // 订单支付实体
        OrderInfoForPay orderInfoForPay = getOrderInfoForPay(orderCode, customerId, type);

        // 如果没有支付订单 则直接返回
        if (Objects.isNull(orderInfoForPay)) {
            logger.error("aliCommonPay fail due to no order....");
            return CommonResponse.build("", -3);
        }

        // 网站地址
        String siteUrl = getSiteUrl();
        if (StringUtils.isEmpty(siteUrl)) {
            logger.error("aliCommonPay fail due to no siteurl....");
            return CommonResponse.build("", -7);
        }

        //支付宝支付设置
        BeanUtils.copyProperties(aliPaySet, aliPaySetting);

        // 校验参数
        if (!aliPaySetting.checkPayParams()) {
            logger.error("aliCommonPay fail due to no checkPayParams fail....");
            return CommonResponse.build("", -8);
        }

        //前台同步回调地址
        aliPaySetting.setBeforeCallbackUrl(aliPaySetting.getBeforeCallbackUrl());
        // aliPaySetting.setBeforeCallbackUrl(siteUrl + aliPaySetting.getBeforeCallbackUrl());
        //异步回调地址
        aliPaySetting.setBackCallbackUrl(siteUrl + aliPaySetting.getBackCallbackUrl());
logger.info("aliPaySetting="+aliPaySetting);
        // 获得阿里的支付html
        String aliPayParams = paySupplier.getPayParams(aliPaySetting, orderInfoForPay);

        //生成表单错误
        if (StringUtils.isEmpty(aliPayParams)) {
            logger.error("aliCommonPay fail due to form error....");
            return CommonResponse.build("", -5);
        }
        logger.debug("aliCommonPay pay success......");

        return CommonResponse.build(aliPayParams, 1);
    }

    /**
     * 获得付款的实体
     *
     * @param orderCode  订单code
     * @param customerId 会员id
     * @param type       类型 1 订单 2 预存款 3 门店订单
     * @return 返回付款实体
     */
    private OrderInfoForPay getOrderInfoForPay(String orderCode, long customerId, int type) {
        // 订单
        if (type == CommonConstant.ORDER_PAY) {
            // 根据订单编号和用户id查询待付款的订单信息
            List<OmsOrder> orders = orderService.queryToPayOrder(orderCode, customerId, OrderItem.SKUS);

            // 没有待支付的订单直接返回
            if (CollectionUtils.isEmpty(orders)) {
                return null;
            }

            return buildOrderInfoForPay(orders.get(0).getOrderSkuNames(), orderCode, getOrdersAllMoeny(orders), type);

        } else if (type == CommonConstant.STORE_ORDER_PAY) {
            // 门店订单

            // 根据订单编号和用户id查询待支付的门店订单信息
            List<TStoreOrder> storeOrders = storeOrderService.queryToPayStoreOrder(orderCode, customerId, OrderItem.SKUS);

            // 没有待支付的门店订单直接返回
            if (CollectionUtils.isEmpty(storeOrders)) {
                return null;
            }

            return buildOrderInfoForPay(storeOrders.get(0).getOrderSkuNames(), orderCode, storeOrders.stream().map(TStoreOrder::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add), type);
        }

        return null;
    }


    /**
     * 微信支付公共方法
     *
     * @param orderCode     订单号
     * @param customerId    用户id
     * @param type          支付类型    1 订单支付 2 预存充值
     * @param wechatSetting 微信设置
     * @param supplier      回调
     * @return -8 没有设置微信支付信息 -9 微信支付没启动 -7 没有配置站点信息 -1 用户不存在 -3 没有待支付订单 -5 微信生存支付信息失败 1 成功
     */
    private <T> CommonResponse<T> weChatCommonPay(String orderCode, long customerId, int type, WechatSetting wechatSetting, PaySupplier<WechatSetting, OrderInfoForPay, CommonResponse<T>> supplier) {

        logger.debug("weChatCommonPay and orderCode:{} \r\n customerId:{}   \r\n:type:{}", orderCode, customerId, type);

        //微信支付设置(数据库)
        WechatPaySet wechatPaySet;

        // 微信app、小程序支付和其他微信支付配置的信息不一样所以这边分开获取微信支付信息
        if (wechatSetting.isAppPay()) {
            wechatPaySet = paySetService.queryPaySet().getWechatAppPaySet();
        } else if (wechatSetting.isAppletPay()) {
            wechatPaySet = paySetService.queryPaySet().getWechatAppletPaySet();
        } else {
            wechatPaySet = paySetService.queryPaySet().getWechatPaySet();
        }

        // 没设置微信支付信息
        if (ObjectUtils.isEmpty(wechatPaySet)) {
            logger.error("wechatQRPay fail due to no wechatPaySet is empty....");
            return CommonResponse.build(null, -8);
        }

        // 微信支付没启用
        if (!wechatPaySet.checkIsUse()) {
            logger.error("wechatQRPay fail due to not used....");
            return CommonResponse.build(null, -9);
        }

        // 网站基本信息
        BaseInfoSet baseInfoSet = baseInfoSetService.queryBaseInfoSet();

        if (ObjectUtils.isEmpty(baseInfoSet) || StringUtils.isEmpty(baseInfoSet.getSiteUrlWithBias()) || StringUtils.isEmpty(baseInfoSet.getSiteName())) {
            logger.error("wechatH5Pay fail due to no baseinfoset....");
            return CommonResponse.build(null, -7);
        }

        // 设置网站名称和网站地址
        wechatSetting.addSiteName(baseInfoSet.getSiteName()).addSiteUrl(baseInfoSet.getSiteUrlWithBias());

        // 设置微信支付成功后回调地址
        wechatSetting.setPayCallback(baseInfoSet.getSiteUrlWithBias() + wechatSetting.getPayCallback());

        BeanUtils.copyProperties(wechatPaySet, wechatSetting);
            logger.info("wechatSetting="+wechatSetting);

        // 检查参数
        if (!wechatSetting.checkPayParams()) {
            logger.error("wechatQRPay fail due to no checkPayParams fail....");
            return CommonResponse.build(null, -8);
        }

        // 查询用户信息
        UmsMember customer = customerService.queryCustomerWithNoPasswordById(customerId);

        // 用户信息为空 则直接返回
        if (Objects.isNull(customer)) {
            logger.error("weChatCommonPay fail due to member is not ");
            return CommonResponse.build(null, -1);
        }

        // 待支付订单
        OrderInfoForPay orderInfoForPay = getOrderInfoForPay(orderCode, customerId, type);

        // 没有待支付订单直接返回
        if (Objects.isNull(orderInfoForPay)) {
            logger.error("weChatCommonPay fail due to no order....");
            return CommonResponse.build(null, -3);
        }

        return supplier.getPayParams(wechatSetting, orderInfoForPay);
    }
}
