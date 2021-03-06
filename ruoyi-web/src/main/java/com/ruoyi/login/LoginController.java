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
 * ???????????????
 *
 * @author SK
 * @since 2018/6/13
 */
@Controller
@Slf4j
@Api(description = "????????????")
public class LoginController {

    /**
     * ?????????????????????????????????
     */
    @Autowired
    private WeChatAppletLoginService weChatAppletLoginService;

    /**
     * ??????redis??????
     */
    @Autowired
    private RedisCache redisService;

    /**
     * jwt??????
     */
    @Value("${token.secret}")
    private String jwtSecretKey;

    /**
     * ??????????????????service
     */
    @Resource(name = "loginService")
    private LoginService loginService;

    /**
     * ??????????????????????????????
     */
    @Autowired
    private WeChatCustomerLinkService weChatCustomerLinkService;
    /**
     * ????????????????????????
     */
    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    /**
     * ????????????????????????
     */
    @Autowired
    private ILsPaySettingService paySetService;
    @Autowired
    private IUmsMemberService memberService;
    /**
     * ????????????
     *
     * @param loginParams ????????????
     * @return -1 ????????????????????????  -2 ????????????  -3 ???????????? 1 ??????  -4 ???????????????
     */
    @PostMapping("/loginByPassword")
    @UnAuth
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "?????????????????????????????????")
    @ApiResponses({
            @ApiResponse(code = 200, message = "result: -1 ????????????????????????  -2 ????????????  -3 ???????????? 1 ??????  -4 ???????????????  token:?????????????????????token", response = Map.class)
    })
    public AjaxResult loginByPassword(@RequestBody LoginParams loginParams, HttpServletRequest request) {
        // ????????????
        return loginService.login(loginParams);
    }
    /**
     * ????????????
     *
     * @param loginParams ????????????
     * @return -1 ????????????????????????  -2 ????????????  -3 ???????????? 1 ??????  -4 ???????????????
     */
    @PostMapping("/logout")
    @UnAuth
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "?????????????????????????????????")
    @ApiResponses({
            @ApiResponse(code = 200, message = "result: -1 ????????????????????????  -2 ????????????  -3 ???????????? 1 ??????  -4 ???????????????  token:?????????????????????token", response = Map.class)
    })
    public AjaxResult logout(@RequestBody LoginParams loginParams, HttpServletRequest request) {
        AppletLoginRedisParamResponse appletLoginRedisParamResponse = JSON.parseObject(redisService.getValue(getToken(request)),AppletLoginRedisParamResponse.class) ;
        redisService.deleteObject(getToken(request));
       // redisService.deleteObject(appletLoginRedisParamResponse.);
        return AjaxResult.success();
    }
    /**
     * ???????????????????????????
     *
     * @param  ????????????????????????????????????????????????????????????????????????????????????????????? api????????? code ?????? openid ??? session_key ?????????
     * @return ????????????
     */
    @UnAuth
    @ResponseBody
    @RequestMapping("/getOpenId")
    @ApiOperation(value = "???????????????????????????", notes = "????????????????????????????????????????????????", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "??????????????????"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "????????????", response = AppletLoginInfo.class)
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
     * ???????????????????????????
     *
     * @param  ????????????????????????????????????????????????????????????????????????????????????????????? api????????? code ?????? openid ??? session_key ?????????
     * @return ????????????
     */
    @UnAuth
    @ResponseBody
    @RequestMapping("/mpWechatLogin")
    @ApiOperation(value = "???????????????????????????", notes = "????????????????????????????????????????????????", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "??????????????????"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "????????????", response = AppletLoginInfo.class)
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
     * ???????????????????????????
     *
     * @param code ????????????????????????????????????????????????????????????????????????????????????????????? api????????? code ?????? openid ??? session_key ?????????
     * @return ????????????
     */
    @UnAuth
    @ResponseBody
    @RequestMapping("/getlogininfo")
    @ApiOperation(value = "???????????????????????????", notes = "????????????????????????????????????????????????", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "??????????????????"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "????????????", response = AppletLoginInfo.class)
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
     * ??????????????????
     *
     * @param weChatAppletUserInfo ???????????????????????????
     * @return ????????????
     */
    @UnAuth
    @ResponseBody
    @RequestMapping("/dealuserinfo")
    @ApiOperation(value = "??????????????????", notes = "???????????????????????????????????????", httpMethod = "POST")
    public AppletLoginInfo dealUserInfo(WeChatAppletUserInfo weChatAppletUserInfo, HttpServletRequest request) {
        String token = getToken(request);
        Claims claims = getClaims(token);
        return weChatAppletLoginService.dealUserInfo(weChatAppletUserInfo, claims.getOpenId(), claims.getSessionKey(), appletLoginRedisParamResponse -> afterDealUserInfo(claims, appletLoginRedisParamResponse, token));
    }

    /**
     * ????????????
     *
     * @param loginParams ????????????
     */
    @RequestMapping("/bindaccount")
    @ResponseBody
    @UnAuth
    @ApiOperation(value = "????????????", notes = "?????????????????????????????????", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "username", value = "?????????"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "password", value = "??????"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "?????????  -1 ????????????????????????  -2 ????????????  -3 ???????????? 1 ??????  -4 ???????????????", response = Integer.class)
    })
    public AjaxResult bindAccount(@ApiIgnore LoginParams loginParams, HttpServletRequest request) {
        //??????
        loginParams.setConsumer(customer ->
                alterLoginSuccess(customer.getId(), request)
        );
        return AjaxResult.success(loginService.login(loginParams));
    }


    /**
     * ????????????
     *
     * @return 1??????
     */
    @RequestMapping("/unbindaccount")
    @ResponseBody
    @ApiOperation(value = "????????????", notes = "??????????????????????????????", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "1??????", response = Integer.class)
    })
    public int unbindAccount(HttpServletRequest request) {
        weChatAppletLoginService.unbindAccount(AppletsLoginUtils.getInstance().getCustomerId(request), AppletsLoginUtils.getInstance().getUnionId(request));
        Claims claims = AppletsLoginUtils.getInstance().getClaims(request);
        claims.setCustomerId(CommonConstant.NO_CUSTOMER_ID);
        redisService.putToRedis(getToken(request), JSONObject.toJSONString(claims));
        return 1;
    }


    /**
     * ??????token
     *
     * @param request request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        // ???????????????header ??????key
        final String authHeader = request.getHeader("Authorization");

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer")) {
            log.info("getClaims fail :Authorization fail ");
            throw new UnAuthorizedException(ResultCode.WX_NOT_AUTHORIZED);
        }
        return authHeader.length() >= 7 ? authHeader.substring(7) : authHeader.substring(6);
    }

    /**
     * ???????????????????????????
     *
     * @param token token
     * @return ?????????????????????
     */
    private Claims getClaims(String token) {
        String claims = redisService.getValue(token);
        return StringUtils.isEmpty(claims) ? null : JSONObject.parseObject(redisService.getValue(token), Claims.class);
    }

    /**
     * ???????????????????????????
     *
     * @param claims                        ?????????????????????
     * @param appletLoginRedisParamResponse ?????????redis??????????????????
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
     * ???????????????????????????
     *
     * @param customerId ??????id
     */
    private void alterLoginSuccess(long customerId, HttpServletRequest request) {
        if (!ObjectUtils.isEmpty(customerId)) {
            Claims claims = getClaims(getToken(request));
            // ???????????????uid  ????????????uid  ???????????????
            if (!claims.hasUnionId()) {
                log.error("applets has not authorized");
                throw new UnAuthorizedException(ResultCode.WX_NOT_AUTHORIZED);
            }
            //????????????
            try {
                weChatAppletLoginService.bindAccount(claims.getOpenId(), claims.getUnionId(), customerId);
            } catch (ServiceException e) {
                throw e;
            } catch (Exception e) {
                //??????????????????????????????????????????
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
