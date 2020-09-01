package com.ruoyi.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.appletsutil.Claims;
import com.ruoyi.appletsutil.ResultCode;
import com.ruoyi.appletsutil.UnAuthorizedException;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SnowflakeIdWorker;
import com.ruoyi.common.utils.WeChatAppletUtils;
import com.ruoyi.common.utils.bean.WeChatAppletLoginResponse;
import com.ruoyi.common.utils.bean.WeChatAppletUserInfo;
import com.ruoyi.common.utils.bean.WechatSetting;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.domain.WeChatCustomerLink;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.service.LoginService;
import com.ruoyi.member.service.WeChatAppletLoginService;
import com.ruoyi.member.service.WeChatCustomerLinkService;
import com.ruoyi.member.vo.AppletLoginInfo;
import com.ruoyi.member.vo.AppletLoginRedisParamResponse;
import com.ruoyi.member.vo.LoginParams;
import com.ruoyi.setting.bean.WechatPaySet;
import com.ruoyi.setting.service.ILsPaySettingService;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 登录控制器
 *
 * @author SK
 * @since 2018/6/13
 */
@Controller
@Slf4j
@Api(description = "登录接口")
public class LoginController {

    /**
     * 注入微信小程序登录服务
     */
    @Autowired
    private WeChatAppletLoginService weChatAppletLoginService;

    /**
     * 注入redis服务
     */
    @Autowired
    private RedisCache redisService;

    /**
     * jwt密钥
     */
    @Value("${token.secret}")
    private String jwtSecretKey;

    /**
     * 注入登录处理service
     */
    @Resource(name = "loginService")
    private LoginService loginService;

    /**
     * 注入微信用户关联服务
     */
    @Autowired
    private WeChatCustomerLinkService weChatCustomerLinkService;
    /**
     * 注入随机数生成器
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    /**
     * 注入支付设置服务
     */
    @Autowired
    private ILsPaySettingService paySetService;
    @Autowired
    private IUmsMemberService memberService;
    /**
     * 会员登录
     *
     * @param loginParams 登录参数
     * @return -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    @PostMapping("/loginByPassword")
    @UnAuth
    @ResponseBody
    @ApiOperation(value = "会员登录", notes = "会员登录（不需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "result: -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误  token:放登录成功后的token", response = Map.class)
    })
    public AjaxResult loginByPassword(@RequestBody LoginParams loginParams, HttpServletRequest request) {
        // 登录结果
        return loginService.login(loginParams);
    }
    /**
     * 会员登录
     *
     * @param loginParams 登录参数
     * @return -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    @PostMapping("/logout")
    @UnAuth
    @ResponseBody
    @ApiOperation(value = "会员登录", notes = "会员登录（不需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "result: -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误  token:放登录成功后的token", response = Map.class)
    })
    public AjaxResult logout(@RequestBody LoginParams loginParams, HttpServletRequest request) {
        AppletLoginRedisParamResponse appletLoginRedisParamResponse = JSON.parseObject(redisService.getValue(getToken(request)),AppletLoginRedisParamResponse.class) ;
        redisService.deleteObject(getToken(request));
       // redisService.deleteObject(appletLoginRedisParamResponse.);
        return AjaxResult.success();
    }
    /**
     * 小程序获取登录信息
     *
     * @param  用户登录凭证（有效期五分钟）。开发者需要在开发者服务器后台调用 api，使用 code 换取 openid 和 session_key 等信息
     * @return 登录信息
     */
    @UnAuth
    @ResponseBody
    @RequestMapping("/getOpenId")
    @ApiOperation(value = "小程序获取登录信息", notes = "小程序获取登录信息（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "用户登录凭证"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录信息", response = AppletLoginInfo.class)
    })
    public AjaxResult getOpenId(@RequestBody  AppletLoginRedisParamResponse appletLoginRedisParamResponse , HttpServletRequest request) {

        WechatPaySet wechatAppletPaySet = paySetService.queryPaySet().getWechatAppletPaySet();
        WechatSetting wechatSetting = new WechatSetting();
        wechatSetting.setAppId(wechatAppletPaySet.getAppId());
        wechatSetting.setAppSecret(wechatAppletPaySet.getAppSecret());
        if (!wechatSetting.checkAppletOAuthParams()) {
            log.error("getLoginInfo fail:checkAppletOAuthParams fail");
            return AjaxResult.error("getLoginInfo fail:checkAppletOAuthParams fail");
        }
        WeChatAppletLoginResponse weChatAppletLoginResponse = WeChatAppletUtils.getLoginInfo(appletLoginRedisParamResponse.getCode(), wechatSetting);
        if (Objects.isNull(weChatAppletLoginResponse)) {
            log.error("getLoginInfo fail: getLoginInfo fail");
            return AjaxResult.error("getLoginInfo fail: getLoginInfo fail");
        }
       UmsMember member = new UmsMember();
        member.setAppletOpenId(weChatAppletLoginResponse.getOpenid());
        long customerId = 0;
        if (!ObjectUtils.isEmpty(getClaims(getToken(request)))) {
            customerId = getClaims(getToken(request)).getCustomerId();
        }
        member.setId(customerId);
        return AjaxResult.success(memberService.updateUmsMember(member));
    }
    /**
     * 小程序获取登录信息
     *
     * @param  用户登录凭证（有效期五分钟）。开发者需要在开发者服务器后台调用 api，使用 code 换取 openid 和 session_key 等信息
     * @return 登录信息
     */
    @UnAuth
    @ResponseBody
    @RequestMapping("/mpWechatLogin")
    @ApiOperation(value = "小程序获取登录信息", notes = "小程序获取登录信息（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "用户登录凭证"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录信息", response = AppletLoginInfo.class)
    })
    public AjaxResult mpWechatLogin(@RequestBody  AppletLoginRedisParamResponse appletLoginRedisParamResponse , HttpServletRequest request) {
        final StringBuilder sb = new StringBuilder();
        Map<String, Object> res = new HashMap<>();
        String token = snowflakeIdWorker.nextId() + "";
        appletLoginRedisParamResponse.setToken(token);
        WechatPaySet wechatAppletPaySet = paySetService.queryPaySet().getWechatAppletPaySet();
        WechatSetting wechatSetting = new WechatSetting();
        wechatSetting.setAppId(wechatAppletPaySet.getAppId());
        wechatSetting.setAppSecret(wechatAppletPaySet.getAppSecret());
        if (!wechatSetting.checkAppletOAuthParams()) {
            log.error("getLoginInfo fail:checkAppletOAuthParams fail");
            return AjaxResult.error("getLoginInfo fail:checkAppletOAuthParams fail");
        }
        WeChatAppletLoginResponse weChatAppletLoginResponse = WeChatAppletUtils.getLoginInfo(appletLoginRedisParamResponse.getCode(), wechatSetting);
        if (Objects.isNull(weChatAppletLoginResponse)) {
            log.error("getLoginInfo fail: getLoginInfo fail");
            return AjaxResult.error("getLoginInfo fail: getLoginInfo fail");
        }
        appletLoginRedisParamResponse.setSessionKey(weChatAppletLoginResponse.getSession_key());
        appletLoginRedisParamResponse.setOpenId(weChatAppletLoginResponse.getOpenid());
        UmsMember member =memberService.queryCustomerByappletOpenId(weChatAppletLoginResponse.getOpenid());
        if (member!=null){
            appletLoginRedisParamResponse.setCustomerId(member.getId());
            appletLoginRedisParamResponse.setToken(sb.toString());
            redisService.putToRedis(sb.toString(), JSON.toJSONString(appletLoginRedisParamResponse));
         //   redisService.putToRedis(loginParams.getMobile(), sb.toString());
            res.put("access_token", sb.toString());
            res.put("refresh_token", sb.toString());
            res.put("member", member);
        }else {
            member = new UmsMember();
            member.setSource("5");
            member.setMobile("123456789");
            member.setAppletOpenId(weChatAppletLoginResponse.getOpenid());
            member.setPassword("123456");
            memberService.addCustomer(member);
            redisService.putToRedis(sb.toString(), JSON.toJSONString(appletLoginRedisParamResponse));
            //   redisService.putToRedis(loginParams.getMobile(), sb.toString());
            res.put("access_token", sb.toString());
            res.put("refresh_token", sb.toString());
            res.put("member", member);
        }
        return AjaxResult.success(res);

    }
    /**
     * 小程序获取登录信息
     *
     * @param code 用户登录凭证（有效期五分钟）。开发者需要在开发者服务器后台调用 api，使用 code 换取 openid 和 session_key 等信息
     * @return 登录信息
     */
    @UnAuth
    @ResponseBody
    @RequestMapping("/getlogininfo")
    @ApiOperation(value = "小程序获取登录信息", notes = "小程序获取登录信息（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "用户登录凭证"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "登录信息", response = AppletLoginInfo.class)
    })
    public AppletLoginInfo getLoginInfo(String code, HttpServletRequest request) {
        String unionId = null;
        if (!StringUtils.isEmpty(getToken(request))) {
            unionId = Objects.isNull(getClaims(getToken(request))) ? null : getClaims(getToken(request)).getUnionId();
            redisService.delValue(getToken(request));
        }
        return weChatAppletLoginService.getLoginInfo(code, unionId, appletLoginRedisParamResponse -> {
            redisService.putToRedis(appletLoginRedisParamResponse.getToken(), JSON.toJSONString(appletLoginRedisParamResponse));
            if (appletLoginRedisParamResponse.hasUnionId()) {
                redisService.putToRedis(appletLoginRedisParamResponse.getUnionId(), appletLoginRedisParamResponse.getToken());
            }
        });
    }

    /**
     * 处理用户信息
     *
     * @param weChatAppletUserInfo 小程序用户信息实体
     * @return 登录信息
     */
    @UnAuth
    @ResponseBody
    @RequestMapping("/dealuserinfo")
    @ApiOperation(value = "处理用户信息", notes = "处理用户信息（不需要认证）", httpMethod = "POST")
    public AppletLoginInfo dealUserInfo(WeChatAppletUserInfo weChatAppletUserInfo, HttpServletRequest request) {
        String token = getToken(request);
        Claims claims = getClaims(token);
        return weChatAppletLoginService.dealUserInfo(weChatAppletUserInfo, claims.getOpenId(), claims.getSessionKey(), appletLoginRedisParamResponse -> afterDealUserInfo(claims, appletLoginRedisParamResponse, token));
    }

    /**
     * 绑定账号
     *
     * @param loginParams 登录参数
     */
    @RequestMapping("/bindaccount")
    @ResponseBody
    @UnAuth
    @ApiOperation(value = "绑定账号", notes = "绑定账号（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "username", value = "用户名"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "password", value = "密码"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码  -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误", response = Integer.class)
    })
    public AjaxResult bindAccount(@ApiIgnore LoginParams loginParams, HttpServletRequest request) {
        //回调
        loginParams.setConsumer(customer ->
                alterLoginSuccess(customer.getId(), request)
        );
        return AjaxResult.success(loginService.login(loginParams));
    }


    /**
     * 解绑账号
     *
     * @return 1成功
     */
    @RequestMapping("/unbindaccount")
    @ResponseBody
    @ApiOperation(value = "解绑账号", notes = "解绑账号（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "1成功", response = Integer.class)
    })
    public int unbindAccount(HttpServletRequest request) {
        weChatAppletLoginService.unbindAccount(AppletsLoginUtils.getInstance().getCustomerId(request), AppletsLoginUtils.getInstance().getUnionId(request));
        Claims claims = AppletsLoginUtils.getInstance().getClaims(request);
        claims.setCustomerId(CommonConstant.NO_CUSTOMER_ID);
        redisService.putToRedis(getToken(request), JSONObject.toJSONString(claims));
        return 1;
    }


    /**
     * 获取token
     *
     * @param request request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        // 认证信息在header 中的key
        final String authHeader = request.getHeader("Authorization");

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer")) {
            log.info("getClaims fail :Authorization fail ");
            throw new UnAuthorizedException(ResultCode.WX_NOT_AUTHORIZED);
        }
        return authHeader.length() >= 7 ? authHeader.substring(7) : authHeader.substring(6);
    }

    /**
     * 获取小程序凭证实体
     *
     * @param token token
     * @return 小程序凭证实体
     */
    private Claims getClaims(String token) {
        String claims = redisService.getValue(token);
        return StringUtils.isEmpty(claims) ? null : JSONObject.parseObject(redisService.getValue(token), Claims.class);
    }

    /**
     * 处理小程序凭证信息
     *
     * @param claims                        小程序凭证实体
     * @param appletLoginRedisParamResponse 小程序redis参数返回实体
     * @param token                         token
     */
    private void afterDealUserInfo(Claims claims, AppletLoginRedisParamResponse appletLoginRedisParamResponse, String token) {
        if (Objects.nonNull(appletLoginRedisParamResponse)) {
            claims.setCustomerId(appletLoginRedisParamResponse.getCustomerId());
            claims.setUnionId(appletLoginRedisParamResponse.getUnionId());
            redisService.putToRedis(token, JSON.toJSONString(claims));
            if (appletLoginRedisParamResponse.hasUnionId()) {
                redisService.putToRedis(appletLoginRedisParamResponse.getUnionId(), token);
            }
        } else {
            log.error("afterDealUserInfo fail:appletLoginRedisParamResponse is null");
        }
    }

    /**
     * 登录成功后处理的事
     *
     * @param customerId 用户id
     */
    private void alterLoginSuccess(long customerId, HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(customerId)) {
            Claims claims = getClaims(getToken(request));
            // 校验有没有uid  如果没有uid  则直接返回
            if (!claims.hasUnionId()) {
                log.error("applets has not authorized");
                throw new UnAuthorizedException(ResultCode.WX_NOT_AUTHORIZED);
            }
            //绑定账号
            try {
                weChatAppletLoginService.bindAccount(claims.getOpenId(), claims.getUnionId(), customerId);
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                //捕获异常，可能已经绑定过账号
                WeChatCustomerLink weChatCustomerLink = weChatCustomerLinkService.queryWeChatCustomerLinkByUnionId(claims.getUnionId());
                if (Objects.nonNull(weChatCustomerLink)) {
                    customerId = weChatCustomerLink.getCustomerId();
                } else {
                    customerId = CommonConstant.NO_CUSTOMER_ID;
                }
            }
            if (customerId == CommonConstant.NO_CUSTOMER_ID) {
                claims.setCustomerId(customerId);
                redisService.putToRedis(getToken(request), JSONObject.toJSONString(claims));
            }
        }
    }
}
