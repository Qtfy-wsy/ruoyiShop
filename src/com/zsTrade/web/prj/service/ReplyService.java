/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.Reply;

 /**
 * 
 * @author zsCat 2017-1-7 16:15:50
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	评论管理
 */
public interface ReplyService extends BaseService<Reply>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Reply
	 * @return
	 */
	public int saveReply(Reply Reply) ;
	/**
	 * 删除
	* @param Reply
	* @return
	 */
	public int deleteReply(Reply Reply);

	public PageInfo<Reply> findPageInfo(Map<String, Object> params);

}
