package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsOrderSetting;
import com.ruoyi.order.service.IOmsOrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单设置Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsOrderSetting")
public class OmsOrderSettingController extends BaseController {
    @Autowired
    private IOmsOrderSettingService omsOrderSettingService;

    /**
     * 查询订单设置列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSetting:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsOrderSetting omsOrderSetting) {
        startPage();
        List<OmsOrderSetting> list = omsOrderSettingService.selectOmsOrderSettingList(omsOrderSetting);
        return getDataTable(list);
    }

    /**
     * 导出订单设置列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSetting:export')")
    @Log(title = "订单设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsOrderSetting omsOrderSetting) {
        List<OmsOrderSetting> list = omsOrderSettingService.selectOmsOrderSettingList(omsOrderSetting);
        ExcelUtil<OmsOrderSetting> util = new ExcelUtil<OmsOrderSetting>(OmsOrderSetting.class);
        return util.exportExcel(list, "OmsOrderSetting");
    }

    /**
     * 获取订单设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSetting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsOrderSettingService.selectOmsOrderSettingById(id));
    }

    /**
     * 新增订单设置
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSetting:add')")
    @Log(title = "订单设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsOrderSetting omsOrderSetting) {
        return toAjax(omsOrderSettingService.insertOmsOrderSetting(omsOrderSetting));
    }

    /**
     * 修改订单设置
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSetting:edit')")
    @Log(title = "订单设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsOrderSetting omsOrderSetting) {
        return toAjax(omsOrderSettingService.updateOmsOrderSetting(omsOrderSetting));
    }

    /**
     * 删除订单设置
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderSetting:remove')")
    @Log(title = "订单设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsOrderSettingService.deleteOmsOrderSettingByIds(ids));
    }
}
