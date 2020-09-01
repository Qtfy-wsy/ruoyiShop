package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsBillingRecords;
import com.ruoyi.order.service.IOmsBillingRecordsService;
import com.ruoyi.web.utils.AdminLoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 账单记录Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsBillingRecords")
public class OmsBillingRecordsController extends BaseController {
    @Autowired
    private IOmsBillingRecordsService omsBillingRecordsService;

    /**
     * 查询账单记录列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBillingRecords:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsBillingRecords omsBillingRecords) {
        omsBillingRecords.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        startPage();
        List<OmsBillingRecords> list = omsBillingRecordsService.selectOmsBillingRecordsList(omsBillingRecords);
        return getDataTable(list);
    }

    /**
     * 导出账单记录列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBillingRecords:export')")
    @Log(title = "账单记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsBillingRecords omsBillingRecords) {
        omsBillingRecords.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        List<OmsBillingRecords> list = omsBillingRecordsService.selectOmsBillingRecordsList(omsBillingRecords);
        ExcelUtil<OmsBillingRecords> util = new ExcelUtil<OmsBillingRecords>(OmsBillingRecords.class);
        return util.exportExcel(list, "OmsBillingRecords");
    }

    /**
     * 获取账单记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBillingRecords:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsBillingRecordsService.selectOmsBillingRecordsById(id));
    }

    /**
     * 新增账单记录
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBillingRecords:add')")
    @Log(title = "账单记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsBillingRecords omsBillingRecords) {
        return toAjax(omsBillingRecordsService.insertOmsBillingRecords(omsBillingRecords));
    }

    /**
     * 修改账单记录
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBillingRecords:edit')")
    @Log(title = "账单记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsBillingRecords omsBillingRecords) {
        return toAjax(omsBillingRecordsService.updateOmsBillingRecords(omsBillingRecords));
    }

    /**
     * 删除账单记录
     */
    @PreAuthorize("@ss.hasPermi('order:OmsBillingRecords:remove')")
    @Log(title = "账单记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsBillingRecordsService.deleteOmsBillingRecordsByIds(ids));
    }
}
