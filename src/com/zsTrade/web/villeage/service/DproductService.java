/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.villeage.model.Dproduct;

 /**
 * 
 * @author zsCat 2017-1-19 11:45:30
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
public interface DproductService extends BaseService<Dproduct>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Dproduct
	 * @return
	 */
	public int saveDproduct(Dproduct Dproduct) ;
	/**
	 * 删除
	* @param Dproduct
	* @return
	 */
	public int deleteDproduct(Dproduct Dproduct);

	public PageInfo<Dproduct> findPageInfo(Map<String, Object> params);

}
