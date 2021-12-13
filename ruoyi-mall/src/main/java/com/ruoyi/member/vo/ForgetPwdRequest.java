package com.ruoyi.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * Created by 伊甸园商城 on 17/11/22.
 * 忘记密码请求
 */
@ApiModel(description = "忘记密码请求")
@Data
public class ForgetPwdRequest {

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobile;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码")
    private String code;

    /**
     * 发送给手机的验证码
     */
    @ApiModelProperty(value = "发送给手机的验证码")
    private String originCode;

    /**
     * 凭证
     */
    @ApiModelProperty(value = "凭证")
    private String certificate;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 随机数
     */
    @ApiModelProperty(value = "随机数")
    private String uuid;

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
     * 随机数（pc端图片验证码用）
     */
    @ApiModelProperty(value = "随机数（pc端图片验证码用）")
    private String uuidForPcKaptcha;

    /**
     * 是否有凭证
     *
     * @return 有返回true
     */
    public boolean hasCertificate() {
        return !StringUtils.isEmpty(certificate);
    }

    /**
     * 判断验证码是否正确
     *
     * @return 正确返回true
     */
    public boolean validateCode() {
        if (StringUtils.isEmpty(this.code)) {
            return false;
        }
        return this.code.equals(this.originCode);
    }

}
