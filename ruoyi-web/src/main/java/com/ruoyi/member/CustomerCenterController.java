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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户中心控制器
 *
 * @author SK
 * @since 2018/6/13
 */
@Controller
@Api(description = "用户中心接口")
public class CustomerCenterController {

    /**
     * 注入会员接口
     */
    @Autowired
    private IUmsMemberService customerService;


    /**
     * 获得会员基本信息
     *
     * @return 返回会员基本信息
     */
    @RequestMapping("customerdetail")
    @ResponseBody
    @ApiOperation(value = "获得会员基本信息", notes = "获得会员基本信息（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回会员基本信息", response = UmsMember.class)
    })
    public AjaxResult customerDetail(HttpServletRequest request) {
        return AjaxResult.success(customerService.queryCustomerWithNoPasswordById(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }
}
