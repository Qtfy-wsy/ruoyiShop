//Powered By zsCat, Since 2016 - 2020

package com.zsTrade.web.blog.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.blog.model.Link;

/**
* @author zsCat 2016-6-14 13:57:04
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */

public interface LinkService extends BaseService<Link>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Link
	 * @return
	 */
	public int saveLink(Link record) ;
	/**
	 * 删除
	* @param CmsArticle
	* @return
	 */
	public int deleteLink(Link record);


}
