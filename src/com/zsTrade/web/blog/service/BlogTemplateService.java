//Powered By if, Since 2014 - 2020

package com.zsTrade.web.blog.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.blog.model.BlogTemplate;

/**
 * 
 * @author
 */

public interface BlogTemplateService extends BaseService<BlogTemplate>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param BlogTemplate
	 * @return
	 */
	public int save(BlogTemplate BlogTemplate) ;
	/**
	 * 删除
	* @param BlogTemplate
	* @return
	 */
	public int deleteBlogTemplate(BlogTemplate BlogTemplate);


}
