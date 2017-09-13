/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.mapper;
import java.util.List;
import java.util.Map;
import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.GoodsOrder;


/**
 * 
 * @author zsCat 2017-1-7 16:07:35
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	商品订单管理
 */
public interface GoodsOrderMapper extends Mapper<GoodsOrder>{
	public List<GoodsOrder> findPageInfo(Map<String, Object> params);
	
}
