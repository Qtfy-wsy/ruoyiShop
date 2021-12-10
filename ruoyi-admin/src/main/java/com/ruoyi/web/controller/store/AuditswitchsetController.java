package com.ruoyi.web.controller.store;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.setting.bean.BaseInfoSet;
import com.ruoyi.setting.service.BaseInfoSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @package: com.ruoyi.web.controller.store
 * @author: lwh
 * @description: 审核开关控制层
 * @create: 2021/12/8 21:57
 * @version: 1.0
 */
@Api("审核开关控制层")
@RestController
@RequestMapping
public class AuditswitchsetController extends BaseEntity {

	@Autowired
	private BaseInfoSetService baseInfoSetService;

	@ApiOperation("查询配置信息")
	@PreAuthorize("@ss.hasPermi('store:auditswitchset:query')")
	@GetMapping("/goodsauditset")
	public BaseInfoSet queryBaseInfoSet() {
		return baseInfoSetService.queryBaseInfoSet();
	}

	@ApiOperation("设置审核开关")
	@PutMapping("/goodsauditset")
	@PreAuthorize("@ss.hasPermi('store:auditswitchset:edit')")
	public int setAuditSwitch(@RequestBody String storeSpuAudit){
		return baseInfoSetService.setAuditSwitch(storeSpuAudit);
	}
}
