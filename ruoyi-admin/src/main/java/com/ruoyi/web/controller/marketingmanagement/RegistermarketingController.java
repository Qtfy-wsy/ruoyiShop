package com.ruoyi.web.controller.marketingmanagement;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.marketing.service.RegisterMarketingService;
import com.ruoyi.store.domain.TStoreInfo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api("店铺信息")
@RestController
@RequestMapping("/marketingmanagement/storemarketing")
public class RegistermarketingController extends BaseController {

	@Autowired
	private RegisterMarketingService registerMarketingService;


	/**
	 * 新增店铺
	 */
	@PreAuthorize("@ss.hasPermi('store:TStoreInfo:add')")
	@Log(title = "新增店铺", businessType = BusinessType.INSERT)
	@PostMapping
	public AjaxResult add(@Validated @RequestBody Map tStoreInfo) { return toAjax(registerMarketingService.insertregisterMarketing(tStoreInfo));
	}

	/**
	 * 修改店铺
	 */
	/*@PreAuthorize("@ss.hasPermi('store:TStoreInfo:edit')")
	@Log(title = "店铺管理", businessType = BusinessType.UPDATE)
	@PutMapping
	public AjaxResult edit(@Validated @RequestBody TStoreInfo tStoreInfo) {
		tStoreInfo.setUpdateBy(SecurityUtils.getUsername());
		return toAjax(tStoreInfoService.updateTStoreInfo(tStoreInfo));
	}*/
}
