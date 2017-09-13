//Powered By zsCat, Since 2014 - 2020

package com.zsTrade.web.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.blog.mapper.LinkMapper;
import com.zsTrade.web.blog.model.Link;
import com.zsTrade.web.blog.service.LinkService;

/**
* @author zsCat 2016-6-14 13:57:04
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */

@Service("LinkService")
public class LinkServiceImpl extends ServiceMybatis<Link> implements LinkService {

	@Resource
	private LinkMapper LinkMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Link
	 * @return
	 */
	public int saveLink(Link record) {
		return this.save(record);
	}

	/**
	 * 删除
	* @param CmsArticle
	* @return
	 */
	public int deleteLink(Link record) {
		return this.delete(record);
	}


}
