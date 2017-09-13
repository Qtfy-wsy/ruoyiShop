/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.ProductType;

 /**
 * 
 * @author zsCat 2016-12-22 9:29:55
 * @Email: [email]
 * @version [version]
 *	项目类别管理
 */
public interface ProductTypeService extends BaseService<ProductType>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param ProductType
	 * @return
	 */
	public int saveProductType(ProductType ProductType) ;
	/**
	 * 删除
	* @param ProductType
	* @return
	 */
	public int deleteProductType(ProductType ProductType);

	public PageInfo<ProductType> findPageInfo(Map<String, Object> params);

}
