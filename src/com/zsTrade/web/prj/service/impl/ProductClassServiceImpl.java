//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.prj.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.prj.mapper.ProductClassMapper;
import com.zsTrade.web.prj.model.ProductClass;
import com.zsTrade.web.prj.service.ProductClassService;
/**
 * 
 * @author
 */

@Service("ProductClassService")
public class ProductClassServiceImpl  extends ServiceMybatis<ProductClass> implements ProductClassService {

	@Resource
	private ProductClassMapper ProductClassMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param ProductClass
	 * @return
	 */
	public int saveProductClass(ProductClass ProductClass) {
		return this.save(ProductClass);
	}

	/**
	 * 删除
	* @param ProductClass
	* @return
	 */
	public int deleteProductClass(ProductClass ProductClass) {
		return this.delete(ProductClass);
	}

   @Override
	public PageInfo<ProductClass> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<ProductClass> list = ProductClassMapper.findPageInfo(params);
		return new PageInfo<ProductClass>(list);
	}
}
