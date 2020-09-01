package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsComment;
import com.ruoyi.goods.service.IPmsCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 单品评论Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/comment")
public class PmsCommentController extends BaseController {
    @Autowired
    private IPmsCommentService pmsCommentService;

    /**
     * 查询单品评论列表
     */
    @PreAuthorize("@ss.hasPermi('goods:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsComment pmsComment) {
        startPage();
        List<PmsComment> list = pmsCommentService.selectPmsCommentList(pmsComment);
        return getDataTable(list);
    }

    /**
     * 导出单品评论列表
     */
    @PreAuthorize("@ss.hasPermi('goods:comment:export')")
    @Log(title = "单品评论", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsComment pmsComment) {
        List<PmsComment> list = pmsCommentService.selectPmsCommentList(pmsComment);
        ExcelUtil<PmsComment> util = new ExcelUtil<PmsComment>(PmsComment.class);
        return util.exportExcel(list, "comment");
    }

    /**
     * 获取单品评论详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:comment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsCommentService.selectPmsCommentById(id));
    }

    /**
     * 新增单品评论
     */
    @PreAuthorize("@ss.hasPermi('goods:comment:add')")
    @Log(title = "单品评论", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsComment pmsComment) {
        return toAjax(pmsCommentService.insertPmsComment(pmsComment));
    }

    /**
     * 修改单品评论
     */
    @PreAuthorize("@ss.hasPermi('goods:comment:edit')")
    @Log(title = "单品评论", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsComment pmsComment) {
        return toAjax(pmsCommentService.updatePmsComment(pmsComment));
    }

    /**
     * 删除单品评论
     */
    @PreAuthorize("@ss.hasPermi('goods:comment:remove')")
    @Log(title = "单品评论", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsCommentService.deletePmsCommentByIds(ids));
    }
}
