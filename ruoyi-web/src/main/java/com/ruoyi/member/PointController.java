package com.ruoyi.member;

import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.integral.domain.CustomerPoint;
import com.ruoyi.integral.service.CustomerPointService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 魔金商城 on 18/6/23.
 * 会员积分控制器
 */
@RestController
@Api(description = "会员积分接口")
public class PointController {

    /**
     * 注入会员积分服务
     */
    @Autowired
    private CustomerPointService customerPointService;

    /**
     * 分页查询用户积分
     *
     * @param pageHelper 分页帮助类
     * @return 返回用户积分
     */
    @RequestMapping(value = "/points")
    @ResponseBody
    @ApiOperation(value = "分页查询用户积分", notes = "分页查询用户积分（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户积分", response = CustomerPoint.class)
    })
    public AjaxResult queryPoints(HttpServletRequest request, @ApiIgnore PageHelper<CustomerPoint> pageHelper,String type) {
        return AjaxResult.success(customerPointService.queryCustomerPoints(pageHelper, AppletsLoginUtils.getInstance().getCustomerId(request), null,type));
    }

    /**
     * 查询用户总积分
     *
     * @return 返回用户总积分
     */
    @RequestMapping(value = "/points/total")
    @ResponseBody
    @ApiOperation(value = "查询用户总积分", notes = "查询用户总积分（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户总积分", response = Integer.class)
    })
    public AjaxResult queryAllPoints(HttpServletRequest request) {
        return AjaxResult.success(customerPointService.queryCustomerPointCount(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }
    /**
     * 分页查询用户积分
     *
     * @return 返回用户积分
     */
    @RequestMapping(value = "/pointTotal")
    @ResponseBody
    @ApiOperation(value = "分页查询用户积分", notes = "分页查询用户积分（需要认证）", httpMethod = "POST")
    public AjaxResult pointTotal(HttpServletRequest request) {
        return AjaxResult.success(customerPointService.queryCustomerPointGroupByType(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }
}
