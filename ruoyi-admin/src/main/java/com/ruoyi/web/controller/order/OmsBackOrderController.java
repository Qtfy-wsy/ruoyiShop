package com.ruoyi.web.controller.order;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsBackOrder;
import com.ruoyi.order.service.IOmsBackOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 退单退款Controller
 *
 * @author 商城
 */
@RestController
@RequestMapping("/order/OmsBackOrder")
public class OmsBackOrderController extends BaseController {
    @Autowired
    private IOmsBackOrderService omsBackOrderService;

    /**
     * 查询退单退款列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBackOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsBackOrder omsBackOrder) {
        startPage();
        List<OmsBackOrder> list = omsBackOrderService.selectOmsBackOrderList(omsBackOrder);
        return getDataTable(list);
    }

    /**
     * 导出退单退款列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBackOrder:export')")
    @Log(title = "退单退款", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsBackOrder omsBackOrder) {
        List<OmsBackOrder> list = omsBackOrderService.selectOmsBackOrderList(omsBackOrder);
        ExcelUtil<OmsBackOrder> util = new ExcelUtil<OmsBackOrder>(OmsBackOrder.class);
        return util.exportExcel(list, "OmsBackOrder");
    }

    /**
     * 获取退单退款详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBackOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsBackOrderService.selectOmsBackOrderById(id));
    }

    /**
     * 新增退单退款
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBackOrder:add')")
    @Log(title = "退单退款", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody String param) {
        OmsBackOrder omsBackOrder = JSON.parseObject(param,OmsBackOrder.class);
        return toAjax(omsBackOrderService.insertOmsBackOrder(omsBackOrder));
    }

    /**
     * 修改退单退款
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBackOrder:edit')")
    @Log(title = "退单退款", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody String param) {
        OmsBackOrder omsBackOrder = JSON.parseObject(param,OmsBackOrder.class);
        return toAjax(omsBackOrderService.updateOmsBackOrder(omsBackOrder));
    }

    /**
     * 删除退单退款
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBackOrder:remove')")
    @Log(title = "退单退款", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsBackOrderService.deleteOmsBackOrderByIds(ids));
    }
}
