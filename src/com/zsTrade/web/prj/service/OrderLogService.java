/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.OrderLog;

 /**
 * 
 * @author zsCat 2017-1-7 16:57:31
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	订单日志管理
 */
public interface OrderLogService extends BaseService<OrderLog>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param OrderLog
	 * @return
	 */
	public int saveOrderLog(OrderLog OrderLog) ;
	/**
	 * 删除
	* @param OrderLog
	* @return
	 */
	public int deleteOrderLog(OrderLog OrderLog);

	public PageInfo<OrderLog> findPageInfo(Map<String, Object> params);

}
