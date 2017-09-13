package com.zsTrade.web.blog.controller;


import java.io.IOException;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.constant.ZsCatConstant;
import com.zsTrade.common.utils.FileUtils;
import com.zsTrade.web.blog.model.Blog;
import com.zsTrade.web.blog.service.BlogService;
	/**
	 * 
	 * @author zs 2016-5-5 11:33:51
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	我的blog
	 */
@Controller
@RequestMapping("blog")
public class BlogController {

	// 博客索引
	//private BlogIndex blogIndex=new BlogIndex();
	@Resource
	private BlogService BlogService;
	
	@RequestMapping
	public String toBlog(Model model){
		return "blog/blog/blog";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute Blog Blog,
			HttpServletRequest request,@RequestParam(value="imgs",required=false) MultipartFile imgs) throws Exception {
		Blog.setReleasedate(new Date());
		Blog.setReplyhit(0);
		Blog.setClickhit(0);
		Blog.setBloggerId(1L);
		Blog.setTypeid(Long.parseLong(Blog.getTypename().split(",")[0]));
		Blog.setTypename(Blog.getTypename().split(",")[1]);
		String pictureSaveFilePath=ZsCatConstant.pictureSaveFilePath;
		//=request.getRealPath("ZSCAT");
		if (null != imgs && !imgs.isEmpty()) {
			try {
				UUID id = UUID.randomUUID();
				// 扩展名格式：
				String extName = "";
				if (imgs.getOriginalFilename().lastIndexOf(".") >= 0) {
					extName = imgs.getOriginalFilename().substring(imgs.getOriginalFilename().lastIndexOf("."));
				}
				Blog.setImg(id+extName);
				FileUtils.copyFile(imgs.getInputStream(), pictureSaveFilePath,id+extName).replaceAll("-", "");
			
			} catch (IOException e) {
			
			}
		}
			
			BlogService.saveBlog(Blog);
			
			return "redirect:/index#/ajax/blog";
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Blog Blog){
		return BlogService.deleteBlog(Blog);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Blog Blog, Model model) {
		PageInfo<Blog> page = BlogService.selectPage(pageNum, pageSize, Blog);
		model.addAttribute("page", page);
		return "blog/blog/blog-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Blog blog = BlogService.selectByPrimaryKey(id);
		model.addAttribute("blog", blog);
		return "blog/blog/blog-save";
	}
	
}
