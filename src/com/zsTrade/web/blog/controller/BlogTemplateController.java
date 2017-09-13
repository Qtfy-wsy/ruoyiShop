package com.zsTrade.web.blog.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zsTrade.web.blog.model.BlogTemplate;
import com.zsTrade.web.blog.service.BlogTemplateService;
	/**
	 * 
	 * @author zsCat 2016-11-4 17:53:14
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	博客模板
	 */
@Controller
@RequestMapping("blogTemplate")
public class BlogTemplateController {

	@Resource
	private BlogTemplateService BlogTemplateService;
	
	@RequestMapping
	public String toBlogTemplate(Model model){
		return "blog/blogTemplate/blogTemplate";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute BlogTemplate BlogTemplate) {
		return BlogTemplateService.save(BlogTemplate);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute BlogTemplate BlogTemplate){
		return BlogTemplateService.deleteBlogTemplate(BlogTemplate);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute BlogTemplate BlogTemplate, Model model) {
		PageInfo<BlogTemplate> page = BlogTemplateService.selectPage(pageNum, pageSize, BlogTemplate);
		model.addAttribute("page", page);
		return "blog/blogTemplate/blogTemplate-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		BlogTemplate blogTemplate = BlogTemplateService.selectByPrimaryKey(id);
		model.addAttribute("blogTemplate", blogTemplate);
		return "blog/blogTemplate/blogTemplate-save";
	}
	
}
