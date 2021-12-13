package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsShippingMethodFreeship;
import com.ruoyi.goods.service.IPmsShippingMethodFreeshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运费模版包邮Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/freeship")
public class PmsShippingMethodFreeshipController extends BaseController {
    @Autowired
    private IPmsShippingMethodFreeshipService pmsShippingMethodFreeshipService;

    /**
     * 查询运费模版包邮列表
     */
    @PreAuthorize("@ss.hasPermi('goods:freeship:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsShippingMethodFreeship pmsShippingMethodFreeship) {
        startPage();
        List<PmsShippingMethodFreeship> list = pmsShippingMethodFreeshipService.selectPmsShippingMethodFreeshipList(pmsShippingMethodFreeship);
        return getDataTable(list);
    }

    /**
     * 导出运费模版包邮列表
     */
    @PreAuthorize("@ss.hasPermi('goods:freeship:export')")
    @Log(title = "运费模版包邮", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsShippingMethodFreeship pmsShippingMethodFreeship) {
        List<PmsShippingMethodFreeship> list = pmsShippingMethodFreeshipService.selectPmsShippingMethodFreeshipList(pmsShippingMethodFreeship);
        ExcelUtil<PmsShippingMethodFreeship> util = new ExcelUtil<PmsShippingMethodFreeship>(PmsShippingMethodFreeship.class);
        return util.exportExcel(list, "freeship");
    }

    /**
     * 获取运费模版包邮详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:freeship:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsShippingMethodFreeshipService.selectPmsShippingMethodFreeshipById(id));
    }

    /**
     * 新增运费模版包邮
     */
    @PreAuthorize("@ss.hasPermi('goods:freeship:add')")
    @Log(title = "运费模版包邮", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsShippingMethodFreeship pmsShippingMethodFreeship) {
        return toAjax(pmsShippingMethodFreeshipService.insertPmsShippingMethodFreeship(pmsShippingMethodFreeship));
    }

    /**
     * 修改运费模版包邮
     */
    @PreAuthorize("@ss.hasPermi('goods:freeship:edit')")
    @Log(title = "运费模版包邮", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsShippingMethodFreeship pmsShippingMethodFreeship) {
        return toAjax(pmsShippingMethodFreeshipService.updatePmsShippingMethodFreeship(pmsShippingMethodFreeship));
    }

    /**
     * 删除运费模版包邮
     */
    @PreAuthorize("@ss.hasPermi('goods:freeship:remove')")
    @Log(title = "运费模版包邮", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsShippingMethodFreeshipService.deletePmsShippingMethodFreeshipByIds(ids));
    }
}
