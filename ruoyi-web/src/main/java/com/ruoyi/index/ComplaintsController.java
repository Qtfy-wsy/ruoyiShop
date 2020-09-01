package com.ruoyi.index;


import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.member.domain.ComplaintOrder;
import com.ruoyi.member.domain.Complaints;
import com.ruoyi.member.service.ComplaintOrderService;
import com.ruoyi.member.service.ComplaintsService;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * @author gxs
 * @date 2020-03-02 14:50
 * <p>
 * 投诉接口
 */
@RestController
@Api(description = "投诉接口")
public class ComplaintsController {

    /**
     * 注入平台投诉服务接口
     */
    @Autowired
    private ComplaintsService complaintsService;

    /**
     * 注入订单投诉服务
     */
    @Autowired
    private ComplaintOrderService complaintOrderService;


    /**
     * 新增订单投诉
     *
     * @param complaintOrder 订单投诉实体
     * @return 1成功 否则失败
     */
    @PostMapping("/complaint/order")
    @ApiOperation(value = "新增订单投诉", notes = "新增订单投诉(需要认证)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "1成功 否则失败", response = Integer.class)
    })
    public AjaxResult addComplaintOrder(@RequestBody ComplaintOrder complaintOrder, HttpServletRequest request) {
        return AjaxResult.success(complaintOrderService.addComplaintOrder(complaintOrder.buildCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request))));
    }


    /**
     * 分页查找订单投诉
     *
     * @param pageHelper 分页帮助类
     * @return 订单投诉列表
     */
    @GetMapping("/complaint/order")
    @ApiOperation(value = "分页查找订单投诉", notes = "分页查找订单投诉(需要认证)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "订单投诉列表", response = ComplaintOrder.class)
    })
    public AjaxResult queryComplaintOrder(@ApiIgnore PageHelper<ComplaintOrder> pageHelper, HttpServletRequest request) {
        return AjaxResult.success(complaintOrderService.queryComplaintOrder(pageHelper, ComplaintOrder.QueryCriteria.buildForPc(AppletsLoginUtils.getInstance().getCustomerId(request))));
    }


    /**
     * 新增平台投诉
     *
     * @param complaints 平台投诉实体
     * @return 成功1 失败0
     */
    @PostMapping("/complaint")
    @ApiOperation(value = "新增平台投诉", notes = "新增平台投诉(需要认证)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "1成功 否则失败", response = Integer.class)
    })
    public AjaxResult addComplaints(@RequestBody Complaints complaints, HttpServletRequest request) {
        complaints.setCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request));
        return AjaxResult.success(complaintsService.addComplaints(complaints));
    }


    /**
     * 分页查询平台投诉
     *
     * @param pageHelper 分页帮助类
     * @return 平台投诉列表
     */
    @GetMapping("complaint")
    @ApiOperation(value = "分页查询平台投诉", notes = "分页查询平台投诉(需要认证)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "平台投诉列表", response = Complaints.class)
    })
    public AjaxResult queryComplaints(@ApiIgnore PageHelper<Complaints> pageHelper, HttpServletRequest request) {
        return AjaxResult.success(complaintsService.queryComplaints(pageHelper, AppletsLoginUtils.getInstance().getCustomerId(request), null));
    }
    @GetMapping("complaintDetail")
    @ApiOperation(value = "分页查询平台投诉", notes = "分页查询平台投诉(需要认证)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "平台投诉列表", response = Complaints.class)
    })
    public AjaxResult complaintDetail(long id) {
        return AjaxResult.success(complaintsService.queryComplaintsById(id));
    }
}
