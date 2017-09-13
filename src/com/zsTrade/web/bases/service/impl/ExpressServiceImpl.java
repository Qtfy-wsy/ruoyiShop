//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.ExpressMapper;
import com.zsTrade.web.bases.model.Express;
import com.zsTrade.web.bases.service.ExpressService;

/**
 * 
 * @author
 */

@Service("ExpressService")
public class ExpressServiceImpl  extends ServiceMybatis<Express> implements ExpressService {

	@Resource
	private ExpressMapper ExpressMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Express
	 * @return
	 */
	public int saveExpress(Express Express) {
		return this.save(Express);
	}

	/**
	 * 删除
	* @param Express
	* @return
	 */
	public int deleteExpress(Express Express) {
		return this.delete(Express);
	}


}
