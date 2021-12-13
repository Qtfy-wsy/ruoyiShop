package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsGoodsImage;
import com.ruoyi.goods.service.IPmsGoodsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品的图片Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/image")
public class PmsGoodsImageController extends BaseController {
    @Autowired
    private IPmsGoodsImageService pmsGoodsImageService;

    /**
     * 查询商品的图片列表
     */
    @PreAuthorize("@ss.hasPermi('goods:image:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsGoodsImage pmsGoodsImage) {
        startPage();
        List<PmsGoodsImage> list = pmsGoodsImageService.selectPmsGoodsImageList(pmsGoodsImage);
        return getDataTable(list);
    }

    /**
     * 导出商品的图片列表
     */
    @PreAuthorize("@ss.hasPermi('goods:image:export')")
    @Log(title = "商品的图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsGoodsImage pmsGoodsImage) {
        List<PmsGoodsImage> list = pmsGoodsImageService.selectPmsGoodsImageList(pmsGoodsImage);
        ExcelUtil<PmsGoodsImage> util = new ExcelUtil<PmsGoodsImage>(PmsGoodsImage.class);
        return util.exportExcel(list, "image");
    }

    /**
     * 获取商品的图片详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:image:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsGoodsImageService.selectPmsGoodsImageById(id));
    }

    /**
     * 新增商品的图片
     */
    @PreAuthorize("@ss.hasPermi('goods:image:add')")
    @Log(title = "商品的图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsGoodsImage pmsGoodsImage) {
        return toAjax(pmsGoodsImageService.insertPmsGoodsImage(pmsGoodsImage));
    }

    /**
     * 修改商品的图片
     */
    @PreAuthorize("@ss.hasPermi('goods:image:edit')")
    @Log(title = "商品的图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsGoodsImage pmsGoodsImage) {
        return toAjax(pmsGoodsImageService.updatePmsGoodsImage(pmsGoodsImage));
    }

    /**
     * 删除商品的图片
     */
    @PreAuthorize("@ss.hasPermi('goods:image:remove')")
    @Log(title = "商品的图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsGoodsImageService.deletePmsGoodsImageByIds(ids));
    }
}
