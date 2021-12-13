package com.ruoyi.web.controller.setting;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.setting.domain.LsWxCustomerLink;
import com.ruoyi.setting.service.ILsWxCustomerLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 微信登录和商城用户的关联Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-29
 */
@RestController
@RequestMapping("/setting/LsWxCustomerLink")
public class LsWxCustomerLinkController extends BaseController {
    @Autowired
    private ILsWxCustomerLinkService lsWxCustomerLinkService;

    /**
     * 查询微信登录和商城用户的关联列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsWxCustomerLink:list')")
    @GetMapping("/list")
    public TableDataInfo list(LsWxCustomerLink lsWxCustomerLink) {
        startPage();
        List<LsWxCustomerLink> list = lsWxCustomerLinkService.selectLsWxCustomerLinkList(lsWxCustomerLink);
        return getDataTable(list);
    }

    /**
     * 导出微信登录和商城用户的关联列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsWxCustomerLink:export')")
    @Log(title = "微信登录和商城用户的关联", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LsWxCustomerLink lsWxCustomerLink) {
        List<LsWxCustomerLink> list = lsWxCustomerLinkService.selectLsWxCustomerLinkList(lsWxCustomerLink);
        ExcelUtil<LsWxCustomerLink> util = new ExcelUtil<LsWxCustomerLink>(LsWxCustomerLink.class);
        return util.exportExcel(list, "LsWxCustomerLink");
    }

    /**
     * 获取微信登录和商城用户的关联详细信息
     */
    @PreAuthorize("@ss.hasPermi('setting:LsWxCustomerLink:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(lsWxCustomerLinkService.selectLsWxCustomerLinkById(id));
    }

    /**
     * 新增微信登录和商城用户的关联
     */
    @PreAuthorize("@ss.hasPermi('setting:LsWxCustomerLink:add')")
    @Log(title = "微信登录和商城用户的关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LsWxCustomerLink lsWxCustomerLink) {
        return toAjax(lsWxCustomerLinkService.insertLsWxCustomerLink(lsWxCustomerLink));
    }

    /**
     * 修改微信登录和商城用户的关联
     */
    @PreAuthorize("@ss.hasPermi('setting:LsWxCustomerLink:edit')")
    @Log(title = "微信登录和商城用户的关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LsWxCustomerLink lsWxCustomerLink) {
        return toAjax(lsWxCustomerLinkService.updateLsWxCustomerLink(lsWxCustomerLink));
    }

    /**
     * 删除微信登录和商城用户的关联
     */
    @PreAuthorize("@ss.hasPermi('setting:LsWxCustomerLink:remove')")
    @Log(title = "微信登录和商城用户的关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(lsWxCustomerLinkService.deleteLsWxCustomerLinkByIds(ids));
    }
}
