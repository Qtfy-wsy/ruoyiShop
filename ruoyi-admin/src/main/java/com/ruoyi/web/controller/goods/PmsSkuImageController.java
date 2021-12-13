package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsSkuImage;
import com.ruoyi.goods.service.IPmsSkuImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单品和图片的关联Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/skuImage")
public class PmsSkuImageController extends BaseController {
    @Autowired
    private IPmsSkuImageService pmsSkuImageService;

    /**
     * 查询单品和图片的关联列表
     */
    @PreAuthorize("@ss.hasPermi('goods:image:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsSkuImage pmsSkuImage) {
        startPage();
        List<PmsSkuImage> list = pmsSkuImageService.selectPmsSkuImageList(pmsSkuImage);
        return getDataTable(list);
    }

    /**
     * 导出单品和图片的关联列表
     */
    @PreAuthorize("@ss.hasPermi('goods:image:export')")
    @Log(title = "单品和图片的关联", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsSkuImage pmsSkuImage) {
        List<PmsSkuImage> list = pmsSkuImageService.selectPmsSkuImageList(pmsSkuImage);
        ExcelUtil<PmsSkuImage> util = new ExcelUtil<PmsSkuImage>(PmsSkuImage.class);
        return util.exportExcel(list, "image");
    }

    /**
     * 获取单品和图片的关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:image:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSkuImageService.selectPmsSkuImageById(id));
    }

    /**
     * 新增单品和图片的关联
     */
    @PreAuthorize("@ss.hasPermi('goods:image:add')")
    @Log(title = "单品和图片的关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsSkuImage pmsSkuImage) {
        return toAjax(pmsSkuImageService.insertPmsSkuImage(pmsSkuImage));
    }

    /**
     * 修改单品和图片的关联
     */
    @PreAuthorize("@ss.hasPermi('goods:image:edit')")
    @Log(title = "单品和图片的关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsSkuImage pmsSkuImage) {
        return toAjax(pmsSkuImageService.updatePmsSkuImage(pmsSkuImage));
    }

    /**
     * 删除单品和图片的关联
     */
    @PreAuthorize("@ss.hasPermi('goods:image:remove')")
    @Log(title = "单品和图片的关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSkuImageService.deletePmsSkuImageByIds(ids));
    }
}
