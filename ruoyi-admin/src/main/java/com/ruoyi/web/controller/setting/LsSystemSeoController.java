package com.ruoyi.web.controller.setting;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.setting.domain.LsSystemSeo;
import com.ruoyi.setting.service.ILsSystemSeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统seo设置Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
@RestController
@RequestMapping("/setting/LsSystemSeo")
public class LsSystemSeoController extends BaseController {
    @Autowired
    private ILsSystemSeoService lsSystemSeoService;

    /**
     * 查询系统seo设置列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSystemSeo:list')")
    @GetMapping("/list")
    public TableDataInfo list(LsSystemSeo lsSystemSeo) {
        startPage();
        List<LsSystemSeo> list = lsSystemSeoService.selectLsSystemSeoList(lsSystemSeo);
        return getDataTable(list);
    }

    /**
     * 导出系统seo设置列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSystemSeo:export')")
    @Log(title = "系统seo设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LsSystemSeo lsSystemSeo) {
        List<LsSystemSeo> list = lsSystemSeoService.selectLsSystemSeoList(lsSystemSeo);
        ExcelUtil<LsSystemSeo> util = new ExcelUtil<LsSystemSeo>(LsSystemSeo.class);
        return util.exportExcel(list, "LsSystemSeo");
    }

    /**
     * 获取系统seo设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSystemSeo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(lsSystemSeoService.selectLsSystemSeoById(id));
    }

    /**
     * 新增系统seo设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSystemSeo:add')")
    @Log(title = "系统seo设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LsSystemSeo lsSystemSeo) {
        return toAjax(lsSystemSeoService.insertLsSystemSeo(lsSystemSeo));
    }

    /**
     * 修改系统seo设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSystemSeo:edit')")
    @Log(title = "系统seo设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LsSystemSeo lsSystemSeo) {
        return toAjax(lsSystemSeoService.updateLsSystemSeo(lsSystemSeo));
    }

    /**
     * 删除系统seo设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSystemSeo:remove')")
    @Log(title = "系统seo设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(lsSystemSeoService.deleteLsSystemSeoByIds(ids));
    }
}
