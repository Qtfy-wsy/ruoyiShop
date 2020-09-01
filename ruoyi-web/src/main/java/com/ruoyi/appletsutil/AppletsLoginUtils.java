package com.ruoyi.appletsutil;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 小程序登录工具类
 *
 * @author SK
 * @since 2018/6/13
 */
public class AppletsLoginUtils {
    private static AppletsLoginUtils ourInstance = new AppletsLoginUtils();


    private AppletsLoginUtils() {
    }

    public static AppletsLoginUtils getInstance() {
        return ourInstance;
    }

    /**
     * 获得用户id
     *
     * @return 返回用户id
     */
    public long getCustomerId(HttpServletRequest request) {
        long customerId = 0;
        if (!ObjectUtils.isEmpty(getClaims(getToken(request)))) {
            customerId = getClaims(getToken(request)).getCustomerId();
        }
        return customerId;
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
            throw new UnAuthorizedException(ResultCode.WX_NOT_AUTHORIZED);
        }
        return authHeader.length() >= 7 ? authHeader.substring(7) : authHeader.substring(6);
    }
    /**
     * 获取openId
     *
     * @return 返回openId
     */
    public String getOpenId(HttpServletRequest request) {
        return ((Claims) request.getAttribute("claims")).getOpenId();
    }
    /**
     * 获取小程序凭证实体
     *
     * @param token token
     * @return 小程序凭证实体
     */
    private Claims getClaims(String token) {
        String claims = SpringUtils.getBean(RedisCache.class).getValue(token);
        return StringUtils.isEmpty(claims) ? null : JSONObject.parseObject(claims, Claims.class);
    }
    /**
     * 获取unionId
     *
     * @return 返回unionId
     */
    public String getUnionId(HttpServletRequest request) {
        return ((Claims) request.getAttribute("claims")).getUnionId();
    }

    /**
     * 获取小程序凭证实体
     *
     * @return 小程序凭证实体
     */
    public Claims getClaims(HttpServletRequest request) {
        return ((Claims) request.getAttribute("claims"));
    }
}
