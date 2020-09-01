package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsSkuBatch;
import com.ruoyi.goods.service.IPmsSkuBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单品起批价格标Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/batch")
public class PmsSkuBatchController extends BaseController {
    @Autowired
    private IPmsSkuBatchService pmsSkuBatchService;

    /**
     * 查询单品起批价格标列表
     */
    @PreAuthorize("@ss.hasPermi('goods:batch:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsSkuBatch pmsSkuBatch) {
        startPage();
        List<PmsSkuBatch> list = pmsSkuBatchService.selectPmsSkuBatchList(pmsSkuBatch);
        return getDataTable(list);
    }

    /**
     * 导出单品起批价格标列表
     */
    @PreAuthorize("@ss.hasPermi('goods:batch:export')")
    @Log(title = "单品起批价格标", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsSkuBatch pmsSkuBatch) {
        List<PmsSkuBatch> list = pmsSkuBatchService.selectPmsSkuBatchList(pmsSkuBatch);
        ExcelUtil<PmsSkuBatch> util = new ExcelUtil<PmsSkuBatch>(PmsSkuBatch.class);
        return util.exportExcel(list, "batch");
    }

    /**
     * 获取单品起批价格标详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:batch:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSkuBatchService.selectPmsSkuBatchById(id));
    }

    /**
     * 新增单品起批价格标
     */
    @PreAuthorize("@ss.hasPermi('goods:batch:add')")
    @Log(title = "单品起批价格标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsSkuBatch pmsSkuBatch) {
        return toAjax(pmsSkuBatchService.insertPmsSkuBatch(pmsSkuBatch));
    }

    /**
     * 修改单品起批价格标
     */
    @PreAuthorize("@ss.hasPermi('goods:batch:edit')")
    @Log(title = "单品起批价格标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsSkuBatch pmsSkuBatch) {
        return toAjax(pmsSkuBatchService.updatePmsSkuBatch(pmsSkuBatch));
    }

    /**
     * 删除单品起批价格标
     */
    @PreAuthorize("@ss.hasPermi('goods:batch:remove')")
    @Log(title = "单品起批价格标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSkuBatchService.deletePmsSkuBatchByIds(ids));
    }
}
