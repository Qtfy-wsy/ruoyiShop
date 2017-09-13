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
import com.zsTrade.web.prj.service.OrderService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.prj.model.Order;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2017-1-7 16:06:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	订单管理
 */
@Controller
@RequestMapping("order")
public class OrderController {

	@Resource
	private OrderService OrderService;
	
	@RequestMapping
	public String toOrder(Model model){
		return "prj/order/order";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Order Order, Model model) {
		PageInfo<Order> page = OrderService.selectPage(pageNum, pageSize, Order);
		model.addAttribute("page", page);
		return "prj/order/order-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Order Order) {
		try {
			return OrderService.saveOrder(Order);
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
	public @ResponseBody Integer del(@ModelAttribute Order Order){
		try {
			return OrderService.deleteOrder(Order);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Order order = OrderService.selectByPrimaryKey(id);
		model.addAttribute("order", order);
		return "prj/order/order-save";
	}
	
}
