/** Powered By zscat科技, Since 2016 - 2020 */
package com.zsTrade.web.prj.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.utils.FileUtils;
import com.zsTrade.common.utils.LogUtils;
import com.zsTrade.web.prj.model.Floor;
import com.zsTrade.web.prj.model.Product;
import com.zsTrade.web.prj.model.ProductClass;
import com.zsTrade.web.prj.service.FloorService;
import com.zsTrade.web.prj.service.ProductClassService;
import com.zsTrade.web.prj.service.ProductService;
import com.zsTrade.web.sys.model.SysRole;
import com.zsTrade.web.sys.model.SysUser;
/**
 * 
 * @author zsCat 2016-12-22 14:23:55
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	楼层管理
 */
@Controller
@RequestMapping("floor")
public class FloorController {
	@Resource
	private ProductClassService productClassService;
	@Resource
	private FloorService FloorService;
	@Resource
	private ProductService productService;
	@RequestMapping
	public String toFloor(Model model){
		return "prj/floor/floor";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Floor Floor, Model model) {
		PageInfo<Floor> page = FloorService.selectPage(pageNum, pageSize, Floor);
		model.addAttribute("page", page);
		return "prj/floor/floor-list";
	}
	
	@RequestMapping(value="saveProduct",method=RequestMethod.POST)
	public @ResponseBody Integer saveFlorProduct(@ModelAttribute Floor Floor){
		return FloorService.saveFloorProduct(Floor);
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
//	@RequestMapping(value = "save", method = RequestMethod.POST)
//	public @ResponseBody Integer save(@ModelAttribute Floor Floor) {
//		try {
//			return FloorService.saveFloor(Floor);
//		} catch (Exception e) {
//			LogUtils.ERROR_LOG.error(e.getMessage());
//			e.printStackTrace();
//		}
//		return null;
//	}
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute Floor Floor,
			HttpServletRequest request,@RequestParam(value="imgs",required=false) MultipartFile imgs) {
		
		//String pictureSaveFilePath=ZsCatConstant.pictureProjectSaveFilePath;
		String pictureSaveFilePath = request.getSession().getServletContext()
			      .getRealPath("/static/upload/floorAdv");
		if (null != imgs && !imgs.isEmpty()) {
			try {
				UUID id = UUID.randomUUID();
	
				Floor.setAdvimg(imgs.getOriginalFilename());
				FileUtils.copyFile(imgs.getInputStream(), pictureSaveFilePath,imgs.getOriginalFilename()).replaceAll("-", "");
			} catch (IOException e) {
			 
			}
		}
		
		FloorService.saveFloor(Floor);
		return "redirect:/index#/ajax/floor";
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Floor Floor){
		try {
			return FloorService.deleteFloor(Floor);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Floor floor = FloorService.selectByPrimaryKey(id);
		model.addAttribute("floor", floor);
		return "prj/floor/floor-save";
	}
	
}
