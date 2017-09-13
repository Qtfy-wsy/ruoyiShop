/** Powered By zscat科技, Since 2016 - 2020 */
package com.zsTrade.web.prj.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.prj.service.ArticleService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.prj.model.Article;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2016-12-22 16:15:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	文章管理
 */
@Controller
@RequestMapping("article")
public class ArticleController {

	@Resource
	private ArticleService ArticleService;
	
	@RequestMapping
	public String toArticle(Model model){
		return "prj/article/article";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Article Article, Model model) {
		PageInfo<Article> page = ArticleService.selectPage(pageNum, pageSize, Article);
		model.addAttribute("page", page);
		return "prj/article/article-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String  save(@ModelAttribute Article Article) {
		try {
			 ArticleService.saveArticle(Article);
		  return "redirect:/index#/ajax/article";
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Article Article){
		try {
			return ArticleService.deleteArticle(Article);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Article article = ArticleService.selectByPrimaryKey(id);
		model.addAttribute("article", article);
		return "prj/article/article-save";
	}
	
}
