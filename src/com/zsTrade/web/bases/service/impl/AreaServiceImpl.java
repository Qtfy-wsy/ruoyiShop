//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.AreaMapper;
import com.zsTrade.web.bases.model.Area;
import com.zsTrade.web.bases.service.AreaService;

/**
 * 
 * @author
 */

@Service("AreaService")
public class AreaServiceImpl  extends ServiceMybatis<Area> implements AreaService {

	@Resource
	private AreaMapper AreaMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Area
	 * @return
	 */
	public int saveArea(Area Area) {
		return this.save(Area);
	}

	/**
	 * 删除
	* @param Area
	* @return
	 */
	public int deleteArea(Area Area) {
		return this.delete(Area);
	}


}
