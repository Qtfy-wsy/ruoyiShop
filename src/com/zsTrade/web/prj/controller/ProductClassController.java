package com.zsTrade.web.prj.controller;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.prj.model.ProductClass;
import com.zsTrade.web.prj.service.ProductClassService;
	/**
	 * 
	 * @author zs 2016-5-24 21:52:07
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	我的prj
	 */
@Controller
@RequestMapping("productClass")
public class ProductClassController {

	@Resource
	private ProductClassService ProductClassService;
	
	@RequestMapping
	public String toProductClass(Model model) {
		model.addAttribute("treeList", JSON.toJSONString(ProductClassService.select(null)));
		return "prj/productClass/productClass";
	}

	/**
	 * 区域树
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<ProductClass> getProductClassTreeList() {
		List<ProductClass> list = ProductClassService.select(null);
		return list;
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ProductClass ProductClass,HttpServletRequest request) {
		ProductClassService.saveProductClass(ProductClass);
		ProductClass.setParentIds(ProductClass.getId()+","+ProductClass.getParentIds()+","); 
		return ProductClassService.updateByPrimaryKeySelective(ProductClass);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute ProductClass ProductClass){
		return ProductClassService.deleteProductClass(ProductClass);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(int pageNum,int pageSize,@ModelAttribute ProductClass ProductClass, Model model) {
		PageInfo<ProductClass> page = ProductClassService.selectPage(pageNum, pageSize, ProductClass);
		model.addAttribute("page", page);
		return "prj/productClass/productClass-list";
	}
	

	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showLayer(Long id, Long parentId,
			@PathVariable("mode") String mode, Model model) {
		ProductClass productClass = null, pProductClass = null;
		if (StringUtils.equalsIgnoreCase(mode, "add")) {
			pProductClass = ProductClassService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
			productClass = ProductClassService.selectByPrimaryKey(id);
			pProductClass = ProductClassService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "detail")) {
			productClass = ProductClassService.selectByPrimaryKey(id);
			pProductClass = ProductClassService.selectByPrimaryKey(productClass.getParentId());
		}
		model.addAttribute("pProductClass", pProductClass).addAttribute("productClass", productClass);
		return mode.equals("detail") ? "prj/productClass/productClass-detail"
				: "prj/productClass/productClass-save";
	}
	
}
