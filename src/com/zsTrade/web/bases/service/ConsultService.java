//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.bases.model.Consult;

/**
 * 
 * @author
 */

public interface ConsultService extends BaseService<Consult>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Consult
	 * @return
	 */
	public int saveConsult(Consult Consult) ;
	/**
	 * 删除
	* @param Consult
	* @return
	 */
	public int deleteConsult(Consult Consult);


}
