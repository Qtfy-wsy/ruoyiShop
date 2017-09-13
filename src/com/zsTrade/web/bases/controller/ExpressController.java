package com.zsTrade.web.bases.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.bases.service.ExpressService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.bases.model.Express;
	/**
	 * 
	 * @author zsCat 2016-10-31 20:31:15
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	基础管理
	 */
@Controller
@RequestMapping("express")
public class ExpressController {

	@Resource
	private ExpressService ExpressService;
	
	@RequestMapping
	public String toExpress(Model model){
		return "base/express/express";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Express Express) {
		return ExpressService.saveExpress(Express);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Express Express){
		return ExpressService.deleteExpress(Express);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Express Express, Model model) {
		PageInfo<Express> page = ExpressService.selectPage(pageNum, pageSize, Express);
		model.addAttribute("page", page);
		return "base/express/express-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Express express = ExpressService.selectByPrimaryKey(id);
		model.addAttribute("express", express);
		return "base/express/express-save";
	}
	
}
