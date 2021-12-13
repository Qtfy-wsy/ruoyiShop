package com.ruoyi.web.controller.member;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.bean.WithdrawResponse;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.member.domain.UmsWithdraw;
import com.ruoyi.member.service.IUmsWithdrawService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提现记录Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-25
 */
@RestController
@RequestMapping("/member/UmsWithdraw")
public class UmsWithdrawController extends BaseController {
    @Autowired
    private IUmsWithdrawService umsWithdrawService;

    /**
     * 查询提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsWithdraw:list')")
    @GetMapping("/list")
    public TableDataInfo list(UmsWithdraw umsWithdraw) {
        startPage();
        List<UmsWithdraw> list = umsWithdrawService.selectUmsWithdrawList(umsWithdraw);
        return getDataTable(list);
    }

    /**
     * 导出提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('member:UmsWithdraw:export')")
    @Log(title = "提现记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(UmsWithdraw umsWithdraw) {
        List<UmsWithdraw> list = umsWithdrawService.selectUmsWithdrawList(umsWithdraw);
        ExcelUtil<UmsWithdraw> util = new ExcelUtil<UmsWithdraw>(UmsWithdraw.class);
        return util.exportExcel(list, "UmsWithdraw");
    }

    /**
     * 获取提现记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('member:UmsWithdraw:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(umsWithdrawService.selectUmsWithdrawById(id));
    }

    /**
     * 新增提现记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsWithdraw:add')")
    @Log(title = "提现记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UmsWithdraw umsWithdraw) {
        return toAjax(umsWithdrawService.insertUmsWithdraw(umsWithdraw));
    }

    /**
     * 修改提现记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsWithdraw:edit')")
    @Log(title = "提现记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UmsWithdraw umsWithdraw) {
        return toAjax(umsWithdrawService.updateUmsWithdraw(umsWithdraw));
    }

    /**
     * 删除提现记录
     */
    @PreAuthorize("@ss.hasPermi('member:UmsWithdraw:remove')")
    @Log(title = "提现记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(umsWithdrawService.deleteUmsWithdrawByIds(ids));
    }
    /**
     * 审核通过
     *
     * @param id 提现申请id
     * @return 成功返回1 失败返回0
     */
    @PutMapping("/withdrawrecords/{id}")
    @ApiOperation(value = "审核通过", notes = "审核通过（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "提现申请id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public int passApply(@PathVariable long id) {
        return umsWithdrawService.updateWithdrawRecordStatus(id, "1");
    }


    /**
     * 拒绝提现申请
     *
     * @param id 提现申请id
     * @return 成功返回1 失败返回0
     */
    @PutMapping("withdrawrecords/refuseapply/{id}")
    @ApiOperation(value = "拒绝提现申请", notes = "拒绝提现申请（需要认证）")

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "提现申请id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功返回1 失败返回0", response = Integer.class)
    })
    public int refuseApply(@PathVariable long id) {
        return umsWithdrawService.updateWithdrawRecordStatus(id, "2");
    }


    /**
     * 发放金额
     *
     * @param id 提现id
     * @return 返回提现结果
     */
    @PutMapping("withdrawrecords/releasemoney/{id}")
    @ApiOperation(value = "发放金额", notes = "发放金额（需要认证）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "long", name = "id", value = "提现申请id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回提现结果", response = WithdrawResponse.class)
    })
    public WithdrawResponse releaseMoney(@PathVariable long id) {
        return umsWithdrawService.releaseMoney(id);
    }

}
