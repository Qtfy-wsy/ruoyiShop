//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.NavigationMapper;
import com.zsTrade.web.bases.model.Navigation;
import com.zsTrade.web.bases.service.NavigationService;

/**
 * 
 * @author
 */

@Service("NavigationService")
public class NavigationServiceImpl  extends ServiceMybatis<Navigation> implements NavigationService {

	@Resource
	private NavigationMapper NavigationMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Navigation
	 * @return
	 */
	public int saveNavigation(Navigation Navigation) {
		return this.save(Navigation);
	}

	/**
	 * 删除
	* @param Navigation
	* @return
	 */
	public int deleteNavigation(Navigation Navigation) {
		return this.delete(Navigation);
	}


}
