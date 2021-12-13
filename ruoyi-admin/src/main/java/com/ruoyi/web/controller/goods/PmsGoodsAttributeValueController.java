package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsGoodsAttributeValue;
import com.ruoyi.goods.service.IPmsGoodsAttributeValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品下面的属性值Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/goodsAttriValue")
public class PmsGoodsAttributeValueController extends BaseController {
    @Autowired
    private IPmsGoodsAttributeValueService pmsGoodsAttributeValueService;

    /**
     * 查询商品下面的属性值列表
     */
    @PreAuthorize("@ss.hasPermi('goods:value:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsGoodsAttributeValue pmsGoodsAttributeValue) {
        startPage();
        List<PmsGoodsAttributeValue> list = pmsGoodsAttributeValueService.selectPmsGoodsAttributeValueList(pmsGoodsAttributeValue);
        return getDataTable(list);
    }

    /**
     * 导出商品下面的属性值列表
     */
    @PreAuthorize("@ss.hasPermi('goods:value:export')")
    @Log(title = "商品下面的属性值", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsGoodsAttributeValue pmsGoodsAttributeValue) {
        List<PmsGoodsAttributeValue> list = pmsGoodsAttributeValueService.selectPmsGoodsAttributeValueList(pmsGoodsAttributeValue);
        ExcelUtil<PmsGoodsAttributeValue> util = new ExcelUtil<PmsGoodsAttributeValue>(PmsGoodsAttributeValue.class);
        return util.exportExcel(list, "value");
    }

    /**
     * 获取商品下面的属性值详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:value:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsGoodsAttributeValueService.selectPmsGoodsAttributeValueById(id));
    }

    /**
     * 新增商品下面的属性值
     */
    @PreAuthorize("@ss.hasPermi('goods:value:add')")
    @Log(title = "商品下面的属性值", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsGoodsAttributeValue pmsGoodsAttributeValue) {
        return toAjax(pmsGoodsAttributeValueService.insertPmsGoodsAttributeValue(pmsGoodsAttributeValue));
    }

    /**
     * 修改商品下面的属性值
     */
    @PreAuthorize("@ss.hasPermi('goods:value:edit')")
    @Log(title = "商品下面的属性值", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsGoodsAttributeValue pmsGoodsAttributeValue) {
        return toAjax(pmsGoodsAttributeValueService.updatePmsGoodsAttributeValue(pmsGoodsAttributeValue));
    }

    /**
     * 删除商品下面的属性值
     */
    @PreAuthorize("@ss.hasPermi('goods:value:remove')")
    @Log(title = "商品下面的属性值", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsGoodsAttributeValueService.deletePmsGoodsAttributeValueByIds(ids));
    }
}
