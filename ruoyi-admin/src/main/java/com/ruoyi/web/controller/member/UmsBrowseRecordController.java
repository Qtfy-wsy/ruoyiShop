package com.ruoyi.web.controller.member;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.member.domain.UmsBrowseRecord;
import com.ruoyi.member.service.IUmsBrowseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员浏览记录Controller
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
@RestController
@RequestMapping("/member/UmsBrowseRecord")
public class UmsBrowseRecordController extends BaseController {
    @Autowired
    private IUmsBrowseRecordService umsBrowseRecordService;

    /**
     * 查询会员浏览记录列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsBrowseRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(UmsBrowseRecord umsBrowseRecord) {
        startPage();
        List<UmsBrowseRecord> list = umsBrowseRecordService.selectUmsBrowseRecordList(umsBrowseRecord);
        return getDataTable(list);
    }

    /**
     * 导出会员浏览记录列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsBrowseRecord:export')")
    @Log(title = "会员浏览记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UmsBrowseRecord umsBrowseRecord) {
        List<UmsBrowseRecord> list = umsBrowseRecordService.selectUmsBrowseRecordList(umsBrowseRecord);
        ExcelUtil<UmsBrowseRecord> util = new ExcelUtil<UmsBrowseRecord>(UmsBrowseRecord.class);
        return util.exportExcel(list, "UmsBrowseRecord");
    }

    /**
     * 获取会员浏览记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:UmsBrowseRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(umsBrowseRecordService.selectUmsBrowseRecordById(id));
    }

    /**
     * 新增会员浏览记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsBrowseRecord:add')")
    @Log(title = "会员浏览记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UmsBrowseRecord umsBrowseRecord) {
        return toAjax(umsBrowseRecordService.insertUmsBrowseRecord(umsBrowseRecord));
    }

    /**
     * 修改会员浏览记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsBrowseRecord:edit')")
    @Log(title = "会员浏览记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsBrowseRecord umsBrowseRecord) {
        return toAjax(umsBrowseRecordService.updateUmsBrowseRecord(umsBrowseRecord));
    }

    /**
     * 删除会员浏览记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsBrowseRecord:remove')")
    @Log(title = "会员浏览记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(umsBrowseRecordService.deleteUmsBrowseRecordByIds(ids));
    }
}
