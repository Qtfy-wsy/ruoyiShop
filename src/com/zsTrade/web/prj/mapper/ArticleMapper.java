/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.mapper;
import java.util.List;
import java.util.Map;
import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.Article;


/**
 * 
 * @author zsCat 2016-12-22 16:15:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	文章管理
 */
public interface ArticleMapper extends Mapper<Article>{
	public List<Article> findPageInfo(Map<String, Object> params);
	
}
