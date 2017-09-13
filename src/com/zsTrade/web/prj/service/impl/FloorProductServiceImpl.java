/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.prj.service.FloorProductService;
import com.zsTrade.web.prj.mapper.FloorProductMapper;

import com.zsTrade.web.prj.model.FloorProduct;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @author zsCat 2016-12-22 14:23:22
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层产品管理
 */
@Service("FloorProductService")
public class FloorProductServiceImpl  extends ServiceMybatis<FloorProduct> implements FloorProductService {

	@Resource
	private FloorProductMapper FloorProductMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param FloorProduct
	 * @return
	 */
	public int saveFloorProduct(FloorProduct FloorProduct) {
		return this.save(FloorProduct);
	}

	/**
	 * 删除
	* @param FloorProduct
	* @return
	 */
	public int deleteFloorProduct(FloorProduct FloorProduct) {
		return this.delete(FloorProduct);
	}

   @Override
	public PageInfo<FloorProduct> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<FloorProduct> list = FloorProductMapper.findPageInfo(params);
		return new PageInfo<FloorProduct>(list);
	}
}
