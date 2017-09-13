package com.zsTrade.web.bases.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.bases.service.AdvService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.bases.model.Adv;
	/**
	 * 
	 * @author zsCat 2016-10-31 20:30:14
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	基础管理
	 */
@Controller
@RequestMapping("adv")
public class AdvController {

	@Resource
	private AdvService AdvService;
	
	@RequestMapping
	public String toAdv(Model model){
		return "base/adv/adv";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Adv Adv) {
		return AdvService.saveAdv(Adv);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Adv Adv){
		return AdvService.deleteAdv(Adv);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Adv Adv, Model model) {
		PageInfo<Adv> page = AdvService.selectPage(pageNum, pageSize, Adv);
		model.addAttribute("page", page);
		return "base/adv/adv-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Adv adv = AdvService.selectByPrimaryKey(id);
		model.addAttribute("adv", adv);
		return "base/adv/adv-save";
	}
	
}
