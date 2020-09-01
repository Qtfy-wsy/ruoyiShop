package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsGoodsSpecValue;
import com.ruoyi.goods.service.IPmsGoodsSpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品和规格值的关联Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/goodsSepcValue")
public class PmsGoodsSpecValueController extends BaseController {
    @Autowired
    private IPmsGoodsSpecValueService pmsGoodsSpecValueService;

    /**
     * 查询商品和规格值的关联列表
     */
    @PreAuthorize("@ss.hasPermi('goods:value:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsGoodsSpecValue pmsGoodsSpecValue) {
        startPage();
        List<PmsGoodsSpecValue> list = pmsGoodsSpecValueService.selectPmsGoodsSpecValueList(pmsGoodsSpecValue);
        return getDataTable(list);
    }

    /**
     * 导出商品和规格值的关联列表
     */
    @PreAuthorize("@ss.hasPermi('goods:value:export')")
    @Log(title = "商品和规格值的关联", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsGoodsSpecValue pmsGoodsSpecValue) {
        List<PmsGoodsSpecValue> list = pmsGoodsSpecValueService.selectPmsGoodsSpecValueList(pmsGoodsSpecValue);
        ExcelUtil<PmsGoodsSpecValue> util = new ExcelUtil<PmsGoodsSpecValue>(PmsGoodsSpecValue.class);
        return util.exportExcel(list, "value");
    }

    /**
     * 获取商品和规格值的关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:value:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsGoodsSpecValueService.selectPmsGoodsSpecValueById(id));
    }

    /**
     * 新增商品和规格值的关联
     */
    @PreAuthorize("@ss.hasPermi('goods:value:add')")
    @Log(title = "商品和规格值的关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsGoodsSpecValue pmsGoodsSpecValue) {
        return toAjax(pmsGoodsSpecValueService.insertPmsGoodsSpecValue(pmsGoodsSpecValue));
    }

    /**
     * 修改商品和规格值的关联
     */
    @PreAuthorize("@ss.hasPermi('goods:value:edit')")
    @Log(title = "商品和规格值的关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsGoodsSpecValue pmsGoodsSpecValue) {
        return toAjax(pmsGoodsSpecValueService.updatePmsGoodsSpecValue(pmsGoodsSpecValue));
    }

    /**
     * 删除商品和规格值的关联
     */
    @PreAuthorize("@ss.hasPermi('goods:value:remove')")
    @Log(title = "商品和规格值的关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsGoodsSpecValueService.deletePmsGoodsSpecValueByIds(ids));
    }
}
