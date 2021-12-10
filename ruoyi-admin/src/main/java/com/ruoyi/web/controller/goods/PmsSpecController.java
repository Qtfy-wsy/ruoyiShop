package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsSpec;
import com.ruoyi.goods.service.IPmsSpecService;
import com.ruoyi.goods.service.IPmsSpecValueService;
import com.ruoyi.web.utils.AdminLoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 规格Controller
 *
 * @author 商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/spec")
public class PmsSpecController extends BaseController {
    @Autowired
    private IPmsSpecService pmsSpecService;
    /**
     * 注入规格值服务接口
     */
    @Autowired
    private IPmsSpecValueService specValueService;

    /**
     * 查询规格列表
     */
    @PreAuthorize("@ss.hasPermi('goods:spec:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsSpec pmsSpec) {
        startPage();
        List<PmsSpec> list = pmsSpecService.selectPmsSpecList(pmsSpec);
        return getDataTable(list);
    }

    /**
     * 导出规格列表
     */
    @PreAuthorize("@ss.hasPermi('goods:spec:export')")
    @Log(title = "规格", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsSpec pmsSpec) {
        List<PmsSpec> list = pmsSpecService.selectPmsSpecList(pmsSpec);
        ExcelUtil<PmsSpec> util = new ExcelUtil<PmsSpec>(PmsSpec.class);
        return util.exportExcel(list, "spec");
    }

    /**
     * 获取规格详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:spec:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsSpecService.selectPmsSpecById(id));
    }

    /**
     * 新增规格
     */
    @PreAuthorize("@ss.hasPermi('goods:spec:add')")
    @Log(title = "规格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsSpec pmsSpec) {
        return toAjax(pmsSpecService.addSpec(pmsSpec.setDetaultValuesForAdd(AdminLoginUtils.getInstance().getManagerName())));
    }

    /**
     * 修改规格
     */
    @PreAuthorize("@ss.hasPermi('goods:spec:edit')")
    @Log(title = "规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsSpec pmsSpec) {
        return toAjax(pmsSpecService.updateSpec(pmsSpec.setDetaultValuesForUpdate(AdminLoginUtils.getInstance().getManagerName())));
    }

    /**
     * 删除规格
     */
    @PreAuthorize("@ss.hasPermi('goods:spec:remove')")
    @Log(title = "规格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsSpecService.deleteSpecs(Arrays.stream(ids).map(id -> PmsSpec.buildForDelete(id, AdminLoginUtils.getInstance().getManagerName())).collect(Collectors.toList())));
    }

    /**
     * 查询规格值是否可以删除
     *
     * @param specValueId 规格值id
     * @return true 可以删除 false 不可以删除
     */
    @GetMapping("/isspecvaluecandelete/{specValueId}")
    public boolean isSpecValueCanDelete(@PathVariable String specValueId) {
        return specValueService.isSpecValueCanDelete(specValueId);
    }
}
