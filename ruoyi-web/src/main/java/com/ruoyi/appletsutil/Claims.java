package com.ruoyi.appletsutil;

import com.alibaba.fastjson.annotation.JSONField;
import com.ruoyi.util.CommonConstant;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created by 伊甸园商城 on 2018/6/13.
 * 小程序凭证实体
 */
@Data
public class Claims {

    /**
     * 小程序unionId
     */
    @JSONField(name = "unionId")
    private String unionId;

    /**
     * 会员id
     */
    @JSONField(name = "customerId")
    private long customerId;

    /**
     * 小程序sessionKey
     */
    @JSONField(name = "sessionKey")
    private String sessionKey;

    /**
     * 微信用户标识
     */
    @JSONField(name = "openId")
    private String openId;


    /**
     * 判断是否有unionId
     *
     * @return 有返回true  没有返回fasle
     */
    public boolean hasUnionId() {
        return !StringUtils.isEmpty(this.unionId);
    }

    /**
     * 判断是否有会员id
     *
     * @return 有返回true  没有返回false
     */
    public boolean hasCustomerId() {
        return CommonConstant.NO_LOGIN_CUSTOMERID != this.customerId;
    }
}
