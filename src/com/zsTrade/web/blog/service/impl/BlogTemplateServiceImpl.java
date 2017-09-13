//Powered By ZSCAT, Since 2014 - 2020

package com.zsTrade.web.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.blog.mapper.BlogTemplateMapper;
import com.zsTrade.web.blog.model.BlogTemplate;
import com.zsTrade.web.blog.service.BlogTemplateService;

/**
 * 
 * @author
 */

@Service("BlogTemplateService")
public class BlogTemplateServiceImpl extends ServiceMybatis<BlogTemplate> implements BlogTemplateService {

	@Resource
	private BlogTemplateMapper BlogTemplateMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param BlogTemplate
	 * @return
	 */
	public int saveBlogTemplate(BlogTemplate BlogTemplate) {
		return this.save(BlogTemplate);
	}

	/**
	 * 删除
	* @param BlogTemplate
	* @return
	 */
	public int deleteBlogTemplate(BlogTemplate BlogTemplate) {
		return this.delete(BlogTemplate);
	}


}
