/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.mapper;
import java.util.List;
import java.util.Map;
import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.Cart;


/**
 * 
 * @author zsCat 2017-1-7 15:58:43
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	评论管理
 */
public interface CartMapper extends Mapper<Cart>{
	public List<Cart> findPageInfo(Map<String, Object> params);
	
}
