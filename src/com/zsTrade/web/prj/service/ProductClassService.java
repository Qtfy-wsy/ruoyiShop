//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.prj.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.ProductClass;

/**
 * 
 * @author
 */

public interface ProductClassService extends BaseService<ProductClass>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param ProductClass
	 * @return
	 */
	public int saveProductClass(ProductClass ProductClass) ;
	/**
	 * 删除
	* @param ProductClass
	* @return
	 */
	public int deleteProductClass(ProductClass ProductClass);

	public PageInfo<ProductClass> findPageInfo(Map<String, Object> params);

}
