//Powered By zsCat, Since 2016 - 2020

package com.zsTrade.web.blog.mapper;

import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.blog.model.Blog;


/**
 * 
 * @author zsCat 2016-6-14 13:56:03
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */
public interface BlogMapper extends Mapper<Blog>{

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
