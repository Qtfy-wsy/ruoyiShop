package com.ruoyi.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created by 伊甸园商城 on 17/11/20.
 * 修改密码实体
 */
@Data
@ApiModel(description = "修改密码实体")
public class UpdatePwdBean {

    /**
     * 会员id
     */
    @ApiModelProperty(value = "会员id")
    private long customerId;

    /**
     * 用户输入的手机验证码
     */
    @ApiModelProperty(value = "用户输入的手机验证码")
    private String code;

    /**
     * 存起来的手机验证码
     */
    @ApiModelProperty(value = "存起来的手机验证码")
    private String originCode;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    /**
     * 凭证
     */
    @ApiModelProperty(value = "凭证")
    private String certificate;

    /**
     * 请求来自 1：pc
     */
    @ApiModelProperty(value = "请求来自 1：pc")
    private String requestFrom;

    /**
     * 用户输入的图片验证码
     */
    @ApiModelProperty(value = "用户输入的图片验证码")
    private String kaptcha;

    /**
     * 存在redis中的图片验证码
     */
    @ApiModelProperty(value = "存在redis中的图片验证码")
    private String oldKaptcha;

    /**
     * 随机数
     */
    @ApiModelProperty(value = "随机数")
    private String uuid;


    /**
     * 是否有凭证
     *
     * @return 有返回true
     */
    public boolean hasCertificate() {
        return !StringUtils.isEmpty(certificate);
    }

    /**
     * 校验用户输入的验证码是否正确
     *
     * @return 正确返回true
     */
    public boolean validateCode() {
        return StringUtils.isEmpty(this.code) ? false : this.code.equals(this.originCode);
    }
}
