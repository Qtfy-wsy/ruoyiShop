package com.ruoyi.web.controller.sms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.StatusEnum;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.sms.domain.SmsHomeNewProduct;
import com.ruoyi.sms.service.ISmsHomeNewProductService;
import com.ruoyi.web.utils.AdminLoginUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新鲜好物Controller
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@RestController
@RequestMapping("/sms/SmsHomeNewProduct")
public class SmsHomeNewProductController extends BaseController {
    @Autowired
    private ISmsHomeNewProductService smsHomeNewProductService;
    @Autowired
    private IPmsGoodsService goodsService;
    /**
     * 查询新鲜好物列表
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeNewProduct:list')")
    @GetMapping("/list")
    public TableDataInfo list(SmsHomeNewProduct smsHomeNewProduct) {
        startPage();
        smsHomeNewProduct.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        List<SmsHomeNewProduct> list = smsHomeNewProductService.selectSmsHomeNewProductList(smsHomeNewProduct);
        return getDataTable(list);
    }

    /**
     * 导出新鲜好物列表
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeNewProduct:export')")
    @Log(title = "新鲜好物", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SmsHomeNewProduct smsHomeNewProduct) {
        smsHomeNewProduct.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        List<SmsHomeNewProduct> list = smsHomeNewProductService.selectSmsHomeNewProductList(smsHomeNewProduct);
        ExcelUtil<SmsHomeNewProduct> util = new ExcelUtil<SmsHomeNewProduct>(SmsHomeNewProduct.class);
        return util.exportExcel(list, "SmsHomeNewProduct");
    }

    /**
     * 获取新鲜好物详细信息
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeNewProduct:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(smsHomeNewProductService.selectSmsHomeNewProductById(id));
    }

    /**
     * 新增新鲜好物
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeNewProduct:add')")
    @Log(title = "新鲜好物", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@RequestBody List<SmsHomeNewProduct> homeNewProducts) {
        for (SmsHomeNewProduct smsHomeBrand : homeNewProducts) {
            smsHomeBrand.setRecommendStatus(StatusEnum.YesNoType.YES.code());
            smsHomeBrand.setSort(0);
            smsHomeBrand.setStoreId(AdminLoginUtils.getInstance().getStoreId());
            SmsHomeNewProduct query = new SmsHomeNewProduct();
            query.setProductId(smsHomeBrand.getProductId());

            List<SmsHomeNewProduct> list = smsHomeNewProductService.selectSmsHomeNewProductList(query);
            if (list!=null && list.size()>0){

            }else {
                smsHomeNewProductService.insertSmsHomeNewProduct(smsHomeBrand);
            }

        }
        return toAjax(homeNewProducts.size());
    }

    /**
     * 修改新鲜好物
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeNewProduct:edit')")
    @Log(title = "新鲜好物", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SmsHomeNewProduct smsHomeNewProduct) {
        smsHomeNewProduct.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        return toAjax(smsHomeNewProductService.updateSmsHomeNewProduct(smsHomeNewProduct));
    }

    /**
     * 删除新鲜好物
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeNewProduct:remove')")
    @Log(title = "新鲜好物", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(smsHomeNewProductService.deleteSmsHomeNewProductByIds(ids));
    }
    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.GET)
    @ResponseBody
    public Object updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = smsHomeNewProductService.updateRecommendStatus(ids, recommendStatus);
        return toAjax(count);
    }

    @ApiOperation("修改推荐排序")
    @RequestMapping(value = "/update/sort/{id}/{sort}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSort(@PathVariable Long id, @PathVariable Integer sort) {
        int count = smsHomeNewProductService.updateSort(id, sort);
        return toAjax(count);
    }
}
