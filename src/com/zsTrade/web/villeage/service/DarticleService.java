/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.villeage.model.Darticle;

 /**
 * 
 * @author zsCat 2017-1-19 10:16:56
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
public interface DarticleService extends BaseService<Darticle>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Darticle
	 * @return
	 */
	public int saveDarticle(Darticle Darticle) ;
	/**
	 * 删除
	* @param Darticle
	* @return
	 */
	public int deleteDarticle(Darticle Darticle);

	public PageInfo<Darticle> findPageInfo(Map<String, Object> params);

}
