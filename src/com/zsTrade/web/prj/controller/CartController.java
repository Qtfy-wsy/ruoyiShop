/** Powered By zscat科技, Since 2016 - 2020 */
package com.zsTrade.web.prj.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.prj.service.CartService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.prj.model.Cart;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2017-1-7 15:58:43
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	评论管理
 */
@Controller
@RequestMapping("cart")
public class CartController {

	@Resource
	private CartService CartService;
	
	@RequestMapping
	public String toCart(Model model){
		return "prj/cart/cart";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Cart Cart, Model model) {
		PageInfo<Cart> page = CartService.selectPage(pageNum, pageSize, Cart);
		model.addAttribute("page", page);
		return "prj/cart/cart-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Cart Cart) {
		try {
			return CartService.saveCart(Cart);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Cart Cart){
		try {
			return CartService.deleteCart(Cart);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Cart cart = CartService.selectByPrimaryKey(id);
		model.addAttribute("cart", cart);
		return "prj/cart/cart-save";
	}
	
}
