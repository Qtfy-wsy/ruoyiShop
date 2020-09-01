package com.ruoyi.pay;


import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.AliPayUtils;
import com.ruoyi.common.utils.WechatUtils;
import com.ruoyi.common.utils.bean.PrepayResult;
import com.ruoyi.common.utils.bean.WechatPayResponse;
import com.ruoyi.order.OrderPayService;
import com.ruoyi.order.vo.PayParam;
import com.ruoyi.order.vo.PrepayParam;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 订单支付控制器
 */
@RestController
@Api(description = "订单支付接口")
@Slf4j
public class OrderPayController {


    /**
     * 获取ip地址用
     */
    private static final String UNKNOWN = "unknown";
    /**
     * 注入订单支付服务
     */
    @Autowired
    private OrderPayService orderPayService;

    /**
     * 获取微信小程序支付参数
     *
     * @param type      1 订单支付 3 门店订单支付
     * @param orderCode 订单code
     * @return 返回码和调起支付需要的参数 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -10没有绑定微信 1 成功
     */
    @RequestMapping("/wechatappletpayparams")
    @ResponseBody
    @ApiOperation(value = "获取微信公众号支付参数", notes = "获取微信公众号支付参数（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderCode", value = "订单code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码和调起支付需要的参数 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -10没有绑定微信 1 成功", response = PrepayResult.class)
    })
    public AjaxResult wechatAppletPayParm(int type, String orderCode, HttpServletRequest request) {
        return AjaxResult.success(orderPayService.wechatAppletPay(orderCode, AppletsLoginUtils.getInstance().getCustomerId(request), getIpAddr(request), type));
    }


    /**
     * 微信支付回调
     */
    @RequestMapping("/wechatnotify")
    @UnAuth
    @ApiIgnore
    public void weChatNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (orderPayService.weChatAppletNotify(request.getInputStream()) > 0) {
            sendMessage(response, WechatUtils.SUCCESS_RETURN);
        }
    }

    /**
     * 预存款支付
     *
     * @param type      1 订单支付 3 门店订单支付
     * @param orderCode 订单code (可能为主订单号也可能为子订单号)
     * @param password  支付密码
     * @return 返回码说明  -1:用户不存在 -2:支付密码错误 -3:没有待支付的订单 -4:用户预存款金额不足 -6:没有设置支付密码 1 成功
     */
    @RequestMapping("/toprepay")
    @ResponseBody
    @ApiOperation(value = "预存款支付", notes = "预存款支付（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderCode", value = "订单code"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "password", value = "支付密码"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码说明  -1:用户不存在 -2:支付密码错误 -3:没有待支付的订单 -4:用户预存款金额不足 -6:没有设置支付密码 1 成功", response = Integer.class)
    })
    public AjaxResult toPrePay(@RequestBody PrepayParam prepayParam, HttpServletRequest request) {
        return AjaxResult.success(orderPayService.predepositPay(prepayParam.getOrderCode(), prepayParam.getPassword(), AppletsLoginUtils.getInstance().getCustomerId(request), prepayParam.getType()));
    }

    /**
     * 支付宝h5支付
     *
     * @param type      支付类型 1 订单支付 3 门店订单支付
     * @param orderCode 订单code
     * @param orderType 订单类型
     * @param orderId   订单id
     */
    @GetMapping("/toaliwappay")
    @ApiOperation(value = "支付宝h5支付", notes = "支付宝h5支付（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderType", value = "订单类型"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderId", value = "订单id"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderCode", value = "订单code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = " 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:支付宝生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功", response = String.class)
    })
    public AjaxResult toAliWapPay(PayParam payParam, HttpServletRequest request) throws IOException {
        return AjaxResult.success(orderPayService.aliWapPay(payParam.getOrderCode(), AppletsLoginUtils.getInstance().getCustomerId(request), payParam.getType(), payParam.getOrderType(), payParam.getOrderId()));
    }

    /**
     * 获取微信H5支付参数
     *
     * @param type      1 订单支付 3 门店订单支付
     * @param orderCode 订单code
     * @param orderType 订单类型
     * @param orderId   订单id
     * @return 返回码和扫码支付url 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  1 成功
     */
    @GetMapping("/wechath5payparm")
    @ApiOperation(value = "获取微信H5支付参数", notes = "获取微信H5支付参数（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderType", value = "订单类型"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderId", value = "订单id"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderCode", value = "订单code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "微信支付返回实体", response = WechatPayResponse.class)
    })
    public AjaxResult wechatH5PayParm(PayParam payParam, HttpServletRequest request) {
        return AjaxResult.success(orderPayService.wechatH5Pay(payParam.getOrderCode(), AppletsLoginUtils.getInstance().getCustomerId(request), getIpAddr(request), payParam.getType(), payParam.getOrderType(), payParam.getOrderId()));
    }

    /**
     * 获取微信公众号支付参数
     *
     * @param type      1 订单支付 3 门店订单支付
     * @param orderCode 订单code
     * @return 返回码和调起支付需要的参数 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  -10没有绑定微信 1 成功
     */
    @GetMapping("/wechatofficialaccountpayparm")
    @ApiOperation(value = "获取微信公众号支付参数", notes = "获取微信公众号支付参数（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderCode", value = "订单code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "下单接口返回实体类", response = PrepayResult.class)
    })
    public AjaxResult wechatOfficialAccountPayParm( PayParam payParam, HttpServletRequest request) {
        return AjaxResult.success(orderPayService.wechatOfficialAccountPay(payParam.getOrderCode(), AppletsLoginUtils.getInstance().getCustomerId(request), getIpAddr(request), payParam.getType()));
    }

    /**
     * 支付宝pc支付
     *
     * @param type      支付方式 1 订单 3 门店订单
     * @param orderCode 订单code
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderCode", value = "订单code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = " 返回码和支付宝支付的html 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:支付宝生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用 1 成功", response = String.class)
    })
    @GetMapping("/toalipagepay")
    @ApiOperation(value = "支付宝pc支付", notes = "支付宝pc支付（需要认证）")
    public AjaxResult toAliPagePay(PayParam payParam, HttpServletRequest request) throws IOException {
        return AjaxResult.success(orderPayService.aliPagePay(payParam.getOrderCode(), AppletsLoginUtils.getInstance().getCustomerId(request), payParam.getType()));
    }
    /**
     * 获取微信扫码支付(pc)参数
     *
     * @param type      支付类型 1 订单  3 门店订单
     * @param orderCode 订单code
     * @return 返回码和扫码支付url 返回码说明  -1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  1 成功
     */
    @GetMapping("/wechatqrpayparm")
    @ApiOperation(value = "获取微信扫码支付(pc)参数", notes = "获取微信扫码支付(pc)参数（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "orderCode", value = "订单code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "-1:用户不存在 -3:没有待支付的订单 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  1 成功", response = WechatPayResponse.class)
    })
    public AjaxResult wechatQRPayParm(PayParam payParam,  HttpServletRequest request) {
        return AjaxResult.success(orderPayService.wechatQRPay(payParam.getOrderCode(), AppletsLoginUtils.getInstance().getCustomerId(request), getIpAddr(request), payParam.getType()));
    }

    /**
     * APP支付宝支付
     *
     * @param orderCode 订单code
     * @param type      1 订单支付 3 门店订单支付
     * @return 返回支付宝的url
     */
    @ApiOperation(value = "支付宝支付", notes = "支付宝支付（需要认证）")
    @RequestMapping(value = "/alipay", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "orderCode", value = "订单code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回支付宝的url", response = String.class)
    })
    public AjaxResult aliPay(PayParam payParam,  HttpServletRequest request) {
        return AjaxResult.success(orderPayService.aliAppPay(payParam.getOrderCode(), AppletsLoginUtils.getInstance().getCustomerId(request), payParam.getType()));
    }

    /**
     * APP微信支付
     *
     * @param orderCode 订单code
     * @param type      1 订单支付 3 门店订单支付
     * @return 返回微信
     */
    @ApiOperation(value = "微信支付", notes = "微信支付（需要认证）")
    @RequestMapping(value = "/wxpay", method = RequestMethod.GET)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "type", value = "1 订单支付 3 门店订单支付"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "orderCode", value = "订单code"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回微信", response = PrepayResult.class)
    })
    public AjaxResult wxPay(PayParam payParam,  HttpServletRequest request) {
        return AjaxResult.success(orderPayService.wechatAppPay(payParam.getOrderCode(), AppletsLoginUtils.getInstance().getCustomerId(request), getIpAddr(request), payParam.getType()));
    }
    /**
     * 支付宝支付回调
     */
    @PostMapping("/alipaynotify")
    @UnAuth
    @ApiIgnore
    public void aliPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (orderPayService.aliPayNotify(request.getParameterMap()) > 0) {
            sendMessage(response, AliPayUtils.SUCCESS);
        }
    }

    /**
     * 获取ip地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.indexOf(",") > -1) {
            ip = ip.split(",")[0];
        }
        return ip;
    }


    /**
     * 回传信息
     *
     * @param message 信息
     */
    private void sendMessage(HttpServletResponse response, String message) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.println(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }


}
