//Powered By zsCat, Since 2016 - 2020

package com.zsTrade.web.blog.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.blog.model.Blog;

/**
* @author zsCat 2016-6-14 13:56:03
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */

public interface BlogService extends BaseService<Blog>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Blog
	 * @return
	 */
	public int saveBlog(Blog record) ;
	/**
	 * 删除
	* @param CmsArticle
	* @return
	 */
	public int deleteBlog(Blog record);
	
	/**
	 * 获取上一个博客
	 * @param id
	 * @return
	 */
	public Blog getLastBlog(Long id);
	
	/**
	 * 获取下一个博客
	 * @param id
	 * @return
	 */
	public Blog getNextBlog(Long id);

}
