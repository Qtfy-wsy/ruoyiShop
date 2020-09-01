package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsLogisticsCompanyUse;
import com.ruoyi.order.service.IOmsLogisticsCompanyUseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 店铺使用的物流公司Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsLogisticsCompanyUse")
public class OmsLogisticsCompanyUseController extends BaseController {
    @Autowired
    private IOmsLogisticsCompanyUseService omsLogisticsCompanyUseService;

    /**
     * 查询店铺使用的物流公司列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompanyUse:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsLogisticsCompanyUse omsLogisticsCompanyUse) {
        startPage();
        List<OmsLogisticsCompanyUse> list = omsLogisticsCompanyUseService.selectOmsLogisticsCompanyUseList(omsLogisticsCompanyUse);
        return getDataTable(list);
    }

    /**
     * 导出店铺使用的物流公司列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompanyUse:export')")
    @Log(title = "店铺使用的物流公司", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsLogisticsCompanyUse omsLogisticsCompanyUse) {
        List<OmsLogisticsCompanyUse> list = omsLogisticsCompanyUseService.selectOmsLogisticsCompanyUseList(omsLogisticsCompanyUse);
        ExcelUtil<OmsLogisticsCompanyUse> util = new ExcelUtil<OmsLogisticsCompanyUse>(OmsLogisticsCompanyUse.class);
        return util.exportExcel(list, "OmsLogisticsCompanyUse");
    }

    /**
     * 获取店铺使用的物流公司详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompanyUse:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsLogisticsCompanyUseService.selectOmsLogisticsCompanyUseById(id));
    }

    /**
     * 新增店铺使用的物流公司
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompanyUse:add')")
    @Log(title = "店铺使用的物流公司", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsLogisticsCompanyUse omsLogisticsCompanyUse) {
        return toAjax(omsLogisticsCompanyUseService.insertOmsLogisticsCompanyUse(omsLogisticsCompanyUse));
    }

    /**
     * 修改店铺使用的物流公司
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompanyUse:edit')")
    @Log(title = "店铺使用的物流公司", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsLogisticsCompanyUse omsLogisticsCompanyUse) {
        return toAjax(omsLogisticsCompanyUseService.updateOmsLogisticsCompanyUse(omsLogisticsCompanyUse));
    }

    /**
     * 删除店铺使用的物流公司
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompanyUse:remove')")
    @Log(title = "店铺使用的物流公司", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsLogisticsCompanyUseService.deleteOmsLogisticsCompanyUseByIds(ids));
    }
}
