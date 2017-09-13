/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.villeage.model.Dproducttype;

 /**
 * 
 * @author zsCat 2017-1-19 11:45:49
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
public interface DproducttypeService extends BaseService<Dproducttype>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Dproducttype
	 * @return
	 */
	public int saveDproducttype(Dproducttype Dproducttype) ;
	/**
	 * 删除
	* @param Dproducttype
	* @return
	 */
	public int deleteDproducttype(Dproducttype Dproducttype);

	public PageInfo<Dproducttype> findPageInfo(Map<String, Object> params);

}
