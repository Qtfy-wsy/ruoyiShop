package com.zsTrade.web.bases.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.bases.service.FavoritesService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.bases.model.Favorites;
	/**
	 * 
	 * @author zsCat 2016-11-2 17:13:42
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	优惠劵管理
	 */
@Controller
@RequestMapping("favorites")
public class FavoritesController {

	@Resource
	private FavoritesService FavoritesService;
	
	@RequestMapping
	public String toFavorites(Model model){
		return "base/favorites/favorites";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Favorites Favorites) {
		return FavoritesService.saveFavorites(Favorites);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Favorites Favorites){
		return FavoritesService.deleteFavorites(Favorites);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Favorites Favorites, Model model) {
		PageInfo<Favorites> page = FavoritesService.selectPage(pageNum, pageSize, Favorites);
		model.addAttribute("page", page);
		return "base/favorites/favorites-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Favorites favorites = FavoritesService.selectByPrimaryKey(id);
		model.addAttribute("favorites", favorites);
		return "base/favorites/favorites-save";
	}
	
}
