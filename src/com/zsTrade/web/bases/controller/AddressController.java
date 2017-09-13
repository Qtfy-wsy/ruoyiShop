package com.zsTrade.web.bases.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.bases.service.AddressService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.bases.model.Address;
	/**
	 * 
	 * @author zsCat 2016-10-31 20:29:45
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	基础管理
	 */
@Controller
@RequestMapping("address")
public class AddressController {

	@Resource
	private AddressService AddressService;
	
	@RequestMapping
	public String toAddress(Model model){
		return "base/address/address";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Address Address) {
		return AddressService.saveAddress(Address);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Address Address){
		return AddressService.deleteAddress(Address);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Address Address, Model model) {
		PageInfo<Address> page = AddressService.selectPage(pageNum, pageSize, Address);
		model.addAttribute("page", page);
		return "base/address/address-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Address address = AddressService.selectByPrimaryKey(id);
		model.addAttribute("address", address);
		return "base/address/address-save";
	}
	
}
