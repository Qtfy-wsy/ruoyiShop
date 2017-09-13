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

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.utils.LogUtils;
import com.zsTrade.web.prj.model.ProductType;
import com.zsTrade.web.prj.service.ProductTypeService;
/**
 * 
 * @author zsCat 2016-12-22 9:29:55
 * @Email: [email]
 * @version [version]
 *	项目类别管理
 */
@Controller
@RequestMapping("productType")
public class ProductTypeController {

	@Resource
	private ProductTypeService ProductTypeService;
	
	@RequestMapping
	public String toProductType(Model model){
		return "prj/productType/productType";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute ProductType ProductType, Model model) {
		PageInfo<ProductType> page = ProductTypeService.selectPage(pageNum, pageSize, ProductType);
		model.addAttribute("page", page);
		return "prj/productType/productType-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute ProductType ProductType) {
		try {
			return ProductTypeService.saveProductType(ProductType);
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
	public @ResponseBody Integer del(@ModelAttribute ProductType ProductType){
		try {
			return ProductTypeService.deleteProductType(ProductType);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		ProductType productType = ProductTypeService.selectByPrimaryKey(id);
		model.addAttribute("productType", productType);
		return "prj/productType/productType-save";
	}
	
}
