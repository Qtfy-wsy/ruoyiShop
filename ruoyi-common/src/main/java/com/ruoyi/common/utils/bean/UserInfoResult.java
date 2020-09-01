package com.ruoyi.common.utils.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 用户信息返回实体类
 */
@Data
public class UserInfoResult {

    /**
     * 用户唯一标志
     */
    @JSONField(name = "openid")
    private String openid;

    /**
     * 昵称
     */
    @JSONField(name = "nickname")
    private String nickname;

    /**
     * 性别
     */
    @JSONField(name = "sex")
    private String sex;

    /**
     * 省份
     */
    @JSONField(name = "province")
    private String province;

    /**
     * 城市
     */
    @JSONField(name = "city")
    private String city;

    /**
     * 国家
     */
    @JSONField(name = "country")
    private String country;

    /**
     *
     */
    @JSONField(name = "headimgurl")
    private String headimgurl;

    /**
     * 联合登录id 只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
     */
    @JSONField(name = "unionid")
    private String unionid;

    /**
     * 错误码（出错时返回）
     */
    @JSONField(name = "errcode")
    private String errcode;

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
