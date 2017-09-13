package com.zsTrade.web.bases.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.bases.service.NavigationService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.bases.model.Navigation;
	/**
	 * 
	 * @author zsCat 2016-10-31 20:31:35
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	基础管理
	 */
@Controller
@RequestMapping("navigation")
public class NavigationController {

	@Resource
	private NavigationService NavigationService;
	
	@RequestMapping
	public String toNavigation(Model model){
		return "base/navigation/navigation";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Navigation Navigation) {
		return NavigationService.saveNavigation(Navigation);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Navigation Navigation){
		return NavigationService.deleteNavigation(Navigation);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Navigation Navigation, Model model) {
		PageInfo<Navigation> page = NavigationService.selectPage(pageNum, pageSize, Navigation);
		model.addAttribute("page", page);
		return "base/navigation/navigation-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Navigation navigation = NavigationService.selectByPrimaryKey(id);
		model.addAttribute("navigation", navigation);
		return "base/navigation/navigation-save";
	}
	
}
