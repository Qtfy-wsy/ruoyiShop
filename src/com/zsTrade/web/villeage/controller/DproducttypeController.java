/** Powered By zscat科技, Since 2016 - 2020 */
package com.zsTrade.web.villeage.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.zsTrade.web.villeage.service.DproducttypeService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.villeage.model.Dproducttype;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2017-1-19 11:45:49
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Controller
@RequestMapping("dproducttype")
public class DproducttypeController {

	@Resource
	private DproducttypeService DproducttypeService;
	
	@RequestMapping
	public String toDproducttype(Model model){
		return "villeage/dproducttype/dproducttype";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Dproducttype Dproducttype, Model model) {
		PageInfo<Dproducttype> page = DproducttypeService.selectPage(pageNum, pageSize, Dproducttype);
		model.addAttribute("page", page);
		return "villeage/dproducttype/dproducttype-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Dproducttype Dproducttype) {
		try {
			return DproducttypeService.saveDproducttype(Dproducttype);
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
	public @ResponseBody Integer del(@ModelAttribute Dproducttype Dproducttype){
		try {
			return DproducttypeService.deleteDproducttype(Dproducttype);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Dproducttype dproducttype = DproducttypeService.selectByPrimaryKey(id);
		model.addAttribute("dproducttype", dproducttype);
		return "villeage/dproducttype/dproducttype-save";
	}
	
}
