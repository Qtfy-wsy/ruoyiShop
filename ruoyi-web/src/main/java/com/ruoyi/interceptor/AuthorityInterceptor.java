package com.ruoyi.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.appletsutil.Claims;
import com.ruoyi.appletsutil.ResultCode;
import com.ruoyi.appletsutil.UnAuthorizedException;
import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.member.domain.WeChatCustomerLink;
import com.ruoyi.member.service.WeChatCustomerLinkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * Created by 魔金商城 on 2018/6/13.
 * 访问拦截器
 */
@Slf4j
@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    /**
     * 注入redis服务
     */
    @Autowired
    private RedisCache redisService;

    /**
     * 注入微信用户关联服务
     */
    @Autowired
    @Lazy
    private WeChatCustomerLinkService weChatCustomerLinkService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestType = ((HttpServletRequest) request).getMethod();
        String fullUrl = ((HttpServletRequest) request).getRequestURL().toString();

        int startIntercept = fullUrl.replace("//", "a").indexOf("/") + 1;
        String interfaceName = fullUrl.substring(startIntercept, fullUrl.length());
        if (!"OPTIONS".equals(requestType) && !interfaceName.contains("webjars")
                && !interfaceName.contains("api-docs")) {
            log.info(formMapKey(AppletsLoginUtils.getInstance().getCustomerId(request), fullUrl, requestType,
                    IpUtils.getIpAddr((HttpServletRequest) request), null, null)
                    + ",\"cost\":\"");
        }

        // 如果不需要拦截 则直接返回
        if (!isNeedFilter(handler)) {
            log.debug("Donot need  authority.....");
            return true;
        }

        // 认证信息在header 中的key
        final String authHeader = request.getHeader("Authorization");


        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            log.info("AuthorityInterceptor preHandle fail....");
            throw new UnAuthorizedException(ResultCode.WX_NOT_AUTHORIZED);
        }

        // 获得凭证
        Claims claims = getAppletsClaimsFromRedis(authHeader.substring(7));

        // 没有凭证 直接返回
        if (Objects.isNull(claims)) {
            log.info("AuthorityInterceptor preHandle fail....");
            throw new UnAuthorizedException(ResultCode.WX_NOT_AUTHORIZED);
        }

        // 校验有没有uid  如果没有uid  则直接返回
        if (!claims.hasCustomerId()) {
            log.error("applets has not authorized");
            throw new UnAuthorizedException(ResultCode.WX_NOT_AUTHORIZED);
        }

        // 如果没有会员id  则从数据库查询 是否有会员id
        if (!claims.hasCustomerId()) {
            // 小程序没有关联用户则提示用户去关联用户
            WeChatCustomerLink weChatCustomerLink = queryWeChatCustomerLinkByUnionId(claims.getUnionId());
            if (Objects.isNull(weChatCustomerLink)) {
                log.error("applets has not linked member....");
                throw new UnAuthorizedException(ResultCode.WX_NOT_LINKD);
            } else {
                log.info("applets has unionId and has member....");
                // 将凭证信息返回redis
                putClaimsToRedis(weChatCustomerLink, claims, authHeader.substring(7));
                //如果没有小程序的openId,则更新关联信息
                if (!weChatCustomerLink.hasAppletOpenId()) {
                    weChatCustomerLink.setAppletOpenId(claims.getOpenId());
                    weChatCustomerLinkService.updateWeChatCustomerLink(weChatCustomerLink);
                }
            }
        }

        request.setAttribute("claims", claims);

        return true;
    }

    private String formMapKey(Object userId, String mothedName, String requestType,
                              String ip, String params, String token) {
        return "\"time\"" + ":\"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date())
                + "\",\"name\"" + ":\"" + mothedName + "\",\"uid\":\"" + userId
                + "\",\"type\":\"" + requestType + "\",\"ip\":\"" + ip
                + "\",\"token\":\"" + token + "\",\"params\":\"" + params + "\"";
    }
    /**
     * 将凭证放入redis
     *
     * @param weChatCustomerLink 会员和小程序的绑定关系
     * @param claims             凭证
     * @param token              token
     */
    private void putClaimsToRedis(WeChatCustomerLink weChatCustomerLink, Claims claims, String token) {
        claims.setCustomerId(weChatCustomerLink.getCustomerId());
        redisService.putToRedis(token, JSON.toJSONString(claims));
    }

    /**
     * 根据unionId查询会员和小程序的绑定关系
     *
     * @param unionId 小程序唯一id
     * @return 返回会员和小程序的绑定关系
     */
    private WeChatCustomerLink queryWeChatCustomerLinkByUnionId(String unionId) {
        return weChatCustomerLinkService.queryWeChatCustomerLinkByUnionId(unionId);
    }


    /**
     * 根据token从redis 获取uid和customerId
     *
     * @param token token
     * @return 返回uid和customerId
     */
    private Claims getAppletsClaimsFromRedis(String token) {
        String claims = redisService.getValue(token);
        return StringUtils.isEmpty(claims) ? null : JSONObject.parseObject(redisService.getValue(token), Claims.class);
    }


    /**
     * 判断是否需要拦截
     */
    private boolean isNeedFilter(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            return handlerMethod.getMethod().getAnnotation(UnAuth.class) == null;
        }
        return false;
    }

}
