package com.ruoyi.pointmall;


import com.ruoyi.appletsutil.AppletsLoginUtils;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.integral.domain.PointMallOrder;
import com.ruoyi.integral.domain.QueryCriteria;
import com.ruoyi.integral.service.PointMallOrderService;
import com.ruoyi.integral.service.PointMallOrderServiceApi;
import com.ruoyi.order.vo.PointOrderMessageCount;
import com.ruoyi.util.PageHelper;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


/**
 * 积分商城订单控制器
 *
 * @author 商城
 */
@Controller
@Api(description = "积分商城订单接口")
public class PointMallOrderController {

    /**
     * 注入积分商城订单聚合服务
     */
    @Autowired
    private PointMallOrderServiceApi pointMallOrderServiceApi;

    /**
     * 注入积分商城订单服务
     */
    @Autowired
    private PointMallOrderService pointMallOrderService;


    /**
     * 提交积分商城订单
     *
     * @param pointMallOrder 积分商城订单实体
     * @return 1成功 0失败 -1缺少参数 -2库存不足 -3积分商品不存在 -4 积分不足 -5 未发布
     */
    @RequestMapping("/submitpointmallorder")
    @ResponseBody
    @ApiOperation(value = "提交积分商城订单", notes = "提交积分商城订单（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "1成功 0失败 -1缺少参数 -2库存不足 -3积分商品不存在 -4 积分不足 -5 未发布", response = Integer.class)
    })
    public AjaxResult submitPointMallOrder(@RequestBody PointMallOrder pointMallOrder, HttpServletRequest request) {
        return pointMallOrderServiceApi.addPointMallOrder(pointMallOrder.buildCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 分页查询用户积分商城订单列表
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回用户积分商城订单列表
     */
    @RequestMapping(value = "/querypointmallorderlist")
    @ResponseBody
    @ApiOperation(value = "分页查询用户积分商城订单列表", notes = "分页查询用户积分商城订单列表（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "status", value = "订单状态 -1:全部 0:待发货 1:待收货 2:已完成"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "code", value = "订单号"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "timeType", value = "查询时间类型 1 近一个月  2 一个月前"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回用户积分商城订单列表", response = PointMallOrder.class)
    })
    public AjaxResult queryCustomerPointMallOrders(@ApiIgnore PageHelper<PointMallOrder> pageHelper, @ApiIgnore QueryCriteria queryCriteria, HttpServletRequest request) {
        return AjaxResult.success(pointMallOrderService.queryPointMallOrdersForSite(pageHelper, queryCriteria.bulidCustomerId(AppletsLoginUtils.getInstance().getCustomerId(request))));
    }

    /**
     * 积分商城订单确认收货
     *
     * @param id 积分商城订单id
     * @return 成功返回>0 失败返回0
     */
    @RequestMapping(value = "/confirmreceiptpointmallorder")
    @ResponseBody
    @ApiOperation(value = "积分商城订单确认收货", notes = "积分商城订单确认收货（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "id", value = "积分商城订单id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回>0 失败返回0", response = Integer.class)
    })
    public AjaxResult confirmReceiptPointMallOrder(long id, HttpServletRequest request) {
        return AjaxResult.success(pointMallOrderService.confirmReceipt(id, AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 查询积分商城订单详情
     *
     * @param id 积分商城订单id
     * @return 返回积分商城订单详情
     */
    @RequestMapping(value = "/querypointmallorderdetail")
    @ResponseBody
    @ApiOperation(value = "查询积分商城订单详情", notes = "查询积分商城订单详情（需要认证）", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "long", name = "id", value = "积分商城订单id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回积分商城订单详情", response = PointMallOrder.class)
    })
    public AjaxResult queryPointMallOrderDetail(long id, HttpServletRequest request) {
        return AjaxResult.success(pointMallOrderServiceApi.queryPointMallOrderDetailById(id, AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

    /**
     * 积分订单统计
     *
     * @return 返回积分订单消息总数返回实体
     */
    @RequestMapping(value = "/pointorder/count")
    @ResponseBody
    @ApiOperation(value = "积分订单统计", notes = "积分订单统计（需要认证）", httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回积分订单消息总数返回实体", response = PointOrderMessageCount.class)
    })
    public PointOrderMessageCount queryPointOrderMessageCount(HttpServletRequest request) {
        return PointOrderMessageCount.build()
                .addToDeliverCount(pointMallOrderService.toDeliverPointOrderCount(AppletsLoginUtils.getInstance().getCustomerId(request)))
                .addToReceiptCount(pointMallOrderService.toReceiptPointOrderCount(AppletsLoginUtils.getInstance().getCustomerId(request)));
    }

}