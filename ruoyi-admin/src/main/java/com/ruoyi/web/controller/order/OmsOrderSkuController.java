package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsOrderSku;
import com.ruoyi.order.service.IOmsOrderSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单单品Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsOrderSku")
public class OmsOrderSkuController extends BaseController {
    @Autowired
    private IOmsOrderSkuService omsOrderSkuService;

    /**
     * 查询订单单品列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSku:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsOrderSku omsOrderSku) {
        startPage();
        List<OmsOrderSku> list = omsOrderSkuService.selectOmsOrderSkuList(omsOrderSku);
        return getDataTable(list);
    }

    /**
     * 导出订单单品列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSku:export')")
    @Log(title = "订单单品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsOrderSku omsOrderSku) {
        List<OmsOrderSku> list = omsOrderSkuService.selectOmsOrderSkuList(omsOrderSku);
        ExcelUtil<OmsOrderSku> util = new ExcelUtil<OmsOrderSku>(OmsOrderSku.class);
        return util.exportExcel(list, "OmsOrderSku");
    }

    /**
     * 获取订单单品详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSku:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsOrderSkuService.selectOmsOrderSkuById(id));
    }

    /**
     * 新增订单单品
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSku:add')")
    @Log(title = "订单单品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsOrderSku omsOrderSku) {
        return toAjax(omsOrderSkuService.insertOmsOrderSku(omsOrderSku));
    }

    /**
     * 修改订单单品
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSku:edit')")
    @Log(title = "订单单品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsOrderSku omsOrderSku) {
        return toAjax(omsOrderSkuService.updateOmsOrderSku(omsOrderSku));
    }

    /**
     * 删除订单单品
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSku:remove')")
    @Log(title = "订单单品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsOrderSkuService.deleteOmsOrderSkuByIds(ids));
    }
}
