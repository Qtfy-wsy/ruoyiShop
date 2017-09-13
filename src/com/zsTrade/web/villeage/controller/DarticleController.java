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

import com.zsTrade.web.sys.utils.SysUserUtils;
import com.zsTrade.web.villeage.service.DarticleService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.villeage.model.Darticle;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2017-1-19 10:16:56
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Controller
@RequestMapping("darticle")
public class DarticleController {

	@Resource
	private DarticleService DarticleService;
	
	@RequestMapping
	public String toDarticle(Model model){
		return "villeage/darticle/darticle";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Darticle Darticle, Model model) {
		PageInfo<Darticle> page = DarticleService.selectPage(pageNum, pageSize, Darticle);
		model.addAttribute("page", page);
		return "villeage/darticle/darticle-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Darticle Darticle) {
		try {
			//Darticle.setVillageid(villageid);
			Darticle.setUserid(SysUserUtils.getCacheLoginUser().getId());
			return DarticleService.saveDarticle(Darticle);
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
	public @ResponseBody Integer del(@ModelAttribute Darticle Darticle){
		try {
			return DarticleService.deleteDarticle(Darticle);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Darticle darticle = DarticleService.selectByPrimaryKey(id);
		model.addAttribute("darticle", darticle);
		return "villeage/darticle/darticle-save";
	}
	
}
