package com.zsTrade.web.blog.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zsTrade.web.blog.model.BlogType;
import com.zsTrade.web.blog.service.BlogTypeService;
	/**
	 * 
	 * @author zs 2016-5-5 11:32:31
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	我的blog
	 */
@Controller
@RequestMapping("blogType")
public class BlogTypeController {

	@Resource
	private BlogTypeService BlogTypeService;
	
	@RequestMapping
	public String toBlogType(Model model){
		return "blog/blogType/blogType";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute BlogType BlogType) {
		return BlogTypeService.saveBlogType(BlogType);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute BlogType BlogType){
		return BlogTypeService.deleteBlogType(BlogType);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute BlogType BlogType, Model model) {
		PageInfo<BlogType> page = BlogTypeService.selectPage(pageNum, pageSize, BlogType);
		model.addAttribute("page", page);
		return "blog/blogType/blogType-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		BlogType blogType = BlogTypeService.selectByPrimaryKey(id);
		model.addAttribute("blogType", blogType);
		return "blog/blogType/blogType-save";
	}
	
}
