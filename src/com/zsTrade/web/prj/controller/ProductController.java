/** Powered By zscat科技, Since 2016 - 2020 */
package com.zsTrade.web.prj.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.constant.ZsCatConstant;
import com.zsTrade.common.utils.FileUtils;
import com.zsTrade.common.utils.LogUtils;
import com.zsTrade.web.prj.model.Product;
import com.zsTrade.web.prj.model.ProductClass;
import com.zsTrade.web.prj.service.ProductClassService;
import com.zsTrade.web.prj.service.ProductService;
import com.zsTrade.web.sys.utils.SysUserUtils;
/**
 * 
 * @author zsCat 2016-12-22 9:29:05
 * @Email: [email]
 * @version [version]
 *	项目管理
 */
@Controller
@RequestMapping("product")
public class ProductController {

	@Resource
	private ProductService ProductService;
	@Resource
	private ProductClassService ProductClassService;
	@RequestMapping
	public String toProduct(Model model){
		return "prj/product/product";
	}
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam(required = false, value = "pageNum", defaultValue = "1")int pageNum,
			@RequestParam(required = false, value = "pageSize", defaultValue = "15")int pageSize,
	@ModelAttribute Product Product, Model model) {
		if(!SysUserUtils.isSuper()){
			Product.setCreateBy(SysUserUtils.getCacheLoginUser().getId());
		}
		PageInfo<Product> page = ProductService.selectPage(pageNum, pageSize, Product);
		model.addAttribute("page", page);
		return "prj/product/product-list";
	}
	
	
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public String save(@ModelAttribute Product Product,
			HttpServletRequest request,@RequestParam(value="imgs",required=false) MultipartFile imgs) throws Exception {
		Product.setClickhit(0);
		Product.setTypeid(Long.parseLong(Product.getTypename().split(",")[0]));
		Product.setTypename(Product.getTypename().split(",")[1]);
		//String pictureSaveFilePath=ZsCatConstant.pictureProjectSaveFilePath;
		String pictureSaveFilePath = request.getSession().getServletContext()
			      .getRealPath("/static/upload/project");
		if (null != imgs && !imgs.isEmpty()) {
			try {
				UUID id = UUID.randomUUID();
				// 扩展名格式：
//				String extName = "";
//				if (imgs.getOriginalFilename().lastIndexOf(".") >= 0) {
//					extName = imgs.getOriginalFilename().substring(imgs.getOriginalFilename().lastIndexOf("."));
//				}
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
		ProductService.saveProduct(Product);
		return "redirect:/index#/ajax/product";
	}
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute Product Product){
		try {
			return ProductService.deleteProduct(Product);
		} catch (Exception e) {
			LogUtils.ERROR_LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		Product product = ProductService.selectByPrimaryKey(id);
		model.addAttribute("product", product);
		ProductClass productClass=ProductClassService.selectByPrimaryKey(product.getType());
		model.addAttribute("productClass", productClass);
		return "prj/product/product-save";
	}
	
}
