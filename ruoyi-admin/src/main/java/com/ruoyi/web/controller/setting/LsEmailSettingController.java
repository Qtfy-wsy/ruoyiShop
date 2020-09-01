package com.ruoyi.web.controller.setting;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.setting.domain.LsEmailSetting;
import com.ruoyi.setting.service.ILsEmailSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 邮箱设置Controller
 *
 * @author 魔金商城
 * @date 2020-07-28
 */
@RestController
@RequestMapping("/setting/LsEmailSetting")
public class LsEmailSettingController extends BaseController {
    @Autowired
    private ILsEmailSettingService lsEmailSettingService;

    /**
     * 查询邮箱设置列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsEmailSetting:list')")
    @GetMapping("/list")
    public TableDataInfo list(LsEmailSetting lsEmailSetting) {
        startPage();
        List<LsEmailSetting> list = lsEmailSettingService.selectLsEmailSettingList(lsEmailSetting);
        return getDataTable(list);
    }

    /**
     * 导出邮箱设置列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsEmailSetting:export')")
    @Log(title = "邮箱设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LsEmailSetting lsEmailSetting) {
        List<LsEmailSetting> list = lsEmailSettingService.selectLsEmailSettingList(lsEmailSetting);
        ExcelUtil<LsEmailSetting> util = new ExcelUtil<LsEmailSetting>(LsEmailSetting.class);
        return util.exportExcel(list, "LsEmailSetting");
    }

    /**
     * 获取邮箱设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('setting:LsEmailSetting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(lsEmailSettingService.selectLsEmailSettingById(id));
    }

    /**
     * 新增邮箱设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsEmailSetting:add')")
    @Log(title = "邮箱设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LsEmailSetting lsEmailSetting) {
        return toAjax(lsEmailSettingService.insertLsEmailSetting(lsEmailSetting));
    }

    /**
     * 修改邮箱设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsEmailSetting:edit')")
    @Log(title = "邮箱设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LsEmailSetting lsEmailSetting) {
        return toAjax(lsEmailSettingService.updateLsEmailSetting(lsEmailSetting));
    }

    /**
     * 删除邮箱设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsEmailSetting:remove')")
    @Log(title = "邮箱设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(lsEmailSettingService.deleteLsEmailSettingByIds(ids));
    }
}
