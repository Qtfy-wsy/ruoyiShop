//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.bases.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.bases.mapper.FavoritesMapper;
import com.zsTrade.web.bases.model.Favorites;
import com.zsTrade.web.bases.service.FavoritesService;

/**
 * 
 * @author
 */

@Service("FavoritesService")
public class FavoritesServiceImpl  extends ServiceMybatis<Favorites> implements FavoritesService {

	@Resource
	private FavoritesMapper FavoritesMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Favorites
	 * @return
	 */
	public int saveFavorites(Favorites Favorites) {
		return this.save(Favorites);
	}

	/**
	 * 删除
	* @param Favorites
	* @return
	 */
	public int deleteFavorites(Favorites Favorites) {
		return this.delete(Favorites);
	}


}
