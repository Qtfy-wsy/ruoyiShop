package com.zsTrade.web.blog.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zsTrade.web.blog.model.Link;
import com.zsTrade.web.blog.service.LinkService;
	/**
	 * 
	 * @author zs 2016-5-5 11:35:57
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	我的blog
	 */
@Controller
@RequestMapping("link")
public class LinkController {

	@Resource
	private LinkService LinkService;
	
	@RequestMapping
	public String toLink(Model model){
		return "blog/link/link";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Link Link) {
		return LinkService.saveLink(Link);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Link Link){
		return LinkService.deleteLink(Link);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Link Link, Model model) {
		PageInfo<Link> page = LinkService.selectPage(pageNum, pageSize, Link);
		model.addAttribute("page", page);
		return "blog/link/link-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Link link = LinkService.selectByPrimaryKey(id);
		model.addAttribute("link", link);
		return "blog/link/link-save";
	}
	
}
