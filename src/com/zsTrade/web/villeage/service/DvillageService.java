/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.villeage.model.Dvillage;

 /**
 * 
 * @author zsCat 2017-1-19 10:17:13
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
public interface DvillageService extends BaseService<Dvillage>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Dvillage
	 * @return
	 */
	public int saveDvillage(Dvillage Dvillage) ;
	/**
	 * 删除
	* @param Dvillage
	* @return
	 */
	public int deleteDvillage(Dvillage Dvillage);

	public PageInfo<Dvillage> findPageInfo(Map<String, Object> params);

}
