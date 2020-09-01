package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsLogisticsCompany;
import com.ruoyi.order.domain.OmsOrder;
import com.ruoyi.order.service.IOmsLogisticsCompanyService;
import com.ruoyi.order.service.IOmsOrderService;
import com.ruoyi.order.service.OrderServiceApi;
import com.ruoyi.order.vo.CancelOrderParams;
import com.ruoyi.order.vo.ConfirmOrderParams;
import com.ruoyi.order.vo.OrderItem;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.web.utils.AdminLoginUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsOrder")
public class OmsOrderController extends BaseController {
    @Autowired
    private IOmsOrderService omsOrderService;
    /**
     * 订单服务接口
     */
    @Autowired
    private OrderServiceApi orderServiceApi;
    /**
     * 注入物流服务接口
     */
    @Autowired
    private IOmsLogisticsCompanyService logisticsCompanyService;
    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsOrder omsOrder) {
        omsOrder.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        startPage();
        List<OmsOrder> list = omsOrderService.selectOmsOrderList(omsOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrder:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsOrder omsOrder) {
        omsOrder.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        List<OmsOrder> list = omsOrderService.selectOmsOrderList(omsOrder);
        ExcelUtil<OmsOrder> util = new ExcelUtil<OmsOrder>(OmsOrder.class);
        return util.exportExcel(list, "OmsOrder");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(orderServiceApi.queryOrderDetailById(id, CommonConstant.QUERY_WITH_NO_STORE, CommonConstant.QUERY_WITH_NO_CUSTOMER, OrderItem.LOG, OrderItem.ATTR, OrderItem.SKUS));
    }


    /**
     * 删除订单
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrder:remove')")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsOrderService.deleteOmsOrderByIds(ids));
    }

    /**
     * 取消订单
     *
     * @param id 订单id
     * @return 1 成功 -1 订单不存在 -2:用户不匹配 0 失败
     */
    @DeleteMapping("/order/{id}")
    @ApiOperation(value = "取消订单", notes = "取消订单（需要认证）")
   // @PreAuthorize("hasAuthority('order/cancelorder')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "1 成功 -1 订单不存在 -2:用户不匹配 0 失败", response = Integer.class)
    })
    public int cancelOrder(@PathVariable long id) {
        return orderServiceApi.cancleOrder(CancelOrderParams.buildManagerSource(AdminLoginUtils.getInstance().getStoreId(), id, AdminLoginUtils.getInstance().getManagerName()));
    }


    /**
     * 确认付款
     *
     * @param id 订单id
     * @return 成功返回1 失败返回0
     */
    @PutMapping("/confirmorder/{id}")
    @ApiOperation(value = "确认付款", notes = "确认付款（需要认证）")
   // @PreAuthorize("hasAuthority('order/confirmorder')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "reason", value = "原因"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public int confirmOrder(@PathVariable long id, String reason) {
        return orderServiceApi.confirmOrderPayed(ConfirmOrderParams.buildManagerSource(id, AdminLoginUtils.getInstance().getStoreId(), reason, AdminLoginUtils.getInstance().getManagerName()));
    }

    /**
     * 修改价格
     *
     * @param id     订单id
     * @param price  修改价格(减多少钱)
     * @param reason 原因
     * @return 成功返回 1 失败返回0 -1 订单最低支付1毛 -2 订单不能修改价格
     */
    @PutMapping("/modifyprice/{id}")
    @ApiOperation(value = "修改价格", notes = "修改价格（需要认证）")
   // @PreAuthorize("hasAuthority('order/modifyprice')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
            @ApiImplicitParam(paramType = "form", dataType = "BigDecimal", name = "price", value = "修改价格(减多少钱)"),
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "reason", value = "原因"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回 1 失败返回0 -1 订单最低支付1毛 -2 订单不能修改价格", response = Integer.class)
    })
    public int modifyPrice(@PathVariable long id, BigDecimal price, String reason) {
        return omsOrderService.modifyPrice(id, price, reason, AdminLoginUtils.getInstance().getStoreId(), AdminLoginUtils.getInstance().getManagerName());
    }


    /**
     * 发货
     *
     * @param id          订单id
     * @param waybillCode 运单号
     * @return 成功返回1 失败返回0 -2 订单正在申请退款 -1 订单号含有中文 -3 拼团未成功  -4 众筹还未成功 -5 订单不存在 -6 物流公司不存在
     */
    @PutMapping("/deliverorder/{id}/{waybillCode}/{companyId}")
    @ApiOperation(value = "发货", notes = "发货（需要认证）")
   // @PreAuthorize("hasAuthority('order/deliverorder')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "waybillCode", value = "运单号"),
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "companyId", value = "物流公司Id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0 -2 订单正在申请退款 -1 订单号含有中文 -3 拼团未成功  -4 众筹还未成功 -5 订单不存在 -6 物流公司不存在", response = Integer.class)
    })
    public int deliverOrder(@PathVariable long id, @PathVariable String waybillCode, @PathVariable long companyId) {
        return orderServiceApi.deliverOrder(id, AdminLoginUtils.getInstance().getStoreId(), waybillCode, AdminLoginUtils.getInstance().getManagerName(), companyId);
    }

    /**
     * 查询店铺使用的物流信息
     *
     * @return 返回订单物流模版信息
     */
    @GetMapping("/order/logisticscompanys")
    @ApiOperation(value = "查询店铺使用的物流信息", notes = "查询店铺使用的物流信息（需要认证）")
   // @PreAuthorize("hasAuthority('order/querylogisticscompanys')")
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回店铺使用的物流信息", response = OmsLogisticsCompany.class)
    })
    public List<OmsLogisticsCompany> queryLogisticsCompanys() {
        return logisticsCompanyService.selectOmsLogisticsCompanyList(new OmsLogisticsCompany());
    }

    /**
     * 核销虚拟商品订单
     *
     * @param orderId      订单id
     * @param writeOffCode 兑换码
     * @return 0 失败   1 成功 -1 核销码错误
     */
    @PutMapping("/chargeoffvirtualorder/{orderId}/{writeOffCode}")
    @ApiOperation(value = "核销虚拟商品订单", notes = "核销虚拟商品订单（需要认证）")
   // @PreAuthorize("hasAuthority('order/chargeoffvirtualorder')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "orderId", value = "订单id"),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "writeOffCode", value = "兑换码"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "0 失败   1 成功 -1 核销码错误", response = Integer.class)
    })
    public int chargeOffVirtualOrder(@PathVariable long orderId, @PathVariable String writeOffCode) {
        return orderServiceApi.writeOffVirtualOrder(orderId, AdminLoginUtils.getInstance().getStoreId(), writeOffCode, AdminLoginUtils.getInstance().getManagerName());
    }

    /**
     * 修改物流单号
     *
     * @param id          订单id
     * @param waybillCode 运单号
     * @return 成功返回1 失败返回0 -2 订单正在申请退款 -1 订单号含有中文 -6 物流公司不存在
     */
    @PutMapping("/changeexpressno/{id}/{waybillCode}/{companyId}")
    @ApiOperation(value = "修改物流单号", notes = "修改物流单号（需要认证）")
    //@PreAuthorize("hasAuthority('order/changeexpressno')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
            @ApiImplicitParam(paramType = "path", dataType = "string", name = "waybillCode", value = "运单号"),
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "companyId", value = "物流公司Id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0 -2 订单正在申请退款 -1 订单号含有中文 -6 物流公司不存在", response = Integer.class)
    })
    public int changeExpressNo(@PathVariable long id, @PathVariable String waybillCode, @PathVariable long companyId) {
        return omsOrderService.changeExpressNo(id, AdminLoginUtils.getInstance().getStoreId(), waybillCode, AdminLoginUtils.getInstance().getManagerName(), companyId);
    }

    /**
     * 根据订单id查询订单信息  (订单的所有信息 此接口慎用)
     *
     * @param id 订单id
     * @return 返回订单信息
     */
    @GetMapping("/order/{id}")
    @ApiOperation(value = "根据订单id查询订单信息  (订单的所有信息 此接口慎用)", notes = "根据订单id查询订单信息  (订单的所有信息 此接口慎用)（需要认证）")
    //@PreAuthorize("hasAuthority('order/queryorderbyid')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "订单id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回订单信息", response = OmsOrder.class)
    })
    public OmsOrder queryOrderById(@PathVariable long id) {
        return orderServiceApi.queryOrderDetailById(id, CommonConstant.QUERY_WITH_NO_STORE, CommonConstant.QUERY_WITH_NO_CUSTOMER, OrderItem.LOG, OrderItem.ATTR, OrderItem.SKUS);
    }

    /**
     * 导出所有订单信息
     *
     * @param status 订单状态
     * @throws IOException
     */
    @PostMapping("/exportallorder")
    @ApiOperation(value = "导出所有订单信息", notes = "导出所有订单信息")
    //@PreAuthorize("hasAuthority('order/exportallorder')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "status", value = "订单状态"),
    })
    public void exportAllOrder(HttpServletResponse response, String status) throws IOException {
        OutputStream os = response.getOutputStream();
      //  ExcelUtils.exportExcel(response, String.valueOf("订单信息_" + System.currentTimeMillis()).concat(".xls"), () -> orderServiceApi.exportAllOrder(os, status, AdminLoginUtils.getInstance().getStoreId(), null));
    }

    /**
     * 导出选中的订单信息
     *
     * @param status 订单状态
     * @param ids    订单id数组
     * @throws IOException
     */
    @PostMapping("/exportcheckedorder")
    @ApiOperation(value = "导出选中的订单信息", notes = "导出选中的订单信息")
   // @PreAuthorize("hasAuthority('order/exportcheckedorder')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "string", name = "status", value = "订单状态"),
            @ApiImplicitParam(paramType = "form", dataType = "array", name = "ids", value = "订单id数组"),
    })
    public void exportCheckedOrder(HttpServletResponse response, String status, Long... ids) throws IOException {
        OutputStream os = response.getOutputStream();
      /*  ExcelUtils.exportExcel(response, String.valueOf("订单信息_" + System.currentTimeMillis()).concat(".xls"), () ->
                orderServiceApi.exportCheckedOrder(os, status, ids, AdminLoginUtils.getInstance().getStoreId()));*/
    }


    /**
     * 查询打印订单详情
     *
     * @param ids 订单id
     * @return 返回打印订单详情
     */
    @GetMapping("/printorderdetails")
    @ApiOperation(value = "查询打印订单详情", notes = "查询打印订单详情（需要认证）")
    //@PreAuthorize("hasAuthority('order/queryprintorderdetails')")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "array", name = "ids", value = "订单id数组"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回打印订单详情", response = Map.class)
    })
    public Map<String, Object> queryPrintOrderDetails(Long... ids) {
        Map<String, Object> result = new HashMap<>();
        result.put("orders", orderServiceApi.queryPrintOrderDetails(Arrays.asList(ids).stream().filter(Objects::nonNull).collect(Collectors.toList())));
        result.put("operator", AdminLoginUtils.getInstance().getManagerName());
        return result;
    }
}
