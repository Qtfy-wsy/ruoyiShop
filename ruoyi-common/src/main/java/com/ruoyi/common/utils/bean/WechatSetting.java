package com.ruoyi.common.utils.bean;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 微信设置实体类
 */
@Data
public class WechatSetting {
    /**
     * 公众号appId(必传)
     */
    private String appId;
    /**
     * AppSecret(必传)
     */
    private String appSecret;
    /**
     * 商户号（支付时必传）
     */
    private String merchantNum;
    /**
     * API密钥（支付时必传）
     */
    private String apiKey;
    /**
     * 支付回调（支付时必传）
     */
    private String payCallback;
    /**
     * 网页授权回调地址(授权时必传)
     */
    private String url;
    /**
     * 网站域名 (H5支付时必传）
     */
    private String siteUrl;

    /**
     * 网站名称（H5支付时必传）
     */
    private String siteName;

    /**
     * 终端ip地址
     */
    private String ip;

    /**
     * 支付方式 1:公众号支付（openid必传） 2:H5支付 3:扫码支付 4:APP支付 5:小程序支付（openId必传）
     */
    private int type;

    /**
     * 公众号支付必传 openid（微信公众号支付）
     */
    private String openId;

    /**
     * 构造微信支付参数实体
     *
     * @param ip          终端ip
     * @param type        支付方式 1:公众号支付（openid必传） 2:H5支付 3:扫码支付 4:APP支付
     * @param callBackUrl 微信异步回调地址
     * @return 返回构造微信支付参数实体
     */
    public static WechatSetting build(String ip, int type, String callBackUrl) {
        WechatSetting wechatSetting = new WechatSetting();
        wechatSetting.ip = ip;
        wechatSetting.type = type;
        wechatSetting.payCallback = callBackUrl;
        return wechatSetting;
    }

    /**
     * 是否是app支付
     *
     * @return app支付返回true  否则返回false
     */
    public boolean isAppPay() {
        return 4 == this.type;
    }

    /**
     * 是否是h5支付
     *
     * @return h5支付返回true 否则返回false
     */
    public boolean isH5Pay() {
        return 2 == this.type;
    }

    /**
     * 是否是扫码支付
     *
     * @return 扫码支付返回true  否则返回false
     */
    public boolean isQRPay() {
        return 3 == this.type;
    }

    /**
     * 是否是公众号支付
     *
     * @return 公众号支付返回true 否则返回false
     */
    public boolean isOfficialAccountPay() {
        return 1 == this.type;
    }

    /**
     * 是否是小程序支付
     *
     * @return 小程序支付返回true 否则返回false
     */
    public boolean isAppletPay() {
        return 5 == this.type;
    }

    /**
     * 检测支付时的参数
     */
    public boolean checkPayParams() {
        return !StringUtils.isEmpty(appId) && !StringUtils.isEmpty(appSecret) &&
                !StringUtils.isEmpty(merchantNum) && !StringUtils.isEmpty(apiKey);
    }

    /**
     * 增加openid
     *
     * @param openId 公众号支付必传 openid（微信公众号支付）
     * @return 返回当前对象
     */
    public WechatSetting addOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    /**
     * 增加网站名称
     *
     * @param siteName 网站名称
     * @return 返回当前对象
     */
    public WechatSetting addSiteName(String siteName) {
        this.siteName = siteName;
        return this;
    }

    /**
     * 增加网站域名
     *
     * @param siteUrl 网站域名
     * @return 返回当前对象
     */
    public WechatSetting addSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
        return this;
    }

    /**
     * 检测H5支付时的参数
     */
    public boolean checkH5PayParams() {
        return !StringUtils.isEmpty(siteUrl) && !StringUtils.isEmpty(siteName) && checkPayParams();
    }

    /**
     * 检测授权时的参数
     */
    public boolean checkOAuthParams() {
        return !StringUtils.isEmpty(appId) && !StringUtils.isEmpty(appSecret) &&
                !StringUtils.isEmpty(url);
    }

    /**
     * 检测小程序授权时的参数
     */
    public boolean checkAppletOAuthParams() {
        return !StringUtils.isEmpty(appId) && !StringUtils.isEmpty(appSecret);
    }

}
