/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.villeage.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.villeage.model.Dviname;

 /**
 * 
 * @author zsCat 2017-1-19 10:37:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
public interface DvinameService extends BaseService<Dviname>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Dviname
	 * @return
	 */
	public int saveDviname(Dviname Dviname) ;
	/**
	 * 删除
	* @param Dviname
	* @return
	 */
	public int deleteDviname(Dviname Dviname);

	public PageInfo<Dviname> findPageInfo(Map<String, Object> params);

}
