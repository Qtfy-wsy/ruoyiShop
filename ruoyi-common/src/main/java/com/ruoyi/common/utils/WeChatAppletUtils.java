package com.ruoyi.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.bean.WeChatAppletLoginResponse;
import com.ruoyi.common.utils.bean.WeChatAppletUserInfo;
import com.ruoyi.common.utils.bean.WechatSetting;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * 微信小程序工具类
 *
 * @author SK
 * @since 2018/6/13
 */
@Slf4j
public class WeChatAppletUtils {

    /**
     * 获取登录信息地址
     */
    private final static String GET_LOGIN_INFO_URL = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
    /**
     * 获取微信小程序access_token地址（参数为 appid 和 secret）
     */
    private final static String GET_WECHAT_APPLET_ACCESS_TOKEN_ = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 获取微信小程序分享码请求地址
     */
    private final static String GET_WECHAT_APPLET_SHARE_CODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";
    /**
     * 获取微信小程序直播房间列表地址
     */
    private final static String GET_WECHAT_APPLET_LIVE_PLAYER_LIST_URL = "http://api.weixin.qq.com/wxa/business/getliveinfo?access_token=%s";

    private WeChatAppletUtils() {
    }

    /**
     * 获取登录信息
     *
     * @param code          用户登录凭证（有效期五分钟）。开发者需要在开发者服务器后台调用 api，使用 code 换取 openid 和 session_key 等信息
     * @param wechatSetting 微信设置
     * @return 微信小程序登录返回实体
     */
    public static WeChatAppletLoginResponse getLoginInfo(String code, WechatSetting wechatSetting) {
        log.debug("getLoginInfo and code:{} \r\n wechatSetting:{}", code, wechatSetting);
        WeChatAppletLoginResponse weChatAppletLoginResponse = JSON.parseObject(WechatUtils.executeHttpGet(String.format(GET_LOGIN_INFO_URL, wechatSetting.getAppId(), wechatSetting.getAppSecret(), code)), WeChatAppletLoginResponse.class);
        if (Objects.isNull(weChatAppletLoginResponse)) {
            log.error("getLoginInfo fail : weChatAppletLoginResponse is null");
            return null;
        }
        if (weChatAppletLoginResponse.isError()) {
            log.debug("getLoginInfo fail and errorMsg:{}", weChatAppletLoginResponse.getErrmsg());
            return null;
        }
        return weChatAppletLoginResponse;
    }

    /**
     * 根据用户信息获取unionId
     *
     * @param sessionKey           会话密钥
     * @param weChatAppletUserInfo 微信小程序用户信息实体
     * @return 用户unionId
     */
    public static String getUnionIdFromUserInfo(String sessionKey, WeChatAppletUserInfo weChatAppletUserInfo) {
        log.debug("getUnionIdFromUserInfo and sessionKey:{} \r\n weChatAppletUserInfo:{}", sessionKey, weChatAppletUserInfo);
        String unionId = null;
        if (Objects.isNull(weChatAppletUserInfo)) {
            log.error("getUnionIdFromUserInfo fail:weChatAppletUserInfo is null ");
            return null;
        }
        // 被加密的数据
        byte[] dataByte = Base64.decodeBase64(weChatAppletUserInfo.getEncryptedData());
        // 加密秘钥
        byte[] aeskey = Base64.decodeBase64(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(weChatAppletUserInfo.getIv());

        try {
            byte[] resultByte = AesUtils.getInstance().decrypt(dataByte, aeskey, ivByte);
            String userInfo = new String(resultByte, "UTF-8");
            unionId = JSON.parseObject(userInfo).getString("unionId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return unionId;
    }

    /**
     * 获取微信小程序access_token（有效期两小时）
     *
     * @param wechatSetting 微信设置实体
     * @return 微信小程序access_token
     */
    public static String getAccessToken(WechatSetting wechatSetting) {
        log.debug("getAccessToken and wechatSetting:{} ", wechatSetting);
        String accessTokenForShareUrl = String.format(GET_WECHAT_APPLET_ACCESS_TOKEN_, wechatSetting.getAppId(), wechatSetting.getAppSecret());
        JSONObject res = JSON.parseObject(CustomHttpUtils.doGet(accessTokenForShareUrl));
        if (!StringUtils.isEmpty(res.getString("errcode"))) {
            if (!StringUtils.isEmpty(res.getString("errmsg"))) {
                log.error("getAccessToken Fail and errmsg:{}", res.getString("errmsg"));
            }
            return null;
        } else {
            return res.getString("access_token");
        }
    }

    /**
     * 获取json参数post请求返回值（微信小程序码）
     *
     * @param url        请求地址
     * @param jsonString 请求参数
     * @return 请求返回（ByteArrayInputStream格式，访问数组的字节输入流）
     */
    public static ByteArrayInputStream getJsonRequestResult(String url, String jsonString) {
        log.debug("getWeChatAppletCode and url :{} \r\n jsonString :{} ", url, jsonString);
        String result = null;
        InputStream inputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
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
        httpRequest.setHeader("Content-type", "application/json; charset=utf-8");
        StringEntity requestParam = new StringEntity(jsonString, "UTF-8");
        requestParam.setContentType("application/json");
        try {
            httpRequest.setEntity(requestParam);
            HttpResponse response = httpClient.execute(httpRequest);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                // 创建一个Buffer字符串
                byte[] buffer = new byte[1024];
                // 每次读取的字符串长度，如果为-1，代表全部读取完毕
                int len = 0;
                // 使用一个输入流从buffer里把数据读取出来
                while ((len = inputStream.read(buffer)) != -1) {
                    // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                    outStream.write(buffer, 0, len);
                }
                // 关闭输入流
                inputStream.close();
                // 把outStream里的数据写入内存
                byteArrayInputStream = new ByteArrayInputStream(outStream.toByteArray());
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        log.debug("getJsonRequestResult and result:{}", result == null ? "" : result);
        return byteArrayInputStream;
    }

    /**
     * 数组的字节输入流转化为base64字符串
     *
     * @param inputStream 输入流
     * @return base64字符串
     */
    public static String getBase64FromInputStream(InputStream inputStream) {
        // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            if (Objects.isNull(inputStream) || inputStream.available() <= 200) {
                log.error("getBase64FromInputStream fail due to inputStream is null");
                return null;
            }
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return java.util.Base64.getEncoder().encodeToString(data);
    }

    /**
     * 获取微信小程序分享码请求地址
     *
     * @param accessToken 微信小程序access_token
     * @return 微信小程序分享码请求地址
     */
    public static String getWeChatAppletShareCodeUrl(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) {
            log.error("getWeChatAppletShareCodeUrl fail due to accessToken is null");
            return null;
        }
        return String.format(GET_WECHAT_APPLET_SHARE_CODE_URL, accessToken);
    }

    /**
     * 获取微信小程序直播列表地址
     *
     * @param accessToken 微信小程序access_token
     * @return 微信小程序直播列表地址
     */
    public static String getWeChatAppletLivePlayerListUrl(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) {
            log.error("getWeChatAppletLivePlayerListUrl fail due to accessToken is null");
            return null;
        }
        return String.format(GET_WECHAT_APPLET_LIVE_PLAYER_LIST_URL, accessToken);
    }

}
