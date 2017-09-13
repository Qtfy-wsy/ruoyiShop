package com.zsTrade.web.web1;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zsTrade.common.constant.Constant;
import com.zsTrade.common.jackson.JsonUtils;
import com.zsTrade.common.redis.RedisUtils;
import com.zsTrade.common.utils.IPUtils;
import com.zsTrade.web.prj.model.Product;
import com.zsTrade.web.prj.service.ProductService;
	/**
	 * 
	 * @author zsCat 2016-10-31 13:59:18
	 * @Email: 951449465@qq.com
	 * @version 4.0v
	 *	商品管理
	 */
@Controller
@RequestMapping("web1/goods")
public class Web1GoodsController {

	@Resource
	private ProductService ProductService;
	
	
	@RequestMapping("/goodsDetail/{id}")
	public ModelAndView goodsDetail(@PathVariable("id") Long id,HttpServletRequest req)throws Exception{
		ModelAndView mav=new ModelAndView();
		Product goods=ProductService.selectByPrimaryKey(id);
		mav.addObject("goods", goods);
		if(goods.getImgmore()!=null && goods.getImgmore().indexOf(",")>-1){
			mav.addObject("imgs", goods.getImgmore().split(","));
		}
		mav.setViewName("web1/goodsDetail");
		goods.setClickhit(goods.getClickhit()+1);
		ProductService.updateByPrimaryKeySelective(goods);
		//查询详情商品的 其他商品
		Product p=new Product();
		p.setCreateBy(goods.getCreateBy());
		List<Product> ownGoods=ProductService.selectPage(1, 15, p, "orderby desc").getList();
		mav.addObject("ownGoods", ownGoods);

		return mav;
	}
	
	
	
	 
	/**
	 * 通过菜单类别
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goodsListBygcId/{gcId}")
	public ModelAndView goodsListBygcId(@PathVariable("gcId") Long gcId)throws Exception{
		ModelAndView mav=new ModelAndView();
		Product g=new Product();
		//g.setGcId(gcId);
		PageInfo<Product> page=ProductService.selectgoodsListByType(1, 40, g);
		mav.addObject("page", page);
		mav.setViewName("web1/search");
		return mav;
	}
	
	@RequestMapping("/goodsListBygcTypeId/{typeId}")
	public ModelAndView goodsListBygcTypeId(@PathVariable("typeId") Long typeId)throws Exception{
		ModelAndView mav=new ModelAndView();
		Product g=new Product();
		g.setTypeid(typeId);
		PageInfo<Product> page=ProductService.selectPage(1, 40, g);
		mav.addObject("page", page);
		mav.setViewName("web1/search");
		return mav;
	}
	@RequestMapping("/goodsList")
	public ModelAndView goodsList()throws Exception{
		ModelAndView mav=new ModelAndView();
		Product g=new Product();
		
		PageInfo<Product> page=ProductService.selectPage(1, 40, g);
		mav.addObject("page", page);
		mav.setViewName("web1/search");
		return mav;
	}
}
