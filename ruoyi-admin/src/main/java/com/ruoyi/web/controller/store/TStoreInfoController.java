package com.ruoyi.web.controller.store;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.store.domain.TStoreInfo;
import com.ruoyi.store.service.impl.TStoreInfoServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @package: com.ruoyi.web.controller.store
 * @author: luweihong
 * @description: 店铺信息
 * @create: 2021/12/9 10:14
 * @version: 1.0
 */
@Api("店铺信息")
@RestController
@RequestMapping("/store/TStoreInfo")
public class TStoreInfoController extends BaseController {

	@Autowired
	private TStoreInfoServiceImpl tStoreInfoService;

	/**
	 * 查询店铺信息列表
	 */
	@PreAuthorize("@ss.hasPermi('store:TStoreInfo:list')")
	@GetMapping("/list")
	public TableDataInfo list(TStoreInfo tStoreInfo) {
		startPage();
		List<TStoreInfo> list = tStoreInfoService.selectTStoreInfoList(tStoreInfo);
		return getDataTable(list);
	}

	/**
	 * 根据店铺id查询店铺信息
	 */
	@PreAuthorize("@ss.hasPermi('store:TStoreInfo:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable Long id) {
		return AjaxResult.success(tStoreInfoService.queryStoreInfo(id));
	}

	/**
	 * 新增店铺
	 */
	@PreAuthorize("@ss.hasPermi('store:TStoreInfo:add')")
	@Log(title = "新增店铺", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody TStoreInfo tStoreInfo) {
		return toAjax(tStoreInfoService.insertTStoreInfo(tStoreInfo));
	}

	/**
	 * 修改店铺
	 */
	@PreAuthorize("@ss.hasPermi('store:TStoreInfo:edit')")
	@Log(title = "店铺管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody TStoreInfo tStoreInfo) {
		tStoreInfo.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(tStoreInfoService.updateTStoreInfo(tStoreInfo));
	}

	/**
	 * 删除店铺信息
	 */
	@PreAuthorize("@ss.hasPermi('sms:TStoreInfo:delete')")
	@Log(title = "店铺信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/TStoreInfo/{id}")
	public AjaxResult deleteTStoreInfoById(@PathVariable Long id) {
		return toAjax(tStoreInfoService.deleteTStoreInfoById(id));
	}

	/**
	 * 删除店铺信息
	 */
	@PreAuthorize("@ss.hasPermi('sms:TStoreInfo:remove')")
	@Log(title = "店铺信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
	public AjaxResult remove(@PathVariable Long[] ids) {
		return toAjax(tStoreInfoService.deleteTStoreInfoByIds(ids));
	}
}
