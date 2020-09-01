package com.ruoyi.common.utils.bean;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 微信小程序登录返回
 *
 * @author SK
 * @since 2018/6/13
 */
@Data
public class WeChatAppletLoginResponse {
    /**
     * 用户唯一标志
     */
    @JSONField(name = "openid")
    private String openid;

    /**
     * 会话密钥
     */
    @JSONField(name = "session_key")
    private String session_key;

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

    /**
     * 判断是否有unionId
     *
     * @return 有返回true, 否则返回false
     */
    public boolean hasUnionId() {
        return !StringUtils.isEmpty(unionid);
    }
}
