package com.ruoyi.web.controller.order;


import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.order.domain.OmsLogisticsCompany;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.service.IOmsLogisticsCompanyService;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.order.service.OrderServiceApi;
import com.ruoyi.order.vo.CancelOrderParams;
import com.ruoyi.order.vo.ConfirmOrderParams;
import com.ruoyi.order.vo.GroupOrder;
import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.util.BaseResponse;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.util.PageHelper;
import com.ruoyi.web.utils.AdminLoginUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by dujinkai on 2019/6/17.
 * 团购订单控制器
 */
@RestController
@Api(description = "团购订单接口")
public class GroupOrderController {


    /**
     * 订单服务接口
     */
    @Autowired
    private IOmsOrderService orderService;

    /**
     * 注入订单混合api接口
     */
    @Autowired
    private OrderServiceApi orderServiceApi;


    /**
     * 注入物流服务接口
     */
    @Autowired
    private IOmsLogisticsCompanyService logisticsCompanyService;


    /**
     * 分页查询拼团订单
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回拼团订单信息
     */
    @GetMapping("/grouporders")
    @ApiOperation(value = "分页查询拼团订单", notes = "分页查询拼团订单（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "groupId", value = "拼团id"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "customerName", value = "用户名"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "startTime", value = "开始时间"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "endTime", value = "结束时间"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回拼团订单列表", response = GroupOrder.class)
    })
    public BaseResponse queryGroupOrders(@ApiIgnore PageHelper<GroupOrder> pageHelper, @ApiIgnore GroupOrder.QueryCriteria queryCriteria) {
        queryCriteria.setStoreId(CommonConstant.ADMIN_STOREID);
        return BaseResponse.build(orderService.queryGroupOrders(queryCriteria, pageHelper, true, true));
    }

    /**
     * 分页查询未支付拼团订单
     *
     * @param pageHelper    分页帮助类
     * @param queryCriteria 查询条件
     * @return 返回未支付的拼团订单信息
     */
    @GetMapping("/nopaygrouporders")
    @ApiOperation(value = "分页查询未支付拼团订单", notes = "分页查询未支付拼团订单（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "groupId", value = "拼团id"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "customerName", value = "用户名"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "startTime", value = "开始时间"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "endTime", value = "结束时间"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回未支付的拼团订单列表", response = GroupOrder.class)
    })
    public BaseResponse queryNotPayGroupOrders(@ApiIgnore PageHelper<GroupOrder> pageHelper, @ApiIgnore GroupOrder.QueryCriteria queryCriteria) {
        queryCriteria.setStoreId(CommonConstant.ADMIN_STOREID);
        return BaseResponse.build(orderService.queryNotPayGroupOrders(queryCriteria, pageHelper, false));
    }

    /**
     * 确认拼团订单付款
     *
     * @param id 订单id
     * @return 成功返回1 失败返回0
     */
    @PutMapping("/confirmgrouporder/{id}")
    @ApiOperation(value = "确认拼团订单付款", notes = "确认拼团订单付款（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "reason", value = "原因"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public int confirmGroupOrder(@PathVariable long id, String reason) {
        return orderServiceApi.confirmOrderPayed(ConfirmOrderParams.buildManagerSource(id, CommonConstant.ADMIN_STOREID, reason, AdminLoginUtils.getInstance().getManagerName()));
    }


    /**
     * 取消拼团订单
     *
     * @param id 订单id
     * @return 1 成功 -1 订单不存在 -2:用户不匹配 0 失败
     */
    @PutMapping("/cancelgrouporder/{id}")
    @ApiOperation(value = "取消拼团订单", notes = "取消拼团订单（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "1 成功 -1 订单不存在 -2:用户不匹配 0 失败", response = Integer.class)
    })
    public int cancelGroupOrder(@PathVariable long id) {
        return orderServiceApi.cancleOrder(CancelOrderParams.buildManagerSource(CommonConstant.ADMIN_STOREID, id, AdminLoginUtils.getInstance().getManagerName()));
    }


    /**
     * 导出所有拼团订单信息(未付款订单)
     *
     * @throws IOException
     */
    @PostMapping("/nopay/exportallgrouporder")
    @ApiOperation(value = "导出所有拼团订单信息", notes = "导出所有拼团订单信息（需要认证）")

    public void exportAllGroupOrderForNoPay(HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
      //  ExcelUtils.exportExcel(response, String.valueOf("订单信息_" + System.currentTimeMillis()).concat(".xls"), () -> orderServiceApi.exportAllGroupOrder(os, "-1", CommonConstant.ADMIN_STOREID));
    }

    /**
     * 导出选中的拼团订单信息(未付款订单)
     *
     * @param ids 订单id数组
     * @throws IOException
     */
    @PostMapping("/nopay/exportcheckedgrouporder")
    @ApiOperation(value = "导出选中的拼团订单信息", notes = "导出选中的拼团订单信息（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "array", name = "ids", value = "订单id数组"),
    })
    public void exportCheckedGroupOrderForNoPay(HttpServletResponse response, Long... ids) throws IOException {
        OutputStream os = response.getOutputStream();

    }


    /**
     * 导出所有拼团订单信息
     *
     * @throws IOException
     */
    @PostMapping("/exportallgrouporder")
    @ApiOperation(value = "导出所有拼团订单信息", notes = "导出所有拼团订单信息（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "status", value = "订单状态"),
    })
    public void exportAllGroupOrder(HttpServletResponse response, String status) throws IOException {
        OutputStream os = response.getOutputStream();
     //   ExcelUtils.exportExcel(response, String.valueOf("订单信息_" + System.currentTimeMillis()).concat(".xls"), () -> orderServiceApi.exportAllGroupOrder(os, status, CommonConstant.ADMIN_STOREID));
    }

    /**
     * 导出选中的拼团订单信息
     *
     * @param status 订单状态
     * @param ids    订单id数组
     * @throws IOException
     */
    @PostMapping("/exportcheckedgrouporder")
    @ApiOperation(value = "导出选中的拼团订单信息", notes = "导出选中的拼团订单信息（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "status", value = "订单状态"),
            @ApiImplicitParam(paramType = "path", dataType = "array", name = "ids", value = "订单id数组"),
    })
    public void exportCheckedGroupOrder(HttpServletResponse response, String status, Long... ids) throws IOException {
        OutputStream os = response.getOutputStream();
       /* ExcelUtils.exportExcel(response, String.valueOf("订单信息_" + System.currentTimeMillis()).concat(".xls"), () ->
                orderServiceApi.exportCheckedGroupOrder(os, status, ids, CommonConstant.ADMIN_STOREID)
        );*/
    }


    /**
     * 根据订单id查询拼团订单信息  (订单的所有信息 此接口慎用)
     *
     * @param id 订单id
     * @return 返回订单信息
     */
    @GetMapping("/grouporder/{id}")
    @ApiOperation(value = "根据订单id查询拼团订单信息  (订单的所有信息 此接口慎用)", notes = "根据订单id查询拼团订单信息  (订单的所有信息 此接口慎用)（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回订单信息", response = OmsOrder.class)
    })
    public AjaxResult queryGroupOrderById(@PathVariable long id) {
        return AjaxResult.success(orderServiceApi.queryOrderDetailById(id, CommonConstant.QUERY_WITH_NO_STORE, CommonConstant.QUERY_WITH_NO_CUSTOMER, OrderItem.LOG, OrderItem.ATTR, OrderItem.SKUS));
    }


    /**
     * 查询店铺使用的物流信息
     *
     * @return 返回店铺使用的物流信息
     */
    @GetMapping("/grouporder/logisticscompanys")
    @ApiOperation(value = "查询店铺使用的物流信息", notes = "查询店铺使用的物流信息（需要认证）")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回店铺使用的物流信息", response = OmsLogisticsCompany.class)
    })
    public List<OmsLogisticsCompany> queryLogisticsCompanys() {
        return logisticsCompanyService.queryStoreUseCompanys(CommonConstant.ADMIN_STOREID);
    }

    /**
     * 拼团订单发货
     *
     * @param id          订单id
     * @param waybillCode 运单号
     * @return 成功返回1 失败返回0 -2 订单正在申请退款 -1 订单号含有中文 -3 拼团未成功  -4 众筹还未成功 -5 拼团没成功 -6物流公司不存在
     */
    @PutMapping("/delivergrouporder/{id}/{waybillCode}/{companyId}")
    @ApiOperation(value = "拼团订单发货", notes = "拼团订单发货（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "waybillCode", value = "运单号"),
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "companyId", value = "物流公司id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0 -2 订单正在申请退款 -1 订单号含有中文 -3 拼团未成功  -4 众筹还未成功 -5 拼团没成功 -6物流公司不存在", response = Integer.class)
    })
    public int deliverGroupOrder(@PathVariable long id, @PathVariable String waybillCode, @PathVariable long companyId) {
        return orderServiceApi.deliverOrder(id, CommonConstant.ADMIN_STOREID, waybillCode, AdminLoginUtils.getInstance().getManagerName(), companyId);
    }


    /**
     * 根据订单id查询拼团订单信息 （未付款） (订单的所有信息 此接口慎用)
     *
     * @param id 订单id
     * @return 返回订单信息
     */
    @GetMapping("nopay/grouporder/{id}")
    @ApiOperation(value = "根据订单id查询拼团订单信息  (订单的所有信息 此接口慎用)", notes = "根据订单id查询拼团订单信息  (订单的所有信息 此接口慎用)（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回订单信息", response = OmsOrder.class)
    })
    public AjaxResult queryGroupOrderByIdForNoPay(@PathVariable long id) {
        return AjaxResult.success(orderServiceApi.queryOrderDetailById(id, CommonConstant.QUERY_WITH_NO_STORE, CommonConstant.QUERY_WITH_NO_CUSTOMER, OrderItem.LOG, OrderItem.ATTR, OrderItem.SKUS));
    }

}
