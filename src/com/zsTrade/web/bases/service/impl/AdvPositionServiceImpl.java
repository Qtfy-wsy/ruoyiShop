//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.AdvPositionMapper;
import com.zsTrade.web.bases.model.AdvPosition;
import com.zsTrade.web.bases.service.AdvPositionService;

/**
 * 
 * @author
 */

@Service("AdvPositionService")
public class AdvPositionServiceImpl  extends ServiceMybatis<AdvPosition> implements AdvPositionService {

	@Resource
	private AdvPositionMapper AdvPositionMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param AdvPosition
	 * @return
	 */
	public int saveAdvPosition(AdvPosition AdvPosition) {
		return this.save(AdvPosition);
	}

	/**
	 * 删除
	* @param AdvPosition
	* @return
	 */
	public int deleteAdvPosition(AdvPosition AdvPosition) {
		return this.delete(AdvPosition);
	}


}
