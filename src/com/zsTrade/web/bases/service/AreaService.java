//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.bases.model.Area;

/**
 * 
 * @author
 */

public interface AreaService extends BaseService<Area>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Area
	 * @return
	 */
	public int saveArea(Area Area) ;
	/**
	 * 删除
	* @param Area
	* @return
	 */
	public int deleteArea(Area Area);


}
