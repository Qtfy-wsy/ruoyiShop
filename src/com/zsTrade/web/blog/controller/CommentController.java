package com.zsTrade.web.blog.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zsTrade.web.blog.model.Comment;
import com.zsTrade.web.blog.service.CommentService;
	/**
	 * 
	 * @author zs 2016-5-5 11:35:17
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	我的blog
	 */
@Controller
@RequestMapping("comment")
public class CommentController {

	@Resource
	private CommentService CommentService;
	
	@RequestMapping
	public String toComment(Model model){
		return "blog/comment/comment";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Comment Comment) {
		return CommentService.saveComment(Comment);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Comment Comment){
		return CommentService.deleteComment(Comment);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Comment Comment, Model model) {
		PageInfo<Comment> page = CommentService.selectPage(pageNum, pageSize, Comment);
		model.addAttribute("page", page);
		return "blog/comment/comment-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Comment comment = CommentService.selectByPrimaryKey(id);
		model.addAttribute("comment", comment);
		return "blog/comment/comment-save";
	}
	
}
