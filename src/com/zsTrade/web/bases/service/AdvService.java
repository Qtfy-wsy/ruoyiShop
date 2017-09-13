//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.bases.model.Adv;

/**
 * 
 * @author
 */

public interface AdvService extends BaseService<Adv>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Adv
	 * @return
	 */
	public int saveAdv(Adv Adv) ;
	/**
	 * 删除
	* @param Adv
	* @return
	 */
	public int deleteAdv(Adv Adv);


}
