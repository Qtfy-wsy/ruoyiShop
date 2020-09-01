package com.ruoyi.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created by 魔金商城 on 17/11/21.
 * 重新绑定手机号码实体
 */
@Data
@ApiModel(description = "重新绑定手机号码实体")
public class BindNewMobileRequest {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private long customerId;

    /**
     * 重新绑定的手机号码
     */
    @ApiModelProperty(value = "重新绑定的手机号码")
    private String mobile;

    /**
     * 用户输入的验证码
     */
    @ApiModelProperty(value = "用户输入的验证码")
    private String code;

    /**
     * 发送给手机的验证码
     */
    @ApiModelProperty(value = "发送给手机的验证码")
    private String originCode;

    /**
     * 是否有凭证
     */
    @ApiModelProperty(value = "是否有凭证")
    private String hasCert;

    /**
     * 判断是否有凭证
     *
     * @return 有返回true
     */
    public boolean hasOwnCert() {
        return !StringUtils.isEmpty(this.hasCert);
    }

    /**
     * 判断验证码是否正确
     *
     * @return 正确返回0
     */
    public boolean validateCode() {
        if (StringUtils.isEmpty(this.code)) {
            return false;
        }

        return this.code.equals(this.originCode);
    }
}
