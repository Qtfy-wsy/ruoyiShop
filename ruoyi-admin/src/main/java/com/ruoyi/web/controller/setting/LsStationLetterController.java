package com.ruoyi.web.controller.setting;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.setting.domain.LsStationLetter;
import com.ruoyi.setting.service.ILsStationLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 站内信Controller
 *
 * @author 魔金商城
 * @date 2020-07-29
 */
@RestController
@RequestMapping("/setting/LsStationLetter")
public class LsStationLetterController extends BaseController {
    @Autowired
    private ILsStationLetterService lsStationLetterService;

    /**
     * 查询站内信列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsStationLetter:list')")
    @GetMapping("/list")
    public TableDataInfo list(LsStationLetter lsStationLetter) {
        startPage();
        List<LsStationLetter> list = lsStationLetterService.selectLsStationLetterList(lsStationLetter);
        return getDataTable(list);
    }

    /**
     * 导出站内信列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsStationLetter:export')")
    @Log(title = "站内信", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LsStationLetter lsStationLetter) {
        List<LsStationLetter> list = lsStationLetterService.selectLsStationLetterList(lsStationLetter);
        ExcelUtil<LsStationLetter> util = new ExcelUtil<LsStationLetter>(LsStationLetter.class);
        return util.exportExcel(list, "LsStationLetter");
    }

    /**
     * 获取站内信详细信息
     */
    @PreAuthorize("@ss.hasPermi('setting:LsStationLetter:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(lsStationLetterService.selectLsStationLetterById(id));
    }

    /**
     * 新增站内信
     */
    @PreAuthorize("@ss.hasPermi('setting:LsStationLetter:add')")
    @Log(title = "站内信", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LsStationLetter lsStationLetter) {
        return toAjax(lsStationLetterService.insertLsStationLetter(lsStationLetter));
    }

    /**
     * 修改站内信
     */
    @PreAuthorize("@ss.hasPermi('setting:LsStationLetter:edit')")
    @Log(title = "站内信", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LsStationLetter lsStationLetter) {
        return toAjax(lsStationLetterService.updateLsStationLetter(lsStationLetter));
    }

    /**
     * 删除站内信
     */
    @PreAuthorize("@ss.hasPermi('setting:LsStationLetter:remove')")
    @Log(title = "站内信", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(lsStationLetterService.deleteLsStationLetterByIds(ids));
    }
}
