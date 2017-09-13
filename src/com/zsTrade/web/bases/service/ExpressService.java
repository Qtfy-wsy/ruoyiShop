//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.bases.model.Express;

/**
 * 
 * @author
 */

public interface ExpressService extends BaseService<Express>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Express
	 * @return
	 */
	public int saveExpress(Express Express) ;
	/**
	 * 删除
	* @param Express
	* @return
	 */
	public int deleteExpress(Express Express);


}
