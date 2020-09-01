package com.ruoyi.member;


import com.ruoyi.common.annotation.UnAuth;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.member.service.ForgetPwdService;
import com.ruoyi.member.vo.ForgetPwdRequest;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 忘记密码控制器
 */
@Controller
@RequestMapping("/forgetpwd")
@Api(description = "忘记密码接口")
public class ForgetPwdController {


    /**
     * 注入忘记密码服务接口
     */
    @Autowired
    private ForgetPwdService forgetPwdService;

    /**
     * 注入redis服务
     */
    @Autowired
    private RedisCache redisService;



    /**
     * 修改密码
     *
     * @param forgetPwdRequest 修改密码请求实体
     * @return -1 参数错误  -2 验证码错误  0 失败 1 成功
     */
    @RequestMapping("/update")
    @UnAuth
    @ResponseBody
    @ApiOperation(value = "修改密码", notes = "修改密码（不需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "mobile", value = "手机号码"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "验证码"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "password", value = "密码"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "-1 参数错误  -2 验证码错误  0 失败 1 成功", response = Integer.class)
    })
    public AjaxResult updatePassword(@ApiIgnore ForgetPwdRequest forgetPwdRequest) {
        forgetPwdRequest.setCertificate(CommonConstant.PASS_FLAG);
        forgetPwdRequest.setOriginCode(redisService.getValue(String.format("%s_%s", CommonConstant.APPLET_REGISTER_CODE_KEY, forgetPwdRequest.getMobile())));
        return AjaxResult.success(forgetPwdService.updatePassword(forgetPwdRequest));
    }

}
