package com.zsTrade.web.blog.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zsTrade.web.blog.model.Blogger;
import com.zsTrade.web.blog.service.BloggerService;
	/**
	 * 
	 * @author zs 2016-5-5 11:34:28
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	我的blog
	 */
@Controller
@RequestMapping("blogger")
public class BloggerController {

	@Resource
	private BloggerService BloggerService;
	
	@RequestMapping
	public String toBlogger(Model model){
		return "blog/blogger/blogger";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Blogger Blogger) {
		return BloggerService.saveBlogger(Blogger);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Blogger Blogger){
		return BloggerService.deleteBlogger(Blogger);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Blogger Blogger, Model model) {
		PageInfo<Blogger> page = BloggerService.selectPage(pageNum, pageSize, Blogger);
		model.addAttribute("page", page);
		return "blog/blogger/blogger-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Blogger blogger = BloggerService.selectByPrimaryKey(id);
		model.addAttribute("blogger", blogger);
		return "blog/blogger/blogger-save";
	}
	
}
