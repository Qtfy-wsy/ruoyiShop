package com.ruoyi.web.controller.goods;

import com.alibaba.fastjson.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.goods.domain.*;
import com.ruoyi.goods.service.*;
import com.ruoyi.member.domain.UmsMemberLevel;
import com.ruoyi.member.service.IUmsMemberLevelService;
import com.ruoyi.order.domain.OmsLogisticsTemplate;
import com.ruoyi.order.service.IOmsLogisticsTemplateService;
import com.ruoyi.util.CommonConstant;
import com.ruoyi.web.utils.AdminLoginUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品Controller
 *
 * @author 魔金商城
 * @date 2020-07-24
 */
@RestController
@RequestMapping("/goods/goods")
public class PmsGoodsController extends BaseController {
	/**
	 * 注入规格服务接口
	 */
	@Autowired
	private IPmsSpecService specService;

	/**
	 * 注入商品服务接口
	 */
	@Autowired
	private IPmsGoodsService spuService;

	/**
	 * 注入品牌服务接口
	 */
	@Autowired
	private IPmsBrandService brandService;

	/**
	 * 注入分类服务接口
	 */
	@Autowired
	private IPmsCategoryService categoryService;


	/**
	 * 注入物流模版服务接口
	 */
	@Autowired
	private IOmsLogisticsTemplateService logisticsTemplateService;

	/**
	 * 注入商品类型接口
	 */
	@Autowired
	private IPmsTypeService typeService;

	/**
	 * 会员等级服务接口
	 */
	@Autowired
	private IUmsMemberLevelService customerLevelService;

	/**
	 * 注入服务支持服务接口
	 */
	@Autowired
	private IPmsServiceSupportService serviceSupportService;

	/**
	 * 注入规格值服务接口
	 */
	@Autowired
	private IPmsSpecValueService specValueService;

	@Autowired
	private IPmsGoodsService pmsGoodsService;

	/**
	 * 查询商品列表
	 */
	@PreAuthorize("@ss.hasPermi('goods:goods:list')")
	@GetMapping("/list")
	public TableDataInfo list(PmsGoods pmsGoods) {
		pmsGoods.setStoreId(CommonConstant.ADMIN_STOREID);
		startPage();
		List<PmsGoods> list = pmsGoodsService.querySpus(pmsGoods);
		return getDataTable(list);
	}

	/**
	 * 导出商品列表
	 */
	@PreAuthorize("@ss.hasPermi('goods:goods:export')")
	@Log(title = "商品", businessType = BusinessType.EXPORT)
	@GetMapping("/export")
	public AjaxResult export(PmsGoods pmsGoods) {
		List<PmsGoods> list = pmsGoodsService.selectPmsGoodsList(pmsGoods);
		ExcelUtil<PmsGoods> util = new ExcelUtil<PmsGoods>(PmsGoods.class);
		return util.exportExcel(list, "goods");
	}

	/**
	 * 获取商品详细信息
	 */
	@PreAuthorize("@ss.hasPermi('goods:goods:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Long id) {
		return AjaxResult.success(pmsGoodsService.selectPmsGoodsById(id));
	}

	/**
	 * 新增商品信息
	 *
	 * @param spu 商品信息
	 * @return 成功返回1  失败返回0 -1存在单品同时有会员价和批发规则
	 */
	@PostMapping("/spu")
	@PreAuthorize("@ss.hasPermi('goods:goods:add')")
	@Log(title = "商品", businessType = BusinessType.INSERT)
	public int addSpu(@RequestBody String param) {
		PmsGoods spu = JSON.parseObject(param, PmsGoods.class);
		spu.setStoreName("平台自营");
		return pmsGoodsService.insertPmsGoods(spu.setDefaultValuesForAdd(AdminLoginUtils.getInstance().getManagerName(), CommonConstant.ADMIN_STOREID));
	}

	/**
	 * 修改商品
	 */
	@PreAuthorize("@ss.hasPermi('goods:goods:edit')")
	@Log(title = "商品", businessType = BusinessType.UPDATE)
	@PutMapping("/spu")
	public AjaxResult edit(@RequestBody PmsGoods pmsGoods) {
		pmsGoods.setStoreId(CommonConstant.ADMIN_STOREID);
		return toAjax(pmsGoodsService.updatePmsGoods(pmsGoods));
	}

	/**
	 * 删除商品
	 */
	@PreAuthorize("@ss.hasPermi('goods:goods:remove')")
	@Log(title = "商品", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(pmsGoodsService.deletePmsGoodsByIds(ids));
	}

	/**
	 * 查询所有品牌
	 *
	 * @return 返回品牌信息
	 */
	@GetMapping("/spu/brands")
	@ApiOperation(value = "查询所有品牌", notes = "查询所有品牌（需要认证）")
	public List<PmsBrand> queryBrands() {
		return brandService.queryAllBrands(CommonConstant.ADMIN_STOREID);
	}


	/**
	 * 根据分类id查询所有子分类信息
	 *
	 * @param parentId 父级id
	 * @return 返回该父级下的所有分类信息
	 */
	@GetMapping("/spu/categorybyparentid/{parentId}")
	@ApiOperation(value = "根据分类id查询所有子分类信息", notes = "根据分类id查询所有子分类信息（需要认证）")
	public List<PmsCategory> queryCategoryByParentIdForSpu(@PathVariable long parentId) {
		return categoryService.queryCategoryByParentId(parentId);
	}


	/**
	 * 批量修改单品的上下架状态
	 *
	 * @param ids    商品id
	 * @param status 上下架状态
	 * @return 成功返回>1 失败返回0
	 */
	@PutMapping("/updateshelvesstatus")
	@ApiOperation(value = "批量修改单品的上下架状态", notes = "批量修改单品的上下架状态（需要认证）")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "form", dataType = "array", name = "ids", value = "商品id"),
			@ApiImplicitParam(paramType = "form", dataType = "String", name = "status", value = "上下架状态"),
	})
	@ApiResponses({
			@ApiResponse(code = 200, message = "成功返回>1 失败返回0", response = Integer.class)
	})
	public int updateShelvesStatus(Long[] ids, String status) {
		return spuService.updateShelvesStatus(Arrays.asList(ids), status, CommonConstant.ADMIN_STOREID, null);
	}

	/**
	 * 批量删除商品信息
	 *
	 * @param ids 商品id集合
	 * @return 成功返回 1 失败返回0
	 */
	@DeleteMapping("/spus")
	@ApiOperation(value = "批量删除商品信息", notes = "批量删除商品信息（需要认证）")
	@PreAuthorize("@ss.hasPermi('spu/deletespus')")

	public int deleteSpus(Long[] ids) {
		return spuService.deleteSpus(Arrays.stream(ids).map(id -> PmsGoods.buildForDelete(AdminLoginUtils.getInstance().getManagerName(), id, CommonConstant.ADMIN_STOREID)).collect(Collectors.toList()));
	}


	/**
	 * 根据分类id查询所有子分类信息
	 *
	 * @param parentId 父级id
	 * @return 返回该父级下的所有分类信息
	 */
	@GetMapping("/spu/add/cate/{parentId}")
	@ApiOperation(value = "根据分类id查询所有子分类信息", notes = "根据分类id查询所有子分类信息（需要认证）")
	public List<PmsCategory> queryCategoryByParentIdForAddSpu(@PathVariable long parentId) {
		return categoryService.queryCategoryByParentId(parentId);
	}


	/**
	 * 查询所有的物流模版
	 *
	 * @return 返回所有的物流模版
	 */
	@GetMapping("/spu/add/alllogisticstemplates")
	@ApiOperation(value = "查询所有的物流模版", notes = "查询所有的物流模版（需要认证）")

	public List<OmsLogisticsTemplate> queryAllLogisticsTemplates() {
		return logisticsTemplateService.queryAllTemplates(CommonConstant.ADMIN_STOREID);
	}


	/**
	 * 查询指定的规格信息
	 *
	 * @param ids 规格id集合
	 * @return 返回指定的规格信息(包含规格值)
	 */
	@GetMapping("/spu/add/specs")
	@ApiOperation(value = "查询指定的规格信息", notes = "查询指定的规格信息（需要认证）")
	public List<PmsSpec> querySpecsByIds(Long[] ids) {
		return specService.querySpecsByIds(ids);
	}

	/**
	 * 查询类型详情
	 *
	 * @param id 类型id
	 * @return 返回类型详情 包括类型,属性,属性值 和类型的规格
	 */
	@GetMapping("/spu/add/type/{id}")
	@ApiOperation(value = "查询类型详情", notes = "查询类型详情（需要认证）")
	public PmsType queryTypeDetail(@PathVariable long id) {
		return typeService.queryTypeDetail(id);
	}


	/**
	 * 查询所有品牌
	 *
	 * @return 返回所有品牌
	 */
	@GetMapping("/spu/add/brands")
	@ApiOperation(value = "查询所有品牌", notes = "查询所有品牌（需要认证）")
	public List<PmsBrand> queryAllBrands() {
		return brandService.queryAllBrands(CommonConstant.ADMIN_STOREID);
	}


	/**
	 * 查询所有的会员等级
	 *
	 * @return 返回所有的会员等级
	 */
	@GetMapping("/spu/add/levels")
	@ApiOperation(value = "查询所有的会员等级", notes = "查询所有的会员等级（需要认证）")
	public List<UmsMemberLevel> queryAllLevels() {
		return customerLevelService.queryAllCustomerLevels();
	}


	/**
	 * 查询所有服务支持
	 *
	 * @return 返回所有服务支持
	 */
	@GetMapping("/spu/add/servciesupports")
	@ApiOperation(value = "查询所有服务支持", notes = "查询所有服务支持（需要认证）")
	public List<PmsServiceSupport> queryAllServiceSupport() {
		return serviceSupportService.queryAllServiceSupport();
	}


	/**
	 * 添加规格值信息
	 *
	 * @param specValue 规格值信息
	 * @return 成功返回1  失败返回0
	 */
	@PostMapping("/spu/specvalue")
	@ApiOperation(value = "添加规格值信息", notes = "添加规格值信息（需要认证）")
	public String addSpecValue(@RequestBody PmsSpecValue specValue) {
		return specValueService.addSpecValue(specValue.setDefaultValuesForAdd(specValue, AdminLoginUtils.getInstance().getManagerName()));
	}


	/**
	 * 查询所有品牌(修改商品用)
	 *
	 * @return 返回所有品牌
	 */
	@GetMapping("/spu/update/brands")
	@ApiOperation(value = "查询所有品牌(修改商品用)", notes = "查询所有品牌（需要认证）")
	public List<PmsBrand> queryAllBrandsForUpdate() {
		return brandService.queryAllBrands(CommonConstant.ADMIN_STOREID);
	}

	/**
	 * 查询所有的物流模版(修改商品用)
	 *
	 * @return 返回所有的物流模版
	 */
	@GetMapping("/spu/update/alllogisticstemplates")
	@ApiOperation(value = "查询所有的物流模版(修改商品用)", notes = "查询所有的物流模版(修改商品用)（需要认证）")
	public List<OmsLogisticsTemplate> queryAllLogisticsTemplatesForUpdate() {
		return logisticsTemplateService.queryAllTemplates(CommonConstant.ADMIN_STOREID);
	}

	/**
	 * 查询所有的会员等级(修改商品用)
	 *
	 * @return 返回所有的会员等级
	 */
	@GetMapping("/spu/update/levels")
	@ApiOperation(value = "查询所有的会员等级(修改商品用)", notes = "查询所有的会员等级（需要认证）")
	public List<UmsMemberLevel> queryAllLevelsForUpdate() {
		return customerLevelService.queryAllCustomerLevels();
	}


	/**
	 * 查询所有服务支持(修改商品用)
	 *
	 * @return 返回所有服务支持
	 */
	@GetMapping("/spu/update/servciesupports")
	@ApiOperation(value = "查询所有服务支持(修改商品用)", notes = "查询所有服务支持（需要认证）")
	public List<PmsServiceSupport> queryAllServiceSupportForUpdate() {
		return serviceSupportService.queryAllServiceSupport();
	}

	/**
	 * 根据商品id查询商品信息
	 *
	 * @param id 商品id
	 * @return 返回商品详细信息(包含 商品服务支持, 商品图片, 商品属性值, 商品规格值, 商品单品......)
	 */
	@GetMapping("/spu/{id}")
	@ApiOperation(value = "根据商品id查询商品信息", notes = "根据商品id查询商品信息（需要认证）")
	public PmsGoods querySpuById(@PathVariable long id) {
		return spuService.querySpu(id, CommonConstant.QUERY_WITH_NO_STORE);
	}


	/**
	 * 查询类型详情(修改商品用)
	 *
	 * @param id 类型id
	 * @return 返回类型详情 包括类型,属性,属性值 和类型的规格
	 */
	@GetMapping("/spu/update/type/{id}")
	@ApiOperation(value = "查询类型详情(修改商品用)", notes = "查询类型详情（需要认证）")
	public PmsType queryTypeDetailForUpdate(@PathVariable long id) {
		return typeService.queryTypeDetail(id);
	}

	@ApiOperation("分页查询所有店铺待审核的商品")
	@GetMapping("/goodsaudit")
	@ResponseBody
	public TableDataInfo queryAllStoreToBeAuditdSpus(PmsGoods pmsGoods) {
	    startPage();
        List<PmsGoods> list = pmsGoodsService.queryAllStoreToBeAuditdSpus();
		return getDataTable(list);
	}
}
