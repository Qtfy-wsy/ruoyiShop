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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.prj.mapper.ProductTypeMapper;
import com.zsTrade.web.prj.model.ProductType;
import com.zsTrade.web.prj.service.ProductTypeService;
/**
 * 
 * @author zsCat 2016-12-22 9:29:55
 * @Email: [email]
 * @version [version]
 *	项目类别管理
 */
@Service("ProductTypeService")
public class ProductTypeServiceImpl  extends ServiceMybatis<ProductType> implements ProductTypeService {

	@Resource
	private ProductTypeMapper ProductTypeMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param ProductType
	 * @return
	 */
	public int saveProductType(ProductType ProductType) {
		return this.save(ProductType);
	}

	/**
	 * 删除
	* @param ProductType
	* @return
	 */
	public int deleteProductType(ProductType ProductType) {
		return this.delete(ProductType);
	}

   @Override
	public PageInfo<ProductType> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ProductType> list = ProductTypeMapper.findPageInfo(params);
		return new PageInfo<ProductType>(list);
	}
}
