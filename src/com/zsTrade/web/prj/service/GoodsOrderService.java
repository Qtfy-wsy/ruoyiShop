/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.GoodsOrder;

 /**
 * 
 * @author zsCat 2017-1-7 16:07:35
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	商品订单管理
 */
public interface GoodsOrderService extends BaseService<GoodsOrder>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param GoodsOrder
	 * @return
	 */
	public int saveGoodsOrder(GoodsOrder GoodsOrder) ;
	/**
	 * 删除
	* @param GoodsOrder
	* @return
	 */
	public int deleteGoodsOrder(GoodsOrder GoodsOrder);

	public PageInfo<GoodsOrder> findPageInfo(Map<String, Object> params);

}
