/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.Order;

 /**
 * 
 * @author zsCat 2017-1-7 16:06:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	订单管理
 */
public interface OrderService extends BaseService<Order>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Order
	 * @return
	 */
	public int saveOrder(Order Order) ;
	/**
	 * 删除
	* @param Order
	* @return
	 */
	public int deleteOrder(Order Order);

	public PageInfo<Order> findPageInfo(Map<String, Object> params);
	public Order insertOrder(String[] cartIds, Long addressid, Long paymentid, String usercontent);

}
