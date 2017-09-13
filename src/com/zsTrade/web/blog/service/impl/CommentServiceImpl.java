//Powered By zsCat, Since 2014 - 2020

package com.zsTrade.web.blog.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.blog.mapper.CommentMapper;
import com.zsTrade.web.blog.model.Comment;
import com.zsTrade.web.blog.service.CommentService;

/**
* @author zsCat 2016-6-14 13:56:49
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */

@Service("CommentService")
public class CommentServiceImpl extends ServiceMybatis<Comment> implements CommentService {

	@Resource
	private CommentMapper CommentMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Comment
	 * @return
	 */
	public int saveComment(Comment record) {
		return this.save(record);
	}

	/**
	 * 删除
	* @param CmsArticle
	* @return
	 */
	public int deleteComment(Comment record) {
		return this.delete(record);
	}


}
