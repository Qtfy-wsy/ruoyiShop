//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.bases.model.AdvPosition;

/**
 * 
 * @author
 */

public interface AdvPositionService extends BaseService<AdvPosition>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param AdvPosition
	 * @return
	 */
	public int saveAdvPosition(AdvPosition AdvPosition) ;
	/**
	 * 删除
	* @param AdvPosition
	* @return
	 */
	public int deleteAdvPosition(AdvPosition AdvPosition);


}
