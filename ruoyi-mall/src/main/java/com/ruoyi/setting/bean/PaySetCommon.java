package com.ruoyi.setting.bean;


import java.util.List;
import java.util.Objects;

/**
 * 支付设置统一类
 *
 * @author 伊甸园商城 on 2017/5/17.
 */

public class PaySetCommon {

    /**
     * 阿里支付
     */
    private AliPaySet aliPaySet = new AliPaySet();
    /**
     * 微信支付(公众号、扫码、H5)
     */

    private WechatPaySet wechatPaySet = new WechatPaySet();
    /**
     * 微信支付(app)
     */

    private WechatPaySet wechatAppPaySet = new WechatPaySet();

    /**
     * 微信支付(小程序支付）
     */

    private WechatPaySet wechatAppletPaySet = new WechatPaySet();

    /**
     * 银联支付
     */
    private UnionPaySet unionPaySet = new UnionPaySet();

    /**
     * 预存款支付
     */
    private PrePaySet prePaySet = new PrePaySet();

    /**
     * 用于组装支付设置对象在前端显示
     *
     * @param paySetCommon 支付设置对象
     * @param paySets      数据库映射对象
     * @return 支付设置对象
     */
    public static PaySetCommon getPaySetCommon(PaySetCommon paySetCommon, List<PaySet> paySets) {
        paySets.forEach(paySet -> {
            String value = paySet.getColumnValue();
            //支付宝
            if ("1".equals(paySet.getCodeType())) {
                if ("appId".equals(paySet.getColumnName())) {
                    paySetCommon.aliPaySet.setAppId(value);
                }
                if ("alipayPublicKey".equals(paySet.getColumnName())) {
                    paySetCommon.aliPaySet.setAlipayPublicKey(value);
                }
                if ("appPrivateKey".equals(paySet.getColumnName())) {
                    paySetCommon.aliPaySet.setAppPrivateKey(value);
                }
                if ("isUse".equals(paySet.getColumnName())) {
                    paySetCommon.aliPaySet.setIsUse(value);
                }
            }
            //微信(公众号、扫码、H5)
            if ("2".equals(paySet.getCodeType())) {
                if ("appId".equals(paySet.getColumnName())) {
                    paySetCommon.wechatPaySet.setAppId(value);
                }
                if ("appSecret".equals(paySet.getColumnName())) {
                    paySetCommon.wechatPaySet.setAppSecret(value);
                }
                if ("merchantNum".equals(paySet.getColumnName())) {
                    paySetCommon.wechatPaySet.setMerchantNum(value);
                }
                if ("apiKey".equals(paySet.getColumnName())) {
                    paySetCommon.wechatPaySet.setApiKey(value);
                }
                if ("loginNotice".equals(paySet.getColumnName())) {
                    paySetCommon.wechatPaySet.setLoginNotice(value);
                }
                if ("isUse".equals(paySet.getColumnName())) {
                    paySetCommon.wechatPaySet.setIsUse(value);
                }
            }
            //银联
            if ("3".equals(paySet.getCodeType())) {
                if ("merchantNum".equals(paySet.getColumnName())) {
                    paySetCommon.unionPaySet.setMerchantNum(value);
                }
                if ("apiKey".equals(paySet.getColumnName())) {
                    paySetCommon.unionPaySet.setApiKey(value);
                }

                if ("isUse".equals(paySet.getColumnName())) {
                    paySetCommon.unionPaySet.setIsUse(value);
                }
            }
            //预存款
            if ("4".equals(paySet.getCodeType())) {
                if ("isUse".equals(paySet.getColumnName())) {
                    paySetCommon.prePaySet.setIsUse(value);
                }
            }
            //微信支付(app)
            if ("5".equals(paySet.getCodeType())) {
                if ("appId".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppPaySet.setAppId(value);
                }
                if ("appSecret".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppPaySet.setAppSecret(value);
                }
                if ("merchantNum".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppPaySet.setMerchantNum(value);
                }
                if ("apiKey".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppPaySet.setApiKey(value);
                }
                if ("isUse".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppPaySet.setIsUse(value);
                }
            }

            //微信支付(小程序)
            if ("6".equals(paySet.getCodeType())) {
                if ("appId".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppletPaySet.setAppId(value);
                }
                if ("appSecret".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppletPaySet.setAppSecret(value);
                }
                if ("merchantNum".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppletPaySet.setMerchantNum(value);
                }
                if ("apiKey".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppletPaySet.setApiKey(value);
                }
                if ("isUse".equals(paySet.getColumnName())) {
                    paySetCommon.wechatAppletPaySet.setIsUse(value);
                }
            }
        });
        return paySetCommon;
    }

    /**
     * 清除敏感 信息
     *
     * @return 返回当前对象
     */
    public PaySetCommon clearSensitiveInfo() {

        if (Objects.nonNull(this.wechatPaySet)) {
            wechatPaySet.clearSensitiveInfo();
        }

        if (Objects.nonNull(this.wechatAppPaySet)) {
            wechatAppPaySet.clearSensitiveInfo();
        }

        if (Objects.nonNull(this.wechatAppletPaySet)) {
            wechatAppletPaySet.clearSensitiveInfo();
        }

        if (Objects.nonNull(this.aliPaySet)) {
            aliPaySet.clearSensitiveInfo();
        }


        if (Objects.nonNull(this.unionPaySet)) {
            unionPaySet.clearSensitiveInfo();
        }
        return this;
    }

    public AliPaySet getAliPaySet() {
        return aliPaySet;
    }

    public void setAliPaySet(AliPaySet aliPaySet) {
        this.aliPaySet = aliPaySet;
    }

    public WechatPaySet getWechatPaySet() {
        return wechatPaySet;
    }

    public void setWechatPaySet(WechatPaySet wechatPaySet) {
        this.wechatPaySet = wechatPaySet;
    }

    public WechatPaySet getWechatAppPaySet() {
        return wechatAppPaySet;
    }

    public void setWechatAppPaySet(WechatPaySet wechatAppPaySet) {
        this.wechatAppPaySet = wechatAppPaySet;
    }

    public WechatPaySet getWechatAppletPaySet() {
        return wechatAppletPaySet;
    }

    public void setWechatAppletPaySet(WechatPaySet wechatAppletPaySet) {
        this.wechatAppletPaySet = wechatAppletPaySet;
    }

    public UnionPaySet getUnionPaySet() {
        return unionPaySet;
    }

    public void setUnionPaySet(UnionPaySet unionPaySet) {
        this.unionPaySet = unionPaySet;
    }

    public PrePaySet getPrePaySet() {
        return prePaySet;
    }

    public void setPrePaySet(PrePaySet prePaySet) {
        this.prePaySet = prePaySet;
    }
}
