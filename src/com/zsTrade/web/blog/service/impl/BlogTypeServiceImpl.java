//Powered By zsCat, Since 2014 - 2020

package com.zsTrade.web.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.blog.mapper.BlogTypeMapper;
import com.zsTrade.web.blog.model.BlogType;
import com.zsTrade.web.blog.service.BlogTypeService;

/**
* @author zsCat 2016-6-14 13:56:36
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */

@Service("BlogTypeService")
public class BlogTypeServiceImpl extends ServiceMybatis<BlogType> implements BlogTypeService {

	@Resource
	private BlogTypeMapper BlogTypeMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param BlogType
	 * @return
	 */
	public int saveBlogType(BlogType record) {
		return this.save(record);
	}

	/**
	 * 删除
	* @param CmsArticle
	* @return
	 */
	public int deleteBlogType(BlogType record) {
		return this.delete(record);
	}


}
