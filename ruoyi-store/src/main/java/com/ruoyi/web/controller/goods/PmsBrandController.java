package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsBrand;
import com.ruoyi.goods.domain.PmsBrandApply;
import com.ruoyi.goods.service.IPmsBrandApplyService;
import com.ruoyi.goods.service.IPmsBrandService;
import com.ruoyi.util.BaseResponse;
import com.ruoyi.util.PageHelper;
import com.ruoyi.web.utils.AdminLoginUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 品牌Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/brand")
public class PmsBrandController extends BaseController {
    @Autowired
    private IPmsBrandService pmsBrandService;
    /**
     * 品牌申请service
     */
    @Autowired
    private IPmsBrandApplyService brandApplyService;
    /**
     * 查询店铺品牌
     *
     * @return 店铺信息
     */
    @GetMapping("/brands")
    @ApiOperation(value = "查询店铺品牌", notes = "查询店铺品牌(需要认证)")
    public List<PmsBrand> queryAllAdminBrand() {
        return pmsBrandService.queryAllAdminBrands();
    }
    /**
     * 查询品牌列表
     */
    @PreAuthorize("@ss.hasPermi('goods:brand:list')")
    @GetMapping("/list")
    public BaseResponse queryStoreBrand(@ApiIgnore PageHelper<PmsBrand> pageHelper, @ApiIgnore String name) {
        return BaseResponse.build(pmsBrandService.queryStoreBrandsForPage(pageHelper, AdminLoginUtils.getInstance().getStoreId(), name));
    }
    /**
     * 申请品牌
     *
     * @param brandApply 品牌实体类
     * @return 添加返回码
     */
    @PostMapping("/addStoreBrand")
    public int addStoreBrand(@RequestBody PmsBrandApply brandApply) {
        return brandApplyService.doAddStoreBrand(brandApply, AdminLoginUtils.getInstance().getStoreId());
    }
    /**
     * 分页查询自定义品牌
     *
     * @param pageHelper 分页帮助类
     * @param name       管理员名称
     * @return 返回管理员信息
     */
    @GetMapping("/custom/brands")
    @ApiOperation(value = "分页查询自定义品牌", notes = "分页查询自定义品牌(需要认证)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageNum", value = "当前页"),
            @ApiImplicitParam(paramType = "form", dataType = "int", name = "pageSize", value = "每页显示的记录数"),
            @ApiImplicitParam(paramType = "form", dataType = "String", name = "name", value = "管理员名称"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回管理员信息", response = PmsBrand.class)
    })
    public BaseResponse queryCustomBrand(@ApiIgnore PageHelper<PmsBrand> pageHelper, @ApiIgnore String name) {
        return BaseResponse.build(pmsBrandService.queryBrands(pageHelper, name, "", AdminLoginUtils.getInstance().getStoreId()));
    }

    /**
     * 添加自定义品牌
     *
     * @param brand 品牌实体类
     * @return 添加返回码
     */
    @PostMapping("/custom/brands")
    @ApiOperation(value = "添加自定义品牌", notes = "添加自定义品牌(需要认证)")
    @ApiResponses({
            @ApiResponse(code = 200, message = "添加返回码", response = Integer.class)
    })
    public int addCustomBrand(@RequestBody PmsBrand brand) {
        return pmsBrandService.addBrand(brand.setDefaultValuesForAdd(AdminLoginUtils.getInstance().getManagerName(), AdminLoginUtils.getInstance().getStoreId()));
    }

    /**
     * 删除自定义品牌
     *
     * @param ids 自定义品牌id
     * @return 删除返回码
     */
    @DeleteMapping("/custom/brands")
    @ApiOperation(value = "删除自定义品牌", notes = "删除自定义品牌(需要认证)")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", dataType = "Long[]", name = "ids", value = "自定义品牌id"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "删除返回码", response = Integer.class)
    })
    public int deleteCustomBrand(Long[] ids) {
        return pmsBrandService.batchDeleteBrands(Arrays.stream(ids).map(
                id -> PmsBrand.buildDeleteBrand(id, AdminLoginUtils.getInstance().getManagerName(), AdminLoginUtils.getInstance().getStoreId())
        ).collect(Collectors.toList()));
    }

    /**
     * 导出品牌列表
     */
    @PreAuthorize("@ss.hasPermi('goods:brand:export')")
    @Log(title = "品牌", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsBrand pmsBrand) {
        pmsBrand.setStoreId(AdminLoginUtils.getInstance().getStoreId());
        List<PmsBrand> list = pmsBrandService.selectPmsBrandList(pmsBrand);
        ExcelUtil<PmsBrand> util = new ExcelUtil<PmsBrand>(PmsBrand.class);
        return util.exportExcel(list, "brand");
    }

    /**
     * 获取品牌详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:brand:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsBrandService.selectPmsBrandById(id));
    }

    /**
     * 新增品牌
     */
    @PreAuthorize("@ss.hasPermi('goods:brand:add')")
    @Log(title = "品牌", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsBrand pmsBrand) {
        return toAjax(pmsBrandService.insertPmsBrand(pmsBrand.setDefaultValuesForAdd(AdminLoginUtils.getInstance().getManagerName(), AdminLoginUtils.getInstance().getStoreId())));
    }

    /**
     * 修改品牌
     */
    @PreAuthorize("@ss.hasPermi('goods:brand:edit')")
    @Log(title = "品牌", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsBrand pmsBrand) {
        return toAjax(pmsBrandService.updatePmsBrand(pmsBrand.setDefaultValuesForAdd(AdminLoginUtils.getInstance().getManagerName(), AdminLoginUtils.getInstance().getStoreId())));
    }

    /**
     * 删除品牌
     */
    @PreAuthorize("@ss.hasPermi('goods:brand:remove')")
    @Log(title = "品牌", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsBrandService.batchDeleteBrands(Arrays.stream(ids).map(id -> PmsBrand.buildDeleteBrand(id, AdminLoginUtils.getInstance().getManagerName(), AdminLoginUtils.getInstance().getStoreId())).collect(Collectors.toList())));
    }
}
