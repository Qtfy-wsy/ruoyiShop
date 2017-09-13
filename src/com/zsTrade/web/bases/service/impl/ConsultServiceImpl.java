//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.ConsultMapper;
import com.zsTrade.web.bases.model.Consult;
import com.zsTrade.web.bases.service.ConsultService;

/**
 * 
 * @author
 */

@Service("ConsultService")
public class ConsultServiceImpl  extends ServiceMybatis<Consult> implements ConsultService {

	@Resource
	private ConsultMapper ConsultMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Consult
	 * @return
	 */
	public int saveConsult(Consult Consult) {
		return this.save(Consult);
	}

	/**
	 * 删除
	* @param Consult
	* @return
	 */
	public int deleteConsult(Consult Consult) {
		return this.delete(Consult);
	}


}
