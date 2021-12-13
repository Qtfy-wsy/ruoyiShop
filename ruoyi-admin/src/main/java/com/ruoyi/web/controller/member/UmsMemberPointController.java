package com.ruoyi.web.controller.member;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.member.domain.UmsMemberPoint;
import com.ruoyi.member.service.IUmsMemberPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 会员积分详情Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
@RestController
@RequestMapping("/member/UmsMemberPoint")
public class UmsMemberPointController extends BaseController {
    @Autowired
    private IUmsMemberPointService umsMemberPointService;

    /**
     * 查询会员积分详情列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberPoint:list')")
    @GetMapping("/list")
    public TableDataInfo list(UmsMemberPoint umsMemberPoint) {
        startPage();
        List<UmsMemberPoint> list = umsMemberPointService.selectUmsMemberPointList(umsMemberPoint);
        return getDataTable(list);
    }

    /**
     * 导出会员积分详情列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberPoint:export')")
    @Log(title = "会员积分详情", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UmsMemberPoint umsMemberPoint) {
        List<UmsMemberPoint> list = umsMemberPointService.selectUmsMemberPointList(umsMemberPoint);
        ExcelUtil<UmsMemberPoint> util = new ExcelUtil<UmsMemberPoint>(UmsMemberPoint.class);
        return util.exportExcel(list, "UmsMemberPoint");
    }

    /**
     * 获取会员积分详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberPoint:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(umsMemberPointService.selectUmsMemberPointById(id));
    }

    /**
     * 新增会员积分详情
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberPoint:add')")
    @Log(title = "会员积分详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UmsMemberPoint umsMemberPoint) {
        return toAjax(umsMemberPointService.insertUmsMemberPoint(umsMemberPoint));
    }

    /**
     * 修改会员积分详情
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberPoint:edit')")
    @Log(title = "会员积分详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsMemberPoint umsMemberPoint) {
        return toAjax(umsMemberPointService.updateUmsMemberPoint(umsMemberPoint));
    }

    /**
     * 删除会员积分详情
     */
    @PreAuthorize("@ss.hasPermi('member:UmsMemberPoint:remove')")
    @Log(title = "会员积分详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(umsMemberPointService.deleteUmsMemberPointByIds(ids));
    }
}
