package com.zsTrade.web.bases.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.bases.service.AreaService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.bases.model.Area;
	/**
	 * 
	 * @author zsCat 2016-11-2 10:56:35
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	优惠劵管理
	 */
@Controller
@RequestMapping("area")
public class AreaController {

	@Resource
	private AreaService AreaService;
	
	@RequestMapping
	public String toArea(Model model){
		return "base/area/area";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Area Area) {
		return AreaService.saveArea(Area);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Area Area){
		return AreaService.deleteArea(Area);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Area Area, Model model) {
		PageInfo<Area> page = AreaService.selectPage(pageNum, pageSize, Area);
		model.addAttribute("page", page);
		return "base/area/area-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Area area = AreaService.selectByPrimaryKey(id);
		model.addAttribute("area", area);
		return "base/area/area-save";
	}
	
}
