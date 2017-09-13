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
import com.zsTrade.web.prj.service.FloorProductService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.prj.model.FloorProduct;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2016-12-22 14:23:22
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层产品管理
 */
@Controller
@RequestMapping("floorProduct")
public class FloorProductController {

	@Resource
	private FloorProductService FloorProductService;
	
	@RequestMapping
	public String toFloorProduct(Model model){
		return "prj/floorProduct/floorProduct";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute FloorProduct FloorProduct, Model model) {
		PageInfo<FloorProduct> page = FloorProductService.selectPage(pageNum, pageSize, FloorProduct);
		model.addAttribute("page", page);
		return "prj/floorProduct/floorProduct-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute FloorProduct FloorProduct) {
		try {
			return FloorProductService.saveFloorProduct(FloorProduct);
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
	public @ResponseBody Integer del(@ModelAttribute FloorProduct FloorProduct){
		try {
			return FloorProductService.deleteFloorProduct(FloorProduct);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		FloorProduct floorProduct = FloorProductService.selectByPrimaryKey(id);
		model.addAttribute("floorProduct", floorProduct);
		return "prj/floorProduct/floorProduct-save";
	}
	
}
