package com.ruoyi.web.controller.member;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.member.domain.UmsMemberLevel;
import com.ruoyi.member.service.IUmsMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员等级Controller
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
@RestController
@RequestMapping("/member/UmsMemberLevel")
public class UmsMemberLevelController extends BaseController {
    @Autowired
    private IUmsMemberLevelService umsMemberLevelService;

    /**
     * 查询会员等级列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberLevel:list')")
    @GetMapping("/list")
    public TableDataInfo list(UmsMemberLevel umsMemberLevel) {
        startPage();
        List<UmsMemberLevel> list = umsMemberLevelService.selectUmsMemberLevelList(umsMemberLevel);
        return getDataTable(list);
    }

    /**
     * 导出会员等级列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberLevel:export')")
    @Log(title = "会员等级", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UmsMemberLevel umsMemberLevel) {
        List<UmsMemberLevel> list = umsMemberLevelService.selectUmsMemberLevelList(umsMemberLevel);
        ExcelUtil<UmsMemberLevel> util = new ExcelUtil<UmsMemberLevel>(UmsMemberLevel.class);
        return util.exportExcel(list, "UmsMemberLevel");
    }

    /**
     * 获取会员等级详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberLevel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(umsMemberLevelService.selectUmsMemberLevelById(id));
    }

    /**
     * 新增会员等级
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberLevel:add')")
    @Log(title = "会员等级", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UmsMemberLevel umsMemberLevel) {
        return toAjax(umsMemberLevelService.insertUmsMemberLevel(umsMemberLevel));
    }

    /**
     * 修改会员等级
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberLevel:edit')")
    @Log(title = "会员等级", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsMemberLevel umsMemberLevel) {
        return toAjax(umsMemberLevelService.updateUmsMemberLevel(umsMemberLevel));
    }

    /**
     * 删除会员等级
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberLevel:remove')")
    @Log(title = "会员等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(umsMemberLevelService.deleteUmsMemberLevelByIds(ids));
    }
}
