//Powered By zsCat, Since 2016 - 2020

package com.zsTrade.web.blog.service;

import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.blog.model.Comment;

/**
* @author zsCat 2016-6-14 13:56:49
 * @Email: 951449465@qq.com
 * @version 4.0v
 *	我的blog
 */

public interface CommentService extends BaseService<Comment>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Comment
	 * @return
	 */
	public int saveComment(Comment record) ;
	/**
	 * 删除
	* @param CmsArticle
	* @return
	 */
	public int deleteComment(Comment record);


}
