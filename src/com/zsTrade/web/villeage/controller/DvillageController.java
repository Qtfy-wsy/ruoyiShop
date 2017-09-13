/** Powered By zscat科技, Since 2016 - 2020 */
package com.zsTrade.web.villeage.controller;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zsTrade.web.sys.utils.SysUserUtils;
import com.zsTrade.web.villeage.service.DvillageService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.villeage.model.Dproduct;
import com.zsTrade.web.villeage.model.Dvillage;
import com.zsTrade.common.utils.FileUtils;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2017-1-19 10:17:13
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Controller
@RequestMapping("dvillage")
public class DvillageController {

	@Resource
	private DvillageService DvillageService;
	
	@RequestMapping
	public String toDvillage(Model model){
		return "villeage/dvillage/dvillage";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Dvillage Dvillage, Model model) {
		PageInfo<Dvillage> page = DvillageService.selectPage(pageNum, pageSize, Dvillage);
		model.addAttribute("page", page);
		return "villeage/dvillage/dvillage-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute Dvillage Dvillage,
			HttpServletRequest request,@RequestParam(value="imgs",required=false) MultipartFile imgs) {
		try {
			String pictureSaveFilePath = request.getSession().getServletContext()
				      .getRealPath("/static/upload/vi/store");
			if (null != imgs && !imgs.isEmpty()) {
				try {
					UUID id = UUID.randomUUID();

					Dvillage.setImg(imgs.getOriginalFilename());
					FileUtils.copyFile(imgs.getInputStream(), pictureSaveFilePath,imgs.getOriginalFilename()).replaceAll("-", "");
				
				} catch (IOException e) {
				 
				}
			}
			Dvillage.setUserid(SysUserUtils.getCacheLoginUser().getId());
			DvillageService.saveDvillage(Dvillage); 
			return "redirect:/index#/ajax/dvillage";
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
	public @ResponseBody Integer del(@ModelAttribute Dvillage Dvillage){
		try {
			return DvillageService.deleteDvillage(Dvillage);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Dvillage dvillage = DvillageService.selectByPrimaryKey(id);
		model.addAttribute("dvillage", dvillage);
		return "villeage/dvillage/dvillage-save";
	}
	
}
