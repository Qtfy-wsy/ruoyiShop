package com.ruoyi.common.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.ruoyi.common.utils.bean.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝支付工具类
 */
public class AliPayUtils {

    /**
     * 编码集，支持GBK/UTF-8
     */
    public final static String CHARSET = "UTF-8";
    /**
     * 异步回调成功返回参数
     */
    public final static String SUCCESS = "success";
    /**
     * 调试日志
     */
    private static final Logger logger = LoggerFactory.getLogger(AliPayUtils.class);
    /**
     * 支付宝网关（固定)
     */
    private final static String PAY_URL = "https://openapi.alipay.com/gateway.do";
    /**
     * 支付宝网关（沙箱)
     */
    private final static String DEV_PAY_URL = "https://openapi.alipaydev.com/gateway.do";
    /**
     * 参数返回格式，只支持json
     */
    private final static String FORMAT = "json";
    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private final static String SIGN_TYPE = "RSA2";

    /**
     * pc 销售产品码，与支付宝签约的产品码名称。
     */
    private final static String PAGE_PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";

    /**
     * wap 销售产品码，与支付宝签约的产品码名称。
     */
    private final static String WAP_PRODUCT_CODE = "QUICK_WAP_WAY";


    private AliPayUtils() {
    }

    /**
     * 电脑网页支付
     *
     * @param aliPaySetting   支付宝设置
     * @param orderInfoForPay 订单信息
     * @return 前台页面请求需要的完整form表单的html（包含自动提交脚本）
     */
    public static String pagePay(AliPaySetting aliPaySetting, OrderInfoForPay orderInfoForPay) {
        logger.debug("AliPayUtils pagePay and aliPaySetting:{} \r\n orderInfoForPay:{}  ", aliPaySetting, orderInfoForPay);
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(PAY_URL, aliPaySetting.getAppId(), aliPaySetting.getAppPrivateKey(), FORMAT, CHARSET, aliPaySetting.getAlipayPublicKey(), SIGN_TYPE);
        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        //前台回跳地址
        alipayRequest.setReturnUrl(aliPaySetting.getBeforeCallbackUrl());
        //后台通知地址
        alipayRequest.setNotifyUrl(aliPaySetting.getBackCallbackUrl());
        //填充业务参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderInfoForPay.getOrderCode() + "\"," +
                "    \"product_code\":\"" + PAGE_PRODUCT_CODE + "\"," +
                "    \"total_amount\":" + orderInfoForPay.getPrice().setScale(2, BigDecimal.ROUND_HALF_EVEN) + "," +
                "    \"subject\":\"" + orderInfoForPay.getGoodsName() + "\"," +
                "    \"passback_params\":\"" + URLEncoder.encode(orderInfoForPay.getType() + "") + "\"" + //公用回传参数，需要UrlEncode
                "  }");
        logger.debug("pagePay and bizContent", alipayRequest.getBizContent());
        String form = "";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();

        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        logger.debug("pagePay and form", form);
        return form;
    }

    /**
     * 手机网页支付
     *
     * @param aliPaySetting   支付宝设置
     * @param orderInfoForPay 订单信息
     * @return 前台页面请求需要的完整form表单的html（包含自动提交脚本）
     */
    public static String wapPay(AliPaySetting aliPaySetting, OrderInfoForPay orderInfoForPay) {
        logger.debug("AliPayUtils wapPay and aliPaySetting:{} \r\n orderInfoForPay:{}  ", aliPaySetting, orderInfoForPay);
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(PAY_URL, aliPaySetting.getAppId(), aliPaySetting.getAppPrivateKey(), FORMAT, CHARSET, aliPaySetting.getAlipayPublicKey(), SIGN_TYPE);
        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        //前台回跳地址
        alipayRequest.setReturnUrl(aliPaySetting.getBeforeCallbackUrl());
        //后台通知地址
        alipayRequest.setNotifyUrl(aliPaySetting.getBackCallbackUrl());
        //填充业务参数
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderInfoForPay.getOrderCode() + "\"," +
                "    \"product_code\":\"" + WAP_PRODUCT_CODE + "\"," +
                "    \"total_amount\":" + orderInfoForPay.getPrice() + "," +
                "    \"subject\":\"" + orderInfoForPay.getGoodsName() + "\"," +
                "    \"passback_params\":\"" + URLEncoder.encode(orderInfoForPay.getType() + "") + "\"" + //公用回传参数，需要UrlEncode
                "  }");
        logger.debug("wapPay and bizContent", alipayRequest.getBizContent());
        String form = "";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        logger.debug("wapPay and form", form);
        return form;
    }

    /**
     * app网页支付
     *
     * @param aliPaySetting   支付宝设置
     * @param orderInfoForPay 订单信息
     * @return 返回可以直接给客户端请求，无需再做处理。
     */
    public static String appPay(AliPaySetting aliPaySetting, OrderInfoForPay orderInfoForPay) {
        logger.debug("appPay and aliPaySetting:{} \r\n orderInfoForPay:{}", aliPaySetting, orderInfoForPay);
        AlipayClient alipayClient = new DefaultAlipayClient(PAY_URL, aliPaySetting.getAppId(), aliPaySetting.getAppPrivateKey(), "json", CHARSET, aliPaySetting.getAlipayPublicKey(), "RSA2");
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setBody(orderInfoForPay.getGoodsName());
        model.setSubject(orderInfoForPay.getGoodsName());
        model.setOutTradeNo(orderInfoForPay.getOrderCode());
        model.setTimeoutExpress("30m");
        model.setTotalAmount(orderInfoForPay.getPrice().toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        model.setPassbackParams(URLEncoder.encode(orderInfoForPay.getType() + ""));
        request.setBizModel(model);
        request.setNotifyUrl(aliPaySetting.getBackCallbackUrl());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            logger.debug("appPay param", response.getBody());
            return response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 验证返回信息
     *
     * @param requestMap 异步通知中收到的所有参数
     * @return 订单支付信息
     */
    public static OrderInfoAfterPay afterPayInfo(AliPaySetting aliPaySetting, Map<String, String[]> requestMap) {
        logger.debug("AliPayUtils afterPayInfo and aliPaySetting:{}\r\n requestMap:{}", aliPaySetting, requestMap);
        Map<String, String> paramsMap = new HashMap<>();
        for (Iterator<?> iter = requestMap.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = requestMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes(ISO_8859_1), "gbk");
            paramsMap.put(name, valueStr.toString());
        }

        OrderInfoAfterPay orderInfoAfterPay = new OrderInfoAfterPay();
        boolean signVerified = false;
        orderInfoAfterPay.setSuccess(false);
        try {
            //调用SDK验证签名
            signVerified = AlipaySignature.rsaCheckV1(paramsMap, aliPaySetting.getAlipayPublicKey(), CHARSET, SIGN_TYPE);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (signVerified) {
            //  验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            if (aliPaySetting.getAppId().equals(paramsMap.get("app_id"))) {
                orderInfoAfterPay.setSuccess(true);
                orderInfoAfterPay.setOrderCode(paramsMap.get("out_trade_no"));
                orderInfoAfterPay.setType(Integer.parseInt(paramsMap.get("passback_params")));
                orderInfoAfterPay.setTransCode(paramsMap.get("trade_no"));
            }
        } else {
            //  验签失败则记录异常日志，并在response中返回failure.
            logger.error("afterPayInfo and signVerified Fail");
        }
        return orderInfoAfterPay;
    }

    /**
     * 支付宝提现
     *
     * @param aliPaySetting   阿里设置
     * @param withdrawRequest 提现请求
     * @return 返回提现结果
     */
    public static WithdrawResponse withdraw(AliPaySetting aliPaySetting, WithdrawRequest withdrawRequest) {

        logger.debug("withdraw and aliPaySetting:{} \r\n ,withdrawRequest:{}", aliPaySetting, withdrawRequest);

        AlipayClient alipayClient = new DefaultAlipayClient(PAY_URL, aliPaySetting.getAppId(), aliPaySetting.getAppPrivateKey(), "json", "UTF-8", aliPaySetting.getAlipayPublicKey(), SIGN_TYPE);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" +
                "\"out_biz_no\":\"" + withdrawRequest.getTradeNo() + "\"," +
                "\"payee_type\":\"ALIPAY_LOGONID\"," +
                "\"payee_account\":\"" + withdrawRequest.getAccount() + "\"," +
                "\"amount\":\"" + withdrawRequest.getMoney().toString() + "\"," +
                "\"payer_show_name\":\"平台\"," +
                "\"payee_real_name\":\"" + withdrawRequest.getName() + "\"," +
                "\"remark\":\"账户提现\"" +
                "  }");
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            return WithdrawResponse.build(response.getCode(), response.getSubCode());
        } catch (Exception e) {
            logger.error("withdraw fail....", e);
        }

        return WithdrawResponse.buildSystemError();
    }

}
