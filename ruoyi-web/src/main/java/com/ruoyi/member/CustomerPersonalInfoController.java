package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.member.domain.UmsMember;
import com.ruoyi.member.service.IUmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员个人信息控制器
 */
@Controller
@RequestMapping("/customer")
@Api(description = "会员个人信息接口")
public class CustomerPersonalInfoController {

    /**
     * 注入会员service
     */
    @Autowired
    private IUmsMemberService customerService;

    /**
     * 更新用户个人信息
     *
     * @param customer 会员实体类
     * @return 返回码 成功返回1 失败返回0
     */
    @RequestMapping("/updatepersonalinfo")
    @ResponseBody
    @ApiOperation(value = "更新用户个人信息", notes = "更新用户个人信息（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回码 成功返回1 失败返回0", response = Integer.class)
    })
    public AjaxResult editCustomerPersonalInfo(HttpServletRequest request, @RequestBody UmsMember customer) {
        return AjaxResult.success(customerService.updateCustomer(customer.setCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request))));
    }
}
