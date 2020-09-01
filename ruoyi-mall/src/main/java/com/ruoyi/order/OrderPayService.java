package com.ruoyi.order;


import com.ruoyi.common.utils.bean.PrepayResult;
import com.ruoyi.common.utils.bean.WechatPayResponse;
import com.ruoyi.util.CommonResponse;

import java.io.InputStream;
import java.util.Map;

/**
 * Created by 魔金商城 on 17/11/10.
 * 订单支付接口
 */
public interface OrderPayService {

    /**
     * 预存款支付
     *
     * @param orderCode  订单code (可能为主订单号也可能为子订单号)
     * @param password   支付密码
     * @param customerId 用户id
     * @param type       1 订单支付 3 门店订单支付
     * @return 返回码说明  -1:用户不存在 -2:支付密码错误 -3:没有待支付的订单 -4:用户预存款金额不足 -6:没有设置支付密码  -9 没有启用 1 成功
     */
    int predepositPay(String orderCode, String password, long customerId, int type);

    /**
     * 支付宝支付app支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param type       支付类型 1:订单支付 2:预存款充值 3 : 门店订单支付
     * @return 返回支付宝支付的html
     */
    CommonResponse<String> aliAppPay(String orderCode, long customerId, int type);

    /**
     * 支付宝pc支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:支付宝生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功
     */
    CommonResponse<String> aliPagePay(String orderCode, long customerId, int type);

    /**
     * 支付宝wap支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @param orderType  订单类型 主要是社区团购使用
     * @param orderId    订单id
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:支付宝生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功
     */
    CommonResponse<String> aliWapPay(String orderCode, long customerId, int type, String orderType, String orderId);

    /**
     * 微信扫码支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param ip         请求真实ip
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功
     */
    CommonResponse<WechatPayResponse> wechatQRPay(String orderCode, long customerId, String ip, int type);

    /**
     * 微信H5支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param ip         请求真实ip
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功
     */
    CommonResponse<WechatPayResponse> wechatH5Pay(String orderCode, long customerId, String ip, int type, String orderType, String orderId);

    /**
     * 微信公众号支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param ip         请求真实ip
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功
     */
    CommonResponse<PrepayResult> wechatOfficialAccountPay(String orderCode, long customerId, String ip, int type);


    /**
     * 微信小程序支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param ip         请求真实ip
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功
     */
    CommonResponse<PrepayResult> wechatAppletPay(String orderCode, long customerId, String ip, int type);

    /**
     * 微信app支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param ip         请求真实ip
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功
     */
    CommonResponse<PrepayResult> wechatAppPay(String orderCode, long customerId, String ip, int type);

    /**
     * 银联支付
     *
     * @param orderCode  订单code
     * @param customerId 用户id
     * @param type       支付类型 1:订单支付 2:预存款充值
     * @return 返回码和银联支付的form 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:银联生成订单出错 -7 没有设置网站地址 -8 缺少配置 -9 没有启用 1 成功
     */
    CommonResponse<String> unionPagePay(String orderCode, long customerId, int type);

    /**
     * 支付宝回调
     *
     * @param requestParams 支付宝回调参数
     * @return 返回码 1:成功 -1:没有订单
     */
    int aliPayNotify(Map requestParams);

    /**
     * 微信回调
     *
     * @param inputStream 微信回调参数
     * @return 返回码 1:成功 -1:没有订单
     */
    int weChatNotify(InputStream inputStream);


    /**
     * 微信回调(app) 微信app回调需要独立 和h5公众号 扫码不一样
     *
     * @param inputStream 微信回调参数
     * @return 返回码 1:成功 -1:没有订单
     */
    int weChatAppNotify(InputStream inputStream);

    /**
     * 微信回调(小程序) 回调
     *
     * @param inputStream 微信回调参数
     * @return 返回码 1:成功 -1:没有订单
     */
    int weChatAppletNotify(InputStream inputStream);

    /**
     * 银联回调
     *
     * @param requestParams 支付宝回调参数
     * @return 返回码 1:成功 -1:没有订单
     */
    int unionPayNotify(Map requestParams);


}
