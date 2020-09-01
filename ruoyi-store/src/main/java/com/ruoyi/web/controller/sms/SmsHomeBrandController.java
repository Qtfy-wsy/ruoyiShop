package com.ruoyi.web.controller.sms;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.sms.domain.SmsHomeBrand;
import com.ruoyi.sms.service.ISmsHomeBrandService;
import com.ruoyi.web.utils.AdminLoginUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 首页推荐品牌Controller
 *
 * @author é­éåå
 * @date 2020-08-06
 */
@RestController
@RequestMapping("/sms/SmsHomeBrand")
public class SmsHomeBrandController extends BaseController {
    @Autowired
    private ISmsHomeBrandService smsHomeBrandService;

    /**
     * 查询首页推荐品牌列表
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeBrand:list')")
    @GetMapping("/list")
    public TableDataInfo list(SmsHomeBrand smsHomeBrand) {
        smsHomeBrand.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        startPage();
        List<SmsHomeBrand> list = smsHomeBrandService.selectSmsHomeBrandList(smsHomeBrand);
        return getDataTable(list);
    }

    /**
     * 导出首页推荐品牌列表
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeBrand:export')")
    @Log(title = "首页推荐品牌", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SmsHomeBrand smsHomeBrand) {
        smsHomeBrand.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        List<SmsHomeBrand> list = smsHomeBrandService.selectSmsHomeBrandList(smsHomeBrand);
        ExcelUtil<SmsHomeBrand> util = new ExcelUtil<SmsHomeBrand>(SmsHomeBrand.class);
        return util.exportExcel(list, "SmsHomeBrand");
    }

    /**
     * 获取首页推荐品牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeBrand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(smsHomeBrandService.selectSmsHomeBrandById(id));
    }

    @PreAuthorize("@ss.hasPermi('sms:SmsHomeBrand:add')")
    @Log(title = "首页推荐品牌", businessType = BusinessType.INSERT)
    @PostMapping
    public Object create(@RequestBody List<SmsHomeBrand> homeBrandList) {
        int count = smsHomeBrandService.create(homeBrandList);
        return  toAjax(count);
    }



    /**
     * 修改首页推荐品牌
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeBrand:edit')")
    @Log(title = "首页推荐品牌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SmsHomeBrand smsHomeBrand) {
        smsHomeBrand.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        return toAjax(smsHomeBrandService.updateSmsHomeBrand(smsHomeBrand));
    }

    /**
     * 删除首页推荐品牌
     */
    @PreAuthorize("@ss.hasPermi('sms:SmsHomeBrand:remove')")
    @Log(title = "首页推荐品牌", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(smsHomeBrandService.deleteSmsHomeBrandByIds(ids));
    }


    @ApiOperation("修改品牌排序")
    @RequestMapping(value = "/update/sort/{id}/{sort}", method = RequestMethod.POST)
    @ResponseBody
    public Object updateSort(@PathVariable Long id, @PathVariable Integer sort) {
        int count = smsHomeBrandService.updateSort(id, sort);
        return toAjax(count);

    }


    @ApiOperation("批量修改推荐状态")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.GET)
    @ResponseBody
    public Object updateRecommendStatus(@RequestParam("ids") List<Long> ids, @RequestParam Integer recommendStatus) {
        int count = smsHomeBrandService.updateRecommendStatus(ids, recommendStatus);
        return toAjax(count);
    }
}
