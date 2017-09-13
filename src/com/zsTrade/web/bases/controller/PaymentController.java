package com.zsTrade.web.bases.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.bases.service.PaymentService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.bases.model.Payment;
	/**
	 * 
	 * @author zsCat 2016-11-2 13:20:51
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	优惠劵管理
	 */
@Controller
@RequestMapping("payment")
public class PaymentController {

	@Resource
	private PaymentService PaymentService;
	
	@RequestMapping
	public String toPayment(Model model){
		return "base/payment/payment";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Payment Payment) {
		return PaymentService.savePayment(Payment);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Payment Payment){
		return PaymentService.deletePayment(Payment);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Payment Payment, Model model) {
		PageInfo<Payment> page = PaymentService.selectPage(pageNum, pageSize, Payment);
		model.addAttribute("page", page);
		return "base/payment/payment-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Payment payment = PaymentService.selectByPrimaryKey(id);
		model.addAttribute("payment", payment);
		return "base/payment/payment-save";
	}
	
}
