package com.ruoyi.web.controller.setting;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.setting.bean.PaySetCommon;
import com.ruoyi.setting.domain.LsPaySetting;
import com.ruoyi.setting.service.ILsPaySettingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 支付设置Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-28
 */
@RestController
@RequestMapping("/setting/LsPaySetting")
public class LsPaySettingController extends BaseController {
    @Autowired
    private ILsPaySettingService lsPaySettingService;

    /**
     * 查询支付设置列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsPaySetting:list')")
    @GetMapping("/list")
    public TableDataInfo list(LsPaySetting lsPaySetting) {
        startPage();
        List<LsPaySetting> list = lsPaySettingService.selectLsPaySettingList(lsPaySetting);
        return getDataTable(list);
    }

    /**
     * 查询支付设置
     *
     * @return 支付设置信息
     */
    @GetMapping("/payset")
    @ApiOperation(value = "查询支付设置", notes = "查询支付设置（需要认证）")
    @PreAuthorize("@ss.hasPermi('setting:LsPaySetting:list')")
    public PaySetCommon queryPaySet() {
        return lsPaySettingService.queryPaySet();
    }

    /**
     * 修改支付设置
     *
     * @param paySetCommon 实体类参数
     * @param codeType     支付设置类型 1 支付宝 2 微信 3 银联
     * @return 成功>=1 否则失败 -1 支付设置类型不存在
     */
    @PostMapping("/payset/{codeType}")
    @ApiOperation(value = "修改支付设置", notes = "修改支付设置（需要认证）")
    @PreAuthorize("@ss.hasPermi('setting:LsPaySetting:add')")
    public int editPaySet(@RequestBody PaySetCommon paySetCommon, @PathVariable String codeType) {
        return lsPaySettingService.editPaySet(paySetCommon, codeType);
    }

    /**
     * 导出支付设置列表
     */
    @PreAuthorize("@ss.hasPermi('setting:LsPaySetting:export')")
    @Log(title = "支付设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LsPaySetting lsPaySetting) {
        List<LsPaySetting> list = lsPaySettingService.selectLsPaySettingList(lsPaySetting);
        ExcelUtil<LsPaySetting> util = new ExcelUtil<LsPaySetting>(LsPaySetting.class);
        return util.exportExcel(list, "LsPaySetting");
    }

    /**
     * 获取支付设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('setting:LsPaySetting:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(lsPaySettingService.selectLsPaySettingById(id));
    }

    /**
     * 新增支付设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsPaySetting:add')")
    @Log(title = "支付设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LsPaySetting lsPaySetting) {
        return toAjax(lsPaySettingService.insertLsPaySetting(lsPaySetting));
    }

    /**
     * 修改支付设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsPaySetting:edit')")
    @Log(title = "支付设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LsPaySetting lsPaySetting) {
        return toAjax(lsPaySettingService.updateLsPaySetting(lsPaySetting));
    }

    /**
     * 删除支付设置
     */
    @PreAuthorize("@ss.hasPermi('setting:LsPaySetting:remove')")
    @Log(title = "支付设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(lsPaySettingService.deleteLsPaySettingByIds(ids));
    }
}
