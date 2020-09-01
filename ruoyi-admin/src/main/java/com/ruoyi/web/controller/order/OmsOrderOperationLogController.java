package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsOrderOperationLog;
import com.ruoyi.order.service.IOmsOrderOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 订单操作日志Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsOrderOperationLog")
public class OmsOrderOperationLogController extends BaseController {
    @Autowired
    private IOmsOrderOperationLogService omsOrderOperationLogService;

    /**
     * 查询订单操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderOperationLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsOrderOperationLog omsOrderOperationLog) {
        startPage();
        List<OmsOrderOperationLog> list = omsOrderOperationLogService.selectOmsOrderOperationLogList(omsOrderOperationLog);
        return getDataTable(list);
    }

    /**
     * 导出订单操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderOperationLog:export')")
    @Log(title = "订单操作日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsOrderOperationLog omsOrderOperationLog) {
        List<OmsOrderOperationLog> list = omsOrderOperationLogService.selectOmsOrderOperationLogList(omsOrderOperationLog);
        ExcelUtil<OmsOrderOperationLog> util = new ExcelUtil<OmsOrderOperationLog>(OmsOrderOperationLog.class);
        return util.exportExcel(list, "OmsOrderOperationLog");
    }

    /**
     * 获取订单操作日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderOperationLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsOrderOperationLogService.selectOmsOrderOperationLogById(id));
    }

    /**
     * 新增订单操作日志
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderOperationLog:add')")
    @Log(title = "订单操作日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsOrderOperationLog omsOrderOperationLog) {
        return toAjax(omsOrderOperationLogService.insertOmsOrderOperationLog(omsOrderOperationLog));
    }

    /**
     * 修改订单操作日志
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderOperationLog:edit')")
    @Log(title = "订单操作日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsOrderOperationLog omsOrderOperationLog) {
        return toAjax(omsOrderOperationLogService.updateOmsOrderOperationLog(omsOrderOperationLog));
    }

    /**
     * 删除订单操作日志
     */
    @PreAuthorize("@ss.hasPermi('order:OmsOrderOperationLog:remove')")
    @Log(title = "订单操作日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsOrderOperationLogService.deleteOmsOrderOperationLogByIds(ids));
    }
}
