package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsSku;
import com.ruoyi.goods.service.IPmsSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单品Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/sku")
public class PmsSkuController extends BaseController {
    @Autowired
    private IPmsSkuService pmsSkuService;

    /**
     * 查询单品列表
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsSku pmsSku) {
        startPage();
        List<PmsSku> list = pmsSkuService.selectPmsSkuList(pmsSku);
        return getDataTable(list);
    }

    /**
     * 导出单品列表
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:export')")
    @Log(title = "单品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsSku pmsSku) {
        List<PmsSku> list = pmsSkuService.selectPmsSkuList(pmsSku);
        ExcelUtil<PmsSku> util = new ExcelUtil<PmsSku>(PmsSku.class);
        return util.exportExcel(list, "sku");
    }

    /**
     * 获取单品详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(pmsSkuService.selectPmsSkuById(id));
    }

    /**
     * 新增单品
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:add')")
    @Log(title = "单品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsSku pmsSku) {
        return toAjax(pmsSkuService.insertPmsSku(pmsSku));
    }

    /**
     * 修改单品
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:edit')")
    @Log(title = "单品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsSku pmsSku) {
        return toAjax(pmsSkuService.updatePmsSku(pmsSku));
    }

    /**
     * 删除单品
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:remove')")
    @Log(title = "单品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(pmsSkuService.deletePmsSkuByIds(ids));
    }
}
