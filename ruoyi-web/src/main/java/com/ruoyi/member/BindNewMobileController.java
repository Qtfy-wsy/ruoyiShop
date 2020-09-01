package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.member.service.BindNewMobileService;
import com.ruoyi.member.service.IUmsMemberService;
import com.ruoyi.member.vo.BindNewMobileRequest;
import com.ruoyi.util.CommonConstant;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * 绑定新手机控制器
 */
@Controller
@RequestMapping("/bindnewmobile")
@Api(description = "绑定手机接口")
public class BindNewMobileController {

    /**
     * 注入绑定新手机服务
     */
    @Autowired
    private BindNewMobileService bindNewMobileService;
    /**
     * 注入用户服务
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 注入redis服务
     */
    @Autowired
    private RedisCache redisService;




    /**
     * 校验输入的验证码是否正确(重新绑定手机的第一个验证码)
     *
     * @param code 用户输入的验证码
     * @return 成功返回0  失败返回-1
     */
    @RequestMapping("/validateoldcode")
    @ResponseBody
    @ApiOperation(value = "重新绑定手机号码发送验证码", notes = "重新绑定手机号码发送验证码（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "用户输入的验证码"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "mobile", value = "电话"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回0  失败返回-1", response = Integer.class)
    })
    public AjaxResult validateOldMobileCode(String code, String mobile) {
        int resultCode = bindNewMobileService.validateCode(code, redisService.getValue(String.format("%s_%s", CommonConstant.APPLET_REGISTER_CODE_KEY, mobile)));
        // 如果成功 则在session 纪录一下
        if (resultCode == 0) {
            redisService.putToRedis(String.format("%s_%s_%s", CommonConstant.APPLET_REGISTER_CODE_KEY, CommonConstant.PASS_FLAG, mobile), CommonConstant.PASS_FLAG, 5, TimeUnit.MINUTES);
        }
        return AjaxResult.success(resultCode);
    }




    /**
     * 用户绑定新的手机号码
     *
     * @param bindNewMobileRequest 绑定新手机号码
     * @return -1 参数错误 -2 没有凭证 -3 验证码错误 -4 手机号码已经存在 1 成功  0 失败
     */
    @RequestMapping("/update")
    @ResponseBody
    @ApiOperation(value = "更新用户绑定的手机号码", notes = "更新用户绑定的手机号码（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "用户输入的验证码"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "mobile", value = "电话"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "-1 参数错误 -2 没有凭证 -3 验证码错误 -4 手机号码已经存在 1 成功  0 失败", response = Integer.class)
    })
    public AjaxResult bindNewMobile(HttpServletRequest request, @ApiIgnore BindNewMobileRequest bindNewMobileRequest) {
        bindNewMobileRequest.setCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request));
        String oldMobile = customerService.queryCustomerWithNoPasswordById(bindNewMobileRequest.getCustomerId()).getMobile();
        bindNewMobileRequest.setOriginCode(redisService.getValue(String.format("%s_%s", CommonConstant.APPLET_REGISTER_CODE_KEY, bindNewMobileRequest.getMobile())));
        bindNewMobileRequest.setHasCert(redisService.getValue(String.format("%s_%s_%s", CommonConstant.APPLET_REGISTER_CODE_KEY, CommonConstant.PASS_FLAG, oldMobile)));
        return AjaxResult.success(bindNewMobileService.bindNewMobile(bindNewMobileRequest));
    }


}
