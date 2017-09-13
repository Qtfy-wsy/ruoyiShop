//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.AdvMapper;
import com.zsTrade.web.bases.model.Adv;
import com.zsTrade.web.bases.service.AdvService;

/**
 * 
 * @author
 */

@Service("AdvService")
public class AdvServiceImpl  extends ServiceMybatis<Adv> implements AdvService {

	@Resource
	private AdvMapper AdvMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Adv
	 * @return
	 */
	public int saveAdv(Adv Adv) {
		return this.save(Adv);
	}

	/**
	 * 删除
	* @param Adv
	* @return
	 */
	public int deleteAdv(Adv Adv) {
		return this.delete(Adv);
	}


}
