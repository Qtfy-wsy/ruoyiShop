package com.ruoyi.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * 绑定新邮箱实体
 */
@Data
@ApiModel(description = "绑定新邮箱实体")
public class BindNewEmailRequest {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private long customerId;

    /**
     * 用户手机
     */
    @ApiModelProperty(value = "用户手机")
    private String mobile;

    /**
     * 新邮箱
     */
    @ApiModelProperty(value = "新邮箱")
    private String email;

    /**
     * 是否有凭证
     */
    @ApiModelProperty(value = "是否有凭证")
    private String hasCert;

    /**
     * 校验码
     */
    @ApiModelProperty(value = "校验码")
    private String checkCode;

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
     * 判断是否有凭证
     *
     * @return 有返回true
     */
    public boolean hasOwnCert() {
        return !StringUtils.isEmpty(this.hasCert);
    }

}
