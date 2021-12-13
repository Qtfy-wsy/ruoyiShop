package com.ruoyi.web.controller.setting;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.setting.domain.LsSmsSetting;
import com.ruoyi.setting.service.ILsSmsSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短信接口设置Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@RestController
@RequestMapping("/setting/LsSmsSetting")
public class LsSmsSettingController extends BaseController {
    @Autowired
    private ILsSmsSettingService lsSmsSettingService;

    /**
     * 查询短信接口设置列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSmsSetting:list')")
    @GetMapping("/list")
    public TableDataInfo list(LsSmsSetting lsSmsSetting) {
        startPage();
        List<LsSmsSetting> list = lsSmsSettingService.selectLsSmsSettingList(lsSmsSetting);
        return getDataTable(list);
    }

    /**
     * 导出短信接口设置列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSmsSetting:export')")
    @Log(title = "短信接口设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LsSmsSetting lsSmsSetting) {
        List<LsSmsSetting> list = lsSmsSettingService.selectLsSmsSettingList(lsSmsSetting);
        ExcelUtil<LsSmsSetting> util = new ExcelUtil<LsSmsSetting>(LsSmsSetting.class);
        return util.exportExcel(list, "LsSmsSetting");
    }

    /**
     * 获取短信接口设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSmsSetting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(lsSmsSettingService.selectLsSmsSettingById(id));
    }

    /**
     * 新增短信接口设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSmsSetting:add')")
    @Log(title = "短信接口设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LsSmsSetting lsSmsSetting) {
        return toAjax(lsSmsSettingService.insertLsSmsSetting(lsSmsSetting));
    }

    /**
     * 修改短信接口设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSmsSetting:edit')")
    @Log(title = "短信接口设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LsSmsSetting lsSmsSetting) {
        return toAjax(lsSmsSettingService.updateLsSmsSetting(lsSmsSetting));
    }

    /**
     * 删除短信接口设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsSmsSetting:remove')")
    @Log(title = "短信接口设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(lsSmsSettingService.deleteLsSmsSettingByIds(ids));
    }
}
