/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.BaseService;
import com.zsTrade.web.prj.model.Cart;

 /**
 * 
 * @author zsCat 2017-1-7 15:58:43
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	评论管理
 */
public interface CartService extends BaseService<Cart>{

	

	
	/**
	 * 保存或更新
	 * 
	 * @param Cart
	 * @return
	 */
	public int saveCart(Cart Cart) ;
	/**
	 * 删除
	* @param Cart
	* @return
	 */
	public int deleteCart(Cart Cart);

	public PageInfo<Cart> findPageInfo(Map<String, Object> params);
	public List<Cart> selectOwnCart();
	int selectOwnCartCount();

}
