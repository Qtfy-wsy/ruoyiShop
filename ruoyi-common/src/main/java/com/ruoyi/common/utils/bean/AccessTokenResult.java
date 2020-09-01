package com.ruoyi.common.utils.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 网页授权返回实体类
 */
@Data
public class AccessTokenResult {


    /**
     * 网页授权接口调用凭证
     */
    @JSONField(name = "access_token")
    private String access_token;

    /**
     * access_token接口调用凭证超时时间，单位（秒）
     */
    @JSONField(name = "expires_in")
    private int expires_in;

    /**
     * 用户刷新access_token
     */
    @JSONField(name = "refresh_token")
    private String refresh_token;

    /**
     * 用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID
     */
    @JSONField(name = "openid")
    private String openid;

    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    @JSONField(name = "scope")
    private String scope;

    /**
     * 联合登录id
     */
    @JSONField(name = "unionid")
    private String unionid;

    /**
     * 错误码
     */
    @JSONField(name = "errcode")
    private String errcode;

    /**
     * 重定向地址（出错时返回）
     */
    private String redirectUrl;

    /**
     * 错误信息（出错时返回）
     */
    @JSONField(name = "errmsg")
    private String errmsg;

    /**
     * 转换为字符串
     */
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    /**
     * 判断是否失败
     */
    @JsonIgnore
    public boolean isError() {
        return !StringUtils.isEmpty(this.errcode);
    }
}
