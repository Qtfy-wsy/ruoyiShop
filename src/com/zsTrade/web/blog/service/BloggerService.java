//Powered By zsCat, Since 2016 - 2020

package com.zsTrade.web.blog.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.blog.model.Blogger;

/**
* @author zsCat 2016-6-14 13:56:18
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */

public interface BloggerService extends BaseService<Blogger>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Blogger
	 * @return
	 */
	public int saveBlogger(Blogger record) ;
	/**
	 * 删除
	* @param CmsArticle
	* @return
	 */
	public int deleteBlogger(Blogger record);
	public Blogger checkBlogger(String username, String password);


}
