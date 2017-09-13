package com.zsTrade.web.bases.controller;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.bases.service.ConsultService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.bases.model.Consult;
	/**
	 * 
	 * @author zsCat 2016-11-1 14:47:36
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	优惠劵管理
	 */
@Controller
@RequestMapping("consult")
public class ConsultController {

	@Resource
	private ConsultService ConsultService;
	
	@RequestMapping
	public String toConsult(Model model){
		return "base/consult/consult";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Consult Consult) {
		return ConsultService.saveConsult(Consult);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Consult Consult){
		return ConsultService.deleteConsult(Consult);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute Consult Consult, Model model) {
		PageInfo<Consult> page = ConsultService.selectPage(pageNum, pageSize, Consult);
		model.addAttribute("page", page);
		return "base/consult/consult-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Consult consult = ConsultService.selectByPrimaryKey(id);
		model.addAttribute("consult", consult);
		return "base/consult/consult-save";
	}
	
}
