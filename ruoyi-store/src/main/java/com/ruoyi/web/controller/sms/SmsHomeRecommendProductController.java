package com.ruoyi.web.controller.sms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.enums.StatusEnum;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.service.IPmsGoodsService;
import com.ruoyi.sms.domain.SmsHomeRecommendProduct;
import com.ruoyi.sms.service.ISmsHomeRecommendProductService;
import com.ruoyi.web.utils.AdminLoginUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 人气推荐商品Controller
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@RestController
@RequestMapping("/sms/SmsHomeRecommendProduct")
public class SmsHomeRecommendProductController extends BaseController {
    @Autowired
    private ISmsHomeRecommendProductService smsHomeRecommendProductService;
    @Autowired
    private IPmsGoodsService goodsService;
    /**
     * 查询人气推荐商品列表
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeRecommendProduct:list')")
    @GetMapping("/list")
    public TableDataInfo list(SmsHomeRecommendProduct smsHomeRecommendProduct) {
        startPage();
        smsHomeRecommendProduct.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        List<SmsHomeRecommendProduct> list = smsHomeRecommendProductService.selectSmsHomeRecommendProductList(smsHomeRecommendProduct);
        return getDataTable(list);
    }

    /**
     * 导出人气推荐商品列表
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeRecommendProduct:export')")
    @Log(title = "人气推荐商品", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SmsHomeRecommendProduct smsHomeRecommendProduct) {
        smsHomeRecommendProduct.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        List<SmsHomeRecommendProduct> list = smsHomeRecommendProductService.selectSmsHomeRecommendProductList(smsHomeRecommendProduct);
        ExcelUtil<SmsHomeRecommendProduct> util = new ExcelUtil<SmsHomeRecommendProduct>(SmsHomeRecommendProduct.class);
        return util.exportExcel(list, "SmsHomeRecommendProduct");
    }

    /**
     * 获取人气推荐商品详细信息
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeRecommendProduct:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(smsHomeRecommendProductService.selectSmsHomeRecommendProductById(id));
    }

    /**
     * 新增人气推荐商品
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeRecommendProduct:add')")
    @Log(title = "人气推荐商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult create(@RequestBody List<SmsHomeRecommendProduct> homeNewProducts) {
        for (SmsHomeRecommendProduct smsHomeBrand : homeNewProducts) {
            smsHomeBrand.setRecommendStatus(StatusEnum.YesNoType.YES.code());
            smsHomeBrand.setSort(0);
            smsHomeBrand.setStoreId(AdminLoginUtils.getInstance().getStoreId());
            SmsHomeRecommendProduct query = new SmsHomeRecommendProduct();
            query.setProductId(smsHomeBrand.getProductId());
            List<SmsHomeRecommendProduct> list = smsHomeRecommendProductService.selectSmsHomeRecommendProductList(query);
            if (list!=null && list.size()>0){

            }else {
                smsHomeRecommendProductService.insertSmsHomeRecommendProduct(smsHomeBrand);
            }

        }
        return toAjax(homeNewProducts.size());
    }

    /**
     * 修改人气推荐商品
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeRecommendProduct:edit')")
    @Log(title = "人气推荐商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SmsHomeRecommendProduct smsHomeRecommendProduct) {
        return toAjax(smsHomeRecommendProductService.updateSmsHomeRecommendProduct(smsHomeRecommendProduct));
    }

    /**
     * 删除人气推荐商品
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeRecommendProduct:remove')")
    @Log(title = "人气推荐商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(smsHomeRecommendProductService.deleteSmsHomeRecommendProductByIds(ids));
    }
    @ApiOperation("修改推荐排序")
    @RequestMapping(value = "/update/sort/{id}/{sort}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSort(@PathVariable Long id, @PathVariable Integer sort) {
        int count = smsHomeRecommendProductService.updateSort(id, sort);
        return toAjax(count);
    }

    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.GET)
    @ResponseBody
    public Object updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = smsHomeRecommendProductService.updateRecommendStatus(ids, recommendStatus);
        return toAjax(count);
    }
}
