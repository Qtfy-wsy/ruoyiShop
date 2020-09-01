package com.ruoyi.common.utils.bean;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 支付宝设置实体类
 */
@Data
public class AliPaySetting {
    /**
     * APPID 即创建应用后生成
     */
    private String appId;
    /**
     * 开发者私钥，由开发者自己生成
     */
    private String appPrivateKey;
    /**
     * 支付宝公钥，由支付宝生成
     */
    private String alipayPublicKey;
    /**
     * 前台回调地址
     */
    private String beforeCallbackUrl;
    /**
     * 后台回调地址
     */
    private String backCallbackUrl;

    /**
     * 构造阿里支付实体
     *
     * @param beforeCallbackUrl 前台回调地址
     * @param backCallbackUrl   后台回调地址
     * @return 返回支付宝配置信息
     */
    public static AliPaySetting build(String beforeCallbackUrl, String backCallbackUrl) {
        AliPaySetting aliPaySetting = new AliPaySetting();

        //前台同步回调地址
        aliPaySetting.setBeforeCallbackUrl(beforeCallbackUrl);
        //异步回调地址
        aliPaySetting.setBackCallbackUrl(backCallbackUrl);

        return aliPaySetting;
    }

    /**
     * 检测支付时的参数
     */
    public boolean checkPayParams() {
        return !StringUtils.isEmpty(appId) && !StringUtils.isEmpty(appPrivateKey) &&
                !StringUtils.isEmpty(alipayPublicKey);
    }

}
