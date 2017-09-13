/** Powered By zscat科技, Since 2016 - 2020 */

package com.zsTrade.web.prj.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zsTrade.common.base.ServiceMybatis;
import com.zsTrade.web.prj.mapper.CartMapper;
import com.zsTrade.web.prj.model.Cart;
import com.zsTrade.web.prj.service.CartService;
import com.zsTrade.web.sys.utils.SysUserUtils;
/**
 * 
 * @author zsCat 2017-1-7 15:58:43
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	评论管理
 */
@Service("CartService")
public class CartServiceImpl  extends ServiceMybatis<Cart> implements CartService {

	@Resource
	private CartMapper CartMapper;

	
	/**
	 * 保存或更新
	 * 
	 * @param Cart
	 * @return
	 */
	public int saveCart(Cart Cart) {
		return this.save(Cart);
	}

	/**
	 * 删除
	* @param Cart
	* @return
	 */
	public int deleteCart(Cart Cart) {
		return this.delete(Cart);
	}

   @Override
	public PageInfo<Cart> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Cart> list = CartMapper.findPageInfo(params);
		return new PageInfo<Cart>(list);
	}
   @Override
	public List<Cart> selectOwnCart() {
		if(SysUserUtils.getSessionLoginUser()!=null){
			Cart cart=new Cart();
			cart.setUserid(SysUserUtils.getSessionLoginUser().getId());
			return CartMapper.select(cart);
		}
		return null;
		
	}
	@Override
	public int selectOwnCartCount() {
		if(SysUserUtils.getSessionLoginUser()!=null){
			Cart cart=new Cart();
			cart.setUserid(SysUserUtils.getSessionLoginUser().getId());
			return CartMapper.selectCount(cart);
		}
		return 0;
		
	}
}
