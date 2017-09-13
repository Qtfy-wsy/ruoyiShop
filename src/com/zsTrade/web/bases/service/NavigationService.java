//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.bases.model.Navigation;

/**
 * 
 * @author
 */

public interface NavigationService extends BaseService<Navigation>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Navigation
	 * @return
	 */
	public int saveNavigation(Navigation Navigation) ;
	/**
	 * 删除
	* @param Navigation
	* @return
	 */
	public int deleteNavigation(Navigation Navigation);


}
