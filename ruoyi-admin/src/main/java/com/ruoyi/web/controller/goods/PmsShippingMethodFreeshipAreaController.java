package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsShippingMethodFreeshipArea;
import com.ruoyi.goods.service.IPmsShippingMethodFreeshipAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 运费方式包邮关联的区域Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/freeShipArea")
public class PmsShippingMethodFreeshipAreaController extends BaseController {
    @Autowired
    private IPmsShippingMethodFreeshipAreaService pmsShippingMethodFreeshipAreaService;

    /**
     * 查询运费方式包邮关联的区域列表
     */
    @PreAuthorize("@ss.hasPermi('goods:area:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea) {
        startPage();
        List<PmsShippingMethodFreeshipArea> list = pmsShippingMethodFreeshipAreaService.selectPmsShippingMethodFreeshipAreaList(pmsShippingMethodFreeshipArea);
        return getDataTable(list);
    }

    /**
     * 导出运费方式包邮关联的区域列表
     */
    @PreAuthorize("@ss.hasPermi('goods:area:export')")
    @Log(title = "运费方式包邮关联的区域", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea) {
        List<PmsShippingMethodFreeshipArea> list = pmsShippingMethodFreeshipAreaService.selectPmsShippingMethodFreeshipAreaList(pmsShippingMethodFreeshipArea);
        ExcelUtil<PmsShippingMethodFreeshipArea> util = new ExcelUtil<PmsShippingMethodFreeshipArea>(PmsShippingMethodFreeshipArea.class);
        return util.exportExcel(list, "area");
    }

    /**
     * 获取运费方式包邮关联的区域详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:area:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsShippingMethodFreeshipAreaService.selectPmsShippingMethodFreeshipAreaById(id));
    }

    /**
     * 新增运费方式包邮关联的区域
     */
    @PreAuthorize("@ss.hasPermi('goods:area:add')")
    @Log(title = "运费方式包邮关联的区域", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea) {
        return toAjax(pmsShippingMethodFreeshipAreaService.insertPmsShippingMethodFreeshipArea(pmsShippingMethodFreeshipArea));
    }

    /**
     * 修改运费方式包邮关联的区域
     */
    @PreAuthorize("@ss.hasPermi('goods:area:edit')")
    @Log(title = "运费方式包邮关联的区域", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsShippingMethodFreeshipArea pmsShippingMethodFreeshipArea) {
        return toAjax(pmsShippingMethodFreeshipAreaService.updatePmsShippingMethodFreeshipArea(pmsShippingMethodFreeshipArea));
    }

    /**
     * 删除运费方式包邮关联的区域
     */
    @PreAuthorize("@ss.hasPermi('goods:area:remove')")
    @Log(title = "运费方式包邮关联的区域", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsShippingMethodFreeshipAreaService.deletePmsShippingMethodFreeshipAreaByIds(ids));
    }
}
