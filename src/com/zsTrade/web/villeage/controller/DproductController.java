/** Powered By zscat科技, Since 2016 - 2020 */
package com.zsTrade.web.villeage.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zsTrade.web.prj.model.Product;
import com.zsTrade.web.sys.utils.SysUserUtils;
import com.zsTrade.web.villeage.service.DproductService;
import com.github.pagehelper.PageInfo;
import com.zsTrade.web.villeage.model.Dproduct;
import com.zsTrade.common.utils.FileUtils;
import com.zsTrade.common.utils.LogUtils;
/**
 * 
 * @author zsCat 2017-1-19 11:45:30
 * @Email: 951449465@qq.com
 * @version 1.0v
 *	村庄管理
 */
@Controller
@RequestMapping("dproduct")
public class DproductController {

	@Resource
	private DproductService DproductService;
	
	@RequestMapping
	public String toDproduct(Model model){
		return "villeage/dproduct/dproduct";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Dproduct Dproduct, Model model) {
		PageInfo<Dproduct> page = DproductService.selectPage(pageNum, pageSize, Dproduct);
		model.addAttribute("page", page);
		return "villeage/dproduct/dproduct-list";
	}
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute Dproduct Product,
			HttpServletRequest request,@RequestParam(value="imgs",required=false) MultipartFile imgs) {
		Product.setClickhit(0);
		Product.setTypeid(Long.parseLong(Product.getTypename().split(",")[0]));
		Product.setTypename(Product.getTypename().split(",")[1]);
		Product.setVillageid(SysUserUtils.getCacheLoginUser().getVillageid());
		//String pictureSaveFilePath=ZsCatConstant.pictureProjectSaveFilePath;
		String pictureSaveFilePath = request.getSession().getServletContext()
			      .getRealPath("/static/upload/vi/project");
		if (null != imgs && !imgs.isEmpty()) {
			try {
				UUID id = UUID.randomUUID();

				Product.setImg(imgs.getOriginalFilename());
				FileUtils.copyFile(imgs.getInputStream(), pictureSaveFilePath,imgs.getOriginalFilename()).replaceAll("-", "");
			
			} catch (IOException e) {
			 
			}
		}
		String imges="";
		String blogInfo=Product.getRemark();
		Document doc=Jsoup.parse(blogInfo);
		Elements jpgs=doc.select("img[src]"); //　查找扩展名是jpg的图片
		for(int i=0;i<jpgs.size();i++){
			Element jpg=jpgs.get(i);
			if(jpg!=null && jpg!=null){
				String linkHref = jpg.attr("src");
				imges+=linkHref+",";
			}
			if(i==2){
				break;
			}
		}
		Product.setImgmore(imges);
		DproductService.saveDproduct(Product);
		return "redirect:/index#/ajax/dproduct";
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Dproduct Dproduct){
		try {
			return DproductService.deleteDproduct(Dproduct);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Dproduct dproduct = DproductService.selectByPrimaryKey(id);
		model.addAttribute("dproduct", dproduct);
		return "villeage/dproduct/dproduct-save";
	}
	
}
