package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsCombinationSku;
import com.ruoyi.goods.service.IPmsCombinationSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品组合下的单品Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/combinationsku")
public class PmsCombinationSkuController extends BaseController {
    @Autowired
    private IPmsCombinationSkuService pmsCombinationSkuService;

    /**
     * 查询商品组合下的单品列表
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsCombinationSku pmsCombinationSku) {
        startPage();
        List<PmsCombinationSku> list = pmsCombinationSkuService.selectPmsCombinationSkuList(pmsCombinationSku);
        return getDataTable(list);
    }

    /**
     * 导出商品组合下的单品列表
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:export')")
    @Log(title = "商品组合下的单品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsCombinationSku pmsCombinationSku) {
        List<PmsCombinationSku> list = pmsCombinationSkuService.selectPmsCombinationSkuList(pmsCombinationSku);
        ExcelUtil<PmsCombinationSku> util = new ExcelUtil<PmsCombinationSku>(PmsCombinationSku.class);
        return util.exportExcel(list, "sku");
    }

    /**
     * 获取商品组合下的单品详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsCombinationSkuService.selectPmsCombinationSkuById(id));
    }

    /**
     * 新增商品组合下的单品
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:add')")
    @Log(title = "商品组合下的单品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsCombinationSku pmsCombinationSku) {
        return toAjax(pmsCombinationSkuService.insertPmsCombinationSku(pmsCombinationSku));
    }

    /**
     * 修改商品组合下的单品
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:edit')")
    @Log(title = "商品组合下的单品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsCombinationSku pmsCombinationSku) {
        return toAjax(pmsCombinationSkuService.updatePmsCombinationSku(pmsCombinationSku));
    }

    /**
     * 删除商品组合下的单品
     */
    @PreAuthorize("@ss.hasPermi('goods:sku:remove')")
    @Log(title = "商品组合下的单品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsCombinationSkuService.deletePmsCombinationSkuByIds(ids));
    }
}
