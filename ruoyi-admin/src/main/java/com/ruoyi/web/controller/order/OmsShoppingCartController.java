package com.ruoyi.web.controller.order;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.order.domain.OmsShoppingCart;
import com.ruoyi.order.service.IOmsShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 购物车Controller
 *
 * @author 伊甸园商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/order/OmsShoppingCart")
public class OmsShoppingCartController extends BaseController {
    @Autowired
    private IOmsShoppingCartService omsShoppingCartService;

    /**
     * 查询购物车列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsShoppingCart:list')")
    @GetMapping("/list")
    public TableDataInfo list(OmsShoppingCart omsShoppingCart) {
        startPage();
        List<OmsShoppingCart> list = omsShoppingCartService.selectOmsShoppingCartList(omsShoppingCart);
        return getDataTable(list);
    }

    /**
     * 导出购物车列表
     */
    @PreAuthorize("@ss.hasPermi('order:OmsShoppingCart:export')")
    @Log(title = "购物车", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(OmsShoppingCart omsShoppingCart) {
        List<OmsShoppingCart> list = omsShoppingCartService.selectOmsShoppingCartList(omsShoppingCart);
        ExcelUtil<OmsShoppingCart> util = new ExcelUtil<OmsShoppingCart>(OmsShoppingCart.class);
        return util.exportExcel(list, "OmsShoppingCart");
    }

    /**
     * 获取购物车详细信息
     */
    @PreAuthorize("@ss.hasPermi('order:OmsShoppingCart:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(omsShoppingCartService.selectOmsShoppingCartById(id));
    }

    /**
     * 新增购物车
     */
    @PreAuthorize("@ss.hasPermi('order:OmsShoppingCart:add')")
    @Log(title = "购物车", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OmsShoppingCart omsShoppingCart) {
        return toAjax(omsShoppingCartService.insertOmsShoppingCart(omsShoppingCart));
    }

    /**
     * 修改购物车
     */
    @PreAuthorize("@ss.hasPermi('order:OmsShoppingCart:edit')")
    @Log(title = "购物车", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OmsShoppingCart omsShoppingCart) {
        return toAjax(omsShoppingCartService.updateOmsShoppingCart(omsShoppingCart));
    }

    /**
     * 删除购物车
     */
    @PreAuthorize("@ss.hasPermi('order:OmsShoppingCart:remove')")
    @Log(title = "购物车", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(omsShoppingCartService.deleteOmsShoppingCartByIds(ids));
    }
}
