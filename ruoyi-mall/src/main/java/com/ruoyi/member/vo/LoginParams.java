package com.ruoyi.member.vo;

import com.ruoyi.member.domain.UmsMember;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.function.Consumer;


/**
 * 登入参数实体
 */
@Data
@ApiModel(description = "登入参数实体")
public class LoginParams {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String mobile;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 回调函数
     */
    @ApiModelProperty(value = "回调函数")
    private Consumer<UmsMember> consumer;

    /**
     * 用户输入的验证码
     */
    @ApiModelProperty(value = "用户输入的验证码")
    private String code;

    /**
     * session中的验证码
     */
    @ApiModelProperty(value = "session中的验证码")
    private String codeInSession;

    /**
     * 登录来源 0 pc  1 app 2 mobile
     */
    private int source;

    public void setFromApp() {
        this.source = 1;
    }

    /**
     * 验证验证码 是否正确 （目前都不需要验证码）
     *
     * @return 正确返回true  否则返回false
     */
    public boolean validateCode() {

        // 目前都不需要验证码
        return true;
    }

}
