package com.ruoyi.web.controller.member;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.member.domain.UmsPreDepositRecord;
import com.ruoyi.member.service.IUmsPreDepositRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员预存款记录Controller
 *
 * @author 魔金商城
 * @date 2020-07-25
 */
@RestController
@RequestMapping("/member/UmsPreDepositRecord")
public class UmsPreDepositRecordController extends BaseController {
    @Autowired
    private IUmsPreDepositRecordService umsPreDepositRecordService;

    /**
     * 查询会员预存款记录列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsPreDepositRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(UmsPreDepositRecord umsPreDepositRecord) {
        startPage();
        List<UmsPreDepositRecord> list = umsPreDepositRecordService.selectUmsPreDepositRecordList(umsPreDepositRecord);
        return getDataTable(list);
    }

    /**
     * 导出会员预存款记录列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsPreDepositRecord:export')")
    @Log(title = "会员预存款记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UmsPreDepositRecord umsPreDepositRecord) {
        List<UmsPreDepositRecord> list = umsPreDepositRecordService.selectUmsPreDepositRecordList(umsPreDepositRecord);
        ExcelUtil<UmsPreDepositRecord> util = new ExcelUtil<UmsPreDepositRecord>(UmsPreDepositRecord.class);
        return util.exportExcel(list, "UmsPreDepositRecord");
    }

    /**
     * 获取会员预存款记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:UmsPreDepositRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(umsPreDepositRecordService.selectUmsPreDepositRecordById(id));
    }

    /**
     * 新增会员预存款记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsPreDepositRecord:add')")
    @Log(title = "会员预存款记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UmsPreDepositRecord umsPreDepositRecord) {
        return toAjax(umsPreDepositRecordService.insertUmsPreDepositRecord(umsPreDepositRecord));
    }

    /**
     * 修改会员预存款记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsPreDepositRecord:edit')")
    @Log(title = "会员预存款记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsPreDepositRecord umsPreDepositRecord) {
        return toAjax(umsPreDepositRecordService.updateUmsPreDepositRecord(umsPreDepositRecord));
    }

    /**
     * 删除会员预存款记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsPreDepositRecord:remove')")
    @Log(title = "会员预存款记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(umsPreDepositRecordService.deleteUmsPreDepositRecordByIds(ids));
    }
}
