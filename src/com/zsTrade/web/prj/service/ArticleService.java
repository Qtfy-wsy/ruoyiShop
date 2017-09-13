/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.Article;

 /**
 * 
 * @author zsCat 2016-12-22 16:15:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	文章管理
 */
public interface ArticleService extends BaseService<Article>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Article
	 * @return
	 */
	public int saveArticle(Article Article) ;
	/**
	 * 删除
	* @param Article
	* @return
	 */
	public int deleteArticle(Article Article);

	public PageInfo<Article> findPageInfo(Map<String, Object> params);

}
