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
import com.zsTrade.web.villeage.service.DvinameService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.villeage.model.Dviname;
import com.zsTrade.common.utils.FileUtils;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2017-1-19 10:37:11
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Controller
@RequestMapping("dviname")
public class DvinameController {

	@Resource
	private DvinameService DvinameService;
	
	@RequestMapping
	public String toDviname(Model model){
		return "villeage/dviname/dviname";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Dviname Dviname, Model model) {
		PageInfo<Dviname> page = DvinameService.selectPage(pageNum, pageSize, Dviname);
		model.addAttribute("page", page);
		return "villeage/dviname/dviname-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public  String save(@ModelAttribute Dviname Dviname,
			HttpServletRequest request,@RequestParam(value="imgs",required=false) MultipartFile imgs) {
		Dviname.setVillageid(SysUserUtils.getCacheLoginUser().getVillageid());
		//String pictureSaveFilePath=ZsCatConstant.pictureProjectSaveFilePath;
		String pictureSaveFilePath = request.getSession().getServletContext()
			      .getRealPath("/static/upload/viname");
		if (null != imgs && !imgs.isEmpty()) {
			try {
				UUID id = UUID.randomUUID();
				Dviname.setImg(imgs.getOriginalFilename());
				FileUtils.copyFile(imgs.getInputStream(), pictureSaveFilePath,imgs.getOriginalFilename()).replaceAll("-", "");
			
			} catch (IOException e) {
			 
			}
		}
		DvinameService.saveDviname(Dviname);
		return "redirect:/index#/ajax/dviname";
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Dviname Dviname){
		try {
			return DvinameService.deleteDviname(Dviname);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Dviname dviname = DvinameService.selectByPrimaryKey(id);
		model.addAttribute("dviname", dviname);
		return "villeage/dviname/dviname-save";
	}
	
}
