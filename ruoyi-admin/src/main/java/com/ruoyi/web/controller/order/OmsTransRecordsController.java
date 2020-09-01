package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsTransRecords;
import com.ruoyi.order.service.IOmsTransRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付流水Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsTransRecords")
public class OmsTransRecordsController extends BaseController {
    @Autowired
    private IOmsTransRecordsService omsTransRecordsService;

    /**
     * 查询支付流水列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsTransRecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsTransRecords omsTransRecords) {
        startPage();
        List<OmsTransRecords> list = omsTransRecordsService.selectOmsTransRecordsList(omsTransRecords);
        return getDataTable(list);
    }

    /**
     * 导出支付流水列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsTransRecords:export')")
    @Log(title = "支付流水", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsTransRecords omsTransRecords) {
        List<OmsTransRecords> list = omsTransRecordsService.selectOmsTransRecordsList(omsTransRecords);
        ExcelUtil<OmsTransRecords> util = new ExcelUtil<OmsTransRecords>(OmsTransRecords.class);
        return util.exportExcel(list, "OmsTransRecords");
    }

    /**
     * 获取支付流水详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsTransRecords:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsTransRecordsService.selectOmsTransRecordsById(id));
    }

    /**
     * 新增支付流水
     */
    @PreAuthorize("@ss.hasPermi('order:OmsTransRecords:add')")
    @Log(title = "支付流水", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsTransRecords omsTransRecords) {
        return toAjax(omsTransRecordsService.insertOmsTransRecords(omsTransRecords));
    }

    /**
     * 修改支付流水
     */
    @PreAuthorize("@ss.hasPermi('order:OmsTransRecords:edit')")
    @Log(title = "支付流水", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsTransRecords omsTransRecords) {
        return toAjax(omsTransRecordsService.updateOmsTransRecords(omsTransRecords));
    }

    /**
     * 删除支付流水
     */
    @PreAuthorize("@ss.hasPermi('order:OmsTransRecords:remove')")
    @Log(title = "支付流水", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsTransRecordsService.deleteOmsTransRecordsByIds(ids));
    }
}
