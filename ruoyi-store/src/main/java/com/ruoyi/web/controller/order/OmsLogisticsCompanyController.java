package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsLogisticsCompany;
import com.ruoyi.order.service.IOmsLogisticsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物流公司Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsLogisticsCompany")
public class OmsLogisticsCompanyController extends BaseController {
    @Autowired
    private IOmsLogisticsCompanyService omsLogisticsCompanyService;

    /**
     * 查询物流公司列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompany:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsLogisticsCompany omsLogisticsCompany) {
        startPage();
        List<OmsLogisticsCompany> list = omsLogisticsCompanyService.selectOmsLogisticsCompanyList(omsLogisticsCompany);
        return getDataTable(list);
    }

    /**
     * 导出物流公司列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompany:export')")
    @Log(title = "物流公司", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsLogisticsCompany omsLogisticsCompany) {
        List<OmsLogisticsCompany> list = omsLogisticsCompanyService.selectOmsLogisticsCompanyList(omsLogisticsCompany);
        ExcelUtil<OmsLogisticsCompany> util = new ExcelUtil<OmsLogisticsCompany>(OmsLogisticsCompany.class);
        return util.exportExcel(list, "OmsLogisticsCompany");
    }

    /**
     * 获取物流公司详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompany:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsLogisticsCompanyService.selectOmsLogisticsCompanyById(id));
    }

    /**
     * 新增物流公司
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompany:add')")
    @Log(title = "物流公司", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsLogisticsCompany omsLogisticsCompany) {
        return toAjax(omsLogisticsCompanyService.insertOmsLogisticsCompany(omsLogisticsCompany));
    }

    /**
     * 修改物流公司
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompany:edit')")
    @Log(title = "物流公司", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsLogisticsCompany omsLogisticsCompany) {
        return toAjax(omsLogisticsCompanyService.updateOmsLogisticsCompany(omsLogisticsCompany));
    }

    /**
     * 删除物流公司
     */
    @PreAuthorize("@ss.hasPermi('order:OmsLogisticsCompany:remove')")
    @Log(title = "物流公司", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsLogisticsCompanyService.deleteOmsLogisticsCompanyByIds(ids));
    }
}
