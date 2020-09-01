package com.ruoyi.member.vo;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 小程序redis参数返回实体类
 *
 * @author SK
 * @since 2018/6/13
 */
@Data
public class AppletLoginRedisParamResponse {

    /**
     * 小程序sessionKey
     */
    private String sessionKey;

    /**
     * token
     */
    private String token;

    /**
     * 联合登录id
     */
    private String unionId;

    /**
     * 用户id
     */
    private long customerId = -1;

    /**
     * 微信用户标识
     */
    private String openId;

    private String code;

    /**
     * 判断是否有unionId
     *
     * @return 有返回true, 否则返回false
     */
    public boolean hasUnionId() {
        return !StringUtils.isEmpty(unionId);
    }
}
