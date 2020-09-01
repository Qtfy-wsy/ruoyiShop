package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.integral.domain.PointSignRule;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.integral.service.PointSignRuleService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 魔金商城 on 18/6/14.
 * 签到控制器
 */
@Api(description = "签到接口")
@RestController
public class CustomerSignInController {

    /**
     * 注入会员积分服务
     */
    @Autowired
    private CustomerPointService customerPointService;

    /**
     * 注入签到积分规则服务
     */
    @Autowired
    private PointSignRuleService pointSignRuleService;

    /**
     * 查找签到积分规则
     */
    @RequestMapping("/querypointsignrule")
    @ResponseBody
    @ApiOperation(value = "查找签到积分规则", notes = "查找签到积分规则（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "签到积分规则", response = PointSignRule.class)
    })
    public AjaxResult queryPointSignRule() {
        return AjaxResult.success(pointSignRuleService.queryPointSignRule());
    }

    /**
     * 查找签到记录
     *
     * @param pointPageHelper 分页帮助类
     * @return 签到记录
     */
    @RequestMapping("/customersignlist")
    @ResponseBody
    @ApiOperation(value = "查找签到记录", notes = "查找签到记录（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "签到记录", response = CustomerPoint.class)
    })
    public AjaxResult customerSignList(HttpServletRequest request, @ApiIgnore PageHelper<CustomerPoint> pointPageHelper) {
        return AjaxResult.success(customerPointService.queryCustomerPoints(pointPageHelper, AppletsLoginUtils.getInstance().getCustomerId(request), "4",""));
    }

    /**
     * 判断今天是否签到
     *
     * @return 未签到返回1
     */
    @RequestMapping("/istodaysign")
    @ResponseBody
    @ApiOperation(value = "判断今天是否签到", notes = "判断今天是否签到（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "未签到返回1", response = Integer.class)
    })
    public AjaxResult isTodaySign(HttpServletRequest request) {
        return AjaxResult.success(customerPointService.isTodaySign(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 签到
     *
     * @return 1：成功 -1：没有开启签到活动 -2：今天已签到
     */
    @RequestMapping("/signin")
    @ResponseBody
    @ApiOperation(value = "签到", notes = "签到（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "1：成功 -1：没有开启签到活动 -2：今天已签到", response = Integer.class)
    })
    public AjaxResult signIn(HttpServletRequest request) {
        AjaxResult ajaxResult = new AjaxResult();
        int res = customerPointService.addSignRecord(AppletsLoginUtils.getInstance().getCustomerId(request), point -> ajaxResult.put(AjaxResult.DATA_TAG,point));
        ajaxResult.put(AjaxResult.CODE_TAG,res);
        return ajaxResult;
    }
}
