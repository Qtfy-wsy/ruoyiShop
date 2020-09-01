package com.ruoyi.web.controller.goods;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.PmsCategory;
import com.ruoyi.goods.domain.PmsSpec;
import com.ruoyi.goods.domain.PmsType;
import com.ruoyi.goods.service.IPmsCategoryService;
import com.ruoyi.goods.service.IPmsSpecService;
import com.ruoyi.goods.service.IPmsTypeService;
import com.ruoyi.web.utils.AdminLoginUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品分类Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/category")
public class PmsCategoryController extends BaseController {
    @Autowired
    private IPmsCategoryService pmsCategoryService;

    /**
     * 注入类型服务接口
     */
    @Autowired
    private IPmsTypeService typeService;

    /**
     * 注入规格服务接口
     */
    @Autowired
    private IPmsSpecService specService;

    /**
     * 根据分类id查询所有子分类信息
     *
     * @param parentId 父级id
     * @return 返回该父级下的所有分类信息
     */
    @GetMapping("/categorybyparentid/{parentId}")
    public List<PmsCategory> queryCategoryByParentId(@PathVariable long parentId) {
        return pmsCategoryService.queryCategoryByParentId(parentId);
    }

    /**
     * 添加分类
     *
     * @param category 分类实体
     * @return 成功返回1  失败返回0
     */
    @PostMapping("/category")
    @PreAuthorize("@ss.hasPermi('goods:category:add')")
    public int addCategory(@RequestBody PmsCategory category) {
        return pmsCategoryService.addCategory(category.setDefaultsForAdd(AdminLoginUtils.getInstance().getManagerName()));
    }


    /**
     * 查询所有的类型信息
     *
     * @return 返回所有的类型信息
     */
    @GetMapping("/category/alltypes")
    public List<PmsType> queryAllTypes() {
        return typeService.queryAllType();
    }


    /**
     * 查询所有规格
     *
     * @return 所有规格
     */
    @GetMapping("/category/allspecs")
    public List<PmsSpec> queryAllSpecs() {
        return specService.queryAllSpec();
    }

    /**
     * 删除分类信息
     *
     * @param id 分类id
     * @return -1 有子分类不能删除  1 成功 0 失败 -2 如果该分类被商品关联了 则不能删除
     */
    @PreAuthorize("@ss.hasPermi('goods:category:remove')")
    @DeleteMapping("/category/{id}")
    public int deleteCategory(@PathVariable long id) {
        return pmsCategoryService.deleteCategory(PmsCategory.buildForDelete(id, AdminLoginUtils.getInstance().getManagerName()));
    }


    /**
     * 根据分类id查询分类信息
     *
     * @param id 分类id
     * @return 分类信息
     */
    @GetMapping("/category/{id}")
    public PmsCategory queryCategoryById(@PathVariable long id) {
        return pmsCategoryService.queryCategoryById(id);
    }


    /**
     * 更新商品分类
     *
     * @param category 分类
     * @return -1 三级分类关联商品，商品类型不能修改 1 成功返回 0 失败返回
     */
    @PutMapping("/category")
    @PreAuthorize("@ss.hasPermi('goods:category:edit')")
    public int updateCategory(@RequestBody PmsCategory category) {
        return pmsCategoryService.updateCategory(category.setDefaultValuesForModify(AdminLoginUtils.getInstance().getManagerName()));
    }


    /**
     * 根据分类id查询三级分类是否关联商品
     *
     * @return -1 该三级分类关联商品，商品类型不能修改 0 不关联
     */
    @GetMapping("/thirdcategoryhasspu/{id}")
    public int queryThirdCategoryHasSpuById(@PathVariable long id) {
        return pmsCategoryService.queryThirdCategoryHasSpuById(id);
    }

    /**
     * 查询商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('goods:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(PmsCategory pmsCategory) {
        startPage();
        List<PmsCategory> list = pmsCategoryService.selectPmsCategoryList(pmsCategory);
        return getDataTable(list);
    }

    /**
     * 导出商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('goods:category:export')")
    @Log(title = "商品分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PmsCategory pmsCategory) {
        List<PmsCategory> list = pmsCategoryService.selectPmsCategoryList(pmsCategory);
        ExcelUtil<PmsCategory> util = new ExcelUtil<PmsCategory>(PmsCategory.class);
        return util.exportExcel(list, "category");
    }

    /**
     * 获取商品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(pmsCategoryService.selectPmsCategoryById(id));
    }

    /**
     * 新增商品分类
     */
    @PreAuthorize("@ss.hasPermi('goods:category:add')")
    @Log(title = "商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PmsCategory pmsCategory) {
        return toAjax(pmsCategoryService.insertPmsCategory(pmsCategory));
    }

    /**
     * 修改商品分类
     */
    @PreAuthorize("@ss.hasPermi('goods:category:edit')")
    @Log(title = "商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PmsCategory pmsCategory) {
        return toAjax(pmsCategoryService.updatePmsCategory(pmsCategory));
    }

    /**
     * 删除商品分类
     */
    @PreAuthorize("@ss.hasPermi('goods:category:remove')")
    @Log(title = "商品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(pmsCategoryService.deletePmsCategoryByIds(ids));
    }
}
