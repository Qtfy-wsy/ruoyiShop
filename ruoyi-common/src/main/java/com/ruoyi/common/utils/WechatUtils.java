package com.ruoyi.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.md5.MD5Utils;
import com.ruoyi.common.utils.bean.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;


/**
 * 微信工具类
 */
public class WechatUtils {

    /**
     * 支付异步回调成功后需返回的xml
     */
    public final static String SUCCESS_RETURN = "<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>";
    /**
     * 成功
     */
    public final static String SUCCESS = "SUCCESS";
    /**
     * 公众号支付
     */
    public final static int OFFICIAL_ACCOUNT_PAY = 1;
    /**
     * H5支付
     */
    public final static int H5_PAY = 2;
    /**
     * 扫码支付
     */
    public final static int QR_PAY = 3;
    /**
     * app支付
     */
    public final static int APP_PAY = 4;
    /**
     * 小程序支付
     */
    public final static int APPLET_PAY = 5;
    /**
     * 调试日志
     */
    private static final Logger logger = LoggerFactory.getLogger(WechatUtils.class);
    /**
     * 获取code地址
     */
    private final static String GET_CODE_URL =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
    /**
     * 获取access_token地址
     */
    private final static String GET_ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    /**
     * 获取用户信息地址
     */
    private final static String GET_USERINFO_URL =
            "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    /**
     * 静默授权
     */
    private final static String SNSAPI_BASE = "snsapi_base";
    /**
     * 用户授权
     */
    private final static String SNSAPI_USERINFO = "snsapi_userinfo";
    /**
     * 微信支付统一下单地址
     */
    private final static String UNION_SUBMIT_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    /**
     * 获取微信分享access_token地址（参数为 appid 和 secret）
     */
    private final static String GET_ACCESS_TOKEN_FOR_SHARE_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 获取微信分享jsapi_ticket地址（参数为 access_token）
     */
    private final static String GET_JSAPI_TICKET_URL =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

    private WechatUtils() {
    }

    /**
     * 获取静默授权地址
     *
     * @param fromUrl       拦截前链接
     * @param wechatSetting 微信设置实体
     * @return 静默授权地址
     */
    public static String getCodeUrlBase(String fromUrl, WechatSetting wechatSetting) {
        logger.debug("getCodeUrlBase and fromUrl:{} \r\n wechatSetting:{}", fromUrl, wechatSetting);
        return String.format(GET_CODE_URL, wechatSetting.getAppId(), wechatSetting.getUrl(), SNSAPI_BASE, fromUrl);
    }

    /**
     * 获取用户授权地址
     *
     * @param fromUrl       拦截前链接
     * @param wechatSetting 微信设置实体
     * @return 用户授权地址
     */
    public static String getCodeUrlInfo(String fromUrl, WechatSetting wechatSetting) {
        logger.debug("getCodeUrlInfo and fromUrl:{}\r\n wechatSetting:{} ", fromUrl, wechatSetting);
        return String.format(GET_CODE_URL, wechatSetting.getAppId(), wechatSetting.getUrl(), SNSAPI_USERINFO, fromUrl);
    }

    /**
     * 获取网页授权access_token
     *
     * @param code          微信code
     * @param state         与code一起返回的状态码（额外参数）
     * @param wechatSetting 微信设置实体
     * @return 网页授权返回实体类
     */
    public static AccessTokenResult getAccessToken(String code, String state, WechatSetting wechatSetting) {
        AccessTokenResult res;
        logger.debug("getAccessToken and code:{}\r\n wechatSetting:{} ", code, wechatSetting);
        String accessTokenUrl = String.format(GET_ACCESS_TOKEN_URL, wechatSetting.getAppId(), wechatSetting.getAppSecret(), code);
        res = JSON.parseObject(executeHttpGet(accessTokenUrl), AccessTokenResult.class);
        if (res.isError()) {
            if (!StringUtils.isEmpty(res.getErrmsg())) {
                logger.error("getAccessToken Fail and errmsg:{}", res.getErrmsg());
            }
            return null;
        } else {
            res.setRedirectUrl(state);
            return res;
        }

    }

    /**
     * 获取微信分享access_token
     *
     * @param wechatSetting 微信设置实体
     * @return 微信分享access_token
     */
    public static String getAccessTokenForShare(WechatSetting wechatSetting) {
        logger.debug("getAccessToken and wechatSetting:{} ", wechatSetting);
        String accessTokenForShareUrl = String.format(GET_ACCESS_TOKEN_FOR_SHARE_URL, wechatSetting.getAppId(), wechatSetting.getAppSecret());
        JSONObject res = JSON.parseObject(executeHttpGet(accessTokenForShareUrl));
        if (!StringUtils.isEmpty(res.getString("errcode"))) {
            if (!StringUtils.isEmpty(res.getString("errmsg"))) {
                logger.error("getAccessTokenForShare Fail and errmsg:{}", res.getString("errmsg"));
            }
            return null;
        } else {
            return res.getString("access_token");
        }
    }

    /**
     * 获取微信分享jsapi_ticket
     *
     * @param access_token 凭证
     * @return 微信分享jsapi_ticket
     */
    public static String getJsapiTicketForShare(String access_token) {
        logger.debug("getAccessToken and access_token:{} ", access_token);
        String jsapiTicketForShareUrl = String.format(GET_JSAPI_TICKET_URL, access_token);
        JSONObject res = JSON.parseObject(executeHttpGet(jsapiTicketForShareUrl));
        if (Integer.parseInt(res.getString("errcode")) != 0) {
            if (!StringUtils.isEmpty(res.getString("errmsg"))) {
                logger.error("getJsapiTicketForShare Fail and errmsg:{}", res.getString("errmsg"));
            }
            return null;
        } else {
            return res.getString("ticket");
        }
    }

    /**
     * 获取用户信息
     *
     * @param accessToken 网页授权接口调用凭证
     * @param openId      用户的唯一标识
     * @return 用户信息返回实体类
     */
    public static UserInfoResult getUserInfo(String accessToken, String openId) {
        logger.debug("getUserInfo and access_token:{}\r\n openid:{} ", accessToken, openId);

        String userInfoUrl = String.format(GET_USERINFO_URL, accessToken, openId);
        UserInfoResult res = JSON.parseObject(executeHttpGet(userInfoUrl), UserInfoResult.class);
        if (res.isError()) {
            if (!StringUtils.isEmpty(res.getErrmsg())) {
                logger.error("getUserInfo Fail and errmsg:{}", res.getErrmsg());
            }
            return null;
        } else {
            return res;
        }
    }

    /**
     * 获取预支付信息
     *
     * @param wechatSetting   微信设置实体
     * @param orderInfoForPay 订单信息
     * @return 预支付信息
     */
    public static PrepayResult getPrepay(WechatSetting wechatSetting, OrderInfoForPay orderInfoForPay) {
        logger.debug("getPrepay and \r\n wechatSetting:{} \r\n orderInfoForPay:{} ", wechatSetting, orderInfoForPay);
        String xml = getWxPayParm(wechatSetting, orderInfoForPay);
        String res = executeHttpPost(UNION_SUBMIT_ORDER_URL, xml);
        logger.debug("getPrepay and res:{}", res);

        //将xml字符串转换为java对象
        JaxbUtil resultBinder = new JaxbUtil(PrepayResult.class);
        PrepayResult prepayResult = resultBinder.fromXml(res);
        if (!prepayResult.isError()) {
            //公众号支付时增加返回信息
            if (wechatSetting.isOfficialAccountPay() || wechatSetting.isAppletPay()) {
                SortedMap<String, String> packageParams = new TreeMap<>();
                prepayResult.setPackage();
                prepayResult.setSign_type("MD5");
                prepayResult.setTime_stamp(getTimeStamp());
                packageParams.put("appId", prepayResult.getAppid());
                packageParams.put("timeStamp", prepayResult.getTime_stamp());
                packageParams.put("nonceStr", prepayResult.getNonce_str());
                packageParams.put("package", prepayResult.getPackage_());
                packageParams.put("signType", prepayResult.getSign_type());
                prepayResult.setPay_sign(createSign(packageParams, wechatSetting.getApiKey()));
            } else if (wechatSetting.isAppPay()) {
                // app支付获得签名
                SortedMap<String, String> packageParams = new TreeMap<>();
                prepayResult.setTime_stamp(getTimeStamp());
                packageParams.put("appid", prepayResult.getAppid());
                packageParams.put("timestamp", prepayResult.getTime_stamp());
                packageParams.put("noncestr", prepayResult.getNonce_str());
                packageParams.put("package", "Sign=WXPay");
                packageParams.put("partnerid", wechatSetting.getMerchantNum());
                packageParams.put("prepayid", prepayResult.getPrepay_id());
                prepayResult.setPay_sign(createSign(packageParams, wechatSetting.getApiKey()));
            }
            return prepayResult;
        } else {
            if (!StringUtils.isEmpty(prepayResult.getErr_code_des())) {
                logger.error("getPrepay Fail and returnmsg:{} \r\n errormsg:{} ", prepayResult.getReturn_msg(), prepayResult.getErr_code_des());
            }
            return null;
        }
    }

    /**
     * 获取统一下单请求参数
     *
     * @param wechatSetting   微信设置实体
     * @param orderInfoForPay 订单信息
     * @return 统一下单请求参数
     */
    private static String getWxPayParm(WechatSetting wechatSetting, OrderInfoForPay orderInfoForPay) {
        logger.debug("getWxPayParm and wechatSetting:{} \r\n orderInfoForPay:{} ", wechatSetting, orderInfoForPay);
        SortedMap<String, String> packageParams = new TreeMap<>();
        // 金额转化为分为单位
        int finalmoney = Integer.parseInt(String.format("%.2f", orderInfoForPay.getPrice()).replace(".", ""));
        //交易方式
        String tradeType = "";
        //场景信息
        String sceneInfo;
        //商品ID
        String productId;
        //公众号支付
        if (wechatSetting.isOfficialAccountPay() || wechatSetting.isAppletPay()) {
            //用户唯一标识
            packageParams.put("openid", wechatSetting.getOpenId());
            tradeType = "JSAPI";
        }
        //H5支付
        if (wechatSetting.isH5Pay()) {
            tradeType = "MWEB";
            sceneInfo = "{\"h5_info\": {\"type\":\"Wap\",\"wap_url\": \"" + wechatSetting.getSiteUrl() + "\",\"wap_name\": \"" + wechatSetting.getSiteName() + "\"}}";
            packageParams.put("scene_info", sceneInfo);
        }
        //扫码支付
        if (wechatSetting.isQRPay()) {
            tradeType = "NATIVE";
            productId = String.valueOf(orderInfoForPay.getGoodsId());
            packageParams.put("product_id", productId);
        }

        // APP支付
        if (wechatSetting.isAppPay()) {
            tradeType = "APP";
        }
        //公众账号ID
        packageParams.put("appid", wechatSetting.getAppId());
        //商户号
        packageParams.put("mch_id", wechatSetting.getMerchantNum());
        //随机字符串
        packageParams.put("nonce_str", getRandomString());
        //商品描述
        packageParams.put("body", getSubGoodName(orderInfoForPay.getGoodsName()));
        //附加数据(支付类型)
        packageParams.put("attach", orderInfoForPay.getType() + "");
        //商户订单号
        packageParams.put("out_trade_no", orderInfoForPay.getOrderCode() + "-" + getTimeStamp());
        //标价金额
        packageParams.put("total_fee", String.valueOf(finalmoney));
        //终端IP
        packageParams.put("spbill_create_ip", wechatSetting.getIp());//本地测试需替换为外网ip
        //通知地址
        packageParams.put("notify_url", wechatSetting.getPayCallback());
        //交易类型
        packageParams.put("trade_type", tradeType);
        //签名
        packageParams.put("sign", createSign(packageParams, wechatSetting.getApiKey()));

        return getRequestXml(packageParams);
    }


    /**
     * 截取商品名
     *
     * @param goodName 商品名
     * @return 截取后的商品名
     */
    private static String getSubGoodName(String goodName) {
        if (goodName.length() < 20) {
            return goodName;
        } else {
            return goodName.substring(0, 19) + "...";
        }
    }


    /**
     * 验证返回信息
     *
     * @param inputStream   微信回调信息
     * @param wechatSetting 微信设置
     * @return 订单支付信息
     */
    public static OrderInfoAfterPay afterPayInfo(InputStream inputStream, WechatSetting wechatSetting) {
        logger.debug("afterPayInfo and wechatSetting:{}", wechatSetting);
        OrderInfoAfterPay orderInfoAfterPay = new OrderInfoAfterPay();
        orderInfoAfterPay.setSuccess(false);
        BufferedReader br;
        br = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                br.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //将xml字符串转换为java对象
        JaxbUtil resultBinder = new JaxbUtil(WechatNotifyResult.class);
        WechatNotifyResult wechatNotifyResult = resultBinder.fromXml(sb.toString());
        //将xml字符串转换为map
        SortedMap<String, String> resultMap = new TreeMap<>(parseMapFromXmlStr(sb.toString()));
        //签名
        String sign = createSign(resultMap, wechatSetting.getApiKey());

        logger.info("wechat notify and wechatNotifyResult :{}", wechatNotifyResult);

        //验证是否失败，并验证签名
        if (!wechatNotifyResult.isError() && sign.equals(wechatNotifyResult.getSign())) {
            //验证其他信息
            if (SUCCESS.equals(wechatNotifyResult.getResult_code())
                    && SUCCESS.equals(wechatNotifyResult.getReturn_code())
                    && wechatSetting.getAppId().equals(wechatNotifyResult.getAppid())
                    && wechatSetting.getMerchantNum().equals(wechatNotifyResult.getMch_id())
            ) {
                orderInfoAfterPay.setOrderCode(wechatNotifyResult.getOut_trade_no().split("-")[0]);
                orderInfoAfterPay.setSuccess(true);
                orderInfoAfterPay.setType(wechatNotifyResult.getAttach());
                orderInfoAfterPay.setTransCode(wechatNotifyResult.getTransaction_id());
            }

        } else {
            logger.error("wechat afterPayInfo fail: returnmsg:{}\r\n errormsg:{}", wechatNotifyResult.getReturn_msg(), wechatNotifyResult.getErr_code_des());
        }
        return orderInfoAfterPay;
    }

    /**
     * 获取请求xml
     *
     * @param packageParams 请求参数
     * @return 请求xml
     */
    private static String getRequestXml(SortedMap<String, String> packageParams) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        Set es = packageParams.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if ("body".equalsIgnoreCase(key) || "sign".equalsIgnoreCase(key)) {
                sb.append("<").append(key).append(">").append("<![CDATA[").append(value).append("]]></").append(key).append(">");
            } else {
                sb.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
            }
        }
        sb.append("</xml>");
        logger.debug("getRequestXml  and xml:{}", sb.toString());
        return sb.toString();
    }

    /**
     * 签名
     *
     * @param packageParams 签名参数
     * @param apiKey        商户key
     * @return 签名
     */
    private static String createSign(SortedMap<String, String> packageParams, String apiKey) {

        StringBuilder sb = new StringBuilder();
        Set es = packageParams.entrySet();
        for (Object e : es) {
            Map.Entry entry = (Map.Entry) e;
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ((v != null) && (!"".equals(v)) && (!"sign".equals(k)) &&
                    (!"key".equals(k))) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append("key=").append(apiKey);
        logger.debug("createSign  and key:{}", apiKey);
        logger.debug("createSign  and stringSign:{}", sb.toString());
        String sign = MD5Utils.getInstance().createMd5(sb.toString())
                .toUpperCase();
        logger.debug("createSign  and sign:{}", sign);
        return sign;

    }

    /**
     * 执行GET方法请求数据
     *
     * @param url 请求地址
     * @return 返回数据
     */
    static String executeHttpGet(String url) {
        logger.debug("executeHttpGet and url:{}", url);
        String result = null;
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpGet httpRequest = new HttpGet(url);

        try {
            //使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
            HttpResponse httpResponse = httpClient.execute(httpRequest);//其中HttpGet是HttpUriRequst的子类
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");//取出应答字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 执行POST方法请求数据
     *
     * @param url 请求地址
     * @param xml 请求参数
     * @return 返回数据
     */
    private static String executeHttpPost(String url, String xml) {
        logger.debug("executeHttpPost and url:{}", url);
        String result = null;
        BasicHttpClientConnectionManager connManager = new BasicHttpClientConnectionManager(
                RegistryBuilder.<ConnectionSocketFactory>create()
                        .register("http", PlainConnectionSocketFactory.getSocketFactory())
                        .register("https", SSLConnectionSocketFactory.getSocketFactory())
                        .build(),
                null,
                null,
                null
        );

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();
        HttpPost httpRequest = new HttpPost(url);

        try {
            httpRequest.setEntity(new StringEntity(xml, "UTF-8"));
            //使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
            HttpResponse httpResponse = httpClient.execute(httpRequest);//其中HttpGet是HttpUriRequst的子类
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, "UTF-8");//取出应答字符串

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 获取随机字符串
     *
     * @return 随机字符串
     */
    public static String getRandomString() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获取时间戳
     *
     * @return 时间戳（秒数）
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000L);
    }

    /**
     * 将xml字符串转换成map
     *
     * @param xml xml
     * @return Map
     */
    private static Map<String, String> parseMapFromXmlStr(String xml) {
        Map<String, String> map = new HashMap<>();
        Document doc;
        try {
            doc = DocumentHelper.parseText(xml); // 将字符串转为XML
            Element rootElt = doc.getRootElement(); // 获取根节点
            List<Element> list = rootElt.elements();//获取根节点下所有节点
            for (Element element : list) {  //遍历节点
                map.put(element.getName(), element.getText()); //节点的name为map的key，text为map的value
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
