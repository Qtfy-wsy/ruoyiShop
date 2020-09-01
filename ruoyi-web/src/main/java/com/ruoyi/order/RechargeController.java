package com.ruoyi.order;


import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.member.service.RechargeService;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by dujinkai on 2020/2/18.
 * 预存款充值接口
 */
@RestController
@Api(description = "预存款充值接口")
public class RechargeController {

    /**
     * 注入预存款服务
     */
    @Autowired
    private RechargeService rechargeService;


    /**
     * 获取ip方法使用此参数
     */
    private static final String UNKNOWN = "unknown";


    /**
     * 支付宝pc支付
     *
     * @return 返回码和支付宝支付的form 返回码说明  -1:用户不存在 -2:生成充值记录出错 -5:支付宝生成订单出错 -7 没有设置网站地址 -8 缺少配置 1 成功
     */
    @GetMapping("/recharge_toalipagepay")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "BigDecimal", name = "money", value = "充值金额"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "type", value = " 2 h5  4 app 5 pc"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码和支付宝支付的html 返回码说明  -1:用户不存在 -2:生成充值记录出错 -5:支付宝生成订单出错 -7 没有设置网站地址 -8 缺少配置  -9 没有启用  1 成功", response = String.class)
    })
    public AjaxResult toAliPagePay(BigDecimal money, int type, HttpServletRequest request) throws IOException {
        return rechargeService.aliPagePay("", money,type, AppletsLoginUtils.getInstance().getCustomerId(request), CommonConstant.RECHARGE_PAY);
    }

    /**
     * 获取微信扫码支付(pc)参数
     *
     * @return 返回码和扫码支付url 返回码说明  -1:用户不存在 -2:生成充值记录出错 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  1 成功
     */
    @GetMapping("/recharge_wechatqrpayparm")
    @ApiOperation(value = "获取微信扫码支付(pc)参数", notes = "获取微信扫码支付(pc)参数（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "transCode", value = "交易流水号"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "type", value = "1 公众号 2 h5 3 小程序 4 app 5 pc"),
            @ApiImplicitParam(paramType = "form", dataType = "BigDecimal", name = "money", value = "充值金额"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码和扫码支付url 返回码说明  -1:用户不存在 -2:生成充值记录出错 -5:微信生成订单出错 -7 没有设置网站地址 -8 缺少配置  1 成功", response = String.class)
    })
    public AjaxResult wechatQRPayParm(String transCode, int type, BigDecimal money, HttpServletRequest request) {
        return rechargeService.wechatQRPay(transCode, money,type, AppletsLoginUtils.getInstance().getCustomerId(request), getIpAddr(request), CommonConstant.RECHARGE_PAY);
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


}
