/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.mapper;
import java.util.List;
import java.util.Map;
import com.github.abel533.mapper.Mapper;
import com.zsTrade.web.prj.model.OrderLog;


/**
 * 
 * @author zsCat 2017-1-7 16:57:31
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	订单日志管理
 */
public interface OrderLogMapper extends Mapper<OrderLog>{
	public List<OrderLog> findPageInfo(Map<String, Object> params);
	
}
